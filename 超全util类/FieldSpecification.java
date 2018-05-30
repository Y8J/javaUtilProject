package com.zjht.youoil.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * ClassName: FieldSpecification <br/> 
 * Function: 字段规则注解. <br/>
 * 仅适用于java.lang.String类型的字段，不适用于其他对象字段. <br/>
 * 
 * @author jun 
 * @version v0.1
 * @since JDK 1.8
 * @date Sep 17, 2015 1:13:15 PM <br/>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldSpecification {
	/**
	 * 字段是否必须. <br/> 
	 * 
	 * @author jun 
	 * @return 
	 * @since JDK 1.8
	 */
	public boolean required() default false;
	
	/**
	 * 字段长度<br/>
	 * 
	 * 
	 * @author jun 
	 * @return 
	 * @since JDK 1.8
	 */
	public int length() default -1;
	
	/**
	 * 字段最大长度<br/>
	 *  
	 * 
	 * @author jun 
	 * @return 
	 * @since JDK 1.8
	 */
	public int maxLength() default 0;
	/**
     * 字段是否必须. <br/> 
     * 
     * @author jun 
     * @return 
     * @since JDK 1.8
     */
	
	/**
	 * 字段最小长度<br/>
	 *  
	 * 
	 * @author jun 
	 * @return 
	 * @since JDK 1.8
	 */
	public int minLength() default 0;
	
	
	/**
	 * 正则
	 * 
	 * 
	 * @author jun 
	 * @return 
	 * @since JDK 1.8
	 */
	public String regex() default "";
	

	/**
	 * 字段的名称<br/> 
	 * 
	 * @author jun 
	 * @return 
	 * @since JDK 1.8
	 */
	public String name(); 

	/**
	 *
	 * 字段取值范围
	 * 
	 * @author jun
	 * @return 
	 * @since JDK 1.8
	 */
	public String[] range() default {};
	
	/**
	 * 默认值
	 * 
	 * @author jun
	 * @return
	 */
	public String value() default "";
	
	
	/**
	 * 备注
	 * 
	 * @return
	 */
	public String remark() default "";
	
}
