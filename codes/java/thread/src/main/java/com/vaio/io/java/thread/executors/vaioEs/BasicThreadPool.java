package com.vaio.io.java.thread.executors.vaioEs;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

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
public class BasicThreadPool extends Thread implements ThreadPool {

  //初始化，最大，核心，活跃线程数量
  private final int initSize;
  private final int maxSize;
  private final int coreSize;
  private int activeCount;

  //线程新建所需要的工厂，以及相关运行队列
  private final ThreadFactory threadFactory;
  private final RunnableQueue runnableQueue;

  //线程池的状态
  private volatile boolean isShutdown = false;
  private final long keepAliveTime;
  private final TimeUnit timeUnit;
  private final Queue<ThreadTask> threadTaskQueue = new ArrayDeque<>();

  public BasicThreadPool(int initSize, int maxSize, int coreSize, int queueSize){
    this(initSize, maxSize, coreSize, new DefaultThreadFactory(),
        queueSize, new DenyPolicy.DiscardDenyPolicy(), 1000, TimeUnit.SECONDS);
  }

  public BasicThreadPool(int initSize, int maxSize, int coreSize,
      ThreadFactory threadFactory, int queueSize, DenyPolicy denyPolicy,
      long keepAliveTime, TimeUnit timeUnit){
    this.initSize = initSize;
    this.maxSize =maxSize;
    this.coreSize = coreSize;
    this.threadFactory = threadFactory;
    this.runnableQueue = new LinkedRunnableQueue(queueSize, denyPolicy,this);
    this.keepAliveTime = keepAliveTime;
    this.timeUnit = timeUnit;
    this.init();
  }

  public void init(){
    //初始化的过程中先起一个监控程序
    start();
    //根据初始化的线程的大小进行线程的初始化
    for (int i = 0; i < initSize; i++){
      newThread();
    }
  }

  public void newThread(){
    //
    InternalTask internalTask = new InternalTask(runnableQueue);
    Thread thread = this.threadFactory.createThread(internalTask);
    ThreadTask threadTask = new ThreadTask(thread, internalTask);
    threadTaskQueue.offer(threadTask);
    this.activeCount++;
    thread.start();
  }

  public void removeThread(){
    //从线程池中移除某个线程
    ThreadTask threadTask = threadTaskQueue.remove();
    threadTask.internalTask.stop();
    this.activeCount --;
  }


  public void run(){
    //run方法主要用于维护线程数量，扩容，回收等工作
    while (! isShutdown && isInterrupted()){
      try {
        timeUnit.sleep(keepAliveTime);
      } catch (InterruptedException e){
        isShutdown = true;
        break;

      }
      synchronized (this){
        if (isShutdown)
          break;
        //当前队列中是否有任务尚未处理，并且 activeCount < coreSize 则进行继续扩容
        if (runnableQueue.size() > 0 && activeCount < coreSize){
          for (int i = 1; i < coreSize; i++){
            newThread();
          }
          //continue的目的在于不想让线程的扩容直接到达maxSize
          continue;
        }
        if (runnableQueue.size() > 0 && activeCount < maxSize){
          for (int i = coreSize; i < maxSize; i++){
            newThread();
          }
        }
        //如果对列中没有任务，则需要进行回收，回收到coreSize即可
        if (runnableQueue.size() == 0 &&  activeCount < coreSize){
          for (int i = coreSize; i < activeCount; i++){
            removeThread();
          }
        }
      }
    }
  }


  private void isShudownSystemOut(){
    if(this.isShutdown){
      throw new IllegalStateException("The thread pool is destroyed");
    }
  }



  @Override
  public void execute(Runnable runnable) {
    isShudownSystemOut();
    this.runnableQueue.offer(runnable);
  }

  @Override
  public void shutdown() {
    synchronized (this){
      if (isShutdown) return;
      isShutdown = true;
      for (ThreadTask threadTask : threadTaskQueue) {
        threadTask.internalTask.stop();
        threadTask.thread.interrupt();
      }
      this.interrupt();
    }

  }

  @Override
  public int getInitSize() {
    isShudownSystemOut();
    return this.initSize;
  }

  @Override
  public int gerMaxSize() {
    isShudownSystemOut();
    return this.maxSize;
  }

  @Override
  public int getCoreSize() {
    isShudownSystemOut();
    return this.coreSize;
  }

  @Override
  public int getQueueSize() {
    isShudownSystemOut();
    return runnableQueue.size();
  }

  @Override
  public int getActiveCount() {
    synchronized (this){
      return this.activeCount;
    }
  }

  @Override
  public boolean isShutdown() {
    return this.isShutdown;
  }
}
