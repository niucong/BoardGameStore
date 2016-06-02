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
import com.yikeguan.boardgamestore.response.CommentMembersListBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.BasicParams;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class CommentGameListParams extends BasicParams {

	/**
	 * 获取某款游戏的评论列表
	 * 
	 * @param start
	 *            可选项
	 * @param limit
	 *            可选项
	 * @param id
	 *            必填项
	 */
	public CommentGameListParams(Context context, String start, String limit,
			String id) {
		super(context);
		setVariable(start, limit, id);
	}

	private void setVariable(String start, String limit, String id) {
		paramsMap.put("method", "comment_game_list");
		if (start != null && !"".equals(start))
			paramsMap.put("start", start);
		if (limit != null && !"".equals(limit))
			paramsMap.put("limit", limit);
		paramsMap.put("id", id);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "CommentGameListParams strParams=" + strParams);
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
		L.i(TAG, "CommentGameListParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		CommentMembersListBean bean = (CommentMembersListBean) JsonParseTool
				.dealComplexResult(mc.getResult().toString(),
						CommentMembersListBean.class);
		mc.setResult(bean);
		return mc;
	}
}
