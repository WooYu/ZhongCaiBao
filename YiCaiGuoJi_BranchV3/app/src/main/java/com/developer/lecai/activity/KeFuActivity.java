package com.developer.lecai.activity;

import android.view.View;

import com.developer.lecai.R;
import com.developer.lecai.fragment.WebViewFragment;
import com.developer.lecai.utils.SharedPreferencesUtils;

public class KeFuActivity extends BaseActivity {

    private View view;
    private WebViewFragment webViewFragment;

    @Override
    public View getLayout() {
        view = View.inflate(KeFuActivity.this, R.layout.activity_ke_fu,null);
        return view;
    }

    @Override
    public void initView() {

        tvTitle.setText("客服");
        webViewFragment = new WebViewFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_kefu, webViewFragment).commit();

       /* WebViewFragment   fg_kefu= (WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.fg_kefu);
        fg_kefu.loadUrl("https://www.baidu.com/");*/

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
        String keFuUrl = SharedPreferencesUtils.getValue(KeFuActivity.this, "KeFuUrl");
        webViewFragment.loadUrl(keFuUrl);
    }
}
