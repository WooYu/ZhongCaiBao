package com.developer.lecai.utils;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by liuwei on 2017/6/27.
 */

public class MyWebChromeClient extends WebChromeClient {
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }
}
