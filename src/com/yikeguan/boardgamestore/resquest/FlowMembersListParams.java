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
import com.yikeguan.boardgamestore.response.FlowMembersListBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class FlowMembersListParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 某个人关注的人群/某个人被关注的人群
	 * 
	 * @param context
	 * @param start
	 *            可选项
	 * @param limit
	 *            可选项
	 * @param id
	 *            必填项
	 * @param type
	 *            0:某个人关注的人群、1:某个人被关注的人群、2：某个人访问的人群、3：某个人被访问的人群（最近谁来过）
	 */
	public FlowMembersListParams(Context context, String start, String limit,
			String id, int type) {
		super(context);
		setVariable(start, limit, id, type);
	}

	private void setVariable(String start, String limit, String id, int type) {
		if (type == 0) {
			paramsMap.put("method", "flow_member_members_list");
		} else if (type == 1) {
			paramsMap.put("method", "flow_members_member_list");
		} else if (type == 2) {
			paramsMap.put("method", "visit_member_members_list");
		} else if (type == 3) {
			paramsMap.put("method", "visit_members_member_list");
		}
		if (start != null && !"".equals(start))
			paramsMap.put("start", start);
		if (limit != null && !"".equals(limit))
			paramsMap.put("limit", limit);
		if (id != null && !"".equals(id))
			paramsMap.put("id", id);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "FlowMembersListParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.getRequest(ConstantUtil.API_URL
				+ "member", getParams());
		L.i(TAG, "FlowMembersListParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		FlowMembersListBean bean = (FlowMembersListBean) JsonParseTool
				.dealComplexResult(mc.getResult().toString(),
						FlowMembersListBean.class);
		mc.setResult(bean);
		return mc;
	}
}
