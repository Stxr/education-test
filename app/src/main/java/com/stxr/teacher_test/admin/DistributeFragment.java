package com.stxr.teacher_test.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.Group;
import com.stxr.teacher_test.entities.Paper;
import com.stxr.teacher_test.entities.Student;
import com.stxr.teacher_test.fragments.BaseFragment;
import com.stxr.teacher_test.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by stxr on 2018/5/4.
 */

public class DistributeFragment extends BaseFragment {
    @BindView(R.id.sp_group1)
    AppCompatSpinner sp_group1;
    @BindView(R.id.sp_group2)
    AppCompatSpinner sp_group2;
    @BindView(R.id.sp_student)
    AppCompatSpinner sp_student;
    @BindView(R.id.sp_paper)
    AppCompatSpinner sp_paper;

    private void updateGroup() {
        BmobQuery<Group> query = new BmobQuery<>();
        query.findObjects(new FindListener<Group>() {
            @Override
            public void done(List<Group> list, BmobException e) {
                ArrayAdapter<Group> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_group1.setAdapter(adapter);
                sp_group2.setAdapter(adapter);
            }
        });
    }

    private void updateStudent() {
        BmobQuery<Student> query = new BmobQuery<>();
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                ArrayAdapter<Student> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_student.setAdapter(adapter);
            }
        });
    }

    private void updatePaper() {
        BmobQuery<Paper> query = new BmobQuery<>();
        query.findObjects(new FindListener<Paper>() {
            @Override
            public void done(List<Paper> list, BmobException e) {
                ArrayAdapter<Paper> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_paper.setAdapter(adapter);
            }
        });
    }


    @Override
    protected void initData(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initData(inflater, container, savedInstanceState);
        updateGroup();
        updatePaper();
        updateStudent();
    }

    @OnClick(R.id.btn_confirm)
    void confirm() {
        Paper paper = (Paper) sp_paper.getSelectedItem();
        Group group1 = (Group) sp_group1.getSelectedItem();
        Group group2 = (Group) sp_group2.getSelectedItem();
        Student student = (Student) sp_student.getSelectedItem();
        BmobRelation relation = new BmobRelation();
        relation.add(group1);
        paper.setOwn(relation);
        //一个学生只能在一个组
        student.setGroup(group2);
        paper.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.show(activity, "更新成功");
                } else {
                    ToastUtil.show(activity, e.getMessage());
                }
            }
        });
        student.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.show(activity, "更新成功");
                } else {
                    ToastUtil.show(activity, e.getMessage());
                }
            }
        });

    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_distribute;
    }

    @Override
    protected String title() {
        return "分配题库";
    }
}
