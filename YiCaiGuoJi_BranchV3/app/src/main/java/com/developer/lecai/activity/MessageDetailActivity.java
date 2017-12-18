package com.developer.lecai.activity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.developer.lecai.R;

public class MessageDetailActivity extends BaseActivity {

    private View view;
    private WebView webView;
    @Override
    public View getLayout() {
        view = View.inflate(MessageDetailActivity.this, R.layout.activity_message_detail,null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("消息");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String content= intent.getStringExtra("content");
        webView = (WebView) findViewById(R.id.wb_content);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(content);
    }


}
