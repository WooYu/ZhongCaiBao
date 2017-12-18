package com.developer.lecai.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.ChippedBean;
import com.developer.lecai.view.TToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

/**
 * Created by wy201 on 2017-08-23.
 * 合买侧滑框
 */

public class ChippedPopup {

    @Nullable
    @BindView(R.id.leftview)
    View leftview;
    @Nullable
    @BindView(R.id.tv_chippedamount)
    TextView tvChippedamount;
    @Nullable
    @BindView(R.id.et_chippedcopies)
    EditText etChippedcopies;
    @Nullable
    @BindView(R.id.et_offertobuy)
    EditText etOffertobuy;
    @Nullable
    @BindView(R.id.et_guaranteednum)
    EditText etGuaranteednum;
    @Nullable
    @BindView(R.id.rg_security)
    RadioGroup rgSecurity;
    @Nullable
    @BindView(R.id.tv_sumpayable)
    TextView tvSumpayable;
    @Nullable
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    private static ChippedPopup mChippedPopup;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private View mArchorView;

    double mSumTotal = 0;//方案金额
    double mChippedSumPayable = 0;//合买应支付的金额
    int mChippedTotalNum = 2;//合买的总份数
    int mChippedOfferNum = 0;//我要认购的份数
    int mChippedMinNum = 0;//保底份数
    int mSecrecyType;//保密类型0 完全 1截止 2跟单 3保密
    private SubmitListener mSubmitListener;
    private boolean mFocusTotal, mFocusOffer, mFocusMin;

