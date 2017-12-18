package com.developer.lecai.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.developer.lecai.R;
import com.developer.lecai.app.XyApplication;

/**
 * Created by ZhangCe on 2016/8/18.
 */
public class ImageUtil {
    /**
     * 为image设置一张网络或本地的图片
     *
     * @param url
     * @param view
     * @param defaultImg 加载时的默认图片
     * @param errorImg   加载错误时的图片
     */
    public static void setImage(String url, ImageView view, int defaultImg, int errorImg) {
        Glide.with(XyApplication.appContext)
                .load(url)
                .placeholder(defaultImg)
                .error(errorImg)
                .crossFade()
                .into(view);
    }
    /**
     * 为image设置一张网络或本地的图片
     *
     * @param url
     * @param view
     */
    public static void setImage(int url, ImageView view) {
        Glide.with(XyApplication.appContext)
                .load(url)
                .crossFade()
                .into(view);
    }

    /**
     * 设置一张图片，自带加载中的图片与加载错误图片
     *
     * @param url
     * @param view
     */
    public static void setImage(String url, ImageView view) {
        Glide.with(XyApplication.appContext)
                .load(url)
                .placeholder(Color.WHITE)
                .error(Color.WHITE)
                .crossFade()
                .into(view);
    }
    /**
     * 设置一张图片，自带加载中的图片与加载错误图片
     *
     * @param url
     * @param view
     */
    public static void setImageView(String url, ImageView view) {
        Glide.with(XyApplication.appContext)
                .load(url)
                .placeholder(R.drawable.kaijiang)
                .error(R.drawable.kaijiang)
                .crossFade()
                .into(view);
    }
    /**
     * 设置一张图片，自带加载中的图片与加载错误图片
     *
     * @param url
     * @param view
     */
    public static void setImage(Bitmap url, ImageView view) {
        Glide.with(XyApplication.appContext)
                .load(url)
                .placeholder(Color.WHITE)
                .error(Color.WHITE)
                .crossFade()
                .into(view);
    }



    /**
     * 圆角控件兼容
     * @param url
     * @param view
     */
    public static void setRoundRectImage(String url, ImageView view, int drawableRes, int errorImg, final int radius) {
        Glide.with(XyApplication.appContext)
                .load(url)
                .asBitmap()
                .placeholder(drawableRes)
                .error(drawableRes)
                .into(new BitmapImageViewTarget(view) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(XyApplication.appContext.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(radius);
                        view.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

}