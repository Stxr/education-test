package com.stxr.teacher_test.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.activities.SplashActivity;
import com.stxr.teacher_test.activities.StudentActivity;
import com.stxr.teacher_test.admin.AccountType;
import com.stxr.teacher_test.admin.AdminActivity;
import com.stxr.teacher_test.admin.AdminFragment;
import com.stxr.teacher_test.admin.SignUpFragment;
import com.stxr.teacher_test.entities.Admin;
import com.stxr.teacher_test.entities.MyUser;
import com.stxr.teacher_test.entities.Student;
import com.stxr.teacher_test.utils.StudentUtil;
import com.stxr.teacher_test.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.stxr.teacher_test.activities.SplashActivity.WHAT;

/**
 * Created by stxr on 2018/5/3.
 */

public class SignInFragment extends SingleBaseFragment {

    public static final int SIGN_IN_REQUEST_CODE = 110;
    private static final String TAG = "OldSignInActivity";
    public static final String TYPE = "type";
    private boolean flag = true;
    private AccountType accountType;
    @BindView(R.id.edt_id)
    EditText edt_id;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.tv_accountType)
    TextView tv_accountType;

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT) {
                //判断跳转
                if (Student.getCurrentUser(activity) != null) {
                    StudentUtil.get().setOnCallBack(new StudentUtil.CallBack() {
                        @Override
                        public void onSuccess(StudentUtil studentUtil, boolean isSuccess) {
                            if (isSuccess && flag) {
                                flag = false;
                                activity.startActivity(StudentActivity.newInstance(activity));
                                activity.popBackStack();
                            } else {
                                Log.e(TAG, "isSuccess: " + isSuccess + "flag: " + flag);
                            }
                        }
                    });
                    StudentUtil.get().setStudent(Student.getCurrentUser(activity));
                } else {
                    startActivity(AdminActivity.newInstance(activity));
                    activity.popBackStack();
                }
            }
        }
    };

    @Override
    protected void initData(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initData(inflater, container, savedInstanceState);
        Bundle arguments = getArguments();
        accountType = (AccountType) arguments.getSerializable(TYPE);
        if (accountType.equals(AccountType.ADMINISTRATOR)) {
            tv_accountType.setText("转到用户登录");
        } else {
            tv_accountType.setText("转到管理员登录");
        }
    }

    @Override
    public int layoutResId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected String title() {
        if (accountType.equals(AccountType.ADMINISTRATOR)) {
            return "管理员登录";
        } else {
            return "学生登录";
        }
    }

    //    @OnClick(R.id.btn_sign_in)
    void signIn() {
        activity.addFragment(new SignUpFragment());
    }


    /**
     * 登录
     */
    @OnClick(R.id.btn_sign_in)
    public void onClickSignIn() {
        if (accountType.equals(AccountType.ADMINISTRATOR)) {
            Admin user = new Admin();
            user.setUsername(edt_id.getText().toString());
            user.setPassword(edt_password.getText().toString());
            user.login(new SaveListener<Student>() {
                @Override
                public void done(Student student, BmobException e) {
                    if (e != null) {
                        ToastUtil.show(activity, e.getMessage());
                    } else {
                        activity.replaceFragment(new AdminFragment());
                    }
                }
            });
        } else {
            Student student = new Student();
            student.setUsername(edt_id.getText().toString());
            student.setPassword(edt_password.getText().toString());
            student.login(activity, new MyUser.SaveListener<Student>() {
                @Override
                public void done(Student student, BmobException e) {
                    if (e == null) {
                        handler.sendEmptyMessage(WHAT);
//                        StudentUtil.get().setStudent(student);
//                        activity.startActivity(StudentActivity.newInstance(activity));
//                        activity.popBackStack();
                    } else {
                        ToastUtil.show(activity, e.getMessage());
                    }
                }
            });
        }
    }

    @OnClick(R.id.tv_accountType)
    void changeAccountType() {
        if (accountType.equals(AccountType.ADMINISTRATOR)) {
            activity.replaceFragment(newInstance(AccountType.USER));
        } else {
            activity.replaceFragment(newInstance(AccountType.ADMINISTRATOR));
        }
    }

    /**
     * 初始化
     *
     * @param type 登录类型
     * @return
     */
    public static SignInFragment newInstance(AccountType type) {
        SignInFragment fragment = new SignInFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }
}

