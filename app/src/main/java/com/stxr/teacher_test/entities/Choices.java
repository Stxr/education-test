package com.stxr.teacher_test.entities;

/**
 * Created by stxr on 2018/5/4.
 */

public class Choices {
    private String choice;

    public Choices() {

    }

    public Choices(String choice) {
        this();
        this.choice = choice;
    }

    @Override
    public String toString() {
        return choice;
    }
}
