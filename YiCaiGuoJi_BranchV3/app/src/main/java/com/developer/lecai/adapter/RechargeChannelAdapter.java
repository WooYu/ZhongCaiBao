package com.developer.lecai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.PatternOfPaymentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-09-16.
 * 充值渠道
 */

public class RechargeChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<PatternOfPaymentBean> mData;
    private OnCustomItemListener mListener;

    public interface OnCustomItemListener{
        void onItemClick(int position);
    }

    public RechargeChannelAdapter(Context mContext, List<PatternOfPaymentBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setCustomItemListener(OnCustomItemListener listener){
        this.mListener = listener;
    }

    public void setmData(List<PatternOfPaymentBean> list){
        this.mData = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rechargechannel,parent,false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PatternOfPaymentBean bean = mData.get(position);
        if(holder instanceof ChannelViewHolder){
            ChannelViewHolder channelViewHolder= (ChannelViewHolder) holder;
            channelViewHolder.ivStatus.setSelected(bean.isSelector());
            channelViewHolder.tvChannelName.setText(bean.getPaytype());
        }
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_channelname)
        TextView tvChannelName;
        @BindView(R.id.iv_status)
        ImageView ivStatus;

        public ChannelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.rl_root)
        void onItemClick(){
            if(null != mListener){
                mListener.onItemClick(getLayoutPosition());
            }
        }
    }
}
