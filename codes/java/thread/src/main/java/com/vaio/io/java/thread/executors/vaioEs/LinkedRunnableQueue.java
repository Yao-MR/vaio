package com.vaio.io.java.thread.executors.vaioEs;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
public class LinkedRunnableQueue implements RunnableQueue{

  //队列的容量的大小以及现在的大小
  private int capacity;
  private  int size;

  //队列的拒绝策略, 使用的队列类型, 对应的线程池
  private final DenyPolicy denyPolicy;
  private LinkedList<Runnable>  runnableLinkedList = new LinkedList<Runnable>();
  private final ThreadPool threadPool;

  private Lock lock = new ReentrantLock();
  Condition isNotEmpty = lock.newCondition();
  Condition isNotFull = lock.newCondition();

  public LinkedRunnableQueue(int limit, DenyPolicy denyPolicy, ThreadPool threadPool){
    this.capacity = limit;
    this.denyPolicy = denyPolicy;
    this.threadPool = threadPool;
  }

  /**
  @Override
  public void offer(Runnable runnable) {
    lock.lock();
    if (runnableLinkedList.size() >= capacity){
      //queue的大小已经无法再进行填充，进行拒绝策略
      denyPolicy.reject(runnable,threadPool);
    } else {
      runnableLinkedList.addLast(runnable);
      isNotEmpty.signal();
    }
    lock.unlock();
  }
   **/

  @Override
  public void offer(Runnable runnable) {
    synchronized (runnableLinkedList) {
      if (runnableLinkedList.size() >= capacity) {
        //无法容纳新的任务时执行拒绝策略
        denyPolicy.reject(runnable, threadPool);
      } else {
        //将任务加入到队尾，并且唤醒阻塞中的线程
        runnableLinkedList.addLast(runnable);
        runnableLinkedList.notifyAll();

      }
    }
  }

  @Override
  public Runnable take(){
    synchronized (runnableLinkedList){
      while (runnableLinkedList.isEmpty()){
        try {
          //如果任务队列中没有可执行的任务，则当前线程将会挂起，进入runnableList关联的
          //monitor waitset中等待唤醒
          runnableLinkedList.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      //从任务队列头部移除一个任务
      return runnableLinkedList.removeFirst();
    }
  }


  /**

  @Override
  public Runnable take() {
    lock.lock();
    while (runnableLinkedList.isEmpty()){
      try {
        isNotEmpty.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }
    return runnableLinkedList.removeFirst();
  }
   **/

  @Override
  public int size() {
    return runnableLinkedList.size();
  }
}
