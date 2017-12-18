package com.developer.lecai.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.adapter.MyAdapter;
import com.developer.lecai.fragment.HuiShuiBBFragment;
import com.developer.lecai.fragment.HuiShuiGPLFragment;
import com.developer.lecai.fragment.HuiShuiHSFragment;

import java.util.ArrayList;
import java.util.List;

public class HuiShuiActivity extends BaseActivity {

    private View view;
    private RelativeLayout rl_huishui_hs;
    private TextView tv_huishui_hs;
    private RelativeLayout rl_huishui_bb;
    private TextView tv_huishui_bb;
    private RelativeLayout rl_huishui_gpl;
    private TextView tv_huishui_gpl;
    private ViewPager vp_huishui_vp;
    private List<Fragment> fragmentList = new ArrayList<>();
    private MyAdapter myAdapter;
    private TextView tv_huishui_money;

    @Override
    public View getLayout() {
        view = View.inflate(HuiShuiActivity.this, R.layout.activity_hui_shui, null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("我的回水");
        rl_huishui_hs = (RelativeLayout) view.findViewById(R.id.rl_huishui_hs);
        tv_huishui_hs = (TextView) view.findViewById(R.id.tv_huishui_hs);

        rl_huishui_bb = (RelativeLayout) view.findViewById(R.id.rl_huishui_bb);
        tv_huishui_bb = (TextView) view.findViewById(R.id.tv_huishui_bb);

        rl_huishui_gpl = (RelativeLayout) view.findViewById(R.id.rl_huishui_gpl);
        tv_huishui_gpl = (TextView) view.findViewById(R.id.tv_huishui_gpl);

        tv_huishui_money = (TextView) view.findViewById(R.id.tv_huishui_money);
        vp_huishui_vp = (ViewPager) view.findViewById(R.id.vp_huishui_vp);

        HuiShuiHSFragment huiShuiHSFragment = new HuiShuiHSFragment();
        HuiShuiBBFragment huiShuiBBFragment = new HuiShuiBBFragment();
        HuiShuiGPLFragment huiShuiGPLFragment = new HuiShuiGPLFragment();
        fragmentList.add(huiShuiHSFragment);
        fragmentList.add(huiShuiBBFragment);
        fragmentList.add(huiShuiGPLFragment);
        myAdapter = new MyAdapter(getSupportFragmentManager(), fragmentList);
        vp_huishui_vp.setAdapter(myAdapter);

    }

    @Override
    public void initListener() {
        rl_huishui_hs.setOnClickListener(this);
        rl_huishui_bb.setOnClickListener(this);
        rl_huishui_gpl.setOnClickListener(this);
    }

    @Override
    public void initData() {
        vp_huishui_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTabColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.rl_huishui_hs:

                vp_huishui_vp.setCurrentItem(0);
                break;
            case R.id.rl_huishui_bb:
                vp_huishui_vp.setCurrentItem(1);
                break;
            case R.id.rl_huishui_gpl:
                vp_huishui_vp.setCurrentItem(2);
                break;
        }
    }

    private void changeTabColor(int position) {
        switch (position) {

            case 0:
                rl_huishui_hs.setBackgroundResource(R.color.color_ff4949);
                tv_huishui_hs.setTextColor(getResources().getColor(R.color.white));

                rl_huishui_bb.setBackgroundResource(R.color.white);
                tv_huishui_bb.setTextColor(getResources().getColor(R.color.color_ff4949));
                rl_huishui_gpl.setBackgroundResource(R.color.white);
                tv_huishui_gpl.setTextColor(getResources().getColor(R.color.color_ff4949));
                break;
            case 1:
                rl_huishui_bb.setBackgroundResource(R.color.color_ff4949);
                tv_huishui_bb.setTextColor(getResources().getColor(R.color.white));

                rl_huishui_hs.setBackgroundResource(R.color.white);
                tv_huishui_hs.setTextColor(getResources().getColor(R.color.color_ff4949));
                rl_huishui_gpl.setBackgroundResource(R.color.white);
                tv_huishui_gpl.setTextColor(getResources().getColor(R.color.color_ff4949));
                break;

            case 2:
                rl_huishui_gpl.setBackgroundResource(R.color.color_ff4949);
                tv_huishui_gpl.setTextColor(getResources().getColor(R.color.white));

                rl_huishui_bb.setBackgroundResource(R.color.white);
                tv_huishui_bb.setTextColor(getResources().getColor(R.color.color_ff4949));
                rl_huishui_hs.setBackgroundResource(R.color.white);
                tv_huishui_hs.setTextColor(getResources().getColor(R.color.color_ff4949));
                break;
        }

    }

}
