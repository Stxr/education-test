package com.stxr.teacher_test.fragments.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.activities.PaperType;
import com.stxr.teacher_test.activities.QuestionActivity;
import com.stxr.teacher_test.entities.Exam;
import com.stxr.teacher_test.entities.Group;
import com.stxr.teacher_test.entities.Paper;
import com.stxr.teacher_test.entities.Student;
import com.stxr.teacher_test.fragments.BaseFragment;
import com.stxr.teacher_test.utils.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by stxr on 2018/3/27.
 */

public class ExamFragment extends BaseFragment {

    @BindView(R.id.btn_start_exam)
    Button btn_exam;
    private String date;
    private Group group;
    private Paper paper;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_exam;
    }

    @Override
    protected void initData(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initData(inflater, container, savedInstanceState);
        loadingData();
    }

    @OnClick(R.id.btn_start_exam)
    void loadingPaper() {
        if (date == null || paper == null || group==null) {
            ToastUtil.show(getContext(), "没有考试信息");
        } else {
            if (check()) {
                startActivity(QuestionActivity.newInstance(getContext(), paper, PaperType.EXAM));
            }
        }
    }

    boolean check() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        try {
            Date examDate = format.parse(this.date);
            if ( isSameDate(examDate, new Date())) {//是考试时间
                btn_exam.setEnabled(true);
                return true;
            } else {
                btn_exam.setText("不是考试时间，考试时间为："+date);
                btn_exam.setEnabled(false);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    private static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

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
        Student student = Student.getCurrentUser(getContext());
        BmobQuery<Student> query = new BmobQuery<>();
        query.getObject(student.getObjectId(), new QueryListener<Student>() {
            @Override
            public void done(Student student, BmobException e) {
                if (e == null) {
                    final BmobQuery<Exam> examQuery = new BmobQuery<>();
                    Group g = student.getGroup();
                    examQuery.addWhereEqualTo("group", new BmobPointer(g));
                    examQuery.include("paper,group");
                    examQuery.order("-createdAt");
                    examQuery.findObjects(new FindListener<Exam>() {
                        @Override
                        public void done(List<Exam> list, BmobException e) {
                            if (e == null) {
                                Exam exam = list.get(0);
                                date = exam.getDate();
                                group = exam.getGroup();
                                paper = exam.getPaper();
                                check();
                            }
                        }
                    });
                }
            }
        });
    }


}
