package com.github.test;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/***
 *   created by android on 2019/4/1
 */
public class App extends Application implements Application.ActivityLifecycleCallbacks{

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.i("===============","===============onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.i("===============","===============onActivityStarted");

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.i("===============","===============onActivityResumed");

    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.i("===============","===============onActivityPaused");

    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.i("===============","===============onActivityStopped");

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.i("===============","===============onActivitySaveInstanceState");

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.i("===============","===============onActivityDestroyed");

    }
}
