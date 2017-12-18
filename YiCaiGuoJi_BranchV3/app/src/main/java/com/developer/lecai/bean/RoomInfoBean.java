package com.developer.lecai.bean;

/**
 * Created by liuwei on 2017/7/25.
 */

public class RoomInfoBean {


    /**
     * name : 回水厅
     * maxuser : 2000
     * vipmaxuser : 500
     * entermin : 0
     */

    private String name;
    private int maxuser;
    private int vipmaxuser;
    private int entermin;
    public String oddsrateurl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxuser() {
        return maxuser;
    }

    public void setMaxuser(int maxuser) {
        this.maxuser = maxuser;
    }

    public int getVipmaxuser() {
        return vipmaxuser;
    }

    public void setVipmaxuser(int vipmaxuser) {
        this.vipmaxuser = vipmaxuser;
    }

    public int getEntermin() {
        return entermin;
    }

    public void setEntermin(int entermin) {
        this.entermin = entermin;
    }
}
