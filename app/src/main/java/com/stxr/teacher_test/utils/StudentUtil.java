package com.stxr.teacher_test.utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.stxr.teacher_test.entities.Exam;
import com.stxr.teacher_test.entities.Group;
import com.stxr.teacher_test.entities.Paper;
import com.stxr.teacher_test.entities.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by stxr on 2018/5/15.
 */

public class StudentUtil {
    public static final int EXAM_PAPER = 1;
    public static final int PRACTICE_PAPER = 2;
    public static final int STUDENT = 3;
    public static final int GROUP = 4;
    public static final int EXAM = 5;
    public static final int INT = 6;
    public static final String EXAM1 = "exam";
    private Student mStudent;
    private Group mGroup;
    private List<Paper> mPapers;
    private static List<Paper> mExamPapers;
    private static List<Exam> mExams;
    private CallBack callBack;
    private String TAG = "StudentUtil";
    //    private static boolean flag = true;
    private boolean flagExam = false;
    private boolean flagPractice = false;

    private StudentUtil() {
        mExamPapers = new ArrayList<>();
        flagExam = false;
        flagPractice = false;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case STUDENT:
                    Log.e(TAG, "handleMessage: student" + mStudent);
                    getGroup();
                    break;
                case GROUP:
                    Log.e(TAG, "handleMessage: GROUP" + mGroup);
                    getExams();
                    getPapers();
                    break;
                case EXAM:
//                    mExams = (List<Exam>) msg.getData().getSerializable(EXAM1);
                    Log.e(TAG, "handleMessage: EXAM" + mExams);
                    if (mExams.size() != 0) {
                        getExamPapers();
                    } else {
                        flagExam = true;
                    }
                    break;
                case INT:
                    Paper paper = (Paper) msg.obj;
                    Log.e(TAG, "handleMessage__paper: " + paper);
                    if (!mExamPapers.contains(paper)) {
                        mExamPapers.add(paper);
                    }
                    Log.e(TAG, "handleMessage: mExamPapers" + mExamPapers);
                    break;

                case PRACTICE_PAPER:
                    flagPractice = true;
                    Log.e(TAG, "handleMessage: flagPractice");
                    if (mExams.size() == 0) {
                        flagExam = true;
                    }
                    if (callBack != null) {
                        callBack.onSuccess(StudentUtil.get(), flagExam);
                    }
                    break;
                case EXAM_PAPER:
                    Log.e(TAG, "EXAM_PAPER: " + flagExam);
                    flagExam = true;
                    Log.e(TAG, "handleMessage: flagExam");
                    if (callBack != null) {
                        callBack.onSuccess(StudentUtil.get(), flagPractice);
                    }
                    break;
            }
        }
    };

    public List<Exam> getExams() {
        if (mExams == null) {
            BmobQuery<Exam> query = new BmobQuery<>();
            query.addWhereEqualTo("group", new BmobPointer(mGroup));
            query.include("paper,group");
            query.findObjects(new FindListener<Exam>() {
                @Override
                public void done(List<Exam> list, BmobException e) {
                    if (e == null) {
                        mExams = list;
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(EXAM1, (Serializable) list);
                        Message message = handler.obtainMessage();
                        message.what = EXAM;
                        message.setData(bundle);
                        handler.sendEmptyMessage(EXAM);
                    }
                }
            });
            return mExams;
        } else {
            return mExams;
        }
    }

    public synchronized List<Paper> getExamPapers() {
        if (mExams != null) {
            Log.e(TAG, "mExamPapers.size(): " + mExamPapers.size() + " mExams.size()" + mExams.size());
        }
        if (mExamPapers.size() == 0) {
            for (int i = 0; i < (mExams != null ? mExams.size() : 0); i++) {
                final Exam exam = mExams.get(i);
                BmobQuery<Student> query = new BmobQuery<>();
                query.addWhereRelatedTo("student", new BmobPointer(exam));
                final int finalI = i;
                query.findObjects(new FindListener<Student>() {
                    @Override
                    public void done(List<Student> list, BmobException e) {
                        if (e == null) {
                            Log.d(TAG, "done() called with: list = [" + list + "], e = [" + e + "]");
                            //学生还没做
                            if (!list.contains(mStudent)) {
                                Message message = handler.obtainMessage();
                                message.obj = exam.getPaper();
                                message.what = INT;
                                handler.sendMessage(message);
//                                mExamPapers.add(exam.getPaper());
                                Log.e(TAG, " exam.getPaper: " + exam.getPaper());
                            }
                            //最后一个
                            if (finalI == mExams.size() - 1) {
                                handler.sendEmptyMessage(EXAM_PAPER);
                            }
                            Log.e(TAG, "mExamPapers " + list + " mStudent:" + mStudent + " exam.getPaper: " + exam.getPaper());
                        } else {
                            Log.e(TAG, "e.getMessage(): " + e.getMessage());
                        }
                    }
                });
            }
            Log.e(TAG, "---getExamPapers.size():" + mExamPapers.size());
            return mExamPapers;
        } else {
            Log.e(TAG, "-getExamPapers.size():" + mExamPapers.size());
            //handler.sendEmptyMessage(EXAM_PAPER);
            return mExamPapers;
        }
    }


    public List<Paper> getPapers() {
        if (mPapers == null) {
            BmobQuery<Paper> query = new BmobQuery<>();
            query.addWhereRelatedTo("papers", new BmobPointer(mGroup));
            query.findObjects(new FindListener<Paper>() {
                @Override
                public void done(List<Paper> list, BmobException e) {
                    if (e == null) {
                        mPapers = list;
                        flagPractice = true;
                        handler.sendEmptyMessage(PRACTICE_PAPER);
                    }
                }
            });
        } else {
            handler.sendEmptyMessage(PRACTICE_PAPER);
            return mPapers;
        }
        return null;
    }


    public Group getGroup() {
        if (mGroup == null) {
            BmobQuery<Group> query = new BmobQuery<>();
            query.getObject(mStudent.getGroup().getObjectId(), new QueryListener<Group>() {
                @Override
                public void done(Group group, BmobException e) {
                    if (e == null) {
                        mGroup = group;
                        handler.sendEmptyMessage(GROUP);
                    }
                }
            });
        } else {
            handler.sendEmptyMessage(GROUP);
            return mGroup;
        }
        return null;
    }


    private static class Holder {
        private static StudentUtil studentUtil = new StudentUtil();
    }

    public static StudentUtil get() {
        return Holder.studentUtil;
    }


    public StudentUtil setStudent(final Student student) {
        BmobQuery<Student> query = new BmobQuery<>();
        query.include("group");
        query.getObject(student.getObjectId(), new QueryListener<Student>() {
            @Override
            public void done(Student student, BmobException e) {
                Log.e(TAG, "done() called with: student = [" + student + "], e = [" + e + "]");
                if (e == null) {
                    mStudent = student;
                    handler.sendEmptyMessage(STUDENT);
                }
            }
        });

        return this;
    }

    public void setOnCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void onSuccess(StudentUtil studentUtil, boolean flag);
    }


    public Student getStudent() {
        return mStudent;
    }


}
