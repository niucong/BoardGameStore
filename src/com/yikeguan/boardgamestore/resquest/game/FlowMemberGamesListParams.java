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
import com.yikeguan.boardgamestore.response.FlowMemberGamesListBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.BasicParams;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class FlowMemberGamesListParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 某个人关注的游戏
	 * 
	 * @param context
	 * @param start
	 *            可选项
	 * @param limit
	 *            可选项
	 * @param id
	 *            必填项
	 */
	public FlowMemberGamesListParams(Context context, String start,
			String limit, String id) {
		super(context);
		setVariable(start, limit, id);
	}

	private void setVariable(String start, String limit, String id) {
		paramsMap.put("method", "flow_member_games_list");
		if (start != null && !"".equals(start))
			paramsMap.put("start", start);
		if (limit != null && !"".equals(limit))
			paramsMap.put("limit", limit);
		if (id != null && !"".equals(id))
			paramsMap.put("id", id);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "FlowMemberGamesListParams strParams=" + strParams);
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
		L.i(TAG, "FlowMemberGamesListParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		FlowMemberGamesListBean bean = (FlowMemberGamesListBean) JsonParseTool
				.dealComplexResult(mc.getResult().toString(),
						FlowMemberGamesListBean.class);
		mc.setResult(bean);
		return mc;
	}
}
