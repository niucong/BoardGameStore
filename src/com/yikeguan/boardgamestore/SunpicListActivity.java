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

import com.yikeguan.boardgamestore.adapter.SunPicAdapter;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.response.SunPicFindListBean;
import com.yikeguan.boardgamestore.response.SunpicInfoBean;
import com.yikeguan.boardgamestore.resquest.group.GroupFindListParams;
import com.yikeguan.boardgamestore.resquest.recommend.RecommendFindListParams;
import com.yikeguan.boardgamestore.resquest.sunpic.SunPicFindListParams;
import com.yikeguan.boardgamestore.view.RefreshListView;
import com.yikeguan.boardgamestore.view.RefreshListView.ILoadMoreViewState;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnLoadMoreListener;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnRefreshListener;

public class SunpicListActivity extends BasicActivity {
	private final String TAG = "SunpicListActivity";

	private ImageView iv_title_left;
	private TextView tv_title;
	private RefreshListView rlv;

	private ArrayList<SunpicInfoBean> list;
	private SunPicAdapter ga;

	private int type;

	private int start;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		type = getIntent().getIntExtra("type", 0);

		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		tv_title = (TextView) findViewById(R.id.tv_title);

		rlv = (RefreshListView) findViewById(R.id.homepage_list);
		list = new ArrayList<SunpicInfoBean>();
		ga = new SunPicAdapter(this, list);
		ga.setType(type);
		rlv.setAdapter(ga);
		start = 0;
		rlv.updateLoadMoreViewState(ILoadMoreViewState.LMVS_LOADING);
		new GameListTask().execute();

		iv_title_left.setImageResource(R.drawable.icon_back);
		if (type == 0) {
			tv_title.setText("求推荐列表");
		} else if (type == 1) {
			tv_title.setText("晒图列表");
		} else if (type == 2) {
			tv_title.setText("求团列表");
		}

		iv_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		rlv.setOnRefreshListener(new IOnRefreshListener() {

			@Override
			public void OnRefresh() {
				start = 0;
				new GameListTask().execute();
			}
		});

		rlv.setOnLoadMoreListener(new IOnLoadMoreListener() {

			@Override
			public void OnLoadMore() {
				start = list.size();
				new GameListTask().execute();
			}
		});
	}

	private class GameListTask extends AsyncTask<String, Integer, ResultModel> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				if (type == 0) {
					mc = new RecommendFindListParams(App.app, "", "" + start,
							"10").getResult();
				} else if (type == 1) {
					mc = new SunPicFindListParams(App.app, "", "" + start, "10")
							.getResult();
				} else if (type == 2) {
					mc = new GroupFindListParams(App.app, "", "" + start, "10")
							.getResult();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			rlv.onRefreshComplete();
			if (result != null && result.getCode() == 1) {
				SunPicFindListBean bean = (SunPicFindListBean) result
						.getResult();
				List<SunpicInfoBean> suns = new ArrayList<SunpicInfoBean>();
				if (type == 0) {
					suns = bean.getRecommends();
				} else if (type == 1) {
					suns = bean.getSuns();
				} else if (type == 1) {
					suns = bean.getRecommends();
				}
				int size = suns.size();
				if (start == 0) {
					if (size == 0) {
						rlv.onLoadMoreComplete(3);
					} else {
						list.clear();
						list.addAll(suns);
						if (size != 10) {
							rlv.onLoadMoreComplete(2);
						} else {
							rlv.onLoadMoreComplete(0);
						}
					}
				} else {
					list.addAll(suns);
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
			// sDialog.cancelProgressDialog("");
		}
	}

}
