package com.app.android.app.Common;

import android.app.Application;
import android.os.Build;

import com.app.android.app.Activity.MainActivity;
import com.app.android.app.R;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;


public class MyApp extends Application {

    static {
        // 提前加载依赖包
        System.loadLibrary("Bugly");
    }

    CrashReport.UserStrategy mUserStrategy;

    @Override
    public void onCreate() {
        super.onCreate();


        //Multidex.install(this);
        // 1: cedc38f1c4
        // 2: 4e1d55a7fb
        //CrashReport.initCrashReport(getApplicationContext(), "4e1d55a7fb", false);
        /***** Beta高级设置 *****/
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.initDelay = 3 * 1000;
        Beta.largeIconId = R.mipmap.ic_launcher;
        Beta.smallIconId = R.mipmap.ic_launcher;
        Beta.showInterruptedStrategy = true;
        Beta.canShowUpgradeActs.add(MainActivity.class);
        //setupStrategy();
        Bugly.init(getApplicationContext(), "4e1d55a7fb", false, mUserStrategy);
        closeAndroidPDialog();
        /****  初始化 x5 浏览器 ***/
        //QbSdk.initX5Environment(getApplicationContext(),null);



        /*
            用来修复如下错误问题。
            #1402 SIGSEGV(SEGV_ACCERR)
            SIGSEGV(SEGV_ACCERR):
            找不到 /system/app/WebViewGoogle/WebViewGoogle.apk 错误问题。
        */
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                String[] s = getApplicationInfo().sharedLibraryFiles;
                int arrayLength = (s != null ? s.length : 0) + 1;
                final String[] sharedLibFiles = new String[arrayLength];
                if (s != null) {
                    System.arraycopy(s, 0, sharedLibFiles, 0, s.length);
                }
                sharedLibFiles[arrayLength - 1] = "/system/app/WebViewGoogle/WebViewGoogle.apk";
                getApplicationInfo().sharedLibraryFiles = sharedLibFiles;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    private void setupStrategy() {
        // 上报bug
        mUserStrategy = new CrashReport.UserStrategy(getApplicationContext());
        mUserStrategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {

            public Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                String x5CrashInfo = com.tencent.smtt.sdk.WebView.getCrashExtraMessage(getApplicationContext());
                map.put("x5crashInfo", x5CrashInfo);
                return map;
            }

            @Override
            public byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType, String errorMessage, String errorStack) {
                try {
                    return "Extra data.".getBytes("UTF-8");
                } catch (Exception e) {
                    return null;
                }

            }
        });
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
