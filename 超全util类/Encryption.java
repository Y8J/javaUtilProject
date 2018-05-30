package com.zjht.youoil.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Decoder;

public class Encryption {
	private static final String KEY_ALGORITHM = "AES";

	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/NoPadding";
	private static final String IVKEY = "20140214";

	public static void main(String args[]) throws Exception {
		String mobile="18944962927";
		String data = "snihcel";
		String key = "ddkss";
		String sign=DigestUtils.md5Hex("15920015584552ss44d");
		
		System.out.println(encrypt(data, key));
		System.out.println(sign);
		System.out.println(desEncrypt(encrypt(data, key), key));
		
		String str="/N3FYJi99OWPg/UYMoc/8g1WDOKCF9SgAiviLPVu4oGDgUEusmQn0bwzWQbYA8nR6F2s/3yX3iMx\r\nwQKiDrT9IF7pJ+xDT/R76h4i0QtmqWFBbmZyP1ak7BqXbTallMj6aJ0M8rTkj2gDaOm+oIj4bn4c\r\nl1UYz+Jr7DE4AoNFpQvHSfemKZlLUAraURfC30wyLRJyQ/M+oY16czANG2ItQcoLgpZzDsNTxUFc\r\nRucBMkwr9iNPFqEfE2vYvzblxpmUOsSsNxaUOWsuf15/cHXhlw==";
		key="-1157793070";
		
		System.out.println(desEncrypt(str, key));


	}

	/**
	 * 产生一个16位的密码 不足16位在其前面补0 超过16位取期最后16位
	 * 
	 * @param key
	 * @return
	 * @author yangxiaoyong
	 * @version 创建时间：2014年6月20日 下午3:31:30 参考 www.sql8.net
	 */
	private static String createKey(String key) {
		int num=16;
		if (key == null)
			key = "zjht";
		key = key.trim();
		int length = key.length();
		if (length < num) {
			for (int i = 0; i < num - length; i++) {
				key = "0" + key;
			}
		}
		if (length > num) {
			String k = key.substring(length - num, length);
			key = k;
		}
		return key;
	}

	/**编码
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 * @author yangxiaoyong
	 * @version 创建时间：2014年6月20日 下午3:49:19 
	 * 参考 www.sql8.net
	 */
	public static String encrypt(String data, String key) throws Exception {
		try {
			key = createKey(key);
			String iv = createKey(getIvkey());

			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			int blockSize = cipher.getBlockSize();

			byte[] dataBytes = data.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}

			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);

			return new sun.misc.BASE64Encoder().encode(encrypted);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**解码
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 * @author yangxiaoyong
	 * @version 创建时间：2014年6月20日 下午3:49:04 
	 * 参考 www.sql8.net
	 */
	public static String desEncrypt(String data, String key) throws Exception {
		try {
			key = createKey(key);
			String iv = createKey(getIvkey());
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);

			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original);
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**将byte转成String
	 * @param data
	 * @return
	 * @author yangxiaoyong
	 * @version 创建时间：2014年6月20日 下午3:48:29 
	 * 参考 www.sql8.net
	 */
	private static String showByteArray(byte[] data) {
		if (null == data) {
			return null;
		}
		StringBuilder sb = new StringBuilder("{");
		for (byte b : data) {
			sb.append(b).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * @return the ivkey
	 */
	public static String getIvkey() {
		return IVKEY;
	}

}
