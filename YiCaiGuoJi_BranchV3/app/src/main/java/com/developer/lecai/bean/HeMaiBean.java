package com.developer.lecai.bean;

/**
 * Created by liuwei on 2017/8/12.
 */

public class HeMaiBean {
    private String title;
    private String createtime;
    private String username;
    private int status;
    private String lcode;

//    [{"status":5,"remark":"发起合买1份","num":1,"winfee":0}]
    private String remark;
    private String num;
    private String winfee;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getWinfee() {
        return winfee;
    }

    public void setWinfee(String winfee) {
        this.winfee = winfee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLcode() {
        return lcode;
    }

    public void setLcode(String lcode) {
        this.lcode = lcode;
    }
}
