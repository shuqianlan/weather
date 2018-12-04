package com.ilifesmart.aop;

import android.util.Log;
import android.widget.TextView;

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

    // call
    // getTarget：获取真实对象.
    @Around("call(* android.widget.TextView.setText(CharSequence))")
    public void setTextViewTitle(ProceedingJoinPoint jPoint, CharSequence text) throws Throwable {
        final Object object = jPoint.getTarget();

        if (object instanceof TextView) {
            jPoint.proceed();
            Log.d(TAG, "setTextViewTitle: ---- " + ((TextView)object).getText().toString());
        }
    }
}
