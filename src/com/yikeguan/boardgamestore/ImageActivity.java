package com.yikeguan.boardgamestore;

import java.io.File;
import java.util.ArrayList;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yikeguan.boardgamestore.util.L;
import com.yikeguan.boardgamestore.util.PhotoUtil;
import com.yikeguan.boardgamestore.view.ExtendedViewPager;
import com.yikeguan.boardgamestore.view.TouchImageView;

public class ImageActivity extends BasicActivity {

	private String[] images;
	private int width;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		width = outMetrics.widthPixels;

		ArrayList<String> paths = getIntent().getStringArrayListExtra("paths");
		L.i("MainActivity", "size=" + paths.size());
		images = new String[paths.size()];
		for (int i = 0; i < images.length; i++) {
			images[i] = paths.get(i);
		}

		ExtendedViewPager mViewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
		mViewPager.setAdapter(new TouchImageAdapter());

		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);

		((TextView) findViewById(R.id.tv_title)).setText("查看图片");

		ImageView iv_left = (ImageView) findViewById(R.id.iv_title_left);
		iv_left.setImageResource(R.drawable.icon_back);
		iv_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	class TouchImageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			TouchImageView img = new TouchImageView(container.getContext());
			// img.setImageResource(images[position]);
			img.setImageBitmap(PhotoUtil.getBitmapFromFileByWidth(new File(
					images[position]), width));
			container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			return img;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return super.onTouchEvent(event);
	}
}
