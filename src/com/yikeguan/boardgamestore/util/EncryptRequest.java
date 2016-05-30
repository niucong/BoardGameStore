package com.yikeguan.boardgamestore.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 对请求加�?
 * 
 * @author datacomo-160
 * 
 */
public class EncryptRequest {
	private final static String TAG = "EncryptRequest";

	/**
	 * sig 加密算法
	 * 
	 * @param map
	 * @param mHashMap
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String getSig(Map<String, String> map,
			HashMap<String, String[]> mHashMap)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		StringBuffer sb = new StringBuffer();
		boolean flag = mHashMap != null;

		for (Iterator<?> iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if (flag && mHashMap.containsKey(key)) {
				String[] values = mHashMap.get(key);
				if (values != null && values.length > 0) {
					for (String value : values) {
						if (null != value) {
							value = value.replaceAll("\n", "<br>");// .replaceAll(" ",
																	// "%20");
							sb.append(key).append("=").append(value);// URLEncoder.encode(value)
						}
					}
				}
			} else {
				String value = map.get(key);
				if (value != null) {
					value = value.replaceAll("\n", "<br>");// .replaceAll(" ",
															// "%20");
					sb.append(key).append("=").append(value);//
				}
			}
		}

		// 追加SECRET_KEY
		sb.append("secret_key=" + ConstantUtil.SECRET_KEY);

		L.i(TAG, "getSig 加密前: secretStr = " + sb.toString());
		String sig = md5Util(URLEncoder.encode(sb.toString(), "utf-8").replace(
				"+", "%20"));
		return sig;
	}

	/**
	 * 加密后拼参数
	 * 
	 * @param map
	 * @param mHashMap
	 * @return
	 */
	public static String getParams(Map<String, String> map,
			HashMap<String, String[]> mHashMap) {
		String strParams = null;
		StringBuilder sb = new StringBuilder();
		boolean flag = mHashMap != null;

		for (Iterator<?> iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();

			if (flag && mHashMap.containsKey(key)) {
				String[] values = mHashMap.get(key);
				if (values != null && values.length > 0) {
					for (String value : values) {
						if (null != value) {
							value = value.replaceAll("\n", "<br>");
							sb.append(key + "=" + value// URLEncoder.encode(value)
									+ "&");
						}
					}
				}
			} else {
				try {
					String value = map.get(key);
					if (value != null) {
						value = value.replaceAll("\n", "<br>");
						sb.append(key + "=" + value// URLEncoder.encode(value)
								+ "&");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		strParams = sb.toString();
		L.d(TAG, "getParams 加密后: strParams = " + strParams);
		return strParams.substring(0, strParams.length() - 1);
	}

	/**
	 * MD5加密字符串
	 * 
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String md5Util(String str) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		String md5str = null;

		MessageDigest md = MessageDigest.getInstance("MD5");

		md.update(str.trim().getBytes("UTF-8"));
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		md5str = buf.toString();
		return md5str;
	}

}
