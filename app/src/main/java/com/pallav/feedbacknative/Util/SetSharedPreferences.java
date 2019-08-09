package com.pallav.feedbacknative.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class SetSharedPreferences {
    public void setValue(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(key, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(key, context.MODE_PRIVATE);
        return sharedPref.getString(key,null);
    }

    public void setInt(Context context, String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(key, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(key, context.MODE_PRIVATE);
        return sharedPref.getInt(key, -1);
    }

    public void setBool(Context context, String key, Boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(key, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean getBool(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(key, context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }
}
