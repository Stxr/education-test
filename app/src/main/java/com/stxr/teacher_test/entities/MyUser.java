package com.stxr.teacher_test.entities;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by stxr on 2018/3/30.
 */

public class MyUser extends BmobUser {
    private BmobRelation wrongQuestion;
    private BmobRelation rightQuestion;
    private BmobRelation practice;

    public BmobRelation getWrongQuestion() {
        return wrongQuestion;
    }

    public void setWrongQuestion(BmobRelation wrongQuestion) {
        this.wrongQuestion = wrongQuestion;
    }

    public BmobRelation getRightQuestion() {
        return rightQuestion;
    }

    public void setRightQuestion(BmobRelation rightQuestion) {
        this.rightQuestion = rightQuestion;
    }

    public BmobRelation getPractice() {
        return practice;
    }

    public void setPractice(BmobRelation practice) {
        this.practice = practice;
    }
}
