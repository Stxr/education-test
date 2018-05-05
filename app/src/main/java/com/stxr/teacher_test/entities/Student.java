package com.stxr.teacher_test.entities;

import android.content.Context;

import com.stxr.teacher_test.utils.ShareUtil;

import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by stxr on 2018/3/30.
 */

public class Student extends MyUser {
    private Group group;
    private BmobRelation papers;
    public static Student getCurrentUser(Context context) {
        String objectId = (String) ShareUtil.get(context, "objectId", "");
        String username = (String) ShareUtil.get(context, "username", "");
        String password = (String) ShareUtil.get(context, "password", "");
        String groupId = (String) ShareUtil.get(context, "groupId", "");
        if (!objectId.equals("") && !username.equals("") && !password.equals("")) {
            Student student = new Student();
            student.setObjectId(objectId);
            student.setUsername(username);
            student.setPassword(password);

            Group group = new Group();
            group.setObjectId(groupId);
            student.setGroup(group);

            return student;
        } else {
            return null;
        }
    }

    public static void logOut(Context context) {
        ShareUtil.clear(context);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
