package com.developer.lecai.bean;

import java.io.Serializable;

/**
 * Created by wy201 on 2017-08-22.
 */

public class AfterNoBean implements Serializable{
//    否
//    BettNum:追号期数
//    BMuch:追号基础倍数
//    StartNum:开始期数
//    TotalFee:总金额
//    Profits:利润比例率 1
//    JsonContent:追号期数字符串
//    例:追号期号,倍数|追号期号,倍数
//    BettType:追号模式0:利润率 1:同倍 2:翻倍
//            (APP固定传1)

    private String BettNum;
    private String BMuch;
    private String StartNum;
    private String TotalFee;
    private String Profits;
    private String JsonContent;
    private String BettType;

    public String getBettNum() {
        return BettNum;
    }

    public void setBettNum(String bettNum) {
        BettNum = bettNum;
    }

    public String getBMuch() {
        return BMuch;
    }

    public void setBMuch(String BMuch) {
        this.BMuch = BMuch;
    }

    public String getStartNum() {
        return StartNum;
    }

    public void setStartNum(String startNum) {
        StartNum = startNum;
    }

    public String getTotalFee() {
        return TotalFee;
    }

    public void setTotalFee(String totalFee) {
        TotalFee = totalFee;
    }

    public String getProfits() {
        return Profits;
    }

    public void setProfits(String profits) {
        Profits = profits;
    }

    public String getJsonContent() {
        return JsonContent;
    }

    public void setJsonContent(String jsonContent) {
        JsonContent = jsonContent;
    }

    public String getBettType() {
        return BettType;
    }

    public void setBettType(String bettType) {
        BettType = bettType;
    }
}
