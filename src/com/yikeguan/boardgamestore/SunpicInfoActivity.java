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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yikeguan.boardgamestore.adapter.CommentListAdapter;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.app.ShowToast;
import com.yikeguan.boardgamestore.dialog.SpinnerProgressDialog;
import com.yikeguan.boardgamestore.response.CommentBean;
import com.yikeguan.boardgamestore.response.LoginBean;
import com.yikeguan.boardgamestore.response.ResourceBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.response.SunPicCommentListBean;
import com.yikeguan.boardgamestore.response.SunpicInfoBean;
import com.yikeguan.boardgamestore.resquest.FlowMemberParams;
import com.yikeguan.boardgamestore.resquest.recommend.RecommendCommentListParams;
import com.yikeguan.boardgamestore.resquest.recommend.RecommendCommentParams;
import com.yikeguan.boardgamestore.resquest.recommend.RecommendInfoParams;
import com.yikeguan.boardgamestore.resquest.sunpic.SunPicCommentListParams;
import com.yikeguan.boardgamestore.resquest.sunpic.SunPicCommentParams;
import com.yikeguan.boardgamestore.resquest.sunpic.SunPicInfoParams;
import com.yikeguan.boardgamestore.util.DateTimeUtil;
import com.yikeguan.boardgamestore.util.FaceUtil;
import com.yikeguan.boardgamestore.view.FacesView;
import com.yikeguan.boardgamestore.view.FacesView.OnFaceChosenListner;
import com.yikeguan.boardgamestore.view.MixedTextView;
import com.yikeguan.boardgamestore.view.RefreshListView;
import com.yikeguan.boardgamestore.view.RefreshListView.ILoadMoreViewState;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnLoadMoreListener;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnRefreshListener;

public class SunpicInfoActivity extends BasicActivity {
	private final String TAG = "SunpicListActivity";

	private ImageView iv_title_left, iv_attention;
	private TextView tv_title, tv_num;
	private RefreshListView rlv;
	private LinearLayout ll_photos;
	private EditText et;

	private ArrayList<CommentBean> list;
	private CommentListAdapter ga;

	private int type;
	private SunpicInfoBean bean;
	String myId, mId;

	private int start;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		type = getIntent().getIntExtra("type", 0);
		bean = (SunpicInfoBean) getIntent().getSerializableExtra(
				"SunpicInfoBean");
		myId = App.app.preferences.getStringMessage("app", "MyId", "0");

		if (Build.VERSION.SDK_INT < 19) {
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		}
		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		tv_title = (TextView) findViewById(R.id.tv_title);

