package com.developer.lecai.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.developer.lecai.R;

public class NoticeDetailActivity extends BaseActivity {

    private View view;
    private TextView tv_notice_title,tv_notice_time,tv_notice_content;
    @Override
    public View getLayout() {
        view = View.inflate(NoticeDetailActivity.this, R.layout.activity_notice_detail,null);
        return view;
    }

    @Override
    public void initView() {

        tvTitle.setText("公告");

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content= intent.getStringExtra("content");
        content = content.replace("<p>","");
        content = content.replace("</p>","");
        tv_notice_title = (TextView) findViewById(R.id.tv_noticetitle);
        tv_notice_title.setText(title);

        tv_notice_content = (TextView) findViewById(R.id.tv_notice_content);
        tv_notice_content.setText(content);
    }
}
