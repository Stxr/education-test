package com.stxr.teacher_test.fragments.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.activities.AnnounceListActivity;
import com.stxr.teacher_test.activities.ListViewActivity;
import com.stxr.teacher_test.admin.AdminActivity;
import com.stxr.teacher_test.entities.Announcement;
import com.stxr.teacher_test.entities.Score;
import com.stxr.teacher_test.entities.Student;
import com.stxr.teacher_test.fragments.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 用户管理Fragment
 */
public class AccountFragment extends BaseFragment {

    private Student student;
    private List<Score> scores;
    private List<Announcement> announcements;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.btn_watch)
    Button btn_watch;
    @Override
    protected int layoutResId() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initData(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initData(inflater, container, savedInstanceState);
        BmobQuery<Score> query = new BmobQuery<>();
        student = Student.getCurrentUser(getActivity());
        tv_name.setText(student.getUsername());
        query.addWhereEqualTo("Student", new BmobPointer(student));
        query.include("Student,paper");
        query.order("-createdAt");
        query.findObjects(new FindListener<Score>() {
            @Override
            public void done(List<Score> list, BmobException e) {
                if (e == null) {
                    scores = list;
                }
            }
        });
        BmobQuery<Announcement> announcementBmobQuery = new BmobQuery<>();
        announcementBmobQuery.include("admin");
        announcementBmobQuery.findObjects(new FindListener<Announcement>() {
            @Override
            public void done(List<Announcement> list, BmobException e) {
                if (e == null) {
                    announcements = list;
                }
            }
        });
    }

    @OnClick(R.id.btn_quit)
    void quit() {
        Student.logOut(getActivity());
       startActivity( AdminActivity.newInstance(getActivity()));
        getActivity().finish();
    }

    @OnClick(R.id.btn_score)
    void scoreShow() {
        startActivity(ListViewActivity.newInstance(getContext(), scores));
    }
    @OnClick(R.id.btn_watch)
    void announceShow() {
        if (announcements != null) {
            startActivity(AnnounceListActivity.newInstance(getContext(), announcements));
        }
    }
}
