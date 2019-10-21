package com.app.android.app.Bean;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPrefernces {
    public static final String  NAME = "MySharedPrefernces";

    public static final String KEY_TOKEN = "token";

    public static  void saveToken(Context context, String token){
        SharedPreferences sp = context.getSharedPreferences(NAME, Activity.MODE_PRIVATE);
        sp.edit().putString(KEY_TOKEN, token).apply();
    }

    public static String getToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Activity.MODE_PRIVATE);
        return sp.getString(KEY_TOKEN, "");
    }

    public static final String KEY_CUSTOMCONTENT = "customcontent";

    public static  void saveCustomcontent(Context context, String customcontent){
        SharedPreferences sp = context.getSharedPreferences(NAME, Activity.MODE_PRIVATE);
        sp.edit().putString(KEY_CUSTOMCONTENT, customcontent).apply();
    }

    public static String getCustomcontent(Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Activity.MODE_PRIVATE);
        return sp.getString(KEY_CUSTOMCONTENT, "");
    }

    public static void clearSharedPrefernces(Context context){
//        saveToken(context,"");
        saveCustomcontent(context,"");
    }

}
