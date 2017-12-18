package com.developer.lecai.activity;

import android.view.View;

import com.developer.lecai.R;

public class HeMaiJiLuActivity extends BaseActivity {


    private View view;

    @Override
    public View getLayout() {
        view = View.inflate(HeMaiJiLuActivity.this, R.layout.activity_he_mai_ji_lu,null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("合买记录");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
