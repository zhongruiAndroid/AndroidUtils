package com.github.androidtools.inter;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/***
 *   created by zhongrui on 2018/9/19
 */
public abstract class MyTextWatcher implements TextWatcher {
    public abstract void  myAfterTextChanged(boolean haveInterval,Editable s);
    private Handler handler;
    private boolean isFirst=true;
    private Runnable runnable;
    private Editable lastStr;
    private Editable currentStr;
    public MyTextWatcher() {
        handler=new Handler(Looper.getMainLooper());
        runnable=new Runnable() {
            @Override
            public void run() {
                if(isFirst||!TextUtils.equals(currentStr,lastStr)){
                    lastStr=currentStr;
                    isFirst=false;
                    myAfterTextChanged(true,lastStr);
                }
            }
        };
    }
    @Override
    public void afterTextChanged(final Editable s) {
        myAfterTextChanged(false,s);
        currentStr=s;
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,800);
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}
