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

public class SendSMSParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 用户登录
	 * 
	 * @param member_phone
	 */
	public SendSMSParams(Context context, String member_phone) {
		super(context);
		setVariable(member_phone);
	}

	private void setVariable(String member_phone) {
		paramsMap.put("method", "sendSMS");
		paramsMap.put("member_phone", member_phone);
		super.setVariable(false);
	}

	@Override
	public String getParams() {
		L.i(TAG, "SendSMS strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.postRequest(ConstantUtil.API_URL
				+ "sendSMS", getParams());
		L.i(TAG, "SendSMS str=" + str);
		return (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
	}
}
