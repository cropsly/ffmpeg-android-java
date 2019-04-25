!!! Deprecated !!!
===================

This project is discontinued. There is a much better [alternative by bravobit](https://github.com/bravobit/FFmpeg-Android) which incorporates good parts of this fork and also attempts to solve some existing issues. I'll be keeping a close eye to its issue tracker and contribute there instead.

About the fork
===================
### Installation
Edit your project's build.gradle (not app) like this. (important line is jitpack)

```
allprojects {
    repositories {
        jcenter()
        mavenCentral()

        ...

        maven {
            url 'https://jitpack.io'
        }
    }
}
```

Add below line to your app's build.gradle dependencies.

`compile 'com.github.diegoperini:ffmpeg-android-java:v0.4.9'`


### Applied fixes

* Added `whenFFmpegIsReady()` to properly wait for ffmpeg state.
* Fixed `killRunningProcesses()` to properly kill the execution.
* Added a `FFmpeg.getInstance()` overload to work with a `ContextProvider` instead of a context. It is a fix for a common memory leak caused by storing the context internally. Old factory method is still supported but marked as deprecated.
* Fixed `isFFmpegCommandRunning()` to properly return running state status. (thanks to @pawaom)


### Help needed

* to update ffmpeg binary versions for all architectures
* to test the fixes
* to build and publish the fork somewhere more common

### License

GPLv3

-----------------------------------

[FFmpeg-Android-Java](http://writingminds.github.io/ffmpeg-android-java/) [![Build Status](https://travis-ci.org/hiteshsondhi88/ffmpeg-android-java.svg?branch=master)](https://travis-ci.org/hiteshsondhi88/ffmpeg-android-java) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-FFmpeg--Android--Java-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/931)
==============

[![Join the chat at https://gitter.im/hiteshsondhi88/ffmpeg-android-java](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/hiteshsondhi88/ffmpeg-android-java?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## About
[FFmpeg Android java](http://writingminds.github.io/ffmpeg-android-java/) is a java library that simplifies your task of using ffmpeg in Android project which I've compiled using [FFmpeg-Android](http://writingminds.github.io/ffmpeg-android/)

These are two basic methods of this library:

* `loadBinary(FFmpegLoadBinaryResponseHandler ffmpegLoadBinaryResponseHandler) throws FFmpegNotSupportedException`
* `execute(String cmd, FFmpegExecuteResponseHandler ffmpegExecuteResponseHandler) throws FFmpegCommandAlreadyRunningException`

For examples and usage instructions head over to:
* [writingminds.github.io/ffmpeg-android-java] (http://writingminds.github.io/ffmpeg-android-java/)

## Supported Architecture
* armv7
* armv7-neon
* x86

## Sample
![http://i.imgur.com/cP4WhLn.gif](http://i.imgur.com/cP4WhLn.gif)
* [Download APK](https://github.com/writingminds/ffmpeg-android-java/releases/download/v0.3.2/app-debug.apk)

## JavaDoc
* [Javadoc](http://writingminds.github.io/ffmpeg-android-java/docs/)

## License
* Check file LICENSE.GPLv3 and Make sure to follow the licensing terms and conditions of the project and the software used to build the project.

## HIRE US
* Get in touch with us - http://www.writingminds.com


[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/hiteshsondhi88/ffmpeg-android-java/trend.png)](https://bitdeli.com/free "Bitdeli Badge")
