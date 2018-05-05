package com.stxr.teacher_test.entities;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by stxr on 2018/3/31.
 */

public class QuestionBank extends BmobObject {
    private String question;
    private BmobRelation examinee;
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public BmobRelation getExaminee() {
        return examinee;
    }

    public void setExaminee(BmobRelation examinee) {
        this.examinee = examinee;
    }

    @Override
    public String toString() {
        return "QuestionBank{" +
                "question='" + question + '\'' +
                ", examinee=" + examinee +
                '}';
    }

}
