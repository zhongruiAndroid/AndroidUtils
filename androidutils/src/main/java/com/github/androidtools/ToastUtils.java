package com.github.androidtools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/6/16.
 */
public class ToastUtils {
    private static Toast mToast = null;
    public static void showToast(Context context, String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, duration);
        } else {
            mToast.setText(msg);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void showToast(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
    public static void cancelToast(){
        if(mToast!=null){
            mToast.cancel();
        }
    }
}
