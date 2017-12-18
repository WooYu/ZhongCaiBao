package com.developer.lecai.fragment;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.activity.ActiveActivity;
import com.developer.lecai.activity.Beijing28Activity;
import com.developer.lecai.activity.FenFenCaiActivity;
import com.developer.lecai.activity.KeFuActivity;
import com.developer.lecai.activity.MarkSixActivity;
import com.developer.lecai.activity.NoticeActivity;
import com.developer.lecai.activity.RechargeActivity;
import com.developer.lecai.adapter.HomeItemAdapter;
import com.developer.lecai.adapter.HomePageBannerAdapter;
import com.developer.lecai.adapter.ListViewWinnerRecordAdapter;
import com.developer.lecai.bean.FanDianBean;
import com.developer.lecai.bean.HomeDataBean;
import com.developer.lecai.bean.HomeItemBean;
import com.developer.lecai.bean.ImgListBean;
import com.developer.lecai.bean.WinnerRecordBean;
import com.developer.lecai.control.FanDianController;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.ui.CycleRelativeLayout;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.utils.SharedPreferencesUtils;
import com.developer.lecai.view.CustomerListView;
import com.developer.lecai.view.ObservableScrollView;
import com.developer.lecai.view.TToast;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by liuwei on 2017/6/19.
 */

public class HomeFragment extends BaseFragment {

    // banner
    private CycleRelativeLayout cycleBanner;
    // 跑马灯文字
    private TextView tvTips;
    // tablayout
    private TabLayout tlHomeTableLayout;
    // 彩票列表
    private GridView gvItems;

    private List<ImgListBean> imgList = new ArrayList<>(); // banner数据列表
    private String[] tabText = {"充值中心", "消息公告", "优惠活动", "联系客服"};
    private int[] tabImgs = {R.mipmap.home_recharge, R.mipmap.home_msg, R.mipmap.home_discounts,
            R.mipmap.home_service};

    private String[] itemText = {"北京幸运28", "加拿大幸运28", "五分彩", "分分彩", "重庆时时彩", "北京PK10", "天津时时彩", "江苏快3", "北京快3",};
    private int[] itemImgs = {R.drawable.home_item_1, R.drawable.home_item_2, R.drawable.home_item_3, R.drawable.home_item_4, R.drawable.home_item_5,
            R.drawable.home_item_6, R.drawable.home_item_7, R.drawable.home_item_7, R.drawable.home_item_7};
    private List<HomeItemBean> homeItems = new ArrayList<>();

   /* {
        for (int i = 0; i < itemText.length; i++) {
            HomeItemBean bean = new HomeItemBean();
          //  bean.setImg(itemImgs[i]);
            bean.setTitle(itemText[i]);
            bean.setDesc("每天179期，五分钟..");
            homeItems.add(bean);
        }
    }*/

    private MsgController msgController;

    // 轮播图点击事件回调
    private HomePageBannerAdapter.ImageCycleViewListener mAdCycleViewListener = new HomePageBannerAdapter.ImageCycleViewListener() {
        @Override
        public void onImageClick(ImgListBean info, int position, View imageView) {
            Log.d("---", "-----onImageClick------" + position);
        }

    };
    private CustomerListView lv_shouye_list;
    private List<WinnerRecordBean> winnerRecordBeen;
    private ListViewWinnerRecordAdapter listViewWinnerRecordAdapter;
    private ObservableScrollView sv_home_scrollview;
    private TextView tv_home_title;

    @Override
    protected View getLayout() {
        msgController = MsgController.getInstance();
        return View.inflate(getContext(), R.layout.fragment_home, null);
    }

    @Override
    protected void initView() {
        cycleBanner = (CycleRelativeLayout) mRootView.findViewById(R.id.cycleBanner);
        tvTips = (TextView) mRootView.findViewById(R.id.tv_tips);
        tlHomeTableLayout = (TabLayout) mRootView.findViewById(R.id.tl_home_tableLayout);
        lv_shouye_list = (CustomerListView) mRootView.findViewById(R.id.lv_shouye_list);
        sv_home_scrollview = (ObservableScrollView) mRootView.findViewById(R.id.sv_home_scrollview);
        tv_home_title = (TextView) mRootView.findViewById(R.id.tv_home_titlename);


        initTabView(tlHomeTableLayout, tabText, tabImgs);
        gvItems = (GridView) mRootView.findViewById(R.id.gv_items);

//        if (!(boolean) SPUtils.get4SP("HOMEDIALOGSHOW", false)) {
//            HomeDialog homeDialog = new HomeDialog(getActivity());
//            homeDialog.show();
//            SPUtils.set2SP("HOMEDIALOGSHOW", true);
//        }
    }

