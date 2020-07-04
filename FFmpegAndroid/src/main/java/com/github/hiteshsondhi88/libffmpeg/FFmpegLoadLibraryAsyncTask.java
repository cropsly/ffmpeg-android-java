package com.github.hiteshsondhi88.libffmpeg;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;

class FFmpegLoadLibraryAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private final String cpuArchNameFromAssets;
    private final FFmpegLoadBinaryResponseHandler ffmpegLoadBinaryResponseHandler;
    private final Context context;

    FFmpegLoadLibraryAsyncTask(Context context, String cpuArchNameFromAssets, FFmpegLoadBinaryResponseHandler ffmpegLoadBinaryResponseHandler) {
        this.context = context;
        this.cpuArchNameFromAssets = cpuArchNameFromAssets;
        this.ffmpegLoadBinaryResponseHandler = ffmpegLoadBinaryResponseHandler;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        File ffmpegFile = new File(FileUtils.getFFmpeg(context));



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

}
