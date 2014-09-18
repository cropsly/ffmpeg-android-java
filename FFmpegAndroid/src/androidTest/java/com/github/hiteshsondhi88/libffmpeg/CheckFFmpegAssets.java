package com.github.hiteshsondhi88.libffmpeg;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class CheckFFmpegAssets extends CommonInstrumentationTestCase {

    public void testFFmpegAssetsWithSha1Sum() {
        AssetManager assetMgr = getInstrumentation().getContext().getResources().getAssets();
        InputStream is = null;
        try {
            is = assetMgr.open("armeabi-v7a/ffmpeg");
            assertEquals(CpuArch.ARMv7.getSha1(), FileUtils.SHA1(is));
            is = assetMgr.open("armeabi-v7a-neon/ffmpeg");
            assertEquals(CpuArch.ARMv7_NEON.getSha1(), FileUtils.SHA1(is));
            is = assetMgr.open("x86/ffmpeg");
            assertEquals(CpuArch.x86.getSha1(), FileUtils.SHA1(is));
        } catch (IOException e) {
            Log.e(e);
        } finally {
            Util.close(is);
        }
    }

}
