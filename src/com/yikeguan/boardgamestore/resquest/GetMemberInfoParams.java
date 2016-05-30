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
import com.yikeguan.boardgamestore.response.LoginBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class GetMemberInfoParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 获取用户详细信息
	 * 
	 * @param member_id
	 *            可选项
	 */
	public GetMemberInfoParams(Context context, String member_id) {
		super(context);
		setVariable(member_id);
	}

	private void setVariable(String member_id) {
		paramsMap.put("method", "get_member_info");
		if (member_id != null && !"".equals(member_id))
			paramsMap.put("member_id", member_id);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "GetMemberInfoParams strParams=" + strParams);
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
		L.getLongLog(TAG, "GetMemberInfoParams", str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		LoginBean bean = (LoginBean) JsonParseTool.dealComplexResult(mc
				.getResult().toString(), LoginBean.class);
		mc.setResult(bean);
		return mc;
	}
}
