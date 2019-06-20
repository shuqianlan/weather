# 脚本文件，用于将C++编译为动态/静态库文件

# $(call my-dir): 获取Android.mk所在的目录
# $(CLEAR_VARS):  清除出LOCAL_PATH以外的所有LOCAL_XXX变量
# LOCAL_MODULE:   生成的库的名字
# $(BUILD_SHARED_LIBRARY): 将C++ 文件编译为动态库 *.so (体积小)
# $(BUILD_STATIC_LIBRARY): 将C++ 文件编译为静态库 *.a  (体积大)
# LOCAL_MODULE:生成的动态库名为"lib"+Name+".so"
# LOCAL_LDLIBS: 此处未链接liblog.so



LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := Hello
LOCAL_SRC_FILES := Hello.cpp
LOCAL_LDLIBS += -llog
include $(BUILD_SHARED_LIBRARY)