package com.github.hiteshsondhi88.libffmpeg;

class ArmArchHelper {
    static {
        System.loadLibrary("ARM_ARCH");
    }

    native String cpuArchFromJNI();

    boolean isARM_v7_CPU(String cpuInfoString) {
        return cpuInfoString.contains("v7");
    }

    boolean isNeonSupported(String cpuInfoString) {
        // check cpu arch for loading correct ffmpeg lib
        return cpuInfoString.contains("-neon");
    }

}