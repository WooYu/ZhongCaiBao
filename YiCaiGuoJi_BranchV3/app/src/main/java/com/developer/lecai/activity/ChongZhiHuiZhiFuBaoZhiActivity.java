package com.developer.lecai.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.bean.HomeDataBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.control.UserController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.DateUtil;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;

import okhttp3.Call;

public class ChongZhiHuiZhiFuBaoZhiActivity extends BaseActivity {

    private View view;
    private TextView tv_chongzhi_num;
    private TextView tv_chongzhi_bannername;
    private TextView tv_chongzhi_shoukuan;
    private TextView tv_chongzhi_shoukuannum;
    private TextView tv_chongzhi_money;
    private TextView tv_chongzhi_time;
    private TextView tv_chongzhi_zhuanzhang;
    private TextView tv_chongzhi_zhuanzhangnum;
    private String money;
    private TextView tv_chongzhi_commit;
    private HomeDataBean homeDataBean;

    @Override
    public View getLayout() {
        view = View.inflate(ChongZhiHuiZhiFuBaoZhiActivity.this, R.layout.chongzhi_huizhi_zhifubao,null);
        return view;
    }

    @Override
    public void initView() {
        money = getIntent().getStringExtra("Money");
        tvTitle.setText("充值信息");
        tv_chongzhi_num = (TextView) view.findViewById(R.id.tv_chongzhi_num);
        tv_chongzhi_bannername = (TextView) view.findViewById(R.id.tv_chongzhi_bannername);
        tv_chongzhi_shoukuan = (TextView) view.findViewById(R.id.tv_chongzhi_shoukuan);
        tv_chongzhi_shoukuannum = (TextView) view.findViewById(R.id.tv_chongzhi_shoukuannum);
        tv_chongzhi_money = (TextView) view.findViewById(R.id.tv_chongzhi_money);
        tv_chongzhi_time = (TextView) view.findViewById(R.id.tv_chongzhi_time);
        tv_chongzhi_zhuanzhang = (TextView) view.findViewById(R.id.tv_chongzhi_zhuanzhang);
        tv_chongzhi_zhuanzhangnum = (TextView) view.findViewById(R.id.tv_chongzhi_zhuanzhangnum);
        tv_chongzhi_commit = (TextView) view.findViewById(R.id.tv_chongzhi_commit);
    }

    @Override
    public void initListener() {
        tv_chongzhi_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_chongzhi_commit:

                MsgController.getInstance().getHuiZhi("支付宝", "", homeDataBean.getTrueName() + "", tv_chongzhi_zhuanzhangnum.getText().toString() + "", money, "支付宝", new HttpCallback(ChongZhiHuiZhiFuBaoZhiActivity.this) {
                    @Override
                    public void onSuccess(Call call, String s) {

                        String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                        String state = JsonUtil.getFieldValue(s, "state");
                        Log.e("回执单", s + "------" + biz_content);
                        if (state.equals("success")) {
                            Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (state.equals("error")) {
                            Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void initData() {
        tv_chongzhi_num.setText(UserController.getInstance().getLoginBean().getLoginname());
        tv_chongzhi_money.setText(money);
        tv_chongzhi_time.setText(DateUtil.getDateByFormat(3));
        MsgController.getInstance().getHomeData(new HttpCallback(ChongZhiHuiZhiFuBaoZhiActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {

                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                LogUtils.e("首页数据", s + "------" + biz_content);
                if (state.equals("success")) {
                    homeDataBean = JsonUtil.parseJsonToBean(biz_content, HomeDataBean.class);

                    tv_chongzhi_bannername.setText(homeDataBean.getBankName());
                    tv_chongzhi_shoukuan.setText(homeDataBean.getTrueName());
                    tv_chongzhi_shoukuannum.setText(homeDataBean.getCardCode());
                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
