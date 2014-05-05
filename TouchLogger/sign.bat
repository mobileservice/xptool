java -jar ./SignApk/signapk.jar ./SignApk/platform.x509.pem ./SignApk/platform.pk8 ./bin/TouchLogger.apk ./bin/TouchLogger1.apk
adb push bin/TouchLogger1.apk /sdcard/zjw