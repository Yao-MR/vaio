package com.vaio.io.java.thread.classic;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目:
 *      三线程循环打印ABC共10次
 *
 * 思路:
 *      线程共有的变量来维持一个全局的计数器
 *      每个线程是会进行根据对3的来决定是否进行输出，否则全局挂起，并全局唤醒，唤醒后根据全局的进行唤醒
 *
 * 算法:
 *      三个线程共用一把锁，并在一个条件上进行等待。在被唤醒后根据条件判定是否自己进行输出
 *
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-06-04
 */
public class Threads3Print {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void print() {
        new PrintThread(lock, condition, 30, 0, "A").start();
        new PrintThread(lock, condition, 30, 1, "B").start();
        new PrintThread(lock, condition, 30, 2, "C").start();
    }

    public static class PrintThread extends Thread {
        private static int currentCount = 0;
        private Lock lock;
        private Condition condition;
        private int flag;
        private String value;
        private int printCount;

        public PrintThread(Lock lock, Condition condition, int printCount, int flag, String value) {
            this.lock = lock;
            this.condition = condition;
            this.flag = flag;
            this.value = value;
            this.printCount = printCount;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                for (; currentCount < printCount; currentCount++) {
                    while (currentCount % 3 != flag) {
                        condition.await();
                    }
                    System.out.println(value);
                    condition.signalAll();
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                lock.unlock();
            }

        }
    }

    public static void main(String[] args) {
        new Threads3Print().print();
    }
}
