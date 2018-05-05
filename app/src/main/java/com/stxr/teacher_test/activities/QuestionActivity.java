package com.stxr.teacher_test.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.Question;
import com.stxr.teacher_test.entities.QuestionBank;
import com.stxr.teacher_test.fragments.IQuestionCallback;
import com.stxr.teacher_test.fragments.QuestionFragment;
import com.stxr.teacher_test.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by stxr on 2018/3/31.
 */

public class QuestionActivity extends AppCompatActivity implements IQuestionCallback {
    @BindView(R.id.vp_question)
    MyViewPager viewPager;
    @BindView(R.id.btn_confirm)
    Button btn_confirm;
    private String TAG = "QuestionActivity";

    private List<Question> questionList = new ArrayList<>();
    private QuestionFragment fragment;
    private Question question;
    private boolean isAnswerTrue;
    private TextView tv_description;
    private RadioGroup rg_question;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        query();
    }

    private void setAdapter(final List<Question> questions) {
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                question = questions.get(position);
                fragment = QuestionFragment.newInstance(question);
                return fragment;
            }

            @Override
            public int getCount() {
                return questions.size();
            }
        });
        viewPager.setCanScroll(false);
    }
    //答题确定按钮
    @OnClick(R.id.btn_confirm)
    void onClick() {
        if (btn_confirm.getText().equals("确定")) {
            if (isAnswerTrue) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                setRadioGroupCheckable(rg_question, false);
                tv_description.setVisibility(View.VISIBLE);
                btn_confirm.setText("下一题");
            }
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            btn_confirm.setText("确定");
        }
    }

    void setRadioGroupCheckable(RadioGroup radioGroup, boolean checkable) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(checkable);
        }
    }

    void query() {
        final BmobQuery<QuestionBank> query = new BmobQuery<>();
        query.findObjects(new FindListener<QuestionBank>() {
            @Override
            public void done(List<QuestionBank> list, BmobException e) {
                if (e == null) {
//                    Log.e(TAG, list.toString());
                    for (QuestionBank question : list) {
                        questionList.add(parseQuestion(question.getQuestion()));
                    }
                    setAdapter(questionList);
                }
            }
        });
    }

    /**
     * 根据json解析题库
     *
     * @param json
     */
    private Question parseQuestion(String json) {
        Gson gson = new Gson();
        Question question = gson.fromJson(json, Question.class);
        Log.e(TAG, "parseQuestion: " + question.toString());
        return question;
    }

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, QuestionActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void answer(boolean isRight,View... views) {
        isAnswerTrue = isRight;
        if (views[0] instanceof TextView) {
            tv_description = (TextView) views[0];
        }
        if (views[1] instanceof RadioGroup) {
            rg_question = (RadioGroup) views[1];
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
}
