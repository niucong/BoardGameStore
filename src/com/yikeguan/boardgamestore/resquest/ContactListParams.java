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
import com.yikeguan.boardgamestore.response.ContactListBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.JsonParseTool;
import com.yikeguan.boardgamestore.util.L;

public class ContactListParams extends BasicParams {

	/**
	 * 获取联系人列表
	 * 
	 * @param start
	 *            可选项
	 * @param limit
	 *            可选项
	 */
	public ContactListParams(Context context, String start, String limit) {
		super(context);
		setVariable(start, limit);
	}

	private void setVariable(String start, String limit) {
		paramsMap.put("method", "contact_list");
		if (start != null && !"".equals(start))
			paramsMap.put("start", start);
		if (limit != null && !"".equals(limit))
			paramsMap.put("limit", limit);
		super.setVariable(true);
	}

	@Override
	public String getParams() {
		L.i(TAG, "ContactListParams strParams=" + strParams);
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.getRequest(ConstantUtil.API_URL
				+ "message", getParams());
		L.i(TAG, "ContactListParams str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		ContactListBean bean = (ContactListBean) JsonParseTool
				.dealComplexResult(mc.getResult().toString(),
						ContactListBean.class);
		mc.setResult(bean);
		return mc;
	}
}
