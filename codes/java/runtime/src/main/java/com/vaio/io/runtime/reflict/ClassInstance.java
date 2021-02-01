package com.vaio.io.runtime.reflict;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 题目: 反射
 * 思路:
 *      在运行时判断任意一个对象所属的类；
 *      在运行时构造任意一个类的对象；
 *      在运行时判断任意一个类所具有的成员变量和方法（通过反射甚至可以调用private方法）；
 *      在运行时调用任意一个对象的方法
 * 算法:
 * <p>
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-04-25
 */
public class ClassInstance {
  /**
   * 接口信息
   */
  public interface IVehicle {
    public void startMe();//启动
    public int forward(int dins);//前进
    public int getEnergy();//计算还有多少油
  }

  /**
   * 实现信息
   */
  public static class BMW implements IVehicle {
    public static final String company = "宝马公司";
    public int energy;// 能量
    public BMW() {
      this.energy = 10;
    }
    public BMW(int energy) {
      this.energy = energy;
    }

    public void startMe() {
      System.out.println("BMW启动...");
    }

    public int forward(int dins) {
      energy -= dins / 100;
      System.out.println("BMW前进" + dins);
      return energy;
    }

    public int getEnergy() {
      return this.energy;
    }


   /** ---------------------------------------------------------------------------------------- **/

    public static void main(String[] args)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
      //直接生成对象
      BMW bmw1 = new BMW();

      //获取类对象的三种方式，使用一个继续
      Class bmwClass1 = bmw1.getClass();
      Class bmwClass2=Class.forName("com.vaio.io.runtime.reflict.BMW");//1、载入类对象（此时该类还处于源文件阶段）
      Class bmwClass3 = BMW.class;

      //调用无参构造器生成对象
      BMW bmw2 = (BMW) bmwClass1.newInstance();
      //调用有参构造器生成对象
      Constructor cons= bmwClass1.getConstructor(int.class);
      BMW bmw3=(BMW)cons.newInstance(100);

      // 判断对象所属类型
      if (bmw1 instanceof BMW) {
        System.out.println("b是BMW类型！");
      }
      if (bmw1 instanceof IVehicle) {
        System.out.println("b是IVehicle类型！");
      }

      /* -------------------------------拿到该对象的所有信息--------------------------------------- */
      // 方法返回一个特定的方法，其中第一个参数为方法名称，后面的参数为方法的参数对应Class的对象。
      Method methodFordword = bmwClass1.getMethod("forward", int.class);
      //方法返回类或接口声明的所有方法，包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。
      Method[] declaredMethods = bmwClass1.getDeclaredMethods();
      //方法返回某个类的所有公用（public）方法，包括其继承类的公用方法。
      Method[] methods = bmwClass1.getMethods();


      // 得到类定义的指定的构造对象
      Constructor conInt= bmwClass1.getConstructor(int.class);
      // 得到类定义的构造器对象数组
      Constructor[] conss = bmwClass1.getConstructors();



      // 得到类定义的属性对象数组
      Field[] fields = bmwClass1.getFields();
      //所有已声明的成员变量，但不能得到其父类的成员变量
      Field fieldEnergy = bmwClass1.getDeclaredField("energy");


      // 得到类的直接父类对象
      Class superClass = bmwClass1.getSuperclass();
      // 得到类所有实现的接口对象数组
      Class[] interfaces = bmwClass1.getInterfaces();

    }
  }
}
