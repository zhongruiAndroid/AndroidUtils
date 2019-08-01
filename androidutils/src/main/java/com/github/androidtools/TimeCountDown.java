package com.github.androidtools;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/***
 *   created by android on 2019/5/6
 */
public class TimeCountDown {
    public interface TimeCallback {
        void onNext(long time);
        void onComplete();
    }

    private static final int msg_what = 6000;
    private Handler handler;
    private long intervalTime=1000;
    private TimeCallback timeCallback;

    private int timeType;
    private final int TIME_TYPE_SECOND=1;
    private final  int TIME_TYPE_MILLIS=2;

    public TimeCountDown() {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg_what != msg.what || timeCallback == null) {
                    return;
                }
                long time = (long)msg.obj;
                sendTime(timeType==TIME_TYPE_MILLIS?time:time/1000, timeCallback);
                time = (long)msg.obj-intervalTime;
                if (time <= 0) {
                    timeCallback.onComplete();
                    return;
                }
                Message message = getMessage(time);
                handler.sendMessageDelayed(message, intervalTime);
            }
        };
    }

    public void start(long timeSecond,TimeCallback timeCallback) {
        this.timeType=TIME_TYPE_SECOND;
        startCountdown(timeSecond*1000,0,1000,timeCallback);
    }

    public void startForSecond(long timeSecond,long delayTimeSecond,long intervalTimeSecond,TimeCallback timeCallback) {
        this.timeType=TIME_TYPE_SECOND;
        startCountdown(timeSecond*1000,delayTimeSecond*1000,intervalTimeSecond*1000,timeCallback);

    }
    public void startForMillis(long timeMillis,long delayTimeMillis,long intervalTimeMillis, TimeCallback timeCallback) {
        this.timeType=TIME_TYPE_MILLIS;
        startCountdown(timeMillis,delayTimeMillis,intervalTimeMillis,timeCallback);
    }
    private void startCountdown(long timeMillis,long delayTimeMillis,long intervalTimeMillis, TimeCallback timeCallback){
        this.intervalTime=intervalTimeMillis;
        this.timeCallback = timeCallback;
        Message message = getMessage(timeMillis);
        handler.sendMessageDelayed(message, delayTimeMillis);
    }

    private Message getMessage(long time) {
        Message obtain = Message.obtain();
        obtain.what = msg_what;
        obtain.obj = time;
        return obtain;
    }

    private void sendTime(long timeSecond, TimeCallback timeCallback) {
        if (timeCallback != null) {
            timeCallback.onNext(timeSecond);
        }
    }

    public void onDestroy() {
        handler.removeMessages(msg_what);
        handler = null;
    }

    public static void onDestroy(TimeCountDown timeCountDown) {
        if (timeCountDown != null) {
            timeCountDown.onDestroy();
        }
    }
}
