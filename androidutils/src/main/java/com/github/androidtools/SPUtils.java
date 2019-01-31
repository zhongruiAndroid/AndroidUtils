package com.github.androidtools;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by Administrator on 2016/10/25.
 */
public class SPUtils {
    private static String xmlName;
    private static Context mContext;

    public static void init(Context context){
        mContext=context;
    }
    public static void init(String fileName){
        xmlName=fileName;
    }
    public static void init(Context context,String fileName){
        mContext=context;
        xmlName=fileName;
    }
    private static Context getContext(){
        if(mContext==null){
            throw new IllegalStateException("请在Application调用SPUtils.init(context)初始化context");
        }
        return mContext;
    }
    private static String getFileName(Context context){
        String fileName = xmlName;
        if(fileName==null){
            fileName = context.getPackageName().replace(".","_");
        }
        return fileName;
    }
    /********************************************************************************************************************/
    public static Set<String> getStringSet(String fileName,Context context, String key,  Set<String> defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.getStringSet(key, defaultValue);
    }
    public static Set<String> getStringSet(Context context, String key,  Set<String> defaultValue) {
        return getStringSet(getFileName(context),context,key, defaultValue);
    }
    public static Set<String> getStringSet(String fileName, String key,  Set<String> defaultValue) {
        return getStringSet(fileName,getContext(),key,defaultValue);
    }
    public static Set<String> getStringSet(String key,  Set<String> defaultValue) {
        return getStringSet(getFileName(getContext()),getContext(),key,defaultValue);
    }
    /********************************************************************************************************************/
    public static boolean setPrefStringSet(String fileName,Context context,  String key,  Set<String> value) {
        final SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.edit().putStringSet(key, value).commit();
    }
    public static boolean setPrefStringSet(Context context,  String key,  Set<String> value) {
        return setPrefStringSet(getFileName(context),context,key,value);
    }
    public static boolean setPrefStringSet(String fileName, String key,  Set<String> value) {
        return setPrefStringSet(fileName,getContext(),key,value);
    }
    public static boolean setPrefStringSet(String key,Set<String> value) {
        return setPrefStringSet(getFileName(getContext()),getContext(),key,value);
    }

    /********************************************************************************************************************/
    // TODO: 2019/1/31  
    public static String getString(String fileName,Context context, String key, final String defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);

    }
    public static String getString(Context context, String key, final String defaultValue) {
        return getString(getFileName(context),context,key,defaultValue);
    }
    /********************************************************************************************************************/
    public static boolean setPrefString(Context context, final String key, final String value) {
        return setPrefString(getFileName(context),context,key,value);
    }
    public static boolean setPrefString(String fileName,Context context, final String key, final String value) {
        final SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.edit().putString(key, value).commit();
    }

    /********************************************************************************************************************/
    public static boolean getBoolean(Context context, final String key, final boolean defaultValue) {
        return getBoolean(getFileName(context),context,key,defaultValue);
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
        return hasKey(getFileName(context),context,key);
    }
    /********************************************************************************************************************/
    public static boolean setPrefBoolean(Context context, final String key, final boolean value) {
        return setPrefBoolean(getFileName(context),context,key,value);
    }
    public static boolean setPrefBoolean(String fileName,Context context, final String key, final boolean value) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.edit().putBoolean(key, value).commit();
    }

    /********************************************************************************************************************/
    public static boolean setPrefInt(Context context, final String key, final int value) {
        return setPrefInt(getFileName(context),context,key,value);
    }
    public static boolean setPrefInt(String fileName,Context context, final String key, final int value) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.edit().putInt(key, value).commit();
    }
    /********************************************************************************************************************/
    public static int getInt( Context context, final String key, final int defaultValue) {
        return getInt(getFileName(context),context,key,defaultValue);
    }
    public static int getInt(String fileName,Context context, final String key, final int defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }
    /********************************************************************************************************************/
    public static boolean setPrefFloat(Context context, final String key, final float value) {
        return setPrefFloat(getFileName(context),context,key,value);
    }
    public static boolean setPrefFloat(String fileName,Context context, final String key, final float value) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.edit().putFloat(key, value).commit();
    }
    /********************************************************************************************************************/
    public static float getFloat(Context context, final String key, final float defaultValue) {
        return getFloat(getFileName(context),context,key,defaultValue);
    }
    public static float getFloat(String fileName,Context context, final String key, final float defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }
    /********************************************************************************************************************/
    public static boolean setPreLong(Context context, final String key, final long value) {
        return  setPreLong(getFileName(context),context,key,value);
    }
    public static boolean setPreLong(String fileName,Context context, final String key, final long value) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return  settings.edit().putLong(key, value).commit();
    }
    /********************************************************************************************************************/
    public static long getLong(Context context, final String key, final long defaultValue) {
        return getLong(getFileName(context),context,key,defaultValue);
    }
    public static long getLong(String fileName,Context context, final String key, final long defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }
    /********************************************************************************************************************/
    public static boolean clearPreference(Context context) {
        return clearPreference(getFileName(context),context);
    }
    public static boolean clearPreference(String fileName,Context context) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = settings.edit();
        edit.clear();
        return edit.commit();
    }
    /********************************************************************************************************************/
    public static boolean removeKey(Context context,String key) {
        return removeKey(getFileName(context),context,key);
    }
    public static boolean removeKey(String fileName,Context context,String key) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = settings.edit();
        edit.remove(key);
        return edit.commit();
    }
    /********************************************************************************************************************/
}
