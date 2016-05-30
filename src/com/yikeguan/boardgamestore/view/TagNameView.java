package com.yikeguan.boardgamestore.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yikeguan.boardgamestore.R;

public class TagNameView {

	private View view;
	private TextView name_tv;
	private LayoutInflater inflater;

	private int[] colors = new int[] { Color.RED, Color.BLACK, Color.BLUE,
			Color.YELLOW, Color.WHITE, Color.GREEN };

	public TagNameView(final Context context, String name) {
		inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.tag_name, null);
		name_tv = (TextView) view.findViewById(R.id.tag_name);
		name_tv.setText(name);

		int txt = colors[(int) (Math.random() * colors.length)];
		name_tv.setTextColor(txt);

		// Drawable drawableRight = context.getResources().getDrawable(
		// R.drawable.tip_del);
		// // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		// drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(),
		// drawableRight.getMinimumHeight());
		// name_tv.setCompoundDrawables(null, null, drawableRight, null); //
		// 设置左图标
	}

	public View getView() {
		return view;
	}
}
