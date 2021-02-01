package com.vaio.io.runtime.annotion;


/**
 *java内置的注解有五个：
 * - @Deprecated 注解主要用于给类、方法、变量打上不建议使用的标签，如果你的代码使用了不建议使用的类、方法、变量，编译器就会给你一个警告
 * - @Override 注解在方法上使用，标识这个方法重写父类的方法。如果这个方法和父类方法不一致，编译器就会显示错误。
 * - @SuppressWarnings 注解也是在方法上使用，用于抑制警告，在调用deprecated的方法或者进行不安全的类型转化时，编译器会发出一些警告，使用@SuppressWarnings就可以忽略那些警告。
 * - @SafeVarargs 注解主要用于抑制参数类型安全检查警告，这个是jdk1.7新增的功能。 使
 * - @FunctionalInterface 注解主要用于编译级错误检查，加上该注解，当你写的接口不符合函数式接口定义的时候，编译器会报错。
 *
 *
 *
 *
 */
public @interface VaioAnnotionWithArgs {
    //注解的成员变量以无参数无方法体的方法形式声明。
    int age();
    String name();
}
