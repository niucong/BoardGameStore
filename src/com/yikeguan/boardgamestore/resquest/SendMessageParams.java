package com.yikeguan.boardgamestore.resquest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;

import android.content.Context;

import com.yikeguan.boardgamestore.net.HttpRequestServers;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class SendMessageParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 发送私信
	 * 
	 * @param context
	 * @param id
	 * @param message_content
	 */
	public SendMessageParams(Context context, String id, String message_content) {
		super(context);
		setVariable(id, message_content);
	}

	private void setVariable(String id, String message_content) {
		paramsMap.put("method", "send_message");
		paramsMap.put("id", id);
		paramsMap.put("message_content", message_content);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "SendMessageParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.postRequest(ConstantUtil.API_URL
				+ "message", getParams());
		L.i(TAG, "SendMessageParams str=" + str);
		return (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
	}
}
