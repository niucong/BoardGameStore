package com.yikeguan.boardgamestore;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.app.ShowToast;
import com.yikeguan.boardgamestore.dialog.SpinnerProgressDialog;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.CreateSuggestionParams;

public class FeedbackActivity extends BasicActivity implements OnClickListener {

	private EditText et_context, et_phone;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);

		ImageView iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		TextView tv_title = (TextView) findViewById(R.id.tv_title);

		iv_title_left.setImageResource(R.drawable.icon_back);
		tv_title.setText("意见反馈");

		et_context = (EditText) findViewById(R.id.feedback_context);
		et_phone = (EditText) findViewById(R.id.feedback_phone);

		iv_title_left.setOnClickListener(this);
		findViewById(R.id.feedback_send).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
		case R.id.iv_title_right:
			finish();
			break;
		case R.id.feedback_send:
			String context = et_context.getText().toString();
			String phone = et_phone.getText().toString();
			if (!"".equals(context)) {
				new FeedbackTask().execute(context, phone);
			}
			break;

		default:
			break;
		}
	}

	private class FeedbackTask extends AsyncTask<String, Integer, ResultModel> {

		SpinnerProgressDialog sDialog;

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			sDialog = new SpinnerProgressDialog(FeedbackActivity.this);
			sDialog.showProgressDialog("反馈中...");
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new CreateSuggestionParams(App.app, params[0], params[1])
						.getResult();
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
				ShowToast.getToast().show("反馈成功");
				finish();
			} else {
				ShowToast.getToast().show("反馈失败");
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			// sDialog.cancelProgressDialog("");
		}
	}

}
