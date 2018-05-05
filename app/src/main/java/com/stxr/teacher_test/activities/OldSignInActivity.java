package com.stxr.teacher_test.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.admin.AccountType;
import com.stxr.teacher_test.entities.Student;
import com.stxr.teacher_test.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 初始界面
 */
public class OldSignInActivity extends AppCompatActivity {

    public static final int SIGN_IN_REQUEST_CODE = 110;
    private static final String TAG = "OldSignInActivity";
    public static final String TYPE = "type";
    private AccountType accountType;
    @BindView(R.id.edt_id)
    EditText edt_id;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.tv_accountType)
    TextView tv_accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        accountType = (AccountType) intent.getSerializableExtra(TYPE);
        if (accountType.equals(AccountType.ADMINISTRATOR)) {
            tv_accountType.setText("转到用户登录");
        } else {
            tv_accountType.setText("转到管理员登录");
        }
    }

    /**
     * 登录
     */
    @OnClick(R.id.btn_sign_in)
    public void onClickSignIn() {
        Student user = new Student();
        user.setUsername(edt_id.getText().toString());
        user.setPassword(edt_password.getText().toString());
//        user.login(new SaveListener<Student>() {
//            @Override
//            public void done(Student student, BmobException e) {
//                if (e != null) {
//                    ToastUtil.show(OldSignInActivity.this, e.getMessage());
//                } else {
//                    StudentActivity.newInstance(OldSignInActivity.this);
//                    finish();
//                }
//            }
//        });
    }


    @OnClick(R.id.tv_accountType)
    void changeAccountType() {
        if (accountType.equals(AccountType.ADMINISTRATOR)) {
            newInstance(this, AccountType.USER);
            finish();
        } else {
            newInstance(this, AccountType.ADMINISTRATOR);
            finish();
        }
    }

    /**
     * 注册
     */
    public void onClickSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, SIGN_IN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //成功注册返回
//        if (requestCode == SIGN_IN_REQUEST_CODE && resultCode == SignUpActivity.SIGN_UP_SUCCESS_RESULT_CODE) {
//            if (data != null) {
//                edt_id.setText(data.getStringExtra(SignUpActivity.ID));
//            }
//        }
    }

    public static void newInstance(Context context,AccountType type) {
        Intent intent = new Intent(context, OldSignInActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }
}
