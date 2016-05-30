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

public class TypeGameListParams extends BasicParams {

	/**
	 * 获取游戏列表
	 * 
	 * @param start
	 *            可选项
	 * @param limit
	 *            可选项
	 * @param game_type
	 *            可选项
	 */
	public TypeGameListParams(Context context, String start, String limit,
			String game_type) {
		super(context);
		setVariable(start, limit, game_type);
	}

	private void setVariable(String start, String limit, String game_type) {
		paramsMap.put("method", "type_games_list");
		if (start != null && !"".equals(start))
			paramsMap.put("start", start);
		if (limit != null && !"".equals(limit))
			paramsMap.put("limit", limit);
		if (game_type != null && !"".equals(game_type))
			paramsMap.put("game_type", game_type);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "FindGameListParams strParams=" + strParams);
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
		L.i(TAG, "FindGameListParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		FindGameListBean bean = (FindGameListBean) JsonParseTool
				.dealComplexResult(mc.getResult().toString(),
						FindGameListBean.class);
		mc.setResult(bean);
		return mc;
	}
}
