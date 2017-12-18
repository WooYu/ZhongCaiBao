package com.developer.lecai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.entiey.MyGameNotesEntity;

import java.util.List;

public class TouZhuAdapter extends BaseAdapter {

    private Context context;
    private List<MyGameNotesEntity> mgnList;

    public TouZhuAdapter(Context context, List<MyGameNotesEntity> mgnList) {
        this.context = context;
        this.mgnList = mgnList;
    }

    public void setData(List<MyGameNotesEntity> list) {
        this.mgnList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == mgnList ? 0 : mgnList.size();
    }

    @Override
    public Object getItem(int position) {
        return mgnList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.touzhu_item, null);

            viewHolder.touzhu_caipiao = (TextView) convertView.findViewById(R.id.touzhu_caipiao);
            viewHolder.touzhu_leixing = (TextView) convertView.findViewById(R.id.touzhu_leixing);
            viewHolder.touzhu_qihao = (TextView) convertView.findViewById(R.id.touzhu_qihao);
            viewHolder.touzhu_jiangjin = (TextView) convertView.findViewById(R.id.touzhu_jiangjin);
            viewHolder.touzhu_zhuangtai = (TextView) convertView.findViewById(R.id.touzhu_zhuangtai);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MyGameNotesEntity entity = mgnList.get(position);

        viewHolder.touzhu_caipiao.setText(entity.getCpname());
        viewHolder.touzhu_leixing.setText(entity.getTypename());
        viewHolder.touzhu_qihao.setText(dealiletou(entity.getContent()));
        viewHolder.touzhu_jiangjin.setText(entity.getWinfee() + "");
        String status = "";
        switch (entity.getStatus()) {
            case 0:
                status = "未开奖";
                break;
            case 1:
                status = "未中奖";
                break;
            case 2:
                status = "已中奖";
                break;
            case 3:
                status = "撤单";
                break;
            case 5:
                status = "认购中";
                break;
        }
        viewHolder.touzhu_zhuangtai.setText(status);
        return convertView;
    }

    static class ViewHolder {
        TextView touzhu_caipiao;
        TextView touzhu_leixing;
        TextView touzhu_qihao;
        TextView touzhu_jiangjin;
        TextView touzhu_zhuangtai;
    }

    //处理开奖结果
    private String dealiletou(String result) {
        if (null == result || "".equals(result)) {
            return "";
        }

        if (result.contains(context.getString(R.string.symbol_91))
                && result.contains(context.getString(R.string.symbol_93))) {
            return result.replace(context.getString(R.string.symbol_91), "")
                    .replace(context.getString(R.string.symbol_93), "");
        }
        return result;
    }
}
