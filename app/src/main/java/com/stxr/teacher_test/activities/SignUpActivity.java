package com.stxr.teacher_test.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.MyUser;
import com.stxr.teacher_test.entities.Student;
import com.stxr.teacher_test.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class SignUpActivity extends AppCompatActivity {
    public static final int SIGN_UP_SUCCESS_RESULT_CODE = 0;
    public static final String ID = "id";
    private static final String TAG = "SignUpActivity";

    @BindView(R.id.edt_id)
    EditText edt_name;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.btn_sign_up)
    Button btn_sign_up;
    private String name;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_sign_up)
    public void onClick(View v) {
        if (checkForm()) {
            //注册用户
            Student user = new Student();
            user.setUsername(name);
            user.setPassword(password);
            user.signUp(new MyUser.SaveListener<Student>() {
                @Override
                public void done(Student student, BmobException e) {
                    if (e == null) {
                        ToastUtil.show(SignUpActivity.this, R.string.success_sign_up);
                    } else if (e.getErrorCode() == 202) {
                        ToastUtil.show(SignUpActivity.this, R.string.username_already_taken);
                    } else {
                        Log.e(TAG, e.toString());
                    }
                }
            });
//            user.signUp(new SaveListener<Student>() {
//                @Override
//                public void done(Student student, BmobException e) {
//                    if (e == null) {
//                        ToastUtil.show(SignUpActivity.this, R.string.success_sign_up);
//                    } else if (e.getErrorCode() == 202) {
//                        ToastUtil.show(SignUpActivity.this, R.string.username_already_taken);
//                    } else {
//                        Log.e(TAG, e.toString());
//                    }
//                }
//            });
        } else {
            ToastUtil.show(this, R.string.error_sign_up);
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

}
