package com.github.androidtools.rx;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Administrator on 2017/6/26.
 */

public class RxBus {
    private static volatile RxBus rxBusInstance;
    private final Subject<Object, Object> bus;
    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    public RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
        mStickyEventMap = new ConcurrentHashMap<>();
    }
    // 单例RxBus
    public static RxBus getInstance() {
        if (rxBusInstance == null) {
            synchronized (RxBus.class) {
                if (rxBusInstance == null) {
                    rxBusInstance = new RxBus();
                }
            }
        }
        return rxBusInstance;
    }
    // 发送一个新的事件
    public void post(Object event) {
        bus.onNext(event);
    }
    public  <T> Observable<T> getEvent (Class<T> eventType) {
        return toObservable(eventType);
    }
    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public  <T> Observable<T> toObservable (Class<T> eventType) {
        return bus.ofType(eventType);
    }
    public  <T> Subscription getEvent(Class<T> eventType, MySubscriber sub){
        return RxBus.getInstance().toObservable(eventType).subscribe(sub);
    }

    /***************************************支持Sticky************************************************/
    private final Map<Class<?>, Object> mStickyEventMap;
    /**
     * 发送一个新Sticky事件
     */
    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
        post(event);
    }
    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservableSticky(final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Observable<T> observable = bus.ofType(eventType);
            final Object event = mStickyEventMap.get(eventType);
            if (event != null) {
                return observable.mergeWith(Observable.create(new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        subscriber.onNext(eventType.cast(event));
                    }
                }));
            } else {
                return observable;
            }
        }
    }
    /**根据eventType获取Sticky事件*/
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }
    /** 移除指定eventType的Sticky事件*/
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }
    /*** 移除所有的Sticky事件*/
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }
    /***************************************支持Sticky************************************************/
}
