package com.stxr.teacher_test.entities;

import cn.bmob.v3.BmobObject;

/**
 * Created by stxr on 2018/5/4.
 */

public class ExamDate extends BmobObject {
    private String date;
    private Group group;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
