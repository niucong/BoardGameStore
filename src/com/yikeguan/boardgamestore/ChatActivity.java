package com.yikeguan.boardgamestore;

import java.io.File;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.ImageMessageBody;
import com.easemob.chat.TextMessageBody;
import com.yikeguan.boardgamestore.adapter.MessageAdapter;
import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.dialog.DialogController;
import com.yikeguan.boardgamestore.dialog.DialogController.DialogCallBack;
import com.yikeguan.boardgamestore.response.LoginBean;
import com.yikeguan.boardgamestore.resquest.SendMessageParams;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.FaceUtil;
import com.yikeguan.boardgamestore.util.L;
import com.yikeguan.boardgamestore.view.FacesView;
import com.yikeguan.boardgamestore.view.FacesView.OnFaceChosenListner;

public class ChatActivity extends BasicActivity {
	private static final String TAG = "ChatActivity";

	private ImageView iv_title_left;
	private TextView tv_title;
	private ListView listView;
	private EditText et;
	private MessageAdapter adapter;

	LoginBean member;
	String mId, fHead;
	private String pictureName = null;
	private final int FROM_GALLERY = 0;
	private final int FROM_CAMERA = 1;
	public static final int REQUEST_CODE_TEXT = 5;

	private int chatType = 1;
	private final int pagesize = 20;
	static int resendPos;

	boolean sending = false;

	private EMConversation conversation;
	private NewMessageBroadcastReceiver receiver;

	public ListView getListView() {
		return listView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		if (Build.VERSION.SDK_INT < 19)
			findViewById(R.id.title_bar).setPadding(0, 0, 0, 0);

		member = (LoginBean) getIntent().getSerializableExtra("LoginBean");
		if (member == null) {
			mId = getIntent().getStringExtra("Id");
		} else {
			mId = member.getMember_id();
			fHead = member.getHead_url() + member.getHead_path();
		}

		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		tv_title = (TextView) findViewById(R.id.tv_title);

		conversation = EMChatManager.getInstance().getConversation(mId);
		// 把此会话的未读数置为0
		conversation.resetUnreadMsgCount();

		// 初始化db时，每个conversation加载数目是getChatOptions().getNumberOfMessagesLoaded
		// 这个数目如果比用户期望进入会话界面时显示的个数不一样，就多加载一些
		final List<EMMessage> msgs = conversation.getAllMessages();
		int msgCount = msgs != null ? msgs.size() : 0;
		if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
			String msgId = null;
			if (msgs != null && msgs.size() > 0) {
				msgId = msgs.get(0).getMsgId();
			}
			conversation.loadMoreMsgFromDB(msgId, pagesize);
		}

		loadmorePB = (ProgressBar) findViewById(R.id.pb_load_more);
		listView = (ListView) findViewById(R.id.print_detail_list);
		adapter = new MessageAdapter(this, mId, chatType, fHead);
		// 显示消息
		listView.setAdapter(adapter);
		listView.setOnScrollListener(new ListScrollListener());
		int count = listView.getCount();
		if (count > 0) {
			listView.setSelection(count - 1);
		}
		adapter.refresh();

		iv_title_left.setImageResource(R.drawable.icon_back);
		if (member != null)
			tv_title.setText(member.getMember_name());

		iv_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		et = (EditText) findViewById(R.id.input_text);

