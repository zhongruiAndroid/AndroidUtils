package com.github.androidtools;

import android.util.SparseLongArray;
import android.view.View;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/8/10.
 */
public class ClickUtils {
    private static SparseLongArray sLastClickTime;
    private static final int MIN_CLICK_DELAY_TIME = 900;
    public synchronized static boolean isFastClick(View view){
        return isFastClick(view,MIN_CLICK_DELAY_TIME);
    }
    public synchronized static boolean isFastClick(View view,int time){
        if(sLastClickTime==null){
            sLastClickTime=new SparseLongArray();
        }
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if(currentTime-sLastClickTime.get(view.getId())>time){
            sLastClickTime.put(view.getId(),currentTime);
            return false;
        }
        return true;
    }

    public synchronized static boolean isFastClickById(int itemId){
        return isFastClickById(itemId,MIN_CLICK_DELAY_TIME);
    }
    public synchronized static boolean isFastClickById(int itemId,int time){
        if(sLastClickTime==null){
            synchronized (ClickUtils.class){
                if(sLastClickTime==null){
                    sLastClickTime=new SparseLongArray();
                }
            }
        }
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if(currentTime-sLastClickTime.get(itemId)>time){
            sLastClickTime.put(itemId,currentTime);
            return false;
        }
        return true;
    }

    public static void clearLastClickTime(){
        sLastClickTime=null;
    }
}
