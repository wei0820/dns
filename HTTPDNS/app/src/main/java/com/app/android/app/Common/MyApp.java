package com.app.android.app.Common;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import com.tencent.bugly.Bugly;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //Multidex.install(this);
        // 1: cedc38f1c4
        // 2: 4e1d55a7fb
        //CrashReport.initCrashReport(getApplicationContext(), "4e1d55a7fb", false);
        Bugly.init(getApplicationContext(), "4e1d55a7fb", true);
        closeAndroidPDialog();

    }
    public boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }

    private void closeAndroidPDialog() {
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                Class aClass = Class.forName("android.content.pm.PackageParser$Package");
                Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
                declaredConstructor.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Class cls = Class.forName("android.app.ActivityThread");
                Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
                declaredMethod.setAccessible(true);
                Object activityThread = declaredMethod.invoke(null);
                Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
                mHiddenApiWarningShown.setAccessible(true);
                mHiddenApiWarningShown.setBoolean(activityThread, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
