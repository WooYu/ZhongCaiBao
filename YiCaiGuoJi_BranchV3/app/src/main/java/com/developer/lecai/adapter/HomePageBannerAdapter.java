package com.developer.lecai.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.developer.lecai.bean.ImgListBean;
import com.developer.lecai.ui.ViewFactory;

import java.util.List;


/**
 * Created by dell-3020 on 2017/6/12.
 */

public class HomePageBannerAdapter extends PagerAdapter {
    private List<ImgListBean> list;
    private ImageCycleViewListener mImageCycleViewListener;
    private Context mContext;

    public HomePageBannerAdapter(Context context, List<ImgListBean> list){
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return getItemsCount()>1? Integer.MAX_VALUE:getItemsCount();
    }

    public int getItemsCount(){
        return list != null ?list.size() :0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        ImgListBean bean = list.get(position % list.size());
        ImageView v = ViewFactory.getImageView(mContext, bean.getImg());
        if (mImageCycleViewListener != null) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImageCycleViewListener.onImageClick(list.get(position%list.size()), position%list.size(), v);
                }
            });
        }
        container.addView(v);
        return v;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setmImageCycleViewListener(ImageCycleViewListener listener){
        this.mImageCycleViewListener = listener;
    }

    /**
     * 轮播控件的监听事件
     *
     * @author minking
     */
    public  interface ImageCycleViewListener {

        /**
         * 单击图片事件
         *
         * @param imageView
         */
        public void onImageClick(ImgListBean info, int postion, View imageView);
    }

}
