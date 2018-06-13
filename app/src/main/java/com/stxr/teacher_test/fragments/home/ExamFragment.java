package com.stxr.teacher_test.fragments.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.activities.PaperType;
import com.stxr.teacher_test.activities.QuestionActivity;
import com.stxr.teacher_test.adapter.PaperAdapter;
import com.stxr.teacher_test.entities.Exam;
import com.stxr.teacher_test.entities.Group;
import com.stxr.teacher_test.entities.Paper;
import com.stxr.teacher_test.entities.Student;
import com.stxr.teacher_test.fragments.BaseFragment;
import com.stxr.teacher_test.utils.StudentUtil;
import com.stxr.teacher_test.utils.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.stxr.teacher_test.R2.id.srl_practice;

/**
 * Created by stxr on 2018/3/27.
 */

public class ExamFragment extends BaseFragment {

    public static final int FINISHED_LOAD = 110;
    @BindView(R.id.srl_exam)
    SwipeRefreshLayout srl_exam;
    @BindView(R.id.rv_show_paper)
    RecyclerView rv_show_paper;
    private String date;
    private Group group;
    private Paper paper;
    private List<Paper> papers = new ArrayList<>();
    private Exam mExam;
    private List<Exam> exams;
    private PaperAdapter adapter;
    private boolean result;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
            Log.e(TAG, "handleMessage: papers: " + papers);
        }
    };

    @Override
    protected int layoutResId() {
        return R.layout.fragment_exam;
    }

    @Override
    protected void initData(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initData(inflater, container, savedInstanceState);
        loadingData();
        rv_show_paper.setLayoutManager(new GridLayoutManager(getContext(), 2));
        srl_exam.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    void loadingPaper(View view) {
        TextView textView = (TextView) view;
        if (date == null || paper == null || group == null) {
            ToastUtil.show(getContext(), "没有考试信息");
        } else {
            if (check()) {
                papers.remove(paper);
                startActivity(QuestionActivity.newInstance(getContext(), paper, PaperType.EXAM));
                BmobRelation relation = new BmobRelation();
                relation.add(Student.getCurrentUser(getContext()));
                mExam.setStudent(relation);
                mExam.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtil.show(getContext(), "开始考试");
                        }
                    }
                });
//                textView.setText("已完成考试");
            } else {
                textView.setText(date + paper);
            }
        }
    }

    boolean check() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        try {
            Date examDate = format.parse(ExamFragment.this.date);
            //是考试时间
            result = isSameDate(examDate, new Date());
        } catch (ParseException err) {
            err.printStackTrace();
        }
        return result;
    }


    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        Log.e("ExamFragment", "isSameDate() called with: date1 = [" + date1 + "], date2 = [" + date2 + "]");
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    void loadingData() {
        exams = StudentUtil.get().getExams();
        papers = StudentUtil.get().getExamPapers();
        Log.e(TAG, "papers: " + papers);
        adapter = new PaperAdapter(papers);
        adapter.setOnclickListener(new PaperAdapter.OnclickListener() {
            @Override
            public void onClick(Paper paper, View view) {
                int i = papers.indexOf(paper);
                Log.e(TAG, "-----papers:" + papers);
                Log.e(TAG, "-----exams:" + exams);
                for (Exam exam : exams) {
                    if (exam.getPaper().equals(paper)) {
                        mExam = exam;
                        break;
                    }
                }
                date = mExam.getDate();
                group = mExam.getGroup();
                ExamFragment.this.paper = paper;
                loadingPaper(view);
            }
        });
        rv_show_paper.setAdapter(adapter);
        handler.sendEmptyMessageDelayed(FINISHED_LOAD, 500);
    }


}
