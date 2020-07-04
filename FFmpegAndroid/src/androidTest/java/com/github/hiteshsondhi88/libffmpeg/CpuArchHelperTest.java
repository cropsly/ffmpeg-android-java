package com.github.hiteshsondhi88.libffmpeg;

import android.os.Build;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

public class CpuArchHelperTest extends TestCase {
    
    public void testGetCpuArch() throws Exception {
        CpuArch cpuArch = CpuArchHelper.getCpuArch();
        assertNotNull(cpuArch);
        if (Build.CPU_ABI.equals(CpuArchHelper.getx86CpuAbi()) || Build.CPU_ABI.equals(CpuArchHelper.getx86_64CpuAbi())) {
            assertEquals(cpuArch, CpuArch.x86);
        } else if (Build.CPU_ABI.equals(CpuArchHelper.getArmeabiv7CpuAbi())) {
            assertEquals(cpuArch, CpuArch.ARMv7);
        } else if (Build.CPU_ABI.equals(CpuArchHelper.getArm64CpuAbi())) {
            assertEquals(cpuArch, CpuArch.ARMv7);
        }else {
            assertEquals(cpuArch, CpuArch.NONE);
        }
    }
}