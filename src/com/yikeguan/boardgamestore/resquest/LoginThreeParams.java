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
import com.yikeguan.boardgamestore.response.LoginBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class LoginThreeParams extends BasicParams {

	/**
	 * 用户登录
	 * 
	 * @param open_id
	 */
	public LoginThreeParams(Context context, String open_id) {
		super(context);
		setVariable(open_id);
	}

	private void setVariable(String open_id) {
		paramsMap.put("method", "log_by_three");
		paramsMap.put("open_id", open_id);
		super.setVariable(false);
	}

	@Override
	public String getParams() {
		L.i(TAG, "LoginParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.postRequest(ConstantUtil.API_URL
				+ "login.json", getParams());
		L.i(TAG, "LoginParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		LoginBean bean = (LoginBean) JsonParseTool.dealComplexResult(mc
				.getResult().toString(), LoginBean.class);
		mc.setResult(bean);
		return mc;
	}
}
