package com.stxr.teacher_test.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.Paper;
import com.stxr.teacher_test.entities.Question;
import com.stxr.teacher_test.entities.QuestionBank;
import com.stxr.teacher_test.entities.Score;
import com.stxr.teacher_test.entities.Student;
import com.stxr.teacher_test.fragments.IQuestionCallback;
import com.stxr.teacher_test.fragments.QuestionFragment;
import com.stxr.teacher_test.utils.MyTimer;
import com.stxr.teacher_test.utils.ToastUtil;
import com.stxr.teacher_test.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by stxr on 2018/3/31.
 */

public class QuestionActivity extends AppCompatActivity implements MyTimer.OnTimerCallBack, QuestionFragment.AnswerCallBack {
    @BindView(R.id.vp_question)
    public MyViewPager viewPager;
    @BindView(R.id.btn_confirm)
    public Button btn_confirm;
    @BindView(R.id.tv_time)
    public TextView tv_time;
    @BindView(R.id.tv_done)
    public TextView tv_done;

    private String TAG = "QuestionActivity";

    TextView tv_description;
    RadioGroup rg_question;
    public List<Question> questionList = new ArrayList<>();
    private QuestionFragment fragment;
    private Question question;
    private Paper paper;
    private PaperType paperType;
    public int num;
    private boolean last = false;
    private int right;
    private RadioButton selectedRB;
    boolean isAnswerTrue;
    private MyTimer timer;
    private EditText edt_answer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);
        initData();
        query();
    }

    private void initData() {
        Intent intent = getIntent();
        paperType = (PaperType) intent.getSerializableExtra("paperType");
        paper = (Paper) intent.getSerializableExtra("paper");
        //判断类型
        if (paperType == PaperType.EXAM) {
            timer = new MyTimer(120, 1000);
            timer.start();
            timer.setOnTimerCallBack(this);
        } else {
            tv_time.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    private void setAdapter(final List<Question> questions) {
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                question = questions.get(position);
                fragment = QuestionFragment.newInstance(question);
                return fragment;
            }

            @Override
            public int getCount() {
                return questions.size();
            }
        });
        viewPager.setCanScroll(false);
    }

    //答题确定按钮
    @OnClick(R.id.btn_confirm)
    void onClick() {
        if (tv_description == null && (selectedRB == null || edt_answer == null)) {
            ToastUtil.show(this, "请开始答题");
            return;
        }
        if (viewPager.getCurrentItem() == num - 1) {
            last = true;
        }
        if (paperType.equals(PaperType.PRACTICE)) {
            if (!last) {
                if (btn_confirm.getText().equals("确定")) {
                    if (isAnswerTrue) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        right++;
                    } else {
                        setRadioGroupCheckable(rg_question, false);
                        tv_description.setVisibility(View.VISIBLE);
                        btn_confirm.setText("下一题");
                    }
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    btn_confirm.setText("确定");
                }
            } else {
                btn_confirm.setEnabled(false);
                if (isAnswerTrue) {
                    right++;
                } else {
                    setRadioGroupCheckable(rg_question, false);
                    tv_description.setVisibility(View.VISIBLE);
                }
                ToastUtil.show(QuestionActivity.this, "没有了");
            }
        } else {
            if (isAnswerTrue) right++;
            if (!last) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                updateScore();
                btn_confirm.setEnabled(false);
            }
        }
        recordDone(viewPager.getCurrentItem() + 1);
    }

    /**
     * 记录
     *
     * @param done 已经做了题目
     * @param all  题目总数
     */
    void recordDone(int done) {
        String format = String.format(Locale.CHINESE, "已做题数：%d/%d", done, num);
        tv_done.setText(format);
    }

    void setRadioGroupCheckable(RadioGroup radioGroup, boolean checkable) {
        if (question.isSelection()) {
            if (radioGroup != null) {
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    radioGroup.getChildAt(i).setEnabled(checkable);
                }
            }
        }
    }

    /**
     * 根据paper查询题目
     */
    void query() {
        final BmobQuery<QuestionBank> query = new BmobQuery<>();
        query.addWhereRelatedTo("question", new BmobPointer(paper));
        query.findObjects(new FindListener<QuestionBank>() {
            @Override
            public void done(List<QuestionBank> list, BmobException e) {
                if (e == null) {
                    for (QuestionBank question : list) {
                        questionList.add(parseQuestion(question.getQuestion()));
                    }
                    num = list.size();
                    setAdapter(questionList);
                }
            }
        });
    }

    /**
     * 根据json解析题库
     *
     * @param json
     */
    private Question parseQuestion(String json) {
        Gson gson = new Gson();
        Question question = gson.fromJson(json, Question.class);
        return question;
    }

    public static Intent newInstance(Context context, Paper p, PaperType type) {
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra("paperType", type);
        intent.putExtra("paper", p);
        return intent;
    }

    void updateScore() {
        double s = ((double) right) / num * 100;
        Score score = new Score();
        score.setPaper(paper);
        score.setStudent(Student.getCurrentUser(this));
        score.setScore(s);
        score.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtil.show(QuestionActivity.this, "上传考试成绩成功");
                }
            }
        });
    }

    @Override
    public void onTick(String time) {
        tv_time.setText(time);
    }

    @Override
    public void onFinish() {
        updateScore();
        finish();
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("考试结束")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create();
        alertDialog.setCancelable(false);
    }

    @Override
    public void answer(Boolean isTrue, View... views) {
        isAnswerTrue = isTrue;
        if (views[0] instanceof TextView) {
            tv_description = (TextView) views[0];
        }
        if (views[1] instanceof RadioButton) {
            selectedRB = (RadioButton) views[1];
        } else if (views[1] instanceof EditText) {
            edt_answer = (EditText) views[1];
        }
    }
}
