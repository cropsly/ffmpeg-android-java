package com.github.hiteshsondhi88.libffmpeg;

import android.content.res.AssetManager;
import android.text.TextUtils;

import com.github.hiteshsondhi88.libffmpeg.utils.AssertionHelper;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CpuArchTest extends CommonInstrumentationTestCase {

    public void testFFmpegAssetsWithSha1Sum() {
        testFFmpegAsset(CpuArch.ARMv7, "armeabi-v7a/ffmpeg");
        testFFmpegAsset(CpuArch.x86, "x86/ffmpeg");
    }

    private void testFFmpegAsset(CpuArch cpuArch, String assetsPath) {
        AssetManager assetMgr = getInstrumentation().getContext().getResources().getAssets();
        InputStream is = null;
        try {
            is = assetMgr.open(assetsPath);
            String assetSha1Sum = FileUtils.SHA1(is);
            assertThat(!TextUtils.isEmpty(cpuArch.getSha1())
                    && !TextUtils.isEmpty(assetSha1Sum)
                    && cpuArch.getSha1().equals(assetSha1Sum)).isTrue();
        } catch (IOException e) {
            Log.e(e);
            AssertionHelper.assertError("error validating ffmpeg asset "+assetsPath);
        } finally {
            Util.close(is);
        }

    }

}
