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
import android.widget.Button;
import android.widget.EditText;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.Admin;
import com.stxr.teacher_test.entities.Group;
import com.stxr.teacher_test.entities.MyUser;
import com.stxr.teacher_test.entities.Student;
import com.stxr.teacher_test.fragments.SingleBaseFragment;
import com.stxr.teacher_test.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by stxr on 2018/5/3.
 */

public class SignUpFragment extends SingleBaseFragment {
    @BindView(R.id.edt_id)
    EditText edt_name;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.btn_sign_up)
    Button btn_sign_up;
    @BindView(R.id.spinner_group)
    AppCompatSpinner spinner;
    private String name;
    private String password;

    @Override
    protected void initData(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initData(inflater, container, savedInstanceState);
        updateSpinner();
    }

    private void updateSpinner() {
        BmobQuery<Group> query = new BmobQuery<>();
        query.findObjects(new FindListener<Group>() {
            @Override
            public void done(List<Group> list, BmobException e) {
                ArrayAdapter<Group> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item,list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_create_group:
                final EditText editText = new EditText(activity);
                editText.setHint("小组名称");
                final AlertDialog dialog = new AlertDialog.Builder(activity)
                        .setTitle("创建小组")
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
                            Group group = new Group(name);
                            group.setAdmin(BmobUser.getCurrentUser(Admin.class));
                            group.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        ToastUtil.show(activity, "创建小组成功");
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

                break;
            default:
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        activity.getMenuInflater().inflate(R.menu.create_group, menu);
    }

    @OnClick(R.id.btn_sign_up)
    public void onClick(View v) {
        if (checkForm()) {
            //注册用户
            Student user = new Student();
            user.setUsername(name);
            user.setPassword(password);
            Group group = (Group) spinner.getSelectedItem();
            user.setGroup(group);
            user.signUp(new MyUser.SaveListener<Student>() {
                @Override
                public void done(Student student, BmobException e) {
                    if (e == null) {
                        ToastUtil.show(activity, R.string.success_sign_up);
                        edt_name.setText(null);
                        edt_password.setText(null);
                    } else if (e.getErrorCode() == 202) {
                        ToastUtil.show(activity, R.string.username_already_taken);
                    } else {
                        Log.e(TAG, e.toString());
                    }
                }
            });
        } else {
            ToastUtil.show(activity, R.string.error_sign_up);
        }
    }

    /**
     * 对输入进行验证
     *
     * @return
     */
    private boolean checkForm() {
        name = edt_name.getText().toString();
        password = edt_password.getText().toString();
        boolean isPass = true;
        if (name.isEmpty()) {
            edt_name.setError("请输入姓名");
            isPass = false;
        } else {
            edt_name.setError(null);
        }
        if (password.isEmpty() || password.length() < 6) {
            edt_password.setError("至少填写6位数的密码");
            isPass = false;
        } else {
            edt_password.setError(null);
        }
        return isPass;
    }


    @Override
    public int layoutResId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected String title() {
        return "创建账号";
    }
}
