package com.stxr.teacher_test.entities;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by stxr on 2018/5/4.
 * 试卷
 */

public class Paper extends BmobObject{
    private String name;
    private BmobRelation own;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobRelation getOwn() {
        return own;
    }

    public void setOwn(BmobRelation own) {
        this.own = own;
    }

    @Override
    public String toString() {
        return name;
    }
}
