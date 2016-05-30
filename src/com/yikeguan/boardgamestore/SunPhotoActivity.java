package com.yikeguan.boardgamestore;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.app.ShowToast;
import com.yikeguan.boardgamestore.dialog.DialogController;
import com.yikeguan.boardgamestore.dialog.DialogController.DialogCallBack;
import com.yikeguan.boardgamestore.dialog.SpinnerProgressDialog;
import com.yikeguan.boardgamestore.net.HttpRequestServers;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.UploadResourceParams;
import com.yikeguan.boardgamestore.resquest.recommend.RecommendCreateParams;
import com.yikeguan.boardgamestore.resquest.sunpic.SunPicCreateParams;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.DensityUtil;
import com.yikeguan.boardgamestore.util.FaceUtil;
import com.yikeguan.boardgamestore.util.FileUtil;
import com.yikeguan.boardgamestore.util.L;
import com.yikeguan.boardgamestore.util.PhotoUtil;
import com.yikeguan.boardgamestore.util.StreamUtil;
import com.yikeguan.boardgamestore.view.FacesView;
import com.yikeguan.boardgamestore.view.FacesView.OnFaceChosenListner;

public class SunPhotoActivity extends BasicActivity implements OnClickListener {
	private final String TAG = "SunPhotoActivity";

	private ImageView iv_title_left, iv_photo, iv_face;
	private TextView tv_title, tv_right;
	private EditText et_title, et_context;
	private LinearLayout ll_photos;

	private String pictureName = null;
	private final int FROM_GALLERY = 0;
	private final int FROM_CAMERA = 1;
	public static ArrayList<String> paths = new ArrayList<>();

