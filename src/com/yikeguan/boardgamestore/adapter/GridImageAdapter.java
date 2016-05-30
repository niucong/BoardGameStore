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
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.yikeguan.boardgamestore.GameDetailActivity;
import com.yikeguan.boardgamestore.GameListActivity;
import com.yikeguan.boardgamestore.R;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.DesignerBean;
import com.yikeguan.boardgamestore.response.DetailedImageBean;
import com.yikeguan.boardgamestore.response.GameBean;
import com.yikeguan.boardgamestore.response.GamesBean;
import com.yikeguan.boardgamestore.response.PublisherBean;

public class GridImageAdapter extends BaseAdapter {
	protected final String TAG = "HomePageAdapter";

	private Context context;
	private ArrayList<Object> list;

	// private int itemWidth;
	/**
	 * 0：出版社 1：设计师 2：游戏墙 3:图片 4：我的游戏墙
	 */
	private int type;

	public GridImageAdapter(Context context, ArrayList<Object> list) {
		this.context = context;
		this.list = list;

		// itemWidth = App.app.preferences.getIntMessage("program",
		// "screen_width", -1);
	}

	/**
	 * 0：出版社 1：设计师 2：游戏墙
	 */
	public void setType(int type) {
		this.type = type;
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
	public void refreshData(ArrayList<Object> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			if (type == 1) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_grid_image, null);
			} else if (type == 3) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_grid_photo, null);
			} else {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_grid_game, null);
			}
			holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			if (type == 2 || type == 4)
				holder.iv_icon.setScaleType(ScaleType.CENTER_CROP);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (type == 0) {
			PublisherBean pb = (PublisherBean) list.get(position);
			App.app.showUrlPublish(holder.iv_icon, pb.getShortcut_image_path());
			holder.tv_name.setVisibility(View.VISIBLE);
			holder.tv_name.setText(pb.getPublisher_name());
		} else if (type == 1) {
			DesignerBean db = (DesignerBean) list.get(position);
			App.app.showUrlHeader(holder.iv_icon, db.getShortcut_image_path());
			holder.tv_name.setVisibility(View.VISIBLE);
			holder.tv_name.setText(db.getDesigner_name());
		} else if (type == 3) {
			DetailedImageBean db = (DetailedImageBean) list.get(position);
			App.app.showUrlImg(holder.iv_icon,
					db.getImage_url() + db.getImage_path());
			// holder.tv_name.setText(db.getDesigner_name());
		} else {
			GameBean gb;
			if (list.get(position) instanceof GamesBean) {
				gb = ((GamesBean) list.get(position)).getGame();
			} else {
				gb = (GameBean) list.get(position);
			}
			App.app.showUrlGame(holder.iv_icon, gb.getShortcut_image_path());
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (type == 0) {
					PublisherBean pb = (PublisherBean) list.get(position);
					Intent pi = new Intent(context, GameListActivity.class);
					pi.putExtra("PublisherBean", pb);
					context.startActivity(pi);
				} else if (type == 1) {
					DesignerBean db = (DesignerBean) list.get(position);
					Intent di = new Intent(context, GameListActivity.class);
					di.putExtra("DesignerBean", db);
					context.startActivity(di);
				} else if (type == 3) {
				} else {
					GameBean gb;
					if (list.get(position) instanceof GamesBean) {
						GamesBean gbs = (GamesBean) list.get(position);
						gb = gbs.getGame();
						gb.setData_id(gbs.getData_id());
					} else {
						gb = (GameBean) list.get(position);
					}
					Intent i = new Intent(context, GameDetailActivity.class);
					i.putExtra("GameBean", gb);
					context.startActivity(i);
				}
			}
		});

		return convertView;
	}

	private class ViewHolder {
		ImageView iv_icon;
		TextView tv_name;
	}

}
