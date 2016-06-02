package com.yikeguan.boardgamestore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.app.ShowToast;
import com.yikeguan.boardgamestore.dialog.SpinnerProgressDialog;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.BindingEmailParams;
import com.yikeguan.boardgamestore.resquest.BindingMobileParams;
import com.yikeguan.boardgamestore.resquest.SendMailParams;
import com.yikeguan.boardgamestore.resquest.SendSMSParams;
import com.yikeguan.boardgamestore.resquest.UpdateMemberInfoParams;
import com.yikeguan.boardgamestore.resquest.ValidateMailParams;
import com.yikeguan.boardgamestore.resquest.ValidateMobileParams;

public class EditInfoActivity extends BasicActivity implements OnClickListener {

	EditText et, et_account, et_code;
	private ImageView iv_man, iv_woman;

	int type;
	String text = "1";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_info);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);

		type = getIntent().getIntExtra("Type", 0);

		ImageView iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		iv_title_left.setImageResource(R.drawable.icon_back);

		et_account = (EditText) findViewById(R.id.edit_info_account);
		et_code = (EditText) findViewById(R.id.edit_info_code);
		findViewById(R.id.edit_info_getcode).setOnClickListener(this);
		findViewById(R.id.edit_info_btn).setOnClickListener(this);
		et = (EditText) findViewById(R.id.edit_info_et);

		String str = getIntent().getStringExtra("Text");
		if (type == 0) {
			tv_title.setText("昵称");
			findViewById(R.id.edit_info_et_ll).setVisibility(View.VISIBLE);
			findViewById(R.id.edit_info_clear).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							et.setText("");
						}
					});
		} else if (type == 1) {
			findViewById(R.id.edit_info_sex).setVisibility(View.VISIBLE);
			tv_title.setText("性别");
			iv_man = (ImageView) findViewById(R.id.edit_info_man_iv);
			iv_woman = (ImageView) findViewById(R.id.edit_info_woman_iv);
			if ("男".equals(str.trim()))
				text = "1";
			else
				text = "2";
			setSex();

			findViewById(R.id.edit_info_man).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							text = "1";
							setSex();
						}
					});
			findViewById(R.id.edit_info_woman).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							text = "2";
							setSex();
						}
					});
		} else if (type == 2) {
			findViewById(R.id.edit_info_bangding).setVisibility(View.VISIBLE);
			tv_title.setText("绑定手机号");
		} else if (type == 4) {
			et_account.setHint("请输入要绑定的Email");
			et_account.setInputType(EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			findViewById(R.id.edit_info_bangding).setVisibility(View.VISIBLE);
			tv_title.setText("绑定Email");
		} else if (type == 3) {
			tv_title.setText("签名");
			findViewById(R.id.edit_info_et_ll).setVisibility(View.VISIBLE);
			findViewById(R.id.edit_info_clear).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							et.setText("");
						}
					});
		}
		iv_title_left.setOnClickListener(this);
		TextView tv_right = (TextView) findViewById(R.id.tv_right);
		tv_right.setText("保存");
		tv_right.setOnClickListener(this);
		et.setText(str);
		et.setSelection(str.length());
	}

	private void setSex() {
		if ("1".equals(text)) {
			iv_man.setImageResource(R.drawable.man);
			iv_woman.setImageResource(R.drawable.woman2);
		} else {
			iv_man.setImageResource(R.drawable.man2);
			iv_woman.setImageResource(R.drawable.woman);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			finish();
			break;
		case R.id.tv_right:
			if (type == 0 || type == 2 || type == 3)
				text = et.getText().toString();

			if (!"".equals(text)) {
				new EditInfoTask().execute();
			} else {
				ShowToast.getToast().show("内容不能为空");
			}
			break;
		case R.id.edit_info_getcode:
			String strAcc = et_account.getText().toString();
			if (strAcc != null && !strAcc.trim().equals("")) {
				new RegisterTask(0).execute(strAcc);
			} else {
				if (type == 2) {
					ShowToast.getToast().show("手机号不能为空");
				} else {
					ShowToast.getToast().show("Email不能为空");
				}
			}
			break;
		case R.id.edit_info_btn:
			String strA = et_account.getText().toString();
			String strC = et_code.getText().toString();
			if (strA != null && !strA.trim().equals("") && strC != null
					&& !strC.trim().equals("")) {
				new RegisterTask(1).execute(strA, strC);
			} else {
				if (type == 2) {
					ShowToast.getToast().show("手机号、验证码均不能为空");
				} else {
					ShowToast.getToast().show("Email、验证码均不能为空");
				}
			}
			break;
		default:
			break;
		}
	}

	private class RegisterTask extends AsyncTask<String, Integer, ResultModel> {

		SpinnerProgressDialog sDialog;

		int tp;
		String account;

		public RegisterTask(int type) {
			this.tp = type;
		}

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			sDialog = new SpinnerProgressDialog(EditInfoActivity.this);
			sDialog.showProgressDialog("处理中...");
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			account = params[0];
			try {
				if (tp == 0) {
					if (type == 4) {
						mc = new ValidateMailParams(App.app, account)
								.getResult();
					} else {
						mc = new ValidateMobileParams(App.app, account)
								.getResult();
					}
				} else if (tp == 1) {
					if (type == 4) {
						mc = new BindingEmailParams(App.app, account, params[1])
								.getResult();
					} else {
						mc = new BindingMobileParams(App.app, account,
								params[1]).getResult();
					}
				} else {
					if (type == 4) {
						// 发送邮箱验证码
						mc = new SendMailParams(App.app, account).getResult();
					} else if (type == 2) {
						// 发送短信验证码
						mc = new SendSMSParams(App.app, account).getResult();
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
					if (tp == 0) {
						// result:0 可注册 1 已注册 -1 异常 -3 参数异常
						String rs = result.getResult().toString();
						if ("0".equals(rs)) {
							new RegisterTask(2).execute(account);
						} else if ("2".equals(rs)) {
							if (type == 4) {
								ShowToast.getToast().show("邮箱已被绑定");
							} else if (type == 2) {
								ShowToast.getToast().show("手机号已被绑定");
							}
						} else {
							ShowToast.getToast().show("请求失败");
						}
					} else if (tp == 1) {
						if (type == 4) {
							// 绑定邮箱
							ShowToast.getToast().show("邮箱绑定成功");
							App.app.preferences.saveStringMessage("app",
									"MyMail", account);
						} else if (type == 2) {
							ShowToast.getToast().show("手机号绑定成功");
							App.app.preferences.saveStringMessage("app",
									"MyPhone", account);
						}
						Intent i = new Intent();
						i.putExtra("Text", account);
						setResult(RESULT_OK, i);
						finish();
					} else {
						if (type == 4) {
							// 发送邮箱验证码
							ShowToast.getToast().show("验证码已发到你的邮箱，请注意查收");
						} else if (type == 2) {
							// 发送短信验证码
							ShowToast.getToast().show("验证码已发到你的手机，请注意查收");
						}
					}
				} else {
				}
			} else {

			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			sDialog.cancelProgressDialog("");
		}
	}

	private class EditInfoTask extends AsyncTask<String, Integer, ResultModel> {
		// SpinnerProgressDialog sDialog;

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			// sDialog = new SpinnerProgressDialog(GameListActivity.this);
			// sDialog.showProgressDialog("加载中...");
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				if (type == 0) {
					mc = new UpdateMemberInfoParams(App.app, text, null, null)
							.getResult();
				} else if (type == 1) {
					mc = new UpdateMemberInfoParams(App.app, null, text, null)
							.getResult();
				} else if (type == 2) {
					// 手机号
				} else if (type == 3) {
					mc = new UpdateMemberInfoParams(App.app, null, null, text)
							.getResult();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(ResultModel result) {
			// sDialog.cancelProgressDialog("");
			if (result != null && result.getCode() == 1) {
				ShowToast.getToast().show("修改成功");
				Intent i = new Intent();
				if (type == 0) {
					App.app.preferences
							.saveStringMessage("app", "MyName", text);
				} else if (type == 1) {
					if ("".equals(text)) {
						text = "1";
					} else {
						text = "2";
					}
					App.app.preferences.saveStringMessage("app", "MySex", text);
				} else if (type == 2) {
					// 手机号
					App.app.preferences.saveStringMessage("app", "MyPhone",
							text);
				} else if (type == 3) {
					App.app.preferences
							.saveStringMessage("app", "MyMood", text);
				}
				i.putExtra("Text", text);
				setResult(RESULT_OK, i);
				finish();
			} else {
				ShowToast.getToast().show("修改失败");
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			// sDialog.cancelProgressDialog("");
		}
	}

}
