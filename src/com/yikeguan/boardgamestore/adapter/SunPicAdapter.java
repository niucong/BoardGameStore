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

import com.yikeguan.boardgamestore.LoginActivity;
import com.yikeguan.boardgamestore.MemberDetailActivity;
import com.yikeguan.boardgamestore.R;
import com.yikeguan.boardgamestore.SunpicInfoActivity;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.LoginBean;
import com.yikeguan.boardgamestore.response.SunpicInfoBean;
import com.yikeguan.boardgamestore.resquest.FlowMemberParams;
import com.yikeguan.boardgamestore.util.DateTimeUtil;
import com.yikeguan.boardgamestore.view.MixedTextView;

public class SunPicAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<SunpicInfoBean> list;
	private ViewHolder holder = null;
	private int type;
	String myId;

	public SunPicAdapter(Context context, ArrayList<SunpicInfoBean> list) {
		this.context = context;
		this.list = list;
		myId = App.app.preferences.getStringMessage("app", "MyId", "0");
	}

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
	public void refreshData(ArrayList<SunpicInfoBean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_square, null);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.item_square_name);
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.item_square_time);
			holder.tv_context = (MixedTextView) convertView
					.findViewById(R.id.item_square_context);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.item_square_title);

			holder.iv_attention = (ImageView) convertView
					.findViewById(R.id.item_square_attention);
			holder.iv_head = (ImageView) convertView
					.findViewById(R.id.item_square_head);
			// holder.iv_div = convertView.findViewById(R.id.item_square_div);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final SunpicInfoBean bean = list.get(position);
		final LoginBean member = bean.getMember();
		holder.tv_name.setText(member.getMember_name());
		App.app.showUrlHeader(holder.iv_head,
				member.getHead_url() + member.getHead_path());
		holder.tv_time.setText(DateTimeUtil.cTimeFormat(bean.getCreate_time()));
		holder.tv_title.setText(bean.getObj_name());
		holder.tv_context.setFaceText(bean.getObj_desc());

		holder.iv_head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, MemberDetailActivity.class);
				i.putExtra("LoginBean", member);
				context.startActivity(i);
			}
		});

		if ("true".equals(member.getIsFlow())
				|| myId.equals(member.getMember_id()))
			holder.iv_attention.setVisibility(View.GONE);
		else
			holder.iv_attention.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if ("0".equals(App.app.preferences.getStringMessage("app",
							"MyId", "0"))) {
						context.startActivity(new Intent(context,
								LoginActivity.class));
					} else {
						v.setVisibility(View.GONE);
						notifyDataSetChanged();
						new Thread() {
							public void run() {
								try {
									new FlowMemberParams(App.app, member
											.getMember_id(), true).getResult();
								} catch (Exception e) {
									e.printStackTrace();
								}
								member.setIsFlow("true");
							};
						}.start();
					}
				}
			});

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(context, SunpicInfoActivity.class);
				it.putExtra("SunpicInfoBean", bean);
				it.putExtra("type", type);
				context.startActivity(it);
			}
		});

		return convertView;
	}

	private class ViewHolder {
		MixedTextView tv_context;
		TextView tv_name, tv_time, tv_title;
		ImageView iv_head, iv_attention;
		// View iv_div;
	}

}
