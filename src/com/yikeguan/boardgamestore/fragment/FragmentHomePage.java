package com.yikeguan.boardgamestore.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yikeguan.boardgamestore.MainActivity;
import com.yikeguan.boardgamestore.R;
import com.yikeguan.boardgamestore.adapter.HomePageAdapter;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.response.TrendBean;
import com.yikeguan.boardgamestore.response.TrendListBean;
import com.yikeguan.boardgamestore.resquest.TrendListParams;
import com.yikeguan.boardgamestore.view.MyPagerGalleryView;
import com.yikeguan.boardgamestore.view.MyPagerGalleryView.MyOnItemClickListener;
import com.yikeguan.boardgamestore.view.RefreshListView;
import com.yikeguan.boardgamestore.view.RefreshListView.ILoadMoreViewState;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnLoadMoreListener;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnRefreshListener;

public class FragmentHomePage extends FragmentBasic {

	// 广告控件
	private MyPagerGalleryView gallery;
	// 圆点容器
	private LinearLayout ovalLayout;
	// 图片上面的文字
	private TextView adgallerytxt;
	/** 图片id的数组,本地测试用 */
	private int[] imageId = new int[] { R.drawable.ad, R.drawable.ad };
	/** 图片网络路径数组 */
	private String[] urlImageList = { "", "", "" };
	private String[] txtViewpager = { "", "", "" };

	RefreshListView rlv;
	private HomePageAdapter adapter, adapter2, adapter3;
	private ArrayList<TrendBean> list, list2, list3;
	private int start, start2, start3;
	private static int dex = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		((MainActivity) getActivity()).iv_title_right.setVisibility(View.GONE);
		((MainActivity) getActivity()).tv_title.setText("玩亦有道");

		final View child = inflater.inflate(R.layout.fragment_home_page, null);

		View viewHeader = LayoutInflater.from(getActivity()).inflate(
				R.layout.homepage_header, null);
		gallery = (MyPagerGalleryView) viewHeader.findViewById(R.id.adgallery);
		ovalLayout = (LinearLayout) viewHeader.findViewById(R.id.ovalLayout1);// 获取圆点容器
		adgallerytxt = (TextView) viewHeader.findViewById(R.id.adgallerytxt);
		adgallerytxt.setVisibility(View.GONE);
		gallery.start(getActivity(), null, imageId, 3000, ovalLayout,
				R.drawable.dot_focused, R.drawable.dot_normal, adgallerytxt,
				null);
		// 添加点击事件的监听
		gallery.setMyOnItemClickListener(new MyOnItemClickListener() {
			public void onItemClick(int curIndex) {
				// TODO
			}
		});

