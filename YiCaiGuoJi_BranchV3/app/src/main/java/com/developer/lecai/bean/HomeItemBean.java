package com.developer.lecai.bean;

import java.io.Serializable;

/**
 * Created by raotf on 2017/7/3.
 */

public class HomeItemBean implements Serializable{

    private String img;
    private String cpname;
    private String tips;
    private String status;
    private String cpcode;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return cpname;
    }

    public void setTitle(String cpname) {
        this.cpname = cpname;
    }

    public String getDesc() {
        return tips;
    }

    public void setDesc(String tips) {
        this.tips = tips;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCpcode() {
        return cpcode;
    }

    public void setCpcode(String cpcode) {
        this.cpcode = cpcode;
    }
}
