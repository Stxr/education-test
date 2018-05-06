package com.stxr.teacher_test.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.stxr.teacher_test.entities.Question;
import com.stxr.teacher_test.fragments.QuestionFragment;

import java.util.List;

/**
 * Created by stxr on 2018/5/6.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private QuestionFragment fragment;
    private List<Question> questions;
    public MyPagerAdapter(FragmentManager fm, List<Question> questions) {
        super(fm);
        this.questions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        return QuestionFragment.newInstance(questions.get(position));
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        fragment = (QuestionFragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public QuestionFragment getFragment() {
        return fragment;
    }
}
