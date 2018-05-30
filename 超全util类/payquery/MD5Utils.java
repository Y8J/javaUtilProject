package com.zjht.youoil.util.payquery;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * MD5签名工具类
 * @author liujun
 * @since 2017年6月29日 下午5:10:10
 */
public class MD5Utils {

	/**
	 * 根据传入参数进行MD5签名
	 * @author liujun
	 * @since 2017年6月26日 上午10:38:23
	 * @param signValueMap - 加密value值集合
	 * @param signKeyNames - 加密key集和  
	 * @param key - 加密key值
	 * @return
	 */
	public static String sign(Map<String, String> signValueMap,String [] signKeyNames,String key) {
		String sign = "";
		if(signValueMap != null && signValueMap.size() > 0){
			//先将加密key按字典排序
			Arrays.sort(signKeyNames);
			
			//组装签名串明文
			StringBuilder sb = new StringBuilder("");
			for(String name:signKeyNames){
				if(!name.equals("sign") && !name.equals("key")){
					sb.append(name+"="+ signValueMap.get(name).toString().trim()+"&");
				}
			}
			//加上key
			sb.append("key="+key);
			System.out.println("加密明文:"+sb.toString());
			//返回加密串
			sign = MD5Encode(sb.toString());
		}
		return sign;
	}

	/**
	 * MD5加密
	 * 
	 * @param plainText
	 *            明文
	 * @return 32位大写密文
	 */
	public static String MD5Encode(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			re_md5 = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5.toUpperCase();
	}

}
