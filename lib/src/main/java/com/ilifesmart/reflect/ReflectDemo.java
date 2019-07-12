package com.ilifesmart.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectDemo {

    public static final String TAG = "ReflectDemo";

/*
 * 通过Class对象可以获取某个类中的：构造方法、成员变量、成员方法；并访问成员；
 *
 * 1.获取构造方法：
 *      1).批量的方法：
 *          public Constructor[] getConstructors()：所有”公有的”构造方法
            public Constructor[] getDeclaredConstructors()：获取所有的构造方法(包括私有、受保护、默认、公有)

 *      2).获取单个的方法，并调用：
 *          public Constructor getConstructor(Class… parameterTypes):获取单个的”公有的”构造方法：
 *          public Constructor getDeclaredConstructor(Class… parameterTypes):获取”某个构造方法”可以是私有的，或受保护、默认、公有；
 *
 *          调用构造方法：
 *          Constructor–>newInstance(Object… initargs)
*/
    public static void test() {
        Student student = new Student();
        Class clazz = student.getClass(); // 返回一个对象的运行时类
        System.out.println("ClassName: " + clazz.getName());

        Class clazz2 = Student.class;
        System.out.println("clazz==clazz2 " + (clazz == clazz2));

        try {
            Class clazz3 = Class.forName("com.ilifesmart.reflect.Student");
            System.out.println("class3==class2 " + (clazz2 == clazz3));

            clazz3.getConstructors(); // 所有构造方法
            clazz3.getDeclaredConstructors();//
            clazz3.getConstructor(null); // 获取共有无参的构造函数

            // 构造方法
            Constructor con = clazz3.getConstructor(null);
            con.newInstance(); // 调用构造函数

            Constructor priv_cont = clazz3.getDeclaredConstructor(int.class);
            priv_cont.setAccessible(true); // 暴力访问
            Object instance = priv_cont.newInstance(12);

            // 属性
            Field[] fields = clazz3.getDeclaredFields();
            for(Field field:fields) {
                System.out.println("Field:" + field);
            }

            Field name = clazz3.getDeclaredField("name");
            name.set(instance, "wuzh"); // 参数:作用的对象， 实参

            Field mobile = clazz3.getDeclaredField("mobile");
            mobile.setAccessible(true);
            mobile.set(instance, "18158531684");
            System.out.println("instance: " + (Student)instance);

            // 方法
            Method getAge = clazz3.getDeclaredMethod("printAge4");
            getAge.setAccessible(true);
            getAge.invoke(instance);


        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
