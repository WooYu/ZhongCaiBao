package com.developer.lecai.http;

/**
 * Created by raotf on 2017/6/2.
 */

public class ResJson {
    private int code;
    private String msg;
    private String resTime;
    private String resValue;


    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getResTime() {
        return resTime;
    }
    public void setResTime(String resTime) {
        this.resTime = resTime;
    }
    public String getResValue() {
        return resValue;
    }
    public void setResValue(String resValue) {
        this.resValue = resValue;
    }
}
