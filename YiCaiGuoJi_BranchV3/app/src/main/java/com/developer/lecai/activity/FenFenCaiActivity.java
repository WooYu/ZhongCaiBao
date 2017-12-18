package com.developer.lecai.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.developer.lecai.adapter.ShiShiCaiAdapter;
import com.developer.lecai.bean.BettedBean;
import com.developer.lecai.bean.CaiPiaoWanFaBean;
import com.developer.lecai.bean.FanDianBean;
import com.developer.lecai.bean.HomeItemBean;
import com.developer.lecai.bean.LastlotteryBean;
import com.developer.lecai.bean.ShowBonusBean;
import com.developer.lecai.control.CheckNumberController;
import com.developer.lecai.control.FanDianController;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.dialog.LaTiaoDialog;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.pop.CaiPiaoPop;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.utils.ShakeUtils;
import com.developer.lecai.utils.SharedPreferencesUtils;
import com.developer.lecai.view.CountdownView;
import com.developer.lecai.view.TToast;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import cn.bingoogolapple.badgeview.BGABadgeView;
import okhttp3.Call;

import static com.developer.lecai.activity.TouZhuConfirmActivity.ACTION_SELECTLOTTERY;
import static com.developer.lecai.control.CheckNumberController.checkNumberNoToast;

/**
 * Created by liuwei on 2017/7/10.
 */

public class FenFenCaiActivity extends BaseActivity {

    private View view;
    private ImageView iv_return;
    private TextView tv_title;
    private RelativeLayout rl_title;
    private ImageView iv_menu;
    private LinearLayout ll_countview;
    private TextView tv_lotterystatus;
    private CountdownView cdv_countdown;
    private RelativeLayout rl_recentlylottery;
    private ListView lv_recentlylottery;
    private TextView tv_gamerule;
    private ListView lv_numericalselection;
    private TextView tv_notes_rmb;
    private TextView tv_jointlotteryarea;
    private BGABadgeView bgabadgeview;
    private Button btn_machine_choose;
    private Button btn_next_step;

    private int mAmountPerNote = 2;//每注的金额
    private List<ShowBonusBean> showBonusBeenList;
    private TimeCount timeCount;
    private List<String> titleList = new ArrayList<>();
    private List<CaiPiaoWanFaBean> mLotteryGameList;//标题显示的彩票玩法种类
    private CaiPiaoPop caiPiaoPop;//点击标题后的弹窗
    private CaiPiaoWanFaBean mCurGameRuleBean;//当前的彩票玩法
    private int[][] mAllLotteryNumbersArray;//所有的号码
    private boolean isShow = false;
    private FFCAdapter ffcAdapter;
    private ShiShiCaiAdapter shiShiCaiAdapter;
    private HomeItemBean mHomeItemBean;//首页每种玩法对应的信息
    private List<BettedBean> mBettedList = new ArrayList<>();//已投注的集合
    private LastlotteryBean mNewestLotteryInfo;//最新一期彩票信息
    private LastlotteryBean mCurlotteryInfo;//当期一期彩票信息，有可能是过期的
    private String mDefaultMultiplyPerNote;//每注默认的赔率
    private NormalDialog mClearLotteryAreaDialog;//倒计时结束后，需要弹出对话框提示是否清除数据
    private BettedBean mReEditBettedBean;//重新编辑的投注
    private int mReEditPosition;//重新编辑的位置
    private boolean isTopActivity;//activity是在栈顶
    private ShakeUtils mShakeUtils;//摇一摇
    private long mLastShakeTime;//上次摇一摇时间
    private PopupWindow mMenuPopWindow;//右上角弹窗
    private boolean menuIsShowing = false;

