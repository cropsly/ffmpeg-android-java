#!/bin/bash

echo no | android create avd -c 50M --force -n testx86 -t android-16 --abi x86
emulator -avd testx86 -no-skin -no-audio -no-window &
EMULATOR_PID=$!
adb wait-for-device
./wait_for_emulator
adb shell input keyevent 82 &
./gradlew --info build connectedAndroidTest
kill -9 $EMULATOR_PID && sleep 10
