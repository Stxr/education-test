package com.stxr.teacher_test.fragments.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stxr.teacher_test.R;

/**
 * Created by stxr on 2018/3/27.
 */

public class ExamFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载布局
        View view = inflater.inflate(R.layout.fragment_exam, container, false);
        return view;
    }
}
