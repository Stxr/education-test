package com.stxr.teacher_test.application;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;


/**
 * Created by stxr on 2018/3/27.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "a9adce08cbd4f55b2b583f4779076ff6");
    }
}
