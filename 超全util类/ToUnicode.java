package com.zjht.youoil.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**专门用于中文转Unicode
 * @author yangxiaoyong
 * @version 创建时间：2014年7月9日 下午3:46:00 
 * 参考：www.sql8.net
 */
public class ToUnicode {

	public static void main(String[] args) {

		String s = "时间:${inTime}\r\n订单号:${orderNo}\r\n姓名:${receiverName}\r\n身份证:${receiverRemarks}\r\n手机号:${receiverMobile}\r\n推荐人:${remarks}\r\n流水号:${payNo}\r\n商品:${name}\r\n金额:${payAmount}\r\n订单状态:${status}\r\n支付状态:${payStatus}\r\n";
		s = stringToUnicode(s);
		System.out.print(s);
	}

	/**
	 * description：把中文字符串转换为十六进制Unicode编码字符串(assic码字符不转换)
	 * 
	 * @param s
	 * @return
	 */
	public static String stringToUnicode(String s) {
		String str = "";

		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);

			if (ch > 255) {
				str += "\\u" + Integer.toHexString(ch);
			} else {
				// str += "\\" + Integer.toHexString(ch);
				str += s.substring(i, i + 1);
			}
		}
		return str;
	}

	/**
	 * description：把十六进制Unicode编码字符串转换为中文字符串(assic码字符不转换)
	 * 
	 * @param str
	 * @return
	 */
	public static String unicodeToString(String str) {

		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

		Matcher matcher = pattern.matcher(str);

		char ch;

		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);

			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

}
