package com.developer.lecai.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by songting on 17/8/28.
 */

public class HuishuiGuiZeBean implements Serializable {


    /**
     * state : success
     * biz_content : [{"id":1,"name":"回水厅","minfee":1000,"maxfee":10000,"backfee":10,"total":6},{"id":2,"name":"回水厅","minfee":10001,"maxfee":30000,"backfee":12,"total":6},{"id":3,"name":"回水厅","minfee":30001,"maxfee":500000,"backfee":15,"total":6},{"id":4,"name":"回水厅","minfee":500001,"maxfee":500000000,"backfee":18,"total":6},{"id":5,"name":"保本厅","minfee":10001,"maxfee":100000,"backfee":5,"total":6},{"id":6,"name":"高赔率厅","minfee":1,"maxfee":10000,"backfee":1,"total":6}]
     */

    private String state;
    /**
     * id : 1
     * name : 回水厅
     * minfee : 1000
     * maxfee : 10000
     * backfee : 10
     * total : 6
     */

    private List<BizContentEntity> biz_content;

    public void setState(String state) {
        this.state = state;
    }

    public void setBiz_content(List<BizContentEntity> biz_content) {
        this.biz_content = biz_content;
    }

    public String getState() {
        return state;
    }

    public List<BizContentEntity> getBiz_content() {
        return biz_content;
    }

    public  static class BizContentEntity implements Serializable{
        private int id;
        private String name;
        private int minfee;
        private int maxfee;
        private int backfee;
        private int total;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMinfee(int minfee) {
            this.minfee = minfee;
        }

        public void setMaxfee(int maxfee) {
            this.maxfee = maxfee;
        }

        public void setBackfee(int backfee) {
            this.backfee = backfee;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getMinfee() {
            return minfee;
        }

        public int getMaxfee() {
            return maxfee;
        }

        public int getBackfee() {
            return backfee;
        }

        public int getTotal() {
            return total;
        }
    }
}
