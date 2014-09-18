package com.github.hiteshsondhi88.libffmpeg;

import android.text.TextUtils;

enum CpuArch {
    x86("cbc1a2b2f1b832265e030f77ce1a25f5c7e0d11a"),
    ARMv7("93deccc6257ade540302fcd2a08a04cff7bc3f21"),
    ARMv7_NEON("ca1729aba3c5a4cb802d3fc0deca80a5c45ea0b8"),
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
