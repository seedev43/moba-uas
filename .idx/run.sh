#!/bin/bash
./gradlew installDebug || exit $?
$ANDROID_SDK_ROOT/platform-tools/adb -s emulator-5554 shell am start -a android.intent.action.MAIN -n com.ourteam.hoohflix/.MainActivity
$ANDROID_SDK_ROOT/platform-tools/adb -s emulator-5554 logcat *:S com.ourteam.hoohflix:D