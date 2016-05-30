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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yikeguan.boardgamestore.adapter.GridImageAdapter;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.FlowMemberGamesListBean;
import com.yikeguan.boardgamestore.response.GamesBean;
import com.yikeguan.boardgamestore.response.LoginBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.response.StatisInfoBean;
import com.yikeguan.boardgamestore.resquest.FlowMemberParams;
import com.yikeguan.boardgamestore.resquest.GetMemberInfoParams;
import com.yikeguan.boardgamestore.resquest.game.FlowMemberGamesListParams;
import com.yikeguan.boardgamestore.util.L;
import com.yikeguan.boardgamestore.view.GridViewWithHeaderAndFooter;

public class MemberDetailActivity extends BasicActivity implements
		OnClickListener {
	private final String TAG = "MemberDetailActivity";

	private ImageView iv_title_left;
	private TextView tv_title, tv_acction, tv_funs, tv_context, tv_num;
	private GridViewWithHeaderAndFooter gv;
	private GridImageAdapter gia;
	private Button iv_attention;
	View view;

	private ArrayList<Object> list;
	private int start;

	private View mFootView;
	private TextView mLoadMoreTextView;
	private ProgressBar mFootProgressBar;

	private final String dMore = "查看更多";
	private final String dLoading = "加载中...";
	private final String dNoMore = "";
	private final String dNo = "";
	private final int limit = 40;

	LoginBean member;
	String myId, mId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_detail);
		member = (LoginBean) getIntent().getSerializableExtra("LoginBean");

		mId = member.getMember_id();
		myId = App.app.preferences.getStringMessage("app", "MyId", "0");
		L.d(TAG, "onCreate mId=" + mId + ",myId=" + myId);

		gv = (GridViewWithHeaderAndFooter) findViewById(R.id.gridView);

		view = LayoutInflater.from(this).inflate(R.layout.member_detail_header,
				null);
		gv.addHeaderView(view);

		if (Build.VERSION.SDK_INT < 19) {
			view.findViewById(R.id.member_title).setPadding(0, 0, 0, 0);
		}

		iv_title_left = (ImageView) view.findViewById(R.id.member_back);
		iv_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

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
				new GameListTask().execute();
			}
		});
		gv.addFooterView(mFootView);

		list = new ArrayList<>();
		gia = new GridImageAdapter(this, list);
		gia.setType(2);
		gv.setAdapter(gia);

		TextView tv_name = (TextView) view.findViewById(R.id.mine_name);
		tv_name.setText(member.getMember_name() + "\t");
		if ("1".equals(member.getMember_sex())) {
			tv_name.setCompoundDrawablesWithIntrinsicBounds(null, null,
					getResources().getDrawable(R.drawable.man), null);
		} else {
			tv_name.setCompoundDrawablesWithIntrinsicBounds(null, null,
					getResources().getDrawable(R.drawable.woman), null);
		}

		TextView tv_mood = (TextView) view.findViewById(R.id.mine_sign);
		tv_mood.setText(member.getMember_mood());

		ImageView mImageView = (ImageView) view.findViewById(R.id.mine_head);
		App.app.showUrlHeader(mImageView,
				member.getHead_url() + member.getHead_path());
		// mImageView.setOnClickListener(this);

		tv_acction = (TextView) view.findViewById(R.id.mine_acction);
		tv_funs = (TextView) view.findViewById(R.id.mine_funs);
		tv_context = (TextView) view.findViewById(R.id.mine_context);

		if (myId.equals(mId)) {
			view.findViewById(R.id.mine_opertion).setVisibility(View.GONE);
		} else {
			view.findViewById(R.id.mine_message).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							if ("0".equals(App.app.preferences
									.getStringMessage("app", "MyId", "0"))) {
								startActivity(new Intent(
										MemberDetailActivity.this,
										LoginActivity.class));
							} else {
								Intent i = new Intent(
										MemberDetailActivity.this,
										ChatActivity.class);
								i.putExtra("LoginBean", member);
								startActivity(i);
							}
						}
					});
			iv_attention = (Button) view.findViewById(R.id.mine_attention);
			if ("true".equals(member.getIsFlow()))
				iv_attention.setText("已关注");
			iv_attention.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if ("0".equals(App.app.preferences.getStringMessage("app",
							"MyId", "0"))) {
						startActivity(new Intent(MemberDetailActivity.this,
								LoginActivity.class));
					} else {
						iv_attention.setEnabled(false);
						boolean isFlow = false;
						if ("true".equals(member.getIsFlow())) {
							isFlow = false;
							iv_attention.setText("关注");
						} else {
							isFlow = true;
							iv_attention.setText("已关注");
						}
						final boolean flow = isFlow;
						new Thread() {
							public void run() {
								try {
									new FlowMemberParams(App.app, member
											.getMember_id(), flow).getResult();
								} catch (Exception e) {
									e.printStackTrace();
								}
								member.setIsFlow("" + flow);
								runOnUiThread(new Runnable() {
									public void run() {
										iv_attention.setEnabled(true);
									}
								});
							};
						}.start();
					}
				}
			});
		}
		tv_num = (TextView) view.findViewById(R.id.mine_games_num);

		new MemberInfoTask().execute();
		start = 0;
		// TODO 某个人关注的游戏 游戏详细（关注、取消关注）
		new GameListTask().execute();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (myId.equals(App.app.preferences
				.getStringMessage("app", "MyId", "0")))
			view.findViewById(R.id.mine_opertion).setVisibility(View.GONE);
	}

	private class MemberInfoTask extends
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
				mc = new GetMemberInfoParams(App.app, "" + mId).getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				LoginBean bean = (LoginBean) result.getResult();
				if (bean != null) {
					StatisInfoBean statisInfo = bean.getStatisInfo();
					tv_acction.setText(statisInfo.getFlow_member_num()
							+ "\n\n关注");
					tv_funs.setText(statisInfo.getMember_flow_num() + "\n\n粉丝");
					tv_context.setText(statisInfo.getCreate_recommend_num()
							+ statisInfo.getCreate_sun_num() + "\n\n文章");

					tv_acction.setOnClickListener(MemberDetailActivity.this);
					tv_funs.setOnClickListener(MemberDetailActivity.this);
					tv_context.setOnClickListener(MemberDetailActivity.this);
				}
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
		}
	}

	private class GameListTask extends AsyncTask<String, Integer, ResultModel> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new FlowMemberGamesListParams(App.app, "" + start, ""
						+ limit, "" + mId).getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				FlowMemberGamesListBean bean = (FlowMemberGamesListBean) result
						.getResult();
				List<GamesBean> gbs = bean.getGames();
				tv_num.setText("游戏墙（" + bean.getPage().getTotalCount() + "）");
				if (gbs != null) {
					int size = gbs.size();
					if (start == 0) {
						if (size == 0) {
							mFootProgressBar.setVisibility(View.GONE);
							mLoadMoreTextView.setText(dNo);
							mFootView.setClickable(false);
						} else {
							list.clear();
							list.addAll(gbs);
							if (size != limit) {
								mFootProgressBar.setVisibility(View.GONE);
								mLoadMoreTextView.setText(dNoMore);
							} else {
								mFootProgressBar.setVisibility(View.GONE);
								mLoadMoreTextView.setText(dMore);
							}
						}
					} else {
						list.addAll(gbs);
						if (size != limit) {
							mFootProgressBar.setVisibility(View.GONE);
							mLoadMoreTextView.setText(dNoMore);
						} else {
							mFootProgressBar.setVisibility(View.GONE);
							mLoadMoreTextView.setText(dMore);
						}
					}
					gia.notifyDataSetChanged();
				} else {

				}
			} else {
				// TODO

			}
		}

		@Override
		protected void onCancelled() {
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mine_head:
			startActivity(new Intent(this, MemberInfoActivity.class));
			break;
		case R.id.mine_acction:
			Intent ai = new Intent(this, MemberListActivity.class);
			ai.putExtra("Type", "acction");
			ai.putExtra("Id", mId);
			startActivity(ai);
			break;
		case R.id.mine_funs:
			Intent fi = new Intent(this, MemberListActivity.class);
			fi.putExtra("Type", "funs");
			fi.putExtra("Id", mId);
			startActivity(fi);
			break;
		case R.id.mine_context:
			Intent ci = new Intent(this, TrendListActivity.class);
			ci.putExtra("Id", mId);
			startActivity(ci);
			break;

		default:
			break;
		}
	}

}
