package com.stxr.teacher_test.entities;

import cn.bmob.v3.BmobObject;

/**
 * Created by stxr on 2018/5/4.
 */

public class Announcement extends BmobObject{
    private Admin admin;
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

}
