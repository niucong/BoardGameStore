package com.yikeguan.boardgamestore;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yikeguan.boardgamestore.adapter.GridImageAdapter;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.DesignerBean;
import com.yikeguan.boardgamestore.response.DesignerListBean;
import com.yikeguan.boardgamestore.response.GameBean;
import com.yikeguan.boardgamestore.response.PublisherBean;
import com.yikeguan.boardgamestore.response.PublisherListBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.game.GameDesignerListParams;
import com.yikeguan.boardgamestore.resquest.game.GameHotDesignerListParams;
import com.yikeguan.boardgamestore.resquest.game.GameHotPublisherListParams;
import com.yikeguan.boardgamestore.resquest.game.GamePublisherListParams;
import com.yikeguan.boardgamestore.view.GridViewWithHeaderAndFooter;

public class PublishListActivity extends BasicActivity {
	private static final String TAG = "PublishListActivity";

	private ImageView iv_title_left;
	private TextView tv_title;
	private GridViewWithHeaderAndFooter gv;
	private GridImageAdapter gia;

	private ArrayList<Object> list;
	private int type, start;

	DesignerListBean dbean;
	PublisherListBean pbean;
	private GameBean gb;

	private View mFootView, mHeadView;
	private TextView mLoadMoreTextView;
	private ProgressBar mFootProgressBar;
	private LinearLayout ll_hot;

	private final String dMore = "查看更多";
	private final String dLoading = "加载中...";
	private final String dNoMore = "没有更多数据";
	private final String dNo = "没有数据";

	private final int limit = 40;

