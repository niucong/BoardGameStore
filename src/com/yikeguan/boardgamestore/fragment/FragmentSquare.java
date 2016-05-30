package com.yikeguan.boardgamestore.fragment;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yikeguan.boardgamestore.LoginActivity;
import com.yikeguan.boardgamestore.MainActivity;
import com.yikeguan.boardgamestore.R;
import com.yikeguan.boardgamestore.SunpicInfoActivity;
import com.yikeguan.boardgamestore.SunpicListActivity;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.LoginBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.response.SunPicFindListBean;
import com.yikeguan.boardgamestore.response.SunpicInfoBean;
import com.yikeguan.boardgamestore.resquest.FlowMemberParams;
import com.yikeguan.boardgamestore.resquest.recommend.RecommendFindListParams;
import com.yikeguan.boardgamestore.resquest.sunpic.SunPicFindListParams;
import com.yikeguan.boardgamestore.util.DateTimeUtil;
import com.yikeguan.boardgamestore.view.MixedTextView;

public class FragmentSquare extends FragmentBasic implements OnClickListener {
	private static final String TAG = "FragmentSquare";

	private LinearLayout ll_tui, ll_shai, ll_tuan;
	String myId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		((MainActivity) getActivity()).iv_title_right.setVisibility(View.GONE);
		((MainActivity) getActivity()).tv_title.setText("广场");

		View child = inflater.inflate(R.layout.fragment_square, null);
		ll_fragment.addView(child);

		ll_tui = (LinearLayout) child.findViewById(R.id.square_tui);
		ll_shai = (LinearLayout) child.findViewById(R.id.square_shai);
		ll_tuan = (LinearLayout) child.findViewById(R.id.square_tuan);

		child.findViewById(R.id.square_tui_more).setOnClickListener(this);
		child.findViewById(R.id.square_shai_more).setOnClickListener(this);
		child.findViewById(R.id.square_tuan_more).setOnClickListener(this);

		myId = App.app.preferences.getStringMessage("app", "MyId", "0");

		new RecommendListTask().execute();
		new SunpicListTask().execute();
		// new GroupListTask().execute();

		return v;
	}

	@Override
	public void onClick(View v) {
		Intent it = new Intent(getActivity(), SunpicListActivity.class);
		switch (v.getId()) {
		case R.id.square_tui_more:
			it.putExtra("type", 0);
			break;
		case R.id.square_shai_more:
			it.putExtra("type", 1);
			break;
		case R.id.square_tuan_more:
			it.putExtra("type", 2);
			break;
		default:
			break;
		}
		getActivity().startActivity(it);
	}

	private class RecommendListTask extends
			AsyncTask<String, Integer, ResultModel> {
		// SpinnerProgressDialog sDialog;

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new RecommendFindListParams(App.app, "", "0", "2")
						.getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				SunPicFindListBean bean = (SunPicFindListBean) result
						.getResult();
				if (bean != null) {
					List<SunpicInfoBean> suns = bean.getRecommends();
					for (SunpicInfoBean sun : suns) {
						addView(sun, 0);
					}
				}
			} else {

			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
		}
	}

	private class SunpicListTask extends
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
				mc = new SunPicFindListParams(App.app, "", "0", "2")
						.getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				SunPicFindListBean bean = (SunPicFindListBean) result
						.getResult();
				if (bean != null) {
					List<SunpicInfoBean> suns = bean.getSuns();
					for (SunpicInfoBean sun : suns) {
						addView(sun, 1);
					}
				}
			} else {

			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
		}
	}

	private class GroupListTask extends AsyncTask<String, Integer, ResultModel> {

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			// try {
			// mc = new SunPicFindListParams(App.app, "").getResult();
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			return mc;
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				SunPicFindListBean bean = (SunPicFindListBean) result
						.getResult();
				if (bean != null) {
					List<SunpicInfoBean> suns = bean.getSuns();
					for (SunpicInfoBean sun : suns) {
						addView(sun, 2);
					}
				}
			} else {

			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
		}
	}

	private void addView(final SunpicInfoBean sun, final int type) {
		View child = LayoutInflater.from(getActivity()).inflate(
				R.layout.item_square, null);
		((TextView) child.findViewById(R.id.item_square_title)).setText(sun
				.getObj_name());
		((MixedTextView) child.findViewById(R.id.item_square_context))
				.setFaceText(sun.getObj_desc());

		final LoginBean member = sun.getMember();
		((TextView) child.findViewById(R.id.item_square_name)).setText(member
				.getMember_name());
		App.app.showUrlHeader(
				((ImageView) child.findViewById(R.id.item_square_head)),
				member.getHead_url() + member.getHead_path());
		((TextView) child.findViewById(R.id.item_square_time))
				.setText(DateTimeUtil.cTimeFormat(sun.getCreate_time()));

		ImageView iv_attention = (ImageView) child
				.findViewById(R.id.item_square_attention);
		if ("true".equals(member.getIsFlow())
				|| myId.equals(member.getMember_id()))
			iv_attention.setVisibility(View.GONE);
		else
			iv_attention.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if ("0".equals(myId)) {
						getActivity().startActivity(
								new Intent(getActivity(), LoginActivity.class));
					} else {
						v.setVisibility(View.GONE);
						new Thread() {
							public void run() {
								try {
									new FlowMemberParams(App.app, member
											.getMember_id(), true).getResult();
								} catch (Exception e) {
									e.printStackTrace();
								}
								member.setIsFlow("true");
							};
						}.start();
					}
				}
			});

		child.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getActivity(), SunpicInfoActivity.class);
				it.putExtra("SunpicInfoBean", sun);
				it.putExtra("type", type);
				startActivity(it);
			}
		});
		if (type == 0) {
			ll_tui.addView(child);
		} else if (type == 1) {
			ll_shai.addView(child);
		} else if (type == 2) {
			// ll_g.addView(child);
		}

	}

}