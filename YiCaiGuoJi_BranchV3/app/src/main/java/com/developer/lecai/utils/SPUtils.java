package com.developer.lecai.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.developer.lecai.app.XyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by liuwei on 2017/7/19.
 */

public class SPUtils {

    private static SharedPreferences sp;
    /**
     * 将字符串数据存入sp
     *
     * @param key
     * @param value
     */
    /**
     * 获取全局Context
     *
     * @return
     */
    public static Context getContext() {

        return XyApplication.appContext;
    }

    public static void set2SP(String key, Object value) {
        if (sp == null) {
            sp = getContext().getSharedPreferences("lecai", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (float) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(baos);
                oos.writeObject(value);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    baos.close();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String base64Product = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            editor.putString(key, base64Product);
        }
        editor.commit();
    }

    /**
     * 从sp中取出字符串数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public static Object get4SP(String key, Object defValue) {
        if (sp == null) {
            sp = getContext().getSharedPreferences("lecai", Context.MODE_PRIVATE);
        }
        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (float) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (int) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (long) defValue);
        } else {
            Object obj = null;
            try {
                String productString = sp.getString(key, "");
                byte[] base64Product = Base64.decode(productString, Base64.DEFAULT);
                ByteArrayInputStream bais = new ByteArrayInputStream(base64Product);
                ObjectInputStream ois = new ObjectInputStream(bais);
                obj = ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return obj;
        }
//        //找不到的话会返回默认的数值
//        return defValue;
    }




}