	private int column = 4;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publish_list);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		type = getIntent().getIntExtra("Type", 0);

		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		tv_title = (TextView) findViewById(R.id.tv_title);

		mFootView = LayoutInflater.from(this).inflate(
				R.layout.refresh_list_footer, null);
		mLoadMoreTextView = (TextView) mFootView
				.findViewById(R.id.refresh_list_footer_text);
		mFootProgressBar = (ProgressBar) mFootView
				.findViewById(R.id.refresh_list_footer_progressbar);
		mFootProgressBar.setVisibility(View.VISIBLE);
		mLoadMoreTextView.setText(dLoading);
		mFootView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mFootProgressBar.setVisibility(View.VISIBLE);
				mLoadMoreTextView.setText(dLoading);

				start = list.size();
				if (type == 1) {
					new GameDesignerListTask().execute();
				} else {
					new GamePublisherListTask().execute();
				}
			}
		});

		mHeadView = LayoutInflater.from(this).inflate(R.layout.publish_head,
				null);
		ll_hot = (LinearLayout) mHeadView.findViewById(R.id.publish_head_ll);

		gv = (GridViewWithHeaderAndFooter) findViewById(R.id.gridView1);
		list = new ArrayList<>();
		gia = new GridImageAdapter(this, list);
		gia.setType(type);
		gv.addHeaderView(mHeadView);
		gv.addFooterView(mFootView);
		gv.setAdapter(gia);

		// gv.setOnLoadMoreListener(new IOnLoadMoreListener() {
		//
		// @Override
		// public void OnLoadMore() {
		// start = list.size();
		// if (type == 1) {
		// new GameDesignerListTask().execute();
		// } else {
		// new GamePublisherListTask().execute();
		// }
		// }
		// });

		iv_title_left.setImageResource(R.drawable.icon_back);
		start = 0;
		if (type == 1) {
			dbean = (DesignerListBean) getIntent().getSerializableExtra(
					"DesignerListBean");
			tv_title.setText("更多设计师");
			ArrayList<Object> dlist = new ArrayList<>();
			dlist.addAll(dbean.getDesigners());
			addHotDesigner(dlist, type);

			new GameHotDesignerListTask().execute(dbean.getPage()
					.getTotalCount());
			new GameDesignerListTask().execute();
		} else if (type == 3) {
			gb = (GameBean) getIntent().getSerializableExtra("GameBean");
			tv_title.setText("图片");
			mHeadView.setVisibility(View.GONE);
			mFootView.setVisibility(View.GONE);
			list.addAll(gb.getDetailedImages());
			gia.notifyDataSetChanged();
		} else {
			pbean = (PublisherListBean) getIntent().getSerializableExtra(
					"PublisherListBean");
			tv_title.setText("更多出版社");
			ArrayList<Object> dlist = new ArrayList<>();
			dlist.addAll(pbean.getPublishers());
			addHotDesigner(dlist, type);

			new GameHotPublisherListTask().execute(pbean.getPage()
					.getTotalCount());
			new GamePublisherListTask().execute();
		}
		tv_title.getPaint().setFakeBoldText(true);

		iv_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * 
	 * @param objs
	 * @param type
	 *            0：出版社、1：设计师
	 */
	private void addHotDesigner(List<Object> objs, final int type) {
		if (objs == null)
			return;
		int size = objs.size() - 1;
		int dex = size / column + 1;

		int ns = (column - 1) - size % column;
		for (int j = 0; j < ns; j++) {
			objs.add(new Object());
		}
		for (int i = 0; i < dex; i++) {
			LinearLayout head_item = (LinearLayout) LayoutInflater.from(this)
					.inflate(R.layout.publish_head_item, null);
			for (int j = 0; j < column; j++) {
				addItem(objs, i, j, head_item);
			}
			ll_hot.addView(head_item);
		}
	}

	/**
	 * 
	 * @param objs
	 * @param i
	 * @param j
	 * @param head_item
	 */
	private void addItem(List<Object> objs, int i, int j, LinearLayout head_item) {
		View view = null;
		if (type == 1) {
			view = LayoutInflater.from(this).inflate(R.layout.item_grid_image,
					null);
		} else {
			view = LayoutInflater.from(this).inflate(R.layout.item_grid_game,
					null);
		}
		ImageView iv = (ImageView) view.findViewById(R.id.iv_icon);
		TextView tv = (TextView) view.findViewById(R.id.tv_name);

		final Object obj = objs.get(column * i + j);
		if (obj instanceof PublisherBean || obj instanceof DesignerBean) {
			if (type == 0) {
				PublisherBean pb = (PublisherBean) obj;
				if (pb != null) {
					App.app.showUrlPublish(iv, pb.getShortcut_image_path());
					tv.setVisibility(View.VISIBLE);
					tv.setText(pb.getPublisher_name());
				}
			} else if (type == 1) {
				DesignerBean db = (DesignerBean) obj;
				if (db != null) {
					App.app.showUrlHeader(iv, db.getShortcut_image_path());
					tv.setVisibility(View.VISIBLE);
					tv.setText(db.getDesigner_name());
				}
			}
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (type == 0) {
						PublisherBean pb = (PublisherBean) obj;
						Intent pi = new Intent(PublishListActivity.this,
								GameListActivity.class);
						pi.putExtra("PublisherBean", pb);
						startActivity(pi);
					} else if (type == 1) {
						DesignerBean db = (DesignerBean) obj;
						Intent di = new Intent(PublishListActivity.this,
								GameListActivity.class);
						di.putExtra("DesignerBean", db);
						startActivity(di);
					}
				}
			});
		} else {
			iv.setImageResource(R.drawable.nothing);
		}
		head_item.addView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1));
	}

	private class GameHotDesignerListTask extends
			AsyncTask<Integer, Integer, ResultModel> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(Integer... params) {
			ResultModel mc = null;
			try {
				mc = new GameHotDesignerListParams(App.app, "", "8", params[0]
						- 8 + "").getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				DesignerListBean bean = (DesignerListBean) result.getResult();
				if (bean != null) {
					ArrayList<Object> dlist = new ArrayList<>();
					dlist.addAll(bean.getDesigners());
					addHotDesigner(dlist, type);
				}
			} else {
				// TODO
			}
		}

		@Override
		protected void onCancelled() {
		}
	}

	private class GameDesignerListTask extends
			AsyncTask<String, Integer, ResultModel> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new GameDesignerListParams(App.app, "", start + "", limit
						+ "").getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				DesignerListBean bean = (DesignerListBean) result.getResult();
				if (bean != null) {
					List<DesignerBean> designers = bean.getDesigners();
					int size = designers.size();
					if (start == 0) {
						if (size == 0) {
							mFootProgressBar.setVisibility(View.GONE);
							mLoadMoreTextView.setText(dNo);
							mFootView.setClickable(false);
						} else {
							list.clear();
							list.addAll(designers);
							if (size != limit) {
								mFootProgressBar.setVisibility(View.GONE);
								mLoadMoreTextView.setText(dNoMore);
							} else {
								mFootProgressBar.setVisibility(View.GONE);
								mLoadMoreTextView.setText(dMore);
							}
						}
					} else {
						list.addAll(designers);
						if (size != limit) {
							mFootProgressBar.setVisibility(View.GONE);
							mLoadMoreTextView.setText(dNoMore);
						} else {
							mFootProgressBar.setVisibility(View.GONE);
							mLoadMoreTextView.setText(dMore);
						}
					}
					gia.notifyDataSetChanged();
				}
			} else {
				// TODO
			}
		}

		@Override
		protected void onCancelled() {
		}
	}

	private class GameHotPublisherListTask extends
			AsyncTask<Integer, Integer, ResultModel> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(Integer... params) {
			ResultModel mc = null;
			try {
				mc = new GameHotPublisherListParams(App.app, "", "8", params[0]
						- 8 + "").getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				PublisherListBean bean = (PublisherListBean) result.getResult();
				if (bean != null) {
					ArrayList<Object> dlist = new ArrayList<>();
					dlist.addAll(bean.getPublishers());
					addHotDesigner(dlist, type);
				}
			} else {
				// TODO
			}
		}

		@Override
		protected void onCancelled() {
		}
	}

	private class GamePublisherListTask extends
			AsyncTask<String, Integer, ResultModel> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new GamePublisherListParams(App.app, "", start + "", limit
						+ "").getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				PublisherListBean bean = (PublisherListBean) result.getResult();
				if (bean != null) {
					List<PublisherBean> publishers = bean.getPublishers();
					int size = publishers.size();
					if (start == 0) {
						if (size == 0) {
							mFootProgressBar.setVisibility(View.GONE);
							mLoadMoreTextView.setText(dNo);
							mFootView.setClickable(false);
						} else {
							list.clear();
							list.addAll(publishers);
							if (size != limit) {
								mFootProgressBar.setVisibility(View.GONE);
								mLoadMoreTextView.setText(dNoMore);
							} else {
								mFootProgressBar.setVisibility(View.GONE);
								mLoadMoreTextView.setText(dMore);
							}
						}
					} else {
						list.addAll(publishers);
						if (size != limit) {
							mFootProgressBar.setVisibility(View.GONE);
							mLoadMoreTextView.setText(dNoMore);
						} else {
							mFootProgressBar.setVisibility(View.GONE);
							mLoadMoreTextView.setText(dMore);
						}
					}
					gia.notifyDataSetChanged();
				}
			} else {
				// TODO
			}
		}

		@Override
		protected void onCancelled() {
		}
	}
}
