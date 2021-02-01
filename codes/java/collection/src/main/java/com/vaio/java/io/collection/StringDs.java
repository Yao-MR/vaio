package com.vaio.java.io.collection;

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
public class StringDs {

  private String string;
  private StringBuffer stringBuffer;
  private StringBuilder stringBuilder;

  public StringDs(){
    this.string = "string-test-case";
    this.stringBuffer = new StringBuffer("string-buffer-test-case");
    this.stringBuilder = new StringBuilder("string-builder-test-case");
  }

  public void StringDsTest(){
    char chars = 'd';
    string.charAt(1);
    stringBuilder.setCharAt(1,'d');
    stringBuilder.charAt(1);
    stringBuffer.charAt(1);
  }

  public static void main(String[] args){

  }
}
