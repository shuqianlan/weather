package com.ilifesmart.aop;

import android.util.Log;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

//@Aspect
public class AspectTest {
    // AOP讲解: https://www.cnblogs.com/weizhxa/p/8567942.html
    // 对象 CheckOnClickActivity
    public static final String TAG = "AspectTest";

//    @Before("execution(* com.ilifesmart.HomeActivity.on***(..))")
    public void onSetText() {
        Log.d(TAG, "onSetText: -------------- ");
    }

}
