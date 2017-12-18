package com.developer.lecai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.AfterNoItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by wy201 on 2017-08-22.
 * 追号
 */

public class AfterNoAdapter extends RecyclerView.Adapter<AfterNoAdapter.ViewHolder> {

    private Context mContext;
    private List<AfterNoItemBean> mAfterNoList;
    private EventListener mListener;

    public AfterNoAdapter(Context context, List<AfterNoItemBean> list) {
        this.mContext = context;
        this.mAfterNoList = list;
    }

    public void setData(List<AfterNoItemBean> list) {
        if (null == list) {
            mAfterNoList = new ArrayList<>();
        } else {
            mAfterNoList = list;
        }
        notifyDataSetChanged();
    }

    public void setListener(EventListener listener) {
        mListener = listener;
    }

    @Override
    public AfterNoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_afternolist, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AfterNoAdapter.ViewHolder holder, int position) {
        AfterNoItemBean bean = mAfterNoList.get(position);
        if (bean.isStatus()) {
            holder.ivStatus.setImageResource(R.drawable.afterno_checked);
        } else {
            holder.ivStatus.setImageResource(R.drawable.afterno_unchecked);
        }
        holder.tvIssue.setText(bean.getIssue());
        holder.tvSumperissue.setText(String.format(mContext.getString(R.string.afterno_item_money), bean.getSum()));
        holder.etMultiple.setText(String.valueOf(bean.getMultiple()));
    }


    @Override
    public int getItemCount() {
        return null == mAfterNoList ? 0 : mAfterNoList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_status)
        ImageView ivStatus;
        @BindView(R.id.tv_issue)
        TextView tvIssue;
        @BindView(R.id.tv_sumperissue)
        TextView tvSumperissue;
        @BindView(R.id.tv_subtract)
        TextView tvSubtract;
        @BindView(R.id.et_multiple)
        EditText etMultiple;
        @BindView(R.id.tv_add)
        TextView tvAdd;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.iv_status)
        public void clickSelectImg() {
            if (null != mListener)
                mListener.clickSelect(getLayoutPosition());
        }

        @OnClick(R.id.tv_subtract)
        public void clickMultipleSub() {
            if (null != mListener) {
                mListener.clickSubMultiple(getLayoutPosition());
            }
        }

        @OnClick(R.id.tv_add)
        public void clickMultipleAdd() {
            if (null != mListener) {
                mListener.clickAddMultiple(getLayoutPosition());
            }
        }

        @OnTextChanged(value = R.id.et_multiple, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
        void afterTextChanged(Editable s) {
            if ("".equals(s.toString())) {
                return;
            }
            int inputMultiple = Integer.parseInt(s.toString());
            if (inputMultiple < 1) {
                inputMultiple = 1;
                etMultiple.setText(String.valueOf(1));
            }
            if (null != mListener) {
                mListener.inputMultiple(getLayoutPosition(), inputMultiple);
            }
        }

    }


    public interface EventListener {
        void clickSelect(int positon);

        void clickAddMultiple(int position);

        void clickSubMultiple(int position);

        void inputMultiple(int position, int multiple);
    }
}
