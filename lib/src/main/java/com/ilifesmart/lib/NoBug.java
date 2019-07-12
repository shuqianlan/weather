package com.ilifesmart.lib;

import com.ilifesmart.annotation.JieCha;

public class NoBug {

    // 注解参考URL: https://blog.csdn.net/qq1404510094/article/details/80577555
    @JieCha
    public void printNumber() {
        System.out.println("1234567890");
    }

    @JieCha
    public void printNumberAdd() {
        System.out.println("1+1=" + (1+1));
    }

    @JieCha
    public void printNumberMinus() {
        System.out.println("1-1=" + (1-1));
    }

    @JieCha
    public void printNumberX() {
        System.out.println("2x9="+(2*9));
    }

    @JieCha
    public void printNumberSub() {
        System.out.println("16/0=" + (16/0));
    }

    @JieCha
    public void printDefault() {
        System.out.println("Default");
    }
}
