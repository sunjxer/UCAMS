package com.ucams.modules.ams.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @author 作者: sunjx 
 * @version 创建时间：2017年7月25日
 * 注解类
 */
//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Retention(RetentionPolicy.RUNTIME) 
//定义注解的作用目标**作用范围字段、枚举的常量/方法
@Target({ElementType.FIELD,ElementType.METHOD})
//说明该注解将被包含在javadoc中
@Documented
public @interface FieldMeta {

	/**
	 * 是否为序列号
	 * @return
	 */
	boolean id() default false;
	/**
	 * 字段名称
	 * @return
	 */
	String name() default "";
	/**
	 * 是否在列表中显示
	 * @return
	 */
	boolean summary() default true;
	/**
	 * 字段描述
	 * @return
	 */
	String description() default "";
}