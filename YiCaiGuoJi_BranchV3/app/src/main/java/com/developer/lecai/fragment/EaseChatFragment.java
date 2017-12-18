package com.developer.lecai.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.developer.lecai.R;
import com.developer.lecai.activity.HuishuiguizeActivity;
import com.developer.lecai.activity.KeFuActivity;
import com.developer.lecai.activity.MineTouZhuActivity;
import com.developer.lecai.activity.PeiLvShuoMingActivity;
import com.developer.lecai.activity.TrendMap;
import com.developer.lecai.adapter.ZuiXinLotteryNotesAdapter;
import com.developer.lecai.bean.HuishuiGuiZeBean;
import com.developer.lecai.bean.PeiLvBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.control.UserController;
import com.developer.lecai.entiey.PeiLvExplainEntity;
import com.developer.lecai.entiey.ZuiXinGuessingNotesEntity;
import com.developer.lecai.entiey.ZuiXinLotteryNotesEntity;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.Constant;
import com.developer.lecai.utils.DateUtil;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.utils.MyUtil;
import com.developer.lecai.utils.SharedPreferencesUtils;
import com.developer.lecai.utils.XyMyContent;
import com.developer.lecai.view.ActionItem;
import com.developer.lecai.view.CountdownView;
import com.developer.lecai.view.TitlePopup;
import com.hyphenate.EMChatRoomChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMMucSharedFile;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseBaiduMapActivity;
import com.hyphenate.easeui.ui.EaseBaseFragment;
import com.hyphenate.easeui.ui.EaseGroupRemoveListener;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.easeui.widget.EaseAlertDialog.AlertDialogUser;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseChatInputMenu.ChatInputMenuListener;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.EaseVoiceRecorderView;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.PathUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.tools.MultiValueMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;

/**
 * you can new an EaseChatFragment to use or you can inherit it to expand. You
 * need call setArguments to pass chatType and userId <br/>
 * <br/>
 * you can see ChatActivity in demo for your reference
 */
