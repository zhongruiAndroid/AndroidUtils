package com.github.androidtools;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import java.util.List;

/***
 *   created by android on 2019/4/1
 */
public class AppUtils {
    public static boolean isAppInBackgroundInternal(Context context) {
        if(context==null){
            return false;
        }
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> task = manager.getRunningTasks(1);
        if (notEmpty(task)) {
            ComponentName info = task.get(0).topActivity;
            if (null != info) {
                return !context.getPackageName().equals(info.getPackageName());
            }
        }
        /*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = manager.getRunningAppProcesses();
            if (notEmpty(runningProcesses)) {
                for (ActivityManager.RunningAppProcessInfo runningProcess : runningProcesses) {
                    if (runningProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return false;
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> task = manager.getRunningTasks(1);
            if (notEmpty(task)) {
                ComponentName info = task.get(0).topActivity;
                if (null != info) {
                    return !context.getPackageName().equals(info.getPackageName());
                }
            }
        }*/
        return true;
    }

    private static boolean isEmpty(List list){
        if(list==null||list.size()==0){
            return true;
        }
        return false;
    }
    private static boolean notEmpty(List list){
        return !isEmpty(list);
    }
}
