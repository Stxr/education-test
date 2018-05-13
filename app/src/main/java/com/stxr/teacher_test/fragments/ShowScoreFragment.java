package com.stxr.teacher_test.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.Exam;
import com.stxr.teacher_test.entities.Score;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by stxr on 2018/5/13.
 * 显示分数
 */

public class ShowScoreFragment extends SingleBaseFragment{

    public static final String SCORES = "scores";
    @BindView(android.R.id.list)
    ListView listView;

    List<HashMap<String, String>> list = new ArrayList<>();
    private List<Score> scores;

    @Override
    protected void initData(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initData(inflater, container, savedInstanceState);
        Bundle arguments = getArguments();
        scores = (List<Score>) arguments.getSerializable(SCORES);
        for (Score score : scores) {
            HashMap<String, String> map = new HashMap<>();
            map.put("score", String.valueOf(score.getScore()));
            map.put("paper", String.valueOf(score.getPaper().getName()));
            map.put("time", String.valueOf(score.getUpdatedAt()));
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(activity,
                list,
                R.layout.item_score,
                new String[]{"score", "paper", "time"},
                new int[]{R.id.tv_score,R.id.tv_paper_name,R.id.tv_time});
        listView.setAdapter(adapter);
    }

    @Override
    protected int layoutResId() {
        return android.R.layout.list_content;
    }

    @Override
    protected String title() {
        return "历史考试成绩";
    }

    public static Fragment newInstance(List<Score> scores) {
        ShowScoreFragment fragment = new ShowScoreFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SCORES, (Serializable) scores);
        fragment.setArguments(bundle);
        return fragment;
    }
}
