package com.stxr.teacher_test.entities;

import cn.bmob.v3.BmobObject;

/**
 * Created by stxr on 2018/5/4.
 * 考试成绩
 */

public class Grade extends BmobObject{
    private Paper paper;
    private Student student;
    private Double grade;

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }
}
