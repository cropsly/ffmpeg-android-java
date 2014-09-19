package com.github.hiteshsondhi88.libffmpeg;

import android.text.TextUtils;

enum CpuArch {
    x86("a0f523f4fce7e4a4b0ad1e86dfa5c3d54ac93b91"),
    ARMv7("6b9b3d574740d69fd50fd6d75e04c71ae3a95c72"),
    ARMv7_NEON("ac0418277061dc8b7547fee51e3e84457069806e"),
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