	private int type;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sun_photo);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		type = getIntent().getIntExtra("type", 0);

		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		tv_title = (TextView) findViewById(R.id.tv_title);
		if (type == 0) {
			tv_title.setText("求推荐");
		} else if (type == 1) {
			tv_title.setText("晒图");
		}

		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_right.setText("发布");
		tv_right.setOnClickListener(this);

		iv_title_left.setImageResource(R.drawable.icon_back);
		iv_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		iv_photo = (ImageView) findViewById(R.id.sun_photo_photo);
		iv_face = (ImageView) findViewById(R.id.sun_photo_face);
		iv_photo.setOnClickListener(this);
		iv_face.setOnClickListener(this);

		et_title = (EditText) findViewById(R.id.sun_photo_title);
		et_context = (EditText) findViewById(R.id.sun_photo_context);

		ll_photos = (LinearLayout) findViewById(R.id.sun_photo_photos);

		initFace();
		et_title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showKeyBoard(v);
			}
		});
		et_context.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showKeyBoard(v);
			}
		});
	}

	private void initFace() {
		final FacesView faceView = (FacesView) findViewById(R.id.input_facesview);
		faceView.setOnFaceChosenListner(new OnFaceChosenListner() {

			@Override
			public void onChosen(String text, int resId) {
				FacesView.doEditChange(SunPhotoActivity.this, et_context, text,
						resId);
			}
		});
		if (faceView.getChildCount() == 0) {
			faceView.setFaces();
		}

		final FacesView faceView2 = (FacesView) findViewById(R.id.input_facesview2);
		faceView2.setRes(FaceUtil.FACE_RES_LIDS, FaceUtil.FACE_LTEXTS);
		faceView2.setOnFaceChosenListner(new OnFaceChosenListner() {

			@Override
			public void onChosen(String text, int resId) {
				FacesView.doEditChange(SunPhotoActivity.this, et_context, text,
						resId);
			}
		});
		if (faceView2.getChildCount() == 0) {
			faceView2.setFaces();
		}

		final TextView tv_face1 = (TextView) findViewById(R.id.input_def);
		final TextView tv_face2 = (TextView) findViewById(R.id.input_other);
		tv_face1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv_face1.setBackgroundColor(getMyColor(R.color.facechoose1));
				tv_face1.setTextColor(getMyColor(R.color.facechoose2));
				tv_face2.setBackgroundColor(getMyColor(R.color.facechoose2));
				tv_face2.setTextColor(getMyColor(R.color.facechoose1));

				faceView.setVisibility(View.VISIBLE);
				faceView2.setVisibility(View.GONE);
			}
		});
		tv_face2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv_face1.setBackgroundColor(getMyColor(R.color.facechoose2));
				tv_face1.setTextColor(getMyColor(R.color.facechoose1));
				tv_face2.setBackgroundColor(getMyColor(R.color.facechoose1));
				tv_face2.setTextColor(getMyColor(R.color.facechoose2));

				faceView2.setVisibility(View.VISIBLE);
				faceView.setVisibility(View.GONE);
			}
		});
	}

	private int getMyColor(int color) {
		return getResources().getColor(color);
	}

	/**
	 * 无输入
	 */
	private void hideKeyBoardFace() {
		InputMethodManager inputMethodManager = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
		inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		findViewById(R.id.input_faces).setVisibility(View.GONE);
		ll_photos.setVisibility(View.VISIBLE);
	}

	/**
	 * 表情输入
	 */
	private void showFace() {
		InputMethodManager inputMethodManager = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
		inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		findViewById(R.id.input_faces).setVisibility(View.VISIBLE);
		ll_photos.setVisibility(View.GONE);
		et_context.requestFocus();
	}

	/**
	 * 键盘输入
	 * 
	 * @param view
	 */
	private void showKeyBoard(View view) {
		InputMethodManager inputMethodManager = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
		findViewById(R.id.input_faces).setVisibility(View.GONE);
		ll_photos.setVisibility(View.VISIBLE);
		try {
			inputMethodManager.showSoftInputFromInputMethod(this
					.getCurrentFocus().getWindowToken(), 0);
			inputMethodManager.showSoftInput(et_context, 0);
			et_context.requestFocus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		paths.clear();
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sun_photo_photo:
			hideKeyBoardFace();
			// if (paths.size() == 9) {
			// ShowToast.getToast().show("最多添加9张图片");
			// } else {
			DialogController.selectDialog(this,
					new String[] { "手机相册", "相机拍照" }, new DialogCallBack() {

						@Override
						public void onDlgCallBack(int which) {
							if (which == 0) {
								// Intent intent = new Intent(
								// Intent.ACTION_GET_CONTENT);
								// intent.setType("image/*");
								// startActivityForResult(intent, FROM_GALLERY);
								Intent intent = new Intent(
										SunPhotoActivity.this,
										SelectPhotoActivity.class);
								startActivityForResult(intent, FROM_GALLERY);
							} else {
								if (SunPhotoActivity.paths.size() == 9) {
									ShowToast.getToast().show("最多添加9张图片");
								} else {
									Intent intent = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									pictureName = System.currentTimeMillis()
											+ ".jpg";
									File saveFile = new File(
											ConstantUtil.CAMERA_PATH);
									if (!saveFile.exists()) {
										saveFile.mkdirs();
									}

									intent.putExtra(
											MediaStore.Images.Media.ORIENTATION,
											0);
									intent.putExtra(MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(new File(saveFile,
													pictureName)));
									startActivityForResult(intent, FROM_CAMERA);
								}
							}
						}
					});
			// }
			break;
		case R.id.sun_photo_face:
			showFace();
			break;
		case R.id.tv_right:
			new SunPhotoTask().execute();
			break;

		default:
			break;
		}
	}

	private class SunPhotoTask extends AsyncTask<String, Integer, ResultModel> {

		private SpinnerProgressDialog spdDialog;

		public SunPhotoTask() {
			spdDialog = new SpinnerProgressDialog(SunPhotoActivity.this);
		}

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			spdDialog.showProgressDialog("正在发表中...");
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel mc = null;

			try {
				int size = paths.size();
				String[] photos = new String[size];
				for (int i = 0; i < size; i++) {
					JSONArray ja = new JSONArray(startThread(paths.get(i)));
					for (int j = 0; j < ja.length(); j++) {
						photos[i] = ja.getString(j);
					}
				}
				if (type == 0) {
					mc = new RecommendCreateParams(getApplicationContext(),
							et_title.getText().toString(), et_context.getText()
									.toString(), photos, null).getResult();
				} else if (type == 1) {
					mc = new SunPicCreateParams(getApplicationContext(),
							et_title.getText().toString(), et_context.getText()
									.toString(), photos, null).getResult();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mc;
		}

		@Override
		protected void onPostExecute(ResultModel a) {
			spdDialog.cancelProgressDialog("");
			if (a == null) {
				ShowToast.getToast().show("发表失败");
			} else {
				if (a.getCode() == 1) {
					ShowToast.getToast().show("发表成功");
					finish();
				} else {
					ShowToast.getToast().show("发表失败");
				}
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Uri uri = null;
			String filePath = null;
			switch (requestCode) {
			case FROM_GALLERY:
				// uri = data.getData();
				// filePath = queryPhoto(uri);
				// L.d(TAG, "onActivityResult filePath=" + filePath);
				// addPhoto(filePath);
				// TODO
				ll_photos.removeAllViews();
				for (String path : paths) {
					addPhoto(path);
				}
				break;
			case FROM_CAMERA:
				try {
					if (data != null) {
						uri = data.getData();
						L.i(TAG, "onActivityResult uri0=" + uri);
						if (uri != null) {
							filePath = queryPhoto(uri);
						} else {
							File saveFile = new File(ConstantUtil.CAMERA_PATH);
							File picture = new File(saveFile, pictureName);
							uri = Uri.fromFile(picture);
							L.i(TAG, "onActivityResult uri1=" + uri);
							filePath = uri.getPath();
							pictureName = null;
						}
					} else {
						File saveFile = new File(ConstantUtil.CAMERA_PATH);
						File picture = new File(saveFile, pictureName);
						uri = Uri.fromFile(picture);
						L.i(TAG, "onActivityResult uri2=" + uri);
						filePath = uri.getPath();
						pictureName = null;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
						uri));
				L.i(TAG, "onActivityResult filepath=" + filePath);
				addPhoto(filePath);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 查找本地图片
	 * 
	 * @param uri
	 * @return
	 */
	private String queryPhoto(Uri uri) {
		String filepath = "";
		Cursor cursor = getContentResolver()
				.query(uri,
						new String[] { "_data", "_display_name", "_size",
								"mime_type" }, null, null, null);
		// Thumbnails.EXTERNAL_CONTENT_URI
		if (cursor != null) {
			cursor.moveToFirst();
			filepath = cursor.getString(0); // 图片文件路径
			cursor.close();
		} else {
			filepath = uri.getPath();
		}
		return filepath;
	}

	private void addPhoto(final String path) {
		L.d(TAG, "addPhoto path=" + path);
		if (path == null)
			return;
		if (!paths.contains(path))
			// return;
			paths.add(path);
		final ImageView iv = (ImageView) LayoutInflater.from(this).inflate(
				R.layout.item_photo, null);
		ll_photos.addView(iv);

		iv.setImageBitmap(PhotoUtil.getBitmapFromFileByWidth(new File(path),
				DensityUtil.dip2px(this, 100)));
		// ImageLoader.getInstance(3,Type.LIFO).loadImage(path, iv);

		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(SunPhotoActivity.this,
						ImageActivity.class);
				i.putExtra("path", path);
				i.putStringArrayListExtra("paths", paths);
				startActivity(i);
			}
		});
		iv.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SunPhotoActivity.this);
				builder.setMessage("确认删除该图片");
				builder.setNegativeButton("取消", null);
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								ll_photos.removeView(iv);
								paths.remove(path);
								if (ll_photos.getChildCount() < 4)
									iv_photo.setVisibility(View.VISIBLE);
							}
						});
				AlertDialog ad = builder.create();
				ad.setCanceledOnTouchOutside(true);
				ad.show();
				return false;
			}
		});
	}

	private String startThread(String filepath) {
		String url = ConstantUtil.API_URL + "upload/uploadResource";
		File file = new File(filepath);
		file = FileUtil.ChangeImage(file, false);

		String method = "RECOMMEND";
		if (type == 1)
			method = "SUN";
		String params = new UploadResourceParams(App.app, method, "PHOTO")
				.getParams();
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

}
