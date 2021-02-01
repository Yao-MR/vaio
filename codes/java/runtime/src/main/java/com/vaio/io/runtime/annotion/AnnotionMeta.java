package com.vaio.io.runtime.annotion;


import java.lang.annotation.*;

/**
 * java提供五种元注解，分别是：
 * - @Retention 指定注解的生命周期，即存活时间。
 *         RetentionPolicy.SOURCE
 *         RetentionPolicy.CLASS
 *         RetentionPolicy.RUNTIME
 * - @Documented javadoc命令生成的文档中体现注解的内容
 * - @Target 指定注解可用于哪些元素，例如类、方法、变量等
 *         - ElementType.ANNOTATION_TYPE 用于描述注解类型
 *         - ElementType.CONSTRUCTOR 用于注解构造方法
 *         - ElementType.FIELD 用于变量注解
 *         - ElementType.LOCAL_VARIABLE 用于局部变量注解
 *         - ElementType.METHOD 用于方法注解
 *         - ElementType.PACKAGE 用于包注解
 *         - ElementType.PARAMETER 用于方法内的参数注解
 *         - ElementType.TYPE 用于类、接口、枚举注解
 * - @Inherited 注解的继承性，
 * - @Repeatable 可重复使用的注解
 *
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface AnnotionMeta {
    public String doAnnotion();
}
