package com.developer.lecai.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.activity.KeFuActivity;
import com.developer.lecai.activity.MineTouZhuActivity;
import com.flyco.dialog.widget.popup.BasePopup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-08-25.
 * 客服和投注记录对话框
 */

public class ServiceAndRecordDialog extends BasePopup<ServiceAndRecordDialog> {

    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_betrecord)
    TextView tvRecord;

    public ServiceAndRecordDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreatePopupView() {
        View inflate = View.inflate(mContext, R.layout.dialog_service_record, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        tvRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "cais", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, MineTouZhuActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @OnClick(R.id.tv_service)
    public void onTvServiceClicked() {
        Intent intent = new Intent(mContext, KeFuActivity.class);
        mContext.startActivity(intent);
    }
//
//    @OnClick(R.id.tv_record)
//    public void onTvRecordClicked() {
//       
//    }
}
