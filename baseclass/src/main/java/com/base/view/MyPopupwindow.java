package com.base.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
    private Drawable backgroundColor;
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
        setBackground();
        setOutsideTouchable(true);
        setFocusable(true);
        setContentView(contentView);
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
        setOnDismissListener(getOnDismissListener());
    }

    public void setBackgroundColor(int color){
        backgroundColor=new ColorDrawable(context.getResources().getColor(color));
    }
    public void setBackgroundColor(Drawable color){
        backgroundColor=color;
    }
    private void setBackground(){
        if(backgroundColor==null){
            backgroundColor= new ColorDrawable(0x000000);
        }
        this.setBackgroundDrawable(backgroundColor);
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


}
