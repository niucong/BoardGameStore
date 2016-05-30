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

public class CreateSuggestionParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 提交意见建议
	 * 
	 * @param context
	 * @param suggestion_desc
	 * @param member_phone
	 */
	public CreateSuggestionParams(Context context, String suggestion_desc,
			String member_phone) {
		super(context);
		setVariable(suggestion_desc, member_phone);
	}

	private void setVariable(String suggestion_desc, String member_phone) {
		paramsMap.put("method", "create_suggestion");
		paramsMap.put("suggestion_desc", suggestion_desc);
		paramsMap.put("suggestion_type", "1");
		if (!"".equals(member_phone))
			paramsMap.put("member_phone", member_phone);
		// paramsMap.put("terminal_type", terminal_type);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "CreateSuggestionParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.postRequest(ConstantUtil.API_URL
				+ "system", getParams());
		L.i(TAG, "CreateSuggestionParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		return mc;
	}
}
