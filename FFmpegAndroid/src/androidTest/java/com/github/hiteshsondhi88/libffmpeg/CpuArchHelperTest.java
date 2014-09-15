package com.github.hiteshsondhi88.libffmpeg;

import android.os.Build;

import junit.framework.TestCase;

public class CpuArchHelperTest extends TestCase {

    public void testGetCpuArch() throws Exception {
        CpuArch cpuArch = CpuArchHelper.getCpuArch();
        assertNotNull(cpuArch);
        if (Build.CPU_ABI.equals(CpuArchHelper.getx86CpuAbi()) || Build.CPU_ABI.equals(CpuArchHelper.getArmeabiv7CpuAbi())) {
            assertNotSame(cpuArch, CpuArch.NONE);
        } else {
            assertEquals(cpuArch, CpuArch.NONE);
        }
    }
}