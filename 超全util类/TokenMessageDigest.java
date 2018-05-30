package com.zjht.youoil.util;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;

/**
 * 
 * @author lijunjie
 *
 */
public class TokenMessageDigest {
	/**
	 * 单例持有类
	 * 
	 */
	private static class SingletonHolder {
		static final TokenMessageDigest INSTANCE = new TokenMessageDigest();
	}

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static TokenMessageDigest getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private MessageDigest digest;

	private TokenMessageDigest() {
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (Exception e) {
			throw new InternalError("init MessageDigest error:"
					+ e.getMessage());
		}
	}

	/**
	 * 将字节数组转换成16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		StringBuilder sbDes = new StringBuilder();
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				sbDes.append("0");
			}
			sbDes.append(tmp);
		}
		return sbDes.toString();
	}

	private String encrypt(String strSrc) {
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		digest.update(bt);
		strDes = byte2hex(digest.digest());
		return strDes;
	}
	/**
	 * 生成token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public String token(String timestamp, String nonce){
		String key = getKey();
		String[] arrTmp = { key, timestamp, nonce };
		Arrays.sort(arrTmp);
		StringBuffer sb = new StringBuffer();
		// 2.将三个参数字符串拼接成一个字符串进行sha1加密
		for (int i = 0; i < arrTmp.length; i++) {
			sb.append(arrTmp[i]);
		}
		String expectedSignature = encrypt(sb.toString());
		return expectedSignature;
	}
	
	/**
	 * 校验请求的签名是否合法
	 * 
	 * 加密/校验流程：
	 * 1. 将token、timestamp、nonce三个参数进行字典序排序 
	 * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3. 开发者获得加密后的字符串可与signature对比
	 * 
	 * @param token 密文
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return
	 */
	public boolean validate(String token, String timestamp, String nonce) {
		// 1. 将token、timestamp、nonce三个参数进行字典序排序
		String key = getKey();
		String[] arrTmp = { key, timestamp, nonce };
		Arrays.sort(arrTmp);
		StringBuffer sb = new StringBuffer();
		// 2.将三个参数字符串拼接成一个字符串进行sha1加密
		for (int i = 0; i < arrTmp.length; i++) {
			sb.append(arrTmp[i]);
		}
		String expectedSignature = encrypt(sb.toString());
		// 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		if (expectedSignature.equals(token)) {
			return true;
		}
		return false;
	}

	private String getKey() {
		return "youoil";
	}

	public static void main(String[] args) {

		String signature = "826be835c1d18d36d2d71ca301dda56dfdd4829d";// 加密需要验证的签名
		String timestamp = "1371608072";// 时间戳
		String nonce = "1372170854";// 随机数

		TokenMessageDigest yywDigest = TokenMessageDigest.getInstance();
		boolean bValid = yywDigest.validate(signature, timestamp, nonce);
		if (bValid) {
			System.out.println("token 验证成功!");
		} else {
			System.out.println("token 验证失败!");
		}
		Long time=1434423581935L;
		Date d=new Date();
		System.out.println((d.getTime()-time)/1000);
	}
}
