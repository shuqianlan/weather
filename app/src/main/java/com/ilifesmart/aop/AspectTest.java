package com.ilifesmart.aop;

import android.util.Log;

import com.ilifesmart.utils.DebouncedClickPredictor;
import com.ilifesmart.utils.Utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectTest {
    public static final String TAG = "AspectTest";

    @Before("execution(* com.ilifesmart.aop.CheckOnClickActivity.onCreate(..))")
    public void logForActivity() {
        Log.d(TAG, "logForActivity: xxxxx ");
    };

//    @Before("logForActivity")
//    public void log(JoinPoint joinPoint) {
//        Log.d(TAG, "log: " + joinPoint.toShortString());
//    }
//
    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void onClickListener(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Log.d(TAG, "onClickListener: OnClick ..");
        if (!Utils.isDoubleClick()) {
            proceedingJoinPoint.proceed();
        }
    }

    @Before("execution(* android.app.Activity.on**(..)))")
    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();
        Log.d(TAG, "onActivityMethodBefore: " + key);
    }
}
