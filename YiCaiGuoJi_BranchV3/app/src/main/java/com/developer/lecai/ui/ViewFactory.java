package com.developer.lecai.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.developer.lecai.R;
import com.developer.lecai.utils.GlideUtil;


/**
 * ImageView创建工厂
 */
public class ViewFactory {

    /**
     * 获取ImageView视图的同时加载显示url
     *
     * @return
     */
    public static ImageView getImageView(Context context, String url) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
                R.layout.view_banner, null);
        GlideUtil.setImage(context, imageView, url);
        return imageView;
    }

}
