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

public class SearchGameListParams extends BasicParams {

	/**
	 * 搜索游戏列表-模糊搜索
	 * 
	 * @param start
	 *            可选项
	 * @param limit
	 *            可选项
	 * @param main_game_name
	 *            可选项
	 * @param publisher
	 *            可选项
	 * @param designer
	 *            可选项
	 */
	public SearchGameListParams(Context context, String start, String limit,
			String main_game_name, String publisher, String designer) {
		super(context);
		setVariable(start, limit, main_game_name, publisher, designer);
	}

	private void setVariable(String start, String limit, String main_game_name,
			String publisher, String designer) {
		paramsMap.put("method", "search_game_list");
		if (start != null && !"".equals(start))
			paramsMap.put("start", start);
		if (limit != null && !"".equals(limit))
			paramsMap.put("limit", limit);
		if (main_game_name != null && !"".equals(main_game_name))
			paramsMap.put("main_game_name", main_game_name);
		if (publisher != null && !"".equals(publisher))
			paramsMap.put("publisher", publisher);
		if (designer != null && !"".equals(designer))
			paramsMap.put("designer", designer);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "SearchGameListParams strParams=" + strParams);
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
		L.i(TAG, "SearchGameListParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		FindGameListBean bean = (FindGameListBean) JsonParseTool
				.dealComplexResult(mc.getResult().toString(),
						FindGameListBean.class);
		mc.setResult(bean);
		return mc;
	}
}
