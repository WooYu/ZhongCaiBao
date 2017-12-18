package com.developer.lecai.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.activity.NoticeDetailActivity;
import com.developer.lecai.adapter.ListViewNoticeAdapter;
import com.developer.lecai.bean.GongGaoBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by liuwei on 2017/7/6.
 */

public class NoticeNoticeFragment extends BaseFragment {

    private View view;
    private ListView lv_notice_noticelv;
    private ListViewNoticeAdapter listViewNoticeAdapter;
    private List<GongGaoBean> list=new ArrayList();

    @Override
    protected View getLayout() {

        view = View.inflate(getContext(), R.layout.fragment_noticenotice,null);
        return view;
    }

    @Override
    protected void initView() {
       /* list.add(1);
        list.add(1);
        list.add(1);*/
        lv_notice_noticelv = (ListView) view.findViewById(R.id.lv_notice_noticelv);
/*        listViewNoticeAdapter = new ListViewNoticeAdapter(getContext(),list);
        lv_notice_noticelv.setAdapter(listViewNoticeAdapter);*/
    }

    @Override
    protected void initLinstener() {
        lv_notice_noticelv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), NoticeDetailActivity.class);
                intent.putExtra("title",list.get(position).getTips());
                intent.putExtra("content",list.get(position).getContent());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {


                MsgController.getInstance().getNotice(1, 1, new HttpCallback(getActivity()) {
                    @Override
                    public void onSuccess(Call call, String s) {
                        Log.e("公告公告", s);
                        String biz_content = JsonUtil.getStringValue(s, "biz_content");
                        String state = JsonUtil.getFieldValue(s, "state");
                        if ("success".equals(state)) {
                            list = (List<GongGaoBean>) JsonUtil.parseJsonToList(biz_content,new TypeToken<List<GongGaoBean>>(){}.getType());
                           if(listViewNoticeAdapter==null) {

                               listViewNoticeAdapter = new ListViewNoticeAdapter(getContext(), list);
                               lv_notice_noticelv.setAdapter(listViewNoticeAdapter);
                           }else{

                               listViewNoticeAdapter.notifyDataSetInvalidated();
                           }
                        } else {
                            Toast.makeText(getContext(), biz_content, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void clickEvent(View view) {

    }
}
