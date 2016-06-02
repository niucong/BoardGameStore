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

public class UpdateMemberInfoParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 修改用户基本信息
	 * 
	 * @param context
	 * @param member_name
	 * @param member_sex
	 * @param member_mood
	 */
	public UpdateMemberInfoParams(Context context, String member_name,
			String member_sex, String member_mood) {
		super(context);
		setVariable(member_name, member_sex, member_mood);
	}

	private void setVariable(String member_name, String member_sex,
			String member_mood) {
		paramsMap.put("method", "update_member_info");
		if (member_name != null && !"".equals(member_name))
			paramsMap.put("member_name", member_name);
		if (member_sex != null && !"".equals(member_sex))
			paramsMap.put("member_sex", member_sex);
		if (member_mood != null && !"".equals(member_mood))
			paramsMap.put("member_mood", member_mood);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "UpdateMemberInfoParams strParams=" + strParams);
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
		L.i(TAG, "UpdateMemberInfoParams str=" + str);
		return (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
	}
}