    /**
     * 初始化tab
     *
     * @param tab
     * @param text
     * @param img
     */
    private void initTabView(TabLayout tab, String[] text, int[] img) {
        for (int i = 0; i < text.length; i++) {
            View view = View.inflate(getContext(), R.layout.layout_home_tab, null);
            ImageView ivImg = (ImageView) view.findViewById(R.id.iv_img);
            TextView tvText = (TextView) view.findViewById(R.id.tv_text);
            ivImg.setImageResource(img[i]);
            tvText.setText(text[i]);
//            if (i == 0) {
//                tvText.setTextColor(getResources().getColor(R.color.color_e8554e));
//            } else {
//                tvText.setTextColor(getResources().getColor(R.color.color_999999));
//            }

            tab.addTab(tlHomeTableLayout.newTab().setCustomView(view), false);
        }
    }

    /**
     * 更新tab状态
     *
     * @param selectItem
     */
    private void updateTabs(TabLayout.Tab selectItem) {
        for (int i = 0; i < tlHomeTableLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tlHomeTableLayout.getTabAt(i);
            TextView tv = (TextView) tab.getCustomView().findViewById(R.id.tv_text);
            ImageView iv = (ImageView) tab.getCustomView().findViewById(R.id.iv_img);
            if (selectItem == tab) {
                tv.setTextColor(getResources().getColor(R.color.color_e8554e));
                iv.setSelected(true);
            } else {
                tv.setTextColor(getResources().getColor(R.color.color_999999));
                iv.setSelected(false);
            }
        }


    }

    @Override
    protected void initLinstener() {
        final int distance= (int) getResources().getDimension(R.dimen.h_586px);
        sv_home_scrollview.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                LogUtils.e("位移",y+"---------------"+oldy+"--------"+distance);
                if (y>distance){
                    tv_home_title.setVisibility(View.VISIBLE);
                }else{
                    tv_home_title.setVisibility(View.GONE);
                }
            }
        });

        tlHomeTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                clickTab(position);
