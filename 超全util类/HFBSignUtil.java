package com.zjht.youoil.util;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.zjhtc.htm.message.serializable.util.MapKeyComparator;
/**
 * 话费宝
 * @author lijunjie
 *
 */
public class HFBSignUtil {
    /**
     * 计算签名； 首先将通知过来的所有参数（注意是所有参数，而不是下面文档中列举的参数），除去sign本身，以及值是空的参数，按参数名字母升序排序，
     * 然后按参数1值1参数2值2…参数n值n（这里的参数和值必须是中经汇通通知过来的原始值，不能是经过处理的，
     * 如不能将&quot;转成”后再拼接）的方式进行连接，得到一个字符串；然后在连接后得到的字符串前面加上通知验证密钥，然后计算md5值，转成大写;
     * 最后拿这个计算出来的MD5串同中经汇通通知里面的sign参数值做比较，如果一致，表明该通知是安全的(是中经汇通发的且没有被篡改过)；否则，
     * 该通知可能是被篡改过的，存在不安全性
     */
    public static String computeSign(String key, Map<String, Object> paraMap) {
        Map<String, Object> filterMap = filterMapByKeyValue(paraMap, "sign", "");// 过滤空值和sign键
        Map<String, Object> sortMap = sortMapByKey(filterMap);// 升序
        if (sortMap == null) {
            return null;
        }
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
        return encodeMd5(sb.toString().replaceAll(" ", ""), "gbk").toUpperCase();
    }

    public static final String encodeMd5(String s,String charsetName) {
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
                'E', 'F' };
        try {
            byte[] btInput = s.getBytes(charsetName);
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
                str[(k++)] = hexDigits[(byte0 & 0xF)];
            }
            return new String(str);
        } catch (Exception e) {
            System.out.println("md5 encode error!,original message : " + s + ",fail detail info:");
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    public static Map<String, Object> filterMapByKeyValue(Map<String, Object> map,
            String filterKey, String filterValue) {
        @SuppressWarnings("unchecked")
        Map<String, Object> resultMap = new HashMap();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Object val = entry.getValue();
            if ((!filterKey.equals(key)) && (!filterValue.equals(val))) {
                resultMap.put(key, val);
            }
        }
        return resultMap;
    }

    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if ((map == null) || (map.isEmpty())) {
            return null;
        }
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Map<String, Object> sortMap = new TreeMap(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }
    
}
