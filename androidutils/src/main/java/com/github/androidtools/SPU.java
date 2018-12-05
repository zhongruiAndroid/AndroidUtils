package com.github.androidtools;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by Administrator on 2016/10/25.
 */
public class SPU{

    private static String xmlName;
    public static void init(String fileName){
        xmlName=fileName;
    }
    private static String fileName(Context context){
        String fileName = xmlName;
        if(fileName==null){
            fileName = context.getPackageName().replace(".","_");
        }
        return fileName;
    }

    /********************************************************************************************************************/
    public static Set<String> getStringSet(String fileName,Context context, String key, final Set<String> defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.getStringSet(key, defaultValue);

    }
    public static Set<String> getStringSet(Context context, String key, final Set<String> defaultValue) {
        return getStringSet(fileName(context),context,key,defaultValue);
    }
    /********************************************************************************************************************/
    public static void setPrefStringSet(Context context, final String key, final Set<String> value) {
        setPrefStringSet(fileName(context),context,key,value);
    }
    public static void setPrefStringSet(String fileName,Context context, final String key, final Set<String> value) {
        final SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        settings.edit().putStringSet(key, value).apply();
    }
    /********************************************************************************************************************/
    public static String getString(String fileName,Context context, String key, final String defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);

    }
    public static String getString(Context context, String key, final String defaultValue) {
        return getString(fileName(context),context,key,defaultValue);
    }
    /********************************************************************************************************************/
    public static void setPrefString(Context context, final String key, final String value) {
        setPrefString(fileName(context),context,key,value);
    }
    public static void setPrefString(String fileName,Context context, final String key, final String value) {
        final SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        settings.edit().putString(key, value).apply();
    }

    /********************************************************************************************************************/
    public static boolean getBoolean(Context context, final String key, final boolean defaultValue) {
        return getBoolean(fileName(context),context,key,defaultValue);
    }
    public static boolean getBoolean(String fileName,Context context, final String key, final boolean defaultValue) {
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
        return hasKey(fileName(context),context,key);
    }
    /********************************************************************************************************************/
    public static void setPrefBoolean(Context context, final String key, final boolean value) {
        setPrefBoolean(fileName(context),context,key,value);
    }
    public static void setPrefBoolean(String fileName,Context context, final String key, final boolean value) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        settings.edit().putBoolean(key, value).apply();
    }

    /********************************************************************************************************************/
    public static void setPrefInt(Context context, final String key, final int value) {
        setPrefInt(fileName(context),context,key,value);
    }
    public static void setPrefInt(String fileName,Context context, final String key, final int value) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        settings.edit().putInt(key, value).apply();
    }
    /********************************************************************************************************************/
    public static int getInt( Context context, final String key, final int defaultValue) {
        return getInt(fileName(context),context,key,defaultValue);
    }
    public static int getInt(String fileName,Context context, final String key, final int defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }
    /********************************************************************************************************************/
    public static void setPrefFloat(Context context, final String key, final float value) {
        setPrefFloat(fileName(context),context,key,value);
    }
    public static void setPrefFloat(String fileName,Context context, final String key, final float value) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        settings.edit().putFloat(key, value).apply();
    }
    /********************************************************************************************************************/
    public static float getFloat(Context context, final String key, final float defaultValue) {
        return getFloat(fileName(context),context,key,defaultValue);
    }
    public static float getFloat(String fileName,Context context, final String key, final float defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }
    /********************************************************************************************************************/
    public static void setSettingLong(Context context, final String key, final long value) {
        setSettingLong(fileName(context),context,key,value);
    }
    public static void setSettingLong(String fileName,Context context, final String key, final long value) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        settings.edit().putLong(key, value).apply();
    }
    /********************************************************************************************************************/
    public static long getLong(Context context, final String key, final long defaultValue) {
        return getLong(fileName(context),context,key,defaultValue);
    }
    public static long getLong(String fileName,Context context, final String key, final long defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }
    /********************************************************************************************************************/
    public static void clearPreference(Context context) {
        clearPreference(fileName(context),context);
    }
    public static void clearPreference(String fileName,Context context) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = settings.edit();
        edit.clear();
        edit.apply();
    }
    /********************************************************************************************************************/
    public static void removeKey(Context context,String key) {
        removeKey(fileName(context),context,key);
    }
    public static void removeKey(String fileName,Context context,String key) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = settings.edit();
        edit.remove(key);
        edit.apply();
    }
    /********************************************************************************************************************/
}
