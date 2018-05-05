package com.stxr.teacher_test.entities;

import cn.bmob.v3.BmobObject;

/**
 * Created by stxr on 2018/5/5.
 *
 */

public class Score extends BmobObject {
    private Student Student;
    private Paper paper;
    private Double score;

    public com.stxr.teacher_test.entities.Student getStudent() {
        return Student;
    }

    public void setStudent(com.stxr.teacher_test.entities.Student student) {
        Student = student;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
