package com.stxr.teacher_test.entities;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by stxr on 2018/5/4.
 */

public class Exam extends BmobObject {
    private String date;
    private Group group;
    private Paper paper;
    private BmobRelation student;
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

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "date='" + date + '\'' +
                ", group=" + group +
                ", paper=" + paper +
                '}';
    }

    public BmobRelation getStudent() {
        return student;
    }

    public void setStudent(BmobRelation student) {
        this.student = student;
    }
}
