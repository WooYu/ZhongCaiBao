package com.developer.lecai.bean;

/**
 * Created by liuwei on 2017/7/24.
 */

public class ChatRoomIdBean {


    /**
     * id : 20893307830273
     * name : bj-fir-vip1
     * owner : 8001
     * affiliations_count : 1
     */

    private String id;
    private String name;
    private String owner;
    private int affiliations_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getAffiliations_count() {
        return affiliations_count;
    }

    public void setAffiliations_count(int affiliations_count) {
        this.affiliations_count = affiliations_count;
    }
}
