package com.developer.lecai.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.bean.HomeDataBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.ImageUtil;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.SDFileHelper;

import okhttp3.Call;

public class ShowZhiFuBaoErWeiMaActivity extends BaseActivity {

    private View view;
    private ImageView iv_show_erweima;
    private TextView tv_show_baocun;
    private TextView tv_show_huizhidan;
    private HomeDataBean homeDataBean;
    private String money;
    private String imageUrl;

    @Override
    public View getLayout() {
        view = View.inflate(ShowZhiFuBaoErWeiMaActivity.this, R.layout.activity_show_er_wei_ma_zhifubao, null);
        return view;
    }

    @Override
    public void initView() {
        money = getIntent().getStringExtra("Money");
        tvTitle.setText("支付宝充值");
        iv_show_erweima = (ImageView) view.findViewById(R.id.iv_show_erweima);
        tv_show_baocun = (TextView) view.findViewById(R.id.tv_show_baocun);
        tv_show_huizhidan = (TextView) view.findViewById(R.id.tv_show_huizhidan);

    }

    @Override
    public void initListener() {
        iv_show_erweima.setOnClickListener(this);
        tv_show_baocun.setOnClickListener(this);
        tv_show_huizhidan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_show_baocun:
                if (!TextUtils.isEmpty(imageUrl)) {
                    SDFileHelper helper = new SDFileHelper(ShowZhiFuBaoErWeiMaActivity.this);
                    helper.savePicture("zhifubao.jpg", imageUrl);
                }
                break;
            case R.id.tv_show_huizhidan:
                Intent intent = new Intent();
                intent.setClass(this, ChongZhiHuiZhiFuBaoZhiActivity.class);
                intent.putExtra("Money",money);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void initData() {
        MsgController.getInstance().getHomeData(new HttpCallback(ShowZhiFuBaoErWeiMaActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {

                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                Log.e("首页数据", s + "------" + biz_content);
                if (state.equals("success")) {
                    homeDataBean = JsonUtil.parseJsonToBean(biz_content, HomeDataBean.class);
                    imageUrl = homeDataBean.getPayQR();
                    ImageUtil.setImage(imageUrl, iv_show_erweima);

                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
