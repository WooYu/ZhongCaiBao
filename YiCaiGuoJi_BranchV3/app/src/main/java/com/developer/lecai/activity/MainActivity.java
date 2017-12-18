package com.developer.lecai.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.adapter.MyAdapter;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.fragment.HomeFragment;
import com.developer.lecai.fragment.MergeFragment;
import com.developer.lecai.fragment.ShowBonusFragment;
import com.developer.lecai.fragment.UserFragment;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


public class MainActivity extends BaseActivity {


    private View view;
    private ViewPager vp_main_viewPager;
    private TabLayout tl_main_tableLayout;
    private String[] tabText = {"首页", "合买", "开奖", "账户"};
    private int[] tabImgs = {R.drawable.select_home_icon, R.drawable.select_product_icon,
            R.drawable.select_brand_icon, R.drawable.select_my_icon};
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private HomeFragment homeFragment;
    private MergeFragment mergeFragment;
    private ShowBonusFragment showBonusFragment;
    private UserFragment userFragment;
    private MsgController msgController;
    @Override
    public View getLayout() {
        userController.syncUserInfo();
        msgController = MsgController.getInstance();
//        if(!userController.isLogin()) {
//            toActivity(LoginActivity.class);
//        }
        view = View.inflate(MainActivity.this, R.layout.activity_main, null);
        return view;
    }

    @Override
    public void initView() {
        hideTitleBar();
        vp_main_viewPager = (ViewPager) view.findViewById(R.id.vp_main_viewPager);
        tl_main_tableLayout = (TabLayout) view.findViewById(R.id.tl_main_tableLayout);
        initTabView(tl_main_tableLayout, tabText, tabImgs);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        if (mergeFragment == null) {
            mergeFragment = new MergeFragment();
        }
        if (showBonusFragment == null) {
            showBonusFragment = new ShowBonusFragment();
        }
        if (userFragment == null) {
            userFragment = new UserFragment();
        }
        fragmentList.add(homeFragment);
        fragmentList.add(mergeFragment);
        fragmentList.add(showBonusFragment);
        fragmentList.add(userFragment);

        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager(), fragmentList);
        vp_main_viewPager.setAdapter(myAdapter);
        updateTabs(tl_main_tableLayout.getTabAt(0));
        vp_main_viewPager.setCurrentItem(0);
        vp_main_viewPager.setOffscreenPageLimit(3);

    }

    @Override
    public void initListener() {
        tl_main_tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                updateTabs(tab);
                vp_main_viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        vp_main_viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl_main_tableLayout));
    }

    @Override
    public void initData() {
        msgController.getHomeTan(1,0, new HttpCallback(this){

            @Override
            public void onSuccess(Call call, String s) {
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String uri = JsonUtil.getStringValue(biz_content, "content");
                Log.d("test","uri="+uri);
                TongzhiDialog(uri);
            }
        });
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
            View view = getLayoutInflater().inflate(R.layout.layout_main_tab, null);
            ImageView ivImg = (ImageView) view.findViewById(R.id.iv_img);
            TextView tvText = (TextView) view.findViewById(R.id.tv_text);
            ivImg.setImageResource(img[i]);
            tvText.setText(text[i]);
            if (i == 0) {
                tvText.setTextColor(getResources().getColor(R.color.color_e8554e));
            } else {
                tvText.setTextColor(getResources().getColor(R.color.color_999999));
            }

            tab.addTab(tl_main_tableLayout.newTab().setCustomView(view), false);
        }
    }

    /**
     * 更新tab状态
     *
     * @param selectItem
     */
    private void updateTabs(TabLayout.Tab selectItem) {
        for (int i = 0; i < tl_main_tableLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tl_main_tableLayout.getTabAt(i);
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
    WebView wv_tongzhi;
    @SuppressLint("InflateParams")
    protected void TongzhiDialog(final String uri) {

        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.alertdialog_tongzhi,
                null);
        dialog.setView(layout);
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.alertdialog_tongzhi);

        Button bt_qd = (Button) window.findViewById(R.id.Yqueding);
        bt_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        wv_tongzhi = new WebView(this);

        wv_tongzhi= (WebView) window.findViewById(R.id.wv_tongzhi);

        //启用支持JavaScript
        wv_tongzhi.getSettings().setJavaScriptEnabled(true);
        //启用支持DOM Storage
        wv_tongzhi.getSettings().setDomStorageEnabled(true);
        //加载web资源

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wv_tongzhi.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wv_tongzhi.loadUrl(uri);
        // wv_tongzhi.loadUrl("file:///android_asset/tongzhi.html");

        Button bt_queding = (Button) window.findViewById(R.id.Yqueding);
        bt_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

}