    protected static final String ACTION_BETTINGLIST = "com.developer.lecai.activity.TouZhuConfirmActivity";
    private BroadcastReceiver mBettingListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.equals(ACTION_BETTINGLIST)) {
                return;
            }

            int type = intent.getIntExtra("type", 0);
            switch (type) {
                case 1://随机一注
                    clickMachineSelection();
                    break;
                case 2://清空列表
                    mCurlotteryInfo = (LastlotteryBean) mNewestLotteryInfo.clone();
                    eraseData(true);
                    break;
                case 3://重新编辑投注
                    int editposition = intent.getIntExtra("editposition", -1);
                    refreshTheSelectedBall(editposition);
                    Log.d("test","返回的结果："+editposition);
                    break;
                case 4://投注集合改变
                    mBettedList = (List<BettedBean>) intent.getSerializableExtra("bettinglist");
                    eraseData(false);
                    break;
                case 5://投注成功
                    FenFenCaiActivity.this.finish();
                    break;
                default:
                    break;
            }

        }
    };

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {
            cdv_countdown.setVisibility(View.GONE);
            tv_lotterystatus.setText(R.string.selectnote_deciding);
        }
    }

    @Override
    public View getLayout() {
        view = View.inflate(FenFenCaiActivity.this, R.layout.activity_zhixuanfushi, null);
        return view;
    }

    @Override
    public void initView() {
        hideTitleBar();
        iv_return = (ImageView) view.findViewById(R.id.iv_return);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        iv_menu = (ImageView) view.findViewById(R.id.iv_menu);
        rl_title = (RelativeLayout) view.findViewById(R.id.rl_title);
        ll_countview = (LinearLayout) view.findViewById(R.id.ll_countdown);
        tv_lotterystatus = (TextView) view.findViewById(R.id.tv_lotterystatus);
        cdv_countdown = (CountdownView) view.findViewById(R.id.cdv_countdown);
        rl_recentlylottery = (RelativeLayout) view.findViewById(R.id.rl_recentlylottery);
        lv_recentlylottery = (ListView) view.findViewById(R.id.lv_recentlylottery);
        lv_numericalselection = (ListView) view.findViewById(R.id.lv_numericalselection);
        View headerview = LayoutInflater.from(this).inflate(R.layout.layout_numericalselect_headview, null);
        tv_gamerule = (TextView) headerview.findViewById(R.id.tv_gamerule);
        lv_numericalselection.addHeaderView(headerview);
        tv_notes_rmb = (TextView) view.findViewById(R.id.tv_notes_rmb);
        tv_jointlotteryarea = (TextView) view.findViewById(R.id.tv_joinlotteryarea);
        bgabadgeview = (BGABadgeView) view.findViewById(R.id.bgabadgeview);
        btn_machine_choose = (Button) view.findViewById(R.id.btn_machine_choose);
        btn_next_step = (Button) view.findViewById(R.id.btn_next_step);

    }

    @Override
    public void initListener() {
        iv_return.setOnClickListener(this);
        tv_title.setOnClickListener(this);
        iv_menu.setOnClickListener(this);
        rl_recentlylottery.setOnClickListener(this);
        tv_jointlotteryarea.setOnClickListener(this);
        btn_next_step.setOnClickListener(this);
        btn_machine_choose.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mHomeItemBean = (HomeItemBean) getIntent().getSerializableExtra("homeitembean");

        if (null == mHomeItemBean)
            return;
        SharedPreferencesUtils.putValue(getApplicationContext(), "GameTitle", mHomeItemBean.getTitle());
        ffcAdapter = new FFCAdapter(FenFenCaiActivity.this);
        lv_recentlylottery.setAdapter(ffcAdapter);

        shiShiCaiAdapter = new ShiShiCaiAdapter(FenFenCaiActivity.this);
        lv_numericalselection.setAdapter(shiShiCaiAdapter);
        shiShiCaiAdapter.setOnClickNumberListener(new ShiShiCaiAdapter.OnClickNumberListener() {
            @Override
            public void onClickNumber() {
                int countCai = checkNumberNoToast(FenFenCaiActivity.this,
                        mCurGameRuleBean, mAllLotteryNumbersArray);
                tv_notes_rmb.setText(String.format(getString(R.string.selectnote_notes_rmb),
                        countCai, countCai * mAmountPerNote));
            }
        });


        initSharkItOff();
        requestRecentlyLotteryRecord();
        requestLotteryGame();
        initTitlePopupWindow();
        registerMessageReceiver();
        requestSystemRebates();
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

    //初始化标题的popup
    private void initTitlePopupWindow() {
        caiPiaoPop = new CaiPiaoPop(FenFenCaiActivity.this);
        caiPiaoPop.setOnClickPopItemListener(new CaiPiaoPop.OnClickPopItemListener() {
            @Override
            public void OnClickPopItem(CaiPiaoWanFaBean content) {
                switchTitleName(content);
            }
        });
    }

    //切换标题(功能：清空选中的球、清空注数)
    private void switchTitleName(CaiPiaoWanFaBean content) {
        if (null == content)
            return;

        tv_notes_rmb.setText(R.string.selectnote_notes_rmb_default);

        LogUtils.e("将要切换的玩法名称", content.getPname());
        mCurGameRuleBean = content;
        String cPName = content.getPname();
        String cPDetail = content.getPlayinfo();
        tv_title.setText(cPName);
        tv_gamerule.setText(cPDetail);
        if (cPName.contains("五星通选-直选复式")) {
            mAllLotteryNumbersArray = new int[5][10];
            for (int i = 0; i < 5; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("万位");
            titleList.add("千位");
            titleList.add("百位");
            titleList.add("十位");
            titleList.add("个位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (cPName.contains("三星直选-后三")) {
            mAllLotteryNumbersArray = new int[3][10];
            for (int i = 0; i < 3; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("百位");
            titleList.add("十位");
            titleList.add("个位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (cPName.contains("三星组三-后三")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("组三");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (cPName.contains("三星组六-后三")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("组六");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (cPName.contains("二星直选-后二")) {
            mAllLotteryNumbersArray = new int[2][10];
            for (int i = 0; i < 2; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("十位");
            titleList.add("个位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (cPName.contains("二星组选-后二")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("组二");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (cPName.contains("不定胆-三星一码")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("任三");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (cPName.contains("不定胆-四星一码")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("任四");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (cPName.contains("不定胆-三星二码")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("不定位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (cPName.contains("不定胆-四星二码")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("不定位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (cPName.contains("不定胆-五星二码")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("不定位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (cPName.contains("不定胆-五星三码")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("不定位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (cPName.contains("定位胆-五星定位胆")) {
            mAllLotteryNumbersArray = new int[5][10];
            for (int i = 0; i < 5; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("万位");
            titleList.add("千位");
            titleList.add("百位");
            titleList.add("十位");
            titleList.add("个位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (cPName.contains("前三名-前三名")) {
            mAllLotteryNumbersArray = new int[3][10];
            for (int i = 0; i < 3; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("冠军");
            titleList.add("亚军");
            titleList.add("季军");
            //shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList,mHomeItemBean.getTitle());
        } else if (cPName.contains("前二名-前二名")) {
            mAllLotteryNumbersArray = new int[2][10];
            for (int i = 0; i < 2; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("冠军");
            titleList.add("亚军");
            //shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList,mHomeItemBean.getTitle());
        } else if (cPName.contains("冠军-冠军")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("冠军");
            //shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList,mHomeItemBean.getTitle());
        } else if (cPName.contains("定位胆-前五")) {
            mAllLotteryNumbersArray = new int[5][10];
            for (int i = 0; i < 5; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("冠军");
            titleList.add("亚军");
            titleList.add("第三名");
            titleList.add("第四名");
            titleList.add("第五名");
            //shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList,mHomeItemBean.getTitle());
        } else if (cPName.contains("定位胆-后五")) {
            mAllLotteryNumbersArray = new int[5][10];
            for (int i = 0; i < 5; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("六名");
            titleList.add("七名");
            titleList.add("八名");
            titleList.add("九名");
            titleList.add("十名");
            //shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList,mHomeItemBean.getTitle());
        } else {
            mAllLotteryNumbersArray = new int[5][10];
            for (int i = 0; i < 5; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("万位");
            titleList.add("千位");
            titleList.add("百位");
            titleList.add("十位");
            titleList.add("个位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        }
        caiPiaoPop.dismiss();
    }

    //请求近期开奖记录
    private void requestRecentlyLotteryRecord() {
        if (null == mHomeItemBean)
            return;

        MsgController.getInstance().getLastlottery(mHomeItemBean.getCpcode(), new HttpCallback(FenFenCaiActivity.this) {
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

                    if (null == mCurlotteryInfo) {
                        mCurlotteryInfo = (LastlotteryBean) mNewestLotteryInfo.clone();
                    }
                    showClearLotteryAreaDialog();

                    ll_countview.setVisibility(View.VISIBLE);
                    tv_lotterystatus.setText(String.format(getString(R.string.selectnote_countdown_period)
                            , mNewestLotteryInfo.getIssuenum()));
                    long finalTime = mNewestLotteryInfo.getTime();
                    long sealtimes = mNewestLotteryInfo.getSealtimes();
                    LogUtils.e("彩票时间查看", finalTime + "------------" + sealtimes);
                    startCountDown(finalTime, sealtimes);
                } else if (state.equals("error")) {
                    Toast.makeText(FenFenCaiActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                    ll_countview.setVisibility(View.GONE);
                }
            }
        });
    }

    //请求彩票的玩法
    private void requestLotteryGame() {
        if (null == mHomeItemBean)
            return;

        MsgController.getInstance().getLotteryPlays(mHomeItemBean.getCpcode(), new HttpCallback(FenFenCaiActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                String state = JsonUtil.getStringValue(s, "state");
                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                LogUtils.e("彩票玩法", biz_content);
                if (state.equals("success")) {
                    mLotteryGameList = (List<CaiPiaoWanFaBean>) JsonUtil.parseJsonToList(biz_content,
                            new TypeToken<List<CaiPiaoWanFaBean>>() {
                            }.getType());
                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (null == mLotteryGameList || mLotteryGameList.size() == 0) {
                    return;
                }

                String caiPiaoName = mLotteryGameList.get(0).getPname();
                String caiPiaoDetail = mLotteryGameList.get(0).getPlayinfo();
                tv_title.setText(caiPiaoName);
                tv_gamerule.setText(caiPiaoDetail);
                mCurGameRuleBean = mLotteryGameList.get(0);
                if (caiPiaoName.contains("五星通选-直选复式")) {
                    mAllLotteryNumbersArray = new int[5][10];
                    for (int i = 0; i < 5; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("万位");
                    titleList.add("千位");
                    titleList.add("百位");
                    titleList.add("十位");
                    titleList.add("个位");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("三星直选-后三")) {
                    mAllLotteryNumbersArray = new int[3][10];
                    for (int i = 0; i < 3; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("百位");
                    titleList.add("十位");
                    titleList.add("个位");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("三星组三-后三")) {
                    mAllLotteryNumbersArray = new int[1][10];
                    for (int i = 0; i < 1; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("组三");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("三星组六-后三")) {
                    mAllLotteryNumbersArray = new int[1][10];
                    for (int i = 0; i < 1; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("组六");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("二星直选-后二")) {
                    mAllLotteryNumbersArray = new int[2][10];
                    for (int i = 0; i < 2; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("十位");
                    titleList.add("个位");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("二星组选-后二")) {
                    mAllLotteryNumbersArray = new int[1][10];
                    for (int i = 0; i < 1; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("组二");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("不定胆-三星一码")) {
                    mAllLotteryNumbersArray = new int[1][10];
                    for (int i = 0; i < 1; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("任三");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("不定胆-四星一码")) {
                    mAllLotteryNumbersArray = new int[1][10];
                    for (int i = 0; i < 1; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("任四");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("不定胆-三星二码")) {
                    mAllLotteryNumbersArray = new int[1][10];
                    for (int i = 0; i < 1; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("不定位");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("不定胆-四星二码")) {
                    mAllLotteryNumbersArray = new int[1][10];
                    for (int i = 0; i < 1; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("不定位");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("不定胆-五星二码")) {
                    mAllLotteryNumbersArray = new int[1][10];
                    for (int i = 0; i < 1; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("不定位");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("不定胆-五星三码")) {
                    mAllLotteryNumbersArray = new int[1][10];
                    for (int i = 0; i < 1; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("不定位");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("定位胆-五星定位胆")) {
                    mAllLotteryNumbersArray = new int[5][10];
                    for (int i = 0; i < 5; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("万位");
                    titleList.add("千位");
                    titleList.add("百位");
                    titleList.add("十位");
                    titleList.add("个位");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("前三名-前三名")) {
                    mAllLotteryNumbersArray = new int[3][10];
                    for (int i = 0; i < 3; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("冠军");
                    titleList.add("亚军");
                    titleList.add("季军");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("前二名-前二名")) {
                    mAllLotteryNumbersArray = new int[2][10];
                    for (int i = 0; i < 2; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("冠军");
                    titleList.add("亚军");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("冠军-冠军")) {
                    mAllLotteryNumbersArray = new int[1][10];
                    for (int i = 0; i < 1; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("冠军");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("定位胆-前五")) {
                    mAllLotteryNumbersArray = new int[5][10];
                    for (int i = 0; i < 5; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("冠军");
                    titleList.add("亚军");
                    titleList.add("第三名");
                    titleList.add("第四名");
                    titleList.add("第五名");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else if (caiPiaoName.contains("定位胆-后五")) {
                    mAllLotteryNumbersArray = new int[5][10];
                    for (int i = 0; i < 5; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("六名");
                    titleList.add("七名");
                    titleList.add("八名");
                    titleList.add("九名");
                    titleList.add("十名");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                } else {
                    mAllLotteryNumbersArray = new int[5][10];
                    for (int i = 0; i < 5; i++) {
                        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        mAllLotteryNumbersArray[i] = a;
                    }
                    titleList.clear();
                    titleList.add("万位");
                    titleList.add("千位");
                    titleList.add("百位");
                    titleList.add("十位");
                    titleList.add("个位");
                    shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
                }
            }
        });
    }

    //请求系统返点
    private void requestSystemRebates() {
        MsgController.getInstance().getBackModelList(new HttpCallback(this) {
            @Override
            public void onSuccess(Call call, String s) {
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                Log.e("系统返点列表", s + "------" + biz_content);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.tv_title:
                if (caiPiaoPop == null) {
                    caiPiaoPop = new CaiPiaoPop(FenFenCaiActivity.this);
                }
                caiPiaoPop.setData(mLotteryGameList);
                caiPiaoPop.showAsDropDown(rl_title);
                break;
            case R.id.iv_menu:
                showMenu();
                break;
            case R.id.rl_recentlylottery://近期开奖
                clickRecentlyLottery();
                break;
            case R.id.tv_joinlotteryarea://加入投彩区
                clickLotteryArea();
                break;
            case R.id.btn_next_step:
                clickBet(true);
                break;
            case R.id.btn_machine_choose:
                clickMachineSelection();
                break;
        }
    }

    //右上角菜单
    private void showMenu() {
        if(!menuIsShowing){
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
                    Intent intent = new Intent(FenFenCaiActivity.this, KeFuActivity.class);
                    startActivity(intent);
                }
            });
            contentView.findViewById(R.id.tv_betrecord).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMenuPopWindow.dismiss();
                    Intent intent = new Intent(FenFenCaiActivity.this, MineTouZhuActivity.class);
                    startActivity(intent);
                }
            });
            contentView.findViewById(R.id.rl_bg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMenuPopWindow.dismiss();
                }
            });
            mMenuPopWindow.showAsDropDown(rl_title);
        }
        menuIsShowing = !menuIsShowing;

    }

    //点击加入投彩区
    private void clickLotteryArea() {
        clickBet(false);
    }

    //点击投注
    private void clickBet(final boolean turn2Bet) {
        if (null == mCurGameRuleBean || null == mAllLotteryNumbersArray)
            return;

        int selectnum = CheckNumberController.checkNumberNoToast(this,
                mCurGameRuleBean, mAllLotteryNumbersArray);

        if ((null == mBettedList || mBettedList.size() == 0) && 0 == selectnum) {
            TToast.show(this, R.string.selectnote_countabnormal, TToast.TIME_2);
            return;
        }

        if (!turn2Bet && 0 == selectnum && null != mBettedList && mBettedList.size() > 0) {
            TToast.show(this, R.string.selectnote_countabnormal, TToast.TIME_2);
            return;
        }

        if (turn2Bet && 0 == selectnum && null != mBettedList && mBettedList.size() > 0) {
            turn2BettingList();
            return;
        }

        int zhuShu = CheckNumberController.checkNumberNoToast(FenFenCaiActivity.this,
                mCurGameRuleBean, mAllLotteryNumbersArray);
        LaTiaoDialog laTiaoDialog = new LaTiaoDialog(FenFenCaiActivity.this, mCurGameRuleBean,
                zhuShu, mAmountPerNote);
        laTiaoDialog.setLeftRightBtnClickListener(new LaTiaoDialog.LeftRightBtnClickListener() {
            @Override
            public void rightClick(String payfee, String num, String mtype, String modename, String multiply,int mAmountPerNote) {
                String content = getContentOfBettedLottery();
                if (null == content || "".equals(content) || null == mNewestLotteryInfo) {
                    return;
                }
                if (mHomeItemBean.getTitle().contains("北京")){
                    content =  content.replaceAll("0","10");
                }
                mDefaultMultiplyPerNote = multiply;
                if(null == mReEditBettedBean){
                    BettedBean bettedBean = new BettedBean();
                    bettedBean.setPayFee(payfee);
                    bettedBean.setNum(num);
                    bettedBean.setpMuch(mAmountPerNote/2+"");
                    bettedBean.setType(mCurGameRuleBean.getPids());
                    bettedBean.setTypeName(mCurGameRuleBean.getPname());
                    bettedBean.setMType(mtype);
                    bettedBean.setModelName(modename);
                    bettedBean.setCPCode(mHomeItemBean.getCpcode());
                    bettedBean.setCPName(mHomeItemBean.getTitle());
                    bettedBean.setIssueNum(mNewestLotteryInfo.getIssuenum());
                    bettedBean.setContent(content);
                    bettedBean.setRoomName("");
                    mBettedList.add(bettedBean);
                }else{
                    mReEditBettedBean.setPayFee(payfee);
                    mReEditBettedBean.setNum(num);
                    mReEditBettedBean.setpMuch(mAmountPerNote/2+"");
                    mReEditBettedBean.setType(mCurGameRuleBean.getPids());
                    mReEditBettedBean.setTypeName(mCurGameRuleBean.getPname());
                    mReEditBettedBean.setMType(mtype);
                    mReEditBettedBean.setModelName(modename);
                    mReEditBettedBean.setCPCode(mHomeItemBean.getCpcode());
                    mReEditBettedBean.setCPName(mHomeItemBean.getTitle());
                    mReEditBettedBean.setIssueNum(mNewestLotteryInfo.getIssuenum());
                    mReEditBettedBean.setContent(content);
                    mReEditBettedBean.setRoomName("");
                }
                eraseData(false);

                if (turn2Bet)
                    turn2BettingList();
            }
        });
        laTiaoDialog.show();
    }

    //点击机选
    private void clickMachineSelection() {
        if (null == mCurGameRuleBean)
            return;

        String caiPiaoLeiName = mCurGameRuleBean.getPname();
        if (caiPiaoLeiName.contains("五星通选-直选复式")) {
            mAllLotteryNumbersArray = new int[5][10];
            for (int i = 0; i < 5; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("万位");
            titleList.add("千位");
            titleList.add("百位");
            titleList.add("十位");
            titleList.add("个位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("三星直选-后三")) {
            mAllLotteryNumbersArray = new int[3][10];
            for (int i = 0; i < 3; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("百位");
            titleList.add("十位");
            titleList.add("个位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("三星组三-后三")) {
            mAllLotteryNumbersArray = new int[1][10];
            int randomCount = (int) ((Math.random()) * 10);
            int randomCount1 = (int) ((Math.random()) * 10);
            randomCount1 = checkNumIsDiff(randomCount, randomCount1);
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                    if (randomCount1 == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("组三");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("三星组六-后三")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
              int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                //int[] reult2 = randomCommon(0,10,3);
                int[] reult2 = getRandomFromArray(a, 3);

                for (int x = 0; x < a.length; x++) {
                    if (reult2[0] == a[x]) {
                        a[x] = 100;
                    }
                    if (reult2[1] == a[x]) {
                        a[x] = 100;
                    }
                    if (reult2[2] == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("组六");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("二星直选-后二")) {
            mAllLotteryNumbersArray = new int[2][10];
            for (int i = 0; i < 2; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("十位");
            titleList.add("个位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("二星组选-后二")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                int randomCount1 = (int) ((Math.random()) * 10);
                randomCount1 = checkNumIsDiff(randomCount, randomCount1);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                    if (randomCount1 == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("组二");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("不定胆-三星一码")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("任三");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("不定胆-四星一码")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("任四");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("不定胆-三星二码")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                int randomCount1 = (int) ((Math.random()) * 10);
                randomCount1 = checkNumIsDiff(randomCount, randomCount1);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                    if (randomCount1 == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("不定位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("不定胆-四星二码")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                int randomCount1 = (int) ((Math.random()) * 10);
                randomCount1 = checkNumIsDiff(randomCount, randomCount1);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                    if (randomCount1 == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("不定位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("不定胆-五星二码")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                int randomCount1 = (int) ((Math.random()) * 10);
                randomCount1 = checkNumIsDiff(randomCount, randomCount1);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                    if (randomCount1 == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("不定位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("不定胆-五星三码")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                int randomCount1 = (int) ((Math.random()) * 10);
                randomCount1 = checkNumIsDiff(randomCount, randomCount1);
                int randomCount2 = (int) ((Math.random()) * 10);
                randomCount2 = checkNumIsDiff(randomCount, randomCount2);
                randomCount2 = checkNumIsDiff(randomCount1, randomCount2);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                    if (randomCount1 == a[x]) {
                        a[x] = 100;
                    }
                    if (randomCount2 == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("不定位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("定位胆-五星定位胆")) {
            mAllLotteryNumbersArray = new int[5][10];
            for (int i = 0; i < 5; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("万位");
            titleList.add("千位");
            titleList.add("百位");
            titleList.add("十位");
            titleList.add("个位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("前三名-前三名")) {
            mAllLotteryNumbersArray = new int[3][10];
            int randomCount = (int) ((Math.random()) * 10);
            int randomCount1 = (int) ((Math.random()) * 10);
            randomCount1 = checkNumIsDiff(randomCount, randomCount1);
            int randomCount2 = (int) ((Math.random()) * 10);
            randomCount2 = checkNumIsDiff(randomCount, randomCount2);
            randomCount2 = checkNumIsDiff(randomCount1, randomCount2);
            for (int i = 0; i < 3; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                for (int x = 0; x < a.length; x++) {
                    if (i == 0) {
                        if (randomCount == a[x]) {
                            a[x] = 100;
                        }
                    } else if (i == 1) {
                        if (randomCount1 == a[x]) {
                            a[x] = 100;
                        }
                    } else if (i == 2) {
                        if (randomCount2 == a[x]) {
                            a[x] = 100;
                        }
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("冠军");
            titleList.add("亚军");
            titleList.add("季军");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("前二名-前二名")) {
            mAllLotteryNumbersArray = new int[2][10];
            int randomCount = (int) ((Math.random()) * 10);
            int randomCount1 = (int) ((Math.random()) * 10);
            randomCount1 = checkNumIsDiff(randomCount, randomCount1);
            for (int i = 0; i < 2; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                for (int x = 0; x < a.length; x++) {
                    if (i == 0) {
                        if (randomCount == a[x]) {
                            a[x] = 100;
                        }
                    } else {
                        if (randomCount1 == a[x]) {
                            a[x] = 100;
                        }
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("冠军");
            titleList.add("亚军");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("冠军-冠军")) {
            mAllLotteryNumbersArray = new int[1][10];
            for (int i = 0; i < 1; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("冠军");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("定位胆-前五")) {
            mAllLotteryNumbersArray = new int[5][10];
            for (int i = 0; i < 5; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("冠军");
            titleList.add("亚军");
            titleList.add("第三名");
            titleList.add("第四名");
            titleList.add("第五名");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else if (caiPiaoLeiName.contains("定位胆-后五")) {
            mAllLotteryNumbersArray = new int[5][10];
            for (int i = 0; i < 5; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("六名");
            titleList.add("七名");
            titleList.add("八名");
            titleList.add("九名");
            titleList.add("十名");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        } else {
            mAllLotteryNumbersArray = new int[5][10];
            for (int i = 0; i < 5; i++) {
                int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                int randomCount = (int) ((Math.random()) * 10);
                for (int x = 0; x < a.length; x++) {
                    if (randomCount == a[x]) {
                        a[x] = 100;
                    }
                }
                mAllLotteryNumbersArray[i] = a;
            }
            titleList.clear();
            titleList.add("万位");
            titleList.add("千位");
            titleList.add("百位");
            titleList.add("十位");
            titleList.add("个位");
            shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
        }

        int countCai = checkNumberNoToast(FenFenCaiActivity.this, mCurGameRuleBean, mAllLotteryNumbersArray);
        //Log.d("test","countCai:"+countCai);
        tv_notes_rmb.setText(String.format(getString(R.string.selectnote_notes_rmb),
                countCai, countCai * mAmountPerNote));
        sendRandomBetBroadCast();
    }

    //点击近期开奖
    private void clickRecentlyLottery() {
        if (null == mHomeItemBean)
            return;

        if (!isShow) {
            lv_recentlylottery.setVisibility(View.VISIBLE);
            isShow = true;
            MsgController.getInstance().getOpenListByCode("1", mHomeItemBean.getCpcode(), new HttpCallback(FenFenCaiActivity.this) {
                @Override
                public void onSuccess(Call call, String s) {
                    Log.e("按彩种获取开奖记录", "-----onItemClick---" + s);
                    String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                    String state = JsonUtil.getFieldValue(s, "state");
                    if ("success".equals(state)) {
                        showBonusBeenList = (List<ShowBonusBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<ShowBonusBean>>() {
                        }.getType());
                        if (ffcAdapter == null) {
                            ffcAdapter = new FFCAdapter(FenFenCaiActivity.this);
                            lv_recentlylottery.setAdapter(ffcAdapter);
                        }
                        ffcAdapter.setData(showBonusBeenList);
                    } else if (state.equals("error")) {
                        Toast.makeText(FenFenCaiActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            lv_recentlylottery.setVisibility(View.GONE);
            isShow = false;
        }
    }

    //开始计时
    private void startCountDown(long finalTime, long sealtimes) {
        if (finalTime < 0 || sealtimes < 0 || (finalTime - sealtimes) < 0) {
            tv_lotterystatus.setText(R.string.selectnote_deciding);
            cdv_countdown.setVisibility(View.GONE);
            requestRecentlyLotteryRecord();
            return;
        }

        cdv_countdown.setVisibility(View.VISIBLE);
        cdv_countdown.start(finalTime * 1000);
        timeCount = new TimeCount((finalTime - sealtimes) * 1000, 1000);
        timeCount.start();
        cdv_countdown.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                requestRecentlyLotteryRecord();
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
                clickMachineSelection();
            }
        });
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
                            mCurlotteryInfo = (LastlotteryBean) mNewestLotteryInfo.clone();
                            eraseData(true);
                        }
                    });

        }

        if (!mClearLotteryAreaDialog.isShowing()) {
            mClearLotteryAreaDialog.show();
        }

    }

    //获取投注接口的Content字段
    private String getContentOfBettedLottery() {
        if (null == mAllLotteryNumbersArray) {
            return "";
        }

        List<String> selectBallList = new ArrayList<>();
        for (int i = 0; i < mAllLotteryNumbersArray.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < mAllLotteryNumbersArray[i].length; j++) {
                int value = mAllLotteryNumbersArray[i][j];
                if (value == 100) {
                    stringBuilder.append(j);
                    stringBuilder.append(" ");
                }
            }

            if (null != stringBuilder.toString() && !"".equals(stringBuilder.toString())) {
                selectBallList.add(stringBuilder.toString());
            }else {
                selectBallList.add("_");
            }
        }

        if (0 == selectBallList.size()) {
            return "";
        }
        String modifierA = getString(R.string.symbol_91);
        String modifierB = getString(R.string.symbol_93);
        String modifierC = getString(R.string.symbol_44);
        StringBuilder sbuilder = new StringBuilder();
        sbuilder.append(modifierA);
        for (String str : selectBallList) {
            sbuilder.append(str + modifierC);
        }
        sbuilder.deleteCharAt(sbuilder.lastIndexOf(modifierC));
        sbuilder.append(modifierB);
        LogUtils.d("content", sbuilder.toString());
        return sbuilder.toString();
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

        //mAllLotteryNumbersArray重新赋值
        if (spilt.length != mAllLotteryNumbersArray.length) {
            return;
        }
        for (int i = 0; i < mAllLotteryNumbersArray.length; i++) {
            String tempStr = spilt[i];
            for (int j = 0; j < mAllLotteryNumbersArray[i].length; j++) {
                if (tempStr.contains(String.valueOf(mAllLotteryNumbersArray[i][j]))) {
                    mAllLotteryNumbersArray[i][j] = 100;
                }
            }
        }
        shiShiCaiAdapter.setData(mAllLotteryNumbersArray, titleList);
    }

    public int checkNumIsDiff(int count, int count1) {
        if (count == count1) {
            count1 = (int) ((Math.random()) * 10);
            checkNumIsDiff(count, count1);
        }
        return count1;
    }

    //刷新选中的球
    private void refreshTheSelectedBall(int position) {
        mReEditPosition = position;
        if (-1 == mReEditPosition) {
            return;
        }

        mReEditBettedBean = mBettedList.get(mReEditPosition);
        String mContent = mReEditBettedBean.getContent();
        if (mHomeItemBean.getTitle().contains("北京")){
            mContent = mContent.replaceAll("10","0");
        }
        analysisContent(mContent);

        Log.d("test","刷新选中的球："+mReEditBettedBean.getContent());
        int notes = checkNumberNoToast(FenFenCaiActivity.this,
                mCurGameRuleBean, mAllLotteryNumbersArray);
        tv_notes_rmb.setText(String.format(getString(R.string.selectnote_notes_rmb),
                notes, notes * mAmountPerNote));
    }

    //清除数据
    private void eraseData(boolean clearAll) {

        mAmountPerNote = 2;
        if (clearAll && null != mBettedList) {
            mBettedList.clear();
        }
        switchTitleName(mCurGameRuleBean);
        setLotteryAreaNum();
    }

    //注册投注页面的广播
    private void registerMessageReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BETTINGLIST);
        registerReceiver(mBettingListReceiver, intentFilter);
    }

    //注销投注界面的广播
    private void unRegisterMessageReceiver() {
        unregisterReceiver(mBettingListReceiver);
    }

    //发送随机选彩的广播
    private void sendRandomBetBroadCast() {
        Intent intent = new Intent(ACTION_SELECTLOTTERY);
        intent.putExtra("randomcontent", getContentOfBettedLottery());
        sendBroadcast(intent);
    }

    //跳转到投注
    private void turn2BettingList() {
        if (null == mBettedList || mBettedList.size() == 0 || null == mCurlotteryInfo
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
        defalutBetted.setModelName(mDefaultMultiplyPerNote + "/" + "0%");
        defalutBetted.setCPCode(mHomeItemBean.getCpcode());
        defalutBetted.setCPName(mHomeItemBean.getTitle());
        defalutBetted.setIssueNum(mCurlotteryInfo.getIssuenum());
        defalutBetted.setRoomName("");

        Intent intent = new Intent(this, TouZhuConfirmActivity.class);
        Bundle bundle = new Bundle();
        mCurlotteryInfo.setTime(cdv_countdown.getRemainTime() / 1000);
        bundle.putSerializable("lastlotteryinfo", mCurlotteryInfo);
        bundle.putString("caitype", mHomeItemBean.getCpcode());
        bundle.putSerializable("lotterylist", (Serializable) mBettedList);
        bundle.putSerializable("randomlottery", defalutBetted);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n 随机数个数
     */
    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }
     /**从集合冲抽取随机数*/
    public int[] getRandomFromArray(int[] array, int count) {
        // ArrayList<Integer>arrayList =null;
        int[] a = array;
        int[] result = new int[count];
        boolean r[] = new boolean[array.length];
        Random random = new Random();
        int m = count; // 要随机取的元素个数
        if (m > a.length || m < 0)
            return a;
        int n = 0;
        while (true) {
            int temp = random.nextInt(a.length);
            if (!r[temp]) {
                if (n == m) // 取到足量随机数后退出循环
                    break;
                n++;
               // System.out.println("得到的第" + n + "个随机数为：" + a[temp]);
                result[n - 1] = a[temp];
                r[temp] = true;
            }
        }
        return result;
    }
}
