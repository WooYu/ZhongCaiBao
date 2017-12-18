package com.developer.lecai.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.adapter.FFCAdapter;
import com.developer.lecai.adapter.MarkSixAdapter;
import com.developer.lecai.bean.BettedBean;
import com.developer.lecai.bean.FanDianBean;
import com.developer.lecai.bean.HomeItemBean;
import com.developer.lecai.bean.LastlotteryBean;
import com.developer.lecai.bean.MarkSixBallTypeBean;
import com.developer.lecai.bean.MarkSixCategoryBean;
import com.developer.lecai.bean.ShowBonusBean;
import com.developer.lecai.control.FanDianController;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.pop.MarkSixTitlePopup;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.utils.ShakeUtils;
import com.developer.lecai.view.CountdownView;
import com.developer.lecai.view.TToast;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.badgeview.BGABadgeView;
import okhttp3.Call;

import static com.developer.lecai.R.id.cdv_countdown;
import static com.developer.lecai.R.id.lv_recentlylottery;
import static com.developer.lecai.R.id.rl_title;
import static com.developer.lecai.R.id.tv_notes_rmb;

/**
 * 六合彩
 */
public class MarkSixActivity extends AppCompatActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_lotterystatus)
    TextView tvLotterystatus;
    @BindView(cdv_countdown)
    CountdownView cdvCountdown;
    @BindView(R.id.ll_countdown)
    LinearLayout llCountdown;
    @BindView(R.id.rl_recentlylottery)
    RelativeLayout rlRecentlylottery;
    @BindView(lv_recentlylottery)
    ListView lvRecentlyLottery;
    @BindView(R.id.tv_gamerule)
    TextView tvGamerule;
    @BindView(R.id.tv_selectnum)
    TextView tvSelectnum;
    @BindView(R.id.tv_losspercent)
    TextView tvLosspercent;
    @BindView(R.id.rv_numericalselection)
    RecyclerView rvNumericalselection;
    @BindView(tv_notes_rmb)
    TextView tvNotesRmb;
    @BindView(R.id.tv_joinlotteryarea)
    TextView tvJoinlotteryarea;
    @BindView(R.id.bgabadgeview)
    BGABadgeView bgabadgeview;
    @BindView(R.id.btn_machine_choose)
    Button btnMachineChoose;
    @BindView(R.id.btn_next_step)
    Button btnNextStep;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private final String[] mGameCategory_A = new String[]{
            "特码a", "特码b"
    };

    private final String[] mGameCategory_B = new String[]{
            "特码生肖", "特码种类", "正肖生肖", "总肖种类", "半波色波", "半半波色波", "七色波种类",
            "头尾数", "五行特殊", "正码数字", "平特尾数", "平特一肖"
    };

    private final String[] mGameCategory_C = new String[]{
            "特码色波"
    };

    private double mAmountPerNote = 2;//每注的金额
    private int mSelectNotes;//选中的注数
    private HomeItemBean mHomeItemBean;//首页每种玩法对应的信息
    private FFCAdapter ffcAdapter;//标题的Adapter
    private ShakeUtils mShakeUtils;//摇一摇
    private boolean isTopActivity;//activity是在栈顶
    private LastlotteryBean mNewestLotteryInfo;//最新一期彩票信息
    private LastlotteryBean mCurLotteryInfo;//当期一期彩票信息，有可能是过期的
    private List<BettedBean> mBettedList = new ArrayList<>();//已投注的集合
    private NormalDialog mClearLotteryAreaDialog;//倒计时结束后，需要弹出对话框提示是否清除数据
    private TimeCount timeCount;
    private MarkSixCategoryBean mCurGameRuleBean;//当前的彩票玩法
    private MarkSixTitlePopup mTitlePopup;//点击标题后的弹窗
    private List<MarkSixCategoryBean> mCategoryList;//六合彩游戏类型
    private List<MarkSixBallTypeBean> mBallList;//选注的种类
    private boolean showRecentylAward;//是否展示最近开奖记录
    private List<ShowBonusBean> mAwardRecordList;//最近开奖记录
    private MarkSixAdapter mMarkSixAdapter;
    private int mLayoutType;//布局类型
    private BettedBean mReEditBettedBean;//重新编辑的投注
    private int mReEditPosition;//重新编辑的位置
    private long mLastShakeTime;//上次摇一摇时间
    private PopupWindow mMenuPopWindow;//右上角弹窗
    private boolean menuIsShowing = false;
    public static final String ACTION_BETTINGLIST_MARKSIX = "com.developer.lecai.activity.MarkSixBettingListActivity";

    private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {
            cdvCountdown.setVisibility(View.GONE);
            tvLotterystatus.setText(R.string.selectnote_deciding);
        }
    }

    private BroadcastReceiver mBettingListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.equals(ACTION_BETTINGLIST_MARKSIX)) {
                return;
            }

            int type = intent.getIntExtra("type", 0);
            switch (type) {
                case 1://投注集合改变
                    mBettedList = (List<BettedBean>) intent.getSerializableExtra("bettinglist");
                    eraseData(false);
                    break;
                case 2://清空列表
                    mCurLotteryInfo = (LastlotteryBean) mNewestLotteryInfo.clone();
                    eraseData(true);
                    break;
                case 3://重新编辑投注
                    int editposition = intent.getIntExtra("editposition", -1);
                    refreshTheSelectedBall(editposition);
                    break;
                case 5://投注成功
                    MarkSixActivity.this.finish();
                    break;

                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_six);
        ButterKnife.bind(this);

        initializationData();
    }

    //初始化数据
    public void initializationData() {
        mHomeItemBean = (HomeItemBean) getIntent().getSerializableExtra("homeitembean");
        if (null == mHomeItemBean)
            return;

        initTitleAdapter();
        initTitlePopupWindow();
        initSharkItOff();
        requestNewestAwardTime();
        requestLotteryCategory();
        requestSystemRebates();
        registerMessageReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTopActivity = true;
        mShakeUtils.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTopActivity = false;
        mShakeUtils.onPause();
    }

    @Override
    protected void onDestroy() {
        unRegisterMessageReceiver();
        if (null != timeCount) {
            timeCount.cancel();
        }
        super.onDestroy();
    }

    @OnClick(R.id.iv_return)
    public void onIvReturnClicked() {
        finish();
    }

    @OnClick(R.id.tv_title)
    public void onTvTitleClicked() {
        if (null == mTitlePopup) {
            initTitlePopupWindow();
        }
        mTitlePopup.showAsDropDown(rlTitle);
    }

    @OnClick(R.id.iv_menu)
    public void onIvMenuClicked() {
        showMenu();
    }

    @OnClick(R.id.rl_recentlylottery)
    public void onRlRecentlylotteryClicked() {
        requestRecentlyAwardRecord();
    }

    @OnClick(R.id.tv_joinlotteryarea)
    public void onTvJoinlotteryareaClicked() {
        if (mSelectNotes <= 0) {
            TToast.show(this, R.string.selectnote_countabnormal, TToast.LENGTH_SHORT);
            return;
        }

        addNote();
    }

    @OnClick(R.id.btn_machine_choose)
    public void onBtnMachineChooseClicked() {
        if (null == mBallList || mBallList.size() == 0 || null == mMarkSixAdapter || null == mCurGameRuleBean) {
            return;
        }
        switchRuleOfGame(mCurGameRuleBean);
        int position;
        do {
            position = (int) (Math.random() * mBallList.size());
        } while (mBallList.get(position).isPitchon());

        mBallList.get(position).setPitchon(true);
        mMarkSixAdapter.notifyItemChanged(position);
        ++mSelectNotes;
        tvNotesRmb.setText(String.format(getString(R.string.marksix_notes_rmb),
                mSelectNotes, mSelectNotes * mAmountPerNote));
    }

    @OnClick(R.id.btn_next_step)
    public void onBtnNextStepClicked() {
        if (mSelectNotes <= 0 && (null == mBettedList || mBettedList.size() == 0)) {
            TToast.show(this, R.string.selectnote_countabnormal, TToast.LENGTH_SHORT);
            return;
        }

        if (mSelectNotes > 0) {
            addNote();
        }
        turn2BettingList();

    }

    private void initTitleAdapter() {
        ffcAdapter = new FFCAdapter(this);
        lvRecentlyLottery.setAdapter(ffcAdapter);
    }

    //初始化标题的popup
    private void initTitlePopupWindow() {
        if (null == mCategoryList || mCategoryList.size() == 0) {
            return;
        }
        mTitlePopup = new MarkSixTitlePopup(this);
        mTitlePopup.setData(mCategoryList);
        mTitlePopup.setOnClickPopItemListener(new MarkSixTitlePopup.OnClickPopItemListener() {
            @Override
            public void OnClickPopItem(MarkSixCategoryBean content) {
                switchRuleOfGame(content);
            }
        });
    }

    //摇一摇
    private void initSharkItOff() {
        mShakeUtils = new ShakeUtils(this);
        mShakeUtils.setOnShakeListener(new ShakeUtils.OnShakeListener() {
            @Override
            public void onShake() {
                long nowShakeTime = System.currentTimeMillis();
                if (nowShakeTime - mLastShakeTime < 2000) {
                    return;
                }
                mLastShakeTime = nowShakeTime;
                onBtnMachineChooseClicked();

            }
        });
    }

    //右上角菜单
    private void showMenu() {
        if (!menuIsShowing) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_service_record, null);
            mMenuPopWindow = new PopupWindow(contentView);
            mMenuPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            mMenuPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            //外部是否可以点击
            mMenuPopWindow.setBackgroundDrawable(new BitmapDrawable());
            mMenuPopWindow.setOutsideTouchable(true);
            contentView.findViewById(R.id.tv_service).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMenuPopWindow.dismiss();
                    Intent intent = new Intent(MarkSixActivity.this, KeFuActivity.class);
                    startActivity(intent);
                }
            });
            contentView.findViewById(R.id.tv_betrecord).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMenuPopWindow.dismiss();
                    Intent intent = new Intent(MarkSixActivity.this, MineTouZhuActivity.class);
                    startActivity(intent);
                }
            });
            contentView.findViewById(R.id.rl_bg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMenuPopWindow.dismiss();
                }
            });
            mMenuPopWindow.showAsDropDown(rlTitle);
        }
        menuIsShowing = !menuIsShowing;

    }

    //请求最新开奖时间
    private void requestNewestAwardTime() {
        if (null == mHomeItemBean)
            return;

        MsgController.getInstance().getLastlottery(mHomeItemBean.getCpcode(), new HttpCallback(this) {
            @Override
            public void onSuccess(Call call, String s) {

                String state = JsonUtil.getStringValue(s, "state");
                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                LogUtils.e("彩票期数和时间", biz_content);
                if (state.equals("success")) {
                    mNewestLotteryInfo = JsonUtil.parseJsonToBean(biz_content,
                            LastlotteryBean.class);
                    if (null == mNewestLotteryInfo)
                        return;

                    if (null == mCurLotteryInfo) {
                        mCurLotteryInfo = (LastlotteryBean) mNewestLotteryInfo.clone();
                    }
                    showClearLotteryAreaDialog();
                    setCountdownview(mCurLotteryInfo);
                } else if (state.equals("error")) {
                    Toast.makeText(MarkSixActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                    tvLotterystatus.setText(R.string.bet_deciding);
                    cdvCountdown.setVisibility(View.GONE);
                }
            }
        });
    }

    //请求游戏种类
    private void requestLotteryCategory() {
        if (null == mHomeItemBean)
            return;

        MsgController.getInstance().getLotteryPlays28(mHomeItemBean.getCpcode(), "0",
                new HttpCallback(this) {
                    @Override
                    public void onSuccess(Call call, String s) {
                        String state = JsonUtil.getStringValue(s, "state");
                        String biz_content = JsonUtil.getStringValue(s, "biz_content");
                        LogUtils.e("彩票玩法", biz_content);
                        if (state.equals("success")) {
                            mCategoryList = (List<MarkSixCategoryBean>) JsonUtil.parseJsonToList(biz_content,
                                    new TypeToken<List<MarkSixCategoryBean>>() {
                                    }.getType());
                            if (null != mCategoryList && mCategoryList.size() != 0) {
                                switchRuleOfGame(mCategoryList.get(0));
                            }
                        } else if (state.equals("error")) {
                            Toast.makeText(MarkSixActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //请求最近开奖记录
    private void requestRecentlyAwardRecord() {
        if (null == mHomeItemBean)
            return;

        if (showRecentylAward) {
            lvRecentlyLottery.setVisibility(View.GONE);
            showRecentylAward = false;
        } else {
            lvRecentlyLottery.setVisibility(View.VISIBLE);
            showRecentylAward = true;
            MsgController.getInstance().getOpenListByCode("1", mHomeItemBean.getCpcode(),
                    new HttpCallback(MarkSixActivity.this) {
                        @Override
                        public void onSuccess(Call call, String s) {
                            Log.e("按彩种获取开奖记录", "-----onItemClick---" + s);
                            String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                            String state = JsonUtil.getFieldValue(s, "state");
                            if ("success".equals(state)) {
                                mAwardRecordList = (List<ShowBonusBean>) JsonUtil
                                        .parseJsonToList(biz_content, new TypeToken<List<ShowBonusBean>>() {
                                        }.getType());
                                if (null == ffcAdapter) {
                                    ffcAdapter = new FFCAdapter(MarkSixActivity.this);
                                    lvRecentlyLottery.setAdapter(ffcAdapter);
                                }
                                ffcAdapter.setData(mAwardRecordList);
                            } else if (state.equals("error")) {
                                Toast.makeText(MarkSixActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    //请求系统返点
    private void requestSystemRebates() {
        MsgController.getInstance().getBackModelList(new HttpCallback(this) {
            @Override
            public void onSuccess(Call call, String s) {
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                Log.e("系统返点列表", biz_content);
                String state = JsonUtil.getFieldValue(s, "state");
                if ("success".equals(state)) {
                    List<FanDianBean> fanDianList = (List<FanDianBean>) JsonUtil.
                            parseJsonToList(biz_content, new TypeToken<List<FanDianBean>>() {
                            }.getType());
                    FanDianController.getInstance().setFanDianBean(fanDianList);
                }
            }
        });
    }

    //切换游戏规则
    private void switchRuleOfGame(MarkSixCategoryBean bean) {
        if (null == bean) {
            return;
        }
        mCurGameRuleBean = bean;
        //标题
        if (null != mCurGameRuleBean.getPname() && !"".equals(mCurGameRuleBean.getPname())) {
            tvTitle.setText(mCurGameRuleBean.getPname());
        } else {
            tvTitle.setText(R.string.title_selectnote);
        }
        //游戏说明
        if (null != mCurGameRuleBean.getPlayinfo() && !"".equals(mCurGameRuleBean.getPlayinfo())) {
            tvGamerule.setText(mCurGameRuleBean.getPlayinfo());
        } else {
            tvGamerule.setText("");
        }
        //注数
        mSelectNotes = 0;
        tvNotesRmb.setText(R.string.selectnote_notes_rmb_default);
        //设置选注号码
        GridLayoutManager gridLayoutManager = null;

        if (contain(mGameCategory_A, bean.getPname())) {
            mLayoutType = MarkSixAdapter.ITEM_A;
            gridLayoutManager = new GridLayoutManager(this, 5);
            tvSelectnum.setText(R.string.marksix_selectnum);
            tvLosspercent.setVisibility(View.VISIBLE);
        } else if (contain(mGameCategory_C, bean.getPname())) {
            mLayoutType = MarkSixAdapter.ITEM_C;
            gridLayoutManager = new GridLayoutManager(this, 1);
            tvSelectnum.setText(R.string.marksix_kind);
            tvLosspercent.setVisibility(View.GONE);
        } else {
            mLayoutType = MarkSixAdapter.ITEM_B;
            gridLayoutManager = new GridLayoutManager(this, 3);
            tvSelectnum.setText(R.string.marksix_selectnum);
            tvLosspercent.setVisibility(View.VISIBLE);
        }
        mBallList = new ArrayList<>();
        for (int i = 0; i < bean.getChildplays().size(); i++) {
            MarkSixBallTypeBean balltypebean = bean.getChildplays().get(i);
            balltypebean.setPitchon(false);
            mBallList.add(balltypebean);
        }
        rvNumericalselection.setLayoutManager(gridLayoutManager);
        mMarkSixAdapter = new MarkSixAdapter(this, mBallList, mLayoutType);
        mMarkSixAdapter.setItemClickLitener(new MarkSixAdapter.OnItemClickListener() {
            @Override
            public void clickItem(int position) {
                MarkSixBallTypeBean bean = mBallList.get(position);
                bean.setPitchon(!bean.isPitchon());
                if (bean.isPitchon()) {
                    ++mSelectNotes;
                } else {
                    --mSelectNotes;
                }
                tvNotesRmb.setText(String.format(getString(R.string.marksix_notes_rmb),
                        mSelectNotes, mSelectNotes * mAmountPerNote));
            }
        });
        rvNumericalselection.setAdapter(mMarkSixAdapter);
    }

    //清空投彩区的提示
    private void showClearLotteryAreaDialog() {
        if (null == mBettedList || mBettedList.size() == 0 || !isTopActivity) {
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
                            mCurLotteryInfo = (LastlotteryBean) mNewestLotteryInfo.clone();
                            eraseData(true);
                        }
                    });
        }

        if (!mClearLotteryAreaDialog.isShowing()) {
            mClearLotteryAreaDialog.show();
        }
    }

    //设置倒计时
    private void setCountdownview(LastlotteryBean lotteryBean) {
        if (null == lotteryBean) {
            return;
        }

        long remainTime = lotteryBean.getTime();
        tvLotterystatus.setText(String.format(getString(R.string.bet_countdown), lotteryBean.getIssuenum()));
        long sealtime = lotteryBean.getSealtimes();
        if (remainTime <= 0 || sealtime < 0 || remainTime <= sealtime) {
            tvLotterystatus.setText(R.string.bet_deciding);
            cdvCountdown.setVisibility(View.GONE);
            return;
        }

        cdvCountdown.setVisibility(View.VISIBLE);
        cdvCountdown.start(remainTime * 1000);
        timeCount = new TimeCount((remainTime - sealtime) * 1000, 1000);
        timeCount.start();
        cdvCountdown.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                requestNewestAwardTime();
            }
        });
    }

    //清除数据
    private void eraseData(boolean clearAll) {
        mAmountPerNote = 2;
        if (clearAll && null != mBettedList) {
            mBettedList.clear();
        }
        switchRuleOfGame(mCurGameRuleBean);
        setLotteryAreaNum();
    }

    //设置投彩区数量
    private void setLotteryAreaNum() {
        int areaSize = null == mBettedList ? 0 : mBettedList.size();
        if (areaSize == 0) {
            bgabadgeview.hiddenBadge();
        } else {
            bgabadgeview.showTextBadge(String.valueOf(areaSize));
        }
    }

    private boolean contain(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

    //计算赔率
    private double calculateTopBonus() {
        if (null == mBallList || mBallList.size() == 0 || null == mBallList.get(0).getContent()
                || "".equals(mBallList.get(0).getContent()))
            return 0;

        return Double.parseDouble(mBallList.get(0).getContent());
    }

    //获取投注接口的Content字段
    private String getContentOfBettedLottery() {
        if (null == mBallList || mSelectNotes < 0) {
            return "";
        }
        String modifierA = getString(R.string.symbol_58);
        String modifierB = getString(R.string.symbol_59);
        String modifierC = getString(R.string.symbol_44);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mBallList.size(); i++) {
            MarkSixBallTypeBean bean = mBallList.get(i);
            if (bean.isPitchon()) {
                stringBuilder.append(bean.getPname())
//                        .append(modifierA)
//                        .append((int)mAmountPerNote)
//                       .append(modifierB);
                .append(modifierC);
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(modifierC));
        LogUtils.d("content", stringBuilder.toString());
        return stringBuilder.toString();
    }

    //解析content字段获取选择的球
    private void analysisContent(String content) {
        if (null == content || "".equals(content))
            return;

        //去除前后的中括号
        String modifierA = getString(R.string.symbol_91);
        String modifierB = getString(R.string.symbol_93);
        String modifierC = getString(R.string.symbol_44);
        if (content.contains(modifierA)) {
            content = content.replace(modifierA, "");
        }
        if (content.contains(modifierB)) {
            content = content.replace(modifierB, "");
        }

        //用逗号分隔字符串，得到位数
        String[] spilt;
        if (content.contains(modifierC)) {
            spilt = content.split(modifierC);
        } else {
            spilt = new String[1];
            spilt[0] = content;
        }

        for (int i = 0; i < mBallList.size(); i++) {
            MarkSixBallTypeBean bean = mBallList.get(i);
            if (contain(spilt, bean.getPname())) {
                bean.setPitchon(true);
                ++mSelectNotes;
            }
        }
        mMarkSixAdapter.setData(mBallList, mLayoutType);
        tvNotesRmb.setText(String.format(getString(R.string.marksix_notes_rmb),
                mSelectNotes, mSelectNotes * mAmountPerNote));
    }

    //添加投注
    private void addNote() {
        double topbonus = calculateTopBonus();
        String content = getContentOfBettedLottery();
        if ("".equals(content)) {
            return;
        }
        if (null == mReEditBettedBean) {
            BettedBean bettedBean = new BettedBean();
            bettedBean.setPayFee(String.valueOf(mSelectNotes * mAmountPerNote));
            bettedBean.setNum(String.valueOf(mSelectNotes));
            bettedBean.setpMuch("1");//倍数，默认1
            bettedBean.setType(mCurGameRuleBean.getPids());
            bettedBean.setTypeName(mCurGameRuleBean.getPname());
            bettedBean.setMType("0");//0:元1:角 2 :分
            bettedBean.setModelName(String.valueOf(topbonus) + "/0%");
            bettedBean.setCPCode(mHomeItemBean.getCpcode());
            bettedBean.setCPName(mHomeItemBean.getTitle());
            bettedBean.setIssueNum(mNewestLotteryInfo.getIssuenum());
            bettedBean.setContent(content);
            bettedBean.setRoomName("");
            mBettedList.add(bettedBean);
        } else {
            mReEditBettedBean.setContent(content);
        }
        eraseData(false);
    }

    //跳转到投注列表
    private void turn2BettingList() {
        if (null == mBettedList || mBettedList.size() == 0 || null == mCurLotteryInfo
                || null == mHomeItemBean || null == mCurGameRuleBean) {
            return;
        }

        mReEditBettedBean = null;

        BettedBean defalutBetted = new BettedBean();
        defalutBetted.setPayFee(String.valueOf(mAmountPerNote));
        defalutBetted.setNum("1");
        defalutBetted.setpMuch("1");
        defalutBetted.setType(mCurGameRuleBean.getPids());
        defalutBetted.setTypeName(mCurGameRuleBean.getPname());
        defalutBetted.setMType("0");
        defalutBetted.setModelName(calculateTopBonus() + "/" + "0%");
        defalutBetted.setCPCode(mHomeItemBean.getCpcode());
        defalutBetted.setCPName(mHomeItemBean.getTitle());
        defalutBetted.setIssueNum(mCurLotteryInfo.getIssuenum());
        defalutBetted.setRoomName("");

        Intent intent = new Intent(this, MarkSixBettingListActivity.class);
        Bundle bundle = new Bundle();
        mCurLotteryInfo.setTime(cdvCountdown.getRemainTime() / 1000);
        bundle.putSerializable("lastlotteryinfo", mCurLotteryInfo);
        bundle.putSerializable("lotterylist", (Serializable) mBettedList);
        bundle.putSerializable("randomlottery", defalutBetted);
        bundle.putString("caitype", mHomeItemBean.getCpcode());
        bundle.putInt("ballquantity", mBallList.size());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //刷新选中的球
    private void refreshTheSelectedBall(int position) {
        mReEditPosition = position;
        if (-1 == mReEditPosition) {
            return;
        }

        mReEditBettedBean = mBettedList.get(mReEditPosition);
        double totalfee = Double.parseDouble(mReEditBettedBean.getPayFee());
        int notes = Integer.parseInt(mReEditBettedBean.getNum());
        mAmountPerNote = totalfee / notes;
        analysisContent(mReEditBettedBean.getContent());
    }

    //注册投注页面的广播
    private void registerMessageReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BETTINGLIST_MARKSIX);
        registerReceiver(mBettingListReceiver, intentFilter);
    }

    //注销投注界面的广播
    private void unRegisterMessageReceiver() {
        unregisterReceiver(mBettingListReceiver);
    }
}
