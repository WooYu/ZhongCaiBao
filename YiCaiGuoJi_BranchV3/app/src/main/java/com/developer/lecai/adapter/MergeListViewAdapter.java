package com.developer.lecai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.MergeBean;
import com.developer.lecai.view.RoundProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwei on 2017/7/3.
 */

public class MergeListViewAdapter<T> extends BaseAdapter {
    private Context context;
    private List<MergeBean> list;

    public MergeListViewAdapter(Context context, List<MergeBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    public void setData(List<MergeBean> datalist){
        if(null == datalist){
            list = new ArrayList<>();
        }else{
            list = datalist;
        }
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        viewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new viewHolder();
            convertView = View.inflate(context, R.layout.mergedetail_listitem, null);
            convertView.setTag(viewHolder);
            viewHolder.pb_listitem_progress = (RoundProgressBar) convertView.findViewById(R.id.pb_listitem_progress);
            viewHolder.tv_listitem_caipiao = (TextView) convertView.findViewById(R.id.tv_listitem_caipiao);
            viewHolder.tv_listitem_name = (TextView) convertView.findViewById(R.id.tv_listitem_name);
            viewHolder.tv_mergedetail_total = (TextView) convertView.findViewById(R.id.tv_mergedetail_total);
            viewHolder.tv_mergedetail_single = (TextView) convertView.findViewById(R.id.tv_mergedetail_single);
            viewHolder.tv_mergedetail_win = (TextView) convertView.findViewById(R.id.tv_mergedetail_win);
            viewHolder.tv_listitem_zhuihao = (TextView) convertView.findViewById(R.id.tv_listitem_zhuihao);
            viewHolder.tv_merge_progress = (TextView) convertView.findViewById(R.id.tv_merge_progress);
            viewHolder.tv_merge_bdprogress = (TextView) convertView.findViewById(R.id.tv_merge_bdprogress);
            viewHolder.tv_merge_zhuiHao = (TextView) convertView.findViewById(R.id.tv_merge_zhuiHao);
        } else {
            viewHolder = (viewHolder) convertView.getTag();
        }


        viewHolder.pb_listitem_progress.setProgress((int) list.get(position).getFsjd());
        viewHolder.tv_merge_progress.setText((int) list.get(position).getFsjd()+"%");
        viewHolder.tv_merge_bdprogress.setText("保"+(int)list.get(position).getBdjd()+"%");

        viewHolder.tv_listitem_caipiao.setText(list.get(position).getCpname());
        viewHolder.tv_listitem_name.setText(list.get(position).getUsername());
        viewHolder.tv_mergedetail_total.setText(list.get(position).getTotalfee()+"元");
        viewHolder.tv_mergedetail_single.setText(list.get(position).getFenfee()+"元");
        String stutas="";
        switch (list.get(position).getStatus()){
            case 0:
                stutas="未开奖";
                break;
            case 1:
                stutas="未中奖";
                break;
            case 2:
                stutas="已中奖";
                break;
            case 3:
                stutas="撤单";
                break;
            case 5:
                stutas="认购中";
                break;
        }
        viewHolder.tv_mergedetail_win.setText(stutas);
        String zhuiHao="";
        if (list.get(position).getIsbett()==1){
            zhuiHao="是";
            viewHolder.tv_merge_zhuiHao.setVisibility(View.VISIBLE);
        }else{
            zhuiHao="否";
            viewHolder.tv_merge_zhuiHao.setVisibility(View.INVISIBLE);
        }
        viewHolder.tv_listitem_zhuihao.setText(zhuiHao);
        return convertView;
    }

    public class viewHolder {

        private RoundProgressBar pb_listitem_progress;
        private TextView tv_listitem_caipiao;
        private TextView tv_listitem_name;
        private TextView tv_mergedetail_total;
        private TextView tv_mergedetail_single;
        private TextView tv_mergedetail_win;
        private TextView tv_listitem_zhuihao;
        private TextView tv_merge_progress;
        private TextView tv_merge_bdprogress;
        private TextView tv_merge_zhuiHao;
    }

}
