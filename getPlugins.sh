#!/bin/bash

if [ "$1" == "--shallow" ]; then
    git clone https://github.com/JetBrains/android.git android --depth 1
else
    git clone https://github.com/JetBrains/android.git android
fi
