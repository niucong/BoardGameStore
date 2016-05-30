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

import com.yikeguan.boardgamestore.adapter.MemberListAdapter;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.FlowMembersListBean;
import com.yikeguan.boardgamestore.response.MemberBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.FlowMembersListParams;
import com.yikeguan.boardgamestore.view.RefreshListView;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnLoadMoreListener;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnRefreshListener;

public class MemberListActivity extends BasicActivity {
	private final String TAG = "MemberListActivity";

	private ImageView iv_title_left, iv_title_right;
	private TextView tv_title;
	private RefreshListView rlv;

	private ArrayList<MemberBean> list;
	private MemberListAdapter ga;

	private int start;

	private String type, mId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		type = getIntent().getStringExtra("Type");
		mId = getIntent().getStringExtra("Id");

		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		iv_title_right = (ImageView) findViewById(R.id.iv_title_right);
		tv_title = (TextView) findViewById(R.id.tv_title);

		rlv = (RefreshListView) findViewById(R.id.homepage_list);
		list = new ArrayList<>();
		ga = new MemberListAdapter(this, list);
		rlv.setAdapter(ga);
		start = 0;
		new MemberListTask().execute();

		iv_title_left.setImageResource(R.drawable.icon_back);
		if ("acction".equals(type)) {
			if (mId == null
					|| mId.equals("")
					|| mId.equals(App.app.preferences.getStringMessage("app",
							"MyId", "0"))) {
				tv_title.setText("我关注的人");
			} else {
				tv_title.setText("他关注的人");
			}
		} else if ("funs".equals(type)) {
			if (mId == null
					|| mId.equals("")
					|| mId.equals(App.app.preferences.getStringMessage("app",
							"MyId", "0"))) {
				tv_title.setText("我的粉丝");
			} else {
				tv_title.setText("他的粉丝");
			}
		} else {
			iv_title_right.setImageResource(R.drawable.icon_class);
			tv_title.setText("搜索结果");
		}

		iv_title_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
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
				new MemberListTask().execute();
			}
		});

		rlv.setOnLoadMoreListener(new IOnLoadMoreListener() {

			@Override
			public void OnLoadMore() {
				start = list.size();
				new MemberListTask().execute();
			}
		});
	}

	private class MemberListTask extends
			AsyncTask<String, Integer, ResultModel> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				if ("acction".equals(type)) {
					mc = new FlowMembersListParams(App.app, "" + start, "10",
							mId, 0).getResult();
				} else if ("funs".equals(type)) {
					mc = new FlowMembersListParams(App.app, "" + start, "10",
							mId, 1).getResult();
				} else {
					// TODO 0:某个人关注的人群、1:某个人被关注的人群、2：某个人访问的人群、3：某个人被访问的人群（最近谁来过）
					mc = new FlowMembersListParams(App.app, "" + start, "10",
							mId, 3).getResult();
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
				FlowMembersListBean bean = (FlowMembersListBean) result
						.getResult();
				if (bean != null) {
					List<MemberBean> gbs = bean.getMembers();
					if (gbs != null) {
						int size = gbs.size();
						if (start == 0) {
							if (size == 0) {
								rlv.onLoadMoreComplete(3);
							} else {
								list.clear();
								list.addAll(gbs);
								if (size != 10) {
									rlv.onLoadMoreComplete(2);
								} else {
									rlv.onLoadMoreComplete(0);
								}
							}
						} else {
							list.addAll(gbs);
							if (size != 10) {
								rlv.onLoadMoreComplete(2);
							} else {
								rlv.onLoadMoreComplete(0);
							}
						}
						ga.notifyDataSetChanged();
					} else {

					}
				} else {

				}
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
