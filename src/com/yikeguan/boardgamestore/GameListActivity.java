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

import com.yikeguan.boardgamestore.adapter.GameListAdapter;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.app.ShowToast;
import com.yikeguan.boardgamestore.response.DesignerBean;
import com.yikeguan.boardgamestore.response.FindGameListBean;
import com.yikeguan.boardgamestore.response.GameBean;
import com.yikeguan.boardgamestore.response.PublisherBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.game.FindGameListParams;
import com.yikeguan.boardgamestore.resquest.game.SearchGameListParams;
import com.yikeguan.boardgamestore.resquest.game.TypeGameListParams;
import com.yikeguan.boardgamestore.view.RefreshListView;
import com.yikeguan.boardgamestore.view.RefreshListView.ILoadMoreViewState;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnLoadMoreListener;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnRefreshListener;

public class GameListActivity extends BasicActivity {
	private final String TAG = "GameListActivity";

	private ImageView iv_title_left;
	private TextView tv_title;
	private RefreshListView rlv;

	private ArrayList<GameBean> list;
	private GameListAdapter ga;

	private DesignerBean db;
	private PublisherBean pb;
	String name, game_type;

	private int start;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);

		game_type = getIntent().getStringExtra("game_type");
		if (game_type == null || "".equals(game_type)) {
			db = (DesignerBean) getIntent()
					.getSerializableExtra("DesignerBean");
			if (db == null) {
				pb = (PublisherBean) getIntent().getSerializableExtra(
						"PublisherBean");
				if (pb == null)
					name = getIntent().getStringExtra("GameName");
			}
		}

		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		tv_title = (TextView) findViewById(R.id.tv_title);

		rlv = (RefreshListView) findViewById(R.id.homepage_list);
		list = new ArrayList<>();
		ga = new GameListAdapter(this, list);
		rlv.setAdapter(ga);
		start = 0;
		rlv.updateLoadMoreViewState(ILoadMoreViewState.LMVS_LOADING);
		new GameListTask().execute();

		iv_title_left.setImageResource(R.drawable.icon_back);
		tv_title.setText("查询结果");

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
				if (game_type != null && !"".equals(game_type)) {
					mc = new TypeGameListParams(App.app, "" + start, "10",
							game_type).getResult();
				} else if (db != null) {
					// mc = new SearchGameDesignerListParams(App.app, ""
					// + db.getBgg_designer_id(), "" + start, "10")
					// .getResult();
					mc = new SearchGameListParams(App.app, "" + start, "10",
							"", "", db.getDesigner_name()).getResult();
				} else if (pb != null) {
					// mc = new SearchGamePublisherListParams(App.app, ""
					// + pb.getBgg_publisher_id(), "" + start, "10")
					// .getResult();
					mc = new SearchGameListParams(App.app, "" + start, "10",
							"", pb.getPublisher_name(), "").getResult();
				} else if (name != null && !"".equals(name)) {
					mc = new SearchGameListParams(App.app, "" + start, "10",
							name, "", "").getResult();
				} else {
					mc = new FindGameListParams(App.app, "" + start, "10", null)
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
				FindGameListBean bean = (FindGameListBean) result.getResult();
				List<GameBean> gbs = bean.getGames();
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
					list.addAll(bean.getGames());
					if (size != 10) {
						rlv.onLoadMoreComplete(2);
					} else {
						rlv.onLoadMoreComplete(0);
					}
				}
				ga.notifyDataSetChanged();
			} else {
				ShowToast.getToast().show("搜索失败");
				rlv.onLoadMoreComplete(3);
			}
		}

		@Override
		protected void onCancelled() {
		}
	}

}
