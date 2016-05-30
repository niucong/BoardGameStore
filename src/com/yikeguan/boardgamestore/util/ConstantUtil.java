package com.yikeguan.boardgamestore.util;

import android.os.Environment;

public class ConstantUtil {

	/** API域名指向 */
	public static final String API_URL = "http://123.57.255.158/yikeguan/";
	// public static final String HEAD_URL = "http://yikeguan.yuuquu.com/";

	public static final String YKG_IMG_BGGIMG = "http://photo.onemomentgames.com/bgg_image_base/";
	public static final String YKG_IMG_PUBIMG = "http://photo.onemomentgames.com/publisher_image_base/";
	public static final String YKG_IMG_DESIMG = "http://photo.onemomentgames.com/designer_image_base/";

	/** 应用的APP_KEY */
	public static final String APP_KEY = "app_android";// app_test

	/** 应用密钥SECRET_KEY */
	public static final String SECRET_KEY = "niucong@123";// app_test

	public static final String WAY = "2";

	public static final String SDCARD_PATH = Environment
			.getExternalStorageDirectory().toString();
	public static final String ROOT_PATH = SDCARD_PATH + "/WYYD/";
	/** 拍照上传文件夹 */
	public static final String CAMERA_PATH = ROOT_PATH + "camera/";
	/** 图片文件夹 */
	public static final String IMAGE_PATH = ROOT_PATH + "image/";
	/** 临时文件夹 */
	public static final String TEMP_PATH = ROOT_PATH + "temp/";
}
