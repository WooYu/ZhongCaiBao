package com.developer.lecai.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;


import com.developer.lecai.R;
import com.developer.lecai.adapter.GuideAdapter;
import com.developer.lecai.fragment.GuideFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwei on 2016/9/20.
 */
public class GuideActivity extends FragmentActivity {
    private ViewPager viewPager;
    private List<GuideFragment> fragments = new ArrayList<>();

    private GuideAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
        initData();

    }


    protected void initView() {
        viewPager = (ViewPager) findViewById(R.id.guide_vp);
    }


    protected void initData() {
//        GuideFragment guideFragment1 = new GuideFragment();
//        GuideFragment guideFragment2 = new GuideFragment();
//        guideFragment2.setBigPicImage(R.drawable.guide_2);
        GuideFragment guideFragment3 = new GuideFragment();
        guideFragment3.setVisibility(true);
//        fragments.add(guideFragment1);
//        fragments.add(guideFragment2);
        fragments.add(guideFragment3);
        if (adapter == null) {
            adapter = new GuideAdapter(getSupportFragmentManager(), fragments);
            viewPager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }


    }

}
