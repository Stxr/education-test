package com.stxr.teacher_test.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.Announcement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by stxr on 2018/5/13.
 * 显示公告
 */

public class ShowAnnouncement extends SingleBaseFragment{

    public static final String ANNOUNCEMENT = "announcements";
    @BindView(android.R.id.list)
    ListView listView;

    List<HashMap<String, String>> list = new ArrayList<>();
    private List<Announcement> announcements;

    @Override
    protected void initData(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initData(inflater, container, savedInstanceState);
        Bundle arguments = getArguments();
        announcements = (List<Announcement>) arguments.getSerializable(ANNOUNCEMENT);
        for (Announcement announce : announcements) {
            HashMap<String, String> map = new HashMap<>();
            map.put("admin", String.valueOf(announce.getAdmin()));
            map.put("content", String.valueOf(announce.getContent()));
            map.put("title", String.valueOf(announce.getTitle()));
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(activity,
                list,
                R.layout.item_announce,
                new String[]{ "content","title","admin"},
                new int[]{R.id.tv_announce_content,R.id.tv_announce_title,R.id.tv_announce_admin});
        listView.setAdapter(adapter);
    }

    @Override
    protected int layoutResId() {
        return android.R.layout.list_content;
    }

    @Override
    protected String title() {
        return "公告";
    }

    public static Fragment newInstance(List<Announcement> announcements) {
        ShowAnnouncement fragment = new ShowAnnouncement();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ANNOUNCEMENT, (Serializable) announcements);
        fragment.setArguments(bundle);
        return fragment;
    }
}
