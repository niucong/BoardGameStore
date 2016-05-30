package com.yikeguan.boardgamestore;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yikeguan.boardgamestore.adapter.ChatContactListAdapter;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.dialog.SpinnerProgressDialog;
import com.yikeguan.boardgamestore.response.ContactBean;
import com.yikeguan.boardgamestore.response.ContactListBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.ContactListParams;
import com.yikeguan.boardgamestore.view.RefreshListView;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnLoadMoreListener;
import com.yikeguan.boardgamestore.view.RefreshListView.IOnRefreshListener;

public class ChatContactListActivity extends BasicActivity {

	private ImageView iv_title_left, iv_title_right;
	private TextView tv_title;
	private RefreshListView rlv;
	ChatContactListAdapter ga;
	ArrayList<ContactBean> list;

	private int start;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_contact_list);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);

		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		tv_title = (TextView) findViewById(R.id.tv_title);

		rlv = (RefreshListView) findViewById(R.id.print_detail_list);
		list = new ArrayList<>();
		ga = new ChatContactListAdapter(this, list);
		rlv.setAdapter(ga);
		start = 0;
		new ContactListTask().execute();

		iv_title_left.setImageResource(R.drawable.icon_back);
		tv_title.setText("聊天列表");

		iv_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		rlv.setOnRefreshListener(new IOnRefreshListener() {

			@Override
			public void OnRefresh() {
				start = 0;
				new ContactListTask().execute();
			}
		});

		rlv.setOnLoadMoreListener(new IOnLoadMoreListener() {

			@Override
			public void OnLoadMore() {
				start = list.size();
				new ContactListTask().execute();
			}
		});
	}

	private class ContactListTask extends
			AsyncTask<String, Integer, ResultModel> {
		SpinnerProgressDialog sDialog;

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			sDialog = new SpinnerProgressDialog(ChatContactListActivity.this);
			sDialog.showProgressDialog("加载中...");
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new ContactListParams(App.app, "" + start, "10")
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
				ContactListBean bean = (ContactListBean) result.getResult();
				List<ContactBean> gbs = bean.getMessages();
				if (gbs != null) {

					int size = gbs.size();
					if (start == 0) {
						if (size == 0) {
							rlv.onLoadMoreComplete(3);
						} else {
							list.clear();
							list.addAll(gbs);
							if (size != 10) {
								rlv.onLoadMoreComplete(2);
							} else {
								rlv.onLoadMoreComplete(0);
							}
						}
					} else {
						list.addAll(bean.getMessages());
						if (size != 10) {
							rlv.onLoadMoreComplete(2);
						} else {
							rlv.onLoadMoreComplete(0);
						}
					}
					ga.notifyDataSetChanged();
				} else {

				}
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
