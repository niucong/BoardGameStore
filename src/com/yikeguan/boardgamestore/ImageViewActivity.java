package com.yikeguan.boardgamestore;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yikeguan.boardgamestore.view.ImageControl;
import com.yikeguan.boardgamestore.view.ImageControl.ICustomMethod;

public class ImageViewActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_view);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		findView();
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		init();
	}

	ImageControl imgControl;
	LinearLayout llTitle;
	TextView tvTitle;

	private void findView() {
		imgControl = (ImageControl) findViewById(R.id.imageview_imageControl1);
		llTitle = (LinearLayout) findViewById(R.id.imageview_llTitle);
		tvTitle = (TextView) findViewById(R.id.imageview_title);
	}

	private void init() {
		// TODO tvTitle.setText("图片测试");
		// 这里可以为imgcontrol的图片路径动态赋值
		imgControl.setImageResource(R.drawable.def_img);

		Bitmap bmp;
		if (imgControl.getDrawingCache() != null) {
			bmp = Bitmap.createBitmap(imgControl.getDrawingCache());
		} else {
			bmp = ((BitmapDrawable) imgControl.getDrawable()).getBitmap();
		}
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int screenW = this.getWindowManager().getDefaultDisplay().getWidth();
		int screenH = this.getWindowManager().getDefaultDisplay().getHeight()
				- statusBarHeight;
		if (bmp != null) {
			imgControl.imageInit(bmp, screenW, screenH, statusBarHeight,
					new ICustomMethod() {

						@Override
						public void customMethod(Boolean currentStatus) {
							// 当图片处于放大或缩小状态时，控制标题是否显示
							if (currentStatus) {
								llTitle.setVisibility(View.GONE);
							} else {
								llTitle.setVisibility(View.VISIBLE);
							}
						}
					});
		} else {
			Toast.makeText(ImageViewActivity.this, "图片加载失败，请稍候再试！",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			imgControl.mouseDown(event);
			break;

		/**
		 * 非第一个点按下
		 */
		case MotionEvent.ACTION_POINTER_DOWN:

			imgControl.mousePointDown(event);

			break;
		case MotionEvent.ACTION_MOVE:
			imgControl.mouseMove(event);

			break;

		case MotionEvent.ACTION_UP:
			imgControl.mouseUp();
			break;

		}

		return super.onTouchEvent(event);
	}
}
