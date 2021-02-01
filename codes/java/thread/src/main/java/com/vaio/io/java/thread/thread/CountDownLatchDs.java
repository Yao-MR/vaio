package com.vaio.io.java.thread.thread;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020- 10- 09
 */
public class CountDownLatchDs {
    public static void main() throws InterruptedException {

        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(3);
        for (int i = 0; i < 3; i++){
            new Thread(){
                @Override
                public void run() {
                    try {
                        startGate.await();
                        System.out.println("");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        endGate.countDown();
                    }
                }
            }.start();
        }
        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
    }
}
