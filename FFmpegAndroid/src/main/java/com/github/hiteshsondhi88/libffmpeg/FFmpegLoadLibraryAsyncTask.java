package com.github.hiteshsondhi88.libffmpeg;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;

import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;

class FFmpegLoadLibraryAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private final String cpuArchNameFromAssets;
    private final FFmpegLoadBinaryResponseHandler ffmpegLoadBinaryResponseHandler;
    private final Context context;
    private final FFmpeg ffmpeg;

    FFmpegLoadLibraryAsyncTask(Context context, String cpuArchNameFromAssets, FFmpegLoadBinaryResponseHandler ffmpegLoadBinaryResponseHandler, FFmpeg ffmpeg) {
        this.context = context;
        this.cpuArchNameFromAssets = cpuArchNameFromAssets;
        this.ffmpegLoadBinaryResponseHandler = ffmpegLoadBinaryResponseHandler;
        this.ffmpeg = ffmpeg;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        File ffmpegFile = new File(FileUtils.getFFmpeg(context));
        if (ffmpegFile.exists() && isDeviceFFmpegVersionOld() && !ffmpegFile.delete()) {
            return false;
        }
        if (!ffmpegFile.exists()) {
            boolean isFileCopied = FileUtils.copyBinaryFromAssetsToData(context,
                    cpuArchNameFromAssets + File.separator + FileUtils.ffmpegFileName,
                    FileUtils.ffmpegFileName);

            // make file executable
            if (isFileCopied) {
                if(!ffmpegFile.canExecute()) {
                    Log.d("FFmpeg is not executable, trying to make it executable ...");
                    if (ffmpegFile.setExecutable(true)) {
                        return true;
                    }
                } else {
                    Log.d("FFmpeg is executable");
                    return true;
                }
            }
        }
        return ffmpegFile.exists() && ffmpegFile.canExecute();
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
        if (ffmpegLoadBinaryResponseHandler != null) {
            if (isSuccess) {
                ffmpegLoadBinaryResponseHandler.onSuccess();
            } else {
                ffmpegLoadBinaryResponseHandler.onFailure();
            }
            ffmpegLoadBinaryResponseHandler.onFinish();
        }
    }

    private boolean isDeviceFFmpegVersionOld() {
        String ffmpegDeviceVersion;
        try {
            ffmpegDeviceVersion = ffmpeg.getDeviceFFmpegVersion();
        } catch (FFmpegCommandAlreadyRunningException e) {
            Log.e("FFmpeg already running", e);
            // return false, ffmpeg is already running so we cannot replace the existing binary file
            return false;
        }
        return !TextUtils.isEmpty(ffmpegDeviceVersion) && !ffmpegDeviceVersion.equals(ffmpeg.getLibraryFFmpegVersion());
    }
}
