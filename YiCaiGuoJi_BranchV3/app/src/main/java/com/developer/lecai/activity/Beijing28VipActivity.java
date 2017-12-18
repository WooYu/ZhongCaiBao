package com.developer.lecai.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.developer.lecai.R;


public class Beijing28VipActivity extends BaseActivity {

    private RelativeLayout rlVip1;

    @Override
    public View getLayout() {
        return View.inflate(this, R.layout.activity_beijing28_vip, null);
    }

    @Override
    public void initView() {
        rlVip1 = (RelativeLayout) findViewById(R.id.rl_vip_1);
    }

    @Override
    public void initListener() {
        rlVip1.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.rl_vip_1:
                intent.setClass(this, FangJianActivity.class);
                startActivity(intent);
                break;
        }
    }
}
