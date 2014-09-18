package com.github.hiteshsondhi88.libffmpeg;

import android.text.TextUtils;

enum CpuArch {
    x86("a93cdd533ec9b8865aed20e625ba01515c86c5c7"),
    ARMv7("3f4580bce9187f94b6cf8ef530074ef0975cb476"),
    ARMv7_NEON("fc544c42cf0ef993c0b55463807e5c68cd323c93"),
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