    private ChippedPopup(Context context, View view, View archorview) {
        this.mContext = context;
        this.mArchorView = archorview;

        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        ButterKnife.bind(this, view);
        mPopupWindow.setAnimationStyle(R.style.Animation);

        rgSecurity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_whole:
                        mSecrecyType = 0;
                        break;
                    case R.id.rb_cutoff:
                        mSecrecyType = 1;
                        break;
                    case R.id.rb_tailafter:
                        mSecrecyType = 2;
                        break;
                    case R.id.rb_secrecy:
                        mSecrecyType = 3;
                        break;
                }
            }
        });
    }

    public static ChippedPopup getIntance(Context context, View view, View archorview) {
        if (null == mChippedPopup) {
            synchronized (ChippedPopup.class) {
                if (null == mChippedPopup) {
                    mChippedPopup = new ChippedPopup(context, view, archorview);
                }
            }
        }else{
            mChippedPopup = null;
            mChippedPopup = new ChippedPopup(context, view, archorview);
        }

        return mChippedPopup;
    }

    @OnClick(R.id.leftview)
    public void onLeftviewClicked() {
        showHiddenPopupWindow(mSumTotal);
    }

    @OnClick(R.id.tv_commit)
    public void onTvCommitClicked() {
        if (0 == mChippedOfferNum) {
            TToast.show(mContext, R.string.chipped_nooffercopies, TToast.LENGTH_SHORT);
            return;
        }

        ChippedBean mChippedBean = new ChippedBean();
        mChippedBean.setBDNum(mChippedMinNum);
        mChippedBean.setZFSNum(mChippedTotalNum);
        mChippedBean.setFenFee(mSumTotal / mChippedTotalNum);
        mChippedBean.setHMType(mSecrecyType);
        mChippedBean.setPlayUserNum(1);
        mChippedBean.setComNum(mChippedOfferNum);
        mChippedBean.setTotalFee(mSumTotal);//mChippedSumPayable
        showHiddenPopupWindow(mSumTotal);
        if (null != mSubmitListener) {
            mSubmitListener.clickSubmit(mChippedBean);
        }
    }

    @OnFocusChange(value = R.id.et_chippedcopies)
    void onFocusChange_totalCopies(View view, boolean hasFocus) {
        mFocusTotal = hasFocus;
    }

    @OnFocusChange(value = R.id.et_offertobuy)
    void onFocusChange_offerCopies(View view, boolean hasFocus) {
        mFocusOffer = hasFocus;
    }

    @OnFocusChange(value = R.id.et_guaranteednum)
    void onFocusChange_minCopies(View view, boolean hasFocus) {
        mFocusMin = hasFocus;
    }

    @OnTextChanged(value = R.id.et_chippedcopies, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChange_totalCopies() {
        if (!mFocusTotal)
            return;

        String inputStr = etChippedcopies.getText().toString().trim();
        if ("".equals(inputStr)) {
            return;
        }

        int totalCopies = Integer.parseInt(inputStr);
        if (0 == totalCopies) {
            return;
        }
        if (mSumTotal / totalCopies < 0.01) {
            TToast.show(mContext, R.string.chipped_eachlimit, TToast.LENGTH_SHORT);
           // etChippedcopies.setText(String.valueOf(mSumTotal * 100));
            etChippedcopies.setText("");
            return;
        }

        String sum = mSumTotal / totalCopies+"";
        if (sum.indexOf(".")!=-1){
            //  Log.d("test","小数点位数:"+(sum.length()-sum.indexOf(".")-1));
            if((sum.length()-sum.indexOf(".")-1)>2){
                TToast.show(mContext, R.string.chipped_eachlimit_error, TToast.LENGTH_SHORT);
            }
        }
        mChippedTotalNum = totalCopies;
        if (mChippedOfferNum + mChippedMinNum < mChippedTotalNum) {
            etOffertobuy.setText(String.valueOf(mChippedOfferNum));
            etGuaranteednum.setText(String.valueOf(mChippedMinNum));
            updataChippedView();
            return;
        }

        if (mChippedOfferNum > mChippedTotalNum && mChippedMinNum <= mChippedTotalNum) {
            mChippedOfferNum = mChippedTotalNum - mChippedMinNum;
        } else if (mChippedMinNum > mChippedTotalNum && mChippedOfferNum <= mChippedTotalNum) {
            mChippedMinNum = mChippedTotalNum - mChippedOfferNum;
        } else if (mChippedOfferNum >= mChippedTotalNum && mChippedMinNum >= mChippedTotalNum) {
            mChippedOfferNum = mChippedTotalNum;
            mChippedMinNum = 0;
        } else {
            mChippedMinNum = mChippedTotalNum - mChippedOfferNum;
        }
        etOffertobuy.setText(String.valueOf(mChippedOfferNum));
        etGuaranteednum.setText(String.valueOf(mChippedMinNum));
        updataChippedView();
    }

    @OnTextChanged(value = R.id.et_offertobuy, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChange_offerCopies() {
        if (!mFocusOffer)
            return;

        String inputStr = etOffertobuy.getText().toString().trim();
        if ("".equals(inputStr)) {
            return;
        }
        int offerCopies = Integer.parseInt(inputStr);
        if (offerCopies > mChippedTotalNum) {
            offerCopies = mChippedTotalNum;
            etOffertobuy.setText(String.valueOf(offerCopies));
            return;
        }

        mChippedOfferNum = offerCopies;
        if (mChippedOfferNum + mChippedMinNum < mChippedTotalNum) {
            etGuaranteednum.setText(String.valueOf(mChippedMinNum));
            updataChippedView();
            return;
        }

        mChippedMinNum = mChippedTotalNum - mChippedOfferNum;
        etGuaranteednum.setText(String.valueOf(mChippedMinNum));
        updataChippedView();
    }

    @OnTextChanged(value = R.id.et_guaranteednum, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChange_minCopies() {
        if (!mFocusMin)
            return;

        String inputStr = etGuaranteednum.getText().toString().trim();
        if ("".equals(inputStr)) {
            return;
        }
        int minCopies = Integer.parseInt(inputStr);
        if (minCopies > mChippedTotalNum) {
            minCopies = mChippedTotalNum;
            etGuaranteednum.setText(String.valueOf(minCopies));
            return;
        }

        mChippedMinNum = minCopies;
        if (mChippedOfferNum + mChippedMinNum < mChippedTotalNum) {
            etOffertobuy.setText(String.valueOf(mChippedOfferNum));
            updataChippedView();
            return;
        }
        mChippedOfferNum = mChippedTotalNum - mChippedMinNum;
        etOffertobuy.setText(String.valueOf(mChippedOfferNum));
        updataChippedView();
    }

    //设置默认的合买界面
    private void setDefaultChippedView(double sum) {
        this.mSumTotal = sum;
        mChippedTotalNum = 2;
        mChippedOfferNum = 0;
        mChippedMinNum = 0;

        tvChippedamount.setText(String.format(mContext.getString(R.string.chipped_amount), mSumTotal));//方案金额
        etChippedcopies.setText(String.valueOf(mChippedTotalNum));
        etOffertobuy.setText(String.valueOf(""));
        etGuaranteednum.setText("");
        tvSumpayable.setText(String.format(mContext.getString(R.string.chipped_sumpayable), 0f));//应付金额
        rgSecurity.check(R.id.rb_whole);
    }

    //更新合买界面
    private void updataChippedView() {
        double amountPerCopy = mSumTotal / mChippedTotalNum;
        mChippedSumPayable = amountPerCopy * (mChippedMinNum + mChippedOfferNum);
        tvSumpayable.setText(String.format(mContext.getString(R.string.chipped_sumpayable),
                mChippedSumPayable));

    }

    public void showHiddenPopupWindow(double sum) {

        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            setDefaultChippedView(sum);
            mPopupWindow.showAsDropDown(mArchorView);
        }
    }

    public interface SubmitListener {
        void clickSubmit(ChippedBean bean);
    }

    public void setmSubmitListener(SubmitListener listener) {
        this.mSubmitListener = listener;
    }

    public   void  dis(){
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
