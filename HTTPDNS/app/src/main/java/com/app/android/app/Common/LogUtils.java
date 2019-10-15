package com.app.android.app.Common;

import android.util.Log;

public class LogUtils {


    private static boolean isDebug = true;

    public static void out(String msg) {
        if (isDebug && msg != null) {
            Log.e("Test By Me  >>> ", msg);
        }
    }
}
