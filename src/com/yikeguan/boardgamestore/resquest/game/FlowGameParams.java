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
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.BasicParams;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class FlowGameParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 关注/取消关注游戏
	 * 
	 * @param id
	 *            必填项
	 * @param isFlow
	 *            true:关注、flase:取消关注
	 */
	public FlowGameParams(Context context, String id, boolean isFlow) {
		super(context);
		setVariable(id, isFlow);
	}

	private void setVariable(String id, boolean isFlow) {
		if (isFlow) {
			paramsMap.put("method", "flow_game");
		} else {
			paramsMap.put("method", "delete_flow_game_info");
		}
		paramsMap.put("id", id);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "FlowGameParams strParams=" + strParams);
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
		L.i(TAG, "FlowGameParams str=" + str);
		return (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
	}
}
