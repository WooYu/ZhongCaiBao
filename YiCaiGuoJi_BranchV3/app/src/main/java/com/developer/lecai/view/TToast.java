package com.developer.lecai.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;


public class TToast {
	public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
	public static final int LENGTH_LONG = Toast.LENGTH_LONG;
	public static final int TIME_3 = 3000; // 3秒
	public static final int TIME_2 = 2000; // 2秒
	private static TToast tToast;
	private Toast toast;
	private TextView text;
	private static Toast sToast;
	
	private TToast(Context context) {
		toast = new Toast(context);
		View layout = LayoutInflater.from(context).inflate(R.layout.toast_view,null);
        text = (TextView) layout.findViewById(R.id.tv_toast_content);
        toast.setView(layout);  
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  
	}
	
	public static TToast getInstance(Context context) {
		if(tToast == null) {
			synchronized (TToast.class) {
				if(tToast == null) {
					tToast = new TToast(context);
				}
			}
		}
		return tToast;
	}
	
	public static void show(Context context, String content, int time) {
		View layout = LayoutInflater.from(context).inflate(R.layout.toast_view,null);  
        TextView text = (TextView) layout.findViewById(R.id.tv_toast_content);  
        text.setText(content);
        if(sToast == null) {
        	sToast = new Toast(context);  
        } 
        Resources res = context.getResources();
        int tBottom = res.getDimensionPixelSize(R.dimen.toast_mg_bottom);
        sToast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, tBottom);  
        sToast.setDuration(time);
        sToast.setView(layout);  
        sToast.show();  
	}

	public static void show(Context context, int rid, int time) {
		String content = "";
		if(context != null) {
			content = context.getResources().getString(rid);
		}
		show(context, content, time);
	}
	
	public void show(String content, int time) {
		toast.setDuration(time);  
		text.setText(content);
        toast.show();
	}
}
