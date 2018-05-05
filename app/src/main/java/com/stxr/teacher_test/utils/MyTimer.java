package com.stxr.teacher_test.utils;

import android.os.CountDownTimer;

import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Created by stxr on 2018/5/5.
 */

public class MyTimer extends CountDownTimer{
    private String time;
    private OnTimerCallBack callback;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public MyTimer(int minute, long countDownInterval) {
        super(TimeUnit.MINUTES.toMillis(minute), countDownInterval);
    }

    @Override
    public void onTick(long millis) {
        long hour = millis / 1000 / 3600;
        long minutes = (millis/1000 - hour * 3600) / 60;
        long second = (millis/1000 - minutes *60-hour * 3600);
        time = String.format(Locale.CHINESE, "剩余时间:%d:%d:%d", hour, minutes, second);
        callback.onTick(time);
    }

    @Override
    public void onFinish() {
        callback.onFinish();
    }

    public String getTime() {
        return time;
    }

    public void setOnTimerCallBack(OnTimerCallBack callBack) {
        this.callback = callBack;
    }
    public interface OnTimerCallBack{
        void onTick(String time);

        void onFinish();

    }
}
