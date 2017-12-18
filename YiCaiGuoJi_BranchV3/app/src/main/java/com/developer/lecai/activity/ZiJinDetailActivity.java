package com.developer.lecai.activity;

import android.view.View;

import com.developer.lecai.R;

public class ZiJinDetailActivity extends BaseActivity {


    @Override
    public View getLayout() {
        View view=View.inflate(ZiJinDetailActivity.this,R.layout.activity_zi_jin_detail,null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("资金明细");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
