package com.vaio.io.java.thread.executors.vaioEs;

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
public class DefaultThreadFactory implements ThreadFactory{

  private static final AtomicInteger GROUP_COUNTER = new AtomicInteger(1);
  private static final ThreadGroup threadGroup = new ThreadGroup("MyThreadPool--" + GROUP_COUNTER.getAndIncrement());
  private static final AtomicInteger COUNTER = new AtomicInteger(0);
  @Override
  public Thread createThread(Runnable runnable) {
    return new Thread(threadGroup, runnable, "我是线程池子: {" + COUNTER.getAndIncrement() + "}");
  }
}