		final View viewHeaderTab = LayoutInflater.from(getActivity()).inflate(
				R.layout.homepage_tab, null);
		viewHeaderTab.findViewById(R.id.homepage_tab1).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						setTab1(child, viewHeaderTab);
					}
				});
		viewHeaderTab.findViewById(R.id.homepage_tab2).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						setTab2(child, viewHeaderTab);
					}
				});
		viewHeaderTab.findViewById(R.id.homepage_tab3).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						setTab3(child, viewHeaderTab);
					}
				});

		final View viewTab = child.findViewById(R.id.homepage_tab);
		viewTab.setVisibility(View.GONE);
		child.findViewById(R.id.homepage_tab1).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						setTab1(child, viewHeaderTab);
					}
				});
		child.findViewById(R.id.homepage_tab2).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						setTab2(child, viewHeaderTab);
					}
				});
		child.findViewById(R.id.homepage_tab3).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						setTab3(child, viewHeaderTab);
					}
				});

		rlv = (RefreshListView) child.findViewById(R.id.homepage_list);
		rlv.addHeaderView(viewHeader);
		rlv.addHeaderView(viewHeaderTab);

		list = new ArrayList<>();
		adapter = new HomePageAdapter(getActivity(), list);
		list2 = new ArrayList<>();
		adapter2 = new HomePageAdapter(getActivity(), list2);
		list3 = new ArrayList<>();
		adapter3 = new HomePageAdapter(getActivity(), list3);

		if (dex == 0) {
			setTab1(child, viewHeaderTab);
		} else if (dex == 1) {
			setTab2(child, viewHeaderTab);
		} else {
			setTab3(child, viewHeaderTab);
		}

		rlv.setOnRefreshListener(new IOnRefreshListener() {

			@Override
			public void OnRefresh() {
				if (dex == 0) {
					start = 0;
					new TrendListTask().execute();
				} else if (dex == 0) {
					start2 = 0;
					new Trend2ListTask().execute();
				} else {
					start3 = 0;
					new Trend3ListTask().execute();
				}
			}
		});

		rlv.setOnLoadMoreListener(new IOnLoadMoreListener() {

			@Override
			public void OnLoadMore() {
				if (dex == 0) {
					start = list.size();
					new TrendListTask().execute();
				} else if (dex == 0) {
					start2 = list2.size();
					new Trend2ListTask().execute();
				} else {
					start3 = list3.size();
					new Trend3ListTask().execute();
				}
			}
		});

		rlv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem > 1) {
					viewTab.setVisibility(View.VISIBLE);
					viewHeaderTab.setVisibility(View.INVISIBLE);
				} else {
					viewTab.setVisibility(View.GONE);
					viewHeaderTab.setVisibility(View.VISIBLE);
				}
			}
		});

		ll_fragment.addView(child);
		return v;
	}

	private void setTab1(View child, View viewHeaderTab) {
		child.findViewById(R.id.homepage_iv_tab1).setVisibility(View.VISIBLE);
		child.findViewById(R.id.homepage_iv_tab2).setVisibility(View.GONE);
		child.findViewById(R.id.homepage_iv_tab3).setVisibility(View.GONE);
		viewHeaderTab.findViewById(R.id.homepage_iv_tab1).setVisibility(
				View.VISIBLE);
		viewHeaderTab.findViewById(R.id.homepage_iv_tab2).setVisibility(
				View.GONE);
		viewHeaderTab.findViewById(R.id.homepage_iv_tab3).setVisibility(
				View.GONE);

		dex = 0;
		rlv.setAdapter(adapter);
		if (list.size() == 0) {
			rlv.updateLoadMoreViewState(ILoadMoreViewState.LMVS_LOADING);
			new TrendListTask().execute();
		}
	}

	private void setTab2(View child, View viewHeaderTab) {
		child.findViewById(R.id.homepage_iv_tab2).setVisibility(View.VISIBLE);
		child.findViewById(R.id.homepage_iv_tab1).setVisibility(View.GONE);
		child.findViewById(R.id.homepage_iv_tab3).setVisibility(View.GONE);
		viewHeaderTab.findViewById(R.id.homepage_iv_tab2).setVisibility(
				View.VISIBLE);
		viewHeaderTab.findViewById(R.id.homepage_iv_tab1).setVisibility(
				View.GONE);
		viewHeaderTab.findViewById(R.id.homepage_iv_tab3).setVisibility(
				View.GONE);
		dex = 1;
		rlv.setAdapter(adapter2);
		if (list2.size() == 0) {
			rlv.updateLoadMoreViewState(ILoadMoreViewState.LMVS_LOADING);
			new Trend2ListTask().execute();
		}
	}

	private void setTab3(View child, View viewHeaderTab) {
		child.findViewById(R.id.homepage_iv_tab3).setVisibility(View.VISIBLE);
		child.findViewById(R.id.homepage_iv_tab2).setVisibility(View.GONE);
		child.findViewById(R.id.homepage_iv_tab1).setVisibility(View.GONE);
		viewHeaderTab.findViewById(R.id.homepage_iv_tab3).setVisibility(
				View.VISIBLE);
		viewHeaderTab.findViewById(R.id.homepage_iv_tab2).setVisibility(
				View.GONE);
		viewHeaderTab.findViewById(R.id.homepage_iv_tab1).setVisibility(
				View.GONE);
		dex = 2;
		rlv.setAdapter(adapter3);
		if (list3.size() == 0) {
			rlv.updateLoadMoreViewState(ILoadMoreViewState.LMVS_LOADING);
			new Trend3ListTask().execute();
		}
	}

	@Override
	public void onResume() {
		gallery.startTimer();
		super.onResume();
	}

	@Override
	public void onPause() {
		gallery.stopTimer();
		super.onPause();
	}

	private class TrendListTask extends AsyncTask<String, Integer, ResultModel> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new TrendListParams(App.app, "" + start, "10", "")
						.getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			rlv.onRefreshComplete();
			if (result != null && result.getCode() == 1) {
				TrendListBean bean = (TrendListBean) result.getResult();
				if (bean != null) {
					List<TrendBean> gbs = bean.getTrends();
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
				}
				adapter.notifyDataSetChanged();
			} else {
				// TODO
			}
		}

		@Override
		protected void onCancelled() {
		}
	}

	private class Trend2ListTask extends
			AsyncTask<String, Integer, ResultModel> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new TrendListParams(App.app, "" + start, "10", "SUN")
						.getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				TrendListBean bean = (TrendListBean) result.getResult();
				if (bean != null) {
					List<TrendBean> gbs = bean.getTrends();
					int size = gbs.size();
					if (start2 == 0) {
						if (size == 0) {
							rlv.onLoadMoreComplete(3);
						} else {
							list2.clear();
							list2.addAll(gbs);
							if (size != 10) {
								rlv.onLoadMoreComplete(2);
							} else {
								rlv.onLoadMoreComplete(0);
							}
						}
					} else {
						list2.addAll(gbs);
						if (size != 10) {
							rlv.onLoadMoreComplete(2);
						} else {
							rlv.onLoadMoreComplete(0);
						}
					}
				}
				adapter2.notifyDataSetChanged();
			} else {
				// TODO
			}
		}

		@Override
		protected void onCancelled() {
		}
	}

	private class Trend3ListTask extends
			AsyncTask<String, Integer, ResultModel> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new TrendListParams(App.app, "" + start, "10", "RECOMMEND")
						.getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				TrendListBean bean = (TrendListBean) result.getResult();
				if (bean != null) {
					List<TrendBean> gbs = bean.getTrends();
					int size = gbs.size();
					if (start3 == 0) {
						if (size == 0) {
							rlv.onLoadMoreComplete(3);
						} else {
							list3.clear();
							list3.addAll(gbs);
							if (size != 10) {
								rlv.onLoadMoreComplete(2);
							} else {
								rlv.onLoadMoreComplete(0);
							}
						}
					} else {
						list3.addAll(gbs);
						if (size != 10) {
							rlv.onLoadMoreComplete(2);
						} else {
							rlv.onLoadMoreComplete(0);
						}
					}
				}
				adapter3.notifyDataSetChanged();
			} else {
				// TODO
			}
		}

		@Override
		protected void onCancelled() {
		}
	}
}
