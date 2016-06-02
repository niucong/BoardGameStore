package com.yikeguan.boardgamestore.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yikeguan.boardgamestore.MainActivity;
import com.yikeguan.boardgamestore.MemberInfoActivity;
import com.yikeguan.boardgamestore.MemberListActivity;
import com.yikeguan.boardgamestore.R;
import com.yikeguan.boardgamestore.TrendListActivity;
import com.yikeguan.boardgamestore.adapter.GridImageAdapter;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.FlowMemberGamesListBean;
import com.yikeguan.boardgamestore.response.GamesBean;
import com.yikeguan.boardgamestore.response.LoginBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.response.StatisInfoBean;
import com.yikeguan.boardgamestore.resquest.GetMemberInfoParams;
import com.yikeguan.boardgamestore.resquest.game.FlowMemberGamesListParams;
import com.yikeguan.boardgamestore.util.L;
import com.yikeguan.boardgamestore.view.GridViewWithHeaderAndFooter;

public class FragmentMine extends FragmentBasic implements OnClickListener {
	private final String TAG = "FragmentMine";

	private ImageView iv_title_left;
	private TextView tv_title, tv_acction, tv_funs, tv_context, tv_num;
	private GridViewWithHeaderAndFooter gv;
	private GridImageAdapter gia;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);

		View child = inflater.inflate(R.layout.member_detail, null);
		ll_fragment.addView(child);

		gv = (GridViewWithHeaderAndFooter) child.findViewById(R.id.gridView);

		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.member_detail_header, null);
		gv.addHeaderView(view);

		iv_title_left = (ImageView) view.findViewById(R.id.member_back);
		iv_title_left.setImageResource(R.drawable.ic_menu);
		iv_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).showHideMenu();
			}
		});

		tv_num = (TextView) view.findViewById(R.id.mine_games_num);

		mFootView = LayoutInflater.from(getActivity()).inflate(
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
		gia = new GridImageAdapter(getActivity(), list);
		gia.setType(4);
		gv.setAdapter(gia);

		// tv_title.setText("达人ID");
		TextView tv_name = (TextView) view.findViewById(R.id.mine_name);
		// tv_name.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		tv_name.setText(App.app.preferences.getStringMessage("app", "MyName",
				"") + "\t");
		String sex = App.app.preferences.getStringMessage("app", "MySex", "");
		if ("1".equals(sex)) {
			tv_name.setCompoundDrawablesWithIntrinsicBounds(null, null,
					getResources().getDrawable(R.drawable.man), null);
		} else {
			tv_name.setCompoundDrawablesWithIntrinsicBounds(null, null,
					getResources().getDrawable(R.drawable.woman), null);
		}
		TextView tv_mood = (TextView) view.findViewById(R.id.mine_sign);
		tv_mood.setText(App.app.preferences.getStringMessage("app", "MyMood",
				""));

		ImageView mImageView = (ImageView) view.findViewById(R.id.mine_head);
		// loadImageByVolley(App.app.preferences.getStringMessage("app",
		// "Head",
		// ""));
		L.d(TAG,
				"Head="
						+ App.app.preferences.getStringMessage("app", "Head",
								""));
		App.app.showUrlHeader(mImageView,
				App.app.preferences.getStringMessage("app", "Head", ""));
		mImageView.setOnClickListener(this);

		tv_acction = (TextView) view.findViewById(R.id.mine_acction);
		tv_funs = (TextView) view.findViewById(R.id.mine_funs);
		tv_context = (TextView) view.findViewById(R.id.mine_context);
		tv_acction.setOnClickListener(this);
		tv_funs.setOnClickListener(this);
		tv_context.setOnClickListener(this);
		view.findViewById(R.id.mine_opertion).setVisibility(View.GONE);
		new MemberInfoTask().execute();
		start = 0;
		// TODO 某个人关注的游戏 游戏详细（关注、取消关注）
		new GameListTask().execute();
		return v;
	}

	private class MemberInfoTask extends
			AsyncTask<String, Integer, ResultModel> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new GetMemberInfoParams(App.app, "").getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

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
				}
			}
		}

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
						+ limit, "").getResult();
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
				if (bean.getPage() == null) {
					tv_num.setText("游戏墙（0）");
					mFootProgressBar.setVisibility(View.GONE);
					mLoadMoreTextView.setText(dNoMore);
					return;
				}
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
			getActivity().startActivity(
					new Intent(getActivity(), MemberInfoActivity.class));
			break;
		case R.id.mine_acction:
			Intent ai = new Intent(getActivity(), MemberListActivity.class);
			ai.putExtra("Type", "acction");
			getActivity().startActivity(ai);
			break;
		case R.id.mine_funs:
			Intent fi = new Intent(getActivity(), MemberListActivity.class);
			fi.putExtra("Type", "funs");
			getActivity().startActivity(fi);
			break;
		case R.id.mine_context:
			Intent ci = new Intent(getActivity(), TrendListActivity.class);
			getActivity().startActivity(ci);
			break;

		default:
			break;
		}
	}

	// @Override
	// public void onStop() {
	// super.onStop();
	// requestQueue.cancelAll(null);
	// }
	//
	// private void init(View v) {
	// mImageView = (ImageView) v.findViewById(R.id.imageView);
	// mNetworkImageView = (NetworkImageView) v
	// .findViewById(R.id.networkImageView);
	// getJSONByVolley();
	// loadImageByVolley();
	// showImageByNetworkImageView();
	// }
	//
	// /**
	// * ����Volley��ȡJSON����
	// */
	// private void getJSONByVolley() {
	// requestQueue = Volley.newRequestQueue(getActivity());
	// String JSONDataUrl =
	// "http://pipes.yahooapis.com/pipes/pipe.run?_id=giWz8Vc33BG6rQEQo_NLYQ&_render=json";
	// final ProgressDialog progressDialog = ProgressDialog.show(
	// getActivity(), "This is title", "...Loading...");
	//
	// JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
	// Request.Method.GET, JSONDataUrl, null,
	// new Response.Listener<JSONObject>() {
	// @Override
	// public void onResponse(JSONObject response) {
	// System.out.println("response=" + response);
	// if (progressDialog.isShowing()
	// && progressDialog != null) {
	// progressDialog.dismiss();
	// }
	// }
	// }, new Response.ErrorListener() {
	// @Override
	// public void onErrorResponse(VolleyError arg0) {
	// System.out.println("sorry,Error");
	// }
	// });
	// requestQueue.add(jsonObjectRequest);
	// }

	// /**
	// * ����Volley�첽����ͼƬ
	// *
	// * ע�ⷽ������: getImageListener(ImageView view, int defaultImageResId, int
	// * errorImageResId) ��һ������:��ʾͼƬ��ImageView �ڶ�������:Ĭ����ʾ��ͼƬ��Դ
	// * ����������:���ش���ʱ��ʾ��ͼƬ��Դ
	// */
	// private void loadImageByVolley(String imageUrl) {
	// RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
	// final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
	// 20);
	// ImageCache imageCache = new ImageCache() {
	// @Override
	// public void putBitmap(String key, Bitmap value) {
	// lruCache.put(key, value);
	// }
	//
	// @Override
	// public Bitmap getBitmap(String key) {
	// return lruCache.get(key);
	// }
	// };
	// ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
	// ImageListener listener = ImageLoader.getImageListener(mImageView,
	// R.drawable.mine_head, R.drawable.mine_head);
	// imageLoader.get(imageUrl, listener);
	// }

	// /**
	// * ����NetworkImageView��ʾ����ͼƬ
	// */
	// private void showImageByNetworkImageView() {
	// String imageUrl = "http://avatar.csdn.net/6/6/D/1_lfdfhl.jpg";
	// RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
	// final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
	// 20);
	// ImageCache imageCache = new ImageCache() {
	// @Override
	// public void putBitmap(String key, Bitmap value) {
	// lruCache.put(key, value);
	// }
	//
	// @Override
	// public Bitmap getBitmap(String key) {
	// return lruCache.get(key);
	// }
	// };
	// ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
	// mNetworkImageView.setTag("url");
	// mNetworkImageView.setImageUrl(imageUrl, imageLoader);
	// }
}