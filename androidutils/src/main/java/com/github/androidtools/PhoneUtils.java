package com.github.androidtools;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.support.v4.math.MathUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/9/6.
 */
public class PhoneUtils {
    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }
    /**
     * 获取屏幕显示指标
     * densityDpi  ===480像素密度
     * density     ===3.0  480/160
     * xdpi        ===422.03
     * ydpi        ===424.069
     * widthPixels ===1080
     * heightPixels===1794
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context){
        return context.getResources().getDisplayMetrics();
    }
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
    public static float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return  (pxValue / scale + 0.5f);
    }

    public static float dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return  (dipValue * scale + 0.5f);
    }
    public static int pxToDip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return  (int)(pxValue / scale + 0.5f);
    }

    public static int dipToPx(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return  (int)(dipValue * scale + 0.5f);
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
        if(activity.getCurrentFocus()==null){
            return;
        }
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

    /**
     * 虚拟按键隐藏之后无法获取高度
     * @param activity
     * @return
     */
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

    /**
     * 对double数据进行取精度.
     * @param value  double数据.
     * @param scale  精度位数(保留的小数位数).
     * @param roundingMode  精度取值方式.
     * @return 精度计算后的数据.
     */
    public static double round(double value, int scale,
                               int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }
    public static double round(double value) {
        return round(value,2,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * double 相加
     * @param d1
     * @param d2
     * @return
     */
    public static double jiaFa(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return round(bd1.add(bd2).doubleValue());
    }


    /**
     * double 相减
     * @param d1
     * @param d2
     * @return
     */
    public static double jianFa(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return round(bd1.subtract(bd2).doubleValue());
    }

    /**
     * double 乘法
     * @param d1
     * @param d2
     * @return
     */
    public static double chengFa(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return round(bd1.multiply(bd2).doubleValue());
    }



    /**
     * double 除法
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return
     */
    public static double chuFa(double d1,double d2,int scale) {
        //  当然在此之前，你要判断分母是否为0，
        //  为0你可以根据实际需求做相应的处理
        try {
            if(d2==0){
                throw new Exception("分母不能为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide
                (bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 将double类型数据转为字符串
     * @param d
     * @return
     */
    public static String double2String(double d){
        BigDecimal bg = new BigDecimal(d * 100);
        double doubleValue = bg.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        return  String.valueOf((int)doubleValue);
    }


}
