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
        Bmob.initialize(this, "673e63d24c26f34e71a10df0ef5d5074");
    }
}
