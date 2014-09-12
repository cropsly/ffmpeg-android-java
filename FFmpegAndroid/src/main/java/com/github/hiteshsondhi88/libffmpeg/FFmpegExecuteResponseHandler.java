package com.github.hiteshsondhi88.libffmpeg;

public interface FFmpegExecuteResponseHandler extends ResponseHandler {

    public void onFailure(String message);
    public void onSuccess(String message);
    public void onProgress(String message);

}
