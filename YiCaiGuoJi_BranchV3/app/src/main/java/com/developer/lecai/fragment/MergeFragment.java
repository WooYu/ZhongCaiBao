package com.developer.lecai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.developer.lecai.R;
import com.developer.lecai.activity.KeFuActivity;
import com.developer.lecai.adapter.MergeDetailFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwei on 2017/6/19.
 */

public class MergeFragment extends BaseFragment {

    private View view;
    private TabLayout tl_merge_tableLayout;
    private ViewPager vp_merge_viewpager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private EditText et_sousuo_edit;
    private ImageView iv_sousuo_kefu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View getLayout() {
        view = View.inflate(getContext(), R.layout.fragment_merge, null);
        return view;
    }

    @Override
    protected void initView() {

        et_sousuo_edit = (EditText) view.findViewById(R.id.et_sousuo_edit);
        iv_sousuo_kefu = (ImageView) view.findViewById(R.id.iv_sousuo_kefu);
        tl_merge_tableLayout = (TabLayout) view.findViewById(R.id.tl_merge_tableLayout);
        vp_merge_viewpager = (ViewPager) view.findViewById(R.id.vp_merge_viewpager);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (null != fragmentList && fragmentList.size() > 0) {
            fragmentList.clear();
        }
        tl_merge_tableLayout.removeAllTabs();
        MergeDetailJinXingFragment mergeDetailJinXingFragment = new MergeDetailJinXingFragment();
        MergeDetailJinDuFragment mergeDetailJinDuFragment = new MergeDetailJinDuFragment();
        MergeDetailFangAnFragment mergeDetailFangAnFragment = new MergeDetailFangAnFragment();
        fragmentList.add(mergeDetailJinXingFragment);
        fragmentList.add(mergeDetailJinDuFragment);
        fragmentList.add(mergeDetailFangAnFragment);
        titleList.add(getResources().getString(R.string.mergeDetail_jingXing));
        titleList.add(getResources().getString(R.string.mergeDetail_jingDu));
        titleList.add(getResources().getString(R.string.mergeDetail_fangAn));
        tl_merge_tableLayout.addTab(tl_merge_tableLayout.newTab().setText(R.string.mergeDetail_jingXing), 0);
        tl_merge_tableLayout.addTab(tl_merge_tableLayout.newTab().setText(R.string.mergeDetail_jingDu), 1);
        tl_merge_tableLayout.addTab(tl_merge_tableLayout.newTab().setText(R.string.mergeDetail_fangAn), 2);
        MergeDetailFragmentAdapter mergeDetailFragmentAdapter = new MergeDetailFragmentAdapter(getChildFragmentManager(), fragmentList, titleList);

        vp_merge_viewpager.setOffscreenPageLimit(2);
        vp_merge_viewpager.setAdapter(mergeDetailFragmentAdapter);
        tl_merge_tableLayout.setupWithViewPager(vp_merge_viewpager);

    }

    @Override
    protected void initLinstener() {
        iv_sousuo_kefu.setOnClickListener(this);
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void clickEvent(View view) {

        switch (view.getId()){

            case R.id.iv_sousuo_kefu:

                Intent intent=new Intent(getContext(), KeFuActivity.class);
                startActivity(intent);
                break;
        }
    }
}
