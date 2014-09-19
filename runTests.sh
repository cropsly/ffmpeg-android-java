#!/bin/bash

function run_test() {
  abi=$1
  api_level=$2
  emulator_name="test_${abi}_${api_level}"
  echo "==============================================="
  echo "      Starting Tests for ${emulator_name}      "
  echo "==============================================="
  echo no | android create avd -c 50M --force -n ${emulator_name} -t ${api_level} --abi ${abi}
  emulator -partition-size 256 -avd ${emulator_name} -no-skin -no-boot-anim -no-audio -no-window -gpu on &
  EMU_PID=$!
  ./wait_for_emulator || exit 1
  adb logcat -v time -s FFmpegInstrumentationTest &
  LOGCAT_PID=$!
  # Running Tests
  ./gradlew --info build connectedCheck || exit 1
  
  # Killing emulator
  kill -9 $EMU_PID $LOGCAT_PID
  
  # delete emulator
  sleep 5 # wait for emulator to get killed
  android delete avd -n ${emulator_name} # delete emulator 
  echo "==============================================="
  echo "      Finished Tests for ${emulator_name}      "
  echo "==============================================="
}

# x86 android-16
run_test x86 android-16

# armeabi-v7a android-16
run_test armeabi-v7a android-16
