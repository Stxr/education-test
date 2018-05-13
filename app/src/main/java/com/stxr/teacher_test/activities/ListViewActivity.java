package com.stxr.teacher_test.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.stxr.teacher_test.admin.SingleFragmentActivity;
import com.stxr.teacher_test.entities.Score;
import com.stxr.teacher_test.fragments.ShowScoreFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stxr on 2018/5/13.
 */

public class ListViewActivity extends SingleFragmentActivity {
    public static final String SCORES = "scores";
    List<Score> scoreList = new ArrayList<>();

    @Override
    protected Fragment createFragment() {
        return ShowScoreFragment.newInstance(scoreList);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        scoreList = (List<Score>) getIntent().getExtras().getSerializable(SCORES);
        super.onCreate(savedInstanceState);
    }

    public static Intent newInstance(Context context, List<Score>scores) {
        Intent intent = new Intent(context, ListViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(SCORES, (Serializable) scores);
        intent.putExtras(bundle);
        return intent;
    }
}
