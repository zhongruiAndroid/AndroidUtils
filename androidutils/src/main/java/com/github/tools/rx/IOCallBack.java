package com.github.tools.rx;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/6/16.
 */
public abstract class IOCallBack<T> {
    public abstract void call(Subscriber<? super T> subscriber);
    public abstract void onMyNext(T obj);
    public void onMyCompleted(){
    };
    public void onMyError(Throwable e){
    };
}
