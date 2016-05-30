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

public class RegisterThreeParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 第三方注册
	 * 
	 * @param context
	 * @param open_id
	 *            必填项
	 * @param three_souce
	 *            必填项 1微信2QQ 3新浪 0 其它
	 * @param member_name
	 *            可选项
	 * @param head_url
	 *            可选项
	 * @param head_path
	 *            可选项
	 * @param member_sex
	 *            可选项
	 * @param member_mood
	 *            可选项
	 */
	public RegisterThreeParams(Context context, String open_id,
			String three_souce, String member_name, String head_url,
			String head_path, String member_sex, String member_mood) {
		super(context);
		setVariable(open_id, three_souce, member_name, head_url, head_path,
				member_sex, member_mood);
	}

	private void setVariable(String open_id, String three_souce,
			String member_name, String head_url, String head_path,
			String member_sex, String member_mood) {
		paramsMap.put("method", "register_member_three");
		paramsMap.put("open_id", open_id);
		paramsMap.put("three_souce", three_souce);

		if (member_name != null && !"".equals(member_name))
			paramsMap.put("member_name", member_name);
		if (head_url != null && !"".equals(head_url))
			paramsMap.put("head_url", head_url);
		if (head_path != null && !"".equals(head_path))
			paramsMap.put("head_path", head_path);
		if (member_sex != null && !"".equals(member_sex))
			paramsMap.put("member_sex", member_sex);
		if (member_mood != null && !"".equals(member_mood))
			paramsMap.put("member_mood", member_mood);
		super.setVariable(false);
	}

	@Override
	public String getParams() {
		return strParams;
	}

	public ResultModel getResult() throws KeyManagementException,
			UnsupportedEncodingException, ProtocolException,
			MalformedURLException, NoSuchAlgorithmException, IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		String str = HttpRequestServers.postRequest(ConstantUtil.API_URL
				+ "register", getParams());
		L.i(TAG, "registerThree str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
//		LoginBean bean = (LoginBean) JsonParseTool.dealComplexResult(mc
//				.getResult().toString(), LoginBean.class);
//		mc.setResult(bean);
		return mc;
	}
}
