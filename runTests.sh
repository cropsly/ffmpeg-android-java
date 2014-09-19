#!/bin/bash

# building project for the first time
./gradlew --info clean build || exit 1

function run_test() {
  abi=$1
  api_level=$2
  emulator_name="test_${abi}_${api_level}"
  echo no | android create avd -c 50M --force -n ${emulator_name} -t ${api_level} --abi ${abi}
  emulator -partition-size 256 -avd ${emulator_name} -no-skin -no-boot-anim -no-audio -no-window -gpu on &
  EMU_PID=$!
  ./wait_for_emulator || exit 1
  adb shell input keyevent 82 &
  
  # Running Tests
  ./gradlew --info connectedCheck || exit 1
  
  # Killing emulator
  kill -9 $EMU_PID
  
  # delete emulator
  sleep 5 # wait for emulator to get killed
  android delete avd -n ${emulator_name} # delete emulator 
}

# x86 android-16
run_test x86 android-16

# armeabi-v7a android-16
run_test armeabi-v7a android-16

# x86 android-L
run_test x86 android-L

# armeabi-v7a android-L 
run_test armeabi-v7a android-L