		findViewById(R.id.input_send).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String cStr = et.getText().toString();
				if (!"".equals(cStr.trim())) {
					EMMessage message = EMMessage
							.createSendMessage(EMMessage.Type.TXT);
					TextMessageBody txtBody = new TextMessageBody(cStr);
					// 设置消息body
					message.addBody(txtBody);
					// 设置要发给谁,用户username或者群聊groupid
					message.setReceipt(mId);
					// 把messgage加到conversation中
					conversation.addMessage(message);
					// 通知adapter有消息变动，adapter会根据加入的这条message显示消息和调用sdk的发送方法
					adapter.refresh();
					listView.setSelection(listView.getCount() - 1);
					et.setText("");

					if (!sending) {
						new Thread() {
							public void run() {
								sending = true;
								try {
									new SendMessageParams(App.app, mId, cStr)
											.getResult();
								} catch (Exception e) {
									e.printStackTrace();
								}
								sending = false;
							};
						}.start();
					}
				}
			}
		});

		initFace();
		et.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showKeyBoard(v);
			}
		});
		findViewById(R.id.input_face).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showFace();
			}
		});
		findViewById(R.id.input_face).setVisibility(View.GONE);
		findViewById(R.id.input_photo).setVisibility(View.GONE);
		findViewById(R.id.input_photo).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						DialogController.selectDialog(ChatActivity.this,
								new String[] { "手机相册", "相机拍照" },
								new DialogCallBack() {

									@Override
									public void onDlgCallBack(int which) {
										if (which == 0) {
											Intent intent = new Intent(
													Intent.ACTION_GET_CONTENT);
											intent.setType("image/*");
											startActivityForResult(intent,
													FROM_GALLERY);
										} else {
											Intent intent = new Intent(
													MediaStore.ACTION_IMAGE_CAPTURE);
											pictureName = System
													.currentTimeMillis()
													+ ".jpg";
											File saveFile = new File(
													ConstantUtil.CAMERA_PATH);
											if (!saveFile.exists()) {
												saveFile.mkdirs();
											}

											intent.putExtra(
													MediaStore.Images.Media.ORIENTATION,
													0);
											intent.putExtra(
													MediaStore.EXTRA_OUTPUT,
													Uri.fromFile(new File(
															saveFile,
															pictureName)));
											startActivityForResult(intent,
													FROM_CAMERA);
										}
									}
								});
					}
				});

		// 注册接收消息广播
		receiver = new NewMessageBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(EMChatManager
				.getInstance().getNewMessageBroadcastAction());
		// 设置广播的优先级别大于Mainacitivity,这样如果消息来的时候正好在chat页面，直接显示消息，而不是提示消息未读
		intentFilter.setPriority(5);
		registerReceiver(receiver, intentFilter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 注销广播
		try {
			unregisterReceiver(receiver);
			receiver = null;
		} catch (Exception e) {
		}
	}

	public static final int CHATTYPE_SINGLE = 1;
	private ProgressBar loadmorePB;
	private boolean isloading;
	private boolean haveMoreData = true;

	/**
	 * listview滑动监听listener
	 * 
	 */
	private class ListScrollListener implements OnScrollListener {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_IDLE:
				if (view.getFirstVisiblePosition() == 0 && !isloading
						&& haveMoreData) {
					loadmorePB.setVisibility(View.VISIBLE);
					// sdk初始化加载的聊天记录为20条，到顶时去db里获取更多
					List<EMMessage> messages;
					try {
						// 获取更多messges，调用此方法的时候从db获取的messages
						// sdk会自动存入到此conversation中
						if (chatType == CHATTYPE_SINGLE)
							messages = conversation.loadMoreMsgFromDB(adapter
									.getItem(0).getMsgId(), pagesize);
						else
							messages = conversation.loadMoreGroupMsgFromDB(
									adapter.getItem(0).getMsgId(), pagesize);
					} catch (Exception e1) {
						loadmorePB.setVisibility(View.GONE);
						return;
					}
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
					}
					if (messages.size() != 0) {
						// 刷新ui
						adapter.notifyDataSetChanged();
						listView.setSelection(messages.size() - 1);
						if (messages.size() != pagesize)
							haveMoreData = false;
					} else {
						haveMoreData = false;
					}
					loadmorePB.setVisibility(View.GONE);
					isloading = false;

				}
				break;
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

		}

	}

	/**
	 * 消息广播接收者
	 * 
	 */
	private class NewMessageBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 记得把广播给终结掉
			abortBroadcast();

			String username = intent.getStringExtra("from");
			String msgid = intent.getStringExtra("msgid");
			// 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
			EMMessage message = EMChatManager.getInstance().getMessage(msgid);
			// 如果是群聊消息，获取到group id
			if (message.getChatType() == ChatType.GroupChat) {
				username = message.getTo();
			}
			if (!username.equals(mId)) {
				// 消息不是发给当前会话，return
				// TODO notifyNewMessage(message);
				return;
			}
			// conversation =
			// EMChatManager.getInstance().getConversation(toChatUsername);
			// 通知adapter有新消息，更新ui
			adapter.refresh();
			listView.setSelection(listView.getCount() - 1);

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
				uri = data.getData();
				filePath = queryPhoto(uri);
				L.d(TAG, "onActivityResult filePath=" + filePath);
				sendPhoto(filePath);
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
				sendPhoto(filePath);
				break;
			case REQUEST_CODE_TEXT:
				resendMessage();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 重发消息
	 */
	private void resendMessage() {
		EMMessage msg = null;
		msg = conversation.getMessage(resendPos);
		// msg.setBackSend(true);
		msg.status = EMMessage.Status.CREATE;

		adapter.refresh();
		listView.setSelection(resendPos);
	}

	private void sendPhoto(String filePath) {
		EMConversation conversation = EMChatManager.getInstance()
				.getConversation(mId);
		EMMessage message = EMMessage.createSendMessage(EMMessage.Type.IMAGE);
		// // 如果是群聊，设置chattype,默认是单聊
		// message.setChatType(ChatType.GroupChat);

		ImageMessageBody body = new ImageMessageBody(new File(filePath));
		// 默认超过100k的图片会压缩后发给对方，可以设置成发送原图
		// body.setSendOriginalImage(true);
		message.addBody(body);
		message.setReceipt(mId);
		conversation.addMessage(message);
		EMChatManager.getInstance().sendMessage(message, new EMCallBack() {

			@Override
			public void onError(int arg0, String arg1) {

			}

			@Override
			public void onProgress(int arg0, String arg1) {

			}

			@Override
			public void onSuccess() {

			}
		});
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

	private void initFace() {
		final FacesView faceView = (FacesView) findViewById(R.id.input_facesview);
		faceView.setOnFaceChosenListner(new OnFaceChosenListner() {

			@Override
			public void onChosen(String text, int resId) {
				FacesView.doEditChange(ChatActivity.this, et, text, resId);
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
				FacesView.doEditChange(ChatActivity.this, et, text, resId);
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
	 * 表情输入
	 */
	private void showFace() {
		InputMethodManager inputMethodManager = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
		inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		findViewById(R.id.input_faces).setVisibility(View.VISIBLE);
		et.requestFocus();
	}

	/**
	 * 键盘输入
	 * 
	 * @param view
	 */
	private void showKeyBoard(View view) {
		InputMethodManager inputMethodManager = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
		findViewById(R.id.input_faces).setVisibility(View.GONE);
		try {
			inputMethodManager.showSoftInputFromInputMethod(this
					.getCurrentFocus().getWindowToken(), 0);
			inputMethodManager.showSoftInput(et, 0);
			et.requestFocus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
