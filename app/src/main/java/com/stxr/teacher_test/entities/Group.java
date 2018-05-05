package com.stxr.teacher_test.entities;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by stxr on 2018/5/4.\
 * 人分组
 */

public class Group extends BmobObject {
    private String name;
    private Admin admin;
    private BmobRelation papers;
    public Group() {

    }
    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return name;
    }

    public BmobRelation getPapers() {
        return papers;
    }

    public void setPapers(BmobRelation papers) {
        this.papers = papers;
    }

}
