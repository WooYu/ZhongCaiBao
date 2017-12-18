package com.developer.lecai.activity;

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
import com.developer.lecai.utils.LogUtils;

import okhttp3.Call;

public class OurActivity extends BaseActivity {


    private ImageView iv_our_image;
    private TextView iv_our_banbenhao;
    private TextView iv_our_qq;

    @Override
    public View getLayout() {
        View  view = View.inflate(OurActivity.this, R.layout.activity_our,null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("关于我们");
        iv_our_image = (ImageView) findViewById(R.id.iv_our_image);
        iv_our_banbenhao = (TextView) findViewById(R.id.iv_our_banbenhao);
        iv_our_qq = (TextView) findViewById(R.id.iv_our_qq);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        MsgController.getInstance().getHomeData(new HttpCallback(OurActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {

                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                LogUtils.e("首页数据", s + "------" + biz_content);
                if (state.equals("success")) {
                    HomeDataBean  homeDataBean = JsonUtil.parseJsonToBean(biz_content, HomeDataBean.class);
                    ImageUtil.setRoundRectImage(homeDataBean.getIcon(),iv_our_image,R.color.white,R.color.white,2);
                    iv_our_banbenhao.setText(homeDataBean.getVerSion()+"");
                    iv_our_qq.setText(homeDataBean.getSysQQ()+"");
                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
