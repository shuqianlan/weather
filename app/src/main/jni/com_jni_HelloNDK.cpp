//
// Created by 吴正华 on 2019-06-25.
//

#include "com_jni_HelloNDK.h"
#include <android/log.h>
#include <malloc.h>
#include <string.h>

// __FILE__: 文件名
// __LINE__:行号
// #:连接
// __VA_ARGS__: 代替(...)

#define LOG_TAG "System.out"
#define LOGD(format, ...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __FILE__":%d|" #format, __LINE__, ##__VA_ARGS__)
#define LOGI(format, ...) __android_log_print( ANDROID_LOG_INFO, LOG_TAG, __FILE__":%d|" #format, __LINE__, ##__VA_ARGS__)

char* JString2CString(JNIEnv *env, jstring jstr) {
    jclass clazz = env->FindClass("java/lang/String");
    if (clazz == 0) {
        LOGD("%s", "invalid class path.");
    }

    jmethodID java_method = env->GetMethodID(clazz, "getBytes", "(Ljava/lang/String;)[B");
    if (java_method == 0) {
        LOGD("%s", "invalid java method.");
    }

    jstring strenCode = env->NewStringUTF("GB2312");
    jbyteArray arr = (jbyteArray) env->CallObjectMethod(jstr, java_method, strenCode);
    jbyte *bytes = env->GetByteArrayElements(arr, JNI_FALSE); //
    jint length = env->GetArrayLength(arr);
    char * result = NULL;

    if (length > 0) {
        result = (char *) malloc(length+1);
        memcpy(result, bytes, length);
        result[length] = 0;
    }

    env->ReleaseByteArrayElements(arr, bytes, JNI_FALSE);
    return result;
}

extern "C" JNIEXPORT void JNICALL Java_com_jni_HelloNDK_callFromC
  (JNIEnv *env, jclass obj) {

    LOGD("%s", "Hello From CPP.");
}

extern "C" JNIEXPORT jint JNICALL Java_com_jni_HelloNDK_callAddFromC
        (JNIEnv *env, jclass obj, jint a, jint b) {

    LOGD("a=%d, b=%d ", a, b);
    return (a+b);
}

extern "C" JNIEXPORT jstring JNICALL Java_com_jni_HelloNDK_callStringAppend
        (JNIEnv *env, jclass obj, jstring str) {

    char *dst = JString2CString(env, str);
    char *append = ":Append";
    return env->NewStringUTF(strcat(dst, append));
}

extern "C" JNIEXPORT void JNICALL Java_com_jni_HelloNDK_callDebugFromJava
        (JNIEnv *env, jobject obj) {

    jclass clazz = env->FindClass("com/jni/HelloNDK");
    if (clazz == 0) {
        LOGD("%s", "invalid class.");
    }

    jmethodID java_method = env->GetMethodID(clazz, "callFromJava", "()V");
    if (java_method == 0) {
        LOGD("%s", "method \"callFromJava\" not exist.");
    }

    env->CallVoidMethod(obj, java_method);
}

extern "C" JNIEXPORT jint JNICALL Java_com_jni_HelloNDK_callAddFromJava
        (JNIEnv *env, jobject obj) {

    jclass clazz = env->FindClass("com/jni/HelloNDK");
    if (clazz == 0) {
        LOGD("%s", "invalid class.");
    }

    jmethodID java_method = env->GetMethodID(clazz, "add", "(II)I");
    if (java_method == 0) {
        LOGD("%s", "method \"callFromJava\" not exist.");
    }

    jint result = env->CallIntMethod(obj, java_method, 3, 4);
    LOGD("Result (3+4)=%d", result);
    return result;
}

extern "C" JNIEXPORT void JNICALL Java_com_jni_HelloNDK_callPrintFromJava
        (JNIEnv *env, jobject obj) {

    jclass clazz = env->FindClass("com/jni/HelloNDK");
    if (clazz == 0) {
        LOGD("%s", "invalid class.");
    }

    jmethodID java_method = env->GetMethodID(clazz, "print", "(Ljava/lang/String;)V");
    if (java_method == 0) {
        LOGD("%s", "method \"callFromJava\" not exist.");
    }

    env->CallVoidMethod(obj, java_method, env->NewStringUTF("String From C++"));
}