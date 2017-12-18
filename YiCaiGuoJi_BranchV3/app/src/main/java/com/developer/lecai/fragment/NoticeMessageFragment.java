package com.developer.lecai.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.activity.MessageDetailActivity;
import com.developer.lecai.adapter.ListViewMessageAdapter;
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

public class NoticeMessageFragment extends BaseFragment {

    private View view;
    private ListView lv_notice_messagelv;
    private List<GongGaoBean> list=new ArrayList();
    private ListViewMessageAdapter listViewNoticeAdapter;

    @Override
    protected View getLayout() {
        view = View.inflate(getContext(), R.layout.fragment_noticemessage,null);
        return view;
    }

    @Override
    protected void initView() {
        /*list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);*/
        lv_notice_messagelv = (ListView) view.findViewById(R.id.lv_notice_messagelv);
/*        listViewNoticeAdapter = new ListViewMessageAdapter(getContext(),list);
        lv_notice_messagelv.setAdapter(listViewNoticeAdapter);*/
    }

    @Override
    protected void initLinstener() {
         lv_notice_messagelv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), MessageDetailActivity.class);

                intent.putExtra("content",list.get(position).getContent());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

        MsgController.getInstance().getNotice(0,1, new HttpCallback(getActivity()) {
            @Override
            public void onSuccess(Call call, String s) {
                Log.e("公告信息", s);
                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                if ("success".equals(state)) {
                    list = (List<GongGaoBean>) JsonUtil.parseJsonToList(biz_content,new TypeToken<List<GongGaoBean>>(){}.getType());
                    if(listViewNoticeAdapter==null) {
                        listViewNoticeAdapter = new ListViewMessageAdapter(getContext(),list);
                        lv_notice_messagelv.setAdapter(listViewNoticeAdapter);
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
