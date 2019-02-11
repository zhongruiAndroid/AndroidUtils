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
         SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
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
         SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
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
    public static String getString(String fileName,Context context, String key,  String defaultValue) {
         SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);

    }
    public static String getString(Context context, String key,  String defaultValue) {
        return getString(getFileName(context),context,key,defaultValue);

    }
    public static String getString(String fileName, String key,  String defaultValue) {
        return getString(fileName,getContext(),key,defaultValue);
    }
    public static String getString(String key,  String defaultValue) {
        return getString(getFileName(getContext()),getContext(),key,defaultValue);
    }
    /********************************************************************************************************************/
    public static boolean setPrefString(String fileName,Context context,  String key,  String value) {
         SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.edit().putString(key, value).commit();
    }
    public static boolean setPrefString(Context context,  String key,  String value) {
        return setPrefString(getFileName(context),context,key,value);
    }
    public static boolean setPrefString(String fileName,  String key,  String value) {
        return setPrefString(fileName,getContext(),key,value);
    }
    public static boolean setPrefString( String key,  String value) {
        return setPrefString(getFileName(getContext()),getContext(),key,value);
    }
    /********************************************************************************************************************/
    public static boolean getBoolean(String fileName,Context context,  String key,  boolean defaultValue) {
         SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }
    public static boolean getBoolean(Context context,  String key,  boolean defaultValue) {
        return getBoolean(getFileName(context),context,key,defaultValue);
    }
    public static boolean getBoolean(String fileName,  String key,  boolean defaultValue) {
        return getBoolean(fileName,getContext(),key,defaultValue);
    }
    public static boolean getBoolean( String key,  boolean defaultValue) {
        return getBoolean(getFileName(getContext()),getContext(),key,defaultValue);
    }
    /********************************************************************************************************************/
    public static boolean hasKey(String fileName,Context context,  String key) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
                .contains(key);
    }
    public static boolean hasKey(Context context,  String key) {
        return hasKey(getFileName(context),context,key);
    }
    public static boolean hasKey(String fileName,  String key) {
        return hasKey(fileName,getContext(),key);
    }
    public static boolean hasKey(String key) {
        return hasKey(getFileName(getContext()),getContext(),key);
    }
    /********************************************************************************************************************/
    public static boolean setPrefBoolean(String fileName,Context context,  String key,  boolean value) {
         SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.edit().putBoolean(key, value).commit();
    }
    public static boolean setPrefBoolean(Context context,  String key,  boolean value) {
        return setPrefBoolean(getFileName(context),context,key,value);
    }
    public static boolean setPrefBoolean(String fileName, String key,  boolean value) {
        return setPrefBoolean(fileName,getContext(),key,value);
    }
    public static boolean setPrefBoolean(String key,  boolean value) {
        return setPrefBoolean(getFileName(getContext()),getContext(),key,value);
    }

    /********************************************************************************************************************/
    public static boolean setPrefInt(String fileName,Context context,  String key,  int value) {
         SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.edit().putInt(key, value).commit();
    }
    public static boolean setPrefInt(Context context,String key,  int value) {
        return setPrefInt(getFileName(context),context,key,value);
    }
    public static boolean setPrefInt(String fileName,  String key,  int value) {
        return setPrefInt(fileName,getContext(),key,value);
    }
    public static boolean setPrefInt(String key,  int value) {
        return setPrefInt(getFileName(getContext()),getContext(),key,value);
    }
    /********************************************************************************************************************/
    public static int getInt(String fileName,Context context,  String key,  int defaultValue) {
         SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }
    public static int getInt( Context context,  String key,  int defaultValue) {
        return getInt(getFileName(context),context,key,defaultValue);
    }
    public static int getInt(String fileName,String key,  int defaultValue) {
        return getInt(fileName,getContext(),key,defaultValue);
    }
    public static int getInt(  String key,  int defaultValue) {
        return getInt(getFileName(getContext()),getContext(),key,defaultValue);
    }
    /********************************************************************************************************************/
    public static boolean setPrefFloat(String fileName,Context context,  String key,  float value) {
         SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.edit().putFloat(key, value).commit();
    }
    public static boolean setPrefFloat(Context context,  String key,  float value) {
        return setPrefFloat(getFileName(context),context,key,value);
    }
    public static boolean setPrefFloat(String fileName, String key,  float value) {
        return setPrefFloat(fileName,getContext(),key,value);
    }
    public static boolean setPrefFloat(String key,  float value) {
        return setPrefFloat(getFileName(getContext()),getContext(),key,value);
    }
    /********************************************************************************************************************/
    public static float getFloat(String fileName,Context context,  String key,  float defaultValue) {
         SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }
    public static float getFloat(Context context,  String key,  float defaultValue) {
        return getFloat(getFileName(context),context,key,defaultValue);
    }
    public static float getFloat(String fileName, String key,  float defaultValue) {
        return getFloat(fileName,getContext(),key,defaultValue);
    }
    public static float getFloat( String key,  float defaultValue) {
        return getFloat(getFileName(getContext()),getContext(),key,defaultValue);
    }
    /********************************************************************************************************************/
    public static boolean setPreLong(String fileName,Context context,  String key,  long value) {
         SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return  settings.edit().putLong(key, value).commit();
    }
    public static boolean setPreLong(Context context,  String key,  long value) {
        return  setPreLong(getFileName(context),context,key,value);
    }
    public static boolean setPreLong(String fileName,String key,  long value) {
        return  setPreLong(fileName,getContext(),key,value);
    }
    public static boolean setPreLong(String key,long value) {
        return  setPreLong(getFileName(getContext()),getContext(),key,value);
    }

    /********************************************************************************************************************/
    public static long getLong(String fileName,Context context,  String key,  long defaultValue) {
         SharedPreferences settings = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }
    public static long getLong(Context context,  String key,  long defaultValue) {
        return getLong(getFileName(context),context,key,defaultValue);
    }
    public static long getLong(String fileName,String key,  long defaultValue) {
        return getLong(fileName,getContext(),key,defaultValue);
    }
    public static long getLong( String key,  long defaultValue) {
        return getLong(getFileName(getContext()),getContext(),key,defaultValue);
    }
    /********************************************************************************************************************/
    public static boolean clearPreference(String fileName,Context context) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = settings.edit();
        edit.clear();
        return edit.commit();
    }
    public static boolean clearPreference(Context context) {
        return clearPreference(getFileName(context),context);
    }
    public static boolean clearPreference(String fileName) {
        return clearPreference(fileName,getContext());
    }
    public static boolean clearPreference() {
        return clearPreference(getFileName(getContext()),getContext());
    }
    /********************************************************************************************************************/
    public static boolean removeKey(String fileName,Context context,String key) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = settings.edit();
        edit.remove(key);
        return edit.commit();
    }
    public static boolean removeKey(Context context,String key) {
        return removeKey(getFileName(context),context,key);
    }
    public static boolean removeKey(String fileName,String key) {
        return removeKey(fileName,getContext(),key);
    }
    public static boolean removeKey(String key) {
        return removeKey(getFileName(getContext()),getContext(),key);
    }
    /********************************************************************************************************************/
}
