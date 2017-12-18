package com.developer.lecai.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.developer.lecai.R;
import com.developer.lecai.adapter.TrendMapAdapter;
import com.developer.lecai.control.UserController;
import com.developer.lecai.entiey.TrendMapEntity;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.utils.MyUtil;
import com.developer.lecai.utils.SharedPreferencesUtils;
import com.developer.lecai.utils.XyMyContent;
import com.developer.lecai.view.PullToRefreshView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 走势图页面
 *
 * @author Administrator
 *
 */
public class TrendMap extends Activity implements OnClickListener, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

	private static final int ZOUSHITU = 0;
	private static final int PULLREFRESH = 1;
	private ListView trend_map_Listview;
	private ImageView trendMap_back;
	private Request<JSONObject> request;
	private RequestQueue trendMapQueue;
	private TrendMapAdapter tmAdapter;
	private List<TrendMapEntity> tmList;
	private String account;
	private String imei;
	private String wanfaType;
	private String signature;
	/** 自定义刷新 */
	private PullToRefreshView mPullToRefreshView;
	/** 等待动画 */
	ProgressDialog p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trend_map);

		initView();
		setListener();
		// 如果SDK的版本在4.4之上，那么应用沉浸式状态栏
		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setNavigationBarTintEnabled(true);
			tintManager.setStatusBarTintColor(getResources().getColor(R.color.statelan_bg));
			SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
//            rlFramePrimaryMain.setPadding(0, config.getPixelInsetTop(false), 0, config.getPixelInsetBottom());
		}*/


	}
	// 设置沉浸状态栏
	public void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	private void initView() {

		trendMapQueue = NoHttp.newRequestQueue();
		// 用户账号
		account = UserController.getInstance().getLoginBean().getLoginname();
		// 玩法类型
		wanfaType = SharedPreferencesUtils.getValue(getApplicationContext(), "wanfaType");

		signature  = MyUtil.getSignature(account);
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());

		trendMapRequest();
		// 上拉刷新 下拉加载
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);

		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		mPullToRefreshView.setLastUpdated(new Date().toLocaleString());// 获取时间
		trendMap_back = (ImageView) findViewById(R.id.trendMap_back);
		trend_map_Listview = (ListView) findViewById(R.id.trend_map_Listview);

	}

	private void trendMapRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.ZOUSHITU_URL, RequestMethod.POST);


		if (wanfaType.equals("bj")) {
			wanfaType = "bjkl8";
		} else if (wanfaType.equals("cakeno")) {
			wanfaType = "cakeno";
		}
		request.add("type", wanfaType);
		request.add("page", "1");
		request.add("imei", imei);
		request.add("account", account);
		request.add("signature", signature);
		trendMapQueue.add(ZOUSHITU, request, responseListener);
	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
				case ZOUSHITU:

					System.out.println("走势图数据" + response);
					try {
						String state = response.get().getString("state");
						String biz_content = response.get().getString("biz_content");

						if (state.equals("success")) {

							tmList = JSON.parseArray(biz_content, TrendMapEntity.class);

							tmAdapter = new TrendMapAdapter(getApplicationContext(), tmList);

							trend_map_Listview.setAdapter(tmAdapter);

						} else if (state.equals("error")) {
							Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case PULLREFRESH:

					JSONArray jsonarray;
					try {
						jsonarray = response.get().getJSONArray("biz_content");
						TrendMapEntity a;
						for (int i = 0; i < jsonarray.length(); i++) {
							a = new TrendMapEntity();
							a = JSON.parseObject(jsonarray.getString(i), TrendMapEntity.class);
							tmList.add(a);
						}
						System.out.println("tmList"+tmList);
						System.out.println("jsonarray"+jsonarray);

						if (jsonarray.equals("获取失败")) {

							Toast.makeText(getApplicationContext(), "没有更多数据", Toast.LENGTH_SHORT).show();

						} else {

							tmAdapter.notifyDataSetChanged();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				default:
					break;
			}

		}

		@Override
		public void onStart(int what) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFinish(int what) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFailed(int what, String url, Object tag, Exception exception, int responseCode,
							 long networkMillis) {
			// TODO Auto-generated method stub

		}
	};

	private void setListener() {

		trendMap_back.setOnClickListener(this);

	}

	private int i = 2;

	// 上拉加载
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();

				// if (i <= Integer.parseInt(total)) {

				PullTorequest(i);
				i++;
				System.out.println("i的值" + i);
				// } else {
				// Toast.makeText(getActivity(), "没有更多数据",
				// Toast.LENGTH_LONG).show();
				// }

				// gridViewData.add(R.drawable.pic1);
				// myAdapter.setGridViewData(gridViewData);
				// Toast.makeText(this,"加载更多数据!", 0).show();
			}

		}, 3000);

	}

	protected void PullTorequest(int i) {

		request = NoHttp.createJsonObjectRequest(XyMyContent.ZOUSHITU_URL, RequestMethod.POST);

		if (wanfaType.equals("北京")) {
			wanfaType = "10";
		} else if (wanfaType.equals("加拿大")) {
			wanfaType = "11";
		}
		request.add("type", wanfaType);
		request.add("page", i);
		request.add("imei", imei);
		request.add("account", account);

		trendMapQueue.add(PULLREFRESH, request, responseListener);
	}

	/** 下拉刷新 */
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPullToRefreshView.onHeaderRefreshComplete("更新于:" + Calendar.getInstance().getTime().toLocaleString());
				mPullToRefreshView.onHeaderRefreshComplete();
				trendMapRequest();
				// Toast.makeText(this, "数据刷新完成!", 0).show();
			}

		}, 3000);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.trendMap_back:
				finish();
				break;

			default:
				break;
		}

	}

}
