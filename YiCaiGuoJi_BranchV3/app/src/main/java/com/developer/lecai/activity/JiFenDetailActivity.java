package com.developer.lecai.activity;

import android.view.View;

import com.developer.lecai.R;

public class JiFenDetailActivity extends BaseActivity {

    @Override
    public View getLayout() {

        View view=View.inflate(JiFenDetailActivity.this,R.layout.activity_ji_fen_detail,null);
        return null;
    }

    @Override
    public void initView() {
        tvTitle.setText("积分明细");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
//        Map<String, String> params = new HashMap<>();
//        params.put(H.getSignature.account, "18518205592");
//        HttpRequest.post(H.getSignature.URL, params, new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e) {
//                Log.e("错误","call="+call.toString()+"Exception="+e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, String s) {
//
//                Log.e("成功","s="+s+"------------"+"call="+call.toString()+ "getSignature="+ MyUtil.getSignature("18518205592"));
//            }
//        });

        //Log.e("woca", "getSignature="+ MyUtil.getSignature("13600000000"));

    }
}
