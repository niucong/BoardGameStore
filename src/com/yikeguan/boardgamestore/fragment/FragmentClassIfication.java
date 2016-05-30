package com.yikeguan.boardgamestore.fragment;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yikeguan.boardgamestore.GameListActivity;
import com.yikeguan.boardgamestore.MainActivity;
import com.yikeguan.boardgamestore.PublishListActivity;
import com.yikeguan.boardgamestore.R;
import com.yikeguan.boardgamestore.SearchActivity;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.DesignerBean;
import com.yikeguan.boardgamestore.response.DesignerListBean;
import com.yikeguan.boardgamestore.response.PublisherBean;
import com.yikeguan.boardgamestore.response.PublisherListBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.game.GameHotDesignerListParams;
import com.yikeguan.boardgamestore.resquest.game.GameHotPublisherListParams;
import com.yikeguan.boardgamestore.view.MyPagerGalleryView;
import com.yikeguan.boardgamestore.view.MyPagerGalleryView.MyOnItemClickListener;
import com.yikeguan.boardgamestore.view.RoundImageView;

public class FragmentClassIfication extends FragmentBasic implements
		OnClickListener {

	// 广告控件
	private MyPagerGalleryView gallery;
	// 圆点容器
	private LinearLayout ovalLayout;
	// 图片上面的文字
	private TextView adgallerytxt;
	/** 图片id的数组,本地测试用 */
	private int[] imageId = new int[] { R.drawable.ad, R.drawable.ad };
	/** 图片网络路径数组 */
	private String[] urlImageList = { "", "", "" };
	private String[] txtViewpager = { "", "", "" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		// ((MainActivity) getActivity()).iv_title_left
		// .setImageResource(R.drawable.icon_menu);

		((MainActivity) getActivity()).iv_title_right
				.setImageResource(R.drawable.icon_search);
		((MainActivity) getActivity()).tv_title.setText("分类");

		((MainActivity) getActivity()).iv_title_right
				.setVisibility(View.VISIBLE);
		((MainActivity) getActivity()).iv_title_right.setOnClickListener(this);

		View view = inflater.inflate(R.layout.fragment_class_ification, null);
		ll_fragment.addView(view);

		initView(view);

		gallery = (MyPagerGalleryView) view.findViewById(R.id.adgallery);
		ovalLayout = (LinearLayout) view.findViewById(R.id.ovalLayout1);// 获取圆点容器
		adgallerytxt = (TextView) view.findViewById(R.id.adgallerytxt);
		adgallerytxt.setVisibility(View.GONE);

		gallery.start(getActivity(), null, imageId, 3000, ovalLayout,
				R.drawable.dot_focused, R.drawable.dot_normal, adgallerytxt,
				null);
		// 添加点击事件的监听
		gallery.setMyOnItemClickListener(new MyOnItemClickListener() {
			public void onItemClick(int curIndex) {
				// TODO
			}
		});

		return v;
	}

	private ImageView iv_p1, iv_p2, iv_p3, iv_p4, iv_p5, iv_p6, iv_p7, iv_p8;
	private RoundImageView iv_d1, iv_d2, iv_d3, iv_d4, iv_d5, iv_d6, iv_d7,
			iv_d8;
	private View pv, dv;

	private void initView(View child) {
		// child.findViewById(R.id.img_class0).setOnClickListener(this);
		child.findViewById(R.id.img_class1).setOnClickListener(this);
		child.findViewById(R.id.img_class2).setOnClickListener(this);
		child.findViewById(R.id.img_class3).setOnClickListener(this);
		child.findViewById(R.id.img_class4).setOnClickListener(this);
		child.findViewById(R.id.img_class5).setOnClickListener(this);
		child.findViewById(R.id.img_class6).setOnClickListener(this);
		child.findViewById(R.id.img_class7).setOnClickListener(this);
		child.findViewById(R.id.img_class8).setOnClickListener(this);

		iv_p1 = (ImageView) child.findViewById(R.id.img_publish1);
		iv_p2 = (ImageView) child.findViewById(R.id.img_publish2);
		iv_p3 = (ImageView) child.findViewById(R.id.img_publish3);
		iv_p4 = (ImageView) child.findViewById(R.id.img_publish4);
		iv_p5 = (ImageView) child.findViewById(R.id.img_publish5);
		iv_p6 = (ImageView) child.findViewById(R.id.img_publish6);
		iv_p7 = (ImageView) child.findViewById(R.id.img_publish7);
		iv_p8 = (ImageView) child.findViewById(R.id.img_publish8);

		iv_d1 = (RoundImageView) child.findViewById(R.id.img_design1);
		iv_d2 = (RoundImageView) child.findViewById(R.id.img_design2);
		iv_d3 = (RoundImageView) child.findViewById(R.id.img_design3);
		iv_d4 = (RoundImageView) child.findViewById(R.id.img_design4);
		iv_d5 = (RoundImageView) child.findViewById(R.id.img_design5);
		iv_d6 = (RoundImageView) child.findViewById(R.id.img_design6);
		iv_d7 = (RoundImageView) child.findViewById(R.id.img_design7);
		iv_d8 = (RoundImageView) child.findViewById(R.id.img_design8);

		pv = child.findViewById(R.id.img_publish_more);
		dv = child.findViewById(R.id.img_design_more);
		pv.setOnClickListener(this);
		dv.setOnClickListener(this);
		pv.setVisibility(View.GONE);
		dv.setVisibility(View.GONE);

		new GamePublisherListTask().execute();
		new GameDesignerListTask().execute();
	}

	@Override
	public void onClick(View v) {
		Intent i = null;
		switch (v.getId()) {
		case R.id.iv_title_right:
			getActivity().startActivity(
					new Intent(getActivity(), SearchActivity.class));
			break;
		// case R.id.img_class0:
		case R.id.img_class1:
			i = new Intent(getActivity(), GameListActivity.class);
			i.putExtra("game_type", "war_military");// Wargames
			getActivity().startActivity(i);
			break;
		case R.id.img_class2:
			i = new Intent(getActivity(), GameListActivity.class);
			i.putExtra("game_type", "happy_party");// Party Games
			getActivity().startActivity(i);
			break;
		case R.id.img_class3:
			i = new Intent(getActivity(), GameListActivity.class);
			i.putExtra("game_type", "abstract_strategy");// Abstract Games
			getActivity().startActivity(i);
			break;
		case R.id.img_class4:
			i = new Intent(getActivity(), GameListActivity.class);
			i.putExtra("game_type", "economic_finance");// Economic
			getActivity().startActivity(i);
			break;
		case R.id.img_class5:
			i = new Intent(getActivity(), GameListActivity.class);
			i.putExtra("game_type", "reasoning_holmes");// Deduction
			getActivity().startActivity(i);
			break;
		case R.id.img_class6:
			i = new Intent(getActivity(), GameListActivity.class);
			i.putExtra("game_type", "history_politics");// Political
			getActivity().startActivity(i);
			break;
		case R.id.img_class7:
			i = new Intent(getActivity(), GameListActivity.class);
			i.putExtra("game_type", "action_response");// Action / Dexterity
			getActivity().startActivity(i);
			break;
		case R.id.img_class8:
			i = new Intent(getActivity(), GameListActivity.class);
			i.putExtra("game_type", "child_game");// Children's Games
			getActivity().startActivity(i);
			break;
		case R.id.img_publish1:
		case R.id.img_publish2:
		case R.id.img_publish3:
		case R.id.img_publish4:
		case R.id.img_publish5:
		case R.id.img_publish6:
		case R.id.img_publish7:
		case R.id.img_publish8:
			PublisherBean pb = (PublisherBean) v.getTag();
			Intent pi = new Intent(getActivity(), GameListActivity.class);
			pi.putExtra("PublisherBean", pb);
			getActivity().startActivity(pi);
			break;
		case R.id.img_design1:
		case R.id.img_design2:
		case R.id.img_design3:
		case R.id.img_design4:
		case R.id.img_design5:
		case R.id.img_design6:
		case R.id.img_design7:
		case R.id.img_design8:
			DesignerBean db = (DesignerBean) v.getTag();
			Intent di = new Intent(getActivity(), GameListActivity.class);
			di.putExtra("DesignerBean", db);
			getActivity().startActivity(di);
			break;
		case R.id.img_publish_more:
			i = new Intent(getActivity(), PublishListActivity.class);
			i.putExtra("Type", 0);
			i.putExtra("PublisherListBean", pbean);
			getActivity().startActivity(i);
			break;
		case R.id.img_design_more:
			i = new Intent(getActivity(), PublishListActivity.class);
			i.putExtra("Type", 1);
			i.putExtra("DesignerListBean", dbean);
			getActivity().startActivity(i);
			break;

		default:
			break;
		}
	}

	DesignerListBean dbean;

	private class GameDesignerListTask extends
			AsyncTask<String, Integer, ResultModel> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new GameHotDesignerListParams(App.app, "", "0", "8")
						.getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				dbean = (DesignerListBean) result.getResult();
				if (dbean != null) {
					List<DesignerBean> designers = dbean.getDesigners();
					if (designers != null && designers.size() > 0) {
						ImageView[] ivs = { iv_d1, iv_d2, iv_d3, iv_d4, iv_d5,
								iv_d6, iv_d7, iv_d8 };
						for (int i = 0; i < designers.size(); i++) {
							setData(ivs[i], designers.get(i));
						}
					}
					dv.setVisibility(View.VISIBLE);
				}
			} else {
				// TODO
			}
		}

		private void setData(ImageView iv, DesignerBean pb) {
			App.app.showUrlHeader(iv, pb.getShortcut_image_path());
			iv.setOnClickListener(FragmentClassIfication.this);
			iv.setTag(pb);
		}

		@Override
		protected void onCancelled() {
		}
	}

	PublisherListBean pbean;

	private class GamePublisherListTask extends
			AsyncTask<String, Integer, ResultModel> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new GameHotPublisherListParams(App.app, "", "0", "8")
						.getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				pbean = (PublisherListBean) result.getResult();
				if (pbean != null) {
					List<PublisherBean> publishers = pbean.getPublishers();
					if (publishers != null && publishers.size() > 0) {
						ImageView[] ivs = { iv_p1, iv_p2, iv_p3, iv_p4, iv_p5,
								iv_p6, iv_p7, iv_p8 };
						for (int i = 0; i < publishers.size(); i++) {
							setData(ivs[i], publishers.get(i));
						}
					}
					pv.setVisibility(View.VISIBLE);
				}
			} else {
				// TODO
			}
		}

		private void setData(ImageView iv, PublisherBean pb) {
			App.app.showUrlPublish(iv, pb.getShortcut_image_path());
			iv.setOnClickListener(FragmentClassIfication.this);
			iv.setTag(pb);
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			// sDialog.cancelProgressDialog("");
		}
	}
}