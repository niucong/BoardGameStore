package com.yikeguan.boardgamestore.util;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.yikeguan.boardgamestore.app.App;

public class BaseData {
	public static int getScreenWidth() {
		AppSharedPreferences preferences = new AppSharedPreferences(App.app);
		return preferences.getIntMessage("program", "screen_width", 0);
	}

	public static int getScreenHeight() {
		AppSharedPreferences preferences = new AppSharedPreferences(App.app);
		return preferences.getIntMessage("program", "screen_height", 0);
	}

	/**
	 * header高度 in px
	 * 
	 * @param context
	 * @return
	 */
	public static int getHeaderHeight(Context context) {
		AppSharedPreferences preferences = new AppSharedPreferences(App.app);
		return preferences.getIntMessage("program", "header_height",
				DensityUtil.dip2px(context, 43.5f));
	}

	/**
	 * 状态栏高度 in px
	 * 
	 * @param context
	 * @return
	 */
	public static int getStateBarHeight(Context context) {
		AppSharedPreferences preferences = new AppSharedPreferences(App.app);
		return preferences.getIntMessage("program", "state_bar_height",
				setStateBarHeight(context));
	}

	public static int setStateBarHeight(Context context) {
		AppSharedPreferences preferences = new AppSharedPreferences(App.app);
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		preferences.saveIntMessage("program", "state_bar_height", sbar);
		return sbar;
	}

	/**
	 * 无输入
	 */
	public static void hideKeyBoard(Activity context) {
		try {
			((InputMethodManager) context
					.getSystemService(Activity.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(context.getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 显示输入框
	 */
	public static void showKeyBoard(Activity context, View editView) {
		editView.requestFocus();
		((InputMethodManager) context
				.getSystemService(Activity.INPUT_METHOD_SERVICE))
				.showSoftInput(editView, 0);
	}
}
