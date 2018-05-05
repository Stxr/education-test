package com.stxr.teacher_test.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.stxr.teacher_test.entities.Admin;
import com.stxr.teacher_test.entities.Student;
import com.stxr.teacher_test.fragments.SignInFragment;

import cn.bmob.v3.BmobUser;

/**
 * Created by stxr on 2018/5/3.
 */

public  class AdminActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        if (BmobUser.getCurrentUser(Admin.class) != null) {
            return new AdminFragment();
        }
        return SignInFragment.newInstance(AccountType.ADMINISTRATOR);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, AdminActivity.class);
        return intent;
    }
}
