package com.developer.lecai.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-09-16.
 * 支付方式
 */

public class PatternOfPaymentBean implements Serializable{

    /**
     * childs : []
     * paycode : 4
     * paytype : qq扫码
     * typecode : ayi
     * typename : 线上充值1号通道
     */

    private String paycode;
    private String paytype;
    private String typecode;
    private String typename;
    private boolean selector;

    public boolean isSelector() {
        return selector;
    }

    public void setSelector(boolean selector) {
        this.selector = selector;
    }

    public String getPaycode() {
        return paycode;
    }

    public void setPaycode(String paycode) {
        this.paycode = paycode;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}
