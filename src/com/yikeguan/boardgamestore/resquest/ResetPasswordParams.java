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

public class ResetPasswordParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 修改用户密码
	 * 
	 * @param context
	 * @param old_password
	 *            必填项
	 * @param new_password
	 *            必填项
	 */
	public ResetPasswordParams(Context context, String new_password,
			String confirm_password) {
		super(context);
		setVariable(new_password, confirm_password);
	}

	private void setVariable(String new_password, String confirm_password) {
		paramsMap.put("method", "reset_password");
		paramsMap.put("new_password", new_password);
		paramsMap.put("confirm_password", confirm_password);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "UpdateMemberPasswordParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.postRequest(ConstantUtil.API_URL
				+ "register", getParams());
		L.i(TAG, "UpdateMemberPasswordParams str=" + str);
		return (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
	}
}
