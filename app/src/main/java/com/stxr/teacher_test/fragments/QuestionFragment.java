package com.stxr.teacher_test.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stxr.teacher_test.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stxr on 2018/3/31.
 */

public class QuestionFragment extends Fragment {
    @BindView(R.id.tv_question)
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, null);
        ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        String text = arguments.getString("text");
        textView.setText(text);

        return view;
    }



    public static QuestionFragment newInstance(String text) {
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
