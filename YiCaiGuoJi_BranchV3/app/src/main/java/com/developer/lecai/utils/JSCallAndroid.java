package com.developer.lecai.utils;

import android.app.Activity;
import android.webkit.JavascriptInterface;

/**
 * Created by raotf on 2017/5/9.
 */

public class JSCallAndroid {
    public static final String JS_CALL_NAME = "callAndroid";

    private Activity Context;

    public JSCallAndroid(Activity c) {
        Context = c;
    }

//    private NormalSharePop sharePop;

    @JavascriptInterface
    public void close() {

    }

    @JavascriptInterface
    public void share(String title, String text, String url, String siteurl, String imageurl) {
        // 显示分享对话框

//        sharePop = new NormalSharePop(Context, NormalPopOnClickListener.builder().setTitle(title).setText(text).
//                setHandler(handler).setUrl(url).setSiteUrl(siteurl).
//                setImageUrl(imageurl));
//        sharePop.showAtLocation(View.inflate(Context, R.layout.fragment_product, null), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


    }
//
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//
//            switch (msg.what) {
//
//                case 33:
//                    if (sharePop != null) {
//                        sharePop.dismiss();
//                    }
//                    break;
//
//            }
//
//            super.handleMessage(msg);
//        }
//    };

}
