package com.developer.lecai.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.bean.CaiPiaoWanFaBean;
import com.developer.lecai.bean.FanDianBean;
import com.developer.lecai.control.FanDianController;
import com.developer.lecai.control.UserController;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.utils.MyUtil;

import java.util.List;


/**
 * Created by liuwei on 16/5/16.
 */
public class LaTiaoDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private final TextView tv_latiao_pl;
    private final TextView tv_latiao_fl;
    private final SeekBar sb_latiao_sb;
    private final EditText et_latiao_je;
    private final TextView tv_latiao_yuan;
    private final TextView tv_latiao_jiao;
    private final TextView tv_latiao_fen;
    private final TextView tv_latiao_ze;
    private final TextView tv_latiao_zs;
    private final TextView tv_latiao_zg;
    private final TextView tv_latiao_qx;
    private final TextView tv_latiao_qd;

    private CaiPiaoWanFaBean mCaiPiaoWanFanBean;
    private int mAmountPerNote;//每注金额
    private int mNotes;//注数
    private double mBonus;//奖金
    private double mMultipleInterval;//间隔倍数
    private double mGap;//差距
    private double mTopPrizePerNote;//单注最高奖金
    private String mTopPrizePerNoteStr;//最高奖金有金钱分隔符
    private double mOriginalTopPrizeNote;//原始单注最高奖金
    private double mRebate;//返利
    private int mPecuniaryUnit = 0;//金钱单位：元角分
    private LeftRightBtnClickListener mClickListener;


    public LaTiaoDialog(Context context, CaiPiaoWanFaBean caiPiaoWanFanBean, int zhuShu, int amountPernote) {
        super(context);
        mContext = context;
        this.mCaiPiaoWanFanBean = caiPiaoWanFanBean;
        this.mNotes = zhuShu;
        this.mAmountPerNote = amountPernote;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.latiao_dialog, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);

        tv_latiao_pl = (TextView) view.findViewById(R.id.tv_latiao_pl);
        tv_latiao_fl = (TextView) view.findViewById(R.id.tv_latiao_fl);
        sb_latiao_sb = (SeekBar) view.findViewById(R.id.sb_latiao_sb);
        et_latiao_je = (EditText) view.findViewById(R.id.et_latiao_je);
        tv_latiao_yuan = (TextView) view.findViewById(R.id.tv_latiao_yuan);
        tv_latiao_jiao = (TextView) view.findViewById(R.id.tv_latiao_jiao);
        tv_latiao_fen = (TextView) view.findViewById(R.id.tv_latiao_fen);
        tv_latiao_ze = (TextView) view.findViewById(R.id.tv_latiao_ze);
        tv_latiao_zs = (TextView) view.findViewById(R.id.tv_latiao_zs);
        tv_latiao_zg = (TextView) view.findViewById(R.id.tv_latiao_zg);
        tv_latiao_qx = (TextView) view.findViewById(R.id.tv_latiao_qx);
        tv_latiao_qd = (TextView) view.findViewById(R.id.tv_latiao_qd);

        Window window = this.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#66000000")));
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.height = height;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        String contentStr = mCaiPiaoWanFanBean.getContent();
        if (contentStr.contains("奖金")) {
            mBonus = Double.parseDouble(contentStr.substring(3));
        } else if (contentStr.contains("一等奖")) {
            int index = contentStr.indexOf(";二等奖");
            mBonus = Double.parseDouble(contentStr.substring(4, index));
        } else {
            mBonus = Double.parseDouble(contentStr);
        }

        String spaceRateStr = mCaiPiaoWanFanBean.getSpacerate();
        if (spaceRateStr.contains("|")) {
            int index1 = spaceRateStr.indexOf("|");
            mMultipleInterval = Double.parseDouble(spaceRateStr.substring(0, index1));
        } else {
            mMultipleInterval = Double.parseDouble(spaceRateStr);
        }

        int backid = UserController.getInstance().getLoginBean().getBackid();
        List<FanDianBean> fanDianList = FanDianController.getInstance().getFanDianBean();
        if (null == fanDianList)
            return;
        int count = fanDianList.size();
        int suoYin = 0;
        for (int i = 0; i < fanDianList.size(); i++) {
            int autoid = fanDianList.get(i).getAutoid();
            if (backid == autoid) {
                suoYin = i;
            }
        }
        mGap = count - suoYin - 1;
        mTopPrizePerNote = mOriginalTopPrizeNote = mBonus + mMultipleInterval * mGap;
        mTopPrizePerNoteStr = MyUtil.spiltAmt(mOriginalTopPrizeNote + "", 2);
        tv_latiao_pl.setText(mTopPrizePerNoteStr);
        tv_latiao_zg.setText(MyUtil.spiltAmt(mOriginalTopPrizeNote * mAmountPerNote / 2 + "", 2));

        tv_latiao_yuan.setSelected(true);
        tv_latiao_fl.setText(String.format(mContext.getString(R.string.selectnote_rebate),
                0f));
        et_latiao_je.setText(mAmountPerNote + "");
        tv_latiao_ze.setText(mNotes * mAmountPerNote + "");
        tv_latiao_zs.setText(mNotes + "");

        tv_latiao_qx.setOnClickListener(this);
        tv_latiao_qd.setOnClickListener(this);
        et_latiao_je.setOnClickListener(this);
        tv_latiao_yuan.setOnClickListener(this);
        tv_latiao_jiao.setOnClickListener(this);
        tv_latiao_fen.setOnClickListener(this);
        et_latiao_je.addTextChangedListener(textWatcher);

        sb_latiao_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double zhanBi = i * 0.01;
                LogUtils.e("占比", i + "---------" + zhanBi);
                mRebate = mGap * 0.1 * zhanBi;
                mTopPrizePerNote = mOriginalTopPrizeNote - (mOriginalTopPrizeNote - mBonus) * zhanBi;
                mTopPrizePerNoteStr = MyUtil.spiltAmt(mTopPrizePerNote + "", 2);
                tv_latiao_fl.setText(String.format(mContext.getString(R.string.selectnote_rebate),
                        mRebate));
                tv_latiao_pl.setText(mTopPrizePerNoteStr);

                if (mPecuniaryUnit == 0) {
                    tv_latiao_zg.setText(MyUtil.spiltAmt(mTopPrizePerNote * mAmountPerNote / 2 + "", 2));
                } else if (mPecuniaryUnit == 1) {
                    String dQJJ1 = MyUtil.spiltAmt(mTopPrizePerNote * mAmountPerNote / 2 * 0.1 + "", 2);
                    tv_latiao_zg.setText(dQJJ1);
                } else if (mPecuniaryUnit == 2) {
                    String dQJJ2 = MyUtil.spiltAmt(mTopPrizePerNote * mAmountPerNote / 2 * 0.01 + "", 2);
                    tv_latiao_zg.setText(dQJJ2);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_latiao_qx:
                dismiss();
                break;
            case R.id.tv_latiao_qd:
                clickSure();
                break;
            case R.id.tv_latiao_yuan:
                mPecuniaryUnit = 0;
                tv_latiao_yuan.setTextColor(mContext.getResources().getColor(R.color.color_e8554e));
                tv_latiao_yuan.setSelected(true);
                tv_latiao_jiao.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                tv_latiao_jiao.setSelected(false);
                tv_latiao_fen.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                tv_latiao_fen.setSelected(false);
                tv_latiao_ze.setText(mNotes * mAmountPerNote + "");
                tv_latiao_zg.setText(MyUtil.spiltAmt(mTopPrizePerNote * mAmountPerNote / 2 + "", 2));
                break;
            case R.id.tv_latiao_jiao:
                mPecuniaryUnit = 1;
                tv_latiao_yuan.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                tv_latiao_yuan.setSelected(false);
                tv_latiao_jiao.setTextColor(mContext.getResources().getColor(R.color.color_e8554e));
                tv_latiao_jiao.setSelected(true);
                tv_latiao_fen.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                tv_latiao_fen.setSelected(false);
                tv_latiao_ze.setText(String.format(mContext.getString(R.string.selectnote_totalamount)
                        , mNotes * mAmountPerNote * 0.1));
                tv_latiao_zg.setText(MyUtil.spiltAmt(mTopPrizePerNote * mAmountPerNote / 2 * 0.1 + "", 2));
                break;
            case R.id.tv_latiao_fen:
                mPecuniaryUnit = 2;
                tv_latiao_yuan.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                tv_latiao_yuan.setSelected(false);
                tv_latiao_jiao.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                tv_latiao_jiao.setSelected(false);
                tv_latiao_fen.setTextColor(mContext.getResources().getColor(R.color.color_e8554e));
                tv_latiao_fen.setSelected(true);
                tv_latiao_ze.setText(String.format(mContext.getString(R.string.selectnote_totalamount)
                        , mNotes * mAmountPerNote * 0.01));
                tv_latiao_zg.setText(MyUtil.spiltAmt(mTopPrizePerNote * mAmountPerNote / 2 * 0.01 + "", 2));
                break;
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String amountPerNoteStr = et_latiao_je.getText().toString().trim();
            double amountPerNoteInt = 0;
            if (null != amountPerNoteStr && !"".equals(amountPerNoteStr)) {
                mAmountPerNote = Integer.parseInt(amountPerNoteStr);
                if (mPecuniaryUnit == 1) {
                    amountPerNoteInt = mAmountPerNote * 0.1;
                } else if (mPecuniaryUnit == 2) {
                    amountPerNoteInt = mAmountPerNote * 0.01;
                } else {
                    amountPerNoteInt = mAmountPerNote;
                }
            } else {
                mAmountPerNote = 0;
            }

            tv_latiao_ze.setText(String.format(mContext.getString(R.string.selectnote_totalamount),
                    mNotes * amountPerNoteInt));
            tv_latiao_zg.setText(MyUtil.spiltAmt(mTopPrizePerNote * amountPerNoteInt / 2 + "", 2));
        }
    };

    public interface LeftRightBtnClickListener {
        void rightClick(String payfee, String num, String mtype, String modename, String multiply,int mAmountPerNote);
    }

    public void setLeftRightBtnClickListener(LeftRightBtnClickListener listener) {
        mClickListener = listener;
    }

    //确定
    private void clickSure() {
        if (mAmountPerNote %2 != 0) {
            Toast.makeText(mContext, R.string.selectnote_singlenotelimit_wanrning, Toast.LENGTH_SHORT).show();
            et_latiao_je.setText("");
            return;
        }
        dismiss();

        if (mAmountPerNote <= 0) {
            Toast.makeText(mContext, R.string.selectnote_singlenotelimit, Toast.LENGTH_SHORT).show();
            return;
        }

        String payfee = tv_latiao_ze.getText().toString().trim();
        String num = tv_latiao_zs.getText().toString().trim();
        String mtype = String.valueOf(mPecuniaryUnit);
        String modename = mTopPrizePerNote + "/" + mRebate + "%";
        String topprize = String.valueOf(mTopPrizePerNote);

        if (null != mClickListener) {
            mClickListener.rightClick(payfee, num, mtype, modename, topprize,mAmountPerNote);
        }
    }
}
