package com.base;

import android.content.Context;
import android.content.res.Resources;

import com.base.view.MyDialog;
import com.base.view.MyPopupwindow;
import com.github.androidtools.rx.IOCallBack;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/8/16.
 */
public abstract class IPresenter<V extends BaseView>{
    protected V mView;
    protected Context mContext;
    private CompositeSubscription mCSubscription;
    protected MyPopupwindow mPopupwindow;
    protected MyDialog.Builder mDialog;
    public IPresenter(Context context) {
        mContext=context;
    }
    public void attach(V view){
        if(mView==null){
            mView=view;
        }
    }
    public void detach(){
        onUnSubscription();
        mView=null;
        mContext=null;
    }
    protected Resources getResources(){
        return mContext.getResources();
    }
    protected String getSStr(int resId){
        return mContext==null?"":mContext.getResources().getString(resId);
    }
    //RXjava注册
    protected void addSubscription(Subscription subscription) {
        if (mCSubscription == null) {
            mCSubscription = new CompositeSubscription();
        }
        mCSubscription.add(subscription);
    }
    //RXjava取消注册，以避免内存泄露
    protected void onUnSubscription() {
        if (mCSubscription != null && mCSubscription.hasSubscriptions()) {
            mCSubscription.unsubscribe();
        }
    }
    protected void RXStart(IOCallBack callBack){
        mView.RXStart(callBack);
    }
    protected void RXStart2(IOCallBack callBack){
        mView.RXStart2(callBack);
    }
}
