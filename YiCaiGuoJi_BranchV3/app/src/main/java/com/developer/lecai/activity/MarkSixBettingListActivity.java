package com.developer.lecai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.adapter.BettingListMarkSixAdapter;
import com.developer.lecai.bean.BettedBean;
import com.developer.lecai.bean.LastlotteryBean;
import com.developer.lecai.control.MsgController;
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
import butterknife.OnTextChanged;
import okhttp3.Call;

import static com.developer.lecai.activity.FenFenCaiActivity.ACTION_BETTINGLIST;
import static com.developer.lecai.activity.MarkSixActivity.ACTION_BETTINGLIST_MARKSIX;

public class MarkSixBettingListActivity extends AppCompatActivity {

    @BindView(R.id.iv_goback)
    ImageView ivGoback;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_countdown)
    TextView tvCountdown;
    @BindView(R.id.countdownview)
    CountdownView countdownview;
    @BindView(R.id.tv_picknum)
    TextView tvPicknum;
    @BindView(R.id.tv_randomnote)
    TextView tvRandomnote;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.et_unifiedamount)
    EditText etUnifiedamount;
    @BindView(R.id.rv_lottery)
    RecyclerView rvLottery;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    @BindView(R.id.tv_bet_detail)
    TextView tvBetDetail;
    @BindView(R.id.tv_betstart)
    TextView tvBetstart;

    private LastlotteryBean mLastLotteryBean;//当期期的彩票信息
    private BettedBean mDefaultBettedBean;
    private String mCpCode;
    private List<BettedBean> mLotteryList;
    private int mMaxBallNumber;//可下注的数量

    private LastlotteryBean mNewestLotteryBean;//最新期彩票信息
    private TimeCount timeCount;
    private NormalDialog mClearLotteryAreaDialog;
    private BettingListMarkSixAdapter bettingListAdapter;
    private double mSumTotal;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_six_betting_list);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (null == bundle)
            return;

        mLastLotteryBean = (LastlotteryBean) bundle.getSerializable("lastlotteryinfo");
        mDefaultBettedBean = (BettedBean) bundle.getSerializable("randomlottery");
        mLotteryList = (List<BettedBean>) bundle.getSerializable("lotterylist");
        mCpCode = bundle.getString("caitype");
        mMaxBallNumber = bundle.getInt("ballquantity");

        setCountdownview(mLastLotteryBean);
        setSummation();
        setLotteryListAdapter();
    }

    @OnClick(R.id.iv_goback)
    public void onIvGobackClicked() {
        finish();
    }

    @OnClick(R.id.tv_picknum)
    public void onTvPicknumClicked() {
        finish();
    }

    @OnClick(R.id.tv_randomnote)
    public void onTvRandomnoteClicked() {
     if (0 == mMaxBallNumber || null == mDefaultBettedBean || null == mLotteryList ||
                null == bettingListAdapter)
            return;

        int random = (int) (Math.random() * mMaxBallNumber);
        String randomcontent = getString(R.string.symbol_91) + random + getString(R.string.symbol_93);
        BettedBean randomBetted = (BettedBean) mDefaultBettedBean.clone();
        randomBetted.setContent(randomcontent);
        mLotteryList.add(randomBetted);
        // mDefaultBettedBean.setContent(getString(R.string.symbol_91) + random + getString(R.string.symbol_93));
       // mLotteryList.add(mDefaultBettedBean);·
        bettingListAdapter.setData(mLotteryList);
        setSummation();

    }

    @OnClick(R.id.tv_empty)
    public void onTvEmptyClicked() {
        sendEmptyBroadCast();
    }

    @OnClick(R.id.tv_betstart)
    public void onTvBetstartClicked() {
        requestBalanceInquiry();
    }

    @OnTextChanged(value = R.id.et_unifiedamount, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void inputUnifiedAmount() {
        if(null == mLotteryList || null == bettingListAdapter){
            return;
        }

        String unifiedamountStr = etUnifiedamount.getText().toString().trim();
        if("".equals(unifiedamountStr)){
            return;
        }
        double unifiedamountDouble = Double.parseDouble(unifiedamountStr);
        for (int i = 0; i < mLotteryList.size(); i++) {
            BettedBean bettedBean = mLotteryList.get(i);
            bettedBean.setPayFee(String.valueOf(Integer.parseInt(bettedBean.getNum())*unifiedamountDouble));
        }
        bettingListAdapter.setData(mLotteryList);
        setSummation();
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

    //请求近期开奖记录
    private void requestRecentlyLotteryRecord() {
        if (null == mCpCode || "".equals(mCpCode)) {
            return;
        }
        MsgController.getInstance().getLastlottery(mCpCode, new HttpCallback(this) {
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
                    Toast.makeText(MarkSixBettingListActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                    tvCountdown.setText(R.string.bet_deciding);
                    countdownview.setVisibility(View.GONE);
                }
            }
        });
    }

    //请求查询余额
    private void requestBalanceInquiry() {
        MsgController.getInstance().getMoney(new HttpCallback(this) {
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
                        TToast.show(MarkSixBettingListActivity.this, R.string.bet_insufficientfunds, TToast.TIME_2);
                    }
                } else if (state.equals("error")) {
                    Toast.makeText(MarkSixBettingListActivity.this, biz_content, Toast.LENGTH_SHORT).show();
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
        String list = gson.toJson(mLotteryList);

        MsgController.getInstance().getTouZhu(totalfee, "", list, "", "",
                new HttpCallback(this) {
                    @Override
                    public void onSuccess(Call call, String s) {
                        LogUtils.d("投注结果", s);
                        String state = JsonUtil.getStringValue(s, "state");
                        String biz_content = JsonUtil.getStringValue(s, "biz_content");
                        if (state.equals("success")) {
                            sendBetSuccessBroadCast();
                        }else{
                            TToast.show(MarkSixBettingListActivity.this,biz_content,TToast.LENGTH_SHORT);
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

    //设置合计的金额，注数，期数，倍数
    private void setSummation() {
        mSumTotal = 0;
        int mNoteTotal = 0;

        if (null != mLotteryList && mLotteryList.size() != 0) {
            for (BettedBean bettedBean : mLotteryList) {
                mSumTotal += Double.parseDouble(bettedBean.getPayFee());
                mNoteTotal += Integer.parseInt(bettedBean.getNum());
            }
        }
        tvSum.setText(String.format(getString(R.string.bet_summation), mSumTotal));
        tvBetDetail.setText(String.format(getString(R.string.bet_notes_nper_multiple),
                mNoteTotal, 1, 1));

        sendBettingListChangeBroadCast();
    }

    //设置彩票列表的adapter
    private void setLotteryListAdapter() {
        bettingListAdapter = new BettingListMarkSixAdapter(this, mLotteryList);
        bettingListAdapter.setItemClickListener(new BettingListMarkSixAdapter.OnItemViewClickListener() {
            @Override
            public void onItemClick(int position) {
                sendEditBroadCast(position);
            }

            @Override
            public void deleteItem(int position) {
                sendDeleteBroadCast(position);
            }

            @Override
            public void changeAmount(int position, double amount) {
                BettedBean bettedBean = mLotteryList.get(position);
                int notes = Integer.parseInt(bettedBean.getNum());
                bettedBean.setPayFee(String.valueOf(notes * amount));
                bettingListAdapter.notifyItemChanged(position);
                setSummation();
            }
        });
        rvLottery.setLayoutManager(new LinearLayoutManager(this));
        rvLottery.setHasFixedSize(true);
        rvLottery.setAdapter(bettingListAdapter);

    }

    //发送投注列表改变的广播
    private void sendBettingListChangeBroadCast(){
        Intent intent = new Intent(ACTION_BETTINGLIST_MARKSIX);
        intent.putExtra("type", 1);
        intent.putExtra("bettinglist", (Serializable) mLotteryList);
        sendBroadcast(intent);
    }

    //发送清空列表的广播
    private void sendEmptyBroadCast() {
        Intent intent = new Intent(ACTION_BETTINGLIST_MARKSIX);
        intent.putExtra("type", 2);
        sendBroadcast(intent);
        finish();
    }

    //发送编辑条目的广播
    private void sendEditBroadCast(int position) {
        Intent intent = new Intent(ACTION_BETTINGLIST_MARKSIX);
        intent.putExtra("type", 3);
        intent.putExtra("editposition", position);
        sendBroadcast(intent);
        finish();
    }

    //发送删除条目的广播（实际是集合改变的广播）
    private void sendDeleteBroadCast(int position) {
        mLotteryList.remove(position);
        setSummation();
        sendBettingListChangeBroadCast();
        if (mLotteryList.size() == 0) {
            finish();
        }
    }

    //发布投注成功的广播
    private void sendBetSuccessBroadCast() {
        Intent intent = new Intent(ACTION_BETTINGLIST_MARKSIX);
        intent.putExtra("type", 5);
        sendBroadcast(intent);
        finish();
    }


}
