package com.stxr.teacher_test.fragments.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.stxr.teacher_test.R;
import com.stxr.teacher_test.activities.PaperType;
import com.stxr.teacher_test.activities.QuestionActivity;
import com.stxr.teacher_test.adapter.PaperAdapter;
import com.stxr.teacher_test.entities.Group;
import com.stxr.teacher_test.entities.Paper;
import com.stxr.teacher_test.entities.Question;
import com.stxr.teacher_test.entities.QuestionBank;
import com.stxr.teacher_test.entities.Student;
import com.stxr.teacher_test.fragments.BaseFragment;
import com.stxr.teacher_test.fragments.QuestionFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by stxr on 2018/3/27.
 */

public class PracticeFragment extends BaseFragment {
    private List<Paper> paperList;
    private PaperAdapter adapter;

    @BindView(R.id.rv_show_question)
    RecyclerView rv_show_question;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_practice;
    }

    @Override
    protected void initData(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initData(inflater, container, savedInstanceState);
        rv_show_question.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        if (paperList != null) {
            rv_show_question.setAdapter(new PaperAdapter(paperList));
        } else {
            query();
        }
    }

    private void query() {
        Student student = Student.getCurrentUser(getActivity());
        BmobQuery<Student> studentQuery = new BmobQuery<>();
        studentQuery.getObject(student.getObjectId(), new QueryListener<Student>() {
            @Override
            public void done(Student student, BmobException e) {
                if (e == null) {
                    String objectId = student.getGroup().getObjectId();
                    BmobQuery<Paper> query = new BmobQuery<>();
                    Group group = new Group();
                    group.setObjectId(objectId);
                    Log.e(TAG, "objectId:" + objectId);
                    query.addWhereRelatedTo("papers", new BmobPointer(group));
                    query.findObjects(new FindListener<Paper>() {
                        @Override
                        public void done(List<Paper> papers, BmobException e) {
                            if (e == null) {
                                paperList = papers;
//                                Log.e(TAG, "done:" + list.toString());
                                adapter = new PaperAdapter(papers);
                                adapter.setOnclickListener(new PaperAdapter.OnclickListener() {
                                    @Override
                                    public void onClick(Paper paper) {
                                      startActivity(QuestionActivity.newInstance(getActivity(), paper, PaperType.PRACTICE));
                                    }
                                });
                                rv_show_question.setAdapter(adapter);
                            }
                        }
                    });
                }
            }
        });
    }


    /**
     * 根据json解析题库
     *
     * @param json
     */
    Question parseQuestion(String json) {
        Gson gson = new Gson();
        Question question = gson.fromJson(json, Question.class);
        Log.e(TAG, "parseQuestion: " + question.toString());
        return question;
    }

    public static PracticeFragment newInstance(List<Paper> paper) {
        PracticeFragment fragment = new PracticeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("papers", (Serializable) paper);
        fragment.setArguments(bundle);
        return fragment;
    }

}
