package com.developer.lecai.bean;

import java.io.Serializable;

/**
 * Created by liuwei on 2017/8/13.
 * 彩票开奖信息：期数、时间、状态
 */

public class LastlotteryBean implements Serializable,Cloneable{

    private String issuenum;
    private long time;
    private long sealtimes;
    private String status;

    public String getIssuenum() {
        return issuenum;
    }

    public void setIssuenum(String issuenum) {
        this.issuenum = issuenum;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getSealtimes() {
        return sealtimes;
    }

    public void setSealtimes(long sealtimes) {
        this.sealtimes = sealtimes;
    }

    @Override
    public Object clone(){
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        return o;
    }
}
