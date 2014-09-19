#!/bin/bash

function create_emulator() {
  abi=$1
  api_level=$2
  port1=$3
  port2=$4
  emulator_name="test_${abi}_${api_level}"
  emulator_serial="emulator-${port1}"
  echo no | android create avd -c 50M --force -n $emulator_name -t ${api_level} --abi ${abi}
  emulator -ports ${port1},${port2} -partition-size 256 -avd $emulator_name -no-skin -no-boot-anim -no-audio -no-window -gpu on &
  ./wait_for_emulator ${emulator_serial} || exit 1
  adb -s ${emulator_serial} shell input keyevent 82 &
}

# x86 emulator android-16
create_emulator x86 android-16 5554 5555

# armeabi-v7a emulator android-16
create_emulator armeabi-v7a android-16 5556 5557 

# Running Tests
./gradlew --info build connectedCheck || exit 1
