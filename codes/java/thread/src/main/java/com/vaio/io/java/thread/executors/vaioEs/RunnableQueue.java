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
public interface RunnableQueue {

  //当有新任务时首先offer到对列中
  void offer(Runnable runnable);

  //工作线程通过take方法获取runnable
  Runnable take();

  //获取任务队列中任务的数量
  int size();
}
