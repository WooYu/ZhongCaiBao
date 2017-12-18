package com.developer.lecai.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.utils.LogUtils;

import java.util.Calendar;
import java.util.HashMap;


/**
 * Created by lw on 16/5/16.
 */
public class ZiJinDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private ImageView ivCarAnim;
    private ImageView vBg;
    private HashMap<String, String> hashMap;

    private OnClickZJMXQueRenListener onClickQueRenListener;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Calendar ca = Calendar.getInstance();
    private int selecteTime;
    private final TextView tv_zijin_kaishi;
    private final TextView tv_zijin_jieshu;
    private final TextView tv_zijin_yizj;
    private final TextView tv_zijin_weizj;
    private final TextView tv_zijin_cxpj;
    private final TextView tv_zijin_hmzjjj;
    private final TextView tv_zijin_txsb;
    private final TextView tv_zijin_hmtk;
    private final TextView tv_zijin_sgkk;
    private final TextView tv_zijin_zjmx;
    private final TextView tv_zijin_jsbd;
    private final TextView tv_zijin_tzkk;
    private final TextView tv_zijin_hsmx;
    private final TextView tv_zijin_jjps;
    private final TextView tv_zijin_yxfd;
    private final TextView tv_zijin_cdfk;
    private final TextView tv_zijin_zhcz;
    private final TextView tv_zijin_hmyj;
    private final TextView tv_zijin_cxzd;
    private final TextView tv_zijin_zhtx;
    private final TextView tv_zijin_czdzs;
    private final TextView tv_zijin_bdtk;
    private final TextView tv_zijin_cyhm;
    private final TextView tv_zijin_cjbd;
    private final TextView tv_zijin_czqfk;
    private final TextView tv_zijin_cz;
    private final TextView tv_zijin_qr;

    public void setOnClickZJMXQueRenListener(OnClickZJMXQueRenListener onClickQueRenListener) {
        this.onClickQueRenListener = onClickQueRenListener;
    }

    public interface OnClickZJMXQueRenListener {

        void onClickQueRen(HashMap<String, String> hashMap);

    }

    public ZiJinDialog(Context context, int statusHeight) {
        super(context, R.style.dlgTheme);
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_zijinmingxi, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);

        // 设置窗口属性
        hashMap = new HashMap<>();

        hashMap.put("KSSJ", "0");
        hashMap.put("JSSJ", "0");
        hashMap.put("ZT", "0");
        hashMap.put("FL", "0");

        tv_zijin_kaishi = (TextView) view.findViewById(R.id.tv_zijin_kaishi);
        tv_zijin_jieshu = (TextView) view.findViewById(R.id.tv_zijin_jieshu);

        tv_zijin_yizj = (TextView) view.findViewById(R.id.tv_zijin_yizj);
        tv_zijin_weizj = (TextView) view.findViewById(R.id.tv_zijin_weizj);

        tv_zijin_cxpj = (TextView) view.findViewById(R.id.tv_zijin_cxpj);
        tv_zijin_hmzjjj = (TextView) view.findViewById(R.id.tv_zijin_hmzjjj);
        tv_zijin_txsb = (TextView) view.findViewById(R.id.tv_zijin_txsb);
        tv_zijin_hmtk = (TextView) view.findViewById(R.id.tv_zijin_hmtk);
        tv_zijin_sgkk = (TextView) view.findViewById(R.id.tv_zijin_sgkk);
        tv_zijin_zjmx = (TextView) view.findViewById(R.id.tv_zijin_zjmx);
        tv_zijin_jsbd = (TextView) view.findViewById(R.id.tv_zijin_jsbd);
        tv_zijin_tzkk = (TextView) view.findViewById(R.id.tv_zijin_tzkk);
        tv_zijin_hsmx = (TextView) view.findViewById(R.id.tv_zijin_hsmx);
        tv_zijin_jjps = (TextView) view.findViewById(R.id.tv_zijin_jjps);
        tv_zijin_yxfd = (TextView) view.findViewById(R.id.tv_zijin_yxfd);
        tv_zijin_cdfk = (TextView) view.findViewById(R.id.tv_zijin_cdfk);
        tv_zijin_zhcz = (TextView) view.findViewById(R.id.tv_zijin_zhcz);
        tv_zijin_hmyj = (TextView) view.findViewById(R.id.tv_zijin_hmyj);
        tv_zijin_cxzd = (TextView) view.findViewById(R.id.tv_zijin_cxzd);
        tv_zijin_zhtx = (TextView) view.findViewById(R.id.tv_zijin_zhtx);
        tv_zijin_czdzs = (TextView) view.findViewById(R.id.tv_zijin_czdzs);
        tv_zijin_bdtk = (TextView) view.findViewById(R.id.tv_zijin_bdtk);
        tv_zijin_cyhm = (TextView) view.findViewById(R.id.tv_zijin_cyhm);
        tv_zijin_cjbd = (TextView) view.findViewById(R.id.tv_zijin_cjbd);
        tv_zijin_czqfk = (TextView) view.findViewById(R.id.tv_zijin_czqfk);

        tv_zijin_cz = (TextView) view.findViewById(R.id.tv_zijin_cz);
        tv_zijin_qr = (TextView) view.findViewById(R.id.tv_zijin_qr);

        Window window = this.getWindow();
