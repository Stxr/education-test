package com.stxr.teacher_test.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.Choices;
import com.stxr.teacher_test.entities.Question;

import java.util.Arrays;
import java.util.List;
import java.util.logging.LogManager;

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
    public RadioGroup rb_question;

    @BindView(R.id.tv_description)
    public TextView tv_description;
    private Question question;
    private List<Choices> choices;

    private  IQuestionCallback callback;
    private String TAG="CreateQuestionFragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
        if (context instanceof IQuestionCallback) {
            callback = (IQuestionCallback) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        View view = inflater.inflate(R.layout.fragment_question, null);
        ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        question = (Question) arguments.getSerializable(QUESTION);
        questionAdapter(question);
        return view;
    }

    private void questionAdapter(final Question question) {
        tv_question_title.setText(question.getQuestion());
        tv_description.setText(question.getDescription());
        tv_description.setVisibility(View.GONE);
        //获取选项
        choices = Arrays.asList(question.getChoices());

        //打乱选项顺序，确保每次出现的次序都不一样
       // Collections.shuffle(choices);
        for (int i = 0; i < choices.size(); i++) {
            RadioButton button = new RadioButton(getActivity());
            button.setId(i);
            button.setText(choices.get(i).toString());
            rb_question.addView(button);
        }
        /**
         * 选择选项监听
         */
        rb_question.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = QuestionFragment.this.getActivity().findViewById(checkedId);
                if (!button.getText().equals(question.getAnswer())) {
                    callback.answer(false,tv_description,rb_question);
                } else {
                    callback.answer(true,tv_description,rb_question);
                }
            }
        });
    }

    /**
     * 入口
     * @param question
     * @return
     */
    public static QuestionFragment newInstance(Question question) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(QUESTION, question);
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
