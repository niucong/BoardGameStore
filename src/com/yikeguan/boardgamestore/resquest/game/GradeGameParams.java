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

public class GradeGameParams extends BasicParams {

	/**
	 * 游戏评分
	 * 
	 * @param id
	 * @param data_value
	 */
	public GradeGameParams(Context context, String id, String data_value) {
		super(context);
		setVariable(id, data_value);
	}

	private void setVariable(String id, String data_value) {
		paramsMap.put("method", "GRADE_GAME");
		paramsMap.put("id", id);
		paramsMap.put("data_value", "" + data_value);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "CollectionGameParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.postRequest(ConstantUtil.API_URL
				+ "game", getParams());
		L.i(TAG, "CollectionGameParams str=" + str);
		return (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
	}
}
