package com.vaio.io.java.thread.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.*;

/**
 * 题目: 线程的实现方式总结
 *      1> 线程的三种实现方式
 *          1. threadThread 继承Thread
 *          2. runnableThread 实现runnable
 *          3. callableThread 实现callable
 *      2> 线程的主要方法
 *          Thread.currentThread().getName()
 *          1.start():1.启动当前线程2.调用线程中的run方法
 *          2.run():通常需要重写Thread类中的此方法，将创建的线程要执行的操作声明在此方法中
 *          3.currentThread():静态方法，返回执行当前代码的线程
 *          4.getName():获取当前线程的名字
 *          5.setName():设置当前线程的名字
 *          6.yield():主动释放当前线程的执行权
 *          7.join():在线程中插入执行另一个线程，该线程被阻塞，直到插入执行的线程完全执行完毕以后，该线程才继续执行下去
 *          8.stop():过时方法。当执行此方法时，强制结束当前线程。
 *          9.sleep（long millitime）：线程休眠一段时间
 *          10.isAlive（）：判断当前线程是否存活
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-04-04
 */
public class ThreadDs {

    private Lock lock = new ReentrantLock(false);
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();
    private Condition condition = lock.newCondition();

    private AtomicInteger atomicInteger = new AtomicInteger(1);
    private AtomicReference<String> atomicReference = new AtomicReference<>("atomicString");

    private int intVar = 1;
    private Integer integerVar = 1;
    private volatile int volatileInt = 1;
    private volatile Integer volatileInteger = 1;

    private Semaphore semaphore = new Semaphore(21);
    private CountDownLatch countDownLatch = new CountDownLatch(3);
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(1);

    private ThreadLocal threadLocal = new ThreadLocal();

    public static class ThreadThread extends Thread {
        private Lock lock;
        private Lock writeLock;
        private Lock readLock;
        private Condition condition;

        public ThreadThread(Lock lock, Lock readLock, Lock writeLock, Condition condition){
            this.lock = lock;
            this.readLock = readLock;
            this.writeLock = writeLock;
            this.condition = condition;
        }

        @Override
        public void run() {
            readLock.lock();
            writeLock.lock();
            lock .lock();
            try {
                System.out.println();
            } finally {
                lock.unlock();
                readLock.unlock();
                writeLock.unlock();
            }
        }
    }

    public static class RunnableThread implements Runnable {
        @Override
        public void run() {
            System.out.println();
        }

        public synchronized void test1() throws InterruptedException {
            System.out.println();
            this.wait();
            this.notify();
        }

        public synchronized static void test2(){
            System.out.println();
        }

        public void test3(){
            synchronized (this) {
                System.out.println();
            }
            synchronized (this.getClass()){
                System.out.println();
            }
        }
    }

    class CallableThread implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            return 0;
        }
    }

    /***
    public void threadRunDemo() {
        //threadThread
        Thread threadThread = new ThreadThread();
        threadThread.start();
        threadThread.setDaemon(true);
        threadThread.join();
        //setDaemon(true)必须在start()方法前执行，否则会抛出IllegalThreadStateException异常

        //runnableThread
        Thread runnableThread = new Thread(new RunnableThread());
        runnableThread.start();

        //futureThread，由于 FutureTask 也是Runnable 接口的实现类
        FutureTask<Integer> futureTask = new FutureTask(new CallableThread());
        Thread futureThread = new Thread(futureTask);
        futureThread.start();
        try {
            //get返回值即为FutureTask构造器参数callable实现类重写的call的返回值
            //阻塞主进程的继续往下执行, 不调用的话不会阻塞
            Integer result = futureTask.get();
            System.out.println(Thread.currentThread().getName() + ":" + result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ThreadDs().threadRunDemo();
    }
     **/
}
