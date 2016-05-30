package com.yikeguan.boardgamestore;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yikeguan.boardgamestore.adapter.CommentListAdapter;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.app.ShowToast;
import com.yikeguan.boardgamestore.dialog.SpinnerProgressDialog;
import com.yikeguan.boardgamestore.response.CommentBean;
import com.yikeguan.boardgamestore.response.CommentMembersListBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.game.CommentGameListParams;
import com.yikeguan.boardgamestore.resquest.game.CommentGameParams;
import com.yikeguan.boardgamestore.util.FaceUtil;
import com.yikeguan.boardgamestore.view.FacesView;
import com.yikeguan.boardgamestore.view.FacesView.OnFaceChosenListner;
import com.yikeguan.boardgamestore.view.RefreshListView;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnLoadMoreListener;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnRefreshListener;

public class CommentListActivity extends BasicActivity implements
		OnClickListener {
	private final String TAG = "GameListActivity";

	private ImageView iv_title_left, iv_title_right;
	private TextView tv_title, tv_send;
	private RefreshListView rlv;
	private EditText et;

	private ArrayList<CommentBean> list;
	private CommentListAdapter ga;

	private int id;

	private int start;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_list);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);

		id = getIntent().getIntExtra("ID", 0);

		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		tv_title = (TextView) findViewById(R.id.tv_title);

		et = (EditText) findViewById(R.id.input_text);
		tv_send = (TextView) findViewById(R.id.input_send);
		tv_send.setOnClickListener(this);

		rlv = (RefreshListView) findViewById(R.id.homepage_list);
		list = new ArrayList<>();
		ga = new CommentListAdapter(this, list);
		rlv.setAdapter(ga);
		start = 0;
		new CommentListTask().execute();

		iv_title_left.setImageResource(R.drawable.icon_back);
		tv_title.setText("评论");

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

		initFace();
		et.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showKeyBoard(v);
			}
		});
		findViewById(R.id.input_face).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showFace();
			}
		});
	}

	private void initFace() {
		final FacesView faceView = (FacesView) findViewById(R.id.input_facesview);
		faceView.setOnFaceChosenListner(new OnFaceChosenListner() {

			@Override
			public void onChosen(String text, int resId) {
				FacesView.doEditChange(CommentListActivity.this, et, text,
						resId);
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
				FacesView.doEditChange(CommentListActivity.this, et, text,
						resId);
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

	private class CommentListTask extends
			AsyncTask<String, Integer, ResultModel> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new CommentGameListParams(App.app, start + "", "10", ""
						+ id).getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			rlv.onRefreshComplete();
			if (result != null && result.getCode() == 1) {
				CommentMembersListBean bean = (CommentMembersListBean) result
						.getResult();
				List<CommentBean> gbs = bean.getComments();
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
						list.addAll(bean.getComments());
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
		case R.id.input_send:
			String text = et.getText().toString();
			if ("".equals(text)) {
				ShowToast.getToast().show("评论内容不能为空");
			} else {
				new CommentTask().execute(text);
			}
			break;

		default:
			break;
		}
	}

	private class CommentTask extends AsyncTask<String, Integer, ResultModel> {
		SpinnerProgressDialog sDialog;

		@Override
		protected void onPreExecute() {
			sDialog = new SpinnerProgressDialog(CommentListActivity.this);
			sDialog.showProgressDialog("评论中...");
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new CommentGameParams(App.app, id + "", params[0])
						.getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			sDialog.cancelProgressDialog("");
			if (result != null && result.getCode() == 1) {
				et.setText("");
				ShowToast.getToast().show("评论成功");
				start = 0;
				new CommentListTask().execute();
			} else {
				// TODO
			}
		}

		@Override
		protected void onCancelled() {
			sDialog.cancelProgressDialog("");
		}
	}

}
