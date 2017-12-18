package com.developer.lecai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.adapter.RechargeChannelAdapter;
import com.developer.lecai.bean.PatternOfPaymentBean;
import com.developer.lecai.bean.RechargeTypeBean;
import com.developer.lecai.view.TToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充值渠道列表
 */
public class RechargeChannelAcitivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_channel)
    RecyclerView rvChannel;

    private String money;
    private RechargeTypeBean rechargeTypeBean;
    private List<PatternOfPaymentBean> mPaymentList;
    private RechargeChannelAdapter mChannelAdapter;
    private int selecPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_channel_acitivity);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        money = getIntent().getStringExtra("money");
        rechargeTypeBean = (RechargeTypeBean) getIntent().getSerializableExtra("rechargetype");

        if (null != rechargeTypeBean) {
            tvTitle.setText(rechargeTypeBean.getTypename());
            initView();
        }

    }

    private void initView() {
        rvChannel.setLayoutManager(new LinearLayoutManager(this));
        mPaymentList = rechargeTypeBean.getChilds();
        mChannelAdapter = new RechargeChannelAdapter(this, mPaymentList);
        mChannelAdapter.setCustomItemListener(new RechargeChannelAdapter.OnCustomItemListener() {
            @Override
            public void onItemClick(int position) {
                selecPosition = position;
                PatternOfPaymentBean bean = mPaymentList.get(position);
                for (PatternOfPaymentBean patternOfPaymentBean : mPaymentList) {
                    patternOfPaymentBean.setSelector(false);
                }
                bean.setSelector(true);
                mChannelAdapter.setmData(mPaymentList);
            }
        });
        rvChannel.setAdapter(mChannelAdapter);
    }

    @OnClick(R.id.iv_return)
    public void onIvReturnClicked() {
        finish();
    }

    @OnClick(R.id.bt_submit)
    public void onBtSubmitClicked() {
        if (-1 == selecPosition) {
            TToast.show(this, "请选择支付方式", TToast.TIME_2);
            return;
        }

        PatternOfPaymentBean bean = mPaymentList.get(selecPosition);
        Intent intent = new Intent();
        if ("bank".equals(bean.getTypecode())) {
            intent.setClass(this, BannerZhuanZhangActivity.class);
        } else {
            intent.setClass(this, ShowQRCodeActivity.class);
        }
        intent.putExtra("money", money);
        intent.putExtra("payment", bean);
        startActivity(intent);
    }
}
