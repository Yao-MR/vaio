package com.vaio.io.runtime.thread;

public class RuntimeMain {


  /**
   * 获取可用的线程的数量
   *
   *
   */
  private static void checkThreads(){
    System.out.println(Runtime.getRuntime().availableProcessors());
  }

  /**
   * 指示虚拟机可以尽快进行一次gc
   */
  private static void systemGc(){
    System.gc();
    Runtime.getRuntime().gc();
  }


  public static void main(String[] args){
    RuntimeMain.systemGc();
  }
}
