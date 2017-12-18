package com.developer.lecai.activity;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.ShowBonusBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ShowBonusDetailActivity extends BaseActivity {

    private View view;
    private ListView lv_showbonusdetail;
    private String cpCode;
    private List<ShowBonusBean> data = new ArrayList<>();
    private String cpName;

    @Override
    public View getLayout() {

        view = View.inflate(ShowBonusDetailActivity.this, R.layout.activity_show_bonus_detail, null);
        return view;
    }

    @Override
    public void initView() {

        lv_showbonusdetail = (ListView) view.findViewById(R.id.lv_showbonusdetail);


        cpCode = getIntent().getStringExtra("cpCode");
        cpName = getIntent().getStringExtra("cpName");
        tvTitle.setText(cpName);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

        MsgController.getInstance().getOpenListByCode(0 + "", cpCode, new HttpCallback(ShowBonusDetailActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                Log.e("开奖详细记录", "-----onItemClick---" + s);
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                String pageCount = JsonUtil.getFieldValue(s, "pageCount");
                if ("success".equals(state)) {
                    data = (List<ShowBonusBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<ShowBonusBean>>() {
                    }.getType());
                    lv_showbonusdetail.setAdapter(new ShowBonusDetailAdapter(ShowBonusDetailActivity.this, data));
                }
            }
        });


    }


    private class ShowBonusDetailAdapter extends BaseAdapter {

        private Context mContext;
        private List<ShowBonusBean> data;

        public ShowBonusDetailAdapter(Context context, List<ShowBonusBean> data) {
            this.mContext = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHodler viewHodler = null;
            if (convertView == null) {
                viewHodler = new ViewHodler();
                convertView = View.inflate(mContext, R.layout.adapter_showbonusdetail, null);
                viewHodler.tv_period_detail = (TextView) convertView.findViewById(R.id.tv_period_detail);
                viewHodler.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                viewHodler.ll_balls = (LinearLayout) convertView.findViewById(R.id.ll_balls);
                convertView.setTag(viewHodler);
            } else {
                viewHodler = (ViewHodler) convertView.getTag();
            }

            String resultNum = data.get(position).getResultnum();
            String[] split = resultNum.split(" ");
            int count = split.length;
            if (viewHodler.ll_balls != null) {
                viewHodler.ll_balls.removeAllViews();
            }
            for (int i = 0; i < count; i++) {
                TextView tv = new TextView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) mContext.getResources().getDimension(R.dimen.w_59px), (int) mContext.getResources().getDimension(R.dimen.w_59px));
                params.rightMargin = (int) mContext.getResources().getDimension(R.dimen.w_15px);
                tv.setLayoutParams(params);
                tv.setBackgroundResource(R.drawable.circle_59);
                tv.setTextColor(getResources().getColor(R.color.white));
                tv.setTextSize(10);
                String string = split[i];
                tv.setText(string);
                tv.setGravity(Gravity.CENTER);
                viewHodler.ll_balls.addView(tv);
            }

            viewHodler.tv_period_detail.setText(data.get(position).getIssuenum());
            viewHodler.tv_time.setText(data.get(position).getOpentime());
            return convertView;
        }

        public class ViewHodler {

            TextView tv_period_detail;
            TextView tv_time;
            LinearLayout ll_balls;

        }
    }


}
