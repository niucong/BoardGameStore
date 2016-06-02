/**
 * 与服务器通信交互类
 */
package com.yikeguan.boardgamestore.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;

import android.net.http.AndroidHttpClient;

public abstract class HTTPTools {
	// private static final String TAG = "HTTPTools";

	private static CookieStore cookieStore;// 定义一个Cookie来保存session

	/**
	 * 向服务器发送URL请求 获得返回数据
	 * 
	 * @param doUrl
	 *            stauts的类名
	 * @param actionName
	 *            方法名
	 * @param params
	 *            传递的参数
	 * @return 获得返回的JSON结果
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String invoke(String url, List<NameValuePair> params)
			throws ClientProtocolException, IOException {
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		if (params != null && params.size() > 0) {
			HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(entity);
		}

		// DefaultHttpClient httpClient = new DefaultHttpClient();
		// // 添加Cookie
		// if (cookieStore != null) {
		// httpClient.setCookieStore(cookieStore);
		// }
		// HttpResponse httpResponse = httpClient.execute(httpPost);

		/*
		 * 类android.net.http.* 实际上是通过对 Apache 的 HttpClient 的封装来实现的一个 HTTP
		 * 编程接口，同时还提供了 HTTP 请求队列管理、以及 HTTP
		 * 连接池管理，以提高并发请求情况下（如转载网页时）的处理效率，除此之外还有网络状态监视等接口。
		 * 
		 * AndroidHttpClient不能在主线程中execute，会抛出异常。
		 * AndroidHttpClient通过静态方法newInstance获得实例
		 * ，参数是代理，不用代理的话填“”。DefaultHttpClient默认是启用Cookie的
		 * ，AndroidHttpClient默认不启用Cookie
		 * ，要使用的话每次execute时要加一个HttpContext参数，并且添加CookieStore
		 * 。用完后别忘了close不然不能创建新实例。
		 */
		BasicHttpContext context = new BasicHttpContext();
		if (cookieStore == null) {
			cookieStore = new BasicCookieStore();
		}
		context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");
		HttpResponse httpResponse = httpClient.execute(httpPost, context);

		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				httpResponse.getEntity().getContent()));
		for (String s = reader.readLine(); s != null; s = reader.readLine()) {
			builder.append(s);
		}
		result = builder.toString();

		httpClient.close();

		// 保存Cookie
		// cookieStore = httpClient.getCookieStore();
		return result;
	}

}
