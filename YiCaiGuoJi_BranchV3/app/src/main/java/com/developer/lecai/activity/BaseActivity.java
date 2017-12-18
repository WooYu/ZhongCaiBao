package com.developer.lecai.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.control.UserController;
import com.developer.lecai.dialog.WaitingDialog;


/**
 * 自定义的BaseActivity
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener, Handler.Callback {

    protected Resources res;
    protected Handler uiHandler;
    // 等待对话框
    private WaitingDialog waitingDialog;
    // 内容区域
    private FrameLayout flContent;
    // 标题栏
    private View titleBar;
    // 返回按钮
    private ImageView ivReturn;
    // 标题
    protected TextView tvTitle;
    private LinearLayout rlError;

    protected UserController userController;
    protected TextView tv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_base);
        userController = UserController.getInstance();
        res = getResources();
        uiHandler = new Handler(this);
        waitingDialog = new WaitingDialog(this);
        titleBar = findViewById(R.id.title_bar_base);
        ivReturn = (ImageView) findViewById(R.id.iv_return);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        flContent = (FrameLayout) findViewById(R.id.fl_content);
        rlError = (LinearLayout) findViewById(R.id.rl_error);
        View view = getLayout();
        if (view != null) {
            flContent.addView(view);
        }

        ivReturn.setOnClickListener(this);

        initView();
        initListener();
        initData();
    }

    public void showErrorPage(){
        flContent.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
    }

    /**
     * 设置内容区域
     *
     * @return
     */
    public abstract View getLayout();

    /**
     * 初始化View，获取所有的view
     */
    public abstract void initView();

    /**
     * 初始化事件，注册各种事件
     */
    public abstract void initListener();

    /**
     * 初始化数据，为各个组件初始化各种数据
     */
    public abstract void initData();

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 点击事件回调
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_return) {
            finish();
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    /**
     * 隐藏标题栏
     */
    public void hideTitleBar() {
        titleBar.setVisibility(View.GONE);
    }

    /**
     * 显示标题栏
     */
    public void showTitleBar() {
        titleBar.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右边文案
     */
    public void setRightTitleBar(String text) {

        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(text);
    }
    /**
     * 隐藏返回按钮
     */
    public void hideReturnButton() {
        ivReturn.setVisibility(View.INVISIBLE);
    }

    /**
     * 显示返回按钮
     */
    public void showReturnButton() {
        ivReturn.setVisibility(View.VISIBLE);
    }

    /**
     * 显示等待对话框
     */
    public void showWaitDialog() {
        if (waitingDialog != null) {
            waitingDialog.show();
        }
    }

    /**
     * 隐藏等待对话框
     */
    public void hideWaitDialog() {
        if (waitingDialog != null && waitingDialog.isShowing()) {
            waitingDialog.dismiss();
        }
    }

    public void toActivity(Class c) {
        toActivity(c, false);
    }
    public void toActivity(Class c, boolean isFinish) {
        toActivity(c, isFinish, null);
    }

    public void toActivity(Class c, boolean isFinish, Bundle bundle) {
        Intent intent = new Intent(this, c);
        if(bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if(isFinish) {
            finish();
        }
    }
}
