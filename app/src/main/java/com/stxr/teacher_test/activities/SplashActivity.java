package com.stxr.teacher_test.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.admin.AdminActivity;
import com.stxr.teacher_test.entities.Student;
import com.stxr.teacher_test.utils.StudentUtil;

/**
 * Created by stxr on 2018/3/31.
 * 初始界面
 */

public class SplashActivity extends Activity {
    public static final int WHAT = 12;
    public static final int DELAY_MILLIS = 1500;
    public static final String TAG = "SplashActivity";
    private boolean flag = true;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT) {
                //判断跳转
                if (Student.getCurrentUser(SplashActivity.this) != null) {
                    StudentUtil.get().setStudent(Student.getCurrentUser(SplashActivity.this));
                    StudentUtil.get().setOnCallBack(new StudentUtil.CallBack() {
                        @Override
                        public void onSuccess(StudentUtil studentUtil, boolean isSuccess) {
                            if (isSuccess && flag) {
                                flag = false;
                                startActivity(StudentActivity.newInstance(SplashActivity.this));
                                finish();
                            } else {
                                Log.e(TAG, "isSuccess: " + isSuccess + "flag: " + flag);
                            }
                        }
                    });
                } else {
                    startActivity(AdminActivity.newInstance(SplashActivity.this));
                    finish();
                }
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
        handler.sendEmptyMessageDelayed(WHAT, DELAY_MILLIS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = true;
    }
}
