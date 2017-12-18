package com.developer.lecai.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.developer.lecai.R;


/**
 * Created by raotf on 16/5/16.
 */
public class WaitingDialog extends Dialog{

    private Context mContext;

    private ImageView ivCarAnim;
    private ImageView vBg;

    public WaitingDialog(Context context) {
        super(context, R.style.dlgTheme);
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dlg_waiting, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);
        // 设置窗口属性
        Window window = this.getWindow();
//		window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));

        WindowManager.LayoutParams params = window.getAttributes();
//		params.width = (int) (res.getDimension(R.dimen.dlg2_ll_w));
//		params.height = (int) (res.getDimension(R.dimen.dlg2_ll_h));
        params.gravity = Gravity.CENTER;

        window.setAttributes(params);

        setCanceledOnTouchOutside(false);

        ivCarAnim = (ImageView) findViewById(R.id.iv_anim);

        AnimationDrawable drawable = (AnimationDrawable) ivCarAnim.getBackground();
        drawable.start();
    }

}
