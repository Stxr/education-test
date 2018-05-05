package com.stxr.teacher_test.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.gson.Gson;
import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.Admin;
import com.stxr.teacher_test.entities.Choices;
import com.stxr.teacher_test.entities.Group;
import com.stxr.teacher_test.entities.Paper;
import com.stxr.teacher_test.entities.Question;
import com.stxr.teacher_test.entities.QuestionBank;
import com.stxr.teacher_test.fragments.BaseFragment;
import com.stxr.teacher_test.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by stxr on 2018/5/4.
 */

public class CreateQuestionFragment extends BaseFragment {
    @BindView(R.id.edt_question_title)
    EditText edt_question_title;
    @BindView(R.id.edt_question_option1)
    EditText edt_question_option1;
    @BindView(R.id.edt_question_option2)
    EditText edt_question_option2;
    @BindView(R.id.edt_question_option3)
    EditText edt_question_option3;
    @BindView(R.id.edt_question_option4)
    EditText edt_question_option4;
    @BindView(R.id.edt_question_answer)
    EditText edt_question_answer;
    @BindView(R.id.edt_queston_description)
    EditText edt_question_description;
    @BindView(R.id.rb_selection_type)
    RadioButton rb_selection_type;

    @BindView(R.id.spinner_group)
    AppCompatSpinner spinner;
    private List<Choices> options = new ArrayList<>();
    private String title;
    private String answer;
    private String option4;
    private String option3;
    private String option2;
    private String option1;
    private String answer1;
    private String description;

    @Override
    public int layoutResId() {

        return R.layout.create_question;
    }

    @Override
    protected String title() {
        return "发布试题";
    }


    @Override
    protected void initData(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initData(inflater, container, savedInstanceState);
        updateSpinner();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        activity.getMenuInflater().inflate(R.menu.publish, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_publish:
                publish();
                break;
            case R.id.menu_create_paper:
                createPaper();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createPaper() {
        final EditText editText = new EditText(activity);
        editText.setHint("试卷名称");
        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("创建试卷")
                .setView(editText)
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null)
                .create();
        //必须先调用show方法
        dialog.show();
        //这样写是为了不让dialog消失
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                if (name.equals("")) {
                    ToastUtil.show(activity, "名称不能为空");
                } else {
                    Paper group = new Paper();
                    group.setName(name);
                    group.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                ToastUtil.show(activity, "创建试卷成功");
                                updateSpinner();
                                dialog.dismiss();
                            } else {
                                ToastUtil.show(activity, e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }

    private void updateSpinner() {
        BmobQuery<Paper> query = new BmobQuery<>();
        query.findObjects(new FindListener<Paper>() {
            @Override
            public void done(List<Paper> list, BmobException e) {
                ArrayAdapter<Paper> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item,list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
        });
    }
    private boolean check() {
        title = edt_question_title.getText().toString();
        answer1 = edt_question_answer.getText().toString();
        option1 = edt_question_option1.getText().toString();
        option2 = edt_question_option2.getText().toString();
        option3 = edt_question_option3.getText().toString();
        option4 = edt_question_option4.getText().toString();
        description = edt_question_description.getText().toString();
        return !title.equals("")
                && !answer1.equals("")
                && !description.equals("")
                && !option1.equals("")
                || !option2.equals("")
                || !option3.equals("")
                || !option4.equals("");
    }

    private void publish() {
        if (check()) {
            options.add(new Choices(option1));
            options.add(new Choices(option2));
            options.add(new Choices(option3));
            options.add(new Choices(option4));
            Question question = new Question();
            question.setAnswer(answer1);
            question.setQuestion(title);
            question.setDescription(description);
            question.setSelection(rb_selection_type.isChecked());
            question.setChoices(options.toArray(new Choices[options.size()]));
            options.clear();
            Gson gson = new Gson();
            String s = gson.toJson(question);

            QuestionBank questionBank = new QuestionBank();
            questionBank.setQuestion(s);
            BmobRelation relation = new BmobRelation();
            relation.add(spinner.getSelectedItem());
            questionBank.setPaper(relation);

            questionBank.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        ToastUtil.show(activity, "添加成功");
                    }
                }
            });
        } else {
            ToastUtil.show(activity,"信息填写不正确");
        }
    }
}
