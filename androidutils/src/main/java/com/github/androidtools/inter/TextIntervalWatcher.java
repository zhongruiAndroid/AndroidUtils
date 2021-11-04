package com.github.androidtools.inter;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/***
 *   created by zhongrui on 2018/9/19
 */
public abstract class TextIntervalWatcher implements TextWatcher {
    public abstract void afterTextIntervalChanged(boolean haveInterval, Editable s);
    private Handler handler;
    private boolean isFirst=true;
    private Runnable runnable;
    private Editable lastStr;
    private Editable currentStr;
    private long timeInterval=800;
    public TextIntervalWatcher() {
        this(800);
    }
    public TextIntervalWatcher(int interval) {
        timeInterval=interval;
        handler=new Handler(Looper.getMainLooper());
        runnable=new Runnable() {
            @Override
            public void run() {
                if(isFirst||!TextUtils.equals(currentStr,lastStr)){
                    lastStr=currentStr;
                    isFirst=false;
                    //有间隔，后续变化的文本
                    afterTextIntervalChanged(true,lastStr);
                }
            }
        };
    }
    @Override
    public void afterTextChanged(final Editable s) {
        //一开始改变的文本内容
        afterTextIntervalChanged(false,s);
        currentStr=s;
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,timeInterval);
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}
