package com.vaio.io.java.thread.executors.vaioEs;

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
public interface ThreadPool {
  //提交任务到线程池中
  void execute(Runnable runnable);

  //关闭线程池
  void shutdown();

  //获取线程池的初始化大小
  int getInitSize();

  //获取线程池的最大线程数
  int gerMaxSize();

  //获取线程池的核心线程数量
  int getCoreSize();

  //获取线程池中用于缓存的任务队列的大小
  int getQueueSize();

  //获取母爱你线程池中活跃的线程的数量的大小
  int getActiveCount();

  //获取线程池的状态
  boolean isShutdown();
}
