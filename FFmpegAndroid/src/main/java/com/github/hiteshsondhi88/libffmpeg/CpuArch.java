package com.github.hiteshsondhi88.libffmpeg;

import android.text.TextUtils;

enum CpuArch {
    x86("dc9b832217acea7c7d8453d0f0d64cb6072323b1"),
    ARMv7("23744779c593150a272d67325962c3b3eae63b6a"),
    ARMv7_NEON("8b9c8d5e758c4f5a2ff26419787a89f0dd8911c1"),
    NONE(null);

    private String sha1;

    CpuArch(String sha1) {
        this.sha1 = sha1;
    }

    String getSha1(){
        return sha1;
    }

    static CpuArch fromString(String sha1) {
        if (!TextUtils.isEmpty(sha1)) {
            for (CpuArch cpuArch : CpuArch.values()) {
                if (sha1.equalsIgnoreCase(cpuArch.sha1)) {
                    return cpuArch;
                }
            }
        }
        return NONE;
    }
}
