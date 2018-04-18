package com.stxr.teacher_test.entities;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by stxr on 2018/3/31.
 */

public class Question implements Serializable{
    private String question;
    private String answer;
    private Choices[] choices;
    private String description;


    public void setChoices(Choices[] choices) {
        this.choices = choices;
    }
    public Choices[] getChoices() {
        return choices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public class Choices {
        public String choice;

        @Override
        public String toString() {
            return choice;
        }
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
