package com.vaio.io.runtime.clazz;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 题目:
 * <p>
 * 思路:
 * <p>
 * 算法:
 * <p>
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-04-25
 */
public class Test {
    public void printVersion(){
      System.out.println("当前版本是1哦");
    }

  public static void main(String[] args) {
    //创建一个2s执行一次的定时任务
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        String swapPath = VaioClassLoader.class.getResource("").getPath() + "swap/";
        String className = "com.example.Test";

        //每次都实例化一个ClassLoader，这里传入swap路径，和需要特殊加载的类名
        VaioClassLoader myClassLoader = new VaioClassLoader(swapPath, new HashSet(
            Collections.singleton(className)));
        try {
          //使用自定义的ClassLoader加载类，并调用printVersion方法。
          Object o = myClassLoader.loadClass(className).newInstance();
          o.getClass().getMethod("printVersion").invoke(o);
        } catch (InstantiationException |
            IllegalAccessException |
            ClassNotFoundException |
            NoSuchMethodException |
            InvocationTargetException ignored) {
        }
      }
    }, 0,2000);
  }

}
