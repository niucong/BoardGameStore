package com.yikeguan.boardgamestore.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yikeguan.boardgamestore.LoginActivity;
import com.yikeguan.boardgamestore.MemberDetailActivity;
import com.yikeguan.boardgamestore.R;
import com.yikeguan.boardgamestore.SunpicInfoActivity;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.CommentBean;
import com.yikeguan.boardgamestore.response.LoginBean;
import com.yikeguan.boardgamestore.response.ResourceBean;
import com.yikeguan.boardgamestore.response.ResourceInfoBean;
import com.yikeguan.boardgamestore.response.StatisInfoBean;
import com.yikeguan.boardgamestore.response.SunpicInfoBean;
import com.yikeguan.boardgamestore.response.TrendBean;
import com.yikeguan.boardgamestore.resquest.FlowMemberParams;
import com.yikeguan.boardgamestore.util.DateTimeUtil;
import com.yikeguan.boardgamestore.util.L;
import com.yikeguan.boardgamestore.view.MixedTextView;

public class HomePageAdapter extends BaseAdapter {
	protected final String TAG = "HomePageAdapter";

	private Context context;
	private ArrayList<TrendBean> list;
	private ImageView shareImg;
	private ViewHolder holder = null;

	String myId;

	public HomePageAdapter(Context context, ArrayList<TrendBean> list) {
		this.context = context;
		this.list = list;
		myId = App.app.preferences.getStringMessage("app", "MyId", "0");
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
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_home_page, null);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.item_home_page_name);
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.item_home_page_time);
			holder.tv_context = (MixedTextView) convertView
					.findViewById(R.id.item_home_page_context);
			holder.tv_more = (TextView) convertView
					.findViewById(R.id.item_home_page_more);
			holder.tv_context1 = (MixedTextView) convertView
					.findViewById(R.id.item_home_page_context1);
			holder.tv_context2 = (MixedTextView) convertView
					.findViewById(R.id.item_home_page_context2);
			holder.tv_type = (TextView) convertView
					.findViewById(R.id.item_home_page_type);
			holder.tv_num = (TextView) convertView
					.findViewById(R.id.item_home_page_browse);

			holder.iv_attention = (ImageView) convertView
					.findViewById(R.id.item_home_page_attention);
			holder.iv_head = (ImageView) convertView
					.findViewById(R.id.item_home_page_head);
			holder.iv_share = (ImageView) convertView
					.findViewById(R.id.item_home_page_share);
			holder.iv_message = (ImageView) convertView
					.findViewById(R.id.item_home_page_message);
			holder.iv1 = (ImageView) convertView
					.findViewById(R.id.item_home_page_iv1);
			holder.iv2 = (ImageView) convertView
					.findViewById(R.id.item_home_page_iv2);
			holder.iv3 = (ImageView) convertView
					.findViewById(R.id.item_home_page_iv3);
			holder.ll_ivs = (LinearLayout) convertView
					.findViewById(R.id.item_home_page_ivs);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final TrendBean bean = list.get(position);
		final LoginBean member = bean.getMember();
		L.d(TAG, "mId=" + member.getMember_id());
		holder.tv_name.setText(member.getMember_name());
		App.app.showUrlHeader(holder.iv_head,
				member.getHead_url() + member.getHead_path());
		holder.tv_time.setText(DateTimeUtil.cTimeFormat(bean.getCreate_time()));

		StatisInfoBean si = bean.getStatisInfo();
		holder.tv_num.setText("浏览 " + si.getVisit_num());
		if (si.getComment_num() > 0) {
			holder.tv_more.setText("查看所有 " + si.getComment_num() + " 条评论");
		} else
			holder.tv_more.setText("暂无评论");

		if ("RECOMMEND".equals(bean.getData_type())) {
			holder.tv_type.setCompoundDrawablesWithIntrinsicBounds(context
					.getResources().getDrawable(R.drawable.qiu), null, null,
					null);
		} else {
			holder.tv_type.setCompoundDrawablesWithIntrinsicBounds(context
					.getResources().getDrawable(R.drawable.shai), null, null,
					null);
		}

		ResourceInfoBean resourceInfo = bean.getResourceInfo();
		final SunpicInfoBean object = resourceInfo.getObject();
		List<CommentBean> comments = resourceInfo.getComments();
		if (comments != null && comments.size() > 0) {
			holder.tv_context1.setVisibility(View.VISIBLE);
			holder.tv_context1.setFaceText(comments.get(0).getMember()
					.getMember_name()
					+ ": " + comments.get(0).getComment_content());
			if (comments.size() > 1) {
				holder.tv_context2.setVisibility(View.VISIBLE);
				holder.tv_context2.setFaceText(comments.get(1).getMember()
						.getMember_name()
						+ ": " + comments.get(1).getComment_content());
			} else {
				holder.tv_context2.setVisibility(View.GONE);
			}
		} else {
			holder.tv_context1.setVisibility(View.GONE);
			holder.tv_context2.setVisibility(View.GONE);
		}

		holder.tv_context.setFaceText(object.getObj_desc());
		holder.tv_type.setText("\t\t" + object.getObj_name());
		List<ResourceBean> resources = object.getResources();
		if (resources != null && resources.size() > 0) {
			holder.ll_ivs.setVisibility(View.VISIBLE);
			ResourceBean rb = resources.get(0);
			App.app.showUrlImg(holder.iv1,
					rb.getResource_url() + rb.getResource_path());
			if (resources.size() > 1) {
				holder.iv2.setVisibility(View.VISIBLE);
				holder.iv3.setVisibility(View.VISIBLE);
				ResourceBean rb1 = resources.get(1);
				App.app.showUrlImg(holder.iv2,
						rb1.getResource_url() + rb1.getResource_path());
				if (resources.size() > 2) {
					holder.iv3.setVisibility(View.VISIBLE);
					ResourceBean rb2 = resources.get(2);
					App.app.showUrlImg(holder.iv3,
							rb2.getResource_url() + rb2.getResource_path());
				} else {
					holder.iv3.setVisibility(View.INVISIBLE);
				}
			} else {
				holder.iv2.setVisibility(View.INVISIBLE);
				holder.iv3.setVisibility(View.INVISIBLE);
			}
		} else {
			holder.ll_ivs.setVisibility(View.GONE);
		}

		holder.iv_head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, MemberDetailActivity.class);
				i.putExtra("LoginBean", member);
				context.startActivity(i);
			}
		});

		holder.iv_message.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// context.startActivity(new Intent(context,
				// ChatActivity.class));
				if ("0".equals(App.app.preferences.getStringMessage("app",
						"MyId", "0")))
					context.startActivity(new Intent(context,
							LoginActivity.class));
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

		// holder.iv1.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// context.startActivity(new Intent(context,
		// ImageViewActivity.class));
		// }
		// });

		holder.iv_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				shareImg = holder.iv1;
				share(holder.tv_context.getText().toString(), true);
			}
		});

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent is = new Intent(context, SunpicInfoActivity.class);
				object.setMember(member);
				is.putExtra("SunpicInfoBean", object);
				if ("SUN".equals(bean.getData_type())) {
					is.putExtra("type", 1);
				}
				context.startActivity(is);
			}
		});

		return convertView;
	}

	private class ViewHolder {
		MixedTextView tv_context, tv_context1, tv_context2;
		TextView tv_name, tv_time, tv_more, tv_type, tv_num;
		ImageView iv_head, iv_share, iv_message, iv1, iv2, iv3, iv_attention;
		LinearLayout ll_ivs;
	}

	private void share(String contextStr, boolean sharePhoto) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		if (sharePhoto) {
			intent.setType("image/*");
			try {
				Uri u = Uri.parse(MediaStore.Images.Media.insertImage(
						context.getContentResolver(),
						shareImg.getDrawingCache(), null, null));
				intent.putExtra(Intent.EXTRA_STREAM, u);
			} catch (Exception e) {
				e.printStackTrace();
			}
			intent.putExtra(Intent.EXTRA_TEXT, contextStr);
		} else {
			intent.setType("text/*");
			// intent.putExtra(Intent.EXTRA_SUBJECT, titleStr);
			intent.putExtra(Intent.EXTRA_TEXT, contextStr);
		}

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent,
				((Activity) context).getTitle()));
	}

}
