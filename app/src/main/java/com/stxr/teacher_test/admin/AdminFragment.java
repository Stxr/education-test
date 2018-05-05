package com.stxr.teacher_test.admin;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.ExamDate;
import com.stxr.teacher_test.entities.Group;
import com.stxr.teacher_test.fragments.BaseFragment;
import com.stxr.teacher_test.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by stxr on 2018/5/4.
 */

public class AdminFragment extends BaseFragment {

    private AppCompatSpinner spinner;

    @Override
    public int layoutResId() {
        return R.layout.fragment_admin;
    }

    @Override
    protected String title() {
        return null;
    }

    //发布公告
    @OnClick({R.id.btn_announcement,
            R.id.btn_create_account,
            R.id.btn_create_question,
            R.id.btn_distribute,
            R.id.btn_exam_date,
            R.id.btn_log_out})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_announcement:
                activity.addFragment(new AnnouncementFragment());
                break;
            case R.id.btn_create_account:
                activity.addFragment(new SignUpFragment());
                break;
            case R.id.btn_create_question:
                activity.addFragment(new CreateQuestionFragment());
                break;
            case R.id.btn_distribute:
                activity.addFragment(new DistributeFragment());
                break;
            case R.id.btn_exam_date:
                createDialog();
                break;
            case R.id.btn_log_out:
                logOut();
                break;
            default:
                break;
        }
    }

    private void logOut() {
        BmobUser.logOut();
        activity.popBackStack();
    }

    private void createDialog() {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_exam_date, null);
        final DatePicker datePicker = view.findViewById(R.id.dp_exam_date);
        spinner = view.findViewById(R.id.sp_group);
        updateGroup(spinner);
        new AlertDialog.Builder(activity)
                .setTitle("设置考试日期")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth();
                        int dayOfMonth = datePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, dayOfMonth).getTime();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
                        ExamDate examDate = new ExamDate();
                        examDate.setGroup((Group) spinner.getSelectedItem());
                        examDate.setDate(format.format(date));
                        examDate.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    ToastUtil.show(activity, "设置成功");
                                }
                            }
                        });

                    }
                })
                .setNegativeButton("取消",null)
                .create()
                .show();
    }

    private void updateGroup(final AppCompatSpinner spinner) {
        BmobQuery<Group> query = new BmobQuery<>();
        query.findObjects(new FindListener<Group>() {
            @Override
            public void done(List<Group> list, BmobException e) {
                ArrayAdapter<Group> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
        });
    }
}
