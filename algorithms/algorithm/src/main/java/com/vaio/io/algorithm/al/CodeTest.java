package com.vaio.io.algorithm.al;


import java.util.concurrent.*;

/**
 * 背景:
 * 思路:
 * 算法:
 * 参考:
 *
 * @author yao.wang
 * @date 2021-01-20
 */
public class CodeTest {
    int[] arrays = new int[]{1,2,3,4,5,6,7,8,9,10};
    int part = 5;

    public int subArraySize(int[] arrays){
        return arrays.length;
    }

    public int getStart(int[] arrays, int index){
        int length =  arrays.length / part;
        return (arrays.length /length) * index;
    }
    public int getEnd(int[] arrays, int index){
        int length =  arrays.length / part;
        return (arrays.length /length) * index + length -1;
    }

    public static class subThread implements Callable<Integer> {
        int[] array;
        int start;
        int end;
        int sum = 0;
        public subThread(int[] arry, int start, int end){
            this.array = arry;
            this.start = start;
            this.end = end;
        }

        @Override
        public Integer call() throws Exception {
            while (start < end){
                sum += array[start];
                start ++;
            }
            return sum;
        }
    }

    public static void main(String[] args){
        Executor executors = Executors.newFixedThreadPool(5);
        FutureTask futureTask 
    }
}