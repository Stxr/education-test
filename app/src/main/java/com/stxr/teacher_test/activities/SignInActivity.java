package com.stxr.teacher_test.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.MyUser;

import java.util.logging.LogManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 初始界面
 */
public class SignInActivity extends AppCompatActivity {

    public static final int SIGN_IN_REQUEST_CODE = 110;
    private static final String TAG = "SignInActivity";
    @BindView(R.id.edt_id)
    EditText edt_id;
    @BindView(R.id.edt_password)
    EditText edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

    }

    /**
     * 登录
     */
    @OnClick(R.id.btn_sign_in)
    public void onClickSignIn() {
        MyUser user = new MyUser();
        user.setUsername(edt_id.getText().toString());
        user.setPassword(edt_password.getText().toString());
        user.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e != null) {
                    Log.e(TAG, e.toString());
                } else {
                    MainActivity.newInstance(SignInActivity.this);
                    finish();
                }
            }
        });
    }

    /**
     * 注册
     */
    @OnClick(R.id.tv_sign_up)
    public void onClickSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, SIGN_IN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE && resultCode == SignUpActivity.SIGN_UP_SUCCESS_RESULT_CODE) {
            edt_id.setText(data.getStringExtra(SignUpActivity.ID));
        }
    }
}
