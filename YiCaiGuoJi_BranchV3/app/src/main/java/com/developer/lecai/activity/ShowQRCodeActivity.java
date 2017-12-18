package com.developer.lecai.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.LoginBean;
import com.developer.lecai.bean.PatternOfPaymentBean;
import com.developer.lecai.control.UserController;
import com.developer.lecai.http.H;
import com.developer.lecai.utils.MyUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 在线二维码
 */
public class ShowQRCodeActivity extends AppCompatActivity {
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.webview)
    WebView webView;

    private String money;
    private PatternOfPaymentBean patternOfPaymentBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qrcode);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        money = getIntent().getStringExtra("money");
        patternOfPaymentBean = (PatternOfPaymentBean) getIntent().getSerializableExtra("payment");

        if (null == money || "".equals(money) || null == patternOfPaymentBean) {
            return;
        }

        loadTheQRCode();
    }

    @OnClick(R.id.iv_return)
    void onBack() {
        finish();
    }

    //加载二维码
    private void loadTheQRCode() {
        UserController userController = UserController.getInstance();
        LoginBean loginBean = userController.getLoginBean();
        String account;
        if (loginBean == null) {
            account = "";
        } else {
            account = loginBean.getLoginname();
        }
        String imei = MyUtil.getIMEI(this);
        String signature = MyUtil.getSignature(account);
        String url = H.URL.ONLINEQRCODEURL + "paytype=" + patternOfPaymentBean.getTypecode()
                + "&type=" + patternOfPaymentBean.getPaycode()
                + "&money=" + money + "&account=" + account +
                "&imei=" + imei + "&signature=" + signature;

        //System.out.println("二维码地址：" + url);

        webView.loadUrl(url);
        // 支持JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        //自适应屏幕
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        //设置webView支持缩放
        webView.getSettings().setBuiltInZoomControls(true);
        //支持保存数据
        webView.getSettings().setSaveFormData(false);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                return true;
            }
        });
    }

}
