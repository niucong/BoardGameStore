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
import com.yikeguan.boardgamestore.resquest.UpdateMemberPasswordParams;

public class SettingPasswordActivity extends BasicActivity implements
		OnClickListener {

	private EditText et_old, et_new, et_renew;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_password);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);

		ImageView iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		TextView tv_title = (TextView) findViewById(R.id.tv_title);

		iv_title_left.setImageResource(R.drawable.icon_back);
		iv_title_left.setOnClickListener(this);
		tv_title.setText("修改密码");
		TextView right = (TextView) findViewById(R.id.tv_right);
		right.setOnClickListener(this);
		right.setText("保存");

		et_old = (EditText) findViewById(R.id.setting_password_old);
		et_new = (EditText) findViewById(R.id.setting_password_new);
		et_renew = (EditText) findViewById(R.id.setting_password_renew);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			finish();
			break;
		case R.id.tv_right:
			String old = et_old.getText().toString();
			String newp = et_new.getText().toString();
			String renew = et_renew.getText().toString();
			if ("".equals(old) || "".equals(newp) || "".equals(renew)) {
				ShowToast.getToast().show("密码不能为空");
			} else {
				if (newp.equals(renew)) {
					new SettingPasswordTask().execute(old, newp);
				} else {
					ShowToast.getToast().show("两次输入的新密码不一致");
				}
			}
			break;

		default:
			break;
		}
	}

	private class SettingPasswordTask extends
			AsyncTask<String, Integer, ResultModel> {

		SpinnerProgressDialog sDialog;

		@Override
		protected void onPreExecute() {
			sDialog = new SpinnerProgressDialog(SettingPasswordActivity.this);
			sDialog.showProgressDialog("修改中...");
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new UpdateMemberPasswordParams(App.app, params[0],
						params[1]).getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			sDialog.cancelProgressDialog("");
			if (result != null && result.getCode() == 1) {
				ShowToast.getToast().show("修改成功");
				finish();
			} else {
				ShowToast.getToast().show("修改失败");
			}
		}

		@Override
		protected void onCancelled() {
			sDialog.cancelProgressDialog("");
		}
	}

}
