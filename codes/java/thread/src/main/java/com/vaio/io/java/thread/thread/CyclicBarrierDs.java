package com.vaio.io.java.thread.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020- 10- 09
 */
public class CyclicBarrierDs {

    public static void main() {
        final long start = System.nanoTime();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                long end = System.nanoTime();
                System.out.println("导入" + 3 + "条数据，至此共用时 " + (end - start) + "豪秒");
            }
        });
        for (int i = 0; i <= 9; i++) {
            final int count = i + 1;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                        System.out.println(count + "完成导入操作");
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        System.out.println("主线程结束");
    }

}
