#!/bin/bash

# x86 emulator
echo no | android create avd -c 50M --force -n testx86 -t android-16 --abi x86
emulator -ports 5554,5555 -partition-size 256 -avd testx86 -no-skin -no-boot-anim -no-audio -no-window &
PID_EMU1=$!
./wait_for_emulator emulator-5554

# armeabi-v7a emulator
echo no | android create avd -c 50M --force -n testarm -t android-16 --abi armeabi-v7a
emulator -ports 5556,5557 -partition-size 256 -avd testarm -no-skin -no-boot-anim -no-audio -no-window &
PID_EMU2=$!
./wait_for_emulator emulator-5556

# Running Tests
adb logcat &
./gradlew --info build connectedCheck || exit 1
kill -9 $PID_EMU1 $PID_EMU2
