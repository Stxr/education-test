package com.stxr.teacher_test.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by stxr on 2018/3/31.
 */

public class ShareUtil {

    public static final String NAME = "ACCOUNT";

    /**
     * 存入数据
     * @param context
     * @param key
     * @param value
     */
    public static void put(Context context, String key,Object value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        editor.apply();
    }

    /**
     *  获取数据
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object get(Context context, String key,Object defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        if (defaultValue instanceof String) {
          return sharedPreferences.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultValue);
        } else {
            return null;
        }
    }

    /**
     * 删除一个值
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}
