package com.developer.lecai.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.developer.lecai.R;
import com.developer.lecai.adapter.HomePageBannerAdapter;
import com.developer.lecai.bean.ImgListBean;

import java.util.List;

/**
 * Created by liujiao on 2017/6/12.
 */

public class CycleRelativeLayout extends RelativeLayout {
    public final int LOOP_START = 111;
    public final int LOOP_TIME=3*1000;
    private ViewPager mViewPager;
    private LinearLayout linearLayout;
    /** 将小圆点的图片用数组表示 */
    private ImageView[] indicators;
    private LoopHandler mHandler;
    private HomePageBannerAdapter adapter;
    private boolean autoScroll = true;
    private int loopTime = 0;
    private Context mContext;

    public CycleRelativeLayout(Context mContext){
        super(mContext);
        initView(mContext);
    }

    public CycleRelativeLayout(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        initView(mContext);
    }

    public CycleRelativeLayout(Context mContext, AttributeSet attrs, int defStyleAttr) {
        super(mContext, attrs, defStyleAttr);
        initView(mContext);
    }

    public void start(){
        if(null == mHandler || adapter == null){
            return;
        }
        if(adapter.getItemsCount() >1 && autoScroll){
            if(mHandler.hasMessages(LOOP_START)){
                mHandler.removeMessages(LOOP_START);
            }
            if(loopTime > 0){
                mHandler.sendEmptyMessageDelayed(LOOP_START,loopTime);
            }else{
                mHandler.sendEmptyMessageDelayed(LOOP_START,LOOP_TIME);
            }
        }
    }

    public void pause(){
        mHandler.removeMessages(LOOP_START);
    }

    private class LoopHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                    case LOOP_START:
                        int index = mViewPager.getCurrentItem();
                        mViewPager.setCurrentItem(index + 1);
                        if(autoScroll){
                            if(loopTime >0){
                                mHandler.sendEmptyMessageDelayed(LOOP_START,loopTime);
                            }else{
                                mHandler.sendEmptyMessageDelayed(LOOP_START,LOOP_TIME);
                            }
                        }
                        break;
                }
        }
    }

    private void initView(Context mContext){
        this.mContext = mContext;
        View.inflate(mContext,R.layout.fragment_homepage_banner,this);
        mViewPager = (ViewPager) findViewById(R.id.vgBanner);
        linearLayout = (LinearLayout) findViewById(R.id.llPoints);
        mHandler = new LoopHandler();

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //当页面切换时设置导航点的状态
                setPointStatus(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state){
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mHandler.removeMessages(LOOP_START);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if(!mHandler.hasMessages(LOOP_START)){
                            if(loopTime >0){
                                mHandler.sendEmptyMessageDelayed(LOOP_START,loopTime);
                            }else{
                                mHandler.sendEmptyMessageDelayed(LOOP_START,LOOP_TIME);
                            }
                        }
                        break;
                }
            }
        });

    }

    //设置导航点的状态
    private void setPointStatus(int position) {
        if(adapter.getItemsCount() <=1 ){
            return;
        }
        position=position % adapter.getItemsCount();
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(R.drawable.dot);
            // 不是当前选中的page，其小圆点设置为未选中的状态
            if (position == i) {
                indicators[i].setBackgroundResource(R.drawable.dot_select);
            }
        }
    }

    /**
     * 初始化viewpager
     *
     * @param showPosition 默认显示位置
     */
    public void setData(List<ImgListBean> list, HomePageBannerAdapter.ImageCycleViewListener listener, int showPosition) {
        int ivSize = list.size();
        if (ivSize == 0) {
            setVisibility(View.GONE);
            return;
        }
        adapter = new HomePageBannerAdapter(mContext, list);
        adapter.setmImageCycleViewListener(listener);
        mViewPager.setAdapter(adapter);

        if(ivSize == 1){
            mViewPager.setCurrentItem(0);
            linearLayout.setVisibility(View.INVISIBLE);
            return;
        }
        mViewPager.setCurrentItem(showPosition);
        initLinearLayout(ivSize, mContext);
    }


    private void initLinearLayout(int length,Context mContext) {
        // 设置指示器
        indicators = new ImageView[length];
        linearLayout.removeAllViews();
        for (int i = 0; i < length; i++) {
            indicators[i] = new ImageView(mContext);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params1.setMargins(30, 0, 0, 20);
            indicators[i].setLayoutParams(params1);
            indicators[i].setScaleType(ImageView.ScaleType.FIT_XY);
            if (i == 0) {
                indicators[i].setBackgroundResource(R.drawable.dot_select);
            } else {
                indicators[i].setBackgroundResource(R.drawable.dot);
            }
            linearLayout.addView(indicators[i]);
        }
        start();
    }

    public void releaseHandler(){
        if(null != mHandler){
            mHandler.removeMessages(LOOP_START);
        }
    }

    //设置是否自动循环，默认自动循环滚动
    public void setAutoScroll(boolean autoScroll){
        this.autoScroll = autoScroll;
    }

    //设置循环时间，默认0
    public void setLoopTime(int loopTime){
        this.loopTime = loopTime;
    }



}
