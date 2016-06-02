package com.yikeguan.boardgamestore.resquest.group;

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
import com.yikeguan.boardgamestore.response.SunpicInfoBean;
import com.yikeguan.boardgamestore.resquest.BasicParams;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class SunPicInfoParams extends BasicParams {

	/**
	 * 获取单个晒图详情
	 * 
	 * @param context
	 * @param id
	 */
	public SunPicInfoParams(Context context, String id) {
		super(context);
		setVariable(id);
	}

	private void setVariable(String id) {
		paramsMap.put("method", "get_sunpic_info");
		paramsMap.put("id", id);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "SunPicCreateParams strParams=" + strParams);
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
		L.i(TAG, "SunPicCreateParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		SunpicInfoBean bean = (SunpicInfoBean) JsonParseTool.dealComplexResult(
				mc.getResult().toString(), SunpicInfoBean.class);
		mc.setResult(bean);
		return mc;
	}
}
