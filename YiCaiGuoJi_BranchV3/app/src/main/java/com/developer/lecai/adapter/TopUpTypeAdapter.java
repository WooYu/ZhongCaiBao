package com.developer.lecai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.RechargeTypeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-09-16.
 * 充值类型
 */

public class TopUpTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int Text_View = 1;
    private final int Payment_View = 2;

    private Context mContext;
    private List<RechargeTypeBean> mData;
    private OnItemClickListener mListener;

    public TopUpTypeAdapter(Context mContext, List<RechargeTypeBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setData(List<RechargeTypeBean> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == Text_View) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recharge_a, parent, false);
            return new RechargeTypeViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recharge_b, parent, false);
            return new ChannelTypeViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RechargeTypeBean rechargeTypeBean = mData.get(position);
        if (holder instanceof RechargeTypeViewHolder) {
            RechargeTypeViewHolder rechargeTypeViewHolder = (RechargeTypeViewHolder) holder;
            rechargeTypeViewHolder.mTypeTv.setText(rechargeTypeBean.getTypename());
        } else if (holder instanceof ChannelTypeViewHolder) {
            ChannelTypeViewHolder channelTypeViewHolder = (ChannelTypeViewHolder) holder;
            channelTypeViewHolder.mChannelTv.setText(rechargeTypeBean.getTypename());
            channelTypeViewHolder.mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        if (position == mData.size() - 1) {
                            mListener.clickBankPayment(position);
                        } else {
                            mListener.clickOnlinePayment(position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position || (mData.size() > 2 && position == mData.size() - 2)
                || (2 == mData.size())) {
            return Text_View;
        }
        return Payment_View;
    }


    public interface OnItemClickListener {
        void clickOnlinePayment(int position);

        void clickBankPayment(int position);
    }

    class RechargeTypeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_type)
        TextView mTypeTv;

        public RechargeTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ChannelTypeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_recharge)
        RelativeLayout mRootView;
        @BindView(R.id.tv_channel)
        TextView mChannelTv;

        public ChannelTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
