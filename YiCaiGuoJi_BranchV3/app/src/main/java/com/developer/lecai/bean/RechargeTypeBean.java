package com.developer.lecai.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017-09-16.
 * 充值类型
 */

public class RechargeTypeBean implements Serializable{

    /**
     * childs : [{"childs":[],"paycode":"3","paytype":"微信扫码","typecode":"ayi","typename":"线上充值1号通道"},{"childs":[],"paycode":"4","paytype":"qq扫码","typecode":"ayi","typename":"线上充值1号通道"}]
     * paycode :
     * paytype :
     * typecode : ayi
     * typename : 线上充值1号通道
     */

    private String paycode;
    private String paytype;
    private String typecode;
    private String typename;
    private List<PatternOfPaymentBean> childs;

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

    public List<PatternOfPaymentBean> getChilds() {
        return childs;
    }

    public void setChilds(List<PatternOfPaymentBean> childs) {
        this.childs = childs;
    }
}
