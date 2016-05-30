package com.yikeguan.boardgamestore.resquest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.json.JSONException;

import android.content.Context;

import com.yikeguan.boardgamestore.net.HttpRequestServers;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class UploadMemberHeadParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 修改头像信息
	 * 
	 * @param context
	 */
	public UploadMemberHeadParams(Context context) {
		super(context);
		setVariable();
	}

	private void setVariable() {
		paramsMap.put("method", "upload_member_head");
		paramsMap.put("file", "file");
		super.setVariable(true);
	}

	public Map<String, String> getParamsMap() {
		return paramsMap;
	}

	@Override
	public String getParams() {
		L.i(TAG, "UploadMemberHeadParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.postRequest(ConstantUtil.API_URL
				+ "member", getParams());
		L.i(TAG, "UploadMemberHeadParams str=" + str);
		return (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
	}
}
