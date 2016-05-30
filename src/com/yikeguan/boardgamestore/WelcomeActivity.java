package com.yikeguan.boardgamestore;

import android.content.Intent;
import android.os.Bundle;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.util.L;

public class WelcomeActivity extends BasicActivity {
	private static final String TAG = "WelcomeActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		String mId = App.app.preferences.getStringMessage("app", "MyId", "0");
		if (!"0".equals(mId)) {
			L.i(TAG, "mId=" + mId);
			// try {
			// // 调用sdk注册方法
			// EMChatManager.getInstance()
			// .createAccountOnServer(mId, "111111");
			// } catch (final EaseMobException e) {
			// e.printStackTrace();
			// }
			EMChatManager.getInstance().login(mId, "111111", new EMCallBack() {// 回调
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
						public void onProgress(int progress, String status) {
							L.d(TAG, "progress=" + progress + ",status="
									+ status);
						}

						@Override
						public void onError(int code, String message) {
							L.i(TAG, "onError code=" + code + ",message="
									+ message);
						}
					});
		}

		new Thread() {
			@Override
			public void run() {
				try {
					sleep(3 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				enterLogin();
			}
		}.start();
	}

	private void enterLogin() {
		if (App.app.preferences.getIntMessage("program", "screen_width", 0) == 0) {
			startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
		} else {
			startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
		}
		WelcomeActivity.this.finish();
	}

}
