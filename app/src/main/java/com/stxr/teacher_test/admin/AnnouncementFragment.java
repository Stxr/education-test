package com.stxr.teacher_test.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.Admin;
import com.stxr.teacher_test.entities.Announcement;
import com.stxr.teacher_test.fragments.SingleBaseFragment;
import com.stxr.teacher_test.utils.ToastUtil;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by stxr on 2018/5/4.
 */

public class AnnouncementFragment extends SingleBaseFragment {

    @BindView(R.id.edt_title)
    EditText edt_title;
    @BindView(R.id.edt_content)
    EditText edt_content;

    @Override
    public int layoutResId() {
        return R.layout.fragment_announcement;
    }

    @Override
    protected String title() {
        return "发布公告";
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_publish:
                publish();
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initData(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        activity.getMenuInflater().inflate(R.menu.publish, menu);
    }

    /**
     * 发布公告
     */
    void publish() {
        String title = edt_title.getText().toString();
        String content = edt_content.getText().toString();
        if (!title.equals("") && !content.equals("")) {
            Announcement announcement = new Announcement();
            announcement.setTitle(title);
            announcement.setContent(content);
            announcement.setAdmin(BmobUser.getCurrentUser(Admin.class));
            announcement.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        ToastUtil.show(activity, "发布成功");
                        activity.popBackStack();
                    }
                }
            });
        } else {
            ToastUtil.show(activity, "请填写正确的信息");
        }
    }
}
