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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
/**
 * 手机快捷证书加密、解密工具类
 * @author lijunjie
 *
 */
public class QpaySignature {

	/**
	 * 手机快捷支付   
	 * 通过证书私钥加密文本
	 * @param orgFilePath
	 * @param infoMsg
	 * @param privatePwd
	 * @return
	 */
	public static String encodeMsgByPrivateKeyForQpay(String orgFilePath, String infoMsg, String privatePwd) {
		String base64 = "";
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			String filePath = "/config/mobile" + PropertyUtil.getConfigDir() + "/" + orgFilePath;
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
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			base64 = encoder.encode(signature.sign());
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
	 * 证书公钥加密(手机快捷支付)
	 * 
	 * @param message
	 *            加密内容
	 * @param encoding
	 *            字符集编码
	 * @param cerFilePath
	 *            公钥证书文件路径或文件名(证书一般放在/config/mobile/……/下)
	 * @return String 加密后
	 */
	public static String publicEncryptForQpay(String message, String encoding, String cerFilePath) {
		String base64 = "";
		byte[] encryptMessage = null;
		BufferedInputStream buff = null;
		String filePath = "/config/mobile" + PropertyUtil.getConfigDir() + "/" + cerFilePath;
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
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		base64 = encoder.encode(encryptMessage);
		return base64;
	}

	/**
	 * 通过公钥加密文件与私钥生成的BASE64编码比较是否匹配(手机快捷支付)
	 * @param val
	 * @param msg
	 * @param orgFilePath
	 * @return
	 */
	public static boolean enCodeByCerForQpay(String val, String msg, String orgFilePath) {
		boolean flag = false;
		try {
			String filePath = "/config/mobile" + PropertyUtil.getConfigDir() + "/" + orgFilePath;
			InputStream inStream = PropertyUtil.class.getResourceAsStream(filePath);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
			// 公钥
			PublicKey pk = cert.getPublicKey();
			// 签名
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(pk);
			signature.update(val.getBytes("utf-8"));

			// 解密
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			byte[] verify = decoder.decodeBuffer(msg);
			// System.out.println("##after:" + new String(verify));

			flag = signature.verify(verify);
			// System.out.println("signature result: " + flag);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR");
		}
		return flag;
	}
	/**
	 * 参数键数组
	 * @return
	 */
	public static String[] getNames() {
		String[] names = { "version", "charset", "currency", "partnerCode", "accountName", "orderNo", "orderTime", "amount", "signType", "returnUrl",
				"notifyUrl", "extend1", "extend2" };
		return names;
	}
	/**
	 * 参数值数组
	 * @param model
	 * @return
	 */
	public static String[] getValues(ModelMap model) {
		String version = "";
		String charset = "";
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
		if (model.get("version") != null) {
			version = model.get("version").toString();
		}
		if (model.get("signType") != null) {
			signType = model.get("signType").toString();
		}
		if (model.get("charset") != null) {
			charset = model.get("charset").toString();
		}
		if (model.get("partnerCode") != null) {
			partnerCode = model.get("partnerCode").toString();
		}
		if (model.get("accountName") != null) {
			accountName = model.get("accountName").toString();
		}
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
