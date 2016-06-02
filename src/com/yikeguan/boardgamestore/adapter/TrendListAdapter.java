package com.yikeguan.boardgamestore.adapter;

import java.util.ArrayList;
import java.util.List;

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
import com.yikeguan.boardgamestore.MemberDetailActivity;
import com.yikeguan.boardgamestore.R;
import com.yikeguan.boardgamestore.SunpicInfoActivity;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.CommentBean;
import com.yikeguan.boardgamestore.response.GameBean;
import com.yikeguan.boardgamestore.response.LoginBean;
import com.yikeguan.boardgamestore.response.ResourceInfoBean;
import com.yikeguan.boardgamestore.response.SunpicInfoBean;
import com.yikeguan.boardgamestore.response.TrendBean;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.DateTimeUtil;
import com.yikeguan.boardgamestore.view.MixedTextView;

public class TrendListAdapter extends BaseAdapter {
	protected final String TAG = "HomePageAdapter";

	private Context context;
	private ArrayList<TrendBean> list;
	// 0：我的回复、1：我的浏览、2：我的消息-个人
	private int type;

	public TrendListAdapter(Context context, ArrayList<TrendBean> list, int type) {
		this.context = context;
		this.list = list;
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
	public void refreshData(ArrayList<TrendBean> list) {
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
					R.layout.item_trend, null);
			holder.iv_icon = (ImageView) convertView
					.findViewById(R.id.item_trend_icon);
			holder.iv_head = (ImageView) convertView
					.findViewById(R.id.item_trend_head);
			holder.iv_game = (ImageView) convertView
					.findViewById(R.id.item_trend_game);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.item_trend_name);
			holder.tv_context = (TextView) convertView
					.findViewById(R.id.item_trend_context);
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.item_trend_time);
			holder.tv_repet = (MixedTextView) convertView
					.findViewById(R.id.item_trend_repet);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final TrendBean bean = list.get(position);
		final LoginBean member = bean.getMember();

		holder.tv_name.setText(member.getMember_name());
		holder.tv_time.setText(DateTimeUtil.cTimeFormat(bean.getUpdate_time()));
		ResourceInfoBean resourceInfo = bean.getResourceInfo();
		SunpicInfoBean object;
		if (resourceInfo != null) {
			object = resourceInfo.getObject();
		} else {
			object = bean.getObject();
		}
		final SunpicInfoBean obj = object;
		holder.tv_context.setText(object.getObj_name());
		if (type == 0) {
			String reStr = "";
			try {
				List<CommentBean> comments = resourceInfo.getComments();
				reStr = comments.get(0).getComment_content();
			} catch (Exception e) {
				e.printStackTrace();
			}
			holder.tv_repet.setFaceText("我的回复：" + reStr);

			holder.iv_icon.setVisibility(View.GONE);
			holder.iv_head.setVisibility(View.GONE);
			holder.iv_game.setVisibility(View.GONE);
			holder.tv_name.setVisibility(View.GONE);
			holder.tv_time.setVisibility(View.GONE);
			holder.tv_repet.setVisibility(View.GONE);

		} else if (type == 1) {
			holder.tv_repet.setVisibility(View.GONE);
		} else if (type == 2) {
			holder.tv_context.setText("评论我的：" + object.getObj_name());
			holder.tv_repet.setFaceText("评论：" + bean.getNotice_content());
		} else {
			holder.tv_repet.setFaceText(object.getObj_desc());
		}

		if ("GAME".equals(bean.getData_type())) {
			holder.iv_icon.setImageResource(R.drawable.you);
			holder.tv_context.setText(object.getMain_game_name());
			holder.iv_head.setVisibility(View.GONE);
			if (type != 0)
				holder.iv_game.setVisibility(View.VISIBLE);
			App.app.showUrlGame(holder.iv_game, ConstantUtil.YKG_IMG_BGGIMG
					+ object.getShortcut_image_path());
		} else {
			if ("RECOMMEND".equals(bean.getData_type())) {
				holder.iv_icon.setImageResource(R.drawable.qiu);
			} else {
				holder.iv_icon.setImageResource(R.drawable.shai);
			}
			if (type != 0)
				holder.iv_head.setVisibility(View.VISIBLE);
			holder.iv_game.setVisibility(View.GONE);
			App.app.showUrlHeader(holder.iv_head,
					member.getHead_url() + member.getHead_path());
			holder.iv_head.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(context, MemberDetailActivity.class);
					i.putExtra("LoginBean", member);
					context.startActivity(i);
				}
			});
		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("GAME".equals(bean.getData_type())) {
					Intent is = new Intent(context, GameDetailActivity.class);
					GameBean gb = new GameBean();
					gb.setBgg_game_id(obj.getBgg_game_id());
					gb.setMain_game_name(obj.getMain_game_name());
					gb.setShortcut_image_path(obj.getShortcut_image_path());
					gb.setSubdomain(obj.getSubdomain());
					gb.setYear_published(obj.getYear_published());
					gb.setDesigner(obj.getDesigner());
					gb.setMin_player_number(obj.getMin_player_number());
					gb.setMax_player_number(obj.getMax_player_number());
					gb.setMin_age(obj.getMin_age());
					gb.setPlay_time(obj.getPlay_time());
					gb.setStat_with_subdomain_type(obj
							.getStat_with_subdomain_type());
					gb.setMechanic(obj.getMechanic());
					gb.setDetailed_description(obj.getDetailed_description());

					is.putExtra("GameBean", gb);
					context.startActivity(is);
				} else {
					Intent is = new Intent(context, SunpicInfoActivity.class);
					obj.setMember(member);
					is.putExtra("SunpicInfoBean", obj);
					if ("SUN".equals(bean.getData_type())) {
						is.putExtra("type", 1);
					}
					context.startActivity(is);
				}
			}
		});
		return convertView;
	}

	private class ViewHolder {
		ImageView iv_icon, iv_head, iv_game;
		TextView tv_name, tv_time, tv_context;
		MixedTextView tv_repet;
	}

}
