package com.developer.lecai.utils;

import android.util.Log;

/**
 * log工具类
 * machao
 */
public class LogUtils {

    public static final boolean ENABLE = true;
    private static final String TAG = "LogUtils";

    /**
     * 打印一个debug等级的 log
     */
    public static void d(String tag, String msg) {
        if (ENABLE) {
            Log.d("lecai:" + tag, msg);
        }
    }

    /**
     * 打印一个debug等级的 log
     */
    public static void e(String tag, String msg) {
        if (ENABLE) {
            Log.e("lecai:" + tag, msg);
        }
    }

    /**
     * 打印一个debug等级的 log
     */
    public static void e(Object obj, String msg) {
        if (ENABLE) {
            Log.e("lecai:" + obj.getClass().getSimpleName(), msg);
        }
    }

    /**
     * 获取stackTrace
     * @param e
     * @return
     */
    public static String getStackTraceString(Exception e) {
        if(ENABLE) {
            return Log.getStackTraceString(e);
        }
        return "";
    }
}
