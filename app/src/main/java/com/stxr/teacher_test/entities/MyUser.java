package com.stxr.teacher_test.entities;

import android.content.Context;
import android.util.Log;

import com.stxr.teacher_test.utils.ShareUtil;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by stxr on 2018/5/4.
 */

public class MyUser extends BmobObject {
    private String TAG = "MyUser";
    private String username;
    private String password;

    public void signUp(final SaveListener<Student> listener) {
        BmobQuery<Student> query = new BmobQuery<>();
        query.addWhereEqualTo("username", username);
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                if (list.size() > 0) {
                    listener.done(null, new BmobException(202, "用户名已存在"));
                } else {
                    save(new cn.bmob.v3.listener.SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            listener.done(null, null);
                        }
                    });
                }
            }
        });

    }

    public void login(final Context context ,final SaveListener<Student> listener) {
        BmobQuery<Student> query = new BmobQuery<>();
        query.addWhereEqualTo("username", username);
        query.addWhereEqualTo("password", password);
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                if (list == null) {
                    listener.done(null, new BmobException("登录失败,密码或用户名不正确"));
                } else {
                    listener.done(list.get(0), null);
                    cache(context, list.get(0));
                }
            }
        });
    }

    protected void cache(Context context, Student student) {
        ShareUtil.put(context, "objectId", student.getObjectId());
        ShareUtil.put(context, "username", student.getUsername());
        ShareUtil.put(context, "password", student.getPassword());
        ShareUtil.put(context, "groupId", student.getGroup().getObjectId());
    }

    /**
     * 更新缓存数据
     */
    protected void updateCache(final Context context, String studentId) {
        BmobQuery<Student> query = new BmobQuery<>();
        query.getObject(studentId, new QueryListener<Student>() {
            @Override
            public void done(Student student, BmobException e) {
                cache(context, student);
            }
        });
    }

    public interface SaveListener<T> {
        void done(T t, BmobException e);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
