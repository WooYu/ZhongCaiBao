package com.developer.lecai.bean;

/**
 * Created by wy201 on 2017-08-22.
 * 合买实体
 */

public class ChippedBean {

//    否
//    BDNum:保底注数
//    ZFSNum:总分数
//    FenFee:每份额度
//    HMType:保密设置
//0 完全 1截止 2跟单 3保密
//    PlayUserNum:参与人数
//    ComNum:认购份数
//    TotalFee:总金额
//    StartNum:开始期数

    private int BDNum;
    private int ZFSNum;
    private double FenFee;
    private int HMType;
    private int PlayUserNum;
    private int ComNum;
    private double TotalFee;
    private String StartNum;

    public int getBDNum() {
        return BDNum;
    }

    public void setBDNum(int BDNum) {
        this.BDNum = BDNum;
    }

    public int getZFSNum() {
        return ZFSNum;
    }

    public void setZFSNum(int ZFSNum) {
        this.ZFSNum = ZFSNum;
    }

    public double getFenFee() {
        return FenFee;
    }

    public void setFenFee(double fenFee) {
        FenFee = fenFee;
    }

    public int getHMType() {
        return HMType;
    }

    public void setHMType(int HMType) {
        this.HMType = HMType;
    }

    public int getPlayUserNum() {
        return PlayUserNum;
    }

    public void setPlayUserNum(int playUserNum) {
        PlayUserNum = playUserNum;
    }

    public int getComNum() {
        return ComNum;
    }

    public void setComNum(int comNum) {
        ComNum = comNum;
    }

    public double getTotalFee() {
        return TotalFee;
    }

    public void setTotalFee(double totalFee) {
        TotalFee = totalFee;
    }

    public String getStartNum() {
        return StartNum;
    }

    public void setStartNum(String startNum) {
        StartNum = startNum;
    }
}
