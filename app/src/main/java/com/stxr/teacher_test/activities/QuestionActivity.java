package com.stxr.teacher_test.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.Question;
import com.stxr.teacher_test.entities.QuestionBank;
import com.stxr.teacher_test.fragments.QuestionFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by stxr on 2018/3/31.
 */

public class QuestionActivity extends AppCompatActivity {
    @BindView(R.id.vp_question)
    ViewPager viewPager;
    private String TAG = "QuestionActivity";
    private List<Question> questionList = new ArrayList<>();

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
                return QuestionFragment.newInstance(questions.get(position).toString());
            }

            @Override
            public int getCount() {
                return questions.size();
            }
        });
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
}
