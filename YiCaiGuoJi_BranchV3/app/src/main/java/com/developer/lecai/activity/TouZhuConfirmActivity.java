package com.developer.lecai.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.adapter.BettingListAdapter;
import com.developer.lecai.bean.AfterNoBean;
import com.developer.lecai.bean.BettedBean;
import com.developer.lecai.bean.ChippedBean;
import com.developer.lecai.bean.LastlotteryBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.dialog.ChippedPopup;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.view.CountdownView;
import com.developer.lecai.view.TToast;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import okhttp3.Call;

import static com.developer.lecai.R.id.tv_countdown;
import static com.developer.lecai.R.id.tv_sum;
import static com.developer.lecai.activity.FenFenCaiActivity.ACTION_BETTINGLIST;

/**
 * Created by liuwei on 2017/7/7.
 * 投注
 */

public class TouZhuConfirmActivity extends AppCompatActivity {

    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.iv_goback)
    ImageView ivBack;
    @BindView(R.id.tv_chipped)
    TextView tvChipped;
    @BindView(tv_countdown)
    TextView tvCountdown;
    @BindView(R.id.countdownview)
    CountdownView countdownview;
    @BindView(R.id.tv_picknum)
    TextView tvPicknum;
    @BindView(R.id.tv_randomnote)
    TextView tvRandomnote;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.rv_lottery)
    RecyclerView rvLottery;
    @BindView(tv_sum)
    TextView tvSum;
    @BindView(R.id.tv_bet_detail)
    TextView tvBetDetail;
    @BindView(R.id.tv_planmultiple)
    TextView tvPlanmultiple;
    @BindView(R.id.tv_betstart)
    TextView tvBetstart;
    private Context mContext;

    private LastlotteryBean mLastLotteryBean;//当期期的彩票信息
    private BettedBean mDefaultBettedBean;
    private String mCpCode;
    private List<BettedBean> mLotteryList;

    private LastlotteryBean mNewestLotteryBean;//最新期彩票信息
    private TimeCount timeCount;
    private NormalDialog mClearLotteryAreaDialog;
    private BettingListAdapter bettingListAdapter;

    double mSumTotal = 0;//合计总金额
    int mNoteTotal = 0;//总共的投注数
    int mPeriodTotal = 1;//总共的期数
    int mMultiple = 1;//倍数

    private AfterNoBean mAfterNoBean;//追号
    private int mIsStart;//中奖后是否停止 0否 1是
    private ChippedBean mChippedBean;//合买

    private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {
            countdownview.setVisibility(View.GONE);
            tvCountdown.setText(R.string.selectnote_deciding);
        }
    }

    public static final String ACTION_SELECTLOTTERY = "com.developer.lecai.activity.FenFenCaiActivity";
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals(ACTION_SELECTLOTTERY))
                return;

            String randomcontent = intent.getStringExtra("randomcontent");
            BettedBean randomBetted = (BettedBean) mDefaultBettedBean.clone();
            randomBetted.setContent(randomcontent);
            mLotteryList.add(randomBetted);
            String typeName = null;
            for (int i = 0; i < mLotteryList.size(); i++) {
                BettedBean bettenBean = mLotteryList.get(i);
                typeName = bettenBean.getTypeName();
                if ((typeName.contains("定位胆")) && i == mLotteryList.size() - 1) {
                    Log.d("test", "投注信息：" + mLotteryList.get(i).getContent());
                    mLotteryList.get(i).setNum("5");
                    mLotteryList.get(i).setPayFee("10");
                }
                if (mLotteryList.get(i).getCPName().contains("北京")&& i == mLotteryList.size() - 1){
                    mLotteryList.get(i).setContent(mLotteryList.get(i).getContent().replace("0","10"));
                }
            }
            bettingListAdapter.setData(mLotteryList);
            setSummation();
            sendBettingListChangeBroadCast();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touzhuconfirm);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (null == bundle)
            return;

        mLastLotteryBean = (LastlotteryBean) bundle.getSerializable("lastlotteryinfo");
        mDefaultBettedBean = (BettedBean) bundle.getSerializable("randomlottery");
        mCpCode = bundle.getString("caitype");
        mLotteryList = (List<BettedBean>) bundle.getSerializable("lotterylist");

        registerMessageReceiver();
        setCountdownview(mLastLotteryBean);
        setSummation();
        setLotteryListAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;

        mAfterNoBean = (AfterNoBean) data.getSerializableExtra("afternobean");
        mIsStart = data.getIntExtra("isstart", 1);
        if (null == mAfterNoBean) {
            return;
        }

        mPeriodTotal = Integer.parseInt(mAfterNoBean.getBettNum());
        setSummation();
        tvPlanmultiple.setText(R.string.bet_selected_multiple);
        tvPlanmultiple.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        unRegisterMessageReceiver();
        super.onDestroy();
    }

    @Optional
    @OnClick(R.id.iv_goback)
    public void onIvBackClicked() {
        finish();
    }

    @Optional
    @OnClick(R.id.tv_chipped)
    public void onTvChippedClicked() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_chipped, null);
        ChippedPopup chippedPopup = ChippedPopup.getIntance(this, view, rlTitle);
        chippedPopup.showHiddenPopupWindow(mSumTotal);
        chippedPopup.setmSubmitListener(new ChippedPopup.SubmitListener() {
            @Override
            public void clickSubmit(ChippedBean bean) {
                bean.setStartNum(mLastLotteryBean.getIssuenum());
                mChippedBean = bean;
            }
        });
    }

    @Optional
    @OnClick(R.id.tv_picknum)
    public void onTvPicknumClicked() {
        finish();
    }

    @Optional
    @OnClick(R.id.tv_randomnote)
    public void onTvRandomnoteClicked() {

     /*   if (typeName.contains("五星定位胆")){
            //sendRandomNoteBroadCast();
            finish();
        }else */
        {
            sendRandomNoteBroadCast();
        }

    }

    @Optional
    @OnClick(R.id.tv_empty)
    public void onTvEmptyClicked() {
        sendEmptyBroadCast();
    }

    @Optional
    @OnClick(R.id.tv_planmultiple)
    public void onTvPlanmultipleClicked() {
        Intent intent = new Intent(this, AfterNoActivity.class);
        intent.putExtra("issue", null == mNewestLotteryBean ? mLastLotteryBean.getIssuenum() :
                mNewestLotteryBean.getIssuenum());
        intent.putExtra("baseamount", mSumTotal);
        startActivityForResult(intent, 0);
    }

    @Optional
    @OnClick(R.id.tv_betstart)
    public void onTvBetstartClicked() {
        requestBalanceInquiry();
    }

    //设置倒计时
    private void setCountdownview(LastlotteryBean lotteryBean) {
        if (null == lotteryBean) {
            return;
        }

        long remainTime = lotteryBean.getTime();
        tvCountdown.setText(String.format(getString(R.string.bet_countdown), lotteryBean.getIssuenum()));
        long sealtime = lotteryBean.getSealtimes();
        if (remainTime <= 0 || sealtime < 0 || remainTime <= sealtime) {
            tvCountdown.setText(R.string.bet_deciding);
            countdownview.setVisibility(View.GONE);
            return;
        }

        countdownview.setVisibility(View.VISIBLE);
        countdownview.start(remainTime * 1000);
        timeCount = new TimeCount((remainTime - sealtime) * 1000, 1000);
        timeCount.start();
        countdownview.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                requestRecentlyLotteryRecord();
            }
        });
    }

    //设置合计的金额，注数，期数，倍数
    private void setSummation() {
        mSumTotal = 0;
        mNoteTotal = 0;

        if (null != mLotteryList && mLotteryList.size() != 0) {
            for (BettedBean bettedBean : mLotteryList) {
                mSumTotal += Double.parseDouble(bettedBean.getPayFee());
                mNoteTotal += Integer.parseInt(bettedBean.getNum());
            }
        }
        tvSum.setText(String.format(getString(R.string.bet_summation), mSumTotal * mPeriodTotal));
        tvBetDetail.setText(String.format(getString(R.string.bet_notes_nper_multiple),
                mNoteTotal, mPeriodTotal, mMultiple));
    }

    //设置彩票列表的adapter
    private void setLotteryListAdapter() {
        bettingListAdapter = new BettingListAdapter(this, mLotteryList);
        bettingListAdapter.setItemClickListener(new BettingListAdapter.OnItemViewClickListener() {
            @Override
            public void onItemClick(int position) {
                sendEditBroadCast(position);
            }

            @Override
            public void deleteItem(int position) {
                sendDeleteBroadCast(position);
            }
        });
        rvLottery.setLayoutManager(new LinearLayoutManager(this));
        rvLottery.setHasFixedSize(true);
        rvLottery.setAdapter(bettingListAdapter);

    }

    //请求近期开奖记录
    private void requestRecentlyLotteryRecord() {
        if (null == mCpCode || "".equals(mCpCode)) {
            return;
        }
        MsgController.getInstance().getLastlottery(mCpCode, new HttpCallback(TouZhuConfirmActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                String state = JsonUtil.getStringValue(s, "state");
                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                LogUtils.e("彩票期数和时间", biz_content);
                if (state.equals("success")) {
                    mNewestLotteryBean = JsonUtil.parseJsonToBean(biz_content,
                            LastlotteryBean.class);
                    if (null == mNewestLotteryBean)
                        return;
                    showClearLotteryAreaDialog();
                    setCountdownview(mNewestLotteryBean);
                } else if (state.equals("error")) {
                    Toast.makeText(TouZhuConfirmActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //请求查询余额
    private void requestBalanceInquiry() {
        MsgController.getInstance().getMoney(new HttpCallback(TouZhuConfirmActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                LogUtils.d("余额查询", s);
                String state = JsonUtil.getStringValue(s, "state");
                String biz_content = JsonUtil.getStringValue(s, "biz_content");

                if (state.equals("success")) {
                    String accountfee = JsonUtil.getStringValue(biz_content, "accountfee");
                    if (Double.parseDouble(accountfee) >= mSumTotal) {
                        requestBetting();
                    } else {
                        TToast.show(TouZhuConfirmActivity.this, R.string.bet_insufficientfunds, TToast.TIME_2);
                    }
                } else if (state.equals("error")) {
                    Toast.makeText(TouZhuConfirmActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //请求投注
    private void requestBetting() {
        if (null == mLotteryList || mLotteryList.size() == 0) {
            return;
        }

        Gson gson = new Gson();

        String totalfee = String.valueOf(mSumTotal);
        String isstart = String.valueOf(mIsStart);
        String list = gson.toJson(mLotteryList);
        String hmclasss = "";
        String zhclasss = "";
        LogUtils.d("投注", list);
        if (null != mChippedBean) {
            hmclasss = gson.toJson(mChippedBean);
        }
        if (null != mAfterNoBean) {
            zhclasss = gson.toJson(mAfterNoBean);
        }

        MsgController.getInstance().getTouZhu(totalfee, isstart, list, hmclasss, zhclasss,
                new HttpCallback(TouZhuConfirmActivity.this) {
                    @Override
                    public void onSuccess(Call call, String s) {
                        LogUtils.d("投注结果", s);
                        String state = JsonUtil.getStringValue(s, "state");
                        String biz_content = JsonUtil.getStringValue(s, "biz_content");
                        Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_LONG).show();
                        if (state.equals("success")) {
                            sendBetSuccessBroadCast();
                        }
                    }
                });
    }

    //清空投彩区的提示
    private void showClearLotteryAreaDialog() {
        if (null == mLotteryList || mLotteryList.size() == 0) {
            return;
        }

        if (null == mClearLotteryAreaDialog) {
            mClearLotteryAreaDialog = new NormalDialog(this);
            mClearLotteryAreaDialog.content(getString(R.string.selectnote_clearlotterydialog_content))//
                    .style(NormalDialog.STYLE_TWO)//
                    .contentTextSize(12)
                    .btnTextSize(12, 12)
                    .titleTextSize(16);

            mClearLotteryAreaDialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            mClearLotteryAreaDialog.dismiss();
                        }
                    },
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            mClearLotteryAreaDialog.dismiss();
                            mLastLotteryBean = (LastlotteryBean) mNewestLotteryBean.clone();
                            onTvEmptyClicked();
                        }
                    });
        }

        if (!mClearLotteryAreaDialog.isShowing()) {
            mClearLotteryAreaDialog.show();
        }
    }

    //发送随机一注的广播
    private void sendRandomNoteBroadCast() {
        Intent intent = new Intent(ACTION_BETTINGLIST);
        intent.putExtra("type", 1);
        sendBroadcast(intent);
    }

    //发送清空列表的广播
    private void sendEmptyBroadCast() {
        Intent intent = new Intent(ACTION_BETTINGLIST);
        intent.putExtra("type", 2);
        sendBroadcast(intent);
        finish();
    }

    //发送编辑条目的广播
    private void sendEditBroadCast(int position) {
        Intent intent = new Intent(ACTION_BETTINGLIST);
        intent.putExtra("type", 3);
        intent.putExtra("editposition", position);
        sendBroadcast(intent);
        finish();
    }

    //发送集合改变的广播
    private void sendDeleteBroadCast(int position) {
        mLotteryList.remove(position);
        setSummation();
        sendBettingListChangeBroadCast();
        if (mLotteryList.size() == 0) {
            finish();
        }
    }

    //发送投注列表改变的广播
    private void sendBettingListChangeBroadCast() {
        Intent intent = new Intent(ACTION_BETTINGLIST);
        intent.putExtra("type", 4);
        intent.putExtra("bettinglist", (Serializable) mLotteryList);
        sendBroadcast(intent);
    }

    //发布投注成功的广播
    private void sendBetSuccessBroadCast() {
        Intent intent = new Intent(ACTION_BETTINGLIST);
        intent.putExtra("type", 5);
        // sendBroadcast(intent);  //发送广播 回到首页
        sendEmptyBroadCast();
        finish();
    }

    //注册投注页面的广播
    private void registerMessageReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SELECTLOTTERY);
        registerReceiver(mMessageReceiver, intentFilter);
    }

    //注销投注界面的广播
    private void unRegisterMessageReceiver() {
        unregisterReceiver(mMessageReceiver);
    }

}
