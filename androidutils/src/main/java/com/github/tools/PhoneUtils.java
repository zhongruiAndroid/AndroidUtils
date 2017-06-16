package com.github.tools;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2016/9/6.
 */
public class PhoneUtils {
    public static int getPhoneWidth(Context context) {
        Point point = new Point();
        ((Activity)context).getWindowManager().getDefaultDisplay().getSize(point);
        return point.x;
    }
    public static int getPhoneHeight(Context context) {
        Point point = new Point();
        ((Activity)context).getWindowManager().getDefaultDisplay().getSize(point);
        return point.y;
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    public static void copyText(Context context,String text){
        ClipboardManager cm = (ClipboardManager)(context).getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setPrimaryClip(ClipData.newPlainText(null,text));
    }
    public static String pasteText(Context context){
        ClipboardManager cm = (ClipboardManager)(context).getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm.hasPrimaryClip()){
            return cm.getPrimaryClip().getItemAt(0).getText().toString();
        }
        return null;
    }

    /**
     * 跳转到手机系统权限管理界面
     * @param context
     */
    public static void PermissionsSetting(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

    /**
     * 推荐使用
     * @param context
     * @param view
     */
    public static void showKeyBoard(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
    }

    /**
     * 推荐使用
     * @param activity
     */
    public static void hiddenKeyBoard(Activity activity){
        ((InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0);
    }
    /**
     * 只要不是通过这个方法显示出来键盘，就无法用这个方法隐藏
     * @param context
     */
    @Deprecated
    public static void showOrHiddenKeyBoard(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public static void hiddenKeyBoard(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    public static boolean keyBoardIsOpen(Activity activity) {
        //获取当前屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int navigationBarHeight=0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            navigationBarHeight = getNavigationBarHeight(activity);
        }
        return screenHeight - rect.bottom-navigationBarHeight != 0;
    }

    public static int getNavigationBarHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度(可能不包含底部导航栏高度)
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        }
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }


}
