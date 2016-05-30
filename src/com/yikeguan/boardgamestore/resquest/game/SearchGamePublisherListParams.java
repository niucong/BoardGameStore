package com.yikeguan.boardgamestore.resquest.game;

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
import com.yikeguan.boardgamestore.response.FindGameListBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.BasicParams;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class SearchGamePublisherListParams extends BasicParams {

	/**
	 * 搜索游戏列表-出版社
	 * 
	 * @param id
	 *            必填项
	 * @param start
	 *            可选项
	 * @param limit
	 *            可选项
	 */
	public SearchGamePublisherListParams(Context context, String id,
			String start, String limit) {
		super(context);
		setVariable(id, start, limit);
	}

	private void setVariable(String id, String start, String limit) {
		paramsMap.put("method", "search_game_publisher_list");
		paramsMap.put("id", id);
		if (start != null && !"".equals(start))
			paramsMap.put("start", start);
		if (limit != null && !"".equals(limit))
			paramsMap.put("limit", limit);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "SearchGamePublisherListParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.getRequest(ConstantUtil.API_URL
				+ "game", getParams());
		L.i(TAG, "SearchGamePublisherListParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		FindGameListBean bean = (FindGameListBean) JsonParseTool
				.dealComplexResult(mc.getResult().toString(),
						FindGameListBean.class);
		mc.setResult(bean);
		return mc;
	}
}
