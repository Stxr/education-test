package com.stxr.teacher_test.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.stxr.teacher_test.admin.AnnouncementFragment;
import com.stxr.teacher_test.admin.SingleFragmentActivity;
import com.stxr.teacher_test.entities.Announcement;
import com.stxr.teacher_test.entities.Score;
import com.stxr.teacher_test.fragments.BaseFragment;
import com.stxr.teacher_test.fragments.ShowAnnouncement;
import com.stxr.teacher_test.fragments.ShowScoreFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by stxr on 2018/6/5.
 */

public class AnnounceListActivity extends SingleFragmentActivity {
    public static final String SCORES = "scores";
    List<Announcement> scoreList = new ArrayList<>();

    @Override
    protected Fragment createFragment() {
        return ShowAnnouncement.newInstance(scoreList);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        scoreList = (List<Announcement>) getIntent().getExtras().getSerializable(SCORES);
        super.onCreate(savedInstanceState);
    }

    public static Intent newInstance(Context context, List<Announcement>scores) {
        Intent intent = new Intent(context, AnnounceListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(SCORES, (Serializable) scores);
        intent.putExtras(bundle);
        return intent;
    }
}
