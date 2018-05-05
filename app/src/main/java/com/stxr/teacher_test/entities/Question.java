package com.stxr.teacher_test.entities;

import java.io.Serializable;
import java.util.Arrays;

import cn.bmob.v3.BmobObject;

/**
 * Created by stxr on 2018/3/31.
 */

public class Question extends BmobObject {
    private String question;
    private String answer;
    private Choices[] choices;
    private String description;
    private boolean isSelection;//是否是选择题


    public void setChoices(Choices[] choices) {
        this.choices = choices;
    }

    public com.stxr.teacher_test.entities.Choices[] getChoices() {
        return choices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelection() {
        return isSelection;
    }

    public void setSelection(boolean selection) {
        isSelection = selection;
    }



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", choices=" + Arrays.toString(choices) +
                ", description='" + description + '\'' +
                '}';
    }
}
