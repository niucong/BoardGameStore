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
import com.yikeguan.boardgamestore.response.FindMemberListBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class FindMemberListParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 获取用户列表
	 * 
	 * @param context
	 * @param start
	 *            可选项
	 * @param limit
	 *            可选项
	 * @param member_name
	 *            可选项
	 */
	public FindMemberListParams(Context context, String start, String limit,
			String member_name) {
		super(context);
		setVariable(start, limit, member_name);
	}

	private void setVariable(String start, String limit, String member_name) {
		paramsMap.put("method", "find_member_list");
		if (start != null && !"".equals(start))
			paramsMap.put("start", start);
		if (limit != null && !"".equals(limit))
			paramsMap.put("limit", limit);
		if (member_name != null && !"".equals(member_name))
			paramsMap.put("member_name", member_name);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "FindMemberListParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.getRequest(ConstantUtil.API_URL
				+ "member", getParams());
		L.i(TAG, "FindMemberListParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		FindMemberListBean bean = (FindMemberListBean) JsonParseTool
				.dealComplexResult(mc.getResult().toString(),
						FindMemberListBean.class);
		mc.setResult(bean);
		return mc;
	}
}
