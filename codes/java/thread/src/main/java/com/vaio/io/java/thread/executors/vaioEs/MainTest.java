package com.vaio.io.java.thread.executors.vaioEs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 题目: 线程的实现方式总结
 * <p>
 * 思路:
 * <p>
 * 算法:
 * <p>
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-04-04
 */
public class MainTest {
  public static AtomicInteger threadNameCount = new AtomicInteger(1);

  public static void main(String[] args) throws InterruptedException{
    final ThreadPool threadPool = new BasicThreadPool(4,8,6,100);

    for(int i = 0; i < 20; i++) {
      threadPool.execute(() -> {
        try {
          TimeUnit.SECONDS.sleep(2);
          System.out.println(
              "借用线程池 :" +
                  Thread.currentThread().getName() +
               "我是线程 : " + threadNameCount.getAndIncrement() + "运行结束");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

    while(true){
      System.out.println("===============线程监控开始=====================");
      System.out.println("getActiveCount:"+threadPool.getActiveCount());
      System.out.println("getQueueSize:"+threadPool.getQueueSize());
      System.out.println("getCoreSize:"+threadPool.getCoreSize());
      System.out.println("getMaxSize:"+threadPool.gerMaxSize());
      System.out.println("===============线程监控结束=======================");
      TimeUnit.SECONDS.sleep(1);
    }
  }
}