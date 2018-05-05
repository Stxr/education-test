package com.stxr.teacher_test.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stxr.teacher_test.admin.SingleFragmentActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by stxr on 2018/5/3.
 */

public abstract class SingleBaseFragment extends BaseFragment {
    protected SingleFragmentActivity activity;
    protected abstract String title();
    @Override
    public void onAttach(Context context) {
        activity = (SingleFragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setTitle();
        return rootView;
    }

    protected void initData(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    }

    protected void setTitle() {
        if (activity.getSupportActionBar() != null && title() != null) {
            activity.getSupportActionBar().setTitle(title());
        }
    }

}
