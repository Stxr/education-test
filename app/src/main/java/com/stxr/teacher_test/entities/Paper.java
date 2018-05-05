package com.stxr.teacher_test.entities;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by stxr on 2018/5/4.
 * 试卷
 */

public class Paper extends BmobObject{
    private String name;
    private BmobRelation question;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }

    public BmobRelation getQuestion() {
        return question;
    }

    public void setQuestion(BmobRelation question) {
        this.question = question;
    }
}
