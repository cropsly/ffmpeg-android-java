package com.github.hiteshsondhi88.libffmpeg;

import android.text.TextUtils;

enum CpuArch {
    x86("704756b89ba6c7ed5b95758c7064fee2f644be17"),
    ARMv7("a25f433a088e197aae10cadd34bcbf2660a45d07"),
    ARMv7_NEON("edb801d23dcf6993b3f498a878520c539ea40f30"),
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
