package net.easipay.cbp.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import net.easipay.dsfc.ApplicationContextInitializer;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-4-2 下午01:19:49
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public class TestNoticeRecManager {

	private static final Logger logger = Logger
			.getLogger(TestNoticeRecManager.class);

	// 测试接口
	public static void main(String arg[]) throws Exception {
		testNoticeSacRecDiff();// 测试接收账务系统关于渠道差错处理的通知

	}

	// 测试接收账务系统关于渠道差错处理的通知
	private static void testNoticeSacRecDiff() throws Exception {
		ApplicationContextInitializer aci = new ApplicationContextInitializer();
		Map<String, String> dsfsConfig = new HashMap<String, String>();
		dsfsConfig.put("protocol", "HTTP");
		dsfsConfig.put("ip", "10.68.5.189");
		dsfsConfig.put("port", "8083");
		dsfsConfig.put("context", "dsf");

		aci.setDsfsConfig(dsfsConfig);
		aci.initApplicationContext();

		JwsClient jwsClient = new JwsClient("SAC-REC-0001");
		jwsClient.putParam("recDetailId", "1");
		jwsClient.putParam("trxSerialNo", "");
		jwsClient.putParam("bankSerialNo", "T2015063011085877200000066");
		jwsClient.putParam("recDate", "20150801");

		jwsClient.putParam("status", "S");
		jwsClient.putParam("dealType", "1");
		jwsClient.putParam("dealOper", "张三");
		JwsResult result = jwsClient.call();
		System.out.println(result.toResult());
	}

	/**
	 * httpClient的post请求方式发送ZIP流
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Boolean sendZipHttpStream(String url, String message) {
		HttpClient httpClient = new HttpClient();
		if (StringUtils.isBlank(message)) {
			logger.info("a blank message, return.");
			return false;
		}
		PostMethod postMethod = new PostMethod(url);
		postMethod.setContentChunked(true);
		postMethod.addRequestHeader("Accept", "text/plain");
		postMethod.setRequestHeader("Content-Encoding", "gzip");
		postMethod.setRequestHeader("Transfer-Encoding", "chunked");

		try {
			ByteArrayOutputStream originalContent = new ByteArrayOutputStream();
			originalContent.write(message.getBytes(Charset.forName("UTF-8")));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
			originalContent.writeTo(gzipOut);
			gzipOut.finish();

			postMethod.setRequestEntity(new ByteArrayRequestEntity(baos
					.toByteArray(), "text/plain; charset=utf-8"));
		} catch (IOException e) {
			logger.error("write message fail.", e);
			return false;
		}
		Boolean isSucc = Boolean.TRUE;
		int retry = 0;
		do {
			try {
				int status = httpClient.executeMethod(postMethod);
				if (HttpStatus.SC_OK == status) {
					if (logger.isDebugEnabled()) {
						logger.debug("send http success, url=" + url
								+ ", content=" + message);
					}
					isSucc = Boolean.TRUE;
					return isSucc;
				} else {
					String rsp = postMethod.getResponseBodyAsString();
					isSucc = Boolean.FALSE;
					logger.error("send http fail, status is: " + status
							+ ", response is: " + rsp);

				}
			} catch (HttpException e) {
				isSucc = Boolean.FALSE;
				logger.info("http exception when send http.", e);
			} catch (IOException e) {
				isSucc = Boolean.FALSE;
				logger.info("io exception when send http.", e);
			} finally {
				postMethod.releaseConnection();
			}
			logger.info("this is " + retry + " time, try next");
			return isSucc;
		} while (retry++ < 3);
	}

	/**
	 * httpClient的get请求方式
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url, String charset) throws Exception {
		/*
		 * 使用 GetMethod 来访问一个 URL 对应的网页,实现步骤: 1:生成一个 HttpClinet 对象并设置相应的参数。
		 * 2:生成一个 GetMethod 对象并设置响应的参数。 3:用 HttpClinet 生成的对象来执行 GetMethod
		 * 生成的Get方法。 4:处理响应状态码。 5:若响应正常，处理 HTTP 响应内容。 6:释放连接。
		 */
		/* 1 生成 HttpClinet 对象并设置参数 */
		HttpClient httpClient = new HttpClient();
		// 设置 Http 连接超时为5秒
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(5000);
		/* 2 生成 GetMethod 对象并设置参数 */
		GetMethod getMethod = new GetMethod(url);
		// 设置 get 请求超时为 5 秒
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		// 设置请求重试处理，用的是默认的重试处理：请求三次
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		String response = "";
		/* 3 执行 HTTP GET 请求 */
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			/* 4 判断访问的状态码 */
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("请求出错: " + getMethod.getStatusLine());
			}
			/* 5 处理 HTTP 响应内容 */
			// HTTP响应头部信息，这里简单打印
			Header[] headers = getMethod.getResponseHeaders();
			for (Header h : headers) {
				System.out
						.println(h.getName() + "------------ " + h.getValue());
			}
			// 读取 HTTP 响应内容，这里简单打印网页内容
			byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
			response = new String(responseBody, charset);
			System.out.println("----------response:" + response);
			// 读取为 InputStream，在网页内容数据量大时候推荐使用
			// InputStream response = getMethod.getResponseBodyAsStream();
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("请检查输入的URL!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			System.out.println("发生网络异常!");
			e.printStackTrace();
		} finally {
			/* 6 .释放连接 */
			getMethod.releaseConnection();
		}
		return response;
	}

	/**
	 * httpClient的post请求
	 * 
	 * @param url
	 * @param json
	 * @return
	 */

	public static JSONObject doPost(String url, JSONObject json) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		JSONObject response = null;
		try {
			/**
			 * 方式1:直接往特定URL发送Json字符串 StringEntity s = new
			 * StringEntity(json.toString()); s.setContentEncoding("UTF-8");
			 * s.setContentType("application/json");//发送json数据需要设置contentType
			 * post.setEntity(s);
			 */
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// 建立一个NameValuePair数组，用于存储欲传送的参数
			params.add(new BasicNameValuePair("content", json.toString()));
			/** 方式二:添加参数 ，往特定URL的方法的参数发送Json字符串 */
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(res.getEntity());// 返回json格式：
				response = JSONObject.fromObject(result);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}

}
