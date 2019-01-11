package com.ilifesmart.utils;

import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

//@Aspect
public class AOPUtils {

	public static final String TAG = "AOPUtils";

//	@Pointcut ("call(* android.widget.ImageView.setImageDrawable(..))")
	private void onSetImageDrawable() {}

//	@Around("onSetImageDrawable()")
//	private Object SetImageDrawable(ProceedingJoinPoint jPoint) throws Throwable {
//		Signature signature = jPoint.getSignature();
//		MethodSignature methodSignature = (MethodSignature)signature;
//		Method targetMethod = methodSignature.getMethod();
//
//		Log.d(TAG, "classname: "  + targetMethod.getDeclaringClass().getName());
//		Log.d(TAG, "superclass:" + targetMethod.getDeclaringClass().getSuperclass().getName());
//		Log.d(TAG, "isinterface:" + targetMethod.getDeclaringClass().isInterface());
//		Log.d(TAG, "target:" + jPoint.getTarget().getClass().getName());
//		Log.d(TAG, "proxy:" + jPoint.getThis().getClass().getName());
//		Log.d(TAG, "method:" + targetMethod.getName());
//
//		for (Object obj : jPoint.getArgs()) {
//			if (obj instanceof BitmapDrawable) {
//				Drawable drawable = (BitmapDrawable)obj;
//				Rect rectF = drawable.getBounds();
//				Log.d(TAG, "SetImageDrawable: rect " + rectF);
//				Log.d(TAG, "SetImageDrawable: width " + drawable.getIntrinsicWidth());
//				Log.d(TAG, "SetImageDrawable: height " + drawable.getIntrinsicHeight());
//				Log.d(TAG, "SetImageDrawable: ------------ BBBB ");
//			} else {
//				Log.d(TAG, "SetImageDrawable: args_ " + obj.getClass());
//			}
//		}
//
//		Object result = jPoint.proceed();
//
//		return result;
//	}
}
