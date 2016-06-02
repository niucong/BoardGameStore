package com.yikeguan.boardgamestore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.app.ShowToast;
import com.yikeguan.boardgamestore.dialog.SpinnerProgressDialog;
import com.yikeguan.boardgamestore.response.GameBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.game.GradeGameParams;

public class GradeScoreActivity extends BasicActivity implements
		OnClickListener {

	RatingBar rb;
	TextView tv;
	TextView tv_title;

	private GameBean gb;
	int s0 = 0;
	int score;

	boolean isChange = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grade_score);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		gb = (GameBean) getIntent().getSerializableExtra("GameBean");

		ImageView iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		tv_title = (TextView) findViewById(R.id.tv_title);

		iv_title_left.setImageResource(R.drawable.icon_back);

		float rating = 0;
		try {
			rating = gb.getGrade_totle_num() / gb.getGrade_member_num();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tv_title.setText("总评分：" + rating);

		iv_title_left.setOnClickListener(this);

		tv = (TextView) findViewById(R.id.grade_score_last);
		final TextView status = (TextView) findViewById(R.id.grade_score_status);

		try {
			s0 = Integer.valueOf(gb.getGrade_score());
			if (s0 > 8) {
				status.setText("非常好");
			} else if (s0 > 6) {
				status.setText("很好");
			} else if (s0 > 4) {
				status.setText("好");
			} else if (s0 > 2) {
				status.setText("一般");
			} else if (s0 > 0) {
				status.setText("差");
			} else {
				status.setText("无");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		tv.setText("我的上次评分：" + s0 + "分");

		rb = (RatingBar) findViewById(R.id.grade_score_rb);
		try {
			rb.setRating(Float.valueOf(gb.getGrade_score()) / 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		findViewById(R.id.grade_score_btn).setOnClickListener(this);

		rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				if (rating > 4) {
					status.setText("非常好");
				} else if (rating > 3) {
					status.setText("很好");
				} else if (rating > 2) {
					status.setText("好");
				} else if (rating > 1) {
					status.setText("一般");
				} else if (rating > 0) {
					status.setText("差");
				} else {
					status.setText("无");
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void back() {
		if (isChange) {
			Intent in = new Intent();
			in.putExtra("GameBean", gb);
			setResult(RESULT_OK, in);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			back();
			finish();
			break;
		case R.id.grade_score_btn:
			score = (int) (rb.getRating() * 2);
			if (score == 0) {
				ShowToast.getToast().show("评分必须大于0分");
			} else if (s0 == score) {

			} else {
				new GradeGameTask().execute();
			}
			break;

		default:
			break;
		}
	}

	private class GradeGameTask extends
			AsyncTask<Integer, Integer, ResultModel> {
		SpinnerProgressDialog sDialog;

		@Override
		protected void onPreExecute() {
			sDialog = new SpinnerProgressDialog(GradeScoreActivity.this);
			sDialog.showProgressDialog("评价中...");
		}

		@Override
		protected ResultModel doInBackground(Integer... params) {
			ResultModel mc = null;
			try {
				mc = new GradeGameParams(App.app, "" + gb.getData_id(), ""
						+ score).getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			sDialog.cancelProgressDialog("");
			if (result != null && result.getCode() == 1) {
				tv.setText("我的上次评分：" + score + "分");
				if (s0 == 0)
					gb.setGrade_member_num(gb.getGrade_member_num() + 1);
				gb.setGrade_totle_num(gb.getGrade_totle_num() - s0 + score);
				gb.setGrade_score("" + score);

				float rating = 0;
				try {
					rating = gb.getGrade_totle_num() / gb.getGrade_member_num();
				} catch (Exception e) {
					e.printStackTrace();
				}
				tv_title.setText("总评分：" + rating);
				isChange = true;
			}
		}

		@Override
		protected void onCancelled() {
		}
	}

}
