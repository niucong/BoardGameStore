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
import com.yikeguan.boardgamestore.response.DesignerListBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.BasicParams;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class GameHotDesignerListParams extends BasicParams {

	/**
	 * 设计师列表
	 * 
	 * @param order
	 *            可选项
	 * @param start
	 *            可选项
	 * @param limit
	 *            可选项
	 */
	public GameHotDesignerListParams(Context context, String order,
			String start, String limit) {
		super(context);
		setVariable(order, start, limit);
	}

	private void setVariable(String order, String start, String limit) {
		paramsMap.put("method", "game_hot_designer_list");
		if (order != null && !"".equals(order))
			paramsMap.put("order", order);
		if (start != null && !"".equals(start))
			paramsMap.put("start", start);
		if (limit != null && !"".equals(limit))
			paramsMap.put("limit", limit);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "GameDesignerListParams strParams=" + strParams);
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
		L.i(TAG, "GameDesignerListParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);

		DesignerListBean bean = (DesignerListBean) JsonParseTool
				.dealComplexResult(mc.getResult().toString(),
						DesignerListBean.class);
		mc.setResult(bean);
		return mc;
	}
}
