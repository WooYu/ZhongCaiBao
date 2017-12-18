package com.developer.lecai.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.developer.lecai.R;
import com.developer.lecai.adapter.MergeDetailFragmentAdapter;
import com.developer.lecai.fragment.NoticeMessageFragment;
import com.developer.lecai.fragment.NoticeNoticeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwei on 2017/7/6.
 */

public class NoticeActivity extends BaseActivity {

    private View view;
    private TabLayout tl_notice_tableLayout;
    private ViewPager vp_notice_viewPager;
    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String> titleList=new ArrayList<>();
    @Override
    public View getLayout() {

        view = View.inflate(NoticeActivity.this, R.layout.activity_notice,null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("公告");
        tl_notice_tableLayout = (TabLayout) view.findViewById(R.id.tl_notice_tableLayout);
        vp_notice_viewPager = (ViewPager) view.findViewById(R.id.vp_notice_viewPager);

        NoticeMessageFragment noticeMessageFragment=new NoticeMessageFragment();
        NoticeNoticeFragment noticeNoticeFragment=new NoticeNoticeFragment();
        fragmentList.add(noticeMessageFragment);
        fragmentList.add(noticeNoticeFragment);
        titleList.add(getResources().getString(R.string.notice_message));
        titleList.add(getResources().getString(R.string.notice_notice));
        tl_notice_tableLayout.addTab(tl_notice_tableLayout.newTab().setText(R.string.notice_message), 0);
        tl_notice_tableLayout.addTab(tl_notice_tableLayout.newTab().setText(R.string.notice_notice), 1);
        MergeDetailFragmentAdapter mergeDetailFragmentAdapter = new MergeDetailFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        vp_notice_viewPager.setAdapter(mergeDetailFragmentAdapter);
        tl_notice_tableLayout.setupWithViewPager(vp_notice_viewPager);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
