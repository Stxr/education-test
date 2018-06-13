package com.stxr.teacher_test.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.stxr.teacher_test.fragments.QuestionFragment;
import com.stxr.teacher_test.utils.MyTimer;
import com.stxr.teacher_test.utils.ToastUtil;
import com.stxr.teacher_test.view.MyPagerAdapter;
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

/**
 * Created by stxr on 2018/3/31.
 */

public class QuestionActivity extends AppCompatActivity implements MyTimer.OnTimerCallBack {
    @BindView(R.id.vp_question)
    public MyViewPager viewPager;
    @BindView(R.id.btn_confirm)
    public Button btn_confirm;
    @BindView(R.id.tv_announce_admin)
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
    private MyPagerAdapter pagerAdapter;

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
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), questions);
        fragment = pagerAdapter.getFragment();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCanScroll(false);
    }

    //答题确定按钮
    @OnClick(R.id.btn_confirm)
    void onClick() {
        fragment = pagerAdapter.getFragment();
        tv_description = fragment.tv_description;
        edt_answer = fragment.edt_answer;
        question = fragment.question;
        rg_question = fragment.rg_question;
        selectedRB = getSelectedRB(rg_question);

        if (!question.isSelection()) {
            if (edt_answer.getText().toString().equals("")) {
                ToastUtil.show(this, "请填写选项.");
                return;
            }
        } else {
            if (selectedRB==null) {
                ToastUtil.show(this, "请填写选项");
                return;
            }
        }
        if (viewPager.getCurrentItem() == num - 1) {
            last = true;
        }
        if (paperType.equals(PaperType.PRACTICE)) {
            if (!last) {
                if (btn_confirm.getText().equals("确定")) {
                    if (isAnswerTrue()) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        right++;
                    } else {
                        setSelectionEnable(false);
                        tv_description.setVisibility(View.VISIBLE);
                        btn_confirm.setText("下一题");
                    }
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    btn_confirm.setText("确定");
                }
            } else {
                btn_confirm.setEnabled(false);
                if (isAnswerTrue()) {
                    right++;
                } else {
                    setSelectionEnable(false);
                    tv_description.setVisibility(View.VISIBLE);
                }
                ToastUtil.show(QuestionActivity.this, "没有了");
            }
        } else {
            if (isAnswerTrue()) right++;
            if (!last) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                updateScore();
                btn_confirm.setEnabled(false);
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setMessage("考试结束")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .create();
                alertDialog.show();
                alertDialog.setCancelable(false);
            }
        }
        recordDone(viewPager.getCurrentItem() + 1);
    }

    boolean isAnswerTrue() {
        if (question.isSelection()) {
            return question.getAnswer().equals(selectedRB.getText().toString());
        } else {
            return question.getAnswer().equals(edt_answer.getText().toString());
        }
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

    void setSelectionEnable(boolean enable) {
        if (question.isSelection()) {
            if (rg_question != null) {
                for (int i = 0; i < rg_question.getChildCount(); i++) {
                    rg_question.getChildAt(i).setEnabled(enable);
                }
            }
        } else {
            if (edt_answer != null) {
                edt_answer.setEnabled(enable);
            }
        }
    }

    private RadioButton getSelectedRB(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton childAt = (RadioButton) radioGroup.getChildAt(i);
            if (childAt.isChecked()) {
                return childAt;
            }
        }
        return null;
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
                    recordDone(0);
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
        double v = (double) Math.round(s * 100) / 100;
        Score score = new Score();
        score.setPaper(paper);
        score.setStudent(Student.getCurrentUser(this));
        score.setScore(v);
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
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        if (paperType == PaperType.EXAM) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("是否提前结束考试？考试成绩将上传")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateScore();
                            finish();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create();
            alertDialog.show();
            alertDialog.setCancelable(false);
        } else {
            super.onBackPressed();
        }
    }
}
