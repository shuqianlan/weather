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

//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.lang.reflect.Method;

@Aspect
public class AspectTest {
    // AOP讲解: https://www.cnblogs.com/weizhxa/p/8567942.html
    // 对象 CheckOnClickActivity
    public static final String TAG = "AspectTest";

    @Before("execution(* com.ilifesmart.HomeActivity.on***(..))")
    public void onHomeActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();

        Log.d(TAG, "onHomeActivityMethodBefore: -------------- " + key);
    }

    @After("execution(* com.ilifesmart.HomeActivity.on***(..))")
    public void onHomeActivityMethodAfter(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();

        Log.d(TAG, "onHomeActivityMethodAfter: -------------- " + key);
    }

    // Around 和 After不能用在同一个方法上.
//    @Around("execution(* com.ilifesmart.HomeActivity.on***(..))")
//    public void onHomeActivityMethodAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        String key = proceedingJoinPoint.getSignature().toString();
//        Log.d(TAG, "onHomeActivityMethodAround:0 " + key);
//        proceedingJoinPoint.proceed(); // 执行原始的方法.
//        Log.d(TAG, "onHomeActivityMethodAround:1 " + key);
//    }

}
