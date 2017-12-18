package com.developer.lecai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.BettedBean;
import com.developer.lecai.view.TToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by wy201 on 2017-08-22.
 * 投注列表
 */

public class BettingListMarkSixAdapter extends RecyclerView.Adapter<BettingListMarkSixAdapter.BettingViewHolder> {

    private Context context;
    private List<BettedBean> mBetList;
    private OnItemViewClickListener mClickListener;
    private boolean binding;

    public BettingListMarkSixAdapter(Context context, List<BettedBean> list) {
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
        View view = View.inflate(context, R.layout.item_marksix_bettinglist, null);
        BettingViewHolder bettingViewHolder = new BettingViewHolder(view);
        return bettingViewHolder;
    }

    @Override
    public void onBindViewHolder(BettingViewHolder holder, int position) {
        binding = true;
        BettedBean bettedBean = mBetList.get(position);
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
        int amount = (int) (Double.parseDouble(bettedBean.getPayFee())
                / Integer.parseInt(bettedBean.getNum()));
        holder.etAmmountPerNote.setText(String.valueOf(amount));
        holder.tvGamename.setText(bettedBean.getTypeName());
        holder.tvNoteinfo.setText(String.format(context.getString(R.string.bet_notes_money),
                bettedBean.getNum(), bettedBean.getPayFee()));
        binding = false;
    }

    class BettingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_betnumber)
        TextView tvBetnumber;
        @BindView(R.id.tv_gamename)
        TextView tvGamename;
        @BindView(R.id.tv_noteinfo)
        TextView tvNoteinfo;
        @BindView(R.id.et_amountpernote)
        EditText etAmmountPerNote;

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

        @OnTextChanged(value = R.id.et_amountpernote,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
        public void changeAmountPerNote(Editable s){
            if(binding){
                return;
            }

            String amount = etAmmountPerNote.getText().toString().trim();
            if("".equals(amount)){
                return;
            }
            double amountDouble = Double.parseDouble(amount);
            if(0 == amountDouble){
                TToast.show(context,context.getString(R.string.marksix_amountlimit),TToast.LENGTH_SHORT);
                return;
            }
            if(null != mClickListener){
                mClickListener.changeAmount(getLayoutPosition(),amountDouble);
            }
        }
    }

    public interface OnItemViewClickListener {
        void onItemClick(int position);
        void deleteItem(int position);
        void changeAmount(int position,double amount);
    }

}
