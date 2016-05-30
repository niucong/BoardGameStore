package com.yikeguan.boardgamestore;

import java.util.ArrayList;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yikeguan.boardgamestore.adapter.HomePageAdapter;
import com.yikeguan.boardgamestore.response.TrendBean;
import com.yikeguan.boardgamestore.view.RefreshListView;

public class PrintDetailActivity extends BasicActivity {

	private ImageView iv_title_left, iv_title_right;
	private TextView tv_title;

	private RefreshListView rlv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.print_detail);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		
		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		iv_title_right = (ImageView) findViewById(R.id.iv_title_right);
		tv_title = (TextView) findViewById(R.id.tv_title);

		iv_title_left.setImageResource(R.drawable.icon_back);
		iv_title_right.setImageResource(R.drawable.icon_class);
		tv_title.setText("晒图详情");

		iv_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		rlv = (RefreshListView) findViewById(R.id.print_detail_list);
		View view = LayoutInflater.from(this).inflate(
				R.layout.print_detail_header, null);
		rlv.addHeaderView(view);

		ArrayList<TrendBean> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(new TrendBean());
		}
		rlv.setAdapter(new HomePageAdapter(this, list));

	}

}
