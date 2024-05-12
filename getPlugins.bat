@echo off

:ParseArgs
if "%~1" == "--shallow" (
    call git clone https://github.com/JetBrains/android.git android --depth 1
) else (
    call git clone https://github.com/JetBrains/android.git android
)
