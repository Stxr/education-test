package com.stxr.teacher_test.fragments.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.admin.AdminActivity;
import com.stxr.teacher_test.entities.Student;
import com.stxr.teacher_test.fragments.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * 用户管理Fragment
 */
public class AccountFragment extends BaseFragment {

    @Override
    protected int layoutResId() {
        return R.layout.fragment_account;
    }


    @OnClick(R.id.btn_quit)
    void onClick() {
        Student.logOut(getActivity());
       startActivity( AdminActivity.newInstance(getActivity()));
        getActivity().finish();
    }
}
