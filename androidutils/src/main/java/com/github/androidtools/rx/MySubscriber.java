package com.github.androidtools.rx;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/6/16.
 */
public abstract class MySubscriber<T> extends Subscriber {
    public abstract void onMyNext(T obj);
    public void onMyCompleted(){
    };
    public void onMyError(Throwable e){
    };
    @Override
    public void onCompleted() {
        onMyCompleted();
    }
    @Override
    public void onError(Throwable e) {
        onMyError(e);
    }
    @Override
    public void onNext(Object obj) {
        onMyNext((T)obj);
    }
}
