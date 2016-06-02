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
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class DeleteTrendParams extends BasicParams {

	/**
	 * 删除某条我发表的动态
	 * 
	 * @param id
	 *            必填项
	 */
	public DeleteTrendParams(Context context, String id) {
		super(context);
		setVariable(id);
	}

	private void setVariable(String id) {
		paramsMap.put("method", "delete_trend");
		paramsMap.put("id", id);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "DeleteTrendParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.getRequest(ConstantUtil.API_URL
				+ "trend", getParams());
		L.i(TAG, "DeleteTrendParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		return mc;
	}
}
