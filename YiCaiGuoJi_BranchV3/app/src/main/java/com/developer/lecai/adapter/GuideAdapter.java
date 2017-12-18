package com.developer.lecai.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.developer.lecai.fragment.GuideFragment;

import java.util.List;

/**
 * Created by ZhangCe on 2016/9/20.
 */
public class GuideAdapter extends FragmentPagerAdapter {
    private List<GuideFragment> fragments;

    public GuideAdapter(FragmentManager fm, List<GuideFragment> list) {
        super(fm);
        fragments = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
