package com.stxr.teacher_test.fragments.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.stxr.teacher_test.R;
import com.stxr.teacher_test.activities.QuestionActivity;
import com.stxr.teacher_test.entities.Question;
import com.stxr.teacher_test.entities.QuestionBank;
import com.stxr.teacher_test.fragments.QuestionFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by stxr on 2018/3/27.
 */

public class PracticeFragment extends Fragment {

    private String TAG = "PracticeFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载布局
        View view = inflater.inflate(R.layout.fragment_practice, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

//    void practice() {
//        getFragmentManager().beginTransaction()
//                .replace(R.layout.activity_question, new CreateQuestionFragment())
//                .commit();
//    }

    //查询题库信息
    @OnClick(R.id.btn_test)
    void toQuestionActivity() {
        QuestionActivity.newInstance(getActivity());
    }

    /**
     * 根据json解析题库
     * @param json
     */
    Question parseQuestion(String json) {
        Gson gson = new Gson();
        Question question = gson.fromJson(json, Question.class);
        Log.e(TAG, "parseQuestion: " + question.toString());
        return question;
    }

}
