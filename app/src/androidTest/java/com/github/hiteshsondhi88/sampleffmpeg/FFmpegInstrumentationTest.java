package com.github.hiteshsondhi88.sampleffmpeg;

import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.io.File;

import javax.inject.Inject;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;

import static org.assertj.core.api.Assertions.assertThat;

public class FFmpegInstrumentationTest extends ActivityInstrumentationTestCase2<Home> {

    private static final String TAG = FFmpegInstrumentationTest.class.getSimpleName();

    @Inject
    FFmpeg ffmpeg;

    public FFmpegInstrumentationTest() {
        super(Home.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        assertNotNull(getActivity());
        ffmpeg = getActivity().ffmpeg;
        assertNotNull(ffmpeg);
        ffmpeg.loadBinary(new FFmpegLoadBinaryResponseHandler() {

            @Override
            public void onStart() {
                // Do nothing
            }

            @Override
            public void onFailure() {
                assertTrue("error loading ffmpeg binary", false);
            }

            @Override
            public void onSuccess() {
                assertTrue("success loading ffmpeg binary", true);
            }

            @Override
            public void onFinish() {
                synchronized (FFmpegInstrumentationTest.this) {
                    FFmpegInstrumentationTest.this.notify();
                }
            }
        });
        synchronized (FFmpegInstrumentationTest.this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Util.copyBinaryFromAssetsToData(getActivity(), "sample_videos/sample_1.mkv", "sample_1.mkv");
            Util.copyBinaryFromAssetsToData(getActivity(), "sample_videos/sample_2.mp4", "sample_2.mp4");
            Util.copyBinaryFromAssetsToData(getActivity(), "sample_videos/sample_3.avi", "sample_3.avi");
            Util.copyBinaryFromAssetsToData(getActivity(), "sample_srt/sample.srt", "sample.srt");
        } catch (Exception e) {
            Log.e(TAG, "could not copy files", e);
            assertTrue("could not copy files", false);
            throw e;
        }
        assertNotNull(getActivity());
        assertNotNull(ffmpeg);
    }

    public String testFFmpegDeviceVersion() {
        String ffmpegDeviceVersion = null;
        try {
            ffmpegDeviceVersion = ffmpeg.getDeviceFFmpegVersion();
        } catch (FFmpegCommandAlreadyRunningException e) {
            assertTrue(false);
        }
        Log.i(TAG, ffmpegDeviceVersion);
        assertThat(ffmpegDeviceVersion).isNotEmpty();
        return ffmpegDeviceVersion;
    }

    public String testFFmpegLibraryVersion() {
        String ffmpegLibraryVersion = ffmpeg.getLibraryFFmpegVersion();
        Log.i(TAG, ffmpegLibraryVersion);
        assertThat(ffmpegLibraryVersion).isNotEmpty();
        return ffmpegLibraryVersion;
    }

    public void testFFmpegDeviceAndLibraryVersionEqual() {
        assertThat(testFFmpegDeviceVersion()).isEqualTo(testFFmpegLibraryVersion());
    }

    public void testFFmpegMP4toMKVUsingx264() {
        File outmkv = new File(getFFmpegFilesDir(), "output.mkv");

        // Convert from mp4 to mkv
        checkFFmpegConvertUsingx264(getMp4SampleFile(), outmkv);
    }

    public void testFFmpegMKVtoAVI() {
        File outavi = new File(getFFmpegFilesDir(), "output.avi");

        // Convert from mkv to avi
        checkFFmpegConvertCommon(getMkvSampleFile(), outavi);
    }

    public void testFFmpegAVItoMP4Usingx264() {
        File outmp4 = new File(getFFmpegFilesDir(), "output.mp4");

        // Convert from avi to mp4
        checkFFmpegConvertUsingx264(getAviSampleFile(), outmp4);
    }

    public void testLibass() {
        File outass = new File(getFFmpegFilesDir(), "output.ass");
        checkFFmpegCommon("-y -i "+getSrtSampleFile()+" "+outass.getAbsolutePath(), outass);
    }

    private void checkFFmpegConvertUsingx264(File inputFile, File outputFile) {
        checkFFmpegCommon("-y -i "+inputFile.getAbsolutePath()+" -c:v libx264 -preset ultrafast "+outputFile.getAbsolutePath(), outputFile);
    }

    private void checkFFmpegConvertCommon(File inputFile, File outputFile) {
        checkFFmpegCommon("-y -i "+inputFile.getAbsolutePath()+" "+outputFile.getAbsolutePath(), outputFile);
    }

    private void checkFFmpegCommon(final String cmd, final File outputFile) {
        Log.d(TAG, "start : "+outputFile.getAbsolutePath());
        try {
            ffmpeg.execute(cmd, new FFmpegExecuteResponseHandler() {

                @Override
                public void onStart() {

                }

                @Override
                public void onProgress(String message) {
                    Log.d(TAG, "progress : "+message);
                }

                @Override
                public void onFailure(String message) {
                    assertTrue(false);
                }

                @Override
                public void onSuccess(String message) {
                    checkFileValid(outputFile);
                }

                @Override
                public void onFinish() {
                    Log.d(TAG, "done : "+outputFile.getAbsolutePath());
                    synchronized (FFmpegInstrumentationTest.this) {
                        FFmpegInstrumentationTest.this.notify();
                    }
                }
            });
        } catch (Exception e) {
            assertTrue(false);
        }
        synchronized (FFmpegInstrumentationTest.this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkFileValid(File file) {
        assertThat(file.exists() && file.length() > 0 && file.delete()).isTrue();
    }

    private File getMkvSampleFile() {
        return getSampleFile("sample_1.mkv");
    }

    private File getMp4SampleFile() {
        return getSampleFile("sample_2.mp4");
    }

    private File getAviSampleFile() {
        return getSampleFile("sample_3.avi");
    }

    private File getSrtSampleFile() {
        return getSampleFile("sample.srt");
    }

    private File getSampleFile(String sampleName) {
        File sample = new File(getActivity().getFilesDir(), sampleName);
        assertThat(sample.exists() && sample.canRead()).isTrue();
        return sample;
    }

    private File getFFmpegFilesDir() {
        File filesDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            filesDir = getActivity().getExternalFilesDir(null);
        else
            filesDir = getActivity().getFilesDir();
        assertThat(filesDir != null && filesDir.exists() && filesDir.canWrite()).isTrue();
        return filesDir;
    }

}
