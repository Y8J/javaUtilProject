/**
 * 
 */
package com.zjht.youoil.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;

/**
 * 公钥-私钥证书管理类
 * @author zhengwy
 *
 */
public class CertificateUtil {

	/**
	 * 证书公钥加密(本地测试)
	 * @param message 加密内容
	 * @param encoding 字符集编码
	 * @param cerFilePath 公钥证书文件路径
	 * @return byte  加密后字节码
	 */
	public static byte[] publicEncrypt(String message,String encoding,String cerFilePath){
		 byte[] encryptMessage=null;
		 BufferedInputStream buff=null;
		 if(StringUtils.isNotBlank(cerFilePath)){
			 try {
				 byte[] msg =message.getBytes(encoding);
				 CertificateFactory cff = CertificateFactory.getInstance("X.509");
				 buff = new BufferedInputStream(new FileInputStream(cerFilePath)); // 证书文件
				 Certificate cf = cff.generateCertificate(buff);
				 PublicKey pk = cf.getPublicKey();// 得到证书文件携带的公钥
				 Cipher cipher= Cipher.getInstance("RSA/ECB/PKCS1Padding");// 定义算法：RSA
				 cipher.init(Cipher.ENCRYPT_MODE, pk);           
				 encryptMessage= cipher.doFinal(msg); // 加密后的数据
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
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
			}finally{
				IOUtils.closeQuietly(buff);
			}
		 }
		 return encryptMessage;
	}
	
	/**
	 * 用证书的私钥解密 - 该私钥存在生成该证书的密钥库中(本地测试)
	 * @param message 解密内容
	 * @param cerName 证书名称
	 * @param cerStorePass 证书库密码
	 * @param cerPass 证书密码
	 * @param cerStoreFilePath 证书库文件路径
	 * @return byte  解密后字节码
	 */
	public static byte[] privateDecrypt(byte[] message,String cerName,String cerStorePass,String cerPass,String cerStoreFilePath){
		 byte[] decryptMessage=null;
		 BufferedInputStream buff=null;
		 if(StringUtils.isNotBlank(cerStoreFilePath)){
			 try {
				  buff = new BufferedInputStream(new FileInputStream(cerStoreFilePath));
				  KeyStore ks = KeyStore.getInstance("JKS"); // 加载证书库
				  char[] kspwd = cerStorePass.toCharArray(); // 证书库密码
				  char[] keypwd = cerPass.toCharArray(); // 证书密码
				  ks.load(buff, kspwd);              // 加载证书
				  PrivateKey pk = (PrivateKey)ks.getKey(cerName, keypwd);     // 获取证书私钥
				  Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				  cipher.init(Cipher.DECRYPT_MODE, pk);
				  decryptMessage= cipher.doFinal(message); // 加密后的数据
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnrecoverableKeyException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}finally{
				IOUtils.closeQuietly(buff);
			}
		 }
		 return decryptMessage;
	}
	
	/**
	 * byte数组转化为字符串 
	 * @param content 转换内容
	 * @param encoding 字符编码
	 * @return String  转换字符串
	 */
	public static final String getByteToStr(byte [] content,String encoding){
		 String message=null;
		try {
			message=new String(content,encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return message;
	}
	/**
	 * 证书公钥加密
	 * @param message 加密内容
	 * @param encoding 字符集编码
	 * @param cerFilePath 公钥证书文件路径
	 * @return String  加密后经过base64加密字符串
	 */
	public static final String encode(String message, String encoding, String cerFilePath){
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
		base64 = Base64Helper.encodeToString(encryptMessage);
		return base64;
	}
	/**
	 * 解密
	 * @param publicCerStr 加密后的字符串
	 * @param cerName 证书名
	 * @param cerStorePass 证书库密码
	 * @param cerPass 证书密码
	 * @param cerStoreFilePath 证书库文件路径
	 * @param encoding 编码
	 * @return
	 * @throws IOException
	 */
	public static final String decode(String publicCerStr,String cerName, String cerStorePass,String cerPass, String cerStoreFilePath,String encoding) throws IOException{
		byte[] message= new BASE64Decoder().decodeBuffer(publicCerStr);
		byte[] decryptMessage=null;
		String filePath = "/config/app" + PropertyUtil.getConfigDir() + "/" + cerStoreFilePath;
		BufferedInputStream buff=null;
		if(StringUtils.isNotBlank(cerStoreFilePath)){
			 try {
				InputStream inStream = PropertyUtil.class.getResourceAsStream(filePath);
				buff = new BufferedInputStream(inStream);// 证书文件
				KeyStore ks = KeyStore.getInstance("JKS"); // 加载证书库
				char[] kspwd = cerStorePass.toCharArray(); // 证书库密码
				char[] keypwd = cerPass.toCharArray(); // 证书密码
				ks.load(buff, kspwd);              // 加载证书
				PrivateKey pk = (PrivateKey)ks.getKey(cerName, keypwd);     // 获取证书私钥
				Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				cipher.init(Cipher.DECRYPT_MODE, pk);
				decryptMessage= cipher.doFinal(message); // 加密后的数据
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnrecoverableKeyException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}finally{
				IOUtils.closeQuietly(buff);
			}
		 }
		 return getByteToStr(decryptMessage,encoding);
		//return getByteToStr((privateDecrypt(new BASE64Decoder().decodeBuffer(publicCerStr), "PointExchangeKey", "zjht009", "zjht8899", "E://MyKeyStore//PointExchageStore.pfx")),"utf8");
	}
	
	public static void main(String[] args) {
		try {
			String code=CertificateUtil.encode("100018182569","utf-8",PropertyUtil.getPropertyValueDir("app", "pePublicKeyFileName"));
			System.out.println("公钥加密："+code);
			String pkName=PropertyUtil.getPropertyValueDir("app", "pePublicKeyName");
			String pkCerPass=PropertyUtil.getPropertyValueDir("app", "pePrivateKeyCerPass");
			String pkCerStorePass=PropertyUtil.getPropertyValueDir("app", "pePrivateKeyCerStorePass");
			String pkFileName=PropertyUtil.getPropertyValueDir("app", "pePrivateKeyFileName");
			System.out.println("私钥解密："+CertificateUtil.decode(code,pkName,pkCerStorePass,pkCerPass,pkFileName,"utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
