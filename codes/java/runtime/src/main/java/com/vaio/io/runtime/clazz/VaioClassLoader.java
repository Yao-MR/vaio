package com.vaio.io.runtime.clazz;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

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
 * @date 2020-04-25
 */
public class VaioClassLoader extends ClassLoader{
  //用于读取.Class文件的路径
  private String swapPath;
  //用于标记这些name的类是先由自身加载的
  private Set<String> useMyClassLoaderLoad;

  public VaioClassLoader(String swapPath, Set<String> useMyClassLoaderLoad) {
    this.swapPath = swapPath;
    this.useMyClassLoaderLoad = useMyClassLoaderLoad;
  }

  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    Class<?> c = findLoadedClass(name);
    if (c == null && useMyClassLoaderLoad.contains(name)){
      //特殊的类让我自己加载
      c = findClass(name);
      if (c != null){
        return c;
      }
    }
    return super.loadClass(name);
  }

  @Override
  protected Class<?> findClass(String name) {
    //根据文件系统路径加载class文件，并返回byte数组
    byte[] classBytes = getClassByte(name);
    //调用ClassLoader提供的方法，将二进制数组转换成Class类的实例
    return defineClass(name, classBytes, 0, classBytes.length);
  }

  private byte[] getClassByte(String name) {
    String className = name.substring(name.lastIndexOf('.') + 1, name.length()) + ".class";
    try {
      FileInputStream fileInputStream = new FileInputStream(swapPath + className);
      byte[] buffer = new byte[1024];
      int length = 0;
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      while ((length = fileInputStream.read(buffer)) > 0){
        byteArrayOutputStream.write(buffer, 0, length);
      }
      return byteArrayOutputStream.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new byte[]{};
  }
}
