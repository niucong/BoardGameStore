package com.yikeguan.boardgamestore;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yikeguan.boardgamestore.adapter.TrendListAdapter;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.response.TrendBean;
import com.yikeguan.boardgamestore.response.TrendListBean;
import com.yikeguan.boardgamestore.resquest.NoticeListParams;
import com.yikeguan.boardgamestore.resquest.TrendMemberListParams;
import com.yikeguan.boardgamestore.view.RefreshListView;
import com.yikeguan.boardgamestore.view.RefreshListView.ILoadMoreViewState;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnLoadMoreListener;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnRefreshListener;

public class TrendListActivity extends BasicActivity implements OnClickListener {
	private final String TAG = "TrendListActivity";

	private ImageView iv_title_left;
	private TextView tv_title, tv_repet, tv_browse;
	private RefreshListView rlv;

	private ArrayList<TrendBean> rList, bList;
	private TrendListAdapter ga;

	private int start;

	// trend_member_list TREND_COMMENT TREND_VISIT
	private int index;
	private int type;
	private String mId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trend_list);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		type = getIntent().getIntExtra("type", 0);
		mId = getIntent().getStringExtra("Id");
		rList = new ArrayList<>();

		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		tv_title = (TextView) findViewById(R.id.tv_title);

		tv_repet = (TextView) findViewById(R.id.trend_list_repet);
		tv_browse = (TextView) findViewById(R.id.trend_list_browse);
		tv_repet.setOnClickListener(this);
		tv_browse.setOnClickListener(this);
		rlv = (RefreshListView) findViewById(R.id.homepage_list);

		iv_title_left.setImageResource(R.drawable.icon_back);
		if (type == 1) {
			tv_title.setText("我的消息");
			tv_repet.setText("个人");
			tv_browse.setText("系统");
			ga = new TrendListAdapter(this, rList, 2);
		} else {
			if (mId == null
					|| mId.equals("")
					|| mId.equals(App.app.preferences.getStringMessage("app",
							"MyId", "0"))) {
				tv_title.setText("我的动态");
			} else {
				tv_title.setText("他的动态");
			}
			ga = new TrendListAdapter(this, rList, index);
		}

		iv_title_left.setOnClickListener(this);
		rlv.setAdapter(ga);
		rlv.updateLoadMoreViewState(ILoadMoreViewState.LMVS_LOADING);
		new MemberListTask().execute();

		rlv.setOnRefreshListener(new IOnRefreshListener() {

			@Override
			public void OnRefresh() {
				start = 0;
				if (index == 0) {
					new MemberListTask().execute();
				} else if (index == 1) {
					new TrendListTask().execute();
				}
			}
		});

		rlv.setOnLoadMoreListener(new IOnLoadMoreListener() {

			@Override
			public void OnLoadMore() {
				if (index == 0) {
					start = rList.size();
					new MemberListTask().execute();
				} else if (index == 1) {
					start = bList.size();
					new TrendListTask().execute();
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			finish();
			break;
		case R.id.trend_list_repet:
			if (type == 1)
				rlv.setVisibility(View.VISIBLE);
			index = 0;
			tv_repet.setBackgroundResource(R.drawable.tab_left_yellow);
			tv_repet.setTextColor(getMyColor(R.color.white));
			tv_browse.setBackgroundResource(R.drawable.tab_right_null);
			tv_browse.setTextColor(getMyColor(R.color.yellow));
			ga = new TrendListAdapter(this, rList, index);
			rlv.setAdapter(ga);
			break;
		case R.id.trend_list_browse:
			index = 1;
			tv_repet.setBackgroundResource(R.drawable.tab_left_null);
			tv_repet.setTextColor(getMyColor(R.color.yellow));
			tv_browse.setBackgroundResource(R.drawable.tab_right_yellow);
			tv_browse.setTextColor(getMyColor(R.color.white));
			if (type == 1) {
				rlv.setVisibility(View.GONE);
			} else {
				if (bList == null)
					bList = new ArrayList<>();
				ga = new TrendListAdapter(this, bList, index);
				rlv.setAdapter(ga);
				if (bList.size() == 0) {
					start = 0;
					rlv.updateLoadMoreViewState(ILoadMoreViewState.LMVS_LOADING);
					new TrendListTask().execute();
				}
			}
			break;

		default:
			break;
		}
	}

	private int getMyColor(int id) {
		return getResources().getColor(id);
	}

	private class MemberListTask extends
			AsyncTask<String, Integer, ResultModel> {

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				if (type == 1) {
					mc = new NoticeListParams(App.app, "" + start, "10")
							.getResult();
				} else {
					mc = new TrendMemberListParams(App.app, mId, "" + start,
							"10", "TREND_COMMENT").getResult();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(ResultModel result) {
			rlv.onRefreshComplete();
			if (result != null && result.getCode() == 1) {
				TrendListBean bean = (TrendListBean) result.getResult();
				List<TrendBean> gbs = bean.getTrends();
				int size = gbs.size();
				if (start == 0) {
					if (size == 0) {
						rlv.onLoadMoreComplete(3);
					} else {
						rList.clear();
						rList.addAll(gbs);
						if (size != 10) {
							rlv.onLoadMoreComplete(2);
						} else {
							rlv.onLoadMoreComplete(0);
						}
					}
				} else {
					rList.addAll(gbs);
					if (size != 10) {
						rlv.onLoadMoreComplete(2);
					} else {
						rlv.onLoadMoreComplete(0);
					}
				}
				ga.notifyDataSetChanged();
			} else {
				// TODO
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
		}
	}

	private class TrendListTask extends AsyncTask<String, Integer, ResultModel> {

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new TrendMemberListParams(App.app, mId, "" + start, "10",
						"TREND_VISIT").getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				TrendListBean bean = (TrendListBean) result.getResult();
				List<TrendBean> gbs = bean.getTrends();
				int size = gbs.size();
				if (start == 0) {
					if (size == 0) {
						rlv.onLoadMoreComplete(3);
					} else {
						bList.clear();
						bList.addAll(gbs);
						if (size != 10) {
							rlv.onLoadMoreComplete(2);
						} else {
							rlv.onLoadMoreComplete(0);
						}
					}
				} else {
					bList.addAll(gbs);
					if (size != 10) {
						rlv.onLoadMoreComplete(2);
					} else {
						rlv.onLoadMoreComplete(0);
					}
				}
				ga.notifyDataSetChanged();
			} else {
				// TODO
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
		}
	}

}
