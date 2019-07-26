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
import java.io.FileOutputStream;
import java.io.IOException;
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
    private static String localId;


    public void getLocalId(Context context,ReadDataListener listener) {
          getLocalId(context, null,listener);
    }
    private void listenerResult(String id,ReadDataListener listener) {
        if (listener != null) {
            listener.result(id);
        }
    }
    public void getLocalId(Context context, String wantSaveId,ReadDataListener listener) {
        if (TextUtils.isEmpty(localId) == false) {
            //1:如果内存里面存在唯一标识就返回
            listenerResult( localId,listener);
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyLocalDeviceId", Context.MODE_PRIVATE);
        String xmlDeviceId = sharedPreferences.getString("deviceId", null);
        if (TextUtils.isEmpty(xmlDeviceId) == false) {
            //2:如果缓存里面存在唯一标识就返回
            listenerResult(saveLocalIdToMemory(xmlDeviceId),listener);
            saveLocalIdToFile(context,wantSaveId);
            return;
        }
        int selfPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (selfPermission == PackageManager.PERMISSION_GRANTED) {
            //3:如果本地目录里面存在唯一标识就返回
            String parentFile = defaultParentPath;
            String fileName = defaultFileName;
            if (fileIsExist(parentFile, fileName)) {
                readFileData(new File(parentFile, fileName),wantSaveId,listener);
                /*FileInputStream fis = null;
                try {
                    fis = new FileInputStream(new File(parentFile, fileName));
                    byte[] txt = new byte[fis.available()];
                    String uuid = new String(txt);
                    saveLocalIdToXml(context, uuid);
                    return uuid;
                } catch (Exception e) {
                    e.printStackTrace();
                    String uuid = TextUtils.isEmpty(wantSaveId) ? UUID.randomUUID().toString() : wantSaveId;
                    saveLocalIdToXml(context, uuid);
                    return uuid;
                }finally {
                    if(fis!=null){
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }*/
            } else {
                String uuid = TextUtils.isEmpty(wantSaveId) ? UUID.randomUUID().toString() : wantSaveId;
                saveLocalIdToXml(context, uuid);
                listenerResult(uuid,listener);
                saveLocalIdToFile(context,uuid);
            }
        } else {
            String uuid = TextUtils.isEmpty(wantSaveId) ? UUID.randomUUID().toString() : wantSaveId;
            saveLocalIdToXml(context, uuid);
            listenerResult(uuid,listener);
            saveLocalIdToFile(context,uuid);
        }
    }

    private String saveLocalIdToMemory(String id) {
        localId = id;
        return localId;
    }

    public void saveLocalIdToXml(Context context, String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyLocalDeviceId", Context.MODE_PRIVATE);
        boolean deviceId = sharedPreferences.edit().putString("deviceId", id).commit();
        if (deviceId) {
            localId = id;
        }
    }

    public void saveLocalIdToFile(Context context, String id) {
        int selfPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (selfPermission == PackageManager.PERMISSION_GRANTED) {
            writeFileData(new File(defaultParentPath,defaultFileName),id);
        }
    }
    public boolean fileIsExist(String parentPath, String fileName) {
        File file = new File(parentPath, fileName);
        return file.exists();
    }
    public interface ReadDataListener {
        void result(String localId);
    }
    private void readFileData(final File file,final String wantSaveId,final ReadDataListener listener) {
        AsyncTask asyncTask = new AsyncTask<File, Void, String>() {
            @Override
            protected String doInBackground(File... files) {
                FileInputStream fis = null;
                String deviceId;
                try {
                    fis = new FileInputStream(file);
                    byte[] txt = new byte[fis.available()];
                    deviceId = new String(txt);
                } catch (Exception e) {
                    e.printStackTrace();
                    deviceId= TextUtils.isEmpty(wantSaveId) ? UUID.randomUUID().toString() : wantSaveId;
                }finally {
                    if(fis!=null){
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return deviceId;
            }
            @Override
            protected void onPostExecute(String result) {
                if (listener != null) {
                    listener.result(result);
                }
            }
        };
        asyncTask.execute(file);
    }

    private void writeFileData(File file,final String wantSaveId) {
        AsyncTask asyncTask = new AsyncTask<File, Void, String>() {
            @Override
            protected String doInBackground(File... files) {
                FileOutputStream fos = null;
                try {
                    /* 判断sd的外部设置状态是否可以读写 */
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        // 先清空内容再写入
                        fos = new FileOutputStream(files[0]);
                        byte[] buffer = wantSaveId.getBytes();
                        fos.write(buffer);
                        fos.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
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
