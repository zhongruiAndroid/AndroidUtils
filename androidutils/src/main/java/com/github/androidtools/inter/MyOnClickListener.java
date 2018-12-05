package com.github.androidtools.inter;

import android.view.View;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/8/10.
 */
public abstract class MyOnClickListener implements View.OnClickListener{
    private static int MIN_CLICK_DELAY_TIME = 900;
    private long lastClickTime = 0;

    public MyOnClickListener() {
        this(900);
    }
    public MyOnClickListener(int interval) {
        this.MIN_CLICK_DELAY_TIME=interval;
    }
    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }
    protected abstract void onNoDoubleClick(View v);
}
