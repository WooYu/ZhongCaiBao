package com.developer.lecai.activity;

import android.view.View;

import com.developer.lecai.R;

public class TouZhuJiLuActivity extends BaseActivity {


    @Override
    public View getLayout() {
        View view=View.inflate(TouZhuJiLuActivity.this,R.layout.activity_tou_zhu_ji_lu,null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("投注记录");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