public class EaseChatFragment extends EaseBaseFragment
        implements EMMessageListener, OnClickListener, CountdownView.OnCountdownEndListener {
    protected static final String TAG = "EaseChatFragment";
    protected static final int REQUEST_CODE_MAP = 1;
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_LOCAL = 3;

    /**
     * params to fragment
     */
    protected Bundle fragmentArgs;
    protected int chatType;
    protected String toChatUsername;
    protected EaseChatMessageList messageList;
    protected EaseChatInputMenu inputMenu;

    protected EMConversation conversation;

    protected InputMethodManager inputManager;
    protected ClipboardManager clipboard;

    protected Handler handler = new Handler();
    protected File cameraFile;
    protected EaseVoiceRecorderView voiceRecorderView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected ListView listView;

    protected boolean isloading;
    protected boolean haveMoreData = true;
    protected int pagesize = 20;
    protected GroupListener groupListener;
    protected EMMessage contextMeview1numessage;

    static final int ITEM_TAKE_PICTURE = 4;
    static final int ITEM_PICTURE = 5;
    static final int ITEM_LOCATION = 6;
    private static final int PEILVSHUOMING = 8;
    private static final int PEILVSHEZHI = 7;
    private static final int LOTTERYNOTES = 9;
    private static final int ZUIXINGUESSINGNOTES = 10;
    private static final int BETPAGEYUE = 11;
    private static final int PANDUANSHIFOUBET = 12;
    private static final int BET = 13;
    private static final int KAIJIANGGENGXIN = 14;
    private static final int ZUIXINLOTTER = 15;
    private static final int JIONROOM = 16;
    private static final int LEAVEROOM = 17;
    private static final int YINGKUI = 18;
    private static final int HUISHUIGUIZE = 19;

    protected int[] itemStrings = {R.string.attach_take_pic, R.string.attach_picture, R.string.attach_location};
    protected int[] itemdrawables = {R.drawable.ease_chat_takepic_selector, R.drawable.ease_chat_image_selector,
            R.drawable.ease_chat_location_selector};
    protected int[] itemIds = {ITEM_TAKE_PICTURE, ITEM_PICTURE, ITEM_LOCATION};
    private EMChatRoomChangeListener chatRoomChangeListener;
    private boolean isMessageListInited;
    protected MyItemClickListener extendMenuItemClickListener;

    // 注册广播
    LocalBroadcastManager broadcastManager;
    private BroadcastReceiver mBroadcastReceiver;

    private PopupWindow popup;
    private ArrayList<Fragment> fragments;
    private ViewPager touzhu_viewPager;
    View view;
    private View view1;
    private View view2;
    private View view3;
    private ArrayList<View> viewList;
    private TextView daxiao;
    private int view1num;
    private int view3num;
    private int view2num;
    private LinearLayout big, small, single, both, maximum, big_single, small_single, big_both, small_both, minimum,
            red, green, blue, orange;
    private LinearLayout view2_0, view2_1, view2_2, view2_3, view2_4, view2_5, view2_6, view2_7, view2_8, view2_9,
            view2_10, view2_11, view2_12, view2_13, view2_14, view2_15, view2_16, view2_17, view2_18, view2_19,
            view2_20, view2_21, view2_22, view2_23, view2_24, view2_25, view2_26, view2_27;
    private TextView winningValues;
    private TextView specialPlay_winningValues;
    private TextView view2_winningValues;
    private TextView odds_explain;
    private int touzhuPage = 0;
    /**
     * 投注金额
     */
    private EditText touzhuMoney;
    /**
     * 投注按钮
     */
    private TextView touzhu_btn;
    /**
     * 赔率控件1
     */
    private TextView big_peilv, small_peilv, single_peilv, both_peilv, maximum_peilv, big_single_peilv,
            small_single_peilv, big_both_peilv, small_both_peilv, minimum_peilv;
    /**
     * 赔率控件2
     */
    private TextView view2_0_peilv, view2_1_peilv, view2_2_peilv, view2_3_peilv, view2_4_peilv, view2_5_peilv,
            view2_6_peilv, view2_7_peilv, view2_8_peilv, view2_9_peilv, view2_10_peilv, view2_11_peilv, view2_12_peilv,
            view2_13_peilv, view2_14_peilv, view2_15_peilv, view2_16_peilv, view2_17_peilv, view2_18_peilv,
            view2_19_peilv, view2_20_peilv, view2_21_peilv, view2_22_peilv, view2_23_peilv, view2_24_peilv,
            view2_25_peilv, view2_26_peilv, view2_27_peilv;
    /**
     * 赔率控件3
     */
    private TextView red_peilv, green_peilv, blue_peilv, orange_peilv;
    private String touzhu_num;
    private ImageView left_image;
    private TextView title;
    private ImageView right_image;
    private ImageView right_image1;
    private TitlePopup titlepop;
    private RelativeLayout touzhu_zhupingmu = null;
    private String fangjianType;
    private String wanfaType;
    private String vipType;
    private String account;
    private String imei;
    private Request<JSONObject> request;
    private RequestQueue touzhuQueue;
    private List<PeiLvBean> plList;
    /**
     * 赔率说明赔率控件
     */
    private List<PeiLvExplainEntity> peiLvExplainList;
    private TextView oddsData;
    private TextView oddsData1;
    private TextView oddsData2;
    private TextView oddsData3;
    private TextView oddsData4;
    private TextView oddsData5;
    private TextView oddsData6;
    private TextView oddsData7;
    private TextView oddsData8;
    private TextView oddsData9;
    private TextView oddsData10;
    private TextView oddsData11;
    private TextView oddsData12;
    private TextView oddsData13;
    private String fangjian;
    private  RelativeLayout topRelBanner;
    /******************************************* 最新10期开奖记录 ******************************************/
    private LinearLayout ll2;
    private ListView lotteryNotes_Listview;
    private List<ZuiXinLotteryNotesEntity> zxlnList;
    private ZuiXinLotteryNotesAdapter zxlnAdapter;

    /******************************************* 最新10期开奖记录 ******************************************/
    /******************************************** 最新竞猜记录 *******************************************************/
    private String expect;
    private String timeType;
    private String data;
    private int sealtimes;
    private TextView timeType2;
    private CountdownView timeType1;
    private RelativeLayout rllfengpanzhong;
    private LinearLayout lldaojishi;
    private TextView chatRoomExpect;
    /**
     * 计时器
     */
    private TimeCount time;
    /**
     * 最近10期开奖记录标题期数
     */
    private TextView ten_lotteryNoteExcept;
    /**
     * 最近10期开奖号码
     */
    private TextView zuixinkaijiangNum;
    /******************************************** 最新竞猜记录 *******************************************************/
    private String getBttypeid;
    private String touZhuType;
    /******************************************** 投注页余额接口 *******************************************************/
    private TextView touzhupage_yue;
    private String daojishiTag;
    private String nick_name;
    private String picImageUrl;
    /**
     * 当前是跟投还是正常投注
     */
    private String gentou_touzhu_type;
    private String dgVipUrl;
    /**
     * 盈亏箭头
     */
    private TextView yingkuijiantou;
    private String signature;
    private String roomName;
    private String content;

    /******************************************** 投注页余额接口 *******************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.ease_fragment_chat, container, false);

        setview();

        return view;
    }

    private void setview() {

        touzhuQueue = NoHttp.newRequestQueue();
        // 用户账号
        account = UserController.getInstance().getLoginBean().getLoginname();
        signature = MyUtil.getSignature(account);
        // imei号
        imei = MyUtil.getIMEI(getActivity());
        fangjianType = SharedPreferencesUtils.getValue(getActivity(), "fangjianType");
        wanfaType = SharedPreferencesUtils.getValue(getActivity(), "wanfaType");
        vipType = SharedPreferencesUtils.getValue(getActivity(), "vipType");
        nick_name = UserController.getInstance().getLoginBean().getUsername();
        picImageUrl = UserController.getInstance().getLoginBean().getAvatar();
        dgVipUrl = UserController.getInstance().getLoginBean().getType() + "";
        System.out.println("房间信息" + wanfaType + fangjianType + vipType);
        // 最新竞猜请求
        zuiXinGuessingRequest();
        // 赔率请求
        peilvRequest();

        // 当前余额请求
        touzhupagerYue();
        // 页面中间显示上一期请求
        zuiXinLotteryRequest();
        //盈亏请求
        //yingkuiRequest();
        System.out.println("imei2" + imei);
        // 注册广播接收者 接受来自EaseChatPrimaryMenu 的广播意图
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        mBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals("TOU_ZHU_BTN")) {
                    // Toast.makeText(getActivity().getApplicationContext(),
                    // "添加", Toast.LENGTH_SHORT).show();
                    // 投注弹窗
                    System.out.println("！！！！！！！！！！！！！！！点击");
                    // Toast.makeText(getActivity(), "点击",
                    // Toast.LENGTH_SHORT).show();
                    initPopupWindow();

                } else {
                    // Toast.makeText(getActivity().getApplicationContext(),
                    // "再次点击", Toast.LENGTH_SHORT).show();
                }

            }

        };

        // 广播过滤器
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction("TOU_ZHU_BTN");
        broadcastManager.registerReceiver(mBroadcastReceiver, mFilter);// 注册接受者
        // " + " 号弹窗
        initpopwindow();

        left_image = (ImageView) view.findViewById(R.id.left_image);
        title = (TextView) view.findViewById(R.id.title);
        right_image = (ImageView) view.findViewById(R.id.right_image);
        right_image1 = (ImageView) view.findViewById(R.id.right_image1);
        touzhu_zhupingmu = (RelativeLayout) view.findViewById(R.id.touzhu_zhupingmu);
        ll2 = (LinearLayout) view.findViewById(R.id.ll2);
        // 封盘中
        timeType2 = (TextView) view.findViewById(R.id.timeType2);
        // 倒计时
        timeType1 = (CountdownView) view.findViewById(R.id.timeType1);
        // 当前投注日期
        chatRoomExpect = (TextView) view.findViewById(R.id.chatRoomExpect);
        // 封盘中大控件
        rllfengpanzhong = (RelativeLayout) view.findViewById(R.id.rllfengpanzhong);
        // 倒计时大控件
        lldaojishi = (LinearLayout) view.findViewById(R.id.lldaojishi);
        // 最近10期开奖记录标题期数
        ten_lotteryNoteExcept = (TextView) view.findViewById(R.id.ten_lotteryNoteExcept);
        zuixinkaijiangNum = (TextView) view.findViewById(R.id.zuixinkaijiangNum);
        // 投注页面余额
        touzhupage_yue = (TextView) view.findViewById(R.id.touzhupage_yue);
        yingkuijiantou = (TextView) view.findViewById(R.id.yingkuijiantou);
        timeType1.setOnCountdownEndListener(this);
        ll2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 最新10期开奖记录
                LotteryNotePopupWindow();

            }

        });
        touzhu_zhupingmu.setOnTouchListener(new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        titlepop.dismiss();
                        right_image.setImageResource(R.drawable.img_add);

                        break;
                    case MotionEvent.ACTION_MOVE:
                        titlepop.dismiss();
                        right_image.setImageResource(R.drawable.img_add);

                        break;
                    case MotionEvent.ACTION_UP:
                        titlepop.dismiss();
                        right_image.setImageResource(R.drawable.img_add);

                        break;

                    default:
                        break;
                }
                return false;
            }
        });

    }

    private void zuiXinLotteryRequest() {
        request = NoHttp.createJsonObjectRequest(XyMyContent.ZUIXINLOTTERYNOTES_URL, RequestMethod.POST);
        if (wanfaType.equals("bj")) {
            request.add("type", "bjkl8");
        } else if (wanfaType.equals("cakeno")) {
            request.add("type", "cakeno");
        }
        System.out.println("@@@@@@@@wanfaType1" + wanfaType);
        System.out.println("@@@@@@@@imei1" + imei);
        System.out.println("@@@@@@@@account1" + account);
        request.add("imei", imei);
        request.add("account", account);
        request.add("signature", signature);
        MultiValueMap<String, Object> requestData = request.getParamKeyValues();
        for (Map.Entry<String, List<Object>> stringListEntry : requestData.entrySet()) {

            LogUtils.d("zuiXinLotteryRequest", "key:" + stringListEntry.getKey() + "velue:" + stringListEntry.getValue());
        }

        touzhuQueue.add(ZUIXINLOTTER, request, responseListener);

    }

    /**
     * 投注页余额请求
     */
    private void touzhupagerYue() {

        request = NoHttp.createJsonObjectRequest(XyMyContent.ACCOUNTMONEY_URL, RequestMethod.POST);

        request.add("signature", signature);
        request.add("imei", imei);
        request.add("account", account);

        touzhuQueue.add(BETPAGEYUE, request, responseListener);

    }

    /**
     * 最新竞猜记录请求
     */
    private void zuiXinGuessingRequest() {

        request = NoHttp.createJsonObjectRequest(XyMyContent.ZUIXINGUESSINGNOTES_URL, RequestMethod.POST);

        if (wanfaType.equals("bj")) {

            request.add("type", "bjkl8");
        } else if (wanfaType.equals("cakeno")) {
            request.add("type", "cakeno");
        }
        request.add("signature", signature);
        request.add("imei", imei);
        request.add("account", account);

        touzhuQueue.add(ZUIXINGUESSINGNOTES, request, responseListener);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        fragmentArgs = getArguments();
        // check if single chat or group chat
        chatType = fragmentArgs.getInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        // userId you are chat with or group id
        toChatUsername = fragmentArgs.getString(EaseConstant.EXTRA_USER_ID);

        roomName = fragmentArgs.getString("RoomName");
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * init view
     */
    protected void initView() {
        voiceRecorderView = (EaseVoiceRecorderView) getView().findViewById(R.id.voice_recorder);
        messageList = (EaseChatMessageList) getView().findViewById(R.id.message_list);
        // if (chatType != EaseConstant.CHATTYPE_SINGLE)
        // 显示用户昵称
        messageList.setShowUserNick(true);

        listView = messageList.getListView();

        extendMenuItemClickListener = new MyItemClickListener();
        inputMenu = (EaseChatInputMenu) getView().findViewById(R.id.input_menu);
        registerExtendMenuItem();
        // init input menu
        inputMenu.init(null);
        // inputMenu.setCustomEmojiconMenu();
        inputMenu.setChatInputMenuListener(new ChatInputMenuListener() {

            @Override
            public void onSendMessage(String content) {
				sendTextMessage(content);
//                requestCancelTheBetting();
            }

            @Override
            public void onBigExpressionClicked(EaseEmojicon emojicon) {
                sendBigExpressionMessage(emojicon.getName(), emojicon.getIdentityCode());
            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return false;
            }

        });

        swipeRefreshLayout = messageList.getSwipeRefreshLayout();
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);

        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @SuppressLint("InflateParams")
    private void LotteryNotePopupWindow() {

        View v = getActivity().getLayoutInflater().inflate(R.layout.lotterynotes_popupwindow,
                null);
        popup = new PopupWindow(v, LinearLayout.LayoutParams.WRAP_CONTENT, 500, true);

        lotteryNotes_Listview = (ListView) v.findViewById(R.id.lotteryNotes_Listview);
        // 开奖记录请求
        LotteryNoteRequest();

        popup.setFocusable(true);
        // 该属性设置为true则你在点击屏幕的空白位置也会退出
        popup.setTouchable(true);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.setOutsideTouchable(true);
        popup.showAsDropDown(ll2, 0, 0, Gravity.CENTER);

    }

    /**
     * 最新10期开奖记录
     */
    private void LotteryNoteRequest() {
        request = NoHttp.createJsonObjectRequest(XyMyContent.ZUIXINLOTTERYNOTES_URL, RequestMethod.POST);
        if (wanfaType.equals("bj")) {
            request.add("type", "bjkl8");
        } else if (wanfaType.equals("cakeno")) {
            request.add("type", "cakeno");
        }
        System.out.println("@@@@@@@@wanfaType" + wanfaType);
        System.out.println("@@@@@@@@imei" + imei);
        System.out.println("@@@@@@@@account" + account);

        request.add("imei", imei);
        request.add("account", account);
        request.add("signature", signature);
        touzhuQueue.add(LOTTERYNOTES, request, responseListener);
    }

    /**
     * 赔率设置接口
     */
    private void peilvRequest() {

        request = NoHttp.createJsonObjectRequest(XyMyContent.PEILVSHEZHI_URL, RequestMethod.POST);
        if (wanfaType.equals("bj")) {
            request.add("cpcode", "bjkl8");
        } else if (wanfaType.equals("cakeno")) {
            request.add("cpcode", "cakeno");
        }
        request.add("imei", imei);
        request.add("account", account);
        request.add("signature", signature);
        request.add("roomtype", "20");
        LogUtils.d("赔率设置接口", imei + "---" + account + "--" + signature + "--" + wanfaType);
        touzhuQueue.add(PEILVSHEZHI, request, responseListener);

    }

    OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

        @Override
        public void onSucceed(int what, Response<JSONObject> response) {

            switch (what) {
                case PEILVSHEZHI:
                    try {
                        System.out.println("赔率设置数据" + response);
                        String state = response.get().getString("state");
                        String biz_content = response.get().getString("biz_content");

                        LogUtils.d("赔率设置数据", biz_content);
                        if (state.equals("success")) {

                            plList = JSON.parseArray(biz_content, PeiLvBean.class);

                        } else if (state.equals("error")) {

                            Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case HUISHUIGUIZE:

                    HuishuiGuiZeBean huishuiGuiZeBean = JsonUtil.parseJsonToBean(response.get().toString(), HuishuiGuiZeBean.class);
                    System.out.println("回水规则" + response);
                    Intent intent = new Intent(getActivity(), HuishuiguizeActivity.class);
                    intent.putExtra("huishuiGuiZeBean", huishuiGuiZeBean);
                    startActivity(intent);

                    break;
                case PEILVSHUOMING:
                    System.out.println("赔率说明" + response);
                    try {
                        String state = response.get().getString("state");
                        String biz_content = response.get().getString("biz_content");

                        if (state.equals("success")) {

//							peiLvExplainList = JSON.parseArray(biz_content, PeiLvExplainEntity.class);
//							zanDlerlog();

                            Intent intent2 = new Intent(getContext(), PeiLvShuoMingActivity.class);
                            startActivity(intent2);

                        } else if (state.equals("error")) {

                            Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case LOTTERYNOTES:

                    System.out.println("最近10期开奖结果" + response);

                    try {
                        String state = response.get().getString("state");
                        String biz_content = response.get().getString("biz_content");
                        if (state.equals("success")) {

                            zxlnList = JSON.parseArray(biz_content, ZuiXinLotteryNotesEntity.class);

                            zxlnAdapter = new ZuiXinLotteryNotesAdapter(getActivity(), zxlnList);
                            ten_lotteryNoteExcept.setText(zxlnList.get(0).issuenum.split("第")[1].split("期")[0]);
                            zuixinkaijiangNum.setText(zxlnList.get(0).getResult());
                            if (lotteryNotes_Listview != null)
                                lotteryNotes_Listview.setAdapter(zxlnAdapter);

                        } else if (state.equals("error")) {

                            Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;
                case ZUIXINGUESSINGNOTES:

                    System.out.println("最新竞猜记录" + response);
                    try {

                        String state = response.get().getString("state");
                        String biz_content = response.get().getString("biz_content");

                        if (state.equals("success")) {

                            System.out.println("最新竞猜记录成功内容" + biz_content);

                            ZuiXinGuessingNotesEntity zEntity = new ZuiXinGuessingNotesEntity();

                            zEntity = JSON.parseObject(biz_content, ZuiXinGuessingNotesEntity.class);

                            expect = zEntity.getExpect();
                            timeType = zEntity.getStatus();
                            data = zEntity.getTime();
                            sealtimes = zEntity.sealtimes;
                            // ten_lotteryNoteExcept.setText(zEntity.issuenum);
                            chatRoomExpect.setText(zEntity.issuenum);

                            if (timeType.equals("已停售")) {

                                rllfengpanzhong.setVisibility(View.VISIBLE);
                                lldaojishi.setVisibility(View.GONE);
                                timeType2.setText(timeType);

                            } else if (timeType.equals("维护中")) {
                                time = new TimeCount(2000, 1000);
                                time.start();
                                rllfengpanzhong.setVisibility(View.VISIBLE);
                                lldaojishi.setVisibility(View.GONE);
                                timeType2.setText(timeType);

                            } else {

                                //	int fengpanTime = Integer.parseInt(timeType);
//								int fengpanTime = 0;
//								fengpanTime = data - sealtimes;
//								try {
////									fengpanTime = (int)DateUtil.minutesBetween(data)/1000;
//								} catch (ParseException e) {
//									e.printStackTrace();
//								}
//								if (fengpanTime <= 30) {
//
//									lldaojishi.setVisibility(View.GONE);
//									rllfengpanzhong.setVisibility(View.VISIBLE);
//									timeType2.setText("封盘中");
//									time = new TimeCount(32000, 1000);
//									time.start();
//								} else if (fengpanTime > 30) {
//
//									rllfengpanzhong.setVisibility(View.GONE);
//									lldaojishi.setVisibility(View.VISIBLE);
//									daojishiTag = "1";
//									Long long1 = Long.parseLong(String.valueOf(fengpanTime - 30));
//
//									timeType1.start(long1 * 1000);
//
//								}


                                int fengpanTime = Integer.parseInt(data);
                                if (fengpanTime <= sealtimes) {

                                    lldaojishi.setVisibility(View.GONE);
                                    rllfengpanzhong.setVisibility(View.VISIBLE);
                                    timeType2.setText("封盘中");

                                    time = new TimeCount(32000, 1000);
                                    time.start();
                                } else if (fengpanTime > sealtimes) {

                                    rllfengpanzhong.setVisibility(View.GONE);
                                    lldaojishi.setVisibility(View.VISIBLE);
                                    daojishiTag = "1";
                                    Long long1 = Long.parseLong(String.valueOf(fengpanTime - sealtimes));

                                    timeType1.start(long1 * 1000);

                                }


                            }

                        } else if (state.equals("error")) {
                            if (getActivity() == null) {
                                System.out.println("getActivity为空");
                            } else {
                                Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;
                case BETPAGEYUE:

                    try {
                        System.out.println("剩余元宝数数据" + response);
                        String state = response.get().getString("state");
                        String biz_content = response.get().getString("biz_content");
                        if (state.equals("success")) {

//							UserInfoEntity entity = new UserInfoEntity();
//
//							entity = JSON.parseObject(biz_content, UserInfoEntity.class);
                            String accountfee = JsonUtil.getFieldValue(biz_content, "accountfee");

                            touzhupage_yue.setText(accountfee);
                        } else if (state.equals("error")) {
                            Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;
                case PANDUANSHIFOUBET:

					/*System.out.println("判断投注是否成功" + response);
                    try {

						String state = response.get().getString("state");
						String biz_content = response.get().getString("biz_content");

						if (state.equals("success")) {

							System.out.println("最新竞猜记录成功内容" + biz_content);

							ZuiXinGuessingNotesEntity zEntity = new ZuiXinGuessingNotesEntity();

							zEntity = JSON.parseObject(biz_content, ZuiXinGuessingNotesEntity.class);

                            expect = zEntity.getExpect();
                            timeType = zEntity.getStatus();
                            data=zEntity.getTime();
							// ten_lotteryNoteExcept.setText(expect);
							// chatRoomExpect.setText(expect);
							if (timeType.equals("已停售")) {
								Toast.makeText(getActivity(), "已停售不能投注", Toast.LENGTH_SHORT).show();
							} else if (timeType.equals("维护中")) {
								Toast.makeText(getActivity(), "维护中不能投注", Toast.LENGTH_SHORT).show();
							} else {

                                int fengpanTime = 0;
                                try {
                                    fengpanTime = DateUtil.minutesBetween(data);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (fengpanTime <= 30) {

									Toast.makeText(getActivity(), "封盘中不能投注", Toast.LENGTH_SHORT).show();
								} else if (fengpanTime > 30) {
									if (gentou_touzhu_type.equals("正常投注")) {

										touzhu_num = touzhuMoney.getText().toString();
									}
									// String content = "第 85665222 期 " + "投注类型 双 "
									// +
									// "投注金额 " + touzhu_num;
									System.out.println("投注类型id（与赔率中的字段对应:" + getBttypeid);
									System.out.println("房间类型名称(初级,中级,高级):" + fangjianType);
									System.out.println("vip类型名称（vip1,vip2,vip3,vip4）:" + vipType);
									System.out.println("账号:" + account);
									System.out.println("手机唯一标识:" + imei);
									System.out.println("金额:" + touzhu_num);
									System.out.println("期号:" + expect);
									betRequest(getBttypeid, fangjianType, vipType, account, imei, touzhu_num, expect);

								}
							}

						} else if (state.equals("error")) {

							Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
*/
                    break;

                case BET:

                    System.out.println("投注结果数据" + response);

                    try {
                        String state = response.get().getString("state");
                        String biz_content = response.get().getString("biz_content");

                        if (state.equals("success")) {
                            String content = expect + "期" + "    投注类型:   " + touZhuType + "\n" + "投注金额:     " + touzhu_num + "元宝";
                            sendTextMessage(content);
                            touzhupagerYue();
                            // 投注后获取当前距下一期开奖时间+30秒后更新当前页面余额
                            KaijiangRequest();
                            Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

                        } else if (state.equals("error")) {

                            Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;

                case KAIJIANGGENGXIN:
                    System.out.println("主要是获取当前倒计时时间" + response);
                    try {

                        String state = response.get().getString("state");
                        String biz_content = response.get().getString("biz_content");

                        if (state.equals("success")) {

                            ZuiXinGuessingNotesEntity zEntity = new ZuiXinGuessingNotesEntity();

                            zEntity = JSON.parseObject(biz_content, ZuiXinGuessingNotesEntity.class);

                            expect = zEntity.getExpect();
                            timeType = zEntity.getStatus();
                            data = zEntity.getTime();

                            int currentTime = 0;
                            try {
                                currentTime = (int) DateUtil.minutesBetween(data) / 1000;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            // 投注后获取当前距下一期开奖时间+30秒后更新当前页面余额
                            daojishiTag = "2";
                            System.out.println("投注后获取的倒计时" + data);
                            time = new TimeCount((currentTime * 1000) + 45000, 1000);

                            time.start();

                        } else if (state.equals("error")) {

                            Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case ZUIXINLOTTER:
                    System.out.println("设置屏幕中间横条显示当前期数和开奖类型" + response);


                    try {
                        String state = response.get().getString("state");
                        String biz_content = response.get().getString("biz_content");
                        LogUtils.d("ZUIXINLOTTER", biz_content);
                        if (state.equals("success")) {

                            zxlnList = JSON.parseArray(biz_content, ZuiXinLotteryNotesEntity.class);

                            ten_lotteryNoteExcept.setText(zxlnList.get(0).issuenum.split("第")[1].split("期")[0]);
                            zuixinkaijiangNum.setText(zxlnList.get(0).getResult());
                            System.out.println(zxlnList.get(0).getExpect() + ":" + zxlnList.get(0).getResult());
                        } else if (state.equals("error")) {

                            Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case JIONROOM:

                    System.out.println("成员进入房间" + response);
                    break;
                case LEAVEROOM:

                    System.out.println("成员离开房间" + response);
                    break;
                case YINGKUI:

                    System.out.println("盈亏指示数据" + response);

                    try {
                        String state = response.get().getString("state");
                        String biz_content = response.get().getString("biz_content");
                        String status = response.get().getString("status");
                        if (state.equals("success")) {
//						if (getActivity() == null) {
//							System.out.println("getActivity为空");
//						} else {
                            if (status.equals("1")) {
                                yingkuijiantou.setTextColor(Color.GREEN);
                                yingkuijiantou.setText(biz_content);
                            } else if (status.equals("2")) {
                                yingkuijiantou.setText(biz_content);
                                yingkuijiantou.setTextColor(Color.RED);
                            } else {
                                yingkuijiantou.setText(biz_content);
                            }
//						}

                        } else if (state.equals("error")) {

                            showChatroomToast(biz_content);

                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;
                default:
                    break;
            }

        }

        @Override
        public void onStart(int what) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onFinish(int what) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode,
                             long networkMillis) {
            System.out.println("异常信息" + exception);

        }
    };

    /**
     * 当前期数倒计时
     */
    @Override
    public void onEnd(CountdownView cv) {
        rllfengpanzhong.setVisibility(View.VISIBLE);
        lldaojishi.setVisibility(View.GONE);
        // daojishiTag = "1";
        time = new TimeCount(32000, 1000);
        time.start();
        // Toast.makeText(getActivity(), "封盘", Toast.LENGTH_SHORT).show();

    }

    /**
     * 开奖后更新余额
     */
    protected void KaijiangRequest() {
        request = NoHttp.createJsonObjectRequest(XyMyContent.ZUIXINGUESSINGNOTES_URL, RequestMethod.POST);

        if (wanfaType.equals("bj")) {

            request.add("type", "bjkl8");
        } else if (wanfaType.equals("cakeno")) {
            request.add("type", "cakeno");
        }
        request.add("signature", signature);
        request.add("imei", imei);
        request.add("account", account);
        touzhuQueue.add(KAIJIANGGENGXIN, request, responseListener);

    }

    /**
     * 投注
     *
     * @param getBttypeid2 账号
     * @param touZhuType   房间类型名称(初级,中级,高级)
     * @param CPCode       vip类型名称（vip1,vip2,vip3,vip4）
     * @param CPName       期号
     * @param imei2        投注类型id（与赔率中的字段对应）
     * @param touzhu_num2  金额
     * @param expect2      手机唯一标识
     */
    protected void betRequest(String getBttypeid2, String touZhuType, String CPCode, String CPName, String imei2,
                              String touzhu_num2, String expect2, String signature) {

        request = NoHttp.createJsonObjectRequest(XyMyContent.BET_URL, RequestMethod.POST);


        LogUtils.d("ddddd", roomName);
        if (roomName.split("-").length > 2) {


            if ("fir".equals(roomName.split("-")[1])) {
                roomName = "回水厅";
            } else if ("sec".equals(roomName.split("-")[1])) {
                roomName = "保本厅";
            } else {
                roomName = "高赔率厅";
            }
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PayFee", touzhu_num2);
            jsonObject.put("Num", "1");
            jsonObject.put("pMuch", "1");
            jsonObject.put("Type", getBttypeid2);
            jsonObject.put("TypeName", touZhuType);
            jsonObject.put("MType", "0");
            jsonObject.put("ModelName", "");
            jsonObject.put("CPCode", CPCode);
            jsonObject.put("CPName", CPName);
            jsonObject.put("IssueNum", expect2);
            jsonObject.put("Content", content);
            jsonObject.put("RoomName", roomName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //jsonArray.add(jsonObject);
        request.add("account", account);
        request.add("totalfee", touzhu_num2);
        request.add("isstart", "1");
        String list = "[" + jsonObject.toString() + "]";
        request.add("list", "[" + jsonObject.toString() + "]");
        request.add("signature", signature);
        request.add("imei", imei2);

        for (Map.Entry<String, List<Object>> stringListEntry : request.getParamKeyValues().entrySet()) {

            LogUtils.d("投注", stringListEntry.getKey() + "---" + stringListEntry.getValue());
        }
        touzhuQueue.add(BET, request, responseListener);

    }

    private TextView peilv_play;
    private TextView peilv_play1;
    private TextView peilv_play2;
    private TextView peilv_play3;
    private TextView peilv_play4;
    private TextView peilv_play5;
    private TextView peilv_play6;
    private TextView peilv_play7;
    private TextView peilv_play8;
    private TextView peilv_play9;
    private TextView peilv_play10;
    private TextView peilv_play11;
    private TextView peilv_play12;
    private TextView peilv_play13;
    private int width;
    private int height;
    private int xWidth;
    private int xHeight;
    private TextView zuixiao_bet;
    private TextView both_bet;
    private TextView text_wanfa;

    @SuppressWarnings("deprecation")
    @SuppressLint("InflateParams")
    protected void initPopupWindow() {
        // if (popup == null) {
        // LayoutInflater layoutInflater = (LayoutInflater) getActivity()
        // .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // View view =
        // layoutInflater.inflate(R.layout.popwindow_layout,null);
        // 创建一个PopuWidow对象
        // popWindow = new
        // PopupWindow(view,LinearLayout.LayoutParams.FILL_PARENT, 200);
        if (getActivity() == null) {
            System.out.println("getActivity为空");
        } else {

            System.out.println("getActivity不为空");
            width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
            height = getActivity().getWindowManager().getDefaultDisplay().getHeight();

            View v = getActivity().getLayoutInflater().inflate(R.layout.popu_touzhu, null);
            popup = new PopupWindow(v, width - 20, height / 2, true);
            // }
            touzhu_viewPager = (ViewPager) v.findViewById(R.id.touzhu_viewpager);
            odds_explain = (TextView) v.findViewById(R.id.odds_explain);
            touzhuMoney = (EditText) v.findViewById(R.id.touzhuMoney);
            touzhu_btn = (TextView) v.findViewById(R.id.touzhu_btn);
            zuixiao_bet = (TextView) v.findViewById(R.id.zuixiao_bet);
            both_bet = (TextView) v.findViewById(R.id.both_bet);
            ImageView  rfanyeBtn = (ImageView) v.findViewById(R.id.rfanyeBtn);
            ImageView  lfanyeBtn = (ImageView) v.findViewById(R.id.lfanyeBtn);
            text_wanfa = (TextView) v.findViewById(R.id.text_wanfa);
            topRelBanner =  (RelativeLayout) v.findViewById(R.id.top_relBanner);
            touzhuPage = 0;
            zuixiao_bet.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (fangjianType.equals("初级")) {

                        touzhuMoney.setText("20");

                    } else if (fangjianType.equals("中级")) {

                        touzhuMoney.setText("50");

                    } else if (fangjianType.equals("高级")) {

                        touzhuMoney.setText("500");

                    }

                }
            });
            rfanyeBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    touzhuPage += 1;
                    if(touzhuPage>touzhu_viewPager.getChildCount()){
                        touzhuPage = touzhu_viewPager.getChildCount();
                    }else {
                        touzhu_viewPager.setCurrentItem(touzhuPage);
                    }


                }
            });
            lfanyeBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    touzhuPage -= 1;
                    if(touzhuPage<0)
                    {
                        touzhuPage =0;

                    }else {
                        touzhu_viewPager.setCurrentItem(touzhuPage);
                    }

                }
            });
            both_bet.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (!touzhuMoney.getText().toString().isEmpty()) {

                        int a = Integer.parseInt(touzhuMoney.getText().toString());
                        int b = a * 2;
                        touzhuMoney.setText(String.valueOf(b));
                    }

                }
            });
            touzhu_btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    String CPCode = null;
                    String CPName = null;
                    if (wanfaType.equals("bj")) {
                        CPCode = "bjkl8";
                        CPName = "北京幸运28";
                    } else {
                        CPCode = "cakeno";
                        CPName = "加拿大幸运28";
                    }
                    gentou_touzhu_type = "正常投注";
                    touzhu_num = touzhuMoney.getText().toString().trim();
                    betRequest(getBttypeid, touZhuType, CPCode, CPName, imei, touzhu_num, expect, signature);
                    popup.dismiss();
                    popup = null;
                }
            });
            odds_explain.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    oddsExplanRequest();

                }
            });
            popup.setFocusable(true);
            // 该属性设置为true则你在点击屏幕的空白位置也会退出
            popup.setTouchable(true);
            popup.setFocusable(true);
            popup.setBackgroundDrawable(new BitmapDrawable());
            popup.setOutsideTouchable(true);
            popup.showAtLocation(inputMenu, Gravity.BOTTOM, 0, 0);
            addinitview();// 添加布局方法
        }
    }

    protected void DetermineWhetherBetRequest() {
        request = NoHttp.createJsonObjectRequest(XyMyContent.ZUIXINGUESSINGNOTES_URL, RequestMethod.POST);

        if (wanfaType.equals("bj")) {

            request.add("type", "bjkl8");
        } else if (wanfaType.equals("cakeno")) {
            request.add("type", "cakeno");
        }
        request.add("signature", signature);
        request.add("imei", imei);
        request.add("account", account);
        touzhuQueue.add(PANDUANSHIFOUBET, request, responseListener);

    }


    protected void huishuiguizeRequest() {

        request = NoHttp.createJsonObjectRequest(XyMyContent.ZHIJIEKAIHU_FANSHUI, RequestMethod.POST);
        if (fangjianType.equals("初级")) {
            fangjian = "20";
        } else if (fangjianType.equals("中级")) {
            fangjian = "21";
        } else if (fangjianType.equals("高级")) {
            fangjian = "22";
        }

        request.add("type", fangjian);
        request.add("imei", imei);
        request.add("account", account);

        touzhuQueue.add(HUISHUIGUIZE, request, responseListener);

//		String oddsrateurl = "";
//
//		SharedPreferencesUtils.getValue(getContext(),"oddsrateurl1");
//
//		if (fangjianType.equals("初级")) {
//			oddsrateurl = SharedPreferencesUtils.getValue(getContext(),"oddsrateurl1");
//		} else if (fangjianType.equals("中级")) {
//			oddsrateurl = SharedPreferencesUtils.getValue(getContext(),"oddsrateurl2");
//		} else if (fangjianType.equals("高级")) {
//			oddsrateurl = SharedPreferencesUtils.getValue(getContext(),"oddsrateurl3");
//		}
//		Intent intent=new Intent(getContext(),PeiLvShuoMingActivity.class);
//		intent.putExtra("oddsrateurl",oddsrateurl);
//		startActivity(intent);

    }

    protected void oddsExplanRequest() {

//		request = NoHttp.createJsonObjectRequest(XyMyContent.PEILVSHUOMING, RequestMethod.POST);
//		if (fangjianType.equals("初级")) {
//			fangjian = "20";
//		} else if (fangjianType.equals("中级")) {
//			fangjian = "21";
//		} else if (fangjianType.equals("高级")) {
//			fangjian = "22";
//		}
//
//		request.add("type", fangjian);
//		request.add("imei", imei);
//		request.add("account", account);
//
//		touzhuQueue.add(PEILVSHUOMING, request, responseListener);
        String oddsrateurl = "";

        SharedPreferencesUtils.getValue(getContext(), "oddsrateurl1");

        if (fangjianType.equals("初级")) {
            oddsrateurl = SharedPreferencesUtils.getValue(getContext(), "oddsrateurl1");
        } else if (fangjianType.equals("中级")) {
            oddsrateurl = SharedPreferencesUtils.getValue(getContext(), "oddsrateurl2");
        } else if (fangjianType.equals("高级")) {
            oddsrateurl = SharedPreferencesUtils.getValue(getContext(), "oddsrateurl3");
        }
        Intent intent = new Intent(getContext(), PeiLvShuoMingActivity.class);
        intent.putExtra("oddsrateurl", oddsrateurl);
        startActivity(intent);

    }

    @SuppressLint("InflateParams")
    protected void zanDlerlog() {
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.alertdialog_shouye,
                null);
        dialog.setView(layout);
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.alertdialog_shouye);
        TextView odd_close = (TextView) window.findViewById(R.id.odd_close);
        oddsData = (TextView) window.findViewById(R.id.oddsData);
        oddsData1 = (TextView) window.findViewById(R.id.oddsData1);
        oddsData2 = (TextView) window.findViewById(R.id.oddsData2);
        oddsData3 = (TextView) window.findViewById(R.id.oddsData3);
        oddsData4 = (TextView) window.findViewById(R.id.oddsData4);
        oddsData5 = (TextView) window.findViewById(R.id.oddsData5);
        oddsData6 = (TextView) window.findViewById(R.id.oddsData6);
        oddsData7 = (TextView) window.findViewById(R.id.oddsData7);
        oddsData8 = (TextView) window.findViewById(R.id.oddsData8);
        oddsData9 = (TextView) window.findViewById(R.id.oddsData9);
        oddsData10 = (TextView) window.findViewById(R.id.oddsData10);
        oddsData11 = (TextView) window.findViewById(R.id.oddsData11);
        oddsData12 = (TextView) window.findViewById(R.id.oddsData12);
        oddsData13 = (TextView) window.findViewById(R.id.oddsData13);

        peilv_play = (TextView) window.findViewById(R.id.peilv_play);
        peilv_play1 = (TextView) window.findViewById(R.id.peilv_play1);
        peilv_play2 = (TextView) window.findViewById(R.id.peilv_play2);
        peilv_play3 = (TextView) window.findViewById(R.id.peilv_play3);
        peilv_play4 = (TextView) window.findViewById(R.id.peilv_play4);
        peilv_play5 = (TextView) window.findViewById(R.id.peilv_play5);
        peilv_play6 = (TextView) window.findViewById(R.id.peilv_play6);
        peilv_play7 = (TextView) window.findViewById(R.id.peilv_play7);
        peilv_play8 = (TextView) window.findViewById(R.id.peilv_play8);
        peilv_play9 = (TextView) window.findViewById(R.id.peilv_play9);
        peilv_play10 = (TextView) window.findViewById(R.id.peilv_play10);
        peilv_play11 = (TextView) window.findViewById(R.id.peilv_play11);
        peilv_play12 = (TextView) window.findViewById(R.id.peilv_play12);
        peilv_play13 = (TextView) window.findViewById(R.id.peilv_play13);

        oddsData.setText(peiLvExplainList.get(0).getOdds());
        oddsData1.setText(peiLvExplainList.get(1).getOdds());
        oddsData2.setText(peiLvExplainList.get(2).getOdds());
        oddsData3.setText(peiLvExplainList.get(3).getOdds());
        oddsData4.setText(peiLvExplainList.get(4).getOdds());
        oddsData5.setText(peiLvExplainList.get(5).getOdds());
        oddsData6.setText(peiLvExplainList.get(6).getOdds());
        oddsData7.setText(peiLvExplainList.get(7).getOdds());
        oddsData8.setText(peiLvExplainList.get(8).getOdds());
        oddsData9.setText(peiLvExplainList.get(9).getOdds());
        oddsData10.setText(peiLvExplainList.get(10).getOdds());
        oddsData11.setText(peiLvExplainList.get(11).getOdds());
        oddsData12.setText(peiLvExplainList.get(12).getOdds());
        oddsData13.setText(peiLvExplainList.get(13).getOdds());

        peilv_play.setText(peiLvExplainList.get(0).getItem());
        peilv_play1.setText(peiLvExplainList.get(1).getItem());
        peilv_play2.setText(peiLvExplainList.get(2).getItem());
        peilv_play3.setText(peiLvExplainList.get(3).getItem());
        peilv_play4.setText(peiLvExplainList.get(4).getItem());
        peilv_play5.setText(peiLvExplainList.get(5).getItem());
        peilv_play6.setText(peiLvExplainList.get(6).getItem());
        peilv_play7.setText(peiLvExplainList.get(7).getItem());
        peilv_play8.setText(peiLvExplainList.get(8).getItem());
        peilv_play9.setText(peiLvExplainList.get(9).getItem());
        peilv_play10.setText(peiLvExplainList.get(10).getItem());
        peilv_play11.setText(peiLvExplainList.get(11).getItem());
        peilv_play12.setText(peiLvExplainList.get(12).getItem());
        peilv_play13.setText(peiLvExplainList.get(13).getItem());

        odd_close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });

    }

    @SuppressLint("InflateParams")
    private void addinitview() {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view1 = inflater.inflate(R.layout.fragment_dxds, null);
        view2 = inflater.inflate(R.layout.fragment_csz, null);
        view3 = inflater.inflate(R.layout.fragment_specialplay, null);
        // 赔率说明按钮

        // view1 控件初始化
        big = (LinearLayout) view1.findViewById(R.id.big);
        // // 设置默认选中“大”设置其背景
        // big.setBackgroundResource(com.qiangudhx.xingyun28.R.drawable.vote_submit_bg_odds);
        small = (LinearLayout) view1.findViewById(R.id.small);
        single = (LinearLayout) view1.findViewById(R.id.single);
        both = (LinearLayout) view1.findViewById(R.id.both);
        maximum = (LinearLayout) view1.findViewById(R.id.maximum);
        big_single = (LinearLayout) view1.findViewById(R.id.big_single);
        small_single = (LinearLayout) view1.findViewById(R.id.small_single);
        big_both = (LinearLayout) view1.findViewById(R.id.big_both);
        small_both = (LinearLayout) view1.findViewById(R.id.small_both);
        minimum = (LinearLayout) view1.findViewById(R.id.minimum);
        winningValues = (TextView) view1.findViewById(R.id.winningValues);

        big_peilv = (TextView) view1.findViewById(R.id.big_peilv);
        small_peilv = (TextView) view1.findViewById(R.id.small_peilv);
        single_peilv = (TextView) view1.findViewById(R.id.single_peilv);
        both_peilv = (TextView) view1.findViewById(R.id.both_peilv);
        maximum_peilv = (TextView) view1.findViewById(R.id.maximum_peilv);
        big_single_peilv = (TextView) view1.findViewById(R.id.big_single_peilv);
        small_single_peilv = (TextView) view1.findViewById(R.id.small_single_peilv);
        big_both_peilv = (TextView) view1.findViewById(R.id.big_both_peilv);
        small_both_peilv = (TextView) view1.findViewById(R.id.small_both_peilv);
        minimum_peilv = (TextView) view1.findViewById(R.id.minimum_peilv);
        // 设置大小单双赔率
        big_peilv.setText(plList.get(0).getChildplays().get(0).getContent());
        small_peilv.setText(plList.get(0).getChildplays().get(1).getContent());
        single_peilv.setText(plList.get(0).getChildplays().get(2).getContent());
        both_peilv.setText(plList.get(0).getChildplays().get(3).getContent());
        maximum_peilv.setText(plList.get(0).getChildplays().get(4).getContent());
        big_single_peilv.setText(plList.get(0).getChildplays().get(5).getContent());
        small_single_peilv.setText(plList.get(0).getChildplays().get(6).getContent());
        big_both_peilv.setText(plList.get(0).getChildplays().get(7).getContent());
        small_both_peilv.setText(plList.get(0).getChildplays().get(8).getContent());
        minimum_peilv.setText(plList.get(0).getChildplays().get(9).getContent());

        // view2 控件初始化
        view2_0 = (LinearLayout) view2.findViewById(R.id.view2_0);
        // view2_0.setBackgroundResource(com.qiangudhx.xingyun28.R.drawable.vote_submit_bg_odds);
        view2_1 = (LinearLayout) view2.findViewById(R.id.view2_1);
        view2_2 = (LinearLayout) view2.findViewById(R.id.view2_2);
        view2_3 = (LinearLayout) view2.findViewById(R.id.view2_3);
        view2_4 = (LinearLayout) view2.findViewById(R.id.view2_4);
        view2_5 = (LinearLayout) view2.findViewById(R.id.view2_5);
        view2_6 = (LinearLayout) view2.findViewById(R.id.view2_6);
        view2_7 = (LinearLayout) view2.findViewById(R.id.view2_7);
        view2_8 = (LinearLayout) view2.findViewById(R.id.view2_8);
        view2_9 = (LinearLayout) view2.findViewById(R.id.view2_9);
        view2_10 = (LinearLayout) view2.findViewById(R.id.view2_10);
        view2_11 = (LinearLayout) view2.findViewById(R.id.view2_11);
        view2_12 = (LinearLayout) view2.findViewById(R.id.view2_12);
        view2_13 = (LinearLayout) view2.findViewById(R.id.view2_13);
        view2_14 = (LinearLayout) view2.findViewById(R.id.view2_14);
        view2_15 = (LinearLayout) view2.findViewById(R.id.view2_15);
        view2_16 = (LinearLayout) view2.findViewById(R.id.view2_16);
        view2_17 = (LinearLayout) view2.findViewById(R.id.view2_17);
        view2_18 = (LinearLayout) view2.findViewById(R.id.view2_18);
        view2_19 = (LinearLayout) view2.findViewById(R.id.view2_19);
        view2_20 = (LinearLayout) view2.findViewById(R.id.view2_20);
        view2_21 = (LinearLayout) view2.findViewById(R.id.view2_21);
        view2_22 = (LinearLayout) view2.findViewById(R.id.view2_22);
        view2_23 = (LinearLayout) view2.findViewById(R.id.view2_23);
        view2_24 = (LinearLayout) view2.findViewById(R.id.view2_24);
        view2_25 = (LinearLayout) view2.findViewById(R.id.view2_25);
        view2_26 = (LinearLayout) view2.findViewById(R.id.view2_26);
        view2_27 = (LinearLayout) view2.findViewById(R.id.view2_27);
        view2_winningValues = (TextView) view2.findViewById(R.id.view2_winningValues);
        // view2 赔率控件初始化
        view2_0_peilv = (TextView) view2.findViewById(R.id.view2_0_peilv);
        view2_1_peilv = (TextView) view2.findViewById(R.id.view2_1_peilv);
        view2_2_peilv = (TextView) view2.findViewById(R.id.view2_2_peilv);
        view2_3_peilv = (TextView) view2.findViewById(R.id.view2_3_peilv);
        view2_4_peilv = (TextView) view2.findViewById(R.id.view2_4_peilv);
        view2_5_peilv = (TextView) view2.findViewById(R.id.view2_5_peilv);
        view2_6_peilv = (TextView) view2.findViewById(R.id.view2_6_peilv);
        view2_7_peilv = (TextView) view2.findViewById(R.id.view2_7_peilv);
        view2_8_peilv = (TextView) view2.findViewById(R.id.view2_8_peilv);
        view2_9_peilv = (TextView) view2.findViewById(R.id.view2_9_peilv);
        view2_10_peilv = (TextView) view2.findViewById(R.id.view2_10_peilv);
        view2_11_peilv = (TextView) view2.findViewById(R.id.view2_11_peilv);
        view2_12_peilv = (TextView) view2.findViewById(R.id.view2_12_peilv);
        view2_13_peilv = (TextView) view2.findViewById(R.id.view2_13_peilv);
        view2_14_peilv = (TextView) view2.findViewById(R.id.view2_14_peilv);
        view2_15_peilv = (TextView) view2.findViewById(R.id.view2_15_peilv);
        view2_16_peilv = (TextView) view2.findViewById(R.id.view2_16_peilv);
        view2_17_peilv = (TextView) view2.findViewById(R.id.view2_17_peilv);
        view2_18_peilv = (TextView) view2.findViewById(R.id.view2_18_peilv);
        view2_19_peilv = (TextView) view2.findViewById(R.id.view2_19_peilv);
        view2_20_peilv = (TextView) view2.findViewById(R.id.view2_20_peilv);
        view2_21_peilv = (TextView) view2.findViewById(R.id.view2_21_peilv);
        view2_22_peilv = (TextView) view2.findViewById(R.id.view2_22_peilv);
        view2_23_peilv = (TextView) view2.findViewById(R.id.view2_23_peilv);
        view2_24_peilv = (TextView) view2.findViewById(R.id.view2_24_peilv);
        view2_25_peilv = (TextView) view2.findViewById(R.id.view2_25_peilv);
        view2_26_peilv = (TextView) view2.findViewById(R.id.view2_26_peilv);
        view2_27_peilv = (TextView) view2.findViewById(R.id.view2_27_peilv);


        LogUtils.d("dddd", plList.size() + "__" + plList.get(2).getChildplays().size() + "--" + plList.get(1).getChildplays().size());

        view2_0_peilv.setText(plList.get(1).getChildplays().get(0).getContent());
        view2_1_peilv.setText(plList.get(1).getChildplays().get(1).getContent());
        view2_2_peilv.setText(plList.get(1).getChildplays().get(2).getContent());
        view2_3_peilv.setText(plList.get(1).getChildplays().get(3).getContent());
        view2_4_peilv.setText(plList.get(1).getChildplays().get(4).getContent());
        view2_5_peilv.setText(plList.get(1).getChildplays().get(5).getContent());

        view2_6_peilv.setText(plList.get(1).getChildplays().get(6).getContent());
        view2_7_peilv.setText(plList.get(1).getChildplays().get(7).getContent());
        view2_8_peilv.setText(plList.get(1).getChildplays().get(8).getContent());
        view2_9_peilv.setText(plList.get(1).getChildplays().get(9).getContent());
        view2_10_peilv.setText(plList.get(1).getChildplays().get(10).getContent());
        view2_11_peilv.setText(plList.get(1).getChildplays().get(11).getContent());
        view2_12_peilv.setText(plList.get(1).getChildplays().get(12).getContent());

        view2_13_peilv.setText(plList.get(1).getChildplays().get(13).getContent());
        view2_14_peilv.setText(plList.get(1).getChildplays().get(14).getContent());
        view2_15_peilv.setText(plList.get(1).getChildplays().get(15).getContent());
        view2_16_peilv.setText(plList.get(1).getChildplays().get(16).getContent());
        view2_17_peilv.setText(plList.get(1).getChildplays().get(17).getContent());
        view2_18_peilv.setText(plList.get(1).getChildplays().get(18).getContent());

        view2_19_peilv.setText(plList.get(1).getChildplays().get(19).getContent());
        view2_20_peilv.setText(plList.get(1).getChildplays().get(20).getContent());
        view2_21_peilv.setText(plList.get(1).getChildplays().get(21).getContent());
        view2_22_peilv.setText(plList.get(1).getChildplays().get(22).getContent());
        view2_23_peilv.setText(plList.get(1).getChildplays().get(23).getContent());
        view2_24_peilv.setText(plList.get(1).getChildplays().get(24).getContent());

        view2_25_peilv.setText(plList.get(1).getChildplays().get(25).getContent());
        view2_26_peilv.setText(plList.get(1).getChildplays().get(26).getContent());
        view2_27_peilv.setText(plList.get(1).getChildplays().get(27).getContent());

        // view3 控件初始化
        red = (LinearLayout) view3.findViewById(R.id.red);
        // red.setBackgroundResource(com.qiangudhx.xingyun28.R.drawable.vote_submit_bg_odds);
        green = (LinearLayout) view3.findViewById(R.id.green);
        blue = (LinearLayout) view3.findViewById(R.id.blue);
        orange = (LinearLayout) view3.findViewById(R.id.orange);
        specialPlay_winningValues = (TextView) view3
                .findViewById(R.id.specialPlay_winningValues);

        red_peilv = (TextView) view3.findViewById(R.id.red_peilv);
        green_peilv = (TextView) view3.findViewById(R.id.green_peilv);
        blue_peilv = (TextView) view3.findViewById(R.id.blue_peilv);
        orange_peilv = (TextView) view3.findViewById(R.id.orange_peilv);

        red_peilv.setText(plList.get(2).getChildplays().get(0).getContent());
        green_peilv.setText(plList.get(2).getChildplays().get(2).getContent());
        blue_peilv.setText(plList.get(2).getChildplays().get(1).getContent());
        orange_peilv.setText(plList.get(2).getChildplays().get(3).getContent());

        // 设置监听方法
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        view1num = 1;
        setbackGround(view1num);
        // 中奖和值
        winningValues.setText(plList.get(0).getChildplays().get(0).getPlayinfo());
        getBttypeid = plList.get(0).getChildplays().get(0).getPids();
        touZhuType = plList.get(0).getChildplays().get(0).getPname();
        content = plList.get(0).getChildplays().get(0).getContent();

        setTouZhuListener();
        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));

                return viewList.get(position);
            }
        };

        touzhu_viewPager.setAdapter(pagerAdapter);

        touzhu_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                touzhuPage = position;
                if(position==0){
                    topRelBanner.setBackgroundColor(Color.parseColor("#3b85ff"));
                }else if(position == 1){
                    topRelBanner.setBackgroundColor(Color.parseColor("#FE2A2A"));
                }else if(position == 2){
                    topRelBanner.setBackgroundColor(Color.parseColor("#65BB2A"));
                }
                text_wanfa.setText(plList.get(touzhuPage).getPname());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTouZhuListener() {
        // view1控件监听
        big.setOnClickListener(this);
        small.setOnClickListener(this);
        single.setOnClickListener(this);
        both.setOnClickListener(this);
        maximum.setOnClickListener(this);
        big_single.setOnClickListener(this);
        small_single.setOnClickListener(this);
        big_both.setOnClickListener(this);
        small_both.setOnClickListener(this);
        minimum.setOnClickListener(this);
        // view2控件监听
        view2_0.setOnClickListener(this);
        view2_1.setOnClickListener(this);
        view2_2.setOnClickListener(this);
        view2_3.setOnClickListener(this);
        view2_4.setOnClickListener(this);
        view2_5.setOnClickListener(this);
        view2_6.setOnClickListener(this);
        view2_7.setOnClickListener(this);
        view2_8.setOnClickListener(this);
        view2_9.setOnClickListener(this);
        view2_10.setOnClickListener(this);
        view2_11.setOnClickListener(this);
        view2_12.setOnClickListener(this);
        view2_13.setOnClickListener(this);
        view2_14.setOnClickListener(this);
        view2_15.setOnClickListener(this);
        view2_16.setOnClickListener(this);
        view2_17.setOnClickListener(this);
        view2_18.setOnClickListener(this);
        view2_19.setOnClickListener(this);
        view2_20.setOnClickListener(this);
        view2_21.setOnClickListener(this);
        view2_22.setOnClickListener(this);
        view2_23.setOnClickListener(this);
        view2_24.setOnClickListener(this);
        view2_25.setOnClickListener(this);
        view2_26.setOnClickListener(this);
        view2_27.setOnClickListener(this);

        // view3控件监听
        red.setOnClickListener(this);
        green.setOnClickListener(this);
        blue.setOnClickListener(this);
        orange.setOnClickListener(this);

    }

    protected void setUpView() {
        title.setText(toChatUsername);
        if (chatType == EaseConstant.CHATTYPE_SINGLE) {
            // set title
            if (null != toChatUsername && !"".equals(toChatUsername) && EaseUserUtils.getUserInfo(toChatUsername) != null) {
                EaseUser user = EaseUserUtils.getUserInfo(toChatUsername);
                if (user != null) {
                    LogUtils.d("tilte", user.getNick());
                    title.setText(user.getNick());
                }
            }
            // titleBar.setRightImageResource(R.drawable.ease_mm_title_remove);
        } else {
            // 群组titleBar 右侧的图片
            // titleBar.setRightImageResource(com.qiangudhx.xingyun28.R.drawable.add);
            // titleBar.setLeftImageResource(com.qiangudhx.xingyun28.R.drawable.back);
            // if (chatType == EaseConstant.CHATTYPE_GROUP) {
            // // group chat
            // EMGroup group =
            // EMClient.getInstance().groupManager().getGroup(toChatUsername);
            // if (group != null)
            // titleBar.setTitle(group.getGroupName());
            // // listen the event that user moved out group or group is
            // // dismissed
            // groupListener = new GroupListener();
            // EMClient.getInstance().groupManager().addGroupChangeListener(groupListener);
            // } else {
            onChatRoomViewCreation();
            // }

        }
        if (chatType != EaseConstant.CHATTYPE_CHATROOM) {
            onConversationInit();
            onMessageListInit();
        }

        // 返回按钮
        left_image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // 添加按钮
        right_image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                    emptyHistory();
                } else {

                    titlepop.show(right_image);
                    right_image.setImageResource(R.drawable.img_add_an);
                    toGroupDetails();
                }
            }
        });
        // 客服按钮
        right_image1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), "此功能暂未开放",
                // Toast.LENGTH_LONG).show();
