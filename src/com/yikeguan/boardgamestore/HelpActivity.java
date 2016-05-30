package com.yikeguan.boardgamestore;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpActivity extends BasicActivity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		
		ImageView iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		TextView tv_title = (TextView) findViewById(R.id.tv_title);

		iv_title_left.setImageResource(R.drawable.icon_back);
		tv_title.setText("帮助中心");

		iv_title_left.setOnClickListener(this);
		findViewById(R.id.help_chatus).setOnClickListener(this);
		findViewById(R.id.help_callus).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
		case R.id.iv_title_right:
			finish();
			break;
		case R.id.help_chatus:
			
			break;
		case R.id.help_callus:
			
			break;

		default:
			break;
		}
	}

}
