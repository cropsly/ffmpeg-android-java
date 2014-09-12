#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <cpu-features.h>

jstring
Java_com_github_hiteshsondhi88_libffmpeg_ArmArchHelper_cpuArchFromJNI(JNIEnv* env, jobject obj)
{
    // Maximum we need to store here is ARM v7-neon
    // Which is 11 char long, so initializing a character array of length 11
	char arch_info[11] = "";

    // checking if CPU is of ARM family or not
	if (android_getCpuFamily() == ANDROID_CPU_FAMILY_ARM) {
		strcpy(arch_info, "ARM");

		// checking if CPU is ARM v7 or not
		uint64_t cpuFeatures = android_getCpuFeatures();
		if ((cpuFeatures & ANDROID_CPU_ARM_FEATURE_ARMv7) != 0) {
		    strcat(arch_info, " v7");

			// checking if CPU is ARM v7 Neon
			if((cpuFeatures & ANDROID_CPU_ARM_FEATURE_NEON) != 0) {
			    strcat(arch_info, "-neon");
			}
		}
	}
	return (*env)->NewStringUTF(env, arch_info);
}
