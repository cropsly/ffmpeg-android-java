package com.github.hiteshsondhi88.libffmpeg;

import android.text.TextUtils;

enum CpuArch {
    x86("a1b90d18322f4a2efa1267f7a625f58706ed0a7d"),
    ARMv7("51cbc41b813661305ee58652a1c36d8bb0141467"),
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