		rlv = (RefreshListView) findViewById(R.id.homepage_list);
		findViewById(R.id.input).setVisibility(View.VISIBLE);
		findViewById(R.id.input).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("0".equals(App.app.preferences.getStringMessage("app",
						"MyId", "0")))
					startActivity(new Intent(SunpicInfoActivity.this,
							LoginActivity.class));
			}
		});
		et = (EditText) findViewById(R.id.input_text);
		findViewById(R.id.input_send).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("0".equals(App.app.preferences.getStringMessage("app",
						"MyId", "0"))) {
					startActivity(new Intent(SunpicInfoActivity.this,
							LoginActivity.class));
				} else {
					String cStr = et.getText().toString();
					if (!"".equals(cStr.trim())) {
						new CommentTask().execute(cStr);
					}
				}
			}
		});

		initFace();
		et.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("0".equals(App.app.preferences.getStringMessage("app",
						"MyId", "0")))
					startActivity(new Intent(SunpicInfoActivity.this,
							LoginActivity.class));
				else
					showKeyBoard(v);
			}
		});
		findViewById(R.id.input_face).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("0".equals(App.app.preferences.getStringMessage("app",
						"MyId", "0")))
					startActivity(new Intent(SunpicInfoActivity.this,
							LoginActivity.class));
				else
					showFace();
			}
		});

		View child = LayoutInflater.from(this).inflate(R.layout.square_info,
				null);
		rlv.addHeaderView(child);

		((TextView) child.findViewById(R.id.square_info_title)).setText(bean
				.getObj_name());
		((MixedTextView) child.findViewById(R.id.square_info_context))
				.setFaceText(bean.getObj_desc());
		ll_photos = (LinearLayout) child.findViewById(R.id.square_info_photos);
		tv_num = (TextView) child.findViewById(R.id.square_info_num);

		final LoginBean member = bean.getMember();
		((TextView) child.findViewById(R.id.square_info_name)).setText(member
				.getMember_name());
		ImageView iv_head = (ImageView) child
				.findViewById(R.id.square_info_head);
		App.app.showUrlHeader(iv_head,
				member.getHead_url() + member.getHead_path());
		iv_head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(SunpicInfoActivity.this,
						MemberDetailActivity.class);
				i.putExtra("LoginBean", member);
				startActivity(i);
			}
		});
		((TextView) child.findViewById(R.id.square_info_time))
				.setText(DateTimeUtil.cTimeFormat(bean.getCreate_time()));

		iv_attention = (ImageView) child
				.findViewById(R.id.square_info_attention);
		mId = member.getMember_id();
		if ("true".equals(member.getIsFlow()) || myId.equals(mId)) {
			iv_attention.setVisibility(View.GONE);
		} else {
			iv_attention.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if ("0".equals(App.app.preferences.getStringMessage("app",
							"MyId", "0"))) {
						startActivity(new Intent(SunpicInfoActivity.this,
								LoginActivity.class));
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
		}
		list = new ArrayList<CommentBean>();
		ga = new CommentListAdapter(this, list);
		rlv.setAdapter(ga);
		start = 0;
		rlv.updateLoadMoreViewState(ILoadMoreViewState.LMVS_LOADING);
		new CommentListTask().execute();
		new SunpicInfoTask().execute();

		iv_title_left.setImageResource(R.drawable.icon_back);
		if (type == 0) {
			tv_title.setText("求推荐详情");
		} else if (type == 1) {
			tv_title.setText("晒图详情");
		} else if (type == 2) {
			tv_title.setText("求团详情");
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
				new CommentListTask().execute();
			}
		});

		rlv.setOnLoadMoreListener(new IOnLoadMoreListener() {

			@Override
			public void OnLoadMore() {
				start = list.size();
				new CommentListTask().execute();
			}
		});
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (App.app.preferences.getStringMessage("app", "MyId", "0")
				.equals(mId))
			iv_attention.setVisibility(View.GONE);
	}

	private void initFace() {
		final FacesView faceView = (FacesView) findViewById(R.id.input_facesview);
		faceView.setOnFaceChosenListner(new OnFaceChosenListner() {

			@Override
			public void onChosen(String text, int resId) {
				FacesView
						.doEditChange(SunpicInfoActivity.this, et, text, resId);
			}
		});
		if (faceView.getChildCount() == 0) {
			faceView.setFaces();
		}

		final FacesView faceView2 = (FacesView) findViewById(R.id.input_facesview2);
		faceView2.setRes(FaceUtil.FACE_RES_LIDS, FaceUtil.FACE_LTEXTS);
		faceView2.setOnFaceChosenListner(new OnFaceChosenListner() {

			@Override
			public void onChosen(String text, int resId) {
				FacesView
						.doEditChange(SunpicInfoActivity.this, et, text, resId);
			}
		});
		if (faceView2.getChildCount() == 0) {
			faceView2.setFaces();
		}

		final TextView tv_face1 = (TextView) findViewById(R.id.input_def);
		final TextView tv_face2 = (TextView) findViewById(R.id.input_other);
		tv_face1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv_face1.setBackgroundColor(getMyColor(R.color.facechoose1));
				tv_face1.setTextColor(getMyColor(R.color.facechoose2));
				tv_face2.setBackgroundColor(getMyColor(R.color.facechoose2));
				tv_face2.setTextColor(getMyColor(R.color.facechoose1));

				faceView.setVisibility(View.VISIBLE);
				faceView2.setVisibility(View.GONE);
			}
		});
		tv_face2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv_face1.setBackgroundColor(getMyColor(R.color.facechoose2));
				tv_face1.setTextColor(getMyColor(R.color.facechoose1));
				tv_face2.setBackgroundColor(getMyColor(R.color.facechoose1));
				tv_face2.setTextColor(getMyColor(R.color.facechoose2));

				faceView2.setVisibility(View.VISIBLE);
				faceView.setVisibility(View.GONE);
			}
		});
	}

	private int getMyColor(int color) {
		return getResources().getColor(color);
	}

	/**
	 * 表情输入
	 */
	private void showFace() {
		InputMethodManager inputMethodManager = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
		inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		findViewById(R.id.input_faces).setVisibility(View.VISIBLE);
		et.requestFocus();
	}

	/**
	 * 键盘输入
	 * 
	 * @param view
	 */
	private void showKeyBoard(View view) {
		InputMethodManager inputMethodManager = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
		findViewById(R.id.input_faces).setVisibility(View.GONE);
		try {
			inputMethodManager.showSoftInputFromInputMethod(this
					.getCurrentFocus().getWindowToken(), 0);
			inputMethodManager.showSoftInput(et, 0);
			et.requestFocus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class CommentTask extends AsyncTask<String, Integer, ResultModel> {

		SpinnerProgressDialog sDialog;

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			sDialog = new SpinnerProgressDialog(SunpicInfoActivity.this);
			sDialog.showProgressDialog("评论中...");
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				if (type == 0) {
					mc = new RecommendCommentParams(App.app, bean.getObj_id(),
							params[0]).getResult();
				} else if (type == 1) {
					mc = new SunPicCommentParams(App.app, bean.getObj_id(),
							params[0]).getResult();
				} else if (type == 2) {

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(ResultModel result) {
			sDialog.cancelProgressDialog("");
			if (result != null && result.getCode() == 1) {
				start = 0;
				new CommentListTask().execute();
				ShowToast.getToast().show("评论成功");
				et.setText("");
			} else {
				ShowToast.getToast().show("评论失败");
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
		}
	}

	private class SunpicInfoTask extends
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
				if (type == 0) {
					mc = new RecommendInfoParams(App.app, bean.getObj_id())
							.getResult();
				} else if (type == 1) {
					mc = new SunPicInfoParams(App.app, bean.getObj_id())
							.getResult();
				} else if (type == 2) {

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				SunpicInfoBean bean = (SunpicInfoBean) result.getResult();

				List<ResourceBean> resources = bean.getResources();
				if (resources != null)
					for (ResourceBean rb : resources) {
						final ImageView iv = (ImageView) LayoutInflater.from(
								SunpicInfoActivity.this).inflate(
								R.layout.item_photo, null);
						ll_photos.addView(iv);
						App.app.showUrlImg(iv,
								rb.getResource_url() + rb.getResource_path());
					}
			} else {
				// TODO
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
		}
	}

	private class CommentListTask extends
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
				if (type == 0) {
					mc = new RecommendCommentListParams(App.app,
							bean.getObj_id()).getResult();
				} else if (type == 1) {
					mc = new SunPicCommentListParams(App.app, bean.getObj_id())
							.getResult();
				} else if (type == 2) {

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
				SunPicCommentListBean bean = (SunPicCommentListBean) result
						.getResult();
				List<CommentBean> comments = bean.getComments();
				if (comments != null) {
					tv_num.setText("评论: (" + bean.getPage().getTotalCount()
							+ ")");
					int size = comments.size();
					if (start == 0) {
						if (size == 0) {
							rlv.onLoadMoreComplete(3);
						} else {
							list.clear();
							list.addAll(comments);
							if (size != 10) {
								rlv.onLoadMoreComplete(2);
							} else {
								rlv.onLoadMoreComplete(0);
							}
						}
					} else {
						list.addAll(comments);
						if (size != 10) {
							rlv.onLoadMoreComplete(2);
						} else {
							rlv.onLoadMoreComplete(0);
						}
					}
					ga.notifyDataSetChanged();
				} else {
					rlv.onLoadMoreComplete(3);
				}
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
