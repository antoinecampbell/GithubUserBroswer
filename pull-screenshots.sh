#!/bin/bash

# Pull screenshots from device
echo "Pulling screenshots to desktop..."
adb pull /sdcard/Robotium-Screenshots ~/Desktop/Robobitum-Screenshots

# Clear screenshots from device
echo "Deleting screenshots..."
adb shell rm /sdcard/Robotium-Screenshots/*
