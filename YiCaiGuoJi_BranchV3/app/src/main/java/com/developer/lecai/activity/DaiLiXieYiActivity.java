package com.developer.lecai.activity;

import android.view.View;

import com.developer.lecai.R;
import com.developer.lecai.fragment.WebViewFragment;
import com.developer.lecai.http.H;

public class DaiLiXieYiActivity extends BaseActivity {

    private View view;
    private WebViewFragment webViewFragment;

    @Override
    public View getLayout() {
        view = View.inflate(DaiLiXieYiActivity.this, R.layout.activity_dai_li_xie_yi, null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("代理协议");
        webViewFragment = new WebViewFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_dailixieyi_fragment, webViewFragment).commit();

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        webViewFragment.loadUrl(H.DOMAINName + "/Home/DLXY");
    }
}
