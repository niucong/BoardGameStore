package com.yikeguan.boardgamestore;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yikeguan.boardgamestore.util.SoftPhoneInfo;

public class AboutActivity extends BasicActivity implements OnClickListener {

	TextView version;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);

		ImageView iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		TextView tv_title = (TextView) findViewById(R.id.tv_title);

		iv_title_left.setImageResource(R.drawable.icon_back);
		tv_title.setText("关于玩亦有道");

		iv_title_left.setOnClickListener(this);

		version = (TextView) findViewById(R.id.about_version);
		String currentVersion = new SoftPhoneInfo(this).getVersionName();
		if (currentVersion != null && !"".equals(currentVersion)) {
			version.setText("当前版本：v" + currentVersion);
		}
		findViewById(R.id.about_protocol).setOnClickListener(this);
		findViewById(R.id.about_copyright).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			finish();
			break;
		case R.id.about_protocol:
			Intent pi = new Intent(this, WebActivity.class);
			pi.putExtra("Title", "玩亦有道协议");
			pi.putExtra("Url", "file:///android_asset/fwxy.htm");
			startActivity(pi);
			break;
		case R.id.about_copyright:
			Intent ci = new Intent(this, WebActivity.class);
			ci.putExtra("Title", "版权信息");
			ci.putExtra("Url", "file:///android_asset/bqxx.htm");
			startActivity(ci);
			break;

		default:
			break;
		}
	}
}
