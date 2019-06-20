//
// Created by 吴正华 on 2019-06-20.
//

#include "Hello.h"
#include <stdio.h>
#include <jni.h>
#include <android/log.h>
#include <stdlib.h>

#define  LOG_TAG "System.out"

/*
* __FILE__ : 文件名
* __LINE__ ： 行号
* __DATE__ ： 日期
* __TIME__ ： 时间
* #:转字符串
* ##： 链接前后使其成一个字符串 A##8 == "A8"
* __VA_ARGS__: ...
* ##__VA_ARGS__: 当可变参数个数为0，##的作用就是把前面的","去掉
*/

#define LOGD(format, ...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __FILE__":%d|" #format, __LINE__, ##__VA_ARGS__)
#define LOGI(format, ...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __FILE__":%d|" #format, __LINE__, ##__VA_ARGS__)

/*
* env: 具体看当前是C还是C++文件
* jclass: 调用此方法的Java对象.
*
* */
extern "C"
JNIEXPORT jstring JNICALL
Java_com_jni_JniDemoActivity_hello__(JNIEnv * env, jclass type) {
  LOGD("%s", "wtf");
  return env->NewStringUTF("Hello from C.");
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_jni_JniDemoActivity_add(JNIEnv *env, jclass type, jint a, jint b) {

    LOGD("args[0] %d, args[1] %d", a, b);
    return (a+b);
}