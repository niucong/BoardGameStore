package com.yikeguan.boardgamestore.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.NameValuePair;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.message.BasicNameValuePair;

import com.yikeguan.boardgamestore.util.L;

/**
 * Http请求服务器
 * 
 * @author datacomo-160
 * 
 */
public class HttpRequestServers {
	private final static String TAG = "HttpRequestServers";

	/**
	 * 客户端调用API的GET请求方式
	 * 
	 * @param urlStr
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ProtocolException
	 * @throws MalformedURLException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String getRequest(String urlStr, String param)
			throws UnsupportedEncodingException, ProtocolException,
			MalformedURLException, KeyManagementException,
			NoSuchAlgorithmException, IOException {
		L.d(TAG, "getRequest : " + urlStr.length() + ",urlStr = " + urlStr
				+ "?" + param);
		ArrayList<NameValuePair> parameters = null;
		if (param != null && param.contains("&")) {
			String[] ps = param.split("&");
			parameters = new ArrayList<NameValuePair>();
			for (String pstr : ps) {
				if (pstr != null && pstr.contains("=")) {
					parameters.add(new BasicNameValuePair(pstr.substring(0,
							pstr.indexOf("=")), pstr.substring(pstr
							.indexOf("=") + 1)));
				}
			}
		}
		return HTTPTools.invoke(urlStr, parameters);

		// String result = "";
		//
		// HttpURLConnection conn = null;
		// InputStream is = null;
		// ByteArrayOutputStream outStream = null;
		//
		// try {
		// URL url = new URL(urlStr + "?" + param);
		//
		// conn = getHttpURLConnection(url);
		//
		// // 设置连接超时时间
		// conn.setConnectTimeout(10 * 1000);
		// // 设置数据读取超时时间
		// conn.setReadTimeout(15 * 1000);
		// conn.setRequestMethod("GET");// 以get方式发起请求
		// is = conn.getInputStream();// 得到网络返回的输入流
		//
		// outStream = new ByteArrayOutputStream();
		// byte[] buffer = new byte[1024];
		// int len = -1;
		// while ((len = is.read(buffer)) != -1) {
		// outStream.write(buffer, 0, len);
		// }
		// byte[] data = outStream.toByteArray();
		// result = new String(data, "utf-8");
		// } finally {
		// if (outStream != null)
		// try {
		// outStream.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// if (is != null)
		// try {
		// is.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// if (conn != null)
		// conn.disconnect();
		// }
		// return result;
	}

	/**
	 * 向指定URL发送POST方法的请求
	 * 
	 * @param urlStr
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 * @throws UnsupportedEncodingException
	 * @throws ProtocolException
	 * @throws MalformedURLException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String postRequest(String urlStr, String param)
			throws UnsupportedEncodingException, ProtocolException,
			MalformedURLException, KeyManagementException,
			NoSuchAlgorithmException, IOException {
		L.d(TAG, "postRequest : " + (urlStr + "?" + param).length() + ",url = "
				+ urlStr + "?" + param);
		ArrayList<NameValuePair> parameters = null;
		if (param != null && param.contains("&")) {
			String[] ps = param.split("&");
			parameters = new ArrayList<NameValuePair>();
			for (String pstr : ps) {
				if (pstr != null && pstr.contains("=")) {
					parameters.add(new BasicNameValuePair(pstr.substring(0,
							pstr.indexOf("=")), pstr.substring(pstr
							.indexOf("=") + 1)));
				}
			}
		}
		return HTTPTools.invoke(urlStr, parameters);

		// String result = "";
		// HttpURLConnection conn = null;
		// InputStream is = null;
		// ByteArrayOutputStream outStream = null;
		// try {
		// URL url = new URL(urlStr);
		//
		// conn = getHttpURLConnection(url);
		// // 设置连接超时时间
		// conn.setConnectTimeout(10 * 1000);
		// // 设置数据读取超时时间
		// conn.setReadTimeout(15 * 1000);
		// // 设置通用的请求属性
		// // 设置文件类型:
		// conn.setRequestProperty("accept", "*/*");
		// // 设置维持长连接:
		// conn.setRequestProperty("connection", "Keep-Alive");
		// // 设置文件字符集:
		// conn.setRequestProperty("Charset", "UTF-8");
		// // 设置文件长度:
		// conn.setRequestProperty("Content-Length",
		// String.valueOf(param.length()));
		//
		// conn.setRequestProperty("user-agent",
		// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		// // 发送POST请求必须设置如下两行
		// // 设置容许输出:
		// conn.setDoOutput(true);
		// // 设置容许输入:
		// conn.setDoInput(true);
		//
		// // 设置使用POST的方式发送:
		// conn.setRequestMethod("POST");
		//
		// OutputStream outputStream = conn.getOutputStream();
		// outputStream.write(param.getBytes("UTF-8"));
		// outputStream.close();
		// is = conn.getInputStream();
		//
		// outStream = new ByteArrayOutputStream();
		// byte[] buffer = new byte[1024];
		// int len = -1;
		// while ((len = is.read(buffer)) != -1) {
		// outStream.write(buffer, 0, len);
		// }
		// byte[] data = outStream.toByteArray();
		// result = new String(data, "utf-8");
		// } finally {
		// if (outStream != null)
		// try {
		// outStream.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// if (is != null)
		// try {
		// is.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// if (conn != null)
		// conn.disconnect();
		// }
		// return result;
	}

	/**
	 * 移动网络下，防止网络超时甚至连接不上，为网络请求设置代理
	 * 
	 * @param realUrl
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static HttpURLConnection getHttpURLConnection(URL url)
			throws KeyManagementException, NoSuchAlgorithmException,
			IOException {

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		if (conn instanceof HttpsURLConnection) {
			X509TrustManager[] xtmArray = new X509TrustManager[] { xtm };
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(new KeyManager[0], xtmArray, new SecureRandom());
			SSLSocketFactory socketFactory = context.getSocketFactory();
			((HttpsURLConnection) conn).setSSLSocketFactory(socketFactory);
			((HttpsURLConnection) conn).setHostnameVerifier(HOSTNAME_VERIFIER);
		}

		// if (Build.VERSION.SDK_INT < 14) {
		// conn.setRequestProperty("Transfer-Encoding", "chunked");
		// }

		return conn;
	}

	private static final AllowAllHostnameVerifier HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
	private static X509TrustManager xtm = new X509TrustManager() {
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};
}
