package com.developer.lecai.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.activity.ShowBonusDetailActivity;
import com.developer.lecai.bean.OpenResultBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.ImageUtil;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by liuwei on 2017/6/19.
 */

public class ShowBonusFragment extends BaseFragment {

    private ImageView ivReturn;
    private TextView tvTitle;

    private ListView lvShowbonus;
    private List<OpenResultBean> data = new ArrayList<>();
    private ShowBonusAdapter showBonusAdapter;

    @Override
    protected View getLayout() {
        return View.inflate(getContext(), R.layout.fragment_showbonus, null);
    }

    @Override
    protected void initView() {
        ivReturn = (ImageView) mRootView.findViewById(R.id.iv_return);
        tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
        lvShowbonus = (ListView) mRootView.findViewById(R.id.lv_showbonus);

        ivReturn.setVisibility(View.GONE);
        tvTitle.setText(R.string.title_showbonus);
    }

    @Override
    protected void initLinstener() {
        lvShowbonus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("---", "-----onItemClick---" + position);
                String cpCode = data.get(position).getCpcode();
                String cpName = data.get(position).getCpname();
                Intent intent = new Intent(getContext(), ShowBonusDetailActivity.class);
                intent.putExtra("cpCode", cpCode);
                intent.putExtra("cpName",cpName);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        LogUtils.e("开奖打印","kaijiang开奖------------");
        MsgController.getInstance().getOpenResultList(new HttpCallback(getActivity()) {
            @Override
            public void onSuccess(Call call, String s) {

                Log.e("开奖记录", "-----onItemClick---" + s);
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                if ("success".equals(state)) {
                    data = (List<OpenResultBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<OpenResultBean>>() {
                    }.getType());
                    if (showBonusAdapter==null) {
                        showBonusAdapter = new ShowBonusAdapter(getContext(), data);
                        lvShowbonus.setAdapter(showBonusAdapter);
                    }else{
                        showBonusAdapter.notifyDataSetInvalidated();
                    }
                } else if (state.equals("error")) {
                    Toast.makeText(getContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void clickEvent(View view) {

    }

    private class ShowBonusAdapter extends BaseAdapter {

        private Context mContext;
        private List<OpenResultBean> data;

        public ShowBonusAdapter(Context context, List<OpenResultBean> data) {
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
            if (convertView==null) {
                viewHodler = new ViewHodler();
                 convertView = View.inflate(mContext, R.layout.adapter_showbonus, null);
                viewHodler.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                viewHodler.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHodler.tv_period = (TextView) convertView.findViewById(R.id.tv_period);
                viewHodler.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                viewHodler.ll_balls = (LinearLayout) convertView.findViewById(R.id.ll_balls);
                convertView.setTag(viewHodler);
            }else{
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
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams((int) mContext.getResources().getDimension(R.dimen.w_59px),(int) mContext.getResources().getDimension(R.dimen.w_59px));
                params.rightMargin=(int) mContext.getResources().getDimension(R.dimen.w_15px);
                tv.setLayoutParams(params);
                tv.setBackgroundResource(R.drawable.circle_59);
                tv.setTextColor(getResources().getColor(R.color.white));
                tv.setTextSize(10);
                String string=split[i];
                tv.setText(string);
                tv.setGravity(Gravity.CENTER);
                viewHodler.ll_balls.addView(tv);
            }
            ImageUtil.setImageView(data.get(position).getImg(), viewHodler.iv_img);
            viewHodler.tv_title.setText(data.get(position).getCpname());
            viewHodler.tv_period.setText(data.get(position).getIssuenum());
            viewHodler.tv_time.setText(data.get(position).getOpentime());
            return convertView;
        }
        public class ViewHodler {
            ImageView  iv_img;
            TextView tv_title;
            TextView tv_period;
            TextView tv_time;
            LinearLayout ll_balls;
        }
    }
}