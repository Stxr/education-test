package com.stxr.teacher_test.entities;

/**
 * Created by stxr on 2018/3/31.
 */

public class Question {
    private String question;
    private String answer;
    private Choice choices;

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

    public Choice getChoices() {
        return choices;
    }

    public void setChoices(Choice choices) {
        this.choices = choices;
    }

    class Choice {
        private String A;
        private String B;
        private String C;
        private String D;

        public String getA() {
            return A;
        }

        public void setA(String a) {
            A = a;
        }

        public String getB() {
            return B;
        }

        public void setB(String b) {
            B = b;
        }

        public String getC() {
            return C;
        }

        public void setC(String c) {
            C = c;
        }

        public String getD() {
            return D;
        }

        public void setD(String d) {
            D = d;
        }

        @Override
        public String toString() {
            return "Choice{" +
                    "A='" + A + '\'' +
                    ", B='" + B + '\'' +
                    ", C='" + C + '\'' +
                    ", D='" + D + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", choices=" + choices.toString() +
                '}';
    }
}