//                updateTabs(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                clickTab(position);
            }
        });
        gvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeItemBean homeItemBean = homeItems.get(position);
                if ("0".equals(homeItemBean.getStatus())) {
                    TToast.show(getContext(), "暂停销售", TToast.TIME_3);
                    return;
                }

                String cpcode = homeItemBean.getCpcode();
                Intent intent = new Intent();
                if ("bjkl8".equals(cpcode)) {//北京幸运28
                    intent.setClass(getActivity(), Beijing28Activity.class);
                    intent.putExtra(Beijing28Activity.WANFATYPE, "bj");
                    startActivity(intent);
                } else if ("cakeno".equals(cpcode)) {//加拿大幸运28
                    intent.setClass(getActivity(), Beijing28Activity.class);
                    intent.putExtra(Beijing28Activity.WANFATYPE, "cakeno");
                    startActivity(intent);
                } else if ("hk6".equals(cpcode)) {//六合彩
                    intent.setClass(getActivity(), MarkSixActivity.class);
                    intent.putExtra("homeitembean", homeItemBean);
                    startActivity(intent);
                } else {//分分彩、三分彩、五分彩、北京赛车、重庆时时彩
                    intent.setClass(getActivity(), FenFenCaiActivity.class);
                    intent.putExtra("homeitembean", homeItemBean);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void initData() {

       /* msgController.getPeiLv(new HttpCallback() {
            @Override
            public void onSuccess(Call call, String s) {
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                Log.e("赔率说明", s+"------" + biz_content);
            }
        });*/

        msgController.getBackModelList(new HttpCallback(getActivity()) {
            @Override
            public void onSuccess(Call call, String s) {
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                Log.e("系统返点列表", s + "------" + biz_content);
                String state = JsonUtil.getFieldValue(s, "state");
                if ("success".equals(state)) {
                    List<FanDianBean> fanDianList = (List<FanDianBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<FanDianBean>>() {
                    }.getType());
                    FanDianController.getInstance().setFanDianBean(fanDianList);
                }
            }
        });

        msgController.getLotteryList(new HttpCallback(getActivity()) {
            @Override
            public void onSuccess(Call call, String s) {
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                Log.e("彩种列表", s + "------" + biz_content);

                homeItems = (List<HomeItemBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<HomeItemBean>>() {
                }.getType());

                gvItems.setAdapter(new HomeItemAdapter(getContext(), homeItems));
                setListViewHeightBasedOnChildren(gvItems);
            }
        });
        msgController.getHomeData(new HttpCallback(getActivity()) {
            @Override
            public void onSuccess(Call call, String s) {

                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                Log.e("首页数据", s + "------" + biz_content);
                HomeDataBean homeDataBean = JsonUtil.parseJsonToBean(biz_content, HomeDataBean.class);
                SharedPreferencesUtils.putValue(getContext(), "KeFuUrl", homeDataBean.getKfurl());
                tvTips.setText(homeDataBean.getGgcontent());
            }
        });

        // 在加载数据前设置是否循环
        msgController.getLBT(1, new HttpCallback(getActivity()) {
            @Override
            public void onSuccess(Call call, String s) {
                // 成功处理
                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                Log.e("轮播图", "------" + biz_content);
                String state = JsonUtil.getFieldValue(s, "state");
                if ("success".equals(state)) {
                    imgList = (List<ImgListBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<ImgListBean>>() {
                    }.getType());
                    cycleBanner.setData(imgList, mAdCycleViewListener, 0);
                }
            }
        });
        //首页中奖排行
        MsgController.getInstance().getWinnerRecord(new HttpCallback(getActivity()) {
            @Override
            public void onSuccess(Call call, String s) {

                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                Log.e("首页中奖排行", "------" + biz_content);
                String state = JsonUtil.getFieldValue(s, "state");
                if ("success".equals(state)) {
                    winnerRecordBeen = (List<WinnerRecordBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<WinnerRecordBean>>() {
                    }.getType());
                    if (listViewWinnerRecordAdapter==null) {
                        listViewWinnerRecordAdapter = new ListViewWinnerRecordAdapter(getActivity(), winnerRecordBeen);
                        lv_shouye_list.setAdapter(listViewWinnerRecordAdapter);
                    }else{
                        listViewWinnerRecordAdapter.notifyDataSetInvalidated();
                    }
                } else {
                    Toast.makeText(getContext(), biz_content, Toast.LENGTH_LONG).show();
                }

            }
        });
//        imgList = new ArrayList<>();
//        ImgListBean bean1 = new ImgListBean();
//        bean1.setAddress("file:///android_asset/test_img_1.jpg");
//        imgList.add(bean1);
//        ImgListBean bean2 = new ImgListBean();
//        bean2.setAddress("file:///android_asset/test_img_2.jpg");
//        imgList.add(bean2);
//        ImgListBean bean3 = new ImgListBean();
//        bean3.setAddress("file:///android_asset/test_img_3.jpg");
//        imgList.add(bean3);
//        cycleBanner.setData(imgList, mAdCycleViewListener, 0);


    }

    @Override
    protected void clickEvent(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != cycleBanner) {
            cycleBanner.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != cycleBanner) {
            cycleBanner.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != cycleBanner)
            cycleBanner.releaseHandler();
    }

    private void setListViewHeightBasedOnChildren(GridView gridView) {
        // 获取listview的adapter
        BaseAdapter gridViewAdapter = (BaseAdapter) gridView.getAdapter();
        if (gridViewAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 2;// listView.getNumColumns();
        int totalHeight = 0;
        int verticalSpace = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ? gridView.getVerticalSpacing() : getResources().getDimensionPixelOffset(R.dimen.h_37px);
        // i每次加2，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        for (int i = 0; i < gridViewAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = gridViewAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight() + verticalSpace;
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
//        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        gridView.setLayoutParams(params);
    }

    private void clickTab(int tabIndex) {
        Intent intent = new Intent();
        switch (tabIndex) {
            case 0:
                intent.setClass(getActivity(), RechargeActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent.setClass(getActivity(), NoticeActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent.setClass(getActivity(), ActiveActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent.setClass(getActivity(), KeFuActivity.class);
                startActivity(intent);
                break;
        }
    }
}
