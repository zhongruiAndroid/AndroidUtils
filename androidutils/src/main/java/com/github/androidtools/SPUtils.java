package com.github.androidtools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/10/25.
 */
public class SPUtils {
    /********************************************************************************************************************/
    public static String getPrefString(String fileName,Context context, String key, final String defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);

    }
    public static String getPrefString(Context context, String key, final String defaultValue) {
        String fileName = context.getPackageName().replace(".","_");
        return getPrefString(fileName,context,key,defaultValue);
    }
    /********************************************************************************************************************/
    public static void setPrefString(Context context, final String key, final String value) {
        String fileName = context.getPackageName().replace(".","_");
        setPrefString(fileName,context,key,value);
    }
    public static void setPrefString(String fileName,Context context, final String key, final String value) {
        final SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        settings.edit().putString(key, value).commit();
    }

    /********************************************************************************************************************/
    public static boolean getPrefBoolean(Context context, final String key, final boolean defaultValue) {
        String fileName = context.getPackageName().replace(".","_");
        return getPrefBoolean(fileName,context,key,defaultValue);
    }
    public static boolean getPrefBoolean(String fileName,Context context, final String key, final boolean defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    /********************************************************************************************************************/
    public static boolean hasKey(String fileName,Context context, final String key) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
                .contains(key);
    }
    public static boolean hasKey(Context context, final String key) {
        String fileName = context.getPackageName().replace(".","_");
        return hasKey(fileName,context,key);
    }
    /********************************************************************************************************************/
    public static void setPrefBoolean(Context context, final String key, final boolean value) {
        String fileName = context.getPackageName().replace(".","_");
        setPrefBoolean(fileName,context,key,value);
    }
    public static void setPrefBoolean(String fileName,Context context, final String key, final boolean value) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        settings.edit().putBoolean(key, value).commit();
    }

    /********************************************************************************************************************/
    public static void setPrefInt(Context context, final String key, final int value) {
        String fileName = context.getPackageName().replace(".","_");
        setPrefInt(fileName,context,key,value);
    }
    public static void setPrefInt(String fileName,Context context, final String key, final int value) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        settings.edit().putInt(key, value).commit();
    }
    /********************************************************************************************************************/
    public static int getPrefInt( Context context, final String key, final int defaultValue) {
        String fileName = context.getPackageName().replace(".","_");
        return getPrefInt(fileName,context,key,defaultValue);
    }
    public static int getPrefInt(String fileName,Context context, final String key, final int defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }
    /********************************************************************************************************************/
    public static void setPrefFloat(Context context, final String key, final float value) {
        String fileName = context.getPackageName().replace(".","_");
        setPrefFloat(fileName,context,key,value);
    }
    public static void setPrefFloat(String fileName,Context context, final String key, final float value) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        settings.edit().putFloat(key, value).commit();
    }
    /********************************************************************************************************************/
    public static float getPrefFloat(Context context, final String key, final float defaultValue) {
        String fileName = context.getPackageName().replace(".","_");
        return getPrefFloat(fileName,context,key,defaultValue);
    }
    public static float getPrefFloat(String fileName,Context context, final String key, final float defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }
    /********************************************************************************************************************/
    public static void setSettingLong(Context context, final String key, final long value) {
        String fileName = context.getPackageName().replace(".","_");
        setSettingLong(fileName,context,key,value);
    }
    public static void setSettingLong(String fileName,Context context, final String key, final long value) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        settings.edit().putLong(key, value).commit();
    }
    /********************************************************************************************************************/
    public static long getPrefLong(Context context, final String key, final long defaultValue) {
        String fileName = context.getPackageName().replace(".","_");
        return getPrefLong(fileName,context,key,defaultValue);
    }
    public static long getPrefLong(String fileName,Context context, final String key, final long defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }
    /********************************************************************************************************************/
    public static void clearPreference(Context context) {
        String fileName = context.getPackageName().replace(".","_");
        clearPreference(fileName,context);
    }
    public static void clearPreference(String fileName,Context context) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = settings.edit();
        edit.clear();
        edit.commit();
    }
    /********************************************************************************************************************/
    public static void removeKey(Context context,String key) {
        String fileName = context.getPackageName().replace(".","_");
        removeKey(fileName,context,key);
    }
    public static void removeKey(String fileName,Context context,String key) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = settings.edit();
        edit.remove(key);
        edit.commit();
    }
    /********************************************************************************************************************/
}
