package com.developer.lecai.activity;

import android.view.View;

import com.developer.lecai.R;

public class HelpActivity extends BaseActivity {

    @Override
    public View getLayout() {
        View  view = View.inflate(HelpActivity.this, R.layout.activity_help,null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("帮助中心");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
