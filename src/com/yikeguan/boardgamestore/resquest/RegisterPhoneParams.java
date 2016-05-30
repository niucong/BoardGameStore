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

public class RegisterPhoneParams extends BasicParams {
	private final String TAG = "APIRequestServers";

	/**
	 * 手机号注册
	 * 
	 * @param context
	 * @param member_phone
	 *            必填项
	 * @param load_password
	 *            必填项
	 * @param validate_code
	 *            必填项
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
	public RegisterPhoneParams(Context context, String member_phone,
			String load_password, String validate_code, String member_name,
			String head_url, String head_path, String member_sex,
			String member_mood) {
		super(context);
		setVariable(member_phone, load_password, validate_code, member_name,
				head_url, head_path, member_sex, member_mood);
	}

	private void setVariable(String member_phone, String load_password,
			String validate_code, String member_name, String head_url,
			String head_path, String member_sex, String member_mood) {
		paramsMap.put("method", "register_member_phone");
		paramsMap.put("member_phone", member_phone);
		paramsMap.put("load_password", load_password);
		paramsMap.put("validate_code", validate_code);
		
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
		L.i(TAG, "SendSMS str=" + str);
		ResultModel mc = (ResultModel) JsonParseTool.dealSingleResult(str,
				ResultModel.class);
		if (mc.getCode() == 1) {
			LoginBean bean = (LoginBean) JsonParseTool.dealComplexResult(mc
					.getResult().toString(), LoginBean.class);
			mc.setResult(bean);
		}
		return mc;
	}
}
