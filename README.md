[FFmpeg-Android-Java](http://hiteshsondhi88.github.io/ffmpeg-android-java/)
==============

* FFmpeg for Android compiled with x264, libass, fontconfig, freetype and fribidi
* Supports Android L

Supported Architecture
----
* armv7
* armv7-neon
* x86

## Usage

### Load Binary
This method copies the ffmpeg binary to device according to device's architecture. You just need to put this once in your code,
whenever you are starting the application or using FFmpeg for the first time. This command does the following:
* Loads/Copies binary to device according to architecture
* Updates binary if it is using old FFmpeg version
* Provides callbacks through FFmpegLoadBinaryResponseHandler interface
```java
FFmpeg ffmpeg = FFmpeg.getInstance(context);
try {
  ffmpeg.loadBinary(new FFmpegLoadBinaryResponseHandler() {

    @Override
    public void onStart() {}

    @Override
    public void onFailure() {}

    @Override
    public void onSuccess() {}

    @Override
    public void onFinish() {}
  });
} catch (FFmpegNotSupportedException e) {
  // Handle if FFmpeg is not supported by device
}
```

### Execute Binary
This method executes ffmpeg command and provides callback through FFmpegExecuteResponseHandler interface. You also need to pass command as argument
to this method.
e.g - To execute "ffmpeg -version" command you just need to pass "-version" as cmd parameter.
```java
FFmpeg ffmpeg = FFmpeg.getInstance(context);
try {
  // to execute "ffmpeg -version" command you just need to pass "-version"
  ffmpeg.execute(cmd, new FFmpegExecuteResponseHandler() {

    @Override
    public void onStart() {}

    @Override
    public void onProgress(String message) {}

    @Override
    public void onFailure(String message) {}

    @Override
    public void onSuccess(String message) {}

    @Override
    public void onFinish() {}
  });
} catch (FFmpegCommandAlreadyRunningException e) {
  // Handle if FFmpeg is already running
}
```

License
----
  check file LICENSE.GPLv3 and Make sure to follow the licensing terms and conditions of the project and the software used to build the project.
