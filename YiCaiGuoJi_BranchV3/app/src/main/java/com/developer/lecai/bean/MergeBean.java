package com.developer.lecai.bean;

/**
 * Created by liuwei on 2017/8/14.
 */

public class MergeBean {
    /**
     * orderid : d7bb7256-1de2-4faa-bf3d-300b404bf9c1
     * isbett : 1
     * iswincacle : 0
     * fenfee : 0.2
     * bdjd : 1.0
     * fsjd : 100.0
     * totalfee : 2.0
     * cpname : 三分彩
     * status : 5
     * username : 技术测试账号
     */

    private String orderid;
    private int isbett;
    private int iswincacle;
    private double fenfee;
    private double bdjd;
    private double fsjd;
    private double totalfee;
    private String cpname;
    private int status;
    private String username;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public int getIsbett() {
        return isbett;
    }

    public void setIsbett(int isbett) {
        this.isbett = isbett;
    }

    public int getIswincacle() {
        return iswincacle;
    }

    public void setIswincacle(int iswincacle) {
        this.iswincacle = iswincacle;
    }

    public double getFenfee() {
        return fenfee;
    }

    public void setFenfee(double fenfee) {
        this.fenfee = fenfee;
    }

    public double getBdjd() {
        return bdjd;
    }

    public void setBdjd(double bdjd) {
        this.bdjd = bdjd;
    }

    public double getFsjd() {
        return fsjd;
    }

    public void setFsjd(double fsjd) {
        this.fsjd = fsjd;
    }

    public double getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(double totalfee) {
        this.totalfee = totalfee;
    }

    public String getCpname() {
        return cpname;
    }

    public void setCpname(String cpname) {
        this.cpname = cpname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
