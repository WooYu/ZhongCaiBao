package com.developer.lecai.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.bean.YouHuiBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.Call;

/**
 * Created by liuwei on 2017/7/6.
 */

public class ActiveActivity extends BaseActivity {

    private View view;
    private TextView tv_active_get;
    private List<YouHuiBean> list;
    private TextView tv_active_v1;
    private TextView tv_active_v2;
    private TextView tv_active_v3;
    private TextView tv_active_v4;
    private TextView tv_active_min1;
    private TextView tv_active_max1;
    private TextView tv_active_percent1;
    private TextView tv_active_min2;
    private TextView tv_active_max2;
    private TextView tv_active_percent2;
    private TextView tv_active_min3;
    private TextView tv_active_max3;
    private TextView tv_active_percent3;
    private TextView tv_active_min4;
    private TextView tv_active_max4;
    private TextView tv_active_percent4;

    @Override
    public View getLayout() {

        view = View.inflate(ActiveActivity.this, R.layout.activity_active, null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("活动大厅");
        tv_active_get = (TextView) view.findViewById(R.id.tv_active_get);

        tv_active_v1 = (TextView) view.findViewById(R.id.tv_active_v1);
        tv_active_v2 = (TextView) view.findViewById(R.id.tv_active_v2);
        tv_active_v3 = (TextView) view.findViewById(R.id.tv_active_v3);
        tv_active_v4 = (TextView) view.findViewById(R.id.tv_active_v4);

        tv_active_min1 = (TextView) view.findViewById(R.id.tv_active_min1);
        tv_active_max1 = (TextView) view.findViewById(R.id.tv_active_max1);
        tv_active_percent1 = (TextView) view.findViewById(R.id.tv_active_percent1);

        tv_active_min2 = (TextView) view.findViewById(R.id.tv_active_min2);
        tv_active_max2 = (TextView) view.findViewById(R.id.tv_active_max2);
        tv_active_percent2 = (TextView) view.findViewById(R.id.tv_active_percent2);

        tv_active_min3 = (TextView) view.findViewById(R.id.tv_active_min3);
        tv_active_max3 = (TextView) view.findViewById(R.id.tv_active_max3);
        tv_active_percent3 = (TextView) view.findViewById(R.id.tv_active_percent3);

        tv_active_min4 = (TextView) view.findViewById(R.id.tv_active_min4);
        tv_active_max4 = (TextView) view.findViewById(R.id.tv_active_max4);
        tv_active_percent4 = (TextView) view.findViewById(R.id.tv_active_percent4);

    }

    @Override
    public void initListener() {
        tv_active_get.setOnClickListener(this);
    }

    @Override
    public void initData() {
        MsgController.getInstance().getYouHui(new HttpCallback(ActiveActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {

                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                Log.e("优惠大厅", s + "------" + biz_content);
                if (state.equals("success")) {
                    list = (List<YouHuiBean>) JsonUtil.parseJsonToList(biz_content,new TypeToken<List<YouHuiBean>>(){}.getType());

                    tv_active_v1.setText(list.get(0).getInfo());
                    tv_active_v2.setText(list.get(1).getInfo());
                    tv_active_v3.setText(list.get(2).getInfo());
                    tv_active_v4.setText(list.get(3).getInfo());

                    tv_active_min1.setText(list.get(0).getMinfee()+"");
                    tv_active_min2.setText(list.get(1).getMinfee()+"");
                    tv_active_min3.setText(list.get(2).getMinfee()+"");
                    tv_active_min4.setText(list.get(3).getMinfee()+"");

                    tv_active_max1.setText(list.get(0).getMaxfee()+"");
                    tv_active_max2.setText(list.get(1).getMaxfee()+"");
                    tv_active_max3.setText(list.get(2).getMaxfee()+"");
                    tv_active_max4.setText(list.get(3).getMaxfee()+"");

                    tv_active_percent1.setText(list.get(0).getKvalue()+"%");
                    tv_active_percent2.setText(list.get(1).getKvalue()+"%");
                    tv_active_percent3.setText(list.get(2).getKvalue()+"%");
                    tv_active_percent4.setText(list.get(3).getKvalue()+"%");

                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_active_get:
                submitInfo();
                break;
        }
    }

    private void submitInfo() {

        MsgController.getInstance().getYouHuiLinQu(new HttpCallback(ActiveActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                Log.e("优惠大厅领取礼金", s + "------" + biz_content);
                if (state.equals("success")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
