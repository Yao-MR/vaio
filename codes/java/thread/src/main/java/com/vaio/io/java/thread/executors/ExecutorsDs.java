package com.vaio.io.java.thread.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 题目: 线程的实现方式总结
 *      
 * 思路:
 *
 * 算法:
 *
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-04-04
 */
public class ExecutorsDs {

  /**
   * 固定长度的线程池
   */
  public static ExecutorService fixedPool = Executors
          .newFixedThreadPool(2);

  /**
   * 可缓存的线程池
   */
  public static ExecutorService cachePool = Executors
          .newCachedThreadPool();

  /**
   * 单线程的线程池
   */
  public static ExecutorService singlePool = Executors
          .newSingleThreadExecutor();
  /**
   * 延迟或者定时的调度线程
   */
  public static ExecutorService scheduledPool = Executors.newScheduledThreadPool(2);

  /**
   *
   */
  public static ExecutorService workStealingPool = Executors.newWorkStealingPool(2);

  /**
   * 完全自定义线程池
   */
  public static ExecutorService customerExecutorService = new ThreadPoolExecutor(10,11,1100, TimeUnit.DAYS, new LinkedBlockingDeque<>());

  public static void main(String[] args){
  }
}
