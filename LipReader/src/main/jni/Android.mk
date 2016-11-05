LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

OPENCV_CAMERA_MODULES:=on
OPENCV_INSTALL_MODULES:=on
OPENCV_LIB_TYPE:=SHARED
include C:\Users\kln\Downloads\Papy\OpenCV-android-sdk\sdk\native\jni\OpenCV.mk

LOCAL_SRC_FILES  := FeatureExtractor_jni.cpp
LOCAL_SRC_FILES  += FeatureExtractor.cpp
LOCAL_C_INCLUDES += $(LOCAL_PATH)
LOCAL_C_INCLUDES += C:\Users\kln\Downloads\Papy\OpenCV-android-sdk\sdk\native\jni\include
LOCAL_LDLIBS     += -llog -ldl

LOCAL_MODULE     := feature_extractor

include $(BUILD_SHARED_LIBRARY)