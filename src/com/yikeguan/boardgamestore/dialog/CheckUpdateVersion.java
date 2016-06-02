package com.yikeguan.boardgamestore.dialog;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.yikeguan.boardgamestore.SettingActivity;
import com.yikeguan.boardgamestore.net.DownLoadFileThread;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.FileUtil;
import com.yikeguan.boardgamestore.util.L;

public class CheckUpdateVersion {
	private static final String TAG = "CheckUpdateVersion";

	private Context context;

	// private static Map<String, String> version;

	public CheckUpdateVersion(Context context) {
		this.context = context;
	}

	/**
	 * 提示新版本对话框
	 */
	@SuppressLint("SimpleDateFormat")
	public void versionDialog(final String url, String version) {
		try {
			// final String versionUrl = version.get("fileUrl");
			L.d(TAG, "versionDialog versionUrl = " + url);
			// String versionDesc = version.get("versionDesc");
			// String msg = "";
			// if (versionDesc.contains("#")) {
			// String[] descs = versionDesc.split("#");
			// for (String desc : descs) {
			// msg = msg + desc + "\n";
			// }
			// msg = msg.substring(0, msg.length() - 1);
			// } else {
			// msg = versionDesc;
			// }

			// final String versionName = version.get("versionName");
			final String versionName = url.substring(url.lastIndexOf("/"),
					url.length());
			new AlertDialog.Builder(context).setTitle("检测到新版本 V" + version)
					// .setMessage("新版本更新\n" + msg)
					.setCancelable(false)
					.setPositiveButton("升级",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String filename = url.substring(
											url.lastIndexOf("/") + 1,
											url.lastIndexOf("."))
											// + versionName
											+ ".apk";
									String filename2 = url.substring(
											url.lastIndexOf("/") + 1,
											url.lastIndexOf("."))
											+ versionName + ".apk";
									File myFile = new File(
											ConstantUtil.ROOT_PATH + filename2);
									L.d(TAG, "versionDialog filename2 = "
											+ filename2);

									if (myFile != null && myFile.exists()) {
										L.d(TAG, "versionDialog myFile = "
												+ myFile.length());
										new FileUtil()
												.openFile(context, myFile);
										SettingActivity.loading = false;
										return;
									}
									L.d(TAG, "versionDialog filename2 = "
											+ filename2);
									new DownLoadFileThread(context, url, 0,
											filename).start();
								}
							}).setNegativeButton("稍后再说", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							L.d(TAG, "versionDialog versionName3 = "
									+ versionName);
							SettingActivity.loading = false;
						}
					}).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
