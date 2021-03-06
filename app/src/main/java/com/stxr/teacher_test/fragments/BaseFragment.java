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
 * Created by stxr on 2018/5/5.
 */

public abstract class BaseFragment extends Fragment {
    protected View rootView;
    private Unbinder unbinder;
    protected String TAG = getClass().getSimpleName();

    protected abstract @LayoutRes
    int layoutResId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(layoutResId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        initData(inflater, container, savedInstanceState);
        return rootView;
    }

    protected void initData(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }


    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
