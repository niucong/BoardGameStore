package com.yikeguan.boardgamestore;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.umeng.analytics.MobclickAgent;
import com.yikeguan.boardgamestore.view.SystemBarTintManager;

public class BasicActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		if (Build.VERSION.SDK_INT < 19) {
		} else {
			// 创建状态栏的管理实例
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			// 激活状态栏设置
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setTintAlpha(0.5f);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
