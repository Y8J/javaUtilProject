package com.zjht.youoil.util.payquery;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Java对象和JSON字符串相互转化工具类 
 * @author liujun
 * @since 2016年8月16日 上午10:55:28
 */  
public final class JsonUtils {  

	
	/**
	 * Map转Json字符串
	 * @author liujun
	 * @since 2016年8月16日 上午10:55:25
	 * @param paramMap
	 * @return
	 */
	public static String getMapToJsonString(Map<String, Object> paramMap) {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(paramMap);
		return jsonStr.toString();
	}
	
	/**
    * 将json字符串转换为map
    * @param data
    * @return
    */
   public static Map<String, String> getJsonStringToMap(String data){
       GsonBuilder gb = new GsonBuilder();
       Gson g = gb.create();
       Map<String, String> map = g.fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
       return map;
   }
  
    /** 
     * json字符串转成对象类型 
     * @param str   
     * @param type 
     * @return  
     */  
    public static <T> T fromJsonByType(String str, Type type) {  
        Gson gson = new Gson();  
        return gson.fromJson(str, type);  
    }  
  
    /** 
     * json字符串转成对象 
     * @param str   
     * @param type  
     * @return  
     */  
    public static <T> T fromJsonByClass(String str, Class<T> objClass) {  
        Gson gson = new Gson();  
        return gson.fromJson(str, objClass);  
    }  

}
