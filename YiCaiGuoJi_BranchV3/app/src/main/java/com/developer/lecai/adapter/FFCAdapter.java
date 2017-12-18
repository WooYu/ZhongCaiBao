package com.developer.lecai.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.ShowBonusBean;

import java.util.List;

/**
 * Created by liuwei on 2017/8/12.
 */

public class FFCAdapter extends BaseAdapter {

    private Context mContext;
    private List<ShowBonusBean> data;

    public FFCAdapter(Context context){
        this.mContext=context;
    }

    public void setData(List<ShowBonusBean> list){
        this.data=list;
        notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {
        return data==null?0:data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.ffc_listview_item, null);
            viewHolder=new ViewHolder();
            viewHolder.tv_ffc_qiaho=(TextView)view.findViewById(R.id.tv_ffc_qiaho);
            viewHolder.ll_balls=(LinearLayout)view.findViewById(R.id.ll_balls);

            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)view.getTag();
        }

        String resultNum = data.get(position).getResultnum();
        String[] split = resultNum.split(" ");
        int count = split.length;
        if (viewHolder.ll_balls != null) {
            viewHolder.ll_balls.removeAllViews();
        }
        for (int i = 0; i < count; i++) {
            TextView tv = new TextView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) mContext.getResources().getDimension(R.dimen.w_59px), (int) mContext.getResources().getDimension(R.dimen.w_59px));
            params.rightMargin = (int) mContext.getResources().getDimension(R.dimen.w_15px);
            tv.setLayoutParams(params);
            tv.setBackgroundResource(R.drawable.circle_59);
            tv.setTextColor(mContext.getResources().getColor(R.color.white));
            tv.setTextSize(10);
            String string = split[i];
            tv.setText(string);
            tv.setGravity(Gravity.CENTER);
            viewHolder.ll_balls.addView(tv);
        }
        viewHolder.tv_ffc_qiaho.setText(data.get(position).getIssuenum());
        return view;
    }

    public class ViewHolder {
        private TextView tv_ffc_qiaho;
        private LinearLayout ll_balls;
    }
}
