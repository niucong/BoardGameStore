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
import com.yikeguan.boardgamestore.util.EncryptRequest;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class UpdateMemberPasswordParams extends BasicParams {
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
	public UpdateMemberPasswordParams(Context context, String old_password,
			String new_password) {
		super(context);
		setVariable(old_password, new_password);
	}

	private void setVariable(String old_password, String new_password) {
		paramsMap.put("method", "update_member_password");
		try {
			paramsMap.put("old_password", EncryptRequest.md5Util(old_password));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		paramsMap.put("new_password", new_password);
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
				+ "member", getParams());
		L.i(TAG, "UpdateMemberPasswordParams str=" + str);
		return (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
	}
}
