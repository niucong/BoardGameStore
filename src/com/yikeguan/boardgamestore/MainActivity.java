package com.yikeguan.boardgamestore;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.sina.weibo.sdk.constant.WBConstants;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.dialog.CheckUpdateVersion;
import com.yikeguan.boardgamestore.dialog.SpinnerProgressDialog;
import com.yikeguan.boardgamestore.fragment.FragmentClassIfication;
import com.yikeguan.boardgamestore.fragment.FragmentHomePage;
import com.yikeguan.boardgamestore.fragment.FragmentMine;
import com.yikeguan.boardgamestore.fragment.FragmentSquare;
import com.yikeguan.boardgamestore.net.DownLoadFileThread;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.response.VersionBean;
import com.yikeguan.boardgamestore.resquest.GetNewsClientVersionParams;
import com.yikeguan.boardgamestore.util.DensityUtil;
import com.yikeguan.boardgamestore.util.L;
import com.yikeguan.boardgamestore.util.SoftPhoneInfo;

public class MainActivity extends BasicActivity implements OnClickListener {
	private final String TAG = "MainActivity";

	private DrawerLayout mDrawerLayout;
	private View mDrawer;

	private LinearLayout menu_acction, menu_brower, menu_pay, menu_game,
			menu_chat, menu_message, menu_feedback, menu_help, menu_setting,
			menu_user;// menu_logout
	private Button btn_loin;
	private TextView tv_name, tv_mood;
	private ImageView head;

	public ImageView iv_title_left, iv_title_right, bottombar_home_iv,
			bottombar_class_iv, bottombar_sun_iv, bottombar_square_iv,
			bottombar_mine_iv;
	public TextView tv_title, bottombar_home_tv, bottombar_class_tv,
			bottombar_sun_tv, bottombar_square_tv, bottombar_mine_tv;
	private LinearLayout ll_homePage, ll_class, ll_sun, ll_square, ll_mine;

	private FragmentHomePage homePage;
	private FragmentClassIfication classIfication;
	// private FragmentSun sun;
	private FragmentSquare square;
	private FragmentMine mine;

	private PopupWindow pw;

	private String myId;

	public static boolean loading = false;
	public SpinnerProgressDialog sDialog;
	private String currentVersion;
	LoadingProgressReceiver progressReceiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);
		sDialog = new SpinnerProgressDialog(this);
		currentVersion = new SoftPhoneInfo(this).getVersionName();

		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		if (-1 == App.app.preferences.getIntMessage("program", "screen_width",
				-1)) {
			App.app.preferences.saveIntMessage("program", "screen_width",
					outMetrics.widthPixels);
			App.app.preferences.saveIntMessage("program", "screen_height",
					outMetrics.heightPixels);
		}

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawer = findViewById(R.id.navdrawer);

		initView();
		setDefaultFragment();

		addMenu();

		// 只有注册了广播才能接收到新消息，目前离线消息，在线消息都是走接收消息的广播（离线消息目前无法监听，在登录以后，接收消息广播会执行一次拿到所有的离线消息）
		msgReceiver = new NewMessageBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(EMChatManager
				.getInstance().getNewMessageBroadcastAction());
		intentFilter.setPriority(3);
		registerReceiver(msgReceiver, intentFilter);

		progressReceiver = new LoadingProgressReceiver();
		IntentFilter ip = new IntentFilter();
		ip.addAction(DownLoadFileThread.TAG);
		registerReceiver(progressReceiver, ip);

		new CheckVersionTask().execute();
	}

	private class CheckVersionTask extends
			AsyncTask<String, Integer, ResultModel> {

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			// sDialog.showProgressDialog("正在检测新版本...");
		}

		@Override
		protected ResultModel doInBackground(String... params) {
			ResultModel a = null;
			try {
				a = new GetNewsClientVersionParams(App.app).getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return a;
		}

		@Override
		protected void onPostExecute(ResultModel a) {
			if (a == null) {
				// sDialog.cancelProgressDialog("检测失败");
			} else {
				if (a.getResult() != null) {
					VersionBean vb = (VersionBean) a.getResult();
					if (currentVersion.equals(vb.getVersion_name())) {
						// sDialog.cancelProgressDialog("已经是最新版本");
					} else {
						// sDialog.cancelProgressDialog("");
						new CheckUpdateVersion(MainActivity.this)
								.versionDialog(
										vb.getVersion_url()
												+ vb.getVersion_path(),
										vb.getVersion_name());
					}
				} else {
					// sDialog.cancelProgressDialog("检测失败");
				}
			}
		}
	}

	public class LoadingProgressReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (DownLoadFileThread.TAG.equals(action)) {
				int progress = intent.getIntExtra("progress", 0);
				if (progress == 100) {
					sDialog.cancelProgressDialog("");
				} else {
					sDialog.showProgressDialog("已下载 " + progress + "%...");
				}
			}
		}
	}

	NewMessageBroadcastReceiver msgReceiver;

	@Override
	protected void onDestroy() {
		unregisterReceiver(msgReceiver);
		super.onDestroy();
	}

	private class NewMessageBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 注销广播
			abortBroadcast();
			// 消息id（每条消息都会生成唯一的一个id，目前是SDK生成）
			String msgId = intent.getStringExtra("msgid");
			// 发送方
			String username = intent.getStringExtra("from");
			L.d(TAG, "NewMessageBroadcastReceiver msgId=" + msgId
					+ ",username=" + username);

			// 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
			EMMessage message = EMChatManager.getInstance().getMessage(msgId);
			EMConversation conversation = EMChatManager.getInstance()
					.getConversation(username);
			L.i(TAG, "NewMessageBroadcastReceiver message="
					+ message.getBody().toString());
			L.d(TAG,
					"NewMessageBroadcastReceiver MsgCount="
							+ conversation.getMsgCount());

			setNotification(username, message);
			// // 如果是群聊消息，获取到group id
			// if (message.getChatType() == ChatType.GroupChat) {
			// username = message.getTo();
			// }
			// if (!username.equals(username)) {
			// // 消息不是发给当前会话，return
			// return;
			// }
		}
	}

	@SuppressWarnings("deprecation")
	private void setNotification(String id, EMMessage message) {
		TextMessageBody txtBody = (TextMessageBody) message.getBody();
		String contentTitle = id;
		String contentText = txtBody.getMessage();

		Intent messageIntent = new Intent(this, ChatActivity.class);
		messageIntent.putExtra("Id", id);
//		PendingIntent messagePendingIntent = PendingIntent.getActivity(this, 0,
//				messageIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, messageIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		Notification.Builder builder = new Notification.Builder(this).setTicker(contentTitle)
				.setSmallIcon(R.drawable.ic_launcher);
		Notification notification = builder.setContentIntent(pendingIntent).setContentTitle(contentTitle).setContentText(contentText).build();
		//FLAG_ONGOING_EVENT表明有程序在运行，该Notification不可由用户清除
//		note.flags = Notification.FLAG_ONGOING_EVENT;

//		Notification notification = new Notification();
//		// 设置通知栏显示内容
//		notification.icon = R.drawable.ic_launcher;
//		notification.flags |= Notification.FLAG_AUTO_CANCEL;
//		notification.tickerText = contentText;
//		notification.setLatestEventInfo(this, contentTitle,// noticeTypeName,//noticeTypeName;
//				contentText, messagePendingIntent);

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(null, 0);
		notificationManager.notify(0, notification);

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		refreshMenu();
	}

	@Override
	protected void onStart() {
		super.onStart();
		refreshMenu();
	}

	private void refreshMenu() {
		myId = App.app.preferences.getStringMessage("app", "MyId", "0");
		L.i(TAG, "myId=" + myId);
		if (!"0".equals(myId)) {
			App.app.showUrlHeader(head,
					App.app.preferences.getStringMessage("app", "Head", ""));
			tv_name.setText(App.app.preferences.getStringMessage("app",
					"MyName", ""));
			tv_mood.setText(App.app.preferences.getStringMessage("app",
					"MyMood", ""));
			btn_loin.setVisibility(View.GONE);
			menu_user.setVisibility(View.VISIBLE);
		} else {
			btn_loin.setVisibility(View.VISIBLE);
			menu_user.setVisibility(View.GONE);
		}
	}

	private void addMenu() {
		menu_acction = (LinearLayout) findViewById(R.id.menu_acction);
		menu_brower = (LinearLayout) findViewById(R.id.menu_brower);
		menu_pay = (LinearLayout) findViewById(R.id.menu_pay);
		menu_game = (LinearLayout) findViewById(R.id.menu_game);
		menu_chat = (LinearLayout) findViewById(R.id.menu_chat);
		menu_message = (LinearLayout) findViewById(R.id.menu_messag);
		menu_feedback = (LinearLayout) findViewById(R.id.menu_feedback);
		menu_setting = (LinearLayout) findViewById(R.id.menu_setting);
		menu_help = (LinearLayout) findViewById(R.id.menu_help);
		// menu_logout = (LinearLayout) findViewById(R.id.menu_logout);
		btn_loin = (Button) findViewById(R.id.menu_login);
		menu_user = (LinearLayout) findViewById(R.id.menu_user);

		btn_loin.setOnClickListener(this);
		menu_acction.setOnClickListener(this);
		menu_brower.setOnClickListener(this);
		menu_pay.setOnClickListener(this);
		menu_game.setOnClickListener(this);
		menu_chat.setOnClickListener(this);
		menu_message.setOnClickListener(this);
		menu_feedback.setOnClickListener(this);
		menu_setting.setOnClickListener(this);
		menu_help.setOnClickListener(this);
		// menu_logout.setOnClickListener(this);

		head = (ImageView) findViewById(R.id.menu_head);
		tv_name = (TextView) findViewById(R.id.menu_name);
		tv_mood = (TextView) findViewById(R.id.menu_mood);
	}

	public void showHideMenu() {
		if (mDrawerLayout.isDrawerOpen(mDrawer)) {
			mDrawerLayout.closeDrawer(mDrawer);
		} else {
			mDrawerLayout.openDrawer(mDrawer);
		}
	}

	private void showTitleBar() {
		findViewById(R.id.fragment_title).setVisibility(View.VISIBLE);
	}

	private void hideTitleBar() {
		// new Thread(){
		// public void run() {
		// try {
		// sleep(10);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// runOnUiThread(new Runnable() {
		// public void run() {
		// findViewById(R.id.fragment_title).setVisibility(View.GONE);
		// }
		// });
		// };
		// }.start();
		findViewById(R.id.fragment_title).setVisibility(View.GONE);
	}

	private void initView() {
		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		iv_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showHideMenu();
			}
		});
		iv_title_left.setVisibility(View.VISIBLE);
		iv_title_left.setImageResource(R.drawable.icon_menu);
		iv_title_right = (ImageView) findViewById(R.id.iv_title_right);
		tv_title = (TextView) findViewById(R.id.tv_title);

		ll_homePage = (LinearLayout) findViewById(R.id.bottombar_home);
		ll_homePage.setOnClickListener(this);
		ll_class = (LinearLayout) findViewById(R.id.bottombar_class);
		ll_class.setOnClickListener(this);
		ll_sun = (LinearLayout) findViewById(R.id.bottombar_sun);
		ll_sun.setOnClickListener(this);
		ll_square = (LinearLayout) findViewById(R.id.bottombar_square);
		ll_square.setOnClickListener(this);
		ll_mine = (LinearLayout) findViewById(R.id.bottombar_mine);
		ll_mine.setOnClickListener(this);

		bottombar_home_iv = (ImageView) findViewById(R.id.bottombar_home_iv);
		bottombar_home_tv = (TextView) findViewById(R.id.bottombar_home_tv);
		bottombar_class_iv = (ImageView) findViewById(R.id.bottombar_class_iv);
		bottombar_class_tv = (TextView) findViewById(R.id.bottombar_class_tv);
		bottombar_sun_iv = (ImageView) findViewById(R.id.bottombar_sun_iv);
		bottombar_sun_tv = (TextView) findViewById(R.id.bottombar_sun_tv);
		bottombar_square_iv = (ImageView) findViewById(R.id.bottombar_square_iv);
		bottombar_square_tv = (TextView) findViewById(R.id.bottombar_square_tv);
		bottombar_mine_iv = (ImageView) findViewById(R.id.bottombar_mine_iv);
		bottombar_mine_tv = (TextView) findViewById(R.id.bottombar_mine_tv);
	}

	private void setDefaultFragment() {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		homePage = new FragmentHomePage();
		transaction.replace(R.id.tabcontent, homePage);
		transaction.commit();
	}

	private int getResColor(int id) {
		return getResources().getColor(id);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		FragmentManager fm = getFragmentManager();
		// 开启Fragment事务
		FragmentTransaction transaction = fm.beginTransaction();
		switch (v.getId()) {
		case R.id.menu_acction:
			if ("0".equals(myId)) {
				enterLogin();
			} else {

			}
			break;
		case R.id.menu_brower:
			if ("0".equals(myId)) {
				enterLogin();
			} else {
				Intent ci = new Intent(this, TrendListActivity.class);
				startActivity(ci);
			}
			break;
		case R.id.menu_pay:
			if ("0".equals(myId)) {
				enterLogin();
			} else {

			}
			break;
		case R.id.menu_game:
			if ("0".equals(myId)) {
				enterLogin();
			} else {

			}
			break;
		case R.id.menu_chat:
			if ("0".equals(myId)) {
				enterLogin();
			} else {
				startActivity(new Intent(this, ChatContactListActivity.class));
			}
			break;
		case R.id.menu_messag:
			if ("0".equals(myId)) {
				enterLogin();
			} else {
				Intent ci = new Intent(this, TrendListActivity.class);
				ci.putExtra("type", 1);
				startActivity(ci);
			}
			break;
		case R.id.menu_feedback:
			startActivity(new Intent(this, FeedbackActivity.class));
			break;
		case R.id.menu_setting:
			startActivity(new Intent(this, SettingActivity.class));
			break;
		case R.id.menu_help:
			startActivity(new Intent(this, HelpActivity.class));
			break;
		case R.id.menu_login:
			enterLogin();
			break;
		// case R.id.menu_logout:// 注销
		// new AlertDialog.Builder(this)
		// .setTitle("提示")
		// .setMessage("退出当前帐号？")
		// .setPositiveButton("确定",
		// new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog,
		// int which) {
		// logout();
		// }
		// }).setNegativeButton("取消", null).show();
		// break;
		case R.id.bottombar_home:
			showTitleBar();
			bottombar_home_iv.setImageResource(R.drawable.icon_home_sel);
			bottombar_home_tv
					.setTextColor(getResColor(R.color.bottombar_tv_home));
			bottombar_class_iv.setImageResource(R.drawable.icon_meassage_nor);
			bottombar_class_tv
					.setTextColor(getResColor(R.color.bottombar_tv_nor));
			bottombar_sun_iv.setImageResource(R.drawable.icon_selfinfo_nor);
			bottombar_sun_tv
					.setTextColor(getResColor(R.color.bottombar_tv_nor));
			bottombar_square_iv.setImageResource(R.drawable.icon_square_nor);
			bottombar_square_tv
					.setTextColor(getResColor(R.color.bottombar_tv_nor));
			bottombar_mine_iv.setImageResource(R.drawable.icon_more_nor);
			bottombar_mine_tv
					.setTextColor(getResColor(R.color.bottombar_tv_nor));

			// if (homePage == null) {
			homePage = new FragmentHomePage();
			// }
			// 使用当前Fragment的布局替代id_content的控件
			transaction.replace(R.id.tabcontent, homePage);
			break;
		case R.id.bottombar_class:
			showTitleBar();
			bottombar_home_iv.setImageResource(R.drawable.icon_home_nor);
			bottombar_home_tv
					.setTextColor(getResColor(R.color.bottombar_tv_nor));
			bottombar_class_iv.setImageResource(R.drawable.icon_meassage_sel);
			bottombar_class_tv
					.setTextColor(getResColor(R.color.bottombar_tv_class));
			bottombar_sun_iv.setImageResource(R.drawable.icon_selfinfo_nor);
			bottombar_sun_tv
					.setTextColor(getResColor(R.color.bottombar_tv_nor));
			bottombar_square_iv.setImageResource(R.drawable.icon_square_nor);
			bottombar_square_tv
					.setTextColor(getResColor(R.color.bottombar_tv_nor));
			bottombar_mine_iv.setImageResource(R.drawable.icon_more_nor);
			bottombar_mine_tv
					.setTextColor(getResColor(R.color.bottombar_tv_nor));

			// if (classIfication == null) {
			classIfication = new FragmentClassIfication();
			// }
			// 使用当前Fragment的布局替代id_content的控件
			transaction.replace(R.id.tabcontent, classIfication);
			break;
		case R.id.bottombar_sun:
			myId = App.app.preferences.getStringMessage("app", "MyId", "0");
			if ("0".equals(myId)) {
				enterLogin();
			} else {
				View v_sun = LayoutInflater.from(this).inflate(R.layout.sun,
						null);
				v_sun.findViewById(R.id.sun_photo).setOnClickListener(this);
				v_sun.findViewById(R.id.sun_recomme).setOnClickListener(this);

				pw = new PopupWindow(v_sun, LayoutParams.FILL_PARENT,
						LayoutParams.FILL_PARENT);
				pw.setOutsideTouchable(true);
				// pw.showAsDropDown(tv_title);
				pw.showAsDropDown(tv_title, 0, DensityUtil.dip2px(this, -48));

				v_sun.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (pw != null && pw.isShowing())
							pw.dismiss();
						return false;
					}
				});
			}
			break;
		case R.id.bottombar_square:
			showTitleBar();
			bottombar_home_iv.setImageResource(R.drawable.icon_home_nor);
			bottombar_home_tv
					.setTextColor(getResColor(R.color.bottombar_tv_nor));
			bottombar_class_iv.setImageResource(R.drawable.icon_meassage_nor);
			bottombar_class_tv
					.setTextColor(getResColor(R.color.bottombar_tv_nor));
			bottombar_sun_iv.setImageResource(R.drawable.icon_selfinfo_nor);
			bottombar_sun_tv
					.setTextColor(getResColor(R.color.bottombar_tv_nor));
			bottombar_square_iv.setImageResource(R.drawable.icon_square_sel);
			bottombar_square_tv
					.setTextColor(getResColor(R.color.bottombar_tv_square));
			bottombar_mine_iv.setImageResource(R.drawable.icon_more_nor);
			bottombar_mine_tv
					.setTextColor(getResColor(R.color.bottombar_tv_nor));

			// if (square == null) {
			square = new FragmentSquare();
			// }
			// 使用当前Fragment的布局替代id_content的控件
			transaction.replace(R.id.tabcontent, square);
			break;
		case R.id.bottombar_mine:
			myId = App.app.preferences.getStringMessage("app", "MyId", "0");
			L.i(TAG, "myId=" + myId);
			if (!"0".equals(myId)) {
				bottombar_home_iv.setImageResource(R.drawable.icon_home_nor);
				bottombar_home_tv
						.setTextColor(getResColor(R.color.bottombar_tv_nor));
				bottombar_class_iv
						.setImageResource(R.drawable.icon_meassage_nor);
				bottombar_class_tv
						.setTextColor(getResColor(R.color.bottombar_tv_nor));
				bottombar_sun_iv.setImageResource(R.drawable.icon_selfinfo_nor);
				bottombar_sun_tv
						.setTextColor(getResColor(R.color.bottombar_tv_nor));
				bottombar_square_iv
						.setImageResource(R.drawable.icon_square_nor);
				bottombar_square_tv
						.setTextColor(getResColor(R.color.bottombar_tv_nor));
				bottombar_mine_iv.setImageResource(R.drawable.icon_more_sel);
				bottombar_mine_tv
						.setTextColor(getResColor(R.color.bottombar_tv_mine));

				// if (mine == null) {
				mine = new FragmentMine();
				// }
				// 使用当前Fragment的布局替代id_content的控件
				transaction.replace(R.id.tabcontent, mine);

				hideTitleBar();
			} else {
				startActivity(new Intent(this, LoginActivity.class));
			}
			break;

		case R.id.sun_photo:
			if (pw != null && pw.isShowing())
				pw.dismiss();
			Intent is = new Intent(this, SunPhotoActivity.class);
			is.putExtra("type", 1);
			startActivity(is);
			break;
		case R.id.sun_recomme:
			if (pw != null && pw.isShowing())
				pw.dismiss();
			Intent ir = new Intent(this, SunPhotoActivity.class);
			ir.putExtra("type", 0);
			startActivity(ir);
			break;
		default:
			break;
		}
		mDrawerLayout.closeDrawer(mDrawer);
		// 事务提交
		transaction.commit();
	}

	private void enterLogin() {
		startActivity(new Intent(this, LoginActivity.class));
	}

	protected void logout() {
		App.app.preferences.saveStringMessage("app", "MyId", "0");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (mDrawerLayout.isDrawerOpen(mDrawer)) {
			mDrawerLayout.closeDrawer(mDrawer);
		} else {
			mDrawerLayout.openDrawer(mDrawer);
		}
		return false;
	}

}
