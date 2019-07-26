package com.github.androidtools;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

/***
 *   created by android on 2019/7/26
 */
public class LocalDeviceId {
    private static LocalDeviceId localDeviceId;
    private LocalDeviceId() {
    }
    public static LocalDeviceId get() {
        if (localDeviceId == null) {
            synchronized (LocalDeviceId.class) {
                if (localDeviceId == null) {
                    localDeviceId = new LocalDeviceId();
                }
            }
        }
        return localDeviceId;
    }


    public static final String defaultParentPath = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/Android/";
    public static final String defaultFileName = "system_temp.txt";
    public static String localId;


    public String getLocalId(Context context) {
        return getLocalId(context,null);
    }
    public String getLocalId(Context context, String wantSaveId) {

        if(TextUtils.isEmpty(localId)==false){
            //1:如果内存里面存在唯一标识就返回
            return localId;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyLocalDeviceId", Context.MODE_PRIVATE);
        String xmlDeviceId = sharedPreferences.getString("deviceId", null);
        if(TextUtils.isEmpty(xmlDeviceId)==false){
            //2:如果缓存里面存在唯一标识就返回
            return saveLocalIdToMemory(xmlDeviceId);
        }
        int selfPermission =  ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(selfPermission==PackageManager.PERMISSION_GRANTED){
            //3:如果本地目录里面存在唯一标识就返回
            String parentFile=defaultParentPath;
            String fileName=defaultFileName;
            if(fileIsExist(parentFile,fileName)){
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(new File(parentFile,fileName));
                    byte[] txt = new byte[fis.available()];
                    String uuid = new String(txt);
                    return uuid;
                } catch (Exception e) {
                    e.printStackTrace();
                    String uuid=TextUtils.isEmpty(wantSaveId)?UUID.randomUUID().toString():wantSaveId;
                    saveLocalIdToXml(context,uuid);
                    return uuid;
                }
            }
        }else{

        }
        return null;
    }

    private String saveLocalIdToMemory(String id){
        localId=id;
        return localId;
    }
    private void saveLocalIdToXml(Context context, String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyLocalDeviceId", Context.MODE_PRIVATE);
        boolean deviceId = sharedPreferences.edit().putString("deviceId", id).commit();
        if(deviceId){
            localId=id;
        }
    }


    public String getUUID() {
        if (fileIsExist(null,null) == false) {
            return UUID.randomUUID().toString();
        }
        FileInputStream fis = null;
        try {
            //获取文件长度
            File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/Android/", "system_temp.txt");
            fis = new FileInputStream(file);
            byte[] txt = new byte[fis.available()];
            String uuid = new String(txt);
            return uuid;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return UUID.randomUUID().toString();
    }

    public boolean fileIsExist(String parentPath, String fileName) {
//        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/Android/system_temp.txt");
        File file = new File(parentPath,fileName);
        return file.exists();
    }
    private interface ReadDataListener{
        void result(String data);
    }

    private void readFileData(File file, ReadDataListener listener) {
        AsyncTask asyncTask = new AsyncTask<File,Void,String>(){
            @Override
            protected String doInBackground(File... files) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(files[0]);
                    byte[] txt = new byte[fis.available()];
                    String uuid = new String(txt);
                    return uuid;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(String result) {

            }
        };
        asyncTask.execute(file);
    }
}
