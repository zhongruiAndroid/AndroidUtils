package com.base.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.base.R;


/**
 *  进入页面加载的Dialog
 */
public class Loading extends Dialog {
    public static int showTag = 0;
    private static Loading loading;
    private static Context context;
    private static boolean isExit;
    public Loading(Context context, int layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        this.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ((Activity) context).getWindowManager()
                .getDefaultDisplay().getWidth() * 3 / 4;
        params.gravity = Gravity.CENTER;
        window.setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(android.R.color.transparent)));
        window.setAttributes(params);
    }

    private static void setLoading(Context ctx) {
        context = ctx;
        loading = new Loading(context, R.layout.loading_wheel, R.style.Theme_dialog);
        loading.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                loading.showTag = 0;
                isExit = false;
            }
        });
    }
    public  static void showForExit(Context ctx) {
        showForExit(ctx,true);
    }
    public  static void showForExit(Context ctx,boolean exit) {
        if(loading==null||!loading.isShowing()){
            isExit=exit;
            setLoading(ctx);
        }
        if (Loading.showTag == 0 && loading != null) {
            Activity activity = (Activity) ctx;
            if (activity != null && !activity.isFinishing()) {
                Loading.showTag = 1;
                loading.show();
            } else {
                loading.showTag = 0;
            }
        }
    }
    public  static void show(Context ctx) {
        if(loading==null||!loading.isShowing()){
            setLoading(ctx);
        }
        if (Loading.showTag == 0 && loading != null) {
            Activity activity = (Activity) ctx;
            if (activity != null && !activity.isFinishing()) {
                Loading.showTag = 1;
                loading.show();
            } else {
                loading.showTag = 0;
            }
        }
    }
    public static void dismissLoading() {
        if (loading != null &&loading.isShowing()) {
            loading.showTag = 0;
            loading.dismiss();
            loading=null;
            context = null;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(isExit&&context!=null&&loading.isShowing()){
            isExit=false;
            loading.dismiss();
            ((Activity)context).finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}