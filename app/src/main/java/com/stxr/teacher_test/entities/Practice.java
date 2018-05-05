package com.stxr.teacher_test.entities;

/**
 * Created by stxr on 2018/5/4.
 * 练习记录
 */

public class Practice {
    private Student student;
    private int right;
    private int wrong;
    private int all;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
