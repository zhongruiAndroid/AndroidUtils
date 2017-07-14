package com.base.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2016/8/2.
 */
public class MyPopupwindow extends PopupWindow{
    private Context context;
    public MyPopupwindow(Context ctx,int contentView) {
        context=ctx;
        View view = LayoutInflater.from(ctx).inflate(contentView, null);
        setPopupwindow(view);
    }
    public MyPopupwindow(Context ctx,View contentView) {
        context=ctx;
        setPopupwindow(contentView);
    }
    public MyPopupwindow(Context ctx,int contentView,int width,int height) {
        context=ctx;
        View view = LayoutInflater.from(ctx).inflate(contentView, null);
        setPopupwindow(view,width,height);
    }
    public MyPopupwindow(Context ctx,View contentView,int width,int height) {
        context=ctx;
        setPopupwindow(contentView,width,height);
    }
    private void setPopupwindow(View contentView) {
        setPopupwindow(contentView, -1, -1);
    }
    private void setPopupwindow(View contentView,int width,int height) {
        setBackgroundColor();
        setOutsideTouchable(true);
        setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(25);
        }
        if(width==-1){
            setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        }else{
            setWidth(width);
        }
        if(height==-1){
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }else{
            setHeight(height);
        }
        setContentView(contentView);
        setOnDismissListener(getOnDismissListener());
    }
    public void setBackground(int color){
        setBackgroundDrawable(context.getResources().getDrawable(color));
    }
    public void setBackground(String color){
        setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
    }
    public void setBackground(Drawable color){
        setBackgroundDrawable(color);
    }
    private void setBackgroundColor(){
        this.setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));
//        WindowManager.LayoutParams lp=((Activity)context).getWindow().getAttributes();
//        lp.alpha = 0.7f;
//        ((Activity)context).getWindow().setAttributes(lp);
    }
    private void onDismissBackground(){
//        WindowManager.LayoutParams lp= ((Activity)context).getWindow().getAttributes();
//        lp.alpha = 1f;
//        ((Activity)context).getWindow().setAttributes(lp);
    }
    @NonNull
    private OnDismissListener getOnDismissListener() {
        return new OnDismissListener() {
            @Override
            public void onDismiss() {
                onDismissBackground();
            }
        };
    }
    @Override
    public void dismiss() {
        super.dismiss();
        context=null;
    }
}
