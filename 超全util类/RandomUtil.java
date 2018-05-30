package com.zjht.youoil.util;

import java.util.Random;

/**
 * 随机数工具类
 * @author huangshipiao
 * @time   2015-9-8 下午2:09:02
 */
public class RandomUtil {
		
	
	public static String getCharAndNumr(int length)     
	{     
	    String val = "";     
	             
	    Random random = new Random();     
	    for(int i = 0; i < length; i++)     
	    {     
	        String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字     
	                 
	        if("char".equalsIgnoreCase(charOrNum)) // 字符串     
	        {     
	            int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母     
	            val += (char) (choice + random.nextInt(26));     
	        }     
	        else if("num".equalsIgnoreCase(charOrNum)) // 数字     
	        {     
	            val += String.valueOf(random.nextInt(10));     
	        }     
	    }     
	             
	    return val;     
	}   
	/**
	 * 产生指定范围的随机long型数字
	 * @param rdm
	 * @param n
	 * @return
	 */
	public static long nextLong(Random rdm, long n) {  
	   // error checking and 2^x checking removed for simplicity.  
	   long bits, val;  
	   do {
	      bits = (rdm.nextLong() << 1) >>> 1;
	      val = bits % n;
	   } while (bits-val+(n-1) < 0L);
	   return val;
	}
	public static void main(String[] args) {
		
		for (int i = 0; i < 10000; i++) {
			System.out.println(i+1+":"+getCharAndNumr(6));
		}
	}
}
