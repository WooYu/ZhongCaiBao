package com.developer.lecai.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.developer.lecai.R;

public class RechargeWFTActivity extends BaseActivity {

    private RelativeLayout rl_wx;
    private RelativeLayout rl_zfb;
    private RelativeLayout rl_qq;
    private CheckBox cb_wx;
    private CheckBox cb_zfb;
    private CheckBox cb_qq;
    private Button bt_submit;
    private String money;
    private int tag = 0;

    @Override
    public View getLayout() {
        return View.inflate(this, R.layout.activity_recharge_wft, null);
    }

    @Override
    public void initView() {
        tvTitle.setText("威富通");
        money = getIntent().getStringExtra("Money");
        rl_wx = (RelativeLayout) findViewById(R.id.rl_wx);
        rl_zfb = (RelativeLayout) findViewById(R.id.rl_zfb);
        rl_qq = (RelativeLayout) findViewById(R.id.rl_qq);
        cb_qq = (CheckBox) findViewById(R.id.cb_qq);
        cb_wx = (CheckBox) findViewById(R.id.cb_wx);
        cb_zfb = (CheckBox) findViewById(R.id.cb_zfb);
        bt_submit = (Button) findViewById(R.id.bt_submit);
    }

    @Override
    public void initListener() {
        rl_wx.setOnClickListener(this);
        rl_qq.setOnClickListener(this);
        rl_zfb.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.rl_wx:
                cb_wx.setChecked(true);
                cb_zfb.setChecked(false);
                cb_qq.setChecked(false);
                tag = 3;
                break;
            case R.id.rl_zfb:
                cb_wx.setChecked(false);
                cb_zfb.setChecked(true);
                cb_qq.setChecked(false);
                tag = 0;
                break;
            case R.id.rl_qq:
                cb_wx.setChecked(false);
                cb_zfb.setChecked(false);
                cb_qq.setChecked(true);
                tag = 4;
                break;
            case R.id.bt_submit:
                Intent intent = new Intent();
                intent.setClass(this, ShowQRCodeActivity.class);
                intent.putExtra("money", money);
                intent.putExtra("type", tag);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            finish();
        }
    }
}
