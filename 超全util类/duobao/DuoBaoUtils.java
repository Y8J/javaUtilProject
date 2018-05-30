package com.zjht.youoil.util.duobao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 此类用于一元夺宝幸运号的生成,产生唯一不重复的幸运号
 * @author yangjing
 *
 */
public class DuoBaoUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(DuoBaoUtils.class);
	    
	 /**
	  * 获取一定范围的随机数(已加同)
	  * @param min
	  * @param max
	  * @param n
	  * @param set1
	  * @param list
	  */
	 public synchronized static void randomSet(int min, int max, int n, Set<Integer> set1,List<Integer> list) {  
		 if (n > (max - min + 1) || max < min) {  
             return;  
         }  
         for (int i = 0; i < n; i++) {           
        	 /**
        	  * 返回指定范围的随机数(m-n之间)的公式但是取不到n
        	  * Math.random()*(n-m)+m;
        	  */
             int num = (int) (Math.random() * (max - min)) + min;  
             while (set1.contains(num)) {
                 num = (int) (Math.random() * (max - min)) + min;
             }
             set1.add(num);// 将不同的数存入HashSet中  
             list.add(num);
         }  
         int setSize = set1.size();  
         // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小  
         if (setSize < n) {  
          randomSet(min, max, n - setSize, set1,list);// 递归  
         }  
     }

       
       
	 /**
	  * 
	  * @param n 要几个夺宝号
	  * @param set1 保存数据库原有夺宝号
	  * @param set1 保存本次执行的夺宝号
	  */
	 public synchronized static void orderSet(int n, Set<Integer> set,Set<Integer> set1) {  
		 
		 Integer j =  Collections.max(set);
		 
		 //循环获取指定夺宝号个数
		 for(int i=0;i<n;i++){
			 set1.add(++j);
		 }
		 
		 //如果循环次数少于原有数据长度+所需夺宝号长度就再次循环获取夺宝号
		 if(set.size()+set1.size()<set.size()+n){
			 Integer g = Collections.max(set1);
			 for(int i=0;i<(set.size()+n)-(set.size()+set1.size());i++){
				set1.add(++g);
			 }
		 }
		 
	 }

    /**
     * 截取字符串有[10001,10002,1003]截取成10001,10002,1003
     * @param set
     * @return
     */
	public static String subString(String set){
		return set.substring(set.indexOf("[")+1,set.indexOf("]"));
		
	}
	 
   	public static void main(String[] args) {
		HashSet<Integer> set = new HashSet<Integer>();
		set.add(1);
		HashSet<Integer> list = new HashSet<Integer>();
		
		//DuoBaoUtils.randomSet(1, 10, 1, set);
		
		DuoBaoUtils.orderSet(3, set, list);
		
		System.out.println("list:"+list.toString());
		System.out.println("list:"+DuoBaoUtils.subString(list.toString()));
		
		System.out.println("set:"+set.toString());
		System.out.println("list:"+DuoBaoUtils.subString(set.toString()));
	}
}
