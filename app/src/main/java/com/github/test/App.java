package com.github.test;

import android.app.Application;
import android.util.Log;

/***
 *   created by android on 2019/4/1
 */
public class App extends Application {

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i("===============","===============level="+level);
        if(level==TRIM_MEMORY_BACKGROUND){
            Log.i("===============","===============TRIM_MEMORY_BACKGROUND");
        }else if(level==TRIM_MEMORY_UI_HIDDEN){
            Log.i("===============","===============UI==Hidden");
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i("===============","===============onLowMemory=");
    }
}
