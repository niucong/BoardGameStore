package com.yikeguan.boardgamestore.resquest.recommend;

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

public class RecommendCommentParams extends BasicParams {

	/**
	 * 评论晒图
	 * 
	 * @param context
	 * @param id
	 */
	public RecommendCommentParams(Context context, String id,
			String comment_content) {
		super(context);
		setVariable(id, comment_content);
	}

	private void setVariable(String id, String comment_content) {
		paramsMap.put("method", "comment_recommend");
		paramsMap.put("id", id);
		paramsMap.put("comment_content", comment_content);
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
