package com.github.test;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.androidtools.AppUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        startActivity(new Intent(this,Main2Activity.class));
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                    boolean flag = isApplicationInBackground(MainActivity.this);
//                    boolean flag = AppUtils.isAppInBackgroundInternal(MainActivity.this);
                    Log.i("=================", "=================" + flag);
                    SystemClock.sleep(1000);
            }
        }) {
        }.start();
    }

    public   boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            Log.i("=================","================="+topActivity.getPackageName());
            if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
