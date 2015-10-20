LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := hello-jni
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/Users/derek/Workspace/Android/Litao/Makeblock-App-For-Android-master-imported/app/src/main/jni/Android.mk \
	/Users/derek/Workspace/Android/Litao/Makeblock-App-For-Android-master-imported/app/src/main/jni/Application.mk \
	/Users/derek/Workspace/Android/Litao/Makeblock-App-For-Android-master-imported/app/src/main/jni/hello-jni.c \

LOCAL_C_INCLUDES += /Users/derek/Workspace/Android/Litao/Makeblock-App-For-Android-master-imported/app/src/main/jni
LOCAL_C_INCLUDES += /Users/derek/Workspace/Android/Litao/Makeblock-App-For-Android-master-imported/app/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
