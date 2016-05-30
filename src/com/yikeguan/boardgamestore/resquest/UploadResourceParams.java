package com.yikeguan.boardgamestore.resquest;

import java.util.Map;

import android.content.Context;

/**
 * 上传头像
 * 
 * @author datacomo-160
 * 
 */
public class UploadResourceParams extends BasicParams {

	/**
	 * 上传文件参数设置
	 */
	public UploadResourceParams(Context context, String data_type,
			String data_value) {
		super(context);
		setVariable(data_type, data_value);
	}

	/**
	 * 设置参数
	 */
	private void setVariable(String data_type, String data_value) {
//		paramsMap.put("method", "uploadResource");
		paramsMap.put("data_type", data_type);
		paramsMap.put("data_value", data_value);

		super.setVariable(true);
	}

	public Map<String, String> getParamsMap() {
		return paramsMap;
	}

	@Override
	public String getParams() {
		return strParams;
	}
}
