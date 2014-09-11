[FFmpeg-Android-Java](http://hiteshsondhi88.github.io/ffmpeg-android-java/)
==============

* FFmpeg for Android compiled with x264, libass, fontconfig, freetype and fribidi
* Supports Android L

## Supported Architecture
* armv7
* armv7-neon
* x86

## Adding Library to Project (Android Studio)
* TODO : I am still working to push this library to maven central repo
* Download [AAR File](https://github.com/hiteshsondhi88/ffmpeg-android-java/releases/download/v0.1.0/FFmpegAndroid.aar)
* Copy FFmpegAndroid.aar to app/libs
* In your app/build.gradle add
```groovy
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    compile(name:'FFmpegAndroid', ext:'aar')
}
```
* Use the library

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
* To execute "ffmpeg -version" command you just need to pass "-version" as cmd parameter.
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

## Available Methods
* `loadBinary(FFmpegLoadBinaryResponseHandler ffmpegLoadBinaryResponseHandler) throws FFmpegNotSupportedException`
* `execute(Map<String, String> environvenmentVars, String cmd, FFmpegExecuteResponseHandler ffmpegExecuteResponseHandler) throws FFmpegCommandAlreadyRunningException`
* `execute(String cmd, FFmpegExecuteResponseHandler ffmpegExecuteResponseHandler) throws FFmpegCommandAlreadyRunningException`
* `getDeviceFFmpegVersion() throws FFmpegCommandAlreadyRunningException`
* `getLibraryFFmpegVersion()`
* `isFFmpegCommandRunning()`
* `killRunningProcesses()`
* `setTimeout(long timeout)`

## JavaDoc
* [Javadoc](http://hiteshsondhi88.github.io/ffmpeg-android-java/docs/)

## License
* Check file LICENSE.GPLv3 and Make sure to follow the licensing terms and conditions of the project and the software used to build the project.
