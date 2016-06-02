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
import com.yikeguan.boardgamestore.response.GameBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.BasicParams;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class GetGameBasicParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 获取游戏基本信息
	 * 
	 * @param id
	 *            必填项
	 */
	public GetGameBasicParams(Context context, String id) {
		super(context);
		setVariable(id);
	}

	private void setVariable(String id) {
		paramsMap.put("method", "get_game_basic");
		paramsMap.put("id", id);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "GetGameBasicParams strParams=" + strParams);
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
		L.i(TAG, "GetGameBasicParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		GameBean bean = (GameBean) JsonParseTool.dealComplexResult(mc
				.getResult().toString(), GameBean.class);
		mc.setResult(bean);
		return mc;
	}
}
