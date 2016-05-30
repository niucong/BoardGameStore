package com.yikeguan.boardgamestore;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.yikeguan.boardgamestore.response.DesignerBean;
import com.yikeguan.boardgamestore.response.PublisherBean;
import com.yikeguan.boardgamestore.view.TagNameView;
import com.yikeguan.boardgamestore.view.TagView;

public class SearchActivity extends BasicActivity {

	private ImageView iv_title_left, iv_title_right;
	private TagView hotView, historyView;

	EditText et;

	RadioGroup rg;
	/** 0：游戏名、1：出版社、2：设计师 */
	int type = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		
		et = (EditText) findViewById(R.id.search_bar_title);

		iv_title_left = (ImageView) findViewById(R.id.search_bar_left);
		iv_title_right = (ImageView) findViewById(R.id.search_bar_right);
		hotView = (TagView) findViewById(R.id.hot_tag);
		for (int i = 0; i < 10; i++) {
			hotView.addView(new TagNameView(this, "热门搜索内容" + i), 0);
		}
		historyView = (TagView) findViewById(R.id.history_tag);
		for (int i = 0; i < 5; i++) {
			historyView.addView(new TagNameView(this, i + "搜索历史内容"), 0);
		}

		iv_title_left.setImageResource(R.drawable.icon_back);
		iv_title_right.setImageResource(R.drawable.icon_search);

		iv_title_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				search();
			}
		});
		iv_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		rg = (RadioGroup) findViewById(R.id.radioGroup1);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radio1) {
					type = 1;
				} else if (checkedId == R.id.radio2) {
					type = 2;
				} else {
					type = 0;
				}
			}
		});

		et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					search();
				}
				return false;
			}
		});
	}

	private void search() {
		String name = et.getText().toString();
		if ("".equals(name))
			return;
		if (type == 1) {
			PublisherBean pb = new PublisherBean();
			pb.setPublisher_name(name);
			Intent pi = new Intent(SearchActivity.this, GameListActivity.class);
			pi.putExtra("PublisherBean", pb);
			startActivity(pi);
		} else if (type == 2) {
			DesignerBean db = new DesignerBean();
			db.setDesigner_name(name);
			Intent di = new Intent(SearchActivity.this, GameListActivity.class);
			di.putExtra("DesignerBean", db);
			startActivity(di);
		} else {
			Intent di = new Intent(SearchActivity.this, GameListActivity.class);
			di.putExtra("GameName", name);
			startActivity(di);
		}
	}
}
