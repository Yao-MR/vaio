package com.vaio.io.designpattern;

/**
 * 题目:
 *      单例模式
 *
 * 思路:
 *
 * 算法:
 *      静态内部类实现单例模式
 *
 *
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-04-25
 */
public class Singleton {

    //静态内部类实现
    public static class Singleton1 {
        private static class SingletonHolder {
            private static final Singleton instance = new Singleton();

            public static Singleton getInstance() {
                return instance;
            }
        }

        private Singleton1() {
        }

        public static Singleton getInstance() {
            return SingletonHolder.getInstance();
        }
    }
}
