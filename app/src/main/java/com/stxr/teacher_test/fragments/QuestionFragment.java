package com.stxr.teacher_test.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stxr on 2018/3/31.
 */

public class QuestionFragment extends Fragment {
    public static final String QUESTION = "text";
    @BindView(R.id.tv_question_title)
    TextView tv_question_title;

    @BindView(R.id.rg_question)
    RadioGroup rb_question;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question, null);
        ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        Question question = (Question) arguments.getSerializable(QUESTION);
        questionAdapter(question);
        return view;
    }

    private void questionAdapter(Question question) {
        tv_question_title.setText(question.getQuestion());
        //获取选项
        Gson gson = new Gson();
        List<Question.Choices> choices = Arrays.asList(question.getChoices());

        //打乱选项顺序，确保每次出现的次序都不一样
        Collections.shuffle(choices);
        for (int i = 0; i < choices.size(); i++) {
            RadioButton button = new RadioButton(getActivity());
            button.setId(i);
            button.setText(choices.get(i).toString());
            rb_question.addView(button);
        }
    }


    public static QuestionFragment newInstance(Question question) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(QUESTION, question);
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
