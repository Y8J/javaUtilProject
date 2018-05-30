package com.zjht.youoil.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;

public class AppSignaturePair {
	// 通过私钥加密文本
	public static String encodeMsgByPrivateKey(String orgFilePath, String infoMsg, String privatePwd) {
		String base64 = "";
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			String filePath = "/config/app" + PropertyUtil.getConfigDir() + "/" + orgFilePath;
			InputStream ksfis = PropertyUtil.class.getResourceAsStream(filePath);
			// FileInputStream ksfis = new FileInputStream(orgFilePath);
			BufferedInputStream ksbufin = new BufferedInputStream(ksfis);
			char[] keyPwd = privatePwd.toCharArray();
			ks.load(ksbufin, keyPwd);
			ksbufin.close();
			Enumeration<String> enum1 = ks.aliases();
			String keyAlias = null;
			if (enum1.hasMoreElements()) { // we are readin just one
											// certificate.
				keyAlias = enum1.nextElement();
				System.out.println("alias=[" + keyAlias + "]");
			}
			// 私钥
			PrivateKey priK = (PrivateKey) ks.getKey(keyAlias, keyPwd);

			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(priK);
			signature.update(infoMsg.getBytes("utf-8"));
//			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
//			base64 = encoder.encode(signature.sign());
            org.apache.commons.codec.binary.Base64 encoder = new org.apache.commons.codec.binary.Base64();
            base64 = encoder.encodeToString(signature.sign());
			// base64=Base64.encodeBase64String(signature.sign());
			// System.out.println("写入对象 pubkeys ok");
			System.out.println("生成密钥对成功");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			// System.out.println("生成密钥对失败");
		}
		return base64;
	}

	/**
	 * 证书公钥加密
	 * 
	 * @param message
	 *            加密内容
	 * @param encoding
	 *            字符集编码
	 * @param cerFilePath
	 *            公钥证书文件路径或文件名(证书一般放在/config/app/……/下)
	 * @return String 加密后
	 */
	public static String publicEncrypt(String message, String encoding, String cerFilePath) {
		String base64 = "";
		byte[] encryptMessage = null;
		BufferedInputStream buff = null;
		String filePath = "/config/app" + PropertyUtil.getConfigDir() + "/" + cerFilePath;
		if (StringUtils.isBlank(encoding)) {
			encoding = "UTF-8";
		}
		if (StringUtils.isNotBlank(cerFilePath)) {
			try {
				byte[] msg = message.getBytes(encoding);
				CertificateFactory cff = CertificateFactory.getInstance("X.509");
				InputStream inStream = PropertyUtil.class.getResourceAsStream(filePath);
				buff = new BufferedInputStream(inStream);// 证书文件
				Certificate cf = cff.generateCertificate(buff);
				PublicKey pk = cf.getPublicKey();// 得到证书文件携带的公钥
				Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// 定义算法：RSA
				cipher.init(Cipher.ENCRYPT_MODE, pk);
				encryptMessage = cipher.doFinal(msg); // 加密后的数据
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(buff);
			}
		}
//		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
//        base64 = encoder.encode(encryptMessage);
        org.apache.commons.codec.binary.Base64 encoder = new org.apache.commons.codec.binary.Base64();
        base64=encoder.encodeToString(encryptMessage);
		return base64;
	}

	// 通过公钥加密文件与私钥生成的BASE64编码比较是否匹配
	public static boolean enCodeByCer(String val, String msg, String orgFilePath) {
		boolean flag = false;
		try {
			String filePath = "/config/app" + PropertyUtil.getConfigDir() + "/" + orgFilePath;
			InputStream inStream = PropertyUtil.class.getResourceAsStream(filePath);
			// InputStream inStream = new FileInputStream(orgFilePath);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
			// 公钥
			PublicKey pk = cert.getPublicKey();
			// 签名
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(pk);
			signature.update(val.getBytes("utf-8"));

			// 解密
//			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
//            byte[] verify = decoder.decodeBuffer(msg);
			org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
			byte[] verify =base64.decode(msg);

			// System.out.println("##after:" + new String(verify));

			flag = signature.verify(verify);
			// System.out.println("signature result: " + flag);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR");
		}
		return flag;
	}

	public static String[] getNames() {
		// String[]
		// names={"version","charset","clientCode","clientFuncCode","orderId","amount","curCode","orderTime","extend1","extend2","returnUrl","notifiyUrl"};
		// String[]
		// names={"version","charset","currency","partnerCode","accountName","orderNo","orderTime","amount","signType","returnUrl","notifyUrl","extend1","extend2"};

		// String[]
		// names={"version","charset","currency","partnerCode","accountName","rechargeType","orderNo","orderTime","cardAccount","amount","name","mobile","signType","returnUrl","notifyUrl","extend1","extend2"};
		String[] names = { "version", "charset", "currency", "partnerCode", "accountName", "orderNo", "orderTime", "amount", "signType", "returnUrl",
				"notifyUrl", "extend1", "extend2" };

		return names;
	}

	public static String[] getValues(ModelMap model) {
		String version = "";
		String charset = "";
		// String clientCode = "";
		// String clientFuncCode = "";
		String orderNo = "";
		String amount = "";
		String currency = "";
		String orderTime = "";
		String extend1 = "";
		String extend2 = "";
		String returnUrl = "";
		String notifyUrl = "";
		String signType = "";
		String partnerCode = "";
		String accountName = "";
		// String password="";

//		String rechargeType = "";
//		String name = "";
//		String mobile = "";
//		String cardAccount = "";

		if (model.get("version") != null) {
			version = model.get("version").toString();
		}
		if (model.get("signType") != null) {
			signType = model.get("signType").toString();
		}
		if (model.get("charset") != null) {
			charset = model.get("charset").toString();
		}
		// if( model.get("clientCode")!=null){
		// clientCode = model.get("clientCode").toString();
		// }
		// if(model.get("clientFuncCode")!=null){
		// clientFuncCode = model.get("clientFuncCode").toString();
		// }
		if (model.get("partnerCode") != null) {
			partnerCode = model.get("partnerCode").toString();
		}
		if (model.get("accountName") != null) {
			accountName = model.get("accountName").toString();
		}
		// if(model.get("password")!=null){
		// password = model.get("password").toString();
		// }

		if (model.get("orderNo") != null) {
			orderNo = model.get("orderNo").toString();
		}
		if (model.get("amount") != null) {
			amount = model.get("amount").toString();
		}
		if (model.get("currency") != null) {
			currency = model.get("currency").toString();
		}
		if (model.get("orderTime") != null) {
			orderTime = model.get("orderTime").toString();
		}
		if (model.get("extend1") != null) {
			extend1 = model.get("extend1").toString();
		}
		if (model.get("extend2") != null) {
			extend2 = model.get("extend2").toString();
		}
		if (model.get("returnUrl") != null) {
			returnUrl = model.get("returnUrl").toString();
		}
		if (model.get("notifyUrl") != null) {
			notifyUrl = model.get("notifyUrl").toString();
		}

//		if (model.get("rechargeType") != null) {
//			rechargeType = model.get("rechargeType").toString();
//		}
//		if (model.get("name") != null) {
//			name = model.get("name").toString();
//		}
//		if (model.get("mobile") != null) {
//			mobile = model.get("mobile").toString();
//		}
//		if (model.get("cardAccount") != null) {
//			cardAccount = model.get("cardAccount").toString();
//		}

		// String[]
		// values={version,charset,clientCode,clientFuncCode,orderId,amount,curCode,orderTime,extend1,extend2,returnUrl,notifiyUrl};
		// String[]
		// values={version,charset,currency,partnerCode,accountName,orderNo,orderTime,amount,signType,returnUrl,notifyUrl,extend1,extend2};

		// String[]
		// values={version,charset,currency,partnerCode,accountName,rechargeType,orderNo,orderTime,cardAccount,amount,name,mobile,signType,returnUrl,notifyUrl,extend1,extend2};
		String[] values = { version, charset, currency, partnerCode, accountName, orderNo, orderTime, amount, signType, returnUrl, notifyUrl, extend1, extend2 };

		return values;
	}

	public static String getSignMsg(String[] names, String[] values) {
		String signMsgVal = "";
		if (names != null && names.length > 0) {
			for (int i = 0; i < names.length; i++) {
				signMsgVal = appendParam(signMsgVal, names[i], values[i]);
			}
		}
		return signMsgVal;
	}

	public static String getFilePath(String type, HttpServletRequest request) {
		String filePath = "";
		if ("privateKeyName".equals(type)) {
			String privateKeyName = request.getSession().getServletContext().getRealPath("/") + "/WEB-INF/classes/config/app" + PropertyUtil.getConfigDir()
					+ "/" + PropertyUtil.getPropertyValue("app", "privateKeyName");
			filePath = privateKeyName;
		} else if ("publicKeyName".equals(type)) {
			String publicKeyName = request.getSession().getServletContext().getRealPath("/") + "/WEB-INF/classes/config/app" + PropertyUtil.getConfigDir()
					+ "/" + PropertyUtil.getPropertyValue("app", "publicKeyName");
			filePath = publicKeyName;
		}
		return filePath;
	}

	public static String appendParam(String returns, String paramId, String paramValue) {
		if (returns != "") {
			String tmp = ((paramValue != null && !"".equals(paramValue)) ? paramValue : "");
			returns += "&" + paramId + "=" + tmp;
		} else {
			String tmp = ((paramValue != null && !"".equals(paramValue)) ? paramValue : "");
			returns = paramId + "=" + tmp;
		}

		return returns;
	}
}
