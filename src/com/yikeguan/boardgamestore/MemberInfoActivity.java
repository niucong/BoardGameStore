package com.yikeguan.boardgamestore;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.app.ShowToast;
import com.yikeguan.boardgamestore.dialog.SpinnerProgressDialog;
import com.yikeguan.boardgamestore.net.HttpRequestServers;
import com.yikeguan.boardgamestore.resquest.UploadMemberHeadParams;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.DensityUtil;
import com.yikeguan.boardgamestore.util.FileUtil;
import com.yikeguan.boardgamestore.util.L;
import com.yikeguan.boardgamestore.util.PhotoUtil;
import com.yikeguan.boardgamestore.util.StreamUtil;

public class MemberInfoActivity extends BasicActivity implements
		OnClickListener {
	private final String TAG = "MemberInfoActivity";

	private final int FROM_GALLERY = 0;
	private final int FROM_CAMERA = FROM_GALLERY + 1;
	private final int FROM_CROP = FROM_GALLERY + 2;
	private final int NAME = FROM_GALLERY + 5;
	private final int SEX = FROM_GALLERY + 6;
	private final int PHONE = FROM_GALLERY + 7;
	private final int SIGN = FROM_GALLERY + 8;
	private final int MAIL = FROM_GALLERY + 9;

	private final String SDCARD_PATH = Environment
			.getExternalStorageDirectory().toString();
	public final String ROOT_PATH = SDCARD_PATH + "/WYYD/";
	private final String HEAD_PATH = ROOT_PATH + "head/";
	private String headimg_name = "headimg.jpg";

	private File saveFile;

	private ImageView iv_title_left, iv_head;
	private TextView tv_title, tv_name, tv_sex, tv_phone, tv_sign, tv_mail;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_info);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);

		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("个人信息");
		iv_title_left.setImageResource(R.drawable.icon_back);

		iv_head = (ImageView) findViewById(R.id.member_info_head_iv);

		tv_name = (TextView) findViewById(R.id.member_info_name_tv);
		tv_sex = (TextView) findViewById(R.id.member_info_sex_tv);
		tv_phone = (TextView) findViewById(R.id.member_info_phone_tv);
		tv_mail = (TextView) findViewById(R.id.member_info_mail_tv);
		tv_sign = (TextView) findViewById(R.id.member_info_sign_tv);

		App.app.showUrlHeader(iv_head,
				App.app.preferences.getStringMessage("app", "Head", ""));
		tv_name.setText(App.app.preferences.getStringMessage("app", "MyName",
				""));
		String sex = App.app.preferences.getStringMessage("app", "MySex", "");
		if ("1".equals(sex)) {
			tv_sex.setText("男");
		} else {
			tv_sex.setText("女");
		}
		tv_phone.setText(App.app.preferences.getStringMessage("app", "MyPhone",
				""));
		tv_mail.setText(App.app.preferences.getStringMessage("app", "MyMail",
				""));
		tv_sign.setText(App.app.preferences.getStringMessage("app", "MyMood",
				""));

		findViewById(R.id.member_info_head).setOnClickListener(this);
		findViewById(R.id.member_info_name).setOnClickListener(this);
		findViewById(R.id.member_info_sex).setOnClickListener(this);
		findViewById(R.id.member_info_phone).setOnClickListener(this);
		findViewById(R.id.member_info_mail).setOnClickListener(this);
		findViewById(R.id.member_info_sign).setOnClickListener(this);
		findViewById(R.id.member_info_background).setOnClickListener(this);
		iv_title_left.setOnClickListener(this);

		saveFile = new File(HEAD_PATH);
		if (saveFile != null && !saveFile.exists()) {
			saveFile.mkdirs();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			finish();
			break;
		case R.id.member_info_head:
			showUpHeadDialog(HEAD_PATH, headimg_name);
			break;
		case R.id.member_info_name:
			Intent ni = new Intent(this, EditInfoActivity.class);
			ni.putExtra("Text", tv_name.getText().toString());
			ni.putExtra("Type", 0);
			startActivityForResult(ni, NAME);
			break;
		case R.id.member_info_sex:
			Intent si = new Intent(this, EditInfoActivity.class);
			si.putExtra("Text", tv_sex.getText().toString());
			si.putExtra("Type", 1);
			startActivityForResult(si, SEX);
			break;
		case R.id.member_info_phone:
			Intent pi = new Intent(this, EditInfoActivity.class);
			pi.putExtra("Text", tv_phone.getText().toString());
			pi.putExtra("Type", 2);
			startActivityForResult(pi, PHONE);
			break;
		case R.id.member_info_mail:
			Intent mi = new Intent(this, EditInfoActivity.class);
			mi.putExtra("Text", tv_name.getText().toString());
			mi.putExtra("Type", 4);
			startActivityForResult(mi, MAIL);
			break;
		case R.id.member_info_sign:
			Intent sgi = new Intent(this, EditInfoActivity.class);
			sgi.putExtra("Text", tv_sign.getText().toString());
			sgi.putExtra("Type", 3);
			startActivityForResult(sgi, SIGN);
			break;
		case R.id.member_info_background:

			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			Uri uri = null;
			switch (requestCode) {
			case FROM_CAMERA:
				File picture = new File(saveFile, headimg_name);
				uri = Uri.fromFile(picture);
				startPhotoZoom(uri, HEAD_PATH, headimg_name);
				break;
			case FROM_GALLERY:
				uri = data.getData();
				startPhotoZoom(uri, HEAD_PATH, headimg_name);
				break;
			case FROM_CROP:
				File headPic = new File(saveFile, headimg_name);
				iv_head.setImageBitmap(getHeadFilePath(this,
						Uri.fromFile(headPic)));
				new UploadMemberHeadTask().execute(headPic.getPath());
				break;
			case NAME:
				tv_name.setText(data.getStringExtra("Text"));
				break;
			case SEX:
				if ("1".equals(data.getStringExtra("Text"))) {
					tv_sex.setText("男");
				} else {
					tv_sex.setText("女");
				}
				break;
			case PHONE:
				tv_phone.setText(data.getStringExtra("Text"));
				break;
			case SIGN:
				tv_sign.setText(data.getStringExtra("Text"));
				break;
			default:
				break;
			}
		}
	}

	private class UploadMemberHeadTask extends
			AsyncTask<String, Integer, String> {

		private SpinnerProgressDialog spdDialog;

		public UploadMemberHeadTask() {
			spdDialog = new SpinnerProgressDialog(MemberInfoActivity.this);
		}

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			spdDialog.showProgressDialog("正在上传中...");
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				return startThread(params[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String a) {
			spdDialog.cancelProgressDialog("");
			// {"result":{"head_url":"http://photo.onemomentgames.com/","head_path":"default/member/head/1441632930833878.jpg"},"code":1,"desc":null,"version":"1.0"}
			try {
				JSONObject json = new JSONObject(a);
				// JSONObject json = new JSONObject(js.getString("result"));
				App.app.preferences.saveStringMessage(
						"app",
						"Head",
						json.getString("head_url")
								+ json.getString("head_path"));
				ShowToast.getToast().show("修改成功");
			} catch (Exception e) {
				e.printStackTrace();
				failTip();
			}
		}

		private void failTip() {
			ShowToast.getToast().show("修改失败");
			App.app.showUrlHeader(iv_head,
					App.app.preferences.getStringMessage("app", "Head", ""));
		}
	}

	private String startThread(String filepath) {
		String url = ConstantUtil.API_URL + "upload/uploadMemberHead";
		File file = new File(filepath);
		file = FileUtil.ChangeImage(file, false);

		String params = new UploadMemberHeadParams(App.app).getParams();
		L.d(TAG, "startThread url=" + url + "?" + params);
		try {
			String result = httpUpload(url + "?" + params, file);
			L.d(TAG, "startThread result=" + result);
			if (result != null) {
				final JSONObject object = new JSONObject(result);
				if (object.getInt("code") == 1) {
					String temps = object.getString("result");
					return temps;
				} else {
					onUploadFailed(file);
				}
			} else {
				onUploadFailed(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			onUploadFailed(file);
		}
		return "";
	}

	private Handler sendHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:

				break;
			default:
				break;
			}
		}
	};

	private void onUploadFailed(File file) {
		Message msg = Message.obtain();
		msg.what = 0;
		msg.obj = file.getName() + "上传失败，请重新上传！";
		sendHandler.sendMessage(msg);
	}

	/**
	 * HttpURLConnection POST上传文件
	 * 
	 * @param uploadUrl
	 * @param filename
	 * @throws Exception
	 */
	private String httpUpload(String uploadUrl, File file) throws Exception {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";
		DataOutputStream dos = null;
		FileInputStream fis = null;
		String result = null;

		try {
			URL url = new URL(uploadUrl);
			HttpURLConnection httpURLConnection = HttpRequestServers
					.getHttpURLConnection(url);

			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			String reqHeader = twoHyphens
					+ boundary
					+ end
					+ "Content-Disposition: form-data; name=\"file\"; filename=\""
					+ file.getName() + "\"" + end
					+ "Content-Type: application/octet-stream" + end + end;
			String reqEnder = end + twoHyphens + boundary + twoHyphens + end;

			long totalLength = file.length();
			httpURLConnection.setFixedLengthStreamingMode(reqHeader.length()
					+ (int) (totalLength) + reqEnder.length());

			dos = new DataOutputStream(httpURLConnection.getOutputStream());
			dos.writeBytes(reqHeader);
			L.d(TAG, "path=" + file.getAbsolutePath());
			fis = new FileInputStream(file);
			L.d(TAG, "httpUpload totalLength=" + totalLength);
			long uploadSize = 0;
			int progress = 0;
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
				uploadSize += count;
				progress = (int) (uploadSize * 100 / totalLength);
				// if (progress > 0) {
				// L.d(TAG, "httpUpload uploadSize=" + uploadSize
				// + ",progress=" + progress);
				// }
				//
				// if (progress >= 100) {
				// }
			}

			dos.writeBytes(reqEnder);
			dos.flush();

			L.d(TAG, "httpUpload dos.size=" + dos.size());
			L.d(TAG,
					"httpUpload ResponseCode="
							+ httpURLConnection.getResponseCode());
			result = StreamUtil.readData(httpURLConnection.getInputStream());
			L.d(TAG, "httpUpload result=" + result);
		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			if (dos != null)
				try {
					dos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return result;
	}

	/**
	 * 显示修改头像对话框
	 **/
	public void showUpHeadDialog(final String head_file, final String head_name) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("修改头像");

		String[] dialogMsg = new String[] { "手机相册", "相机拍照" };
		builder.setItems(dialogMsg, new DialogInterface.OnClickListener() {
			// .setAdapter(new ArrayAdapter(homePg, R.layout.choice_item,
			// dialogMsg),
			// new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/*");
					startActivityForResult(
							Intent.createChooser(intent, "选择图片"), FROM_GALLERY);
					break;
				case 1:
					Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File saveFile = new File(head_file);
					if (saveFile.exists()) {
						// Log.d(TAG,"目录已存在");
					} else {
						saveFile.mkdirs();
					}
					intent1.putExtra(MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(new File(saveFile, head_name)));
					startActivityForResult(intent1, FROM_CAMERA);
					break;
				case 2:
					dialog.dismiss();
					break;
				}
			}
		});
		AlertDialog ad = builder.create();
		ad.setCanceledOnTouchOutside(true);
		ad.show();
	}

	/**
	 * 获取返回图片的路径
	 * 
	 * @param uri
	 */
	private Bitmap getHeadFilePath(Context c, Uri uri) {
		String sUri = uri.toString();
		String imgPath = null;
		// 如果是从系统gallery取图片，返回一个contentprovider的uri
		if (sUri.startsWith("content://")) {
			Cursor cursor = c.getContentResolver().query(uri, null, null, null,
					null);
			if (cursor.moveToFirst()) {
				imgPath = cursor.getString(1); // 图片文件路径
			} else {

			}
		} else if (sUri.startsWith("file://")
				&& (sUri.endsWith(".png") || sUri.endsWith(".jpg") || sUri
						.endsWith(".gif"))) {
			// 如果从某些第三方软件中选取文件，返回的是一个文件具体路径。
			imgPath = sUri.replace("file://", "");
		} else if (sUri.startsWith(SDCARD_PATH)) {
			// 直接获取临时图片地址
			imgPath = sUri;
		} else {
			// 返回的uri不合法或者文件不是图片。
		}
		L.d(TAG, "getHeadFilePath imgPath=" + imgPath);
		return PhotoUtil.getBitmapFromFile(new File(imgPath),
				DensityUtil.dip2px(c, 180), DensityUtil.dip2px(c, 180));
	}

	/**
	 * 裁剪图片
	 * 
	 * @param uri
	 *            图片路径
	 **/
	private void startPhotoZoom(Uri uri, String path, String name) {
		Intent cropIntent = new Intent("com.android.camera.action.CROP");
		cropIntent.setDataAndType(uri, "image/*");// 设置要裁剪的图片
		cropIntent.putExtra("crop", "true");// crop=true 裁剪页面.
		// 设置其他信息：
		cropIntent.putExtra("aspectX", 1);// 设置裁剪框的比例.
		cropIntent.putExtra("aspectY", 1);// x:y=1:1
		// outputX outputY 是裁剪图片宽高
		// cropIntent.putExtra("outputX", 1200);
		// cropIntent.putExtra("outputY", 1200);
		cropIntent.putExtra("return-data", "false");
		File saveFile = new File(path);
		File picture = new File(saveFile, name);
		cropIntent.putExtra("output", Uri.fromFile(picture));// 保存输出文件
		cropIntent.putExtra("outputFormat", "JPEG");// 返回格式
		startActivityForResult(cropIntent, FROM_CROP);
	}

}
