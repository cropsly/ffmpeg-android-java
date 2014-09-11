LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := ARM_ARCH

LOCAL_SRC_FILES := armArch.c

LOCAL_CFLAGS := -DHAVE_NEON=1
LOCAL_STATIC_LIBRARIES := cpufeatures

LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)
$(call import-module,cpufeatures)
