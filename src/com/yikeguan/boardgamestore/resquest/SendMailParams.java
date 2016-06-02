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

public class SendMailParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 用户登录
	 * 
	 * @param loadName
	 * @param password
	 */
	public SendMailParams(Context context, String member_email) {
		super(context);
		setVariable(member_email);
	}

	private void setVariable(String member_email) {
		paramsMap.put("method", "sendMail");
		paramsMap.put("member_email", member_email);
		super.setVariable(false);
	}

	@Override
	public String getParams() {
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.postRequest(ConstantUtil.API_URL
				+ "sendMail", getParams());
		L.i(TAG, "SendMailParams str=" + str);
		return (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
	}
}
