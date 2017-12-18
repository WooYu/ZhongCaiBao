package com.developer.lecai.bean;

/**
 * Created by liuwei on 2017/8/21.
 */

public class YouHuiBean {


    /**
     * autoid : 10
     * kname : paysendfee
     * kvalue : 1
     * info : 每日首充level1
     * status : 0
     * isshow : 1
     * minfee : 100.0
     * maxfee : 1000.0
     */

    private int autoid;
    private String kname;
    private String kvalue;
    private String info;
    private int status;
    private int isshow;
    private double minfee;
    private double maxfee;

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

    public String getKname() {
        return kname;
    }

    public void setKname(String kname) {
        this.kname = kname;
    }

    public String getKvalue() {
        return kvalue;
    }

    public void setKvalue(String kvalue) {
        this.kvalue = kvalue;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsshow() {
        return isshow;
    }

    public void setIsshow(int isshow) {
        this.isshow = isshow;
    }

    public double getMinfee() {
        return minfee;
    }

    public void setMinfee(double minfee) {
        this.minfee = minfee;
    }

    public double getMaxfee() {
        return maxfee;
    }

    public void setMaxfee(double maxfee) {
        this.maxfee = maxfee;
    }
}
