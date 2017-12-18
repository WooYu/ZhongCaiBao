package com.developer.lecai.bean;

import java.io.Serializable;

/**
 * Created by liuwei on 2017/8/20.
 */

public class NowBankCardBean implements Serializable {


    /**
     * autoid : 1
     * iconurl : 中国农业银行
     * bankcode : ABC
     * banname : 中国农业银行
     * cardcode : 123456789
     */

    private int autoid;
    private String iconurl;
    private String bankcode;
    private String bankname;
    private String cardcode;

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getCardcode() {
        return cardcode;
    }

    public void setCardcode(String cardcode) {
        this.cardcode = cardcode;
    }
}
