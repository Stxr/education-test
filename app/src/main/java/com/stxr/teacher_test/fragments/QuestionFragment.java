package com.stxr.teacher_test.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.activities.PaperType;
import com.stxr.teacher_test.activities.QuestionActivity;
import com.stxr.teacher_test.activities.QuestionType;
import com.stxr.teacher_test.entities.Choices;
import com.stxr.teacher_test.entities.Paper;
import com.stxr.teacher_test.entities.Question;
import com.stxr.teacher_test.utils.MyTimer;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stxr on 2018/3/31.
 */

public class QuestionFragment extends Fragment {
    public static final String QUESTION = "question";
    public static final String PAPER = "paper";
    public static final String TYPE = "questionType";
    private String TAG = "CreateQuestionFragment";

    @BindView(R.id.tv_question_title)
    public TextView tv_question_title;
    @BindView(R.id.rg_question)
    public RadioGroup rg_question;
    @BindView(R.id.edt_answer)
    public EditText edt_answer;
    @BindView(R.id.tv_description)
    public TextView tv_description;

    public Question question;
    private List<Choices> choices;
    private QuestionType questionType;
    public RadioButton radioButton;

    //    private AnswerCallBack callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, null);
        ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        question = (Question) arguments.getSerializable(QUESTION);
        Log.e(TAG, question.toString());
        questionAdapter(question);
        return view;
    }

    private void questionAdapter(final Question question) {
        tv_question_title.setText(question.getQuestion());
        tv_description.setText(question.getDescription());
        tv_description.setVisibility(View.GONE);
        questionType = question.isSelection() ? QuestionType.SELECTION : QuestionType.BLANK;
        if (questionType.equals(QuestionType.SELECTION)) {//是选择题
            edt_answer.setVisibility(View.GONE);
            rg_question.setVisibility(View.VISIBLE);
            //获取选项
            choices = Arrays.asList(question.getChoices());
            for (int i = 0; i < choices.size(); i++) {
                RadioButton button = new RadioButton(getActivity());
                button.setId(i);
                button.setText(choices.get(i).toString());
                if (i == 0) {
                    radioButton = button;
                }
                rg_question.addView(button);
            }
            /**
             * 选择选项监听
             */
            rg_question.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radioButton = getActivity().findViewById(checkedId);
//                    if (button.getText().toString().equals(question.getAnswer())) {
//                        callback.answer(true, tv_description, button);
//                    } else {
//                        callback.answer(false, tv_description, button);
//                    }
                }
            });
        } else {//是填空题
            rg_question.setVisibility(View.GONE);
            edt_answer.setVisibility(View.VISIBLE);
//            edt_answer.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    if (question.getAnswer().equals(s.toString())) {
//                        callback.answer(true, tv_description, edt_answer);
//                    } else {
//                        callback.answer(false, tv_description, edt_answer);
//                    }
//                }
//            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof AnswerCallBack) {
//            callback = (AnswerCallBack) context;
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        callback = null;
    }

    /**
     * 入口
     *
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


//    public interface AnswerCallBack {
//        void answer(Boolean isTrue, View... views);
//    }

}
