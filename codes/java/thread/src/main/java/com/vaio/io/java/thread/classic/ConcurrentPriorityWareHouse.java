package com.vaio.io.java.thread.classic;

import com.vaio.io.algorithm.ds.Ds3Tree.VaioHeap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目: 存储为具有优先级特性的数据仓库，基于生产者消费者模式来进行构建
 * <p>
 * 思路:
 * <p>
 * 算法:
 * <p>
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-05-23
 */
public class ConcurrentPriorityWareHouse {

  private Lock lock = new ReentrantLock();
  private Condition isNotFull = lock.newCondition();
  private Condition isNotEmpty = lock.newCondition();

  public VaioHeap wareHouse;

  public ConcurrentPriorityWareHouse(int capacity) {
    this.wareHouse = new VaioHeap(capacity);
  }

  public void consumer() {
    lock.lock();
    try{
      if (wareHouse.getSize() > 0){
        wareHouse.getHeapHead();
        isNotFull.signal();
      } else {
        isNotEmpty.await();
      }
    } catch (Exception e) {
    } finally {
      lock.unlock();
    }
  }

  public void produce(int value) {
    lock.lock();
    try{
      if (wareHouse.getCapacity() > wareHouse.getSize()){
        wareHouse.insertHeapTail(value);
        isNotEmpty.signal();
      } else {
        isNotFull.await();
      }
    } catch (Exception e) {
    } finally {
      lock.unlock();
    }
  }
}
