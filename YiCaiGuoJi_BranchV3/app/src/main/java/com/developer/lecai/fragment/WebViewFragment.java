package com.developer.lecai.fragment;

import android.app.Activity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.utils.JSCallAndroid;
import com.developer.lecai.utils.MyWebChromeClient;
import com.developer.lecai.utils.MyWebViewClient;

/**
 * WebView的Fragment
 * Created by raotf on 2017/5/9.
 */
public class WebViewFragment extends BaseFragment {

    private Activity mContext;
    private WebView webView;
    private String firstUrl;
    private String curUrl;
    private TextView tvTitle;

    @Override
    public View getLayout() {
        mContext = getActivity();
        return View.inflate(mContext, R.layout.fragment_webview, null);
    }

    @Override
    public void initView() {
        webView = (WebView) mRootView.findViewById(R.id.wv_web);
        tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);

        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setSavePassword(false);
        webSetting.setSaveFormData(false);
        webSetting.setSupportZoom(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getActivity().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        setWebViewClient(new MyWebViewClient());
        setWebChromeClient(new MyWebChromeClient());
        webView.addJavascriptInterface(new JSCallAndroid(mContext), JSCallAndroid.JS_CALL_NAME);


    }

    @Override
    protected void initLinstener() {
    }

    @Override
    public void initData() {
    }

    @Override
    public void clickEvent(View v) {
        getActivity().finish();

    }

    /**
     * 加载Url
     * @param url
     */
    public void loadUrl(String url) {
        this.firstUrl = url;
        webView.loadUrl(url);
    }

    /**
     * 获取webview组件
     * @return
     */
    public WebView getWebView() {
        return webView;
    }

    /**
     * 是否能返回
     * @return
     */
    public boolean canGoBack() {
        return webView.canGoBack();
    }

    public void setWebViewClient(MyWebViewClient client) {
        webView.setWebViewClient(client);
    }

    public void setWebChromeClient(MyWebChromeClient chromeClient) {
        webView.setWebChromeClient(chromeClient);
    }

    /**
     * 返回上一页
     */
    public void goBack() {
        webView.goBack();
    }

}
