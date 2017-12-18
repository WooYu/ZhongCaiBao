package com.developer.lecai.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.bean.GongGaoBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.Call;


/**
 * Created by liuwei on 16/5/16.
 */
public class HomeDialog extends Dialog {

    private Activity mContext;

    private ImageView ivCarAnim;
    private ImageView vBg;
    private final TextView tv_home_title;
    private final TextView tv_home_content;
    private final TextView tv_home_sure;

    public HomeDialog(Activity context) {
        super(context, R.style.dlgTheme);
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dlg_home, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);
        // 设置窗口属性

        tv_home_title = (TextView) view.findViewById(R.id.tv_home_title);
        tv_home_content = (TextView) view.findViewById(R.id.tv_home_content);
        tv_home_sure = (TextView) view.findViewById(R.id.tv_home_sure);
        Window window = this.getWindow();
//		window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));

        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;


        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.height = height;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setCanceledOnTouchOutside(false);

        setData();
        tv_home_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeDialog.this.dismiss();
            }
        });
    }

    private void setData() {

        MsgController.getInstance().getNotice(1, 1, new HttpCallback(mContext) {
            @Override
            public void onSuccess(Call call, String s) {
                Log.e("弹出框", s);
                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                if ("success".equals(state)) {
                    List<GongGaoBean> list= (List<GongGaoBean>) JsonUtil.parseJsonToList(biz_content,new TypeToken<List<GongGaoBean>>(){}.getType());
                    String title = list.get(0).getTitle();
                    String tips = list.get(0).getTips();
                    tv_home_title.setText(title);
                    tv_home_content.setText(tips);
                } else {
                    Toast.makeText(getContext(), biz_content, Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}
