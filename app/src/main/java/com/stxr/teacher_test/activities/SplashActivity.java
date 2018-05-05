package com.stxr.teacher_test.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.admin.AdminActivity;
import com.stxr.teacher_test.entities.Student;

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
                if (Student.getCurrentUser(SplashActivity.this) != null) {
                    startActivity(StudentActivity.newInstance(SplashActivity.this));
                } else {
                    startActivity(AdminActivity.newInstance(SplashActivity.this));
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
