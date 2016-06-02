package com.yikeguan.boardgamestore;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.CategoryBean;
import com.yikeguan.boardgamestore.response.DesignerBean;
import com.yikeguan.boardgamestore.response.DetailedImageBean;
import com.yikeguan.boardgamestore.response.GameBean;
import com.yikeguan.boardgamestore.response.MechanicBean;
import com.yikeguan.boardgamestore.response.PublisherBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.game.FlowGameParams;
import com.yikeguan.boardgamestore.resquest.game.GetGameInfoParams;

public class GameDetailActivity extends BasicActivity implements
		OnClickListener {

	private TextView tv_title, tv_right;

	private RatingBar rb;

	private GameBean gb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_detail);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		gb = (GameBean) getIntent().getSerializableExtra("GameBean");
		if (gb == null)
			finish();
		ImageView iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		iv_title_left.setImageResource(R.drawable.icon_back);
		iv_title_left.setOnClickListener(this);

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("游戏详情");

		tv_right = (TextView) findViewById(R.id.tv_right);

		findViewById(R.id.game_detail_price).setOnClickListener(this);
		findViewById(R.id.game_detail_comment).setOnClickListener(this);

		rb = (RatingBar) findViewById(R.id.game_detail_rb);
		rb.setEnabled(false);

		App.app.showUrlGame((ImageView) findViewById(R.id.game_detail_iv),
				gb.getShortcut_image_path());

		((TextView) findViewById(R.id.game_detail_name)).setText(gb
				.getMain_game_name());
		((TextView) findViewById(R.id.game_detail_year)).setText(gb
				.getYear_published() + "年");

		String num = "未知";
		if (!"-1".equals(gb.getMin_player_number()))
			num = gb.getMin_player_number() + " ~ " + gb.getMax_player_number()
					+ "人";
		((TextView) findViewById(R.id.game_detail_num)).setText(num);

		String age = "未知";
		if (!"-1".equals(gb.getMin_age()))
			age = gb.getMin_age() + "岁以上";
		((TextView) findViewById(R.id.game_detail_age)).setText(age);

		String time = "未知";
		if (!"-1".equals(gb.getPlay_time()))
			time = gb.getPlay_time() + "分钟";
		((TextView) findViewById(R.id.game_detail_time)).setText(time);

		((TextView) findViewById(R.id.game_detail_context)).setText(gb
				.getDetailed_description());

		new GameInfoTask().execute();
	}

	private class GameInfoTask extends AsyncTask<String, Integer, ResultModel> {

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;
			try {
				mc = new GetGameInfoParams(App.app, "" + gb.getData_id())
						.getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(ResultModel result) {
			if (result != null && result.getCode() == 1) {
				gb = (GameBean) result.getResult();
				if (gb != null) {
					if ("true".equals(gb.getIsFlow())) {
						tv_right.setText("取消关注");
					} else {
						tv_right.setText("关注");
					}
					tv_right.setOnClickListener(GameDetailActivity.this);

					float rating = 0;
					try {
						rating = gb.getGrade_totle_num()
								/ gb.getGrade_member_num();
					} catch (Exception e) {
						e.printStackTrace();
					}
					rb.setRating(rating / 2);
					findViewById(R.id.game_detail_rbl).setOnClickListener(
							GameDetailActivity.this);

					String dStr = "";
					List<DesignerBean> designers = gb.getDesigners();
					SpannableStringBuilder ssd = new SpannableStringBuilder();
					if (designers != null)
						for (DesignerBean d : designers) {
							// dStr += d.getDesigner_name() + "\n";
							String dName = d.getDesigner_name() + "\n";
							ssd.append(dName);
							ssd.setSpan(
									new MySpan("1#" + d.getBgg_designer_id()),
									ssd.length() - dName.length(),
									ssd.length(),
									Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						}
					if (dStr.length() > 1)
						dStr = dStr.substring(0, dStr.length() - 1);
					TextView tv_d = (TextView) findViewById(R.id.game_detail_design);
					tv_d.setText(ssd);
					tv_d.setMovementMethod(LinkMovementMethod.getInstance());

					String pStr = "";
					List<PublisherBean> publishers = gb.getPublishers();
					SpannableStringBuilder ssp = new SpannableStringBuilder();
					if (publishers != null)
						for (PublisherBean d : publishers) {
							// pStr += d.getPublisher_name() + "\n";
							String pName = d.getPublisher_name() + "\n";
							ssp.append(pName);
							ssp.setSpan(
									new MySpan("0#" + d.getBgg_publisher_id()),
									ssp.length() - pName.length(),
									ssp.length(),
									Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						}
					if (pStr.length() > 1)
						pStr = pStr.substring(0, pStr.length() - 1);
					TextView tv_p = (TextView) findViewById(R.id.game_detail_publish);
					tv_p.setText(ssp);
					tv_p.setMovementMethod(LinkMovementMethod.getInstance());

					String cStr = "";
					List<CategoryBean> categorys = gb.getCategorys();
					if (categorys != null)
						for (CategoryBean d : categorys) {
							cStr += d.getCategory_name() + "\n";
						}
					if (cStr.length() > 1)
						cStr = cStr.substring(0, cStr.length() - 1);
					((TextView) findViewById(R.id.game_detail_level))
							.setText(cStr);

					String mStr = "";
					List<MechanicBean> mechanics = gb.getMechanics();
					if (mechanics != null)
						for (MechanicBean d : mechanics) {
							mStr += d.getMechanic_name() + "\n";
						}
					if (mStr.length() > 1)
						mStr = mStr.substring(0, mStr.length() - 1);
					((TextView) findViewById(R.id.game_detail_mechanism))
							.setText(mStr);

					final List<DetailedImageBean> detailedImages = gb
							.getDetailedImages();
					LinearLayout ll_photos = (LinearLayout) findViewById(R.id.game_detail_game);
					if (detailedImages != null) {
						int ds = detailedImages.size();
						if (ds > 0) {
							ll_photos.setVisibility(View.VISIBLE);
							ll_photos.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									Intent intent = new Intent(
											GameDetailActivity.this,
											PublishListActivity.class);
									intent.putExtra("GameBean", gb);
									intent.putExtra("Type", 3);
									startActivity(intent);
								}
							});

							setImage(detailedImages.get(0),
									R.id.game_detail_game1);
							if (ds > 1) {
								setImage(detailedImages.get(1),
										R.id.game_detail_game2);
								if (ds > 2) {
									setImage(detailedImages.get(2),
											R.id.game_detail_game3);
									if (ds > 3) {
										setImage(detailedImages.get(3),
												R.id.game_detail_game4);
										if (ds > 4) {
											setImage(detailedImages.get(4),
													R.id.game_detail_game5);
											if (ds > 5) {
												setImage(detailedImages.get(5),
														R.id.game_detail_game6);
												if (ds > 6) {
													TextView tv_num = (TextView) findViewById(R.id.game_detail_more);
													tv_num.getBackground()
															.setAlpha(156);
												}
											}
										}
									}
								}
							}
						} else {
							ll_photos.setVisibility(View.GONE);
							findViewById(R.id.game_detail_no).setVisibility(
									View.VISIBLE);
						}
					} else {
						ll_photos.setVisibility(View.GONE);
						findViewById(R.id.game_detail_no).setVisibility(
								View.VISIBLE);
					}
				}
			}
		}

		private void setImage(DetailedImageBean dib, int id) {
			ImageView iv = (ImageView) findViewById(id);
			App.app.showUrlImg(iv, dib.getImage_url() + dib.getImage_path());
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
		}
	}

	private class MySpan extends ClickableSpan {

		private String mSpan;

		MySpan(String span) {
			mSpan = span;
		}

		@Override
		public void onClick(View widget) {
			// 链接被点击 这里可以做一些自己定义的操作
			String type = mSpan.substring(0, mSpan.indexOf("#"));
			Intent pi = new Intent(GameDetailActivity.this,
					GameListActivity.class);
			if ("0".equals(type)) {
				PublisherBean pb = new PublisherBean();
				pb.setBgg_publisher_id(Integer.valueOf(mSpan.substring(mSpan
						.indexOf("#") + 1)));
				pi.putExtra("PublisherBean", pb);
			} else {
				DesignerBean db = new DesignerBean();
				db.setBgg_designer_id(Integer.valueOf(mSpan.substring(mSpan
						.indexOf("#") + 1)));
				pi.putExtra("DesignerBean", db);
			}
			startActivity(pi);
		}

		public void updateDrawState(TextPaint tp) {
			super.updateDrawState(tp);
			// 设置超链接字体颜色
			tp.setColor(Color.BLUE);
			// 设置取消超链接下划线
			tp.setUnderlineText(false);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			finish();
			break;
		case R.id.tv_right:
			tv_right.setEnabled(false);
			new FlowGameTask().execute("关注".equals(tv_right.getText()
					.toString()));
			if ("关注".equals(tv_right.getText().toString())) {
				tv_right.setText("取消关注");
			} else {
				tv_right.setText("关注");
			}
			break;
		case R.id.game_detail_price:
			startActivity(new Intent(this, PriceActivity.class));
			break;
		case R.id.game_detail_rbl:
			Intent rbi = new Intent(this, GradeScoreActivity.class);
			rbi.putExtra("GameBean", gb);
			startActivityForResult(rbi, 100);
			break;
		case R.id.game_detail_comment:
			Intent i = new Intent(this, CommentListActivity.class);
			i.putExtra("ID", gb.getData_id());
			startActivity(i);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 100:
				float rating = 0;
				try {
					gb = (GameBean) data.getSerializableExtra("GameBean");
					rating = gb.getGrade_totle_num() / gb.getGrade_member_num();
				} catch (Exception e) {
					e.printStackTrace();
				}
				rb.setRating(rating / 2);
				break;
			default:
				break;
			}
		}
	}

	private class FlowGameTask extends AsyncTask<Boolean, Integer, ResultModel> {
		// boolean flag;

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ResultModel doInBackground(Boolean... params) {
			ResultModel mc = null;
			// flag = params[0];
			try {
				mc = new FlowGameParams(App.app, "" + gb.getData_id(),
						params[0]).getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel result) {
			tv_right.setEnabled(true);
			if (result != null && result.getCode() == 1) {

			}
		}

		@Override
		protected void onCancelled() {
		}
	}

}
