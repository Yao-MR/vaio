package com.vaio.java.io.collection;

import java.util.Arrays;
import java.util.Collections;

/**
 * 题目:
 * <p>
 * 思路:
 * <p>
 * 算法:
 * <p>
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-03-15
 */
public class Utils {
  private Arrays arrays;
  private Collections collections;
  private System system;


  public void ArraysMethod(){
    //数组的复制方法, 底层还是使用的是System.arrayCopy
    Arrays.copyOf(new int[1],10);
  }
  public void SystemMethod(){
    //底层的数组复制方法，在涉及扩容的方法中基于复制的算法都是使用的此方法
    System.arraycopy(new int[1], 0, new int[2],0,0);
  }


}
