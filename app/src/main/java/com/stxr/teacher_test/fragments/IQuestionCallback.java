package com.stxr.teacher_test.fragments;

import android.view.View;

import com.stxr.teacher_test.entities.Question;

/**
 * Created by stxr on 2018/4/18.
 */

public interface IQuestionCallback {
    void answer(boolean isRight, View... views);
}
