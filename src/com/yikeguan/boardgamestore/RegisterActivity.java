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
import com.yikeguan.boardgamestore.resquest.FindPasswordEmailParams;
import com.yikeguan.boardgamestore.resquest.FindPasswordMobileParams;
import com.yikeguan.boardgamestore.resquest.RegisterMailParams;
import com.yikeguan.boardgamestore.resquest.RegisterPhoneParams;
import com.yikeguan.boardgamestore.resquest.ResetPasswordParams;
import com.yikeguan.boardgamestore.resquest.SendMailParams;
import com.yikeguan.boardgamestore.resquest.SendSMSParams;
import com.yikeguan.boardgamestore.util.CharUtil;

public class RegisterActivity extends BasicActivity {

	private ImageView iv_title_left;
	private TextView tv_title, tv_getcode, tv_register;
	private EditText et_account, et_code, et_password, et_repassword;

	boolean IsFind;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		IsFind = getIntent().getBooleanExtra("IsFind", false);

		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		tv_title = (TextView) findViewById(R.id.tv_title);

		iv_title_left.setImageResource(R.drawable.icon_back);
		if (IsFind) {
			tv_title.setText("重置密码");
		} else {
			tv_title.setText("注册");
		}

		iv_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		initView();
	}

	private void initView() {
		tv_getcode = (TextView) findViewById(R.id.register_getcode);
		tv_register = (TextView) findViewById(R.id.register_ok);
		et_account = (EditText) findViewById(R.id.register_account);
		et_code = (EditText) findViewById(R.id.register_code);
		et_password = (EditText) findViewById(R.id.register_password);
		et_repassword = (EditText) findViewById(R.id.register_repassword);

		tv_getcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String strAcc = et_account.getText().toString();
				if (strAcc != null && !strAcc.trim().equals("")) {
					new RegisterTask(0).execute(strAcc);
				} else {
					ShowToast.getToast().show("账号不能为空");
				}
			}
		});

		if (IsFind) {
			findViewById(R.id.register_tip).setVisibility(View.GONE);
			tv_register.setText("重置密码");
		}
		tv_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String strAcc = et_account.getText().toString();
				String strCode = et_code.getText().toString();
				String strPsw = et_password.getText().toString();
				String strRepsw = et_repassword.getText().toString();
				if (strAcc != null && !strAcc.trim().equals("")
						&& strCode != null && !strCode.trim().equals("")
						&& strPsw != null && !strPsw.trim().equals("")
						&& strRepsw != null && !strRepsw.trim().equals("")) {
					if (!strPsw.equals(strRepsw)) {
						ShowToast.getToast().show("两次输入的密码不一致");
					} else {
						new RegisterTask(1).execute(strAcc, strPsw, strCode);
					}
				} else {
					ShowToast.getToast().show("账号、验证码、密码均不能为空");
				}
			}
		});
	}

	private class RegisterTask extends AsyncTask<String, Integer, ResultModel> {

		SpinnerProgressDialog sDialog;

		int type;
		String account;

		public RegisterTask(int type) {
			this.type = type;
		}

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			sDialog = new SpinnerProgressDialog(RegisterActivity.this);
			if (IsFind) {
				sDialog.showProgressDialog("重置中...");
			} else {
				sDialog.showProgressDialog("注册中...");
			}

		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			account = params[0];
			try {
				if (type == 0) {
					// 获取验证码
					if (CharUtil.isValidEmail(account)) {
						// 发送短信验证码
						mc = new SendMailParams(App.app, account).getResult();
					} else if (CharUtil.isValidPhone(account)) {
						// 发送邮箱验证码
						mc = new SendSMSParams(App.app, account).getResult();
					}
				} else {
					if (IsFind) {
						if (CharUtil.isValidEmail(account)) {
							mc = new FindPasswordEmailParams(App.app, account,
									params[2]).getResult();
						} else if (CharUtil.isValidPhone(account)) {
							mc = new FindPasswordMobileParams(App.app, account,
									params[2]).getResult();
						}
						App.app.preferences.saveStringMessage("app",
								"SessionKey", mc.getResult().toString());
						type = 2;
						mc = new ResetPasswordParams(App.app, params[1],
								params[1]).getResult();
					} else {
						// 注册
						if (CharUtil.isValidEmail(account)) {
							// 邮箱注册
							mc = new RegisterMailParams(App.app, account,
									params[1], params[2], null, null, null,
									null, null).getResult();
						} else if (CharUtil.isValidPhone(account)) {
							// 手机号码注册
							mc = new RegisterPhoneParams(App.app, account,
									params[1], params[2], null, null, null,
									null, null).getResult();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(final ResultModel result) {
			sDialog.cancelProgressDialog("");
			if (result != null) {
				if (result.getCode() == 1) {
					if (type == 0) {
						ShowToast.getToast().show("获取成功");
					} else if (type == 2) {
						ShowToast.getToast().show("重置成功，请登录");
						finish();
					} else {
						ShowToast.getToast().show("注册成功，请登录");
						finish();
					}
				} else {
					if (result.getCode() == 10) {
						if (type == 1) {
							ShowToast.getToast().show(
									result.getDesc().replace("member_phone",
											account));
						}
					} else {
						if (type == 0) {
							ShowToast.getToast().show("获取失败");
						} else if (type == 2) {
							ShowToast.getToast().show("重置密码失败");
							finish();
						} else {
							ShowToast.getToast().show("注册失败");
							finish();
						}
					}
				}
			} else {
				if (type == 0) {
					ShowToast.getToast().show("获取失败");
				} else if (type == 2) {
					ShowToast.getToast().show("重置密码失败");
					finish();
				} else {
					ShowToast.getToast().show("注册失败");
					finish();
				}
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			sDialog.cancelProgressDialog("");
		}
	}

}
