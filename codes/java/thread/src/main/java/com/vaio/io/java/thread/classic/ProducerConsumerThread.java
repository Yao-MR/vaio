package com.vaio.io.java.thread.classic;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目:
 *      生产者消费者模式
 *
 * 思路:
 *      实际上都是对仓库的消费和生产方法进行加锁，对外在的线程的倒是没有限制，对共享对象进行控制
 *
 *      常见的实现方式主要有以下几种。
 *           ①使用wait()和notify()
 *           ②使用Lock和Condition
 *           ③使用信号量Semaphore
 *           ④使用JDK自带的阻塞队列
 *           ⑤使用管道流
 * 算法:
 *
 * 参考:
 *
 * https://www.jianshu.com/p/7cbb6b0bbabc
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-09-27
 */
public class ProducerConsumerThread {

    public static class LockMode {
        private Lock lock = new ReentrantLock();
        private Condition notFull = lock.newCondition();
        private Condition notEmpty = lock.newCondition();

        private int capacity;
        private int value;

        public LockMode(int capacity) {
            this.capacity = capacity;
            this.value = 0;
        }

        public void produce(int value) {
            lock.lock();
            try {
                if (value + this.value > capacity) {
                    notFull.await();
                } else {
                    this.value += value;
                    System.out.println("生产" + value);
                    notEmpty.signalAll();
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }

        public void consume(int value) {
            lock.lock();
            try {
                if (this.value - value > 0) {
                    this.value -= value;
                    System.out.println("消费" + value);
                    notFull.signalAll();
                } else {
                    notEmpty.await();
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }

        public void test() {
            LockMode lockMode = new LockMode(100);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {

                        lockMode.produce(1);
                        try {
                            Thread.sleep(1);
                        } catch (Exception e) {
                            System.out.println(e);
                        } finally {

                        }

                    }
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {


                        lockMode.consume(2);
                        try {
                            Thread.sleep(2);
                        } catch (Exception e) {
                            System.out.println(e);
                        } finally {

                        }
                    }
                }
            }).start();
        }
    }

    public static class SynchronizedMode {
        private int capacity;
        private int value;

        public SynchronizedMode(int capacity) {
            this.capacity = capacity;
            this.value = 0;
        }

        public synchronized void produce(int value) throws InterruptedException {
            if (this.value + value > capacity) {
                this.wait();
            } else {
                this.value += value;
                System.out.println("生产" + value);
                this.notifyAll();
            }
        }

        public synchronized void consume(int value) throws InterruptedException {
            if (this.value - value < 0) {
                this.wait();
            } else {
                this.value -= value;
                System.out.println("消费" + value);
                this.notifyAll();
            }
        }

        public void test() {
            SynchronizedMode synchronizedMode = new SynchronizedMode(10);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {

                        try {
                            synchronizedMode.produce(1);
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {


                        try {
                            synchronizedMode.consume(1);
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    public static class SemaphoreMode {

    }

    public static void main(String[] args) {
        new SynchronizedMode(10).test();
        new LockMode(10).test();
    }
}
