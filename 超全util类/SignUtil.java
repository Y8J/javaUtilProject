package com.zjht.youoil.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.zjht.youoil.util.hsh.EncryptUtil;
import com.zjht.youoil.util.hsh.MapUtil;

public class SignUtil {

	/**
	 * 计算阿里巴巴签名；
	 * 首先将通知过来的所有参数（注意是所有参数，而不是下面文档中列举的参数），除去sign本身，以及值是空的参数，按参数名字母升序排序，
	 * 然后按参数1值1参数2值2…参数n值n（这里的参数和值必须是淘宝通知过来的原始值，不能是经过处理的，
	 * 如不能将&quot;转成”后再拼接）的方式进行连接，得到一个字符串；然后在连接后得到的字符串前面加上通知验证密钥，然后计算md5值，转成大写;
	 * 最后拿这个计算出来的MD5串同淘宝通知里面的sign参数值做比较，如果一致，表明该通知是安全的(是淘宝发的且没有被篡改过)；否则，
	 * 该通知可能是被篡改过的，存在不安全性 isFilterLastKey 是否过滤最后一个key加入签名方式
	 */
	public static String computeSign(String key, Map<String, Object> paraMap, boolean isFilterLastKey) {
		Map<String, Object> filterMap = MapUtil.filterMapByKeyValue(paraMap, "sign", "");// 过滤空值和sign键
		Map<String, Object> sortMap = MapUtil.sortMapByKey(filterMap);// 升序
		StringBuffer sb = new StringBuffer();
		sb.append(key);
		Iterator<Entry<String, Object>> iter = sortMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			Object mapKey = entry.getKey();
			Object mapValue = entry.getValue();
			sb.append(mapKey);
			sb.append(mapValue);
		}
		if (!isFilterLastKey) {
			sb.append(key);
		}
		return EncryptUtil.getMD5Str(sb.toString().replaceAll(" ", ""), "gbk").toUpperCase();
	}

	/**
	 * 计算签名； 首先将通知过来的所有参数（注意是所有参数，而不是下面文档中列举的参数），除去sign本身，以及值是空的参数，按参数名字母升序排序，
	 * 然后按参数1值1参数2值2…参数n值n（这里的参数和值必须是中经汇通通知过来的原始值，不能是经过处理的，
	 * 如不能将&quot;转成”后再拼接）的方式进行连接，得到一个字符串；然后在连接后得到的字符串前面加上通知验证密钥，然后计算md5值，转成大写;
	 * 最后拿这个计算出来的MD5串同中经汇通通知里面的sign参数值做比较，如果一致，表明该通知是安全的(是中经汇通发的且没有被篡改过)；否则，
	 * 该通知可能是被篡改过的，存在不安全性
	 */
	public static String computeSign(String key, Map<String, Object> paraMap) {
		Map<String, Object> filterMap = MapUtil.filterMapByKeyValue(paraMap, "sign", "");// 过滤空值和sign键
		Map<String, Object> sortMap = MapUtil.sortMapByKey(filterMap);// 升序
		StringBuffer sb = new StringBuffer();
		sb.append(key);
		Iterator<Entry<String, Object>> iter = sortMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			Object mapKey = entry.getKey();
			Object mapValue = entry.getValue();
			sb.append(mapKey);
			sb.append(mapValue);
		}
		sb.append(key);
		return EncryptUtil.getMD5Str(sb.toString().replaceAll(" ", ""), "gbk").toUpperCase();
	}

	public static boolean validateSign(Map<String, Object> paraMap, String reSign, String key) {

		String sign = SignUtil.computeSign(key, paraMap);
		if (reSign != null) {
			if (reSign.equals(sign)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
