package com.yikeguan.boardgamestore.resquest.group;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.json.JSONException;

import android.content.Context;

import com.yikeguan.boardgamestore.net.HttpRequestServers;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.BasicParams;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class SunPicCreateParams extends BasicParams {

	/**
	 * 发布晒图
	 * 
	 * @param context
	 * @param obj_name
	 * @param obj_desc
	 * @param photos
	 * @param files
	 */
	public SunPicCreateParams(Context context, String obj_name,
			String obj_desc, String[] photos, String[] files) {
		super(context);
		setVariable(obj_name, obj_desc, photos, files);
	}

	private void setVariable(String obj_name, String obj_desc, String[] photos,
			String[] files) {
		paramsMap.put("method", "create_sunpic");
		paramsMap.put("obj_name", obj_name);
		paramsMap.put("obj_desc", obj_desc);

		mHashMap = new HashMap<String, String[]>();
		if (photos != null && photos.length > 0) {
			paramsMap.put("photos", "");
			mHashMap.put("photos", photos);
		}
		if (files != null && files.length > 0) {
			paramsMap.put("files", "");
			mHashMap.put("files", files);
		}
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
		String str = HttpRequestServers.postRequest(ConstantUtil.API_URL
				+ "object", getParams());
		L.i(TAG, "SunPicCreateParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);

		return mc;
	}
}
