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

public class NoticeNoreadListParams extends BasicParams {

	/**
	 * 获取未读通知列表
	 * 
	 * @param start
	 *            可选项
	 * @param limit
	 *            可选项
	 */
	public NoticeNoreadListParams(Context context, String start, String limit) {
		super(context);
		setVariable(start, limit);
	}

	private void setVariable(String start, String limit) {
		paramsMap.put("method", "notice_noread_list");
		if (start != null && !"".equals(start))
			paramsMap.put("start", start);
		if (limit != null && !"".equals(limit))
			paramsMap.put("limit", limit);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "NoticeNoreadListParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.getRequest(ConstantUtil.API_URL
				+ "notice", getParams());
		L.i(TAG, "NoticeNoreadListParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		// TODO
		TrendListBean bean = (TrendListBean) JsonParseTool.dealComplexResult(mc
				.getResult().toString(), TrendListBean.class);
		mc.setResult(bean);
		return mc;
	}
}
