//
// Created by 吴正华 on 2019-06-20.
//

#include <jni.h>

#ifndef WEATHER_HELLO_H
#define WEATHER_HELLO_H

#endif //WEATHER_HELLO_H

// JNICALL:声明其是JNI调用
extern "C" JNIEXPORT jstring JNICALL Java_com_jni_JniDemoActivity_hello__(JNIEnv *, jclass);

extern "C" JNIEXPORT jint JNICALL Java_com_jni_JniDemoActivity_add(JNIEnv *env, jclass type, jint a, jint b);