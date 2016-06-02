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

public class DeleteNoticeParams extends BasicParams {

	/**
	 * 删除某条通知
	 * 
	 * @param id
	 *            必填项
	 */
	public DeleteNoticeParams(Context context, String id) {
		super(context);
		setVariable(id);
	}

	private void setVariable(String id) {
		paramsMap.put("method", "delete_notice");
		paramsMap.put("id", id);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "DeleteNoticeParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.postRequest(ConstantUtil.API_URL
				+ "notice", getParams());
		L.i(TAG, "DeleteNoticeParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		return mc;
	}
}
