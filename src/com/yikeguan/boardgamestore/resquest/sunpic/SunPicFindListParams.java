package com.yikeguan.boardgamestore.resquest.sunpic;

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
import com.yikeguan.boardgamestore.response.SunPicFindListBean;
import com.yikeguan.boardgamestore.resquest.BasicParams;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class SunPicFindListParams extends BasicParams {

	/**
	 * 获取晒图列表/获取某人晒图列表
	 * 
	 * @param context
	 * @param id
	 */
	public SunPicFindListParams(Context context, String id, String start,
			String limit) {
		super(context);
		setVariable(id, start, limit);
	}

	private void setVariable(String id, String start, String limit) {
		paramsMap.put("method", "find_sunpic_list");
		if (id != null && !"".equals(id) && !"0".equals(id))
			paramsMap.put("id", id);
		paramsMap.put("start", start);
		paramsMap.put("limit", limit);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "SunPicFindListParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.getRequest(ConstantUtil.API_URL
				+ "object", getParams());
		L.i(TAG, "SunPicFindListParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		SunPicFindListBean bean = (SunPicFindListBean) JsonParseTool
				.dealComplexResult(mc.getResult().toString(),
						SunPicFindListBean.class);
		mc.setResult(bean);
		return mc;
	}
}
