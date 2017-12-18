package com.developer.lecai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.MarkSixBallTypeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-08-25.
 */

public class MarkSixAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MarkSixBallTypeBean> mBallList;
    private OnItemClickListener mListener;
    private int mType;
    public static final int ITEM_A = 1;//圆形
    public static final int ITEM_B = 2;//长方形
    public static final int ITEM_C = 3;//大长方形

    public MarkSixAdapter(Context mContext, List<MarkSixBallTypeBean> mBallList, int type) {
        this.mContext = mContext;
        this.mBallList = mBallList;
        this.mType = type;
    }

    public void setData(List<MarkSixBallTypeBean> list, int type) {
        this.mType = type;
        if (null == list || list.size() == 0) {
            list = new ArrayList<>();
        }
        mBallList = list;
        notifyDataSetChanged();
    }

    public void setItemClickLitener(OnItemClickListener litener) {
        this.mListener = litener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM_A:
                View viewa = layoutInflater.inflate(R.layout.item_marksix_a, null);
                viewHolder = new ViewHolderA(viewa);
                break;
            case ITEM_B:
                View viewb = layoutInflater.inflate(R.layout.item_marksix_b, null);
                viewHolder = new ViewHolderB(viewb);
                break;
            case ITEM_C:
                View viewc = layoutInflater.inflate(R.layout.item_marksix_c, null);
                viewHolder = new ViewHolderC(viewc);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        MarkSixBallTypeBean ballTypeBean = mBallList.get(position);
        if (ITEM_A == type) {
            ViewHolderA viewHolderA = (ViewHolderA) holder;
            viewHolderA.tvNumber.setText(ballTypeBean.getPname());
            viewHolderA.tvNumber.setSelected(ballTypeBean.isPitchon());
            viewHolderA.tvOdds.setText(ballTypeBean.getContent());
        } else if (ITEM_C == type) {
            ViewHolderC viewHolderC = (ViewHolderC) holder;
            viewHolderC.llBg.setSelected(ballTypeBean.isPitchon());
            viewHolderC.tvNumber.setText(ballTypeBean.getPname());
            viewHolderC.tvOdds.setText(ballTypeBean.getContent());
            viewHolderC.tvDesc.setText(ballTypeBean.getContent());
        } else {
            ViewHolderB viewHolderB = (ViewHolderB) holder;
            viewHolderB.tvNumber.setText(ballTypeBean.getPname());
            viewHolderB.tvNumber.setSelected(ballTypeBean.isPitchon());
            viewHolderB.tvOdds.setText(ballTypeBean.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return null == mBallList ? 0 : mBallList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mType;
    }

    public interface OnItemClickListener {
        void clickItem(int position);
    }

    public class ViewHolderA extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_odds)
        TextView tvOdds;

        public ViewHolderA(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_number)
        public void clicknumber() {
            notifyItemChanged(getLayoutPosition());
            if (null != mListener) {
                mListener.clickItem(getLayoutPosition());
            }
        }
    }

    public class ViewHolderB extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_odds)
        TextView tvOdds;

        public ViewHolderB(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_number)
        public void clicknumber() {
            notifyItemChanged(getLayoutPosition());
            if (null != mListener) {
                mListener.clickItem(getLayoutPosition());
            }
        }
    }

    public class ViewHolderC extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_odds)
        TextView tvOdds;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.ll_bg)
        LinearLayout llBg;

        public ViewHolderC(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ll_bg)
        public void clicknumber() {
            notifyItemChanged(getLayoutPosition());
            if (null != mListener) {
                mListener.clickItem(getLayoutPosition());
            }
        }
    }

}
