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

import com.yikeguan.boardgamestore.GameDetailActivity;
import com.yikeguan.boardgamestore.R;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.GameBean;

public class GameListAdapter extends BaseAdapter {
	protected final String TAG = "HomePageAdapter";

	private Context context;
	private ArrayList<GameBean> list;

	public GameListAdapter(Context context, ArrayList<GameBean> list) {
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
	public void refreshData(ArrayList<GameBean> list) {
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
					R.layout.item_game, null);
			holder.iv_icon = (ImageView) convertView
					.findViewById(R.id.game_head);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.game_name);
			holder.tv_type = (TextView) convertView
					.findViewById(R.id.game_type);
			holder.tv_year = (TextView) convertView
					.findViewById(R.id.game_year);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final GameBean gb = list.get(position);

		App.app.showUrlGame(holder.iv_icon, gb.getShortcut_image_path());

		holder.tv_name.setText(gb.getMain_game_name());
		holder.tv_type.setText(gb.getSubdomain());
		holder.tv_year.setText(gb.getYear_published());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, GameDetailActivity.class);
				i.putExtra("GameBean", gb);
				context.startActivity(i);
			}
		});

		return convertView;
	}

	private class ViewHolder {
		ImageView iv_icon;
		TextView tv_name, tv_type, tv_year;
	}

}
