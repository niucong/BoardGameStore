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

public class FindPasswordEmailParams extends BasicParams {

	/**
	 * 手机号注册
	 * 
	 * @param context
	 * @param member_email
	 *            必填项
	 * @param validate_code
	 */
	public FindPasswordEmailParams(Context context, String member_email,
			String validate_code) {
		super(context);
		setVariable(member_email, validate_code);
	}

	private void setVariable(String member_email, String validate_code) {
		paramsMap.put("method", "find_password_email");
		paramsMap.put("member_email", member_email);
		paramsMap.put("validate_code", validate_code);
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
				+ "register", getParams());
		L.i(TAG, "getResult str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		return mc;
	}
}