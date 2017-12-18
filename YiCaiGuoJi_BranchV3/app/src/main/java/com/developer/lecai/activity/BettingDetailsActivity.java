package com.developer.lecai.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.bean.BettingDetailBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.entiey.MyGameNotesEntity;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 投注详情
 */
public class BettingDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_particulars)
    TextView tvParticulars;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_serial)
    TextView tvSerial;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_period)
    TextView tvPeriod;
    @BindView(R.id.tv_endtime)
    TextView tvEndtime;
    @BindView(R.id.tv_gametype)
    TextView tvGametype;
    @BindView(R.id.tv_betnumber)
    TextView tvBetnumber;
    @BindView(R.id.tv_betamount)
    TextView tvBetamount;
    @BindView(R.id.tv_bonus)
    TextView tvBonus;
    private MyGameNotesEntity mBetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting_details);
        ButterKnife.bind(this);

        initData();
    }

    //初始化数据
    private void initData() {
        mBetData = (MyGameNotesEntity) getIntent().getSerializableExtra("bettingdata");
        requestBettingDetail();
    }

    @OnClick(R.id.iv_return)
    public void onViewClicked() {
        finish();
    }

    //请求投注详情
    private void requestBettingDetail() {
        if (null == mBetData) {
            return;
        }
        MsgController.getInstance().getBettingDetails(mBetData.getLcode(),
                new HttpCallback(this) {
                    @Override
                    public void onSuccess(Call call, String s) {
                        String state = JsonUtil.getStringValue(s, "state");
                        String biz_content = JsonUtil.getStringValue(s, "biz_content");

                        if (state.equals("success")) {
                            BettingDetailBean detailBean = JsonUtil.parseJsonToBean(biz_content,
                                    BettingDetailBean.class);
                            updateview(detailBean);
                        } else if (state.equals("error")) {
                            Toast.makeText(BettingDetailsActivity.this, biz_content,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //更新界面
    private void updateview(BettingDetailBean bettingDetailBean) {
        if (null == bettingDetailBean) {
            return;
        }

        //方案信息
        tvParticulars.setText(bettingDetailBean.getLcode());
        tvResult.setText(bettingDetailBean.getResultnum());
        tvSerial.setText(bettingDetailBean.getNum());
        tvTotal.setText(bettingDetailBean.getPayfee());
        tvPeriod.setText(bettingDetailBean.getIssuenum());
        tvEndtime.setText(bettingDetailBean.getOpentime());

        //方案内容
        tvGametype.setText(String.format(getString(R.string.bettingdetail_gametype)
                , mBetData.getTypename()));
        tvBetnumber.setText(dealiletou(mBetData.getContent()));
        tvBetamount.setText(String.format(getString(R.string.bettingdetail_amount)
                , bettingDetailBean.getPayfee()));
        tvBonus.setText(String.format(getString(R.string.bettingdetail_bonus)
                , bettingDetailBean.getWinfee()));
    }

    //处理开奖结果
    private String dealiletou(String result) {
        if (null == result || "".equals(result)) {
            return "";
        }

        if (result.contains(getString(R.string.symbol_91))
                && result.contains(getString(R.string.symbol_93))) {
            return result.replace(getString(R.string.symbol_91), "")
                    .replace(getString(R.string.symbol_93), "");
        }
        return result;
    }
}
