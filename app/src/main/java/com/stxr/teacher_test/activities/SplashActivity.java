package com.stxr.teacher_test.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by stxr on 2018/3/31.
 * 初始界面
 */

public class SplashActivity extends Activity {
    public static final int WHAT = 12;
    public static final int DELAY_MILLIS = 1500;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT) {
                //判断跳转
                if (BmobUser.getCurrentUser(MyUser.class) != null) {
                    MainActivity.newInstance(SplashActivity.this);
                } else {
                    SignInActivity.newInstance(SplashActivity.this);
                }
                finish();
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(WHAT, DELAY_MILLIS);
    }
}
