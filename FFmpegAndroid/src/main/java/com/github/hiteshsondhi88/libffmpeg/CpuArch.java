package com.github.hiteshsondhi88.libffmpeg;

import android.text.TextUtils;

enum CpuArch {
    x86("64978fa085af04a2d08907a3a853a6ed8f2f25b6"),
    ARMv7("b924a0ee8650f18da292a7e2ad398d2791d2966c"),
    ARMv7_NEON("7eb42611f316de0349edbd244440484f3b0f29bd"),
    NONE(null);

    private String sha1;

    CpuArch(String sha1) {
        this.sha1 = sha1;
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