//				showChatroomToast("此功能暂未开放");
                Intent intent = new Intent(getActivity(), KeFuActivity.class);
                startActivity(intent);
            }
        });

        setRefreshLayoutListener();

        // show forward message if the message is not null
        String forward_msg_id = getArguments().getString("forward_msg_id");
        if (forward_msg_id != null) {
            forwardMessage(forward_msg_id);
        }
        /** 设置聊天界面的各种控件监听 */
        setChatFragmentListener(new EaseChatFragmentHelper() {
            /**
             * 设置消息扩展属性
             */
            @Override
            public void onSetMessageAttributes(EMMessage message) {
                // TODO Auto-generated method stub

                // 设置要发送扩展消息用户昵称
                message.setAttribute(Constant.USER_NAME, nick_name);
                // 发送出去的用戶的頭像
                message.setAttribute(Constant.HEAD_IMAGE_URL, picImageUrl);
                //发送出去的用户等级
                message.setAttribute(Constant.USER_VIP, dgVipUrl);
            }

            /**
             * 设置自定义chatrow提供者
             *
             * @return
             */
            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                // TODO Auto-generated method stub
                return null;
            }

            /**
             * 消息气泡框长按事件
             */
            @Override
            public void onMessageBubbleLongClick(EMMessage message) {
                // TODO Auto-generated method stub

            }

            /**
             * 消息气泡框点击事件
             */
            @Override
            public boolean onMessageBubbleClick(EMMessage message) {

                if (!message.getUserName().equals("8001") && !message.getUserName().equals("8002") && !message.getUserName().equals("8003")) {
                    System.out.println("点击气泡" + message.getFrom());
                    System.out.println("点击气泡" + message.getBody());
                    System.out.println("点击气泡" + message.getTo());
                    // 点击气泡276112888937054736
                    // 276112888643453468
                    // 获取聊天框内的信息
                    String fString = message.getBody().toString();
                    // 输出的没有空格字符串
                    String aaaa = fString.replace(" ", "");
                    // 去掉换行符后的
                    String bbbb = aaaa.replace("\n", "");
                    // 去掉冒号
                    String cccc = bbbb.replace(":", "");
                    // 去掉双引号符后的
                    String dddd = cccc.replace("\"", "");
                    System.out.println("输出的没有空格字符串   " + aaaa);
                    System.out.println("去掉换行符后的  " + bbbb);
                    // txt:"808889期投注类型:小单投注金额:10元宝"
                    System.out.println("去掉冒号符后的  " + cccc);
                    // txt"808889期投注类型小单投注金额10元宝"
                    System.out.println("去掉双引号符后的  " + dddd);
                    // txt808889期投注类型小单投注金额10元宝
                    // 01234567890123456789012345678901234
                    String gentouNick = message.getFrom();
                    // 期号
                    String qihao = dddd.substring(dddd.indexOf("xt") + 2, dddd.indexOf("期"));
                    // 投注类型
                    String betType = dddd.substring(dddd.indexOf("型") + 1, dddd.indexOf("投注金额"));
                    // 投注金额
                    String betMoney = dddd.substring(dddd.indexOf("额") + 1, dddd.indexOf("元"));
                    System.out.println("获取期号         " + qihao);
                    System.out.println("投注类型         " + betType);
                    System.out.println("投注金额         " + betMoney);
                    if (expect.equals(qihao)) {

                        //boundBankCard(gentouNick, qihao, betType, betMoney);
                    } else {
                        Toast.makeText(getActivity(), "仅限跟投当前期", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }

            /**
             * 扩展输入栏item点击事件,如果要覆盖EaseChatFragment已有的点击事件，return true
             *
             * @param view
             * @param itemId
             * @return
             */
            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                // TODO Auto-generated method stub
                return false;
            }

            /**
             * 进入会话详情
             */
            @Override
            public void onEnterToChatDetails() {
                // TODO Auto-generated method stub

            }

            /**
             * 用户头像长按点击事件
             *
             * @param username
             */
            @Override
            public void onAvatarLongClick(String username) {
                // TODO Auto-generated method stub

            }

            /**
             * 用户头像点击事件
             *
             * @param username
             */
            @Override
            public void onAvatarClick(String username) {
                // TODO Auto-generated method stub

            }
        });
    }

    /**
     * 点击“ + ” 号弹出的popupwindow
     */
    protected void initpopwindow() {
        titlepop = new TitlePopup(getActivity(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titlepop.setItemOnClickListener(onitemClick);
        // 给标题栏弹窗添加子类

        titlepop.addAction(new ActionItem(getActivity(), "投注记录"));
        titlepop.addAction(new ActionItem(getActivity(), "玩法介绍"));
        titlepop.addAction(new ActionItem(getActivity(), "走势图"));
        titlepop.addAction(new ActionItem(getActivity(), "回水规则"));

    }

    /**
     * 点击添加按钮 后充值和提现的监听
     */
    private TitlePopup.OnItemOnClickListener onitemClick = new TitlePopup.OnItemOnClickListener() {

        @Override
        public void onItemClick(ActionItem item, int position) {
            // mLoadingDialog.show();
            switch (position) {
                case 0:// 投注记录

                    right_image.setImageResource(R.drawable.img_add);// 点击充值按钮后“X”图片替换成“+”
                    Intent intent = new Intent(getActivity(), MineTouZhuActivity.class);
                    startActivity(intent);
                    System.out.println("点击位置" + position);

                    break;
                case 1:// 玩法介绍

                    right_image.setImageResource(R.drawable.img_add);// 点击提现按钮后“X”图片替换成“+”
//					Intent intent1 = new Intent(getActivity(), PlayIntroduce.class);
//					intent1.putExtra("tag_type", wanfaType);
//					startActivity(intent1);
                    oddsExplanRequest();
                    System.out.println("点击位置" + position);
                    break;
                case 2:// 走势图

                    right_image.setImageResource(R.drawable.img_add);// 点击提现按钮后“X”图片替换成“+”
                    Intent intent2 = new Intent(getActivity(), TrendMap.class);
                    startActivity(intent2);
                    System.out.println("点击位置" + position);
                    break;
                case 3:
                    right_image.setImageResource(R.drawable.img_add);
                    huishuiguizeRequest();
                    break;

            }
        }
    };

    /**
     * register extend menu, item id need > 3 if you override this method and
     * keep exist item
     */
    protected void registerExtendMenuItem() {
        for (int i = 0; i < itemStrings.length; i++) {
            inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], extendMenuItemClickListener);
        }
    }

    protected void onConversationInit() {
        conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername,
                EaseCommonUtils.getConversationType(chatType), true);
        conversation.markAllMessagesAsRead();
        // the view1number of messages loaded into conversation is
        // getChatOptions().getview1numberOfMessagesLoaded
        // you can change this view1number
        final List<EMMessage> msgs = conversation.getAllMessages();
        int msgCount = msgs != null ? msgs.size() : 0;
        if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
            String msgId = null;
            if (msgs != null && msgs.size() > 0) {
                msgId = msgs.get(0).getMsgId();
            }
            conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    protected void onMessageListInit() {
        messageList.init(toChatUsername, chatType,
                chatFragmentHelper != null ? chatFragmentHelper.onSetCustomChatRowProvider() : null);
        setListItemClickListener();

        messageList.getListView().setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                inputMenu.hideExtendMenuContainer();
                return false;
            }
        });

        isMessageListInited = true;
    }

    protected void setListItemClickListener() {
        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

            // 头像点击事件
            @Override
            public void onUserAvatarClick(String username) {

                if (chatFragmentHelper != null) {
                    chatFragmentHelper.onAvatarClick(username);
                }
            }

            @Override
            public void onUserAvatarLongClick(String username) {
                if (chatFragmentHelper != null) {
                    chatFragmentHelper.onAvatarLongClick(username);
                }
            }

            // 重发消息按钮点击事件
            @Override
            public void onResendClick(final EMMessage message) {
                new EaseAlertDialog(getActivity(), R.string.resend, R.string.confirm_resend, null,
                        new AlertDialogUser() {
                            @Override
                            public void onResult(boolean confirmed, Bundle bundle) {
                                if (!confirmed) {
                                    return;
                                }
                                resendMessage(message);
                            }
                        }, true).show();
            }

            // 气泡框长按事件
            @Override
            public void onBubbleLongClick(EMMessage message) {
                contextMeview1numessage = message;
                if (chatFragmentHelper != null) {
                    chatFragmentHelper.onMessageBubbleLongClick(message);
                }
            }

            // 气泡框点击事件
            @Override
            public boolean onBubbleClick(EMMessage message) {
                if (chatFragmentHelper == null) {
                    return false;
                }
                return chatFragmentHelper.onMessageBubbleClick(message);

            }

        });
    }

    protected void setRefreshLayoutListener() {
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (listView.getFirstVisiblePosition() == 0 && !isloading && haveMoreData) {
                            List<EMMessage> messages;
                            try {
                                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                                    messages = conversation.loadMoreMsgFromDB(messageList.getItem(0).getMsgId(),
                                            pagesize);
                                } else {
                                    messages = conversation.loadMoreMsgFromDB(messageList.getItem(0).getMsgId(),
                                            pagesize);
                                }
                            } catch (Exception e1) {
                                swipeRefreshLayout.setRefreshing(false);
                                return;
                            }
                            if (messages.size() > 0) {
                                messageList.refreshSeekTo(messages.size() - 1);
                                if (messages.size() != pagesize) {
                                    haveMoreData = false;
                                }
                            } else {
                                haveMoreData = false;
                            }

                            isloading = false;

                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_more_messages),
                                    Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 600);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) { // capture new image
                if (cameraFile != null && cameraFile.exists())
                    sendImageMessage(cameraFile.getAbsolutePath());
            } else if (requestCode == REQUEST_CODE_LOCAL) { // send local image
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        sendPicByUri(selectedImage);
                    }
                }
            } else if (requestCode == REQUEST_CODE_MAP) { // location
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                String locationAddress = data.getStringExtra("address");
                if (locationAddress != null && !locationAddress.equals("")) {
                    sendLocationMessage(latitude, longitude, locationAddress);
                } else {
                    Toast.makeText(getActivity(), R.string.unable_to_get_loaction, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isMessageListInited)
            messageList.refresh();
        EaseUI.getInstance().pushActivity(getActivity());
        // register the event listener when enter the foreground
        EMClient.getInstance().chatManager().addMessageListener(this);

        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // unregister this event listener when this activity enters the
        // background
        EMClient.getInstance().chatManager().removeMessageListener(this);

        // remove activity from foreground activity list
        EaseUI.getInstance().popActivity(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (groupListener != null) {
            EMClient.getInstance().groupManager().removeGroupChangeListener(groupListener);
        }

        if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
        }

        if (chatRoomChangeListener != null) {
            EMClient.getInstance().chatroomManager().removeChatRoomChangeListener(chatRoomChangeListener);
        }

    }

    public void onBackPressed() {
        if (inputMenu.onBackPressed()) {
            getActivity().finish();
            if (chatType == EaseConstant.CHATTYPE_GROUP) {
                EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
                EaseAtMessageHelper.get().cleanToAtUserList();
            }
            if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
                EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
            }
        }
    }

    protected void onChatRoomViewCreation() {
        final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "Joining......");
        EMClient.getInstance().chatroomManager().joinChatRoom(toChatUsername, new EMValueCallBack<EMChatRoom>() {

            @Override
            public void onSuccess(final EMChatRoom value) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity().isFinishing() || !toChatUsername.equals(value.getId()))
                            return;
                        pd.dismiss();
                        EMChatRoom room = EMClient.getInstance().chatroomManager().getChatRoom(toChatUsername);
                        if (room != null) {
                            // if (room.getName().equals("bj-fir-vip1")) {

                            title.setText(wanfaType + fangjianType + vipType);

                            String fangjian = "";
                            String fangjiannum = "";

                            LogUtils.d("titleddd", wanfaType + "--" + fangjianType + "--" + vipType);
                            if ("初级".equals(fangjianType)) {
                                fangjian = "回水厅";
                            } else if ("中级".equals(fangjianType)) {
                                fangjian = "保本厅";
                            } else if ("高级".equals(fangjianType)) {
                                fangjian = "高赔率厅";
                            }

                            if ("vip1".equals(vipType)) {
                                fangjiannum = "VIP01房";
                            } else if ("vip2".equals(vipType)) {
                                fangjiannum = "VIP02房";
                            } else if ("vip3".equals(vipType)) {
                                fangjiannum = "VIP03房";
                            } else if ("vip4".equals(vipType)) {
                                fangjiannum = "VIP04房";
                            }

                            title.setText(fangjian + fangjiannum);

                            // title.setText(room.getName());
                            // }
                            System.out.println("!!!!!!!" + room.getName());
                            EMLog.d(TAG, "join room success : " + room.getName());
                        } else {
                            System.out.println("@@@@@@@@" + toChatUsername);
                            title.setText(toChatUsername);
                        }
                        addChatRoomChangeListenr();
                        onConversationInit();
                        onMessageListInit();
                    }
                });
            }

            @Override
            public void onError(final int error, String errorMsg) {
                // TODO Auto-generated method stub
                EMLog.d(TAG, "join room failure : " + error);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                    }
                });
                getActivity().finish();
            }
        });
    }

    protected void addChatRoomChangeListenr() {
        chatRoomChangeListener = new EMChatRoomChangeListener() {
            @Override
            public void onChatRoomDestroyed(String roomId, String roomName) {
                if (roomId.equals(toChatUsername)) {
                    showChatroomToast(" room : " + roomId + " with room name : " + roomName + " was destroyed");
                    getActivity().finish();
                }
            }

            @Override
            public void onMemberJoined(String roomId, String participant) {
                System.out.println("房间id" + roomId);
                // showChatroomToast("成员 : " + participant + " 加入房间 : " +
                // wanfaType + fangjianType + vipType);
                // String content = "成员" + participant + "加入房间";
                // sendTextMessage(content);
                // 此处通过监听给后台发送当前房间iD,和要知道的房间成员，让后台模仿8001发送成员加入或者离开房间
                //renjionRoom(roomId, participant);
            }

            @Override
            public void onMemberExited(String roomId, String roomName, String participant) {
                if (roomId.equals(toChatUsername)) {
                    String curUser = EMClient.getInstance().getCurrentUser();
                    if (curUser.equals(participant)) {
                        EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
                        getActivity().finish();
                    } else {
                        // showChatroomToast("member : " + participant + " was
                        // kicked from the room : " + roomId
                        // + " room name : " + roomName);
                    }
                }
            }

            @Override
            public void onRemovedFromChatRoom(String s, String s1, String s2) {

            }

            @Override
            public void onMuteListAdded(String s, List<String> list, long l) {

            }

            @Override
            public void onMuteListRemoved(String s, List<String> list) {

            }

            @Override
            public void onAdminAdded(String s, String s1) {

            }

            @Override
            public void onAdminRemoved(String s, String s1) {

            }

            @Override
            public void onOwnerChanged(String s, String s1, String s2) {

            }

            @Override
            public void onAnnouncementChanged(String s, String s1) {

            }
        };

        EMClient.getInstance().chatroomManager().addChatRoomChangeListener(chatRoomChangeListener);
    }

    protected void renjionRoom(String roomId, String participant) {

        request = NoHttp.createJsonObjectRequest(XyMyContent.JION_AND_LEAVE_ROOM, RequestMethod.POST);

        request.add("roomid", roomId);
        request.add("account", participant);
        request.add("type", "1");
        System.out.println("加入房间请求");
        touzhuQueue.add(JIONROOM, request, responseListener);
    }

    protected void renleaveRoom(String roomId, String participant) {
        request = NoHttp.createJsonObjectRequest(XyMyContent.JION_AND_LEAVE_ROOM, RequestMethod.POST);

        request.add("roomid", roomId);
        request.add("account", participant);
        request.add("type", "2");

        touzhuQueue.add(LEAVEROOM, request, responseListener);

    }

    protected void showChatroomToast(final String toastContent) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), toastContent, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // implement methods in EMMessageListener
    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        for (EMMessage message : messages) {
            String username = null;
            // group message message.getChatType() == ChatType.GroupChat ||
            if (message.getChatType() == ChatType.ChatRoom) {
                username = message.getTo();

                System.out.println("收到" + message.getUserName() + "的消息");
                if (message.getUserName().equals("8001")) {
                    System.out.println("收到8001的消息");
                    // 页面中间显示上一期请求
                    zuiXinLotteryRequest();
                    touzhupagerYue();
                    zuiXinGuessingRequest();
                    //yingkuiRequest();
                }
            } else {
                // single chat message
                username = message.getFrom();
            }

            // if the message is for current conversation
            if (username.equals(toChatUsername) || message.getTo().equals(toChatUsername)) {
                messageList.refreshSelectLast();
                EaseUI.getInstance().getNotifier().vibrateAndPlayTone(message);
            } else {
                EaseUI.getInstance().getNotifier().onNewMsg(message);
            }
        }
    }


    private void yingkuiRequest() {
        request = NoHttp.createJsonObjectRequest(XyMyContent.YINGKUI_URL, RequestMethod.POST);

        request.add("account", account);
        if (wanfaType.equals("北京")) {

            request.add("type", "10");
        } else if (wanfaType.equals("加拿大")) {
            request.add("type", "11");
        }
        request.add("imei", imei);

        touzhuQueue.add(YINGKUI, request, responseListener);


    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {
        if (isMessageListInited) {
            messageList.refresh();
        }
    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {
        if (isMessageListInited) {
            messageList.refresh();
        }
    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object change) {
        if (isMessageListInited) {
            messageList.refresh();
        }
    }

    /**
     * handle the click event for extend menu
     */
    class MyItemClickListener implements EaseChatExtendMenu.EaseChatExtendMenuItemClickListener {

        @Override
        public void onClick(int itemId, View view) {
            if (chatFragmentHelper != null) {
                if (chatFragmentHelper.onExtendMenuItemClick(itemId, view)) {
                    return;
                }
            }
            switch (itemId) {
                case ITEM_TAKE_PICTURE:
                    selectPicFromCamera();
                    break;
                case ITEM_PICTURE:
                    selectPicFromLocal();
                    break;
                case ITEM_LOCATION:
                    startActivityForResult(new Intent(getActivity(), EaseBaiduMapActivity.class), REQUEST_CODE_MAP);
                    break;

                default:
                    break;
            }
        }

    }

    /**
     * input @
     *
     * @param username
     */
    protected void inputAtUsername(String username, boolean autoAddAtSymbol) {
        if (EMClient.getInstance().getCurrentUser().equals(username) || chatType != EaseConstant.CHATTYPE_GROUP) {
            return;
        }
        EaseAtMessageHelper.get().addAtUser(username);
        EaseUser user = getUserInfo(username);
        if (user != null) {
            username = user.getNick();
        }
        if (autoAddAtSymbol)
            inputMenu.insertText("@" + username + " ");
        else
            inputMenu.insertText(username + " ");
    }

    /**
     * input @
     *
     * @param username
     */
    protected void inputAtUsername(String username) {
        inputAtUsername(username, true);
    }

    // send message
    protected void sendTextMessage(String content) {
        if (EaseAtMessageHelper.get().containsAtUsername(content)) {
            sendAtMessage(content);
        } else {
            EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
            sendMessage(message);
        }
    }

    /**
     * send @ message, only support group chat message
     *
     * @param content
     */
    private void sendAtMessage(String content) {
        if (chatType != EaseConstant.CHATTYPE_GROUP) {
            EMLog.e(TAG, "only support group chat message");
            return;
        }
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
        if (EMClient.getInstance().getCurrentUser().equals(group.getOwner())
                && EaseAtMessageHelper.get().containsAtAll(content)) {
            message.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG, EaseConstant.MESSAGE_ATTR_VALUE_AT_MSG_ALL);
        } else {
            message.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG, EaseAtMessageHelper.get()
                    .atListToJsonArray(EaseAtMessageHelper.get().getAtMessageUsernames(content)));
        }
        sendMessage(message);

    }

    protected void sendBigExpressionMessage(String name, String identityCode) {
        EMMessage message = EaseCommonUtils.createExpressionMessage(toChatUsername, name, identityCode);
        sendMessage(message);
    }

    protected void sendVoiceMessage(String filePath, int length) {
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, toChatUsername);
        sendMessage(message);
    }

    protected void sendImageMessage(String imagePath) {
        EMMessage message = EMMessage.createImageSendMessage(imagePath, false, toChatUsername);
        sendMessage(message);
    }

    protected void sendLocationMessage(double latitude, double longitude, String locationAddress) {
        EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, toChatUsername);
        sendMessage(message);
    }

    protected void sendVideoMessage(String videoPath, String thumbPath, int videoLength) {
        EMMessage message = EMMessage.createVideoSendMessage(videoPath, thumbPath, videoLength, toChatUsername);
        sendMessage(message);
    }

    protected void sendFileMessage(String filePath) {
        EMMessage message = EMMessage.createFileSendMessage(filePath, toChatUsername);
        sendMessage(message);
    }

    protected void sendMessage(EMMessage message) {
        if (message == null) {
            return;
        }
        if (chatFragmentHelper != null) {
            // set extension
            chatFragmentHelper.onSetMessageAttributes(message);
        }
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            message.setChatType(ChatType.GroupChat);
        } else if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            message.setChatType(ChatType.ChatRoom);
        }
        // send message
        EMClient.getInstance().chatManager().sendMessage(message);
        // refresh ui
        if (isMessageListInited) {
            messageList.refreshSelectLast();
        }
    }

    public void resendMessage(EMMessage message) {
        message.setStatus(EMMessage.Status.CREATE);
        EMClient.getInstance().chatManager().sendMessage(message);
        messageList.refresh();
    }

    // ===================================================================================

    /**
     * send image
     *
     * @param selectedImage
     */
    protected void sendPicByUri(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(getActivity(), R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            sendImageMessage(picturePath);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(getActivity(), R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            sendImageMessage(file.getAbsolutePath());
        }

    }

    /**
     * send file
     *
     * @param uri
     */
    protected void sendFileByUri(Uri uri) {
        String filePath = null;
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;

            try {
                cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        if (filePath == null) {
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            Toast.makeText(getActivity(), R.string.File_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }
        // limit the size < 10M
        if (file.length() > 10 * 1024 * 1024) {
            Toast.makeText(getActivity(), R.string.The_file_is_not_greater_than_10_m, Toast.LENGTH_SHORT).show();
            return;
        }
        sendFileMessage(filePath);
    }

    /**
     * capture new image
     */
    protected void selectPicFromCamera() {
        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(getActivity(), R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        cameraFile = new File(PathUtil.getInstance().getImagePath(),
                EMClient.getInstance().getCurrentUser() + System.currentTimeMillis() + ".jpg");
        // noinspection ResultOfMethodCallIgnored
        cameraFile.getParentFile().mkdirs();
        startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                REQUEST_CODE_CAMERA);
    }

    /**
     * select local image
     */
    protected void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }

    /**
     * clear the conversation history
     */
    protected void emptyHistory() {
        String msg = getResources().getString(R.string.Whether_to_empty_all_chats);
        new EaseAlertDialog(getActivity(), null, msg, null, new AlertDialogUser() {

            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if (confirmed) {
                    EMClient.getInstance().chatManager().deleteConversation(toChatUsername, true);
                    messageList.refresh();
                }
            }
        }, true).show();
    }

    /**
     * open group detail
     */
    protected void toGroupDetails() {
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
            if (group == null) {
                Toast.makeText(getActivity(), R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
            if (chatFragmentHelper != null) {
                chatFragmentHelper.onEnterToChatDetails();
            }
        } else if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            if (chatFragmentHelper != null) {
                chatFragmentHelper.onEnterToChatDetails();
            }
        }
    }

    /**
     * hide
     */
    protected void hideKeyboard() {
        if (getActivity().getWindow()
                .getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * forward message
     *
     * @param forward_msg_id
     */
    protected void forwardMessage(String forward_msg_id) {
        final EMMessage forward_msg = EMClient.getInstance().chatManager().getMessage(forward_msg_id);
        EMMessage.Type type = forward_msg.getType();
        switch (type) {
            case TXT:
                if (forward_msg.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)) {
                    sendBigExpressionMessage(((EMTextMessageBody) forward_msg.getBody()).getMessage(),
                            forward_msg.getStringAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, null));
                } else {
                    // get the content and send it
                    String content = ((EMTextMessageBody) forward_msg.getBody()).getMessage();
                    sendTextMessage(content);
                }
                break;
            case IMAGE:
                // send image
                String filePath = ((EMImageMessageBody) forward_msg.getBody()).getLocalUrl();
                if (filePath != null) {
                    File file = new File(filePath);
                    if (!file.exists()) {
                        // send thumb nail if original image does not exist
                        filePath = ((EMImageMessageBody) forward_msg.getBody()).thumbnailLocalPath();
                    }
                    sendImageMessage(filePath);
                }
                break;
            default:
                break;
        }

        if (forward_msg.getChatType() == EMMessage.ChatType.ChatRoom) {
            EMClient.getInstance().chatroomManager().leaveChatRoom(forward_msg.getTo());
        }
    }

    /**
     * listen the group event
     */
    class GroupListener extends EaseGroupRemoveListener {

        @Override
        public void onUserRemoved(final String groupId, String groupName) {
            getActivity().runOnUiThread(new Runnable() {

                public void run() {
                    if (toChatUsername.equals(groupId)) {
                        Toast.makeText(getActivity(), R.string.you_are_group, Toast.LENGTH_LONG).show();
                        Activity activity = getActivity();
                        if (activity != null && !activity.isFinishing()) {
                            activity.finish();
                        }
                    }
                }
            });
        }

        @Override
        public void onGroupDestroyed(final String groupId, String groupName) {
            // prompt group is dismissed and finish this activity
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (toChatUsername.equals(groupId)) {
                        Toast.makeText(getActivity(), R.string.the_current_group_destroyed, Toast.LENGTH_LONG).show();
                        Activity activity = getActivity();
                        if (activity != null && !activity.isFinishing()) {
                            activity.finish();
                        }
                    }
                }
            });
        }

        @Override
        public void onAnnouncementChanged(String s, String s1) {

        }

        @Override
        public void onSharedFileAdded(String s, EMMucSharedFile emMucSharedFile) {

        }

        @Override
        public void onSharedFileDeleted(String s, String s1) {

        }

    }

    protected EaseChatFragmentHelper chatFragmentHelper;


    public void setChatFragmentListener(EaseChatFragmentHelper chatFragmentHelper) {
        this.chatFragmentHelper = chatFragmentHelper;
    }

    public interface EaseChatFragmentHelper {
        /**
         * set message attribute
         */
        void onSetMessageAttributes(EMMessage message);

        /**
         * enter to chat detail
         */
        void onEnterToChatDetails();

        /**
         * on avatar clicked
         *
         * @param username
         */
        void onAvatarClick(String username);

        /**
         * on avatar long pressed
         *
         * @param username
         */
        void onAvatarLongClick(String username);

        /**
         * on message bubble clicked
         */
        boolean onMessageBubbleClick(EMMessage message);

        /**
         * on message bubble long pressed
         */
        void onMessageBubbleLongClick(EMMessage message);

        /**
         * on extend menu item clicked, return true if you want to override
         *
         * @param view
         * @param itemId
         * @return
         */
        boolean onExtendMenuItemClick(int itemId, View view);

        /**
         * on set custom chat row provider
         *
         * @return
         */
        EaseCustomChatRowProvider onSetCustomChatRowProvider();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.big:

                view1num = 1;
                setbackGround(view1num);
                // 中奖和值
                winningValues.setText(plList.get(0).getChildplays().get(0).getPlayinfo());
                getBttypeid = plList.get(0).getChildplays().get(0).getPids();
                touZhuType = plList.get(0).getChildplays().get(0).getPname();
                content = plList.get(0).getChildplays().get(0).getContent();
//			Toast.makeText(getContext(), "大", Toast.LENGTH_SHORT).show();
                break;
            case R.id.small:
//			Toast.makeText(getContext(), "小", Toast.LENGTH_SHORT).show();
                view1num = 2;
                setbackGround(view1num);
                // 中奖和值
                winningValues.setText(plList.get(0).getChildplays().get(1).getPlayinfo());
                getBttypeid = plList.get(0).getChildplays().get(1).getPids();
                touZhuType = plList.get(0).getChildplays().get(1).getPname();
                content = plList.get(0).getChildplays().get(1).getContent();
                break;
            case R.id.single:
                view1num = 3;
//			Toast.makeText(getContext(), "单", Toast.LENGTH_SHORT).show();
                setbackGround(view1num);
                // 中奖和值
                winningValues.setText(plList.get(0).getChildplays().get(2).getPlayinfo());
                getBttypeid = plList.get(0).getChildplays().get(2).getPids();
                touZhuType = plList.get(0).getChildplays().get(2).getPname();
                content = plList.get(0).getChildplays().get(2).getContent();
                break;
            case R.id.both:
                view1num = 4;
//			Toast.makeText(getContext(), "双", Toast.LENGTH_SHORT).show();
                setbackGround(view1num);
                // 中奖和值
                winningValues.setText(plList.get(0).getChildplays().get(3).getPlayinfo());
                getBttypeid = plList.get(0).getChildplays().get(3).getPids();
                touZhuType = plList.get(0).getChildplays().get(3).getPname();
                content = plList.get(0).getChildplays().get(3).getContent();
                break;
            case R.id.maximum:

                view1num = 5;
                setbackGround(view1num);
                // 中奖和值
                winningValues.setText(plList.get(0).getChildplays().get(4).getPlayinfo());
                getBttypeid = plList.get(0).getChildplays().get(4).getPids();
                touZhuType = plList.get(0).getChildplays().get(4).getPname();
                content = plList.get(0).getChildplays().get(4).getContent();
                break;
            case R.id.big_single:
                view1num = 6;
                setbackGround(view1num);
                // 中奖和值
                winningValues.setText(plList.get(0).getChildplays().get(5).getPlayinfo());
                getBttypeid = plList.get(0).getChildplays().get(5).getPids();
                touZhuType = plList.get(0).getChildplays().get(5).getPname();
                content = plList.get(0).getChildplays().get(5).getContent();
                break;
            case R.id.small_single:
                view1num = 7;
                setbackGround(view1num);
                // 中奖和值
                winningValues.setText(plList.get(0).getChildplays().get(6).getPlayinfo());
                getBttypeid = plList.get(0).getChildplays().get(6).getPids();
                touZhuType = plList.get(0).getChildplays().get(6).getPname();
                content = plList.get(0).getChildplays().get(6).getContent();
                break;
            case R.id.big_both:
                view1num = 8;
                setbackGround(view1num);
                // 中奖和值
                winningValues.setText(plList.get(0).getChildplays().get(7).getPlayinfo());
                getBttypeid = plList.get(0).getChildplays().get(7).getPids();
                touZhuType = plList.get(0).getChildplays().get(7).getPname();
                content = plList.get(0).getChildplays().get(7).getContent();
                break;
            case R.id.small_both:
                view1num = 9;
                setbackGround(view1num);
                // 中奖和值
                winningValues.setText(plList.get(0).getChildplays().get(8).getPlayinfo());
                getBttypeid = plList.get(0).getChildplays().get(8).getPids();
                touZhuType = plList.get(0).getChildplays().get(8).getPname();
                content = plList.get(0).getChildplays().get(8).getContent();
                break;

            case R.id.minimum:
                view1num = 10;
                setbackGround(view1num);
                // 中奖和值
                winningValues.setText(plList.get(0).getChildplays().get(9).getPlayinfo());
                getBttypeid = plList.get(0).getChildplays().get(9).getPids();
                touZhuType = plList.get(0).getChildplays().get(9).getPname();
                content = plList.get(0).getChildplays().get(9).getContent();
                break;
            case R.id.red:
                view3num = 1;
                setView3BackGround(view3num);
                // 中奖和值
                specialPlay_winningValues.setText(plList.get(2).getChildplays().get(0).getPlayinfo());
                getBttypeid = plList.get(2).getChildplays().get(0).getPids();
                touZhuType = plList.get(2).getChildplays().get(0).getPname();
                content = plList.get(2).getChildplays().get(0).getContent();
                break;

            case R.id.green:
                view3num = 2;
                setView3BackGround(view3num);
                // 中奖和值
                specialPlay_winningValues.setText(plList.get(2).getChildplays().get(2).getPlayinfo());
                getBttypeid = plList.get(2).getChildplays().get(2).getPids();
                touZhuType = plList.get(2).getChildplays().get(2).getPname();
                content = plList.get(2).getChildplays().get(2).getContent();
                break;

            case R.id.blue:
                view3num = 3;
                setView3BackGround(view3num);
                // 中奖和值
                specialPlay_winningValues.setText(plList.get(2).getChildplays().get(1).getPlayinfo());
                getBttypeid = plList.get(2).getChildplays().get(1).getPids();
                touZhuType = plList.get(2).getChildplays().get(1).getPname();
                content = plList.get(2).getChildplays().get(1).getContent();
                break;
            case R.id.orange:
                view3num = 4;
                setView3BackGround(view3num);
                // 中奖和值
                specialPlay_winningValues.setText(plList.get(2).getChildplays().get(3).getPlayinfo());
                getBttypeid = plList.get(2).getChildplays().get(3).getPids();
                touZhuType = plList.get(2).getChildplays().get(3).getPname();
                content = plList.get(2).getChildplays().get(3).getContent();
                break;
            case R.id.view2_0:

                view2num = 0;
                if(plList.get(2).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(2).getChildplays().get(0).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(2).getChildplays().get(0).getPids();
                touZhuType = plList.get(2).getChildplays().get(0).getPname();
                content = plList.get(2).getChildplays().get(0).getContent();
                break;
            case R.id.view2_1:
                view2num = 1;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;

                view2_winningValues.setText(plList.get(1).getChildplays().get(1).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(1).getPids();
                touZhuType = plList.get(1).getChildplays().get(1).getPname();
                content = plList.get(1).getChildplays().get(1).getContent();
                break;
            case R.id.view2_2:

                view2num = 2;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(2).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(2).getPids();
                touZhuType = plList.get(1).getChildplays().get(2).getPname();
                content = plList.get(1).getChildplays().get(2).getContent();
                break;
            case R.id.view2_3:

                view2num = 3;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(3).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(3).getPids();
                touZhuType = plList.get(1).getChildplays().get(3).getPname();
                content = plList.get(1).getChildplays().get(3).getContent();
                break;
            case R.id.view2_4:

                view2num = 4;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(4).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(4).getPids();
                touZhuType = plList.get(1).getChildplays().get(4).getPname();
                content = plList.get(1).getChildplays().get(4).getContent();
                break;
            case R.id.view2_5:

                view2num = 5;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(5).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(5).getPids();
                touZhuType = plList.get(1).getChildplays().get(5).getPname();
                content = plList.get(1).getChildplays().get(5).getContent();
                break;
            case R.id.view2_6:

                view2num = 6;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(6).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(6).getPids();
                touZhuType = plList.get(1).getChildplays().get(6).getPname();
                content = plList.get(1).getChildplays().get(6).getContent();
                break;
            case R.id.view2_7:

                view2num = 7;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(7).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(7).getPids();
                touZhuType = plList.get(1).getChildplays().get(7).getPname();
                content = plList.get(1).getChildplays().get(7).getContent();
                break;
            case R.id.view2_8:

                view2num = 8;

                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(8).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(8).getPids();
                touZhuType = plList.get(1).getChildplays().get(8).getPname();
                content = plList.get(1).getChildplays().get(8).getContent();
                break;
            case R.id.view2_9:

                view2num = 9;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(9).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(9).getPids();
                touZhuType = plList.get(1).getChildplays().get(9).getPname();
                content = plList.get(1).getChildplays().get(9).getContent();
                break;
            case R.id.view2_10:

                view2num = 10;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(10).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(10).getPids();
                touZhuType = plList.get(1).getChildplays().get(10).getPname();
                content = plList.get(1).getChildplays().get(10).getContent();
                break;
            case R.id.view2_11:

                view2num = 11;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(11).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(11).getPids();
                touZhuType = plList.get(1).getChildplays().get(11).getPname();
                content = plList.get(1).getChildplays().get(11).getContent();
                break;
            case R.id.view2_12:

                view2num = 12;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(12).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(12).getPids();
                touZhuType = plList.get(1).getChildplays().get(12).getPname();
                content = plList.get(1).getChildplays().get(12).getContent();
                break;
            case R.id.view2_13:

                view2num = 13;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(13).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(13).getPids();
                touZhuType = plList.get(1).getChildplays().get(13).getPname();
                content = plList.get(1).getChildplays().get(13).getContent();
                break;
            case R.id.view2_14:

                view2num = 14;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(14).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(14).getPids();
                touZhuType = plList.get(1).getChildplays().get(14).getPname();
                content = plList.get(1).getChildplays().get(14).getContent();
                break;
            case R.id.view2_15:
                view2num = 15;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(15).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(15).getPids();
                touZhuType = plList.get(1).getChildplays().get(15).getPname();
                content = plList.get(1).getChildplays().get(15).getContent();
                break;
            case R.id.view2_16:
                view2num = 16;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(16).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(16).getPids();
                touZhuType = plList.get(1).getChildplays().get(16).getPname();
                content = plList.get(1).getChildplays().get(16).getContent();
                break;
            case R.id.view2_17:
                view2num = 17;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(17).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(17).getPids();
                touZhuType = plList.get(1).getChildplays().get(17).getPname();
                content = plList.get(1).getChildplays().get(17).getContent();
                break;
            case R.id.view2_18:
                view2num = 18;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(18).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(18).getPids();
                touZhuType = plList.get(1).getChildplays().get(18).getPname();
                content = plList.get(1).getChildplays().get(18).getContent();
                break;
            case R.id.view2_19:
                view2num = 19;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(19).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(19).getPids();
                touZhuType = plList.get(1).getChildplays().get(19).getPname();
                content = plList.get(1).getChildplays().get(19).getContent();
                break;
            case R.id.view2_20:
                view2num = 20;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(20).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(20).getPids();
                touZhuType = plList.get(1).getChildplays().get(20).getPname();
                content = plList.get(1).getChildplays().get(20).getContent();
                break;
            case R.id.view2_21:
                view2num = 21;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(21).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(21).getPids();
                touZhuType = plList.get(1).getChildplays().get(21).getPname();
                content = plList.get(1).getChildplays().get(21).getContent();
                break;
            case R.id.view2_22:
                view2num = 22;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(22).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(22).getPids();
                touZhuType = plList.get(1).getChildplays().get(22).getPname();
                content = plList.get(1).getChildplays().get(22).getContent();
                break;
            case R.id.view2_23:
                view2num = 23;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(23).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(23).getPids();
                touZhuType = plList.get(1).getChildplays().get(23).getPname();
                content = plList.get(1).getChildplays().get(23).getContent();
                break;
            case R.id.view2_24:
                view2num = 24;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(24).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(24).getPids();
                touZhuType = plList.get(1).getChildplays().get(24).getPname();
                content = plList.get(1).getChildplays().get(24).getContent();
                break;
            case R.id.view2_25:
                view2num = 25;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(2).getChildplays().get(25).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(25).getPids();
                touZhuType = plList.get(1).getChildplays().get(25).getPname();
                content = plList.get(1).getChildplays().get(25).getContent();
                break;
            case R.id.view2_26:
                view2num = 26;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(26).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(26).getPids();
                touZhuType = plList.get(1).getChildplays().get(26).getPname();
                content = plList.get(1).getChildplays().get(26).getContent();
                break;
            case R.id.view2_27:
                view2num = 27;
                if(plList.get(1).getChildplays().size()<view2num-1)
                    return;
                view2_winningValues.setText(plList.get(1).getChildplays().get(27).getPlayinfo());
                setView2BackGround(view2num);
                getBttypeid = plList.get(1).getChildplays().get(27).getPids();
                touZhuType = plList.get(1).getChildplays().get(27).getPname();
                content = plList.get(1).getChildplays().get(27).getContent();
                break;
            default:
                break;
        }

    }

    private void setView2BackGround(int view2num2) {

        switch (view2num2) {
            case 0:
                view2_0.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 1:
                view2_0.setBackground(null);
                view2_1.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 2:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 3:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 4:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 5:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 6:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 7:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 8:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 9:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 10:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 11:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 12:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 13:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 14:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 15:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 16:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 17:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 18:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 19:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 20:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 21:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 22:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 23:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 24:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 25:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_26.setBackground(null);
                view2_27.setBackground(null);
                break;
            case 26:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                view2_27.setBackground(null);
                break;
            case 27:
                view2_0.setBackground(null);
                view2_1.setBackground(null);
                view2_2.setBackground(null);
                view2_3.setBackground(null);
                view2_4.setBackground(null);
                view2_5.setBackground(null);
                view2_6.setBackground(null);
                view2_7.setBackground(null);
                view2_8.setBackground(null);
                view2_9.setBackground(null);
                view2_10.setBackground(null);
                view2_11.setBackground(null);
                view2_12.setBackground(null);
                view2_13.setBackground(null);
                view2_14.setBackground(null);
                view2_15.setBackground(null);
                view2_16.setBackground(null);
                view2_17.setBackground(null);
                view2_18.setBackground(null);
                view2_19.setBackground(null);
                view2_20.setBackground(null);
                view2_21.setBackground(null);
                view2_22.setBackground(null);
                view2_23.setBackground(null);
                view2_24.setBackground(null);
                view2_25.setBackground(null);
                view2_26.setBackground(null);
                view2_27.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                break;

            default:
                break;
        }

    }

    private void setView3BackGround(int view3num3) {

        switch (view3num3) {
            case 1:
                red.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                green.setBackground(null);
                blue.setBackground(null);
                orange.setBackground(null);
                break;
            case 2:
                green.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                red.setBackground(null);
                blue.setBackground(null);
                orange.setBackground(null);
                break;
            case 3:
                blue.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                red.setBackground(null);
                green.setBackground(null);
                orange.setBackground(null);
                break;
            case 4:
                orange.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                red.setBackground(null);
                green.setBackground(null);
                blue.setBackground(null);
                break;

            default:
                break;
        }

    }

    private void setbackGround(int view1num) {

        switch (view1num) {
            case 1:
                big.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                small.setBackground(null);
                single.setBackground(null);
                both.setBackground(null);
                maximum.setBackground(null);
                big_single.setBackground(null);
                small_single.setBackground(null);
                big_both.setBackground(null);
                small_both.setBackground(null);
                minimum.setBackground(null);
                break;
            case 2:
                small.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                big.setBackground(null);
                single.setBackground(null);
                both.setBackground(null);
                maximum.setBackground(null);
                big_single.setBackground(null);
                small_single.setBackground(null);
                big_both.setBackground(null);
                small_both.setBackground(null);
                minimum.setBackground(null);
                break;
            case 3:
                single.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                big.setBackground(null);
                small.setBackground(null);
                both.setBackground(null);
                maximum.setBackground(null);
                big_single.setBackground(null);
                small_single.setBackground(null);
                big_both.setBackground(null);
                small_both.setBackground(null);
                minimum.setBackground(null);
                break;
            case 4:
                both.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                big.setBackground(null);
                small.setBackground(null);
                single.setBackground(null);
                maximum.setBackground(null);
                big_single.setBackground(null);
                small_single.setBackground(null);
                big_both.setBackground(null);
                small_both.setBackground(null);
                minimum.setBackground(null);
                break;
            case 5:
                maximum.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                big.setBackground(null);
                small.setBackground(null);
                single.setBackground(null);
                both.setBackground(null);
                big_single.setBackground(null);
                small_single.setBackground(null);
                big_both.setBackground(null);
                small_both.setBackground(null);
                minimum.setBackground(null);
                break;
            case 6:
                big_single.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                big.setBackground(null);
                small.setBackground(null);
                single.setBackground(null);
                both.setBackground(null);
                maximum.setBackground(null);
                small_single.setBackground(null);
                big_both.setBackground(null);
                small_both.setBackground(null);
                minimum.setBackground(null);
                break;
            case 7:
                small_single.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                big.setBackground(null);
                small.setBackground(null);
                single.setBackground(null);
                both.setBackground(null);
                maximum.setBackground(null);
                big_single.setBackground(null);
                big_both.setBackground(null);
                small_both.setBackground(null);
                minimum.setBackground(null);
                break;
            case 8:
                big_both.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                big.setBackground(null);
                small.setBackground(null);
                single.setBackground(null);
                both.setBackground(null);
                maximum.setBackground(null);
                big_single.setBackground(null);
                small_single.setBackground(null);
                small_both.setBackground(null);
                minimum.setBackground(null);
                break;
            case 9:
                small_both.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                big.setBackground(null);
                small.setBackground(null);
                single.setBackground(null);
                both.setBackground(null);
                maximum.setBackground(null);
                big_single.setBackground(null);
                small_single.setBackground(null);
                big_both.setBackground(null);
                minimum.setBackground(null);
                break;
            case 10:
                minimum.setBackgroundResource(R.drawable.vote_submit_bg_odds);
                big.setBackground(null);
                small.setBackground(null);
                single.setBackground(null);
                both.setBackground(null);
                maximum.setBackground(null);
                big_single.setBackground(null);
                small_single.setBackground(null);
                big_both.setBackground(null);
                small_both.setBackground(null);
                break;

            default:
                break;
        }

    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            // System.out.println("获取的TAG值：" + daojishiTag);
            // if (daojishiTag.equals("1")) {
            // System.out.println("封盘后重启");
            // // 最新竞猜请求
            zuiXinGuessingRequest();
//			zuiXinLotteryRequest();
            // } else if (daojishiTag.equals("2")) {
            // System.out.println("开奖后刷新余额");
            // // 当前余额请求
//			touzhupagerYue();
            // }

            // register_getcode.setText("重新验证");
            // register_getcode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            // register_getcode.setClickable(false);
            // register_getcode.setText(millisUntilFinished / 1000 + "秒");

        }
    }

    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        if (popup != null) {
            popup = null;
        }
        super.onDetach();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@结束");
    }

    //取消投注
    private void requestCancelTheBetting() {
        String type = "";
        if (wanfaType.equals("bj")) {
            type = "bjkl8";
        } else if (wanfaType.equals("cakeno")) {
            type = "cakeno";
        }
        String expectNum = chatRoomExpect.getText().toString().trim();
        MsgController.getInstance().getCancelTheBetting(type + "$" + expectNum,
                new HttpCallback(getActivity()) {
                    @Override
                    public void onSuccess(Call call, String s) {
                        String state = JsonUtil.getStringValue(s, "state");
                        String biz_content = JsonUtil.getStringValue(s, "biz_content");

                        Toast.makeText(getActivity(), biz_content,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
