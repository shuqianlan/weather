package com.ilifesmart.reflect;

public class Student {

    public Student() {
        System.out.println("共有 无参构造函数");
    }

    public Student(String str) {
        System.out.println("共有 有参构造函数 s: " + str);
    }

    //有一个参数的构造方法  
    public Student(char name){
        System.out.println("姓名：" + name);
    }

    //有多个参数的构造方法  
    public Student(String name ,int age){
        System.out.println("姓名："+name+"年龄："+ age);//这的执行效率有问题，以后解决。
    }

    //受保护的构造方法  
    protected Student(boolean n){
        System.out.println("受保护的构造方法 n = " + n);
    }

    //私有构造方法  
    private Student(int age){
        System.out.println("私有的构造方法   年龄："+ age);
    }

    public String name;
    protected int age;
    char sex;
    private String mobile;

    public void printAge() {
        System.out.println("public age: " + age);
    }

    void printAge2() {
        System.out.println("Default age: " + age);
    }

    protected void printAge3() {
        System.out.println("protected age: " + age);
    }

    private void printAge4() {
        System.out.println("private age: " + age);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
