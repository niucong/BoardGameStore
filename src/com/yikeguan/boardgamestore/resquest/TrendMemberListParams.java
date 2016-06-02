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
import com.yikeguan.boardgamestore.response.TrendListBean;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class TrendMemberListParams extends BasicParams {

	/**
	 * 获取某个人的动态
	 * 
	 * @param id
	 *            可选项
	 * @param start
	 *            可选项
	 * @param limit
	 *            可选项
	 */
	public TrendMemberListParams(Context context, String id, String start,
			String limit, String trend_type) {
		super(context);
		setVariable(id, start, limit, trend_type);
	}

	private void setVariable(String id, String start, String limit,
			String trend_type) {
		paramsMap.put("method", "trend_member_list");
		if (id != null && !"".equals(id))
			paramsMap.put("id", id);
		if (start != null && !"".equals(start))
			paramsMap.put("start", start);
		if (limit != null && !"".equals(limit))
			paramsMap.put("limit", limit);
		if (trend_type != null && !"".equals(trend_type))
			paramsMap.put("trend_type", trend_type);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "TrendMemberListParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.getRequest(ConstantUtil.API_URL
				+ "trend", getParams());
		L.i(TAG, "TrendMemberListParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		TrendListBean bean = (TrendListBean) JsonParseTool.dealComplexResult(mc
				.getResult().toString(), TrendListBean.class);
		mc.setResult(bean);
		return mc;
	}
}
