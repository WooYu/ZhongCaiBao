package com.developer.lecai.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-08-20.
 * 已投注
 */

public class BettedBean implements Serializable,Cloneable {
    //"PayFee":"50",PayFee 投注金额（总额）
    //    "Num":"25",Num:注数
    //    "pMuch":"1",pMuch:倍数，默认1
    //    "Type":"1ERX_2HE_3ZHIXFS",Type:玩法ID    pids
    //    "TypeName":"二星后二_直选复式",TypeName:玩法名称 pname
    //    "MType":0,MType:模式0:元1:角 2 :分
    //    "ModelName":"186/0%" “赔率/返利”奖金模式 (北京28 加拿大28 为字符串空 其他游戏必填)
    //    "CPCode":"CQSSC",CPCode:游戏代码
    //    "CPName":"重庆时时彩",CPName:游戏名称
    //    "IssueNum":"20170713071",IssueNum:期号
    //    "Content":"[\"56789\",\"13579\"]",Content:内容(北京28 加拿大28不需要前后[] 其他游戏必须有)
    //    "RoomName":""RoomName:房间名称(北京28 加拿大28 使用其他游戏为字符串空)

    private String PayFee;
    private String Num;
    private String pMuch;
    private String Type;
    private String TypeName;
    private String MType;
    private String ModelName;
    private String CPCode;
    private String CPName;
    private String IssueNum;
    private String Content;
    private String RoomName;


    public String getPayFee() {
        return PayFee;
    }

    public void setPayFee(String payFee) {
        PayFee = payFee;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getpMuch() {
        return pMuch;
    }

    public void setpMuch(String pMuch) {
        this.pMuch = pMuch;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getMType() {
        return MType;
    }

    public void setMType(String MType) {
        this.MType = MType;
    }

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public String getCPCode() {
        return CPCode;
    }

    public void setCPCode(String CPCode) {
        this.CPCode = CPCode;
    }

    public String getCPName() {
        return CPName;
    }

    public void setCPName(String CPName) {
        this.CPName = CPName;
    }

    public String getIssueNum() {
        return IssueNum;
    }

    public void setIssueNum(String issueNum) {
        IssueNum = issueNum;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
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
