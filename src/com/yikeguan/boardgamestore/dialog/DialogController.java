package com.yikeguan.boardgamestore.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

public class DialogController {
	protected static final String TAG = "DialogController";
	
	public interface DialogCallBack {
		public void onDlgCallBack(int which);
	}

	/**
	 * 选择对话框
	 * 
	 * @param c
	 * @param data
	 * @param tv
	 */
	public static void selectDialog(Context c, final String[] data,
			final DialogCallBack dialogCallBack) {
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		builder.setItems(data, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogCallBack.onDlgCallBack(which);
			}
		});
		AlertDialog ad = builder.create();
		ad.setCanceledOnTouchOutside(true);
		ad.show();
	}

//	/**
//	 * 选择对话框
//	 * 
//	 * @param c
//	 * @param data
//	 * @param tv
//	 */
//	public static void selectDialog(Context c, final String[] data,
//			final long[] regions, final TextView tv, final TextView tv2) {
//		AlertDialog.Builder builder = new AlertDialog.Builder(c);
//		builder.setItems(data, new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				tv.setText(data[which]);
//				tv.setTag(which);
//				if (tv2 != null) {
//					long region = regions[which];
//					L.d(TAG, "selectDialog region=" + region);
//					try {
//						tv2.setText(DBUtil
//								.getBaseDBSession(App.app)
//								.getTRegionDao()
//								.queryBuilder()
//								.where(TRegionDao.Properties.Region_id
//										.eq(region)).list().get(0).getRegion_shortname());
//						tv2.setTag(region);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		});
//		AlertDialog ad = builder.create();
//		ad.setCanceledOnTouchOutside(true);
//		ad.show();
//	}

	/**
	 * 选择对话框
	 * 
	 * @param c
	 * @param data
	 * @param tv
	 */
	public static void setNumDialog(Context c, final TextView tv) {
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		final EditText et = new EditText(c);
		et.setInputType(InputType.TYPE_CLASS_NUMBER);
		et.setSingleLine(true);
		builder.setTitle("输入日龄");
		builder.setView(et);
		builder.setNegativeButton("取消", null);
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				tv.setText(et.getText());
			}
		});
		AlertDialog ad = builder.create();
		ad.setCanceledOnTouchOutside(true);
		ad.show();
	}

	/**
	 * 提醒对话框
	 * 
	 * @param c
	 * @param msg
	 */
	public static void tipDialog(Context c, String msg, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		if (title != null && !"".equals(title))
			builder.setTitle(title);
		builder.setMessage(msg);
		AlertDialog ad = builder.create();
		ad.setCanceledOnTouchOutside(true);
		ad.show();
	}

}
