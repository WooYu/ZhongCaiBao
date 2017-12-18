package com.developer.lecai.bean;

import java.io.Serializable;

/**
 * Created by raotf on 2017/7/11.
 */

public class BankCardBean implements Serializable {

    private String iconurl;
    private String bankname;
    private String bankcode;

    public String getBankImg() {
        return iconurl;
    }

    public void setBankImg(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getBankTitle() {
        return bankname;
    }

    public void setBankTitle(String bankname) {
        this.bankname = bankname;
    }

    public String getBankDesc() {
        return bankcode;
    }

    public void setBankDesc(String bankcode) {
        this.bankcode = bankcode;
    }
}
