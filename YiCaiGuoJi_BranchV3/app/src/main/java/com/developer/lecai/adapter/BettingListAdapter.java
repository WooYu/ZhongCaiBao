package com.developer.lecai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.BettedBean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wy201 on 2017-08-22.
 * 投注列表
 */

public class BettingListAdapter extends RecyclerView.Adapter<BettingListAdapter.BettingViewHolder> {

    private Context context;
    private List<BettedBean> mBetList;
    private OnItemViewClickListener mClickListener;

    public BettingListAdapter(Context context, List<BettedBean> list) {
        this.context = context;
        this.mBetList = list;
    }

    public void setData(List<BettedBean> list) {
        if (null == list) {
            mBetList = new ArrayList<>();
        } else {
            mBetList = list;
        }
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemViewClickListener listener) {
        this.mClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return null == mBetList ? 0 : mBetList.size();
    }

    @Override
    public BettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_bettinglist, null);
        BettingViewHolder bettingViewHolder = new BettingViewHolder(view);
        return bettingViewHolder;
    }

    @Override
    public void onBindViewHolder(BettingViewHolder holder, int position) {

        BettedBean bettedBean = mBetList.get(position);
        Log.d("test","mBetList:"+bettedBean.getNum());

        String content = bettedBean.getContent();
        //去除前后的中括号
        String modifierA = context.getString(R.string.symbol_91);
        String modifierB = context.getString(R.string.symbol_93);
        if (content.contains(modifierA)) {
            content = content.replace(modifierA, "");
        }
        if (content.contains(modifierB)) {
            content = content.replace(modifierB, "");
        }
        holder.tvBetnumber.setText(content);
        holder.tvGamename.setText(bettedBean.getTypeName());

        holder.tvNoteinfo.setText(String.format(context.getString(R.string.bet_notes_money),
                bettedBean.getNum(), bettedBean.getPayFee()));
    }

    class BettingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_betnumber)
        TextView tvBetnumber;
        @BindView(R.id.tv_gamename)
        TextView tvGamename;
        @BindView(R.id.tv_noteinfo)
        TextView tvNoteinfo;


        public BettingViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.iv_delete)
        public void clickDelete() {
            if (null != mClickListener) {
                notifyItemRemoved(getLayoutPosition());
                mClickListener.deleteItem(getLayoutPosition());
            }
        }

        @OnClick(R.id.ll_rootview)
        public void clickItem() {
            if (null != mClickListener) {
                mClickListener.onItemClick(getLayoutPosition());
            }
        }

    }

    public interface OnItemViewClickListener {
        void onItemClick(int position);

        void deleteItem(int position);
    }

}
