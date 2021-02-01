package com.vaio.io.java.thread.classic;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目: 线程交替打印一直到10
 *
 * 思路: 一把锁，一个等待队列，相互等待，相互唤醒
 *
 * 算法:
 *
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-06-04
 */
public class Thread2Print {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void print() {
        new PrintThread(lock, condition, 10, "threadName1").start();
        new PrintThread(lock, condition, 10, "threadName2").start();
    }

    public static class PrintThread extends Thread {
        private Lock lock;
        private Condition condition;
        private int printCount;
        private static int currentCount = 0;
        private String name;

        public PrintThread(Lock lock, Condition condition, int printCount, String name) {
            this.lock = lock;
            this.condition = condition;
            this.printCount = printCount;
            this.name = name;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                while (currentCount < printCount) {
                    System.out.println("线程" + name + "打印" + currentCount++);
                    condition.signalAll();
                    condition.await();
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Thread2Print().print();
    }
}