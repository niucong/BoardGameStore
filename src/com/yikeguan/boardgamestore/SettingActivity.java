package com.yikeguan.boardgamestore;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.dialog.CheckUpdateVersion;
import com.yikeguan.boardgamestore.dialog.SpinnerProgressDialog;
import com.yikeguan.boardgamestore.net.DownLoadFileThread;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.response.VersionBean;
import com.yikeguan.boardgamestore.resquest.GetNewsClientVersionParams;
import com.yikeguan.boardgamestore.util.SoftPhoneInfo;

public class SettingActivity extends BasicActivity implements OnClickListener {

	public static boolean loading = false;
	public SpinnerProgressDialog sDialog;
	private String currentVersion;
	LoadingProgressReceiver progressReceiver;

	String myId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		sDialog = new SpinnerProgressDialog(this);
		currentVersion = new SoftPhoneInfo(this).getVersionName();

		ImageView iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		TextView tv_title = (TextView) findViewById(R.id.tv_title);

		iv_title_left.setImageResource(R.drawable.icon_back);
		tv_title.setText("设置");

		iv_title_left.setOnClickListener(this);
		findViewById(R.id.setting_about).setOnClickListener(this);

		myId = App.app.preferences.getStringMessage("app", "MyId", "0");
		if ("0".equals(myId))
			findViewById(R.id.setting_exit).setVisibility(View.GONE);
		findViewById(R.id.setting_password).setOnClickListener(this);
		findViewById(R.id.setting_version).setOnClickListener(this);
		findViewById(R.id.setting_sys).setOnClickListener(this);
		findViewById(R.id.setting_exit).setOnClickListener(this);

		progressReceiver = new LoadingProgressReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(DownLoadFileThread.TAG);
		registerReceiver(progressReceiver, intentFilter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(progressReceiver); // 取消监听
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			finish();
			break;
		case R.id.setting_password:
			if (!"0".equals(myId)) {
				startActivity(new Intent(this, SettingPasswordActivity.class));
			} else {
				enterLogin();
			}
			break;
		case R.id.setting_version:
			if (loading) {

			} else {
				new CheckVersionTask().execute();
			}
			break;
		case R.id.setting_sys:
			if (!"0".equals(myId)) {

			} else {
				enterLogin();
			}
			break;
		case R.id.setting_about:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		case R.id.setting_exit:
			App.app.preferences.saveStringMessage("app", "MyId", "0");
			App.app.preferences.saveStringMessage("app", "SessionKey", "");
			finish();
			break;

		default:
			break;
		}
	}

	private void enterLogin() {
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}

	private class CheckVersionTask extends
			AsyncTask<String, Integer, ResultModel> {

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			sDialog.showProgressDialog("正在检测新版本...");
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel a = null;
			try {
				a = new GetNewsClientVersionParams(App.app).getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return a;
		}

		@Override
		protected void onPostExecute(ResultModel a) {
			if (a == null) {
				sDialog.cancelProgressDialog("检测失败");
			} else {
				if (a.getResult() != null) {
					VersionBean vb = (VersionBean) a.getResult();
					if (currentVersion.equals(vb.getVersion_name())) {
						sDialog.cancelProgressDialog("已经是最新版本");
					} else {
						sDialog.cancelProgressDialog("");
						new CheckUpdateVersion(SettingActivity.this)
								.versionDialog(
										vb.getVersion_url()
												+ vb.getVersion_path(),
										vb.getVersion_name());
					}
				} else {
					sDialog.cancelProgressDialog("检测失败");
				}
			}
		}
	}

	public class LoadingProgressReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (DownLoadFileThread.TAG.equals(action)) {
				int progress = intent.getIntExtra("progress", 0);
				if (progress == 100) {
					sDialog.cancelProgressDialog("");
				} else {
					sDialog.showProgressDialog("已下载 " + progress + "%...");
				}
			}
		}
	}

}
