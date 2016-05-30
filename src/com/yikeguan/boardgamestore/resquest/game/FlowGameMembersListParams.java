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
import com.yikeguan.boardgamestore.response.FlowMembersListBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.BasicParams;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class FlowGameMembersListParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 某款游戏关注、访问、收藏的人群
	 * 
	 * @param context
	 * @param start
	 *            可选项
	 * @param limit
	 *            可选项
	 * @param id
	 *            必填项
	 * @param type
	 *            0:某款游戏关注的人群、1:某款游戏访问的人群、2：获取某个游戏被收藏的人群
	 */
	public FlowGameMembersListParams(Context context, String start,
			String limit, String id, int type) {
		super(context);
		setVariable(start, limit, id, type);
	}

	private void setVariable(String start, String limit, String id, int type) {
		if (type == 0) {
			paramsMap.put("method", "flow_game_members_list");
		} else if (type == 1) {
			paramsMap.put("method", "visit_game_members_list");
		} else if (type == 2) {
			// TODO paramsMap.put("method", "visit_game_members_list");
		}

		if (start != null && !"".equals(start))
			paramsMap.put("start", start);
		if (limit != null && !"".equals(limit))
			paramsMap.put("limit", limit);
		paramsMap.put("id", id);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "FlowGameMembersListParams strParams=" + strParams);
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
		L.i(TAG, "FlowGameMembersListParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		FlowMembersListBean bean = (FlowMembersListBean) JsonParseTool
				.dealComplexResult(mc.getResult().toString(),
						FlowMembersListBean.class);
		mc.setResult(bean);
		return mc;
	}
}