//		window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));
        window.setWindowAnimations(R.style.Animation);
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

       /* Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;*/
        tv_zijin_kaishi.setOnClickListener(this);
        tv_zijin_jieshu.setOnClickListener(this);
        
        tv_zijin_yizj.setOnClickListener(this);
        tv_zijin_weizj.setOnClickListener(this);

        tv_zijin_cxpj.setOnClickListener(this);
        tv_zijin_hmzjjj.setOnClickListener(this);
        tv_zijin_txsb.setOnClickListener(this);
        tv_zijin_hmtk.setOnClickListener(this);
        tv_zijin_sgkk.setOnClickListener(this);
        tv_zijin_zjmx.setOnClickListener(this);
        tv_zijin_jsbd.setOnClickListener(this);
        tv_zijin_tzkk.setOnClickListener(this);
        tv_zijin_hsmx.setOnClickListener(this);
        tv_zijin_jjps.setOnClickListener(this);
        tv_zijin_yxfd.setOnClickListener(this);
        tv_zijin_cdfk.setOnClickListener(this);
        tv_zijin_zhcz.setOnClickListener(this);
        tv_zijin_hmyj.setOnClickListener(this);
        tv_zijin_cxzd.setOnClickListener(this);
        tv_zijin_zhtx.setOnClickListener(this);
        tv_zijin_czdzs.setOnClickListener(this);
        tv_zijin_bdtk.setOnClickListener(this);
        tv_zijin_cyhm.setOnClickListener(this);
        tv_zijin_cjbd.setOnClickListener(this);
        tv_zijin_czqfk.setOnClickListener(this);

        tv_zijin_cz.setOnClickListener(this);
        tv_zijin_qr.setOnClickListener(this);
        
        WindowManager.LayoutParams params = window.getAttributes();
        params.y = statusHeight + getContext().getResources().getDimensionPixelSize(R.dimen.h_144px);
        params.width = width;
        params.height = height - params.y;
        params.gravity = Gravity.RIGHT|Gravity.TOP;
        window.setAttributes(params);
        setCanceledOnTouchOutside(false);
        //ll_hmjl_total.set
        setData();
    }

    private void setData() {


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_zijin_cz:
                hashMap.put("KSSJ", "0");
                hashMap.put("JSSJ", "0");
                hashMap.put("FL", "0");
                hashMap.put("ZT", "0");

                tv_zijin_yizj.setSelected(false);
                tv_zijin_weizj.setSelected(false);
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;

            case R.id.tv_zijin_qr:
                if (onClickQueRenListener!=null){
                    onClickQueRenListener.onClickQueRen(hashMap);
                }
                break;

            case R.id.tv_zijin_yizj:
                tv_zijin_yizj.setSelected(true);
                tv_zijin_weizj.setSelected(false);
                hashMap.put("ZT", "1");
                break;

            case R.id.tv_zijin_weizj:
                tv_zijin_yizj.setSelected(false);
                tv_zijin_weizj.setSelected(true);
                hashMap.put("ZT", "2");
                break;

            case R.id.tv_zijin_kaishi:
                mYear = ca.get(Calendar.YEAR);
                mMonth = ca.get(Calendar.MONTH);
                mDay = ca.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(mContext, onDateSetListener, mYear, mMonth, mDay).show();
                selecteTime=1;
                break;
            case R.id.tv_zijin_jieshu:
                mYear = ca.get(Calendar.YEAR);
                mMonth = ca.get(Calendar.MONTH);
                mDay = ca.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(mContext, onDateSetListener, mYear, mMonth, mDay).show();
                selecteTime=2;
                break;

            case R.id.tv_zijin_cxpj:
                hashMap.put("FL", "1");
                tv_zijin_cxpj.setSelected(true);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_hmzjjj:
                hashMap.put("FL", "2");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(true);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_txsb:
                hashMap.put("FL", "3");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(true);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_hmtk:
                hashMap.put("FL", "4");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(true);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_sgkk:
                hashMap.put("FL", "5");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(true);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_zjmx:
                hashMap.put("FL", "6");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(true);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_jsbd:
                hashMap.put("FL", "7");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(true);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_tzkk:
                hashMap.put("FL", "8");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(true);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_hsmx:
                hashMap.put("FL", "9");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(true);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_jjps:
                hashMap.put("FL", "10");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(true);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_yxfd:
                hashMap.put("FL", "11");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(true);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_cdfk:
                hashMap.put("FL", "12");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(true);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_zhcz:
                hashMap.put("FL", "13");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(true);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_hmyj:
                hashMap.put("FL", "14");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(true);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_cxzd:
                hashMap.put("FL", "15");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(true);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_zhtx:
                hashMap.put("FL", "16");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(true);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_czdzs:
                hashMap.put("FL", "17");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(true);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_bdtk:
                hashMap.put("FL", "18");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(true);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_cyhm:
                hashMap.put("FL", "19");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(true);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_cjbd:
                hashMap.put("FL", "20");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(true);
                tv_zijin_czqfk.setSelected(false);
                break;
            case R.id.tv_zijin_czqfk:
                hashMap.put("FL", "21");
                tv_zijin_cxpj.setSelected(false);
                tv_zijin_hmzjjj.setSelected(false);
                tv_zijin_txsb.setSelected(false);
                tv_zijin_hmtk.setSelected(false);
                tv_zijin_sgkk.setSelected(false);
                tv_zijin_zjmx.setSelected(false);
                tv_zijin_jsbd.setSelected(false);
                tv_zijin_tzkk.setSelected(false);
                tv_zijin_hsmx.setSelected(false);
                tv_zijin_jjps.setSelected(false);
                tv_zijin_yxfd.setSelected(false);
                tv_zijin_cdfk.setSelected(false);
                tv_zijin_zhcz.setSelected(false);
                tv_zijin_hmyj.setSelected(false);
                tv_zijin_cxzd.setSelected(false);
                tv_zijin_zhtx.setSelected(false);
                tv_zijin_czdzs.setSelected(false);
                tv_zijin_bdtk.setSelected(false);
                tv_zijin_cyhm.setSelected(false);
                tv_zijin_cjbd.setSelected(false);
                tv_zijin_czqfk.setSelected(true);
                break;
        }
    }

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            LogUtils.e("日期对话框",year+"-------"+monthOfYear+"----------"+dayOfMonth);
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            }
            LogUtils.e("日期对话框",days);
            if (selecteTime==1){
                tv_zijin_kaishi.setText(days);
                hashMap.put("KSSJ", days);
            }else if(selecteTime==2){
                tv_zijin_jieshu.setText(days);
                hashMap.put("JSSJ", days);
            }
        }
    };


}
