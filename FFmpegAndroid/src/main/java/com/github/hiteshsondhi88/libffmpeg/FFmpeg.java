package com.github.hiteshsondhi88.libffmpeg;

import android.content.Context;
import android.text.TextUtils;

import java.lang.reflect.Array;
import java.util.Map;

import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

@SuppressWarnings("unused")
public class FFmpeg implements FFmpegInterface {

    private final Context context;
    private FFmpegExecuteAsyncTask ffmpegExecuteAsyncTask;
    private FFmpegLoadLibraryAsyncTask ffmpegLoadLibraryAsyncTask;

    private static final long MINIMUM_TIMEOUT = 10 * 1000;
    private long timeout = Long.MAX_VALUE;

    private static FFmpeg instance = null;

    private FFmpeg(Context context) {
        this.context = context.getApplicationContext();
        Log.setDEBUG(Util.isDebug(this.context));
    }

    public static FFmpeg getInstance(Context context) {
        if (instance == null) {
            instance = new FFmpeg(context);
        }
        return instance;
    }

    @Override
    public void loadBinary(FFmpegLoadBinaryResponseHandler ffmpegLoadBinaryResponseHandler) throws FFmpegNotSupportedException {
        String cpuArchNameFromAssets = null;
        switch (CpuArchHelper.getCpuArch()) {
            case x86:
                Log.i("Loading FFmpeg for x86 CPU");
                cpuArchNameFromAssets = "x86";
                break;
            case ARMv7:
                Log.i("Loading FFmpeg for armv7 CPU");
                cpuArchNameFromAssets = "armeabi-v7a";
                break;
            case ARMv7_NEON:
                Log.i("Loading FFmpeg for armv7-neon CPU");
                cpuArchNameFromAssets = "armeabi-v7a-neon";
                break;
            case NONE:
                throw new FFmpegNotSupportedException("Device not supported");
        }

        if (!TextUtils.isEmpty(cpuArchNameFromAssets)) {
            ffmpegLoadLibraryAsyncTask = new FFmpegLoadLibraryAsyncTask(context, cpuArchNameFromAssets, ffmpegLoadBinaryResponseHandler);
            ffmpegLoadLibraryAsyncTask.execute();
        } else {
            throw new FFmpegNotSupportedException("Device not supported");
        }
    }

    @Override
    public void execute(Map<String, String> environvenmentVars, String[] cmd, FFmpegExecuteResponseHandler ffmpegExecuteResponseHandler) throws FFmpegCommandAlreadyRunningException {
        if (ffmpegExecuteAsyncTask != null && !ffmpegExecuteAsyncTask.isProcessCompleted()) {
            throw new FFmpegCommandAlreadyRunningException("FFmpeg command is already running, you are only allowed to run single command at a time");
        }
        if (cmd.length != 0) {
            String[] ffmpegBinary = new String[] { FileUtils.getFFmpeg(context, environvenmentVars) };
            String[] command = concatenate(ffmpegBinary, cmd);
            ffmpegExecuteAsyncTask = new FFmpegExecuteAsyncTask(command , timeout, ffmpegExecuteResponseHandler);
            ffmpegExecuteAsyncTask.execute();
        } else {
            throw new IllegalArgumentException("shell command cannot be empty");
        }
    }

    public <T> T[] concatenate (T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    @Override
    public void execute(String[] cmd, FFmpegExecuteResponseHandler ffmpegExecuteResponseHandler) throws FFmpegCommandAlreadyRunningException {
        execute(null, cmd, ffmpegExecuteResponseHandler);
    }

    @Override
    public String getDeviceFFmpegVersion() throws FFmpegCommandAlreadyRunningException {
        ShellCommand shellCommand = new ShellCommand();
        CommandResult commandResult = shellCommand.runWaitFor(new String[] { FileUtils.getFFmpeg(context), "-version" });
        if (commandResult.success) {
            return commandResult.output.split(" ")[2];
        }
        // if unable to find version then return "" to avoid NPE
        return "";
    }

    @Override
    public String getLibraryFFmpegVersion() {
        return context.getString(R.string.shipped_ffmpeg_version);
    }

    @Override
    public boolean isFFmpegCommandRunning() {
        return ffmpegExecuteAsyncTask != null && !ffmpegExecuteAsyncTask.isProcessCompleted();
    }

    @Override
    public boolean killRunningProcesses() {
        return Util.killAsync(ffmpegLoadLibraryAsyncTask) || Util.killAsync(ffmpegExecuteAsyncTask);
    }

    @Override
    public void setTimeout(long timeout) {
        if (timeout >= MINIMUM_TIMEOUT) {
            this.timeout = timeout;
        }
    }
}
