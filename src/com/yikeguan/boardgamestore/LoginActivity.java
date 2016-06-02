package com.yikeguan.boardgamestore;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.app.ShowToast;
import com.yikeguan.boardgamestore.dialog.SpinnerProgressDialog;
import com.yikeguan.boardgamestore.response.LoginBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.LoginLoadNameParams;
import com.yikeguan.boardgamestore.resquest.LoginMailParams;
import com.yikeguan.boardgamestore.resquest.LoginPhoneParams;
import com.yikeguan.boardgamestore.resquest.LoginThreeParams;
import com.yikeguan.boardgamestore.resquest.RegisterThreeParams;
import com.yikeguan.boardgamestore.util.CharUtil;
import com.yikeguan.boardgamestore.util.Constants;
import com.yikeguan.boardgamestore.util.L;

public class LoginActivity extends BasicActivity implements OnClickListener {
	private static final String TAG = "LoginActivity";

	private ImageView iv_title_left;
	private TextView tv_title, tv_right;

	private EditText et_account, et_password;

	private Tencent mTencent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);

		initView();
	}

	private void initView() {
		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_title = (TextView) findViewById(R.id.tv_title);

		et_account = (EditText) findViewById(R.id.login_account);
		et_password = (EditText) findViewById(R.id.login_password);

		findViewById(R.id.login_btn).setOnClickListener(this);
		findViewById(R.id.login_phone).setOnClickListener(this);
		findViewById(R.id.login_findpsd).setOnClickListener(this);
		findViewById(R.id.login_sina).setOnClickListener(this);
		findViewById(R.id.login_qq).setOnClickListener(this);
		findViewById(R.id.login_wenxin).setOnClickListener(this);

		iv_title_left.setImageResource(R.drawable.icon_back);
		tv_right.setText("注册");
		tv_title.setText("登录");

		tv_right.setOnClickListener(this);
		iv_title_left.setOnClickListener(this);

		String account = App.app.preferences.getStringMessage("app", "Account",
				"");
		if (!account.equals(""))
			et_account.setText(account);
	}

	private SsoHandler mSsoHandler;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			finish();
			break;
		case R.id.tv_right:
			startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			break;
		case R.id.login_btn:
			String strAcc = et_account.getText().toString();
			String strPsd = et_password.getText().toString();
			if (strAcc != null && !strAcc.trim().equals("") && strPsd != null
					&& !strPsd.trim().equals("")) {
				new LoginTask().execute(strAcc, strPsd);
			} else {
				Toast.makeText(this, "手机号和密码均不能为空", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.login_phone:
			Toast.makeText(this, "全球手机登录", Toast.LENGTH_LONG).show();
			break;
		case R.id.login_findpsd:
			Intent fi = new Intent(LoginActivity.this, RegisterActivity.class);
			fi.putExtra("IsFind", true);
			startActivity(fi);
			break;
		case R.id.login_sina:
			Toast.makeText(this, "新浪帐号登录", Toast.LENGTH_LONG).show();
			AuthInfo mAuthInfo = new AuthInfo(this, Constants.APP_KEY,
					Constants.REDIRECT_URL, Constants.SCOPE);
			mSsoHandler = new SsoHandler(this, mAuthInfo);
			mSsoHandler.authorize(new AuthListener());
			break;
		case R.id.login_qq:
			ShowToast.getToast().show("QQ帐号登录");
			if (mTencent == null)
				mTencent = Tencent.createInstance("1104560823", this);
			mTencent.login(this, "get_simple_userinfo", new BaseUiListener());
			break;
		case R.id.login_wenxin:
			ShowToast.getToast().show("微信帐号登录");
			break;
		default:
			break;
		}
	}

	private class BaseUiListener implements IUiListener {
		@Override
		public void onComplete(Object response) {
			// V2.0版本，参数类型由JSONObject 改成了Object,具体类型参考api文档
			L.d(TAG, "onComplete: " + response);
			try {
				initOpenidAndToken(new JSONObject(response.toString()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onError(UiError e) {
			L.d(TAG, "onError: code:" + e.errorCode + ", msg:" + e.errorMessage
					+ ", detail:" + e.errorDetail);
		}

		@Override
		public void onCancel() {
			L.d(TAG, "onCancel");
		}
	}

	public void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject
					.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject
					.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
			openId = jsonObject
					.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
			L.i(TAG, "initOpenidAndToken token=" + token + ",expires="
					+ expires + ",openId=" + openId);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
					&& !TextUtils.isEmpty(openId)) {
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openId);
				UserInfo info = new UserInfo(this, mTencent.getQQToken());
				L.d(TAG, "initOpenidAndToken info=" + info.toString());
				info.getUserInfo(new IUiListener() {

					@Override
					public void onError(UiError arg0) {

					}

					@Override
					public void onComplete(Object arg0) {
						L.d(TAG, "initOpenidAndToken onComplete=" + arg0);
						// {"is_yellow_year_vip":"0","ret":0,"figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/1104560823\/E8BA5A0F66BB3559D1866C7F704A0C68\/40","figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/1104560823\/E8BA5A0F66BB3559D1866C7F704A0C68\/100","nickname":"玄飞","yellow_vip_level":"0","is_lost":0,"msg":"","city":"昌平","figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104560823\/E8BA5A0F66BB3559D1866C7F704A0C68\/50","vip":"0","level":"0","figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104560823\/E8BA5A0F66BB3559D1866C7F704A0C68\/100","province":"北京","is_yellow_vip":"0","gender":"男","figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104560823\/E8BA5A0F66BB3559D1866C7F704A0C68\/30"}
						try {
							JSONObject json = new JSONObject(arg0.toString());
							new RegisterTask(2).execute(openId + "",
									json.getString("nickname"),
									json.getString("figureurl_qq_2"),
									json.getString("gender"),
									json.getString("msg"));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onCancel() {

					}
				});
			}
		} catch (Exception e) {
		}
	}

	private class LoginTask extends AsyncTask<String, Integer, ResultModel> {

		SpinnerProgressDialog sDialog;

		String account;

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			sDialog = new SpinnerProgressDialog(LoginActivity.this);
			sDialog.showProgressDialog("登录中...");
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			account = params[0];
			try {
				if (uid != 0) {
					mc = new LoginThreeParams(App.app, uid + "").getResult();
				} else if (openId != null && !"".equals(openId)) {
					mc = new LoginThreeParams(App.app, openId).getResult();
				} else if (params.length > 1) {
					if (CharUtil.isValidEmail(account)) {
						// 邮箱登录
						mc = new LoginMailParams(App.app, account, params[1])
								.getResult();
					} else if (CharUtil.isValidPhone(account)) {
						// 手机号登录
						mc = new LoginPhoneParams(App.app, account, params[1])
								.getResult();
					} else {
						// 用户名
						mc = new LoginLoadNameParams(App.app, account,
								params[1]).getResult();
					}
				} else {
					// 登录失败
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			uid = 0;
			openId = null;
			return mc;
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(ResultModel result) {
			sDialog.cancelProgressDialog("");
			if (result != null) {
				int code = result.getCode();
				if (code == 1) {
					final LoginBean bean = (LoginBean) result.getResult();

					EMChatManager.getInstance().login(bean.getMember_id(),
							"111111", new EMCallBack() {// 回调
								@Override
								public void onSuccess() {
									runOnUiThread(new Runnable() {
										public void run() {
											EMGroupManager.getInstance()
													.loadAllGroups();
											EMChatManager.getInstance()
													.loadAllConversations();
											L.i(TAG, "LoginTask 登陆聊天服务器成功！");
										}
									});
								}

								@Override
								public void onProgress(int progress,
										String status) {

								}

								@Override
								public void onError(int code, String message) {
									L.i(TAG, "LoginTask 登陆聊天服务器失败！");
									L.i(TAG, "LoginTask code=" + code
											+ ",message=" + message);

									new Thread(new Runnable() {
										public void run() {
											try {
												// 调用sdk注册方法
												EMChatManager
														.getInstance()
														.createAccountOnServer(
																bean.getMember_id(),
																"111111");
												EMChatManager
														.getInstance()
														.login(bean
																.getMember_id(),
																"111111", null);

												EMChatManager
														.getInstance()
														.updateCurrentUserNick(
																bean.getMember_name());
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}).start();
								}
							});
					try {
						EMChatManager.getInstance().updateCurrentUserNick(
								bean.getMember_name());
					} catch (Exception e) {
						e.printStackTrace();
					}

					finish();
					App.app.preferences.saveStringMessage("app", "Account",
							account);
					App.app.preferences.saveStringMessage("app", "SessionKey",
							bean.getSession_key());
					App.app.preferences.saveStringMessage("app", "MyId",
							bean.getMember_id());
					App.app.preferences.saveStringMessage("app", "MyName",
							bean.getMember_name());
					App.app.preferences.saveStringMessage("app", "Head",
							bean.getHead_url() + bean.getHead_path());
					App.app.preferences.saveStringMessage("app", "MyMood",
							bean.getMember_mood());
					App.app.preferences.saveStringMessage("app", "MySex",
							bean.getMember_sex());
					App.app.preferences.saveStringMessage("app", "MyPhone",
							bean.getMember_phone());
				} else if (code == 0) {
					// {"result":null,"code":0,"desc":"用户名或密码错误！","version":"1.0"}
					ShowToast.getToast().show(result.getDesc());
				} else {
					ShowToast.getToast().show("登录失败");
				}
			} else {
				ShowToast.getToast().show("登录失败");
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			sDialog.cancelProgressDialog("");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	class AuthListener implements WeiboAuthListener {
		@Override
		public void onComplete(Bundle values) {
			Oauth2AccessToken mAccessToken = Oauth2AccessToken
					.parseAccessToken(values); // 从 Bundle中解析 Token
			if (mAccessToken.isSessionValid()) {
				// AccessTokenKeeper.writeAccessToken(LoginActivity.this,
				// mAccessToken); // 保存Token

				L.d(TAG,
						"AuthListener ExpiresTime="
								+ mAccessToken.getExpiresTime());
				L.d(TAG,
						"AuthListener RefreshToken="
								+ mAccessToken.getRefreshToken());
				L.d(TAG, "AuthListener Token=" + mAccessToken.getToken());
				L.d(TAG, "AuthListener Uid=" + mAccessToken.getUid());

				UsersAPI mUsersAPI = new UsersAPI(LoginActivity.this,
						Constants.APP_KEY, mAccessToken);
				uid = Long.parseLong(mAccessToken.getUid());
				mUsersAPI.show(uid, mListener);
			} else {
				// 当您注册的应用程序签名不正确时，就会收到错误Code，请确保签名正确
				String code = values.getString("code", "");
				L.d(TAG, "AuthListener code=" + code);
			}
		}

		@Override
		public void onCancel() {
			L.v(TAG, "AuthListener onCancel...");
		}

		@Override
		public void onWeiboException(WeiboException arg0) {
			L.v(TAG, "AuthListener arg0=" + arg0);
		}
	}

	long uid;
	String openId;

	private RequestListener mListener = new RequestListener() {
		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {
				// 调用 User#parse 将JSON串解析成User对象
				User user = User.parse(response);
				L.i(TAG, "mListener name=" + user.name);
				L.i(TAG, "mListener avatar_large=" + user.avatar_large);
				L.i(TAG, "mListener description=" + user.description);
				L.i(TAG, "mListener gender=" + user.gender);
				// TODO
				new RegisterTask(3).execute(uid + "", user.name,
						user.avatar_large, user.gender, user.description);
			}
		}

		@Override
		public void onWeiboException(WeiboException arg0) {
			L.v(TAG, "mListener arg0=" + arg0);
		}
	};

	private class RegisterTask extends AsyncTask<String, Integer, ResultModel> {

		SpinnerProgressDialog sDialog;

		String account;

		int type;

		/**
		 * open_id、member_name、head_url、member_sex、member_mood
		 * 
		 * @param type
		 */
		public RegisterTask(int type) {
			this.type = type;
		}

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			sDialog = new SpinnerProgressDialog(LoginActivity.this);
			sDialog.showProgressDialog("注册中...");
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			account = params[0];
			try {
				String member_sex = "1";
				if (!"m".equals(params[3]) && !"男".equals(params[3])) {
					member_sex = "2";
				}
				mc = new RegisterThreeParams(App.app, account, "" + type,
						params[1], params[2], null, member_sex, params[4])
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
				new LoginTask().execute(uid + "");
			} else {
				// TODO
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			sDialog.cancelProgressDialog("");
		}
	}

}
