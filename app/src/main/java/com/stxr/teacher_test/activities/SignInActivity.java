package com.stxr.teacher_test.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stxr.teacher_test.R;

/**
 * 初始界面
 */
public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int SIGN_IN_REQUEST_CODE = 110;
    private TextView tv_find_password;
    private TextView tv_sign_up;
    private EditText edt_id;
    private EditText edt_password;
    private Button btn_sign_in;
    private String id;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();
        initData();
    }

    private void initData() {
        btn_sign_in.setOnClickListener(this);
        tv_sign_up.setOnClickListener(this);
    }

    private void initView() {
        tv_find_password = findViewById(R.id.tv_find_password);
        tv_sign_up = findViewById(R.id.tv_sign_up);
        edt_id = findViewById(R.id.edt_id);
        edt_password = findViewById(R.id.edt_password);
        btn_sign_in = findViewById(R.id.btn_sign_in);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登陆
            case R.id.btn_sign_in:
                if (edt_id.getText().toString().equals(id) && edt_password.getText().toString().equals(password)) {
                    Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                }
                MainActivity.newInstance(this);
                finish();
                break;
            //注册
            case R.id.tv_sign_up:
//                Intent intent = new Intent(this, SignUpActivity.class);
//                startActivityForResult(intent, SIGN_IN_REQUEST_CODE);
                break;
            default:
                break;

        }
    }



}
