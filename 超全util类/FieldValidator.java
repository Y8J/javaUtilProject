package com.zjht.youoil.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.zjht.youoil.common.exception.InvalidFieldException;

/**
 * 标注注解@lFieldSpecification的字段检查类
 * 
 * @author jun
 * @since JDK 1.8
 * @date Sep 28, 2015 11:04:21 AM
 */
public class FieldValidator {

	private final static Class<FieldSpecification> ANNOTAION_CLASS = FieldSpecification.class;

    /**
     * 根据@FieldSpecification注解校验字段
     * 
     * @author jun
     * @param instance
     * @param field
     * @throws IllegalAccessException 
     * @throws InvalidFieldException 
     */
    private static void validateField(Object instance, Field field) throws InvalidFieldException, IllegalAccessException {
        FieldSpecification fieldSpec = null;
        Class<?> fieldType = null;
        Object fieldValue  = null;

        field.setAccessible(true);
        fieldValue = field.get(instance);
        fieldSpec  = field.getAnnotation(ANNOTAION_CLASS);
        fieldType  = field.getType();
        
        if(!isSupportFieldType(fieldType)){
        	throw new InvalidFieldException("Unsupported field type :"+fieldType);
        }

        validateFieldValue(fieldSpec, (String)fieldValue);
    }

    /**
     * 判断是否支持的字段类型
     * 
     * @param fieldType
     * @return
     */
    private static boolean isSupportFieldType(Class<?> fieldType) {
    	Preconditions.checkArgument(fieldType!=null , "Field Type can not be null!");
		return fieldType.isAssignableFrom(String.class);
	}

	/**
	 * 校验字段
	 * 
     * @author jun
     * @param fieldType
     * @param fieldSpec
     * @param fieldValue
     */
    private static void validateFieldValue( FieldSpecification fieldSpec,
            String fieldValue) {
    	
    	boolean required = fieldSpec.required();
		String name      = fieldSpec.name();// 节点名称
		String regex     = fieldSpec.regex();// 正则
		int length       = fieldSpec.length();// 长度
		int minLength    = fieldSpec.minLength();// 最小长度
		int maxLength    = fieldSpec.maxLength();// 最大长度
		String[] range   = fieldSpec.range();// 范围
		String value     = fieldValue;

        // 只校验java.lang.String类型且字段为必须或者字段为非必须但是值不为空
        if (!required && Strings.isNullOrEmpty(value)) {
            return;
        }

        if (required && Strings.isNullOrEmpty(value)) {
            throw new InvalidFieldException("字段[" + name + "]的值不能为空");
        }

        if (minLength > 0 && minLength > value.length()) {
            throw new InvalidFieldException("字段[" + name + "]的值["+value+"]的长度必须大于等于" + minLength);
        }

        if (maxLength > 0 && maxLength < value.length()) {
            throw new InvalidFieldException("字段[" + name + "]的值["+value+"]的长度必须小于于等于" + maxLength);
        }

        if (length > 0 && length != value.length()) {
            throw new InvalidFieldException("字段[" + name + "]的值["+value+"]的长度必须等于" + length);
        }

        if ((!Strings.isNullOrEmpty(regex)
                && !Pattern.compile(regex).matcher(value).matches())) {
            throw new InvalidFieldException("字段[" + name + "]的值["+value+"]必须满足正则表达式："+ regex);
        }
        
        if (range!=null && range.length > 0 && !Arrays.asList(range).contains(value)) {
            throw new InvalidFieldException("字段[" + name + "]的值["+value+"]必须满足取值范围："+ Arrays.toString(range));
        }
    }


    /**
     * 校验对象中的字段</br>
     * <b>仅当对象中的字段有@lFieldSpecification注解标注时使用</b>
     * 
     * @author jun
     * @param t
     */
    public static void validate(Object instance) {
        Preconditions.checkArgument(instance!=null, "object cant be null!");
        
        Class<?> clazz = instance.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field field = null;
        
        try {
        	for (int i = 0; i < fields.length; i++) {
        		field = fields[i];
        		if(null==field.getAnnotation(ANNOTAION_CLASS)){
        			continue;
        		}
        		validateField(instance,field);
			}
/*            Stream.of(fields).filter(f -> {
                return !Objects.isNull(f.getDeclaredAnnotation(FieldSpecification.class))
                        && isSupportFieldType(f.getType());
            }).forEach(f -> {
                validateField(t, f);
            });*/
        } catch (Exception e) {
            throw new InvalidFieldException(e.getMessage());
        }
    }
}