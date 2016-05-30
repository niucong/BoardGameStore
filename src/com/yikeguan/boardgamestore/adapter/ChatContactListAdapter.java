package com.yikeguan.boardgamestore.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yikeguan.boardgamestore.ChatActivity;
import com.yikeguan.boardgamestore.R;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.ContactBean;
import com.yikeguan.boardgamestore.response.LoginBean;

public class ChatContactListAdapter extends BaseAdapter {
	protected final String TAG = "HomePageAdapter";

	private Context context;
	private ArrayList<ContactBean> list;

	public ChatContactListAdapter(Context context, ArrayList<ContactBean> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 重新加载数据
	 * 
	 * @param list
	 */
	public void refreshData(ArrayList<ContactBean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_chat_contact, null);
			holder.iv_icon = (ImageView) convertView
					.findViewById(R.id.game_head);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.game_name);
			holder.tv_context = (TextView) convertView
					.findViewById(R.id.game_context);
			holder.tv_year = (TextView) convertView
					.findViewById(R.id.game_year);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final ContactBean gb = list.get(position);
		final LoginBean member = gb.getMember();
		App.app.showUrlHeader(holder.iv_icon,
				member.getHead_url() + member.getHead_path());
		holder.tv_name.setText(member.getMember_name());
		holder.tv_context.setText(member.getMember_mood());
		String time = member.getUpdate_time();
		holder.tv_year.setText(time.substring(time.indexOf("-") + 1,
				time.lastIndexOf(":")));

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, ChatActivity.class);
				i.putExtra("LoginBean", member);
				context.startActivity(i);
			}
		});

		return convertView;
	}

	private class ViewHolder {
		ImageView iv_icon;
		TextView tv_name, tv_context, tv_year;
	}

}