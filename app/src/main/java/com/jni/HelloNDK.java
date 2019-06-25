package com.jni;

import android.util.Log;

public class HelloNDK {

	/*
	 * jclass: 类
	 * jobject: 类对象
	 *
	 * */

	public static final String TAG = "HelloNDK";
	public static native void callFromC();
	public static native int  callAddFromC(int a, int b);
	public static native String callStringAppend(String s);

	public void callFromJava() {
		Log.d(TAG, "callFromJava: Hello~");
	}
	public int add(int a, int b) {
		return a+b;
	}
	public void print(String content) {
		Log.d(TAG, "print: content " + content);
	}

	public native void callDebugFromJava();
	public native int  callAddFromJava();
	public native void callPrintFromJava();

	/*
	* NDK开发流程
	*
	* 0. 声明native函数
	* 1. 创建jni目录(New->Folder->jniFolder)
	* 2. javah生成头文件
	* 3. C/C++代码处理
  * 4. gradle指明Android.mk位置及过滤的架构类型
	* 5. C调用Java函数中的参数，(javap -s XXX.class查看参数声明)
	* 6. system.loadLibrary("XXX"); //根据类对象或类合理地调用即可.
	*
	* */
}
