package com.developer.lecai.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.developer.lecai.R;
import com.developer.lecai.fragment.WebViewFragment;

public class PeiLvShuoMingActivity extends BaseActivity {

    private View view;
    private WebViewFragment webViewFragment;

    private String mOddsrateurl;
    @Override
    public View getLayout() {

        view = View.inflate(PeiLvShuoMingActivity.this, R.layout.activity_pei_lv_shuo_ming,null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("赔率说明");
        tvTitle.setTextColor(Color.parseColor("#ffffff"));
        webViewFragment = new WebViewFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_peilv, webViewFragment).commit();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
    @Override
    protected void onResume() {
        super.onResume();
        mOddsrateurl = getIntent().getStringExtra("oddsrateurl");
        webViewFragment.loadUrl(mOddsrateurl);
    }
}
