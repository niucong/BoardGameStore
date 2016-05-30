package com.yikeguan.boardgamestore.resquest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;

import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.util.ConstantUtil;
import com.yikeguan.boardgamestore.util.EncryptRequest;

public abstract class BasicParams {
	protected final String TAG = "APIRequestServers";

	/** 非数组参数集 */
	protected Map<String, String> paramsMap = null;
	protected String strParams = null;
	/** 数组参数集：某一参数是数组时 */
	protected HashMap<String, String[]> mHashMap = null;

	protected Context context;
	private String session_key = null;

	public BasicParams(Context context) {
		this.context = context;
		paramsMap = new TreeMap<String, String>();
	}

	/**
	 * 设置公共参数
	 */
	protected void setVariable(boolean hasKey) {
		if (hasKey) {
			session_key = App.app.preferences.getStringMessage("app",
					"SessionKey", "");
			// session_key = "5809eb3addb3e72a";
			if (session_key != null && !"".equals(session_key)) {
				paramsMap.put("session_key", session_key);
			} else {
				// TODO
			}
		}
		paramsMap.put("app_key", ConstantUtil.APP_KEY);
		paramsMap.put("way", ConstantUtil.WAY);
		paramsMap.put("call_id", "" + System.currentTimeMillis());

		String sig = null;
		try {
			sig = EncryptRequest.getSig(paramsMap, mHashMap);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		paramsMap.put("sig", sig);
		strParams = EncryptRequest.getParams(paramsMap, mHashMap);
	}

	public abstract String getParams();
}
