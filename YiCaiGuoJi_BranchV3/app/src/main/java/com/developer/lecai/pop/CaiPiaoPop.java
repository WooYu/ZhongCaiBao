package com.developer.lecai.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.CaiPiaoWanFaBean;

import java.util.List;

/**
 * Created by liuwei on 2017/8/12.
 */

public class CaiPiaoPop extends PopupWindow {

    Context mcontext;
    private View mView;
    private final GridView gv_title_list;
    private List<CaiPiaoWanFaBean> list;
    private OnClickPopItemListener onClickPopItemListener;
    private final GridAdapter gridAdapter;

    public interface OnClickPopItemListener {
        void OnClickPopItem(CaiPiaoWanFaBean content);
    }

    public void setOnClickPopItemListener(OnClickPopItemListener onClickPopItemListener) {
        this.onClickPopItemListener = onClickPopItemListener;
    }

    public CaiPiaoPop(Activity context) {

        super(context);
        mcontext = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.ffc_pop_item, null);

        gv_title_list = (GridView) mView.findViewById(R.id.gv_title_list);
        gridAdapter = new GridAdapter(mcontext);
        gv_title_list.setAdapter(gridAdapter);
        gv_title_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (onClickPopItemListener != null) {
                    onClickPopItemListener.OnClickPopItem(list.get(i));
                }
            }
        });
//        gv_title_list.s
        //设置PopupWindow的View
        this.setContentView(mView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.PopAnimation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x60000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        setOutsideTouchable(true);


    }
    public void setData(List<CaiPiaoWanFaBean> caiPiaoBean) {
        this.list = caiPiaoBean;

        if (gridAdapter!=null)
        gridAdapter.notifyDataSetInvalidated();
    }
    private class GridAdapter extends BaseAdapter {

        private Context context;


        public GridAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(mcontext, R.layout.ffc_gridview_item, null);
            }
            TextView tv_grid_title = (TextView) view.findViewById(R.id.tv_grid_title);
            tv_grid_title.setText(list.get(i).getPname());
           /* tv_grid_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(onClickPopItemListener!=null){
                        onClickPopItemListener.OnClickPopItem(list.get(i));
                    }
                }
            });*/
            return view;
        }
    }


}
