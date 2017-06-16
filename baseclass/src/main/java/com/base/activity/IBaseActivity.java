package com.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.base.adapter.CommonAdapter;
import com.base.view.Loading;
import com.base.view.MyDialog;
import com.base.view.MyPopupwindow;
import com.github.tools.ToastUtils;
import com.github.tools.rx.IOCallBack;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/6/16.
 */
public class IBaseActivity extends AppCompatActivity {
    protected Activity mContext;
    protected CommonAdapter mAdapter;
    protected MyPopupwindow mPopupwindow;
    protected MyDialog.Builder mDialog;

    private CompositeSubscription mCSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        RxBus.get().register(this);
        mContext = this;
    }

    protected Intent getmIntent() {
        return new Intent();
    }

    protected View inflateView(int resource, ViewGroup viewGroup) {
        return LayoutInflater.from(this).inflate(resource, viewGroup);
    }

    protected View inflateView(int resource) {
        return inflateView(resource, null);
    }

    protected void showToastS(String toast) {
        ToastUtils.showToast(this, toast);
    }

    protected void showToastL(String toast) {
        ToastUtils.showToast(this, toast, Toast.LENGTH_LONG);
    }

    protected void STActivityForResult(Class clazz, int requestCode) {
        startActivityForResult(new Intent(this, clazz), requestCode);
    }

    protected void STActivityForResult(Intent intent, Class clazz, int requestCode) {
        intent.setClass(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    protected void STActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    protected void STActivity(Intent intent, Class clazz) {
        intent.setClass(this, clazz);
        startActivity(intent);

    }

    protected void addFragment(int resId, Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().add(resId, fragment).commitAllowingStateLoss();
        }
    }

    protected void replaceFragment(int resId, Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(resId, fragment).commitAllowingStateLoss();
        }
    }

    protected void hideFragment(Fragment fragment) {
        if (fragment != null && !fragment.isHidden()) {
            getSupportFragmentManager().beginTransaction().hide(fragment).commitAllowingStateLoss();
        }
    }

    protected void showFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().show(fragment).commitAllowingStateLoss();
        }
    }

    protected void showLoading(boolean isExit) {
        Loading.showForExit(this, isExit);
    }

    protected void showLoading() {
        Loading.show(this);
    }

    protected void dismissLoading() {
        Loading.dismissLoading();
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

    /****************************************************************************/
    protected <T> void RXStart(final IOCallBack<T> callBack) {
        Subscription subscribe = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                callBack.call(subscriber);
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {
                callBack.onMyCompleted();
            }
            @Override
            public void onError(Throwable e) {
                callBack.onMyError(e);
            }
            @Override
            public void onNext(T t) {
                callBack.onMyNext(t);
            }
        });
        addSubscription(subscribe);
    }
    protected <T> void RXStart2(final IOCallBack<T> callBack) {
        Subscription subscribe = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                callBack.call(subscriber);
            }
        })
        .asObservable()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {
                callBack.onMyCompleted();
            }
            @Override
            public void onError(Throwable e) {
                callBack.onMyError(e);
            }
            @Override
            public void onNext(T t) {
                callBack.onMyNext(t);
            }
        });
        addSubscription(subscribe);
    }
    /****************************************************************************/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContext = null;
        onUnSubscription();
//        RxBus.get().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ToastUtils.cancelToast();
    }


}
