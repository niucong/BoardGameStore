package com.yikeguan.boardgamestore.app;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yikeguan.boardgamestore.R;

public class ShowToast extends Toast {

	private static ShowToast instance = null;
	private View layout;
	private TextView text;

	private ShowToast(Context context) {
		super(context);
		init(context);
	}

	public static ShowToast getToast() {
		if (instance == null) {
			instance = new ShowToast(App.app);
		}
		return instance;
	}

	private void init(Context context) {
		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflate.inflate(R.layout.transient_notification, null);
		text = (TextView) layout.findViewById(R.id.message);
		setGravity(Gravity.CENTER, 0, 0);
		this.setView(layout);
	}

	public void show(String msg) {
		if (msg != null && !"".equals(msg)) {
			text.setText(msg);
			this.setDuration(Toast.LENGTH_LONG);
			this.show();
		}
	}
}
