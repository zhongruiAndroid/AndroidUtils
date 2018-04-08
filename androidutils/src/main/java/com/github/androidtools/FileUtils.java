package com.github.androidtools;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/12/12.
 */
public class FileUtils {

    /***
     * 复制单个文件
     *
     * @param oldPath 文件路径+文件名
     * @param newPath 文件路径+文件名
     * @return -1复制出错，0文件不存在，1复制成功
     */
    public static int copyFile(String oldPath, String newPath) {
        if (!new File(oldPath).exists()) {
            return 0;//文件不存在
        }
        InputStream is = null;
        try {
            is = new FileInputStream(oldPath); //读入原文件
            int len = 0;
            byte buffer[] = new byte[1024];
            FileOutputStream fos = new FileOutputStream(newPath);
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            fos.close();
            return 1;//复制成功
        } catch (Exception e) {
            e.printStackTrace();
            return -1;//复制文件出错
        }
    }

    /***
     * 复制指定目录下的所有文件及文件夹
     *
     * @param fromFile
     * @param toFile
     * @return
     */
    public static int copy(String fromFile, String toFile) {
        //要复制的文件目录
        File[] currentFiles;
        File root = new File(fromFile);
        //如同判断SD卡是否存在或者文件是否存在
        //如果不存在则 return出去
        if (!root.exists()) {
            return -1;
        }
        //如果存在则获取当前目录下的全部文件 填充数组
        currentFiles = root.listFiles();
        //目标目录
        File targetDir = new File(toFile);
        //创建目录
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        //遍历要复制该目录下的全部文件
        for (int i = 0; i < currentFiles.length; i++) {
            if (currentFiles[i].isDirectory())//如果当前项为子目录 进行递归
            {
                copy(currentFiles[i].getPath() + "/", toFile + currentFiles[i].getName() + "/");

            } else//如果当前项为文件则进行文件拷贝
            {
                copyNoChild(currentFiles[i].getPath(), toFile + currentFiles[i].getName());
            }
        }
        return 1;
    }

    /***
     * 文件拷贝-要复制的目录下的所有[非子目录(文件夹)]文件拷贝
     *
     * @param fromFile
     * @param toFile
     * @return
     */
    public static int copyNoChild(String fromFile, String toFile) {
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 1;

        } catch (Exception ex) {
            return -1;
        }
    }

    /**
     * 将Assets下的文件复制到指定目录下
     *
     * @param context
     * @param fileName
     * @param filePath
     */
    public static void copyFileForAssets(Context context, String fileName, File filePath) {
        AssetManager assets = context.getAssets();
        InputStream is = null;
        try {
            is = assets.open(fileName);
            int len = 0;
            byte buffer[] = new byte[1024];
            FileOutputStream fos = new FileOutputStream(filePath);
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将Assets下的文件复制到指定目录下
     *
     * @param context
     * @param fileName
     * @param filePath
     */
    public static void copyFileForAssets(Context context, String fileName, String filePath) {
        copyFileForAssets(context, fileName, new File(filePath));
    }


    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param file 要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(File file) {
        String fileName=file.getAbsolutePath();
        if (!file.exists()) {
            Log.i("===","删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        return delete(file);
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.i("===","删除单个文件" + fileName + "成功！");
                return true;
            } else {
                Log.i("===","删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            Log.i("===","删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            Log.i("===","删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            Log.i("===","删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            Log.i("===","删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }


}
