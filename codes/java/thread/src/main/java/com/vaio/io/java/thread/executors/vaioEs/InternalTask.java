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
public class InternalTask implements Runnable{

  private final RunnableQueue runnableQueue;
  private volatile boolean running = true;
  public InternalTask(RunnableQueue runnableQueue){
    this.runnableQueue = runnableQueue;
  }

  @Override
  public void run() {
    while (running && !Thread.currentThread().isInterrupted()){
      try {
        Runnable task = runnableQueue.take();
        task.run();
      } catch (Exception e){
        running = false;
        break;
      }
    }
  }

  public void stop() {
    this.running = false;
  }
}
