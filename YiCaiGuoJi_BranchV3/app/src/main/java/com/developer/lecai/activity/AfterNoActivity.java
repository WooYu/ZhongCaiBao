package com.developer.lecai.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.adapter.AfterNoAdapter;
import com.developer.lecai.bean.AfterNoBean;
import com.developer.lecai.bean.AfterNoItemBean;
import com.developer.lecai.view.TToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 追号
 */
public class AfterNoActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_periods_sum)
    TextView tvPeriodsSum;
    @BindView(R.id.tv_winningundo)
    TextView tvWinningundo;
    @BindView(R.id.rv_afterno)
    RecyclerView rvAfterNo;

    private int isStart = 1;//isstart	中奖后是否停止 0否 1是
    private long mStartIssue;
    private double mBaseAmount;
    private List<AfterNoItemBean> mAfterNOItemList;
    private AfterNoAdapter mAfterNoAdapter;
    private int mSelectedIssue = 0;//选中的期数
    private double mTotalFee = 0;//总金额

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterno);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String issue = intent.getStringExtra("issue");
        mBaseAmount = intent.getDoubleExtra("baseamount", 0);

        if (null == issue || 0 == mBaseAmount) {
            return;
        }
        mStartIssue = Long.parseLong(issue);
        creationData();
    }


    @OnClick(R.id.tv_winningundo)
    public void onTvWinningundoClicked() {
        Drawable drawable;
        if (isStart == 1) {
            isStart = 0;
            drawable = getResources().getDrawable(R.drawable.afterno_revoke);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        } else {
            isStart = 1;
            drawable = getResources().getDrawable(R.drawable.afterno_revoke_select);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        tvWinningundo.setCompoundDrawables(drawable, null, null, null);
    }

    @OnClick(R.id.tv_submit)
    public void onTvSubmitClicked() {
//        isstart	中奖后是否停止 0否 1是	否 (追号必填写 否则报错)
//        totalfee	总金额
        if (mSelectedIssue == 0) {
            TToast.show(this, R.string.afterno_selectnone, TToast.LENGTH_SHORT);
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mAfterNOItemList.size(); i++) {
            AfterNoItemBean afterNoItemBean = mAfterNOItemList.get(i);
            if (afterNoItemBean.isStatus()) {
                stringBuilder.append(afterNoItemBean.getIssue())
                        .append(",")
                        .append(afterNoItemBean.getMultiple())
                        .append("|");
            }
        }

        AfterNoBean afterNoBean = new AfterNoBean();
        afterNoBean.setBettNum(String.valueOf(mSelectedIssue));
        afterNoBean.setBMuch("1");
        afterNoBean.setStartNum(String.valueOf(mStartIssue));
        afterNoBean.setTotalFee(String.valueOf(mTotalFee));
        afterNoBean.setProfits("1");
        afterNoBean.setJsonContent(stringBuilder.toString());
        afterNoBean.setBettType("1");

        getIntent().putExtra("afternobean", afterNoBean);
        getIntent().putExtra("isstart", isStart);
        setResult(Activity.RESULT_OK, getIntent());
        finish();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    //创建数据
    private void creationData() {
        mAfterNOItemList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            AfterNoItemBean afterNoItemBean = new AfterNoItemBean();
            afterNoItemBean.setIssue(String.valueOf(mStartIssue + i));
            afterNoItemBean.setMultiple(1);
            afterNoItemBean.setStatus(false);
            afterNoItemBean.setSum(mBaseAmount);
            mAfterNOItemList.add(afterNoItemBean);
        }

        mAfterNoAdapter = new AfterNoAdapter(this, mAfterNOItemList);
        mAfterNoAdapter.setListener(new AfterNoAdapter.EventListener() {
            @Override
            public void clickSelect(int positon) {
                AfterNoItemBean afterNoItemBean = mAfterNOItemList.get(positon);
                afterNoItemBean.setStatus(!afterNoItemBean.isStatus());
                updateList(positon,afterNoItemBean);
            }

            @Override
            public void clickAddMultiple(int position) {
                AfterNoItemBean afterNoItemBean = mAfterNOItemList.get(position);
                long multiple = afterNoItemBean.getMultiple();
                afterNoItemBean.setMultiple(++multiple);
                afterNoItemBean.setSum(afterNoItemBean.getMultiple() * mBaseAmount);
                updateList(position, afterNoItemBean);
            }

            @Override
            public void clickSubMultiple(int position) {
                AfterNoItemBean afterNoItemBean = mAfterNOItemList.get(position);
                long multiple = afterNoItemBean.getMultiple();
                afterNoItemBean.setMultiple(multiple == 1 ? 1 : --multiple);
                afterNoItemBean.setSum(afterNoItemBean.getMultiple() * mBaseAmount);
                updateList(position, afterNoItemBean);
            }

            @Override
            public void inputMultiple(int position, int multiple) {
                AfterNoItemBean afterNoItemBean = mAfterNOItemList.get(position);
                afterNoItemBean.setMultiple(multiple);
                afterNoItemBean.setSum(afterNoItemBean.getMultiple() * mBaseAmount);
            }
        });

        rvAfterNo.setLayoutManager(new LinearLayoutManager(this));
        rvAfterNo.setHasFixedSize(true);
        rvAfterNo.setAdapter(mAfterNoAdapter);
    }

    //计算期数和金额
    private void calculateIssueAndMoney() {
        mSelectedIssue = 0;
        mTotalFee = 0;
        for (int i = 0; i < mAfterNOItemList.size(); i++) {
            AfterNoItemBean afterNoItemBean = mAfterNOItemList.get(i);
            if (afterNoItemBean.isStatus()) {
                ++mSelectedIssue;
                mTotalFee += afterNoItemBean.getSum();
            }
        }
        tvPeriodsSum.setText(String.format(getString(R.string.afterno_statistics),
                mSelectedIssue, mTotalFee));
    }

    //更新列表
    private void updateList(int position, AfterNoItemBean afterNoItemBean) {
        mAfterNoAdapter.notifyItemChanged(position);
        calculateIssueAndMoney();
    }

}
