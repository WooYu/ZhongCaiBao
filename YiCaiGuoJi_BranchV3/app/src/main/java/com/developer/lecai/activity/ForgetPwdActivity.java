package com.developer.lecai.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.developer.lecai.R;

public class ForgetPwdActivity extends BaseActivity {


    @Override
    public View getLayout() {
        return View.inflate(this, R.layout.activity_forget_pwd, null);
    }

    @Override
    public void initView() {

        tvTitle.setText("登录");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
