package com.developer.lecai.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;

import okhttp3.Call;

public class DaiLiHouTaiActivity extends BaseActivity {

    private View view;
    private TextView tv_total_money;
    private TextView tv_today_income;
    private RelativeLayout rl_income_analy;
    private RelativeLayout rl_user_infor;
    private RelativeLayout tv_proxy_protocol;

    @Override
    public View getLayout() {
        view = View.inflate(DaiLiHouTaiActivity.this, R.layout.activity_dai_li_hou_tai,null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("代理后台");
        tv_total_money = (TextView) view.findViewById(R.id.tv_total_money);
        tv_today_income = (TextView) view.findViewById(R.id.tv_today_income);
        rl_income_analy = (RelativeLayout) view.findViewById(R.id.rl_income_analy);
        rl_user_infor = (RelativeLayout) view.findViewById(R.id.rl_user_infor);
        tv_proxy_protocol = (RelativeLayout) view.findViewById(R.id.tv_proxy_protocol);
    }

    @Override
    public void initListener() {
        rl_income_analy.setOnClickListener(this);
        rl_user_infor.setOnClickListener(this);
        tv_proxy_protocol.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.rl_income_analy:
                intent.setClass(DaiLiHouTaiActivity.this, ShouYiFenXiActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_user_infor:
                intent.setClass(DaiLiHouTaiActivity.this, UserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_proxy_protocol:
                intent.setClass(DaiLiHouTaiActivity.this, DaiLiXieYiActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void initData() {

        MsgController.getInstance().getDaiLiHouTai(new HttpCallback(DaiLiHouTaiActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {

                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                Log.e("回执单", s + "------" + biz_content);
                if (state.equals("success")) {
                    String usercount = JsonUtil.getStringValue(biz_content, "usercount");
                    String registercount = JsonUtil.getStringValue(biz_content, "registercount");
                    tv_total_money.setText(usercount);
                    tv_today_income.setText(registercount);
                } else if (state.equals("error")) {
                    Toast.makeText(DaiLiHouTaiActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
