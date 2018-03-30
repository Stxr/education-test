package com.stxr.teacher_test.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by stxr on 2018/3/30.
 */

public class Tool {
    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
