package net.easipay.cbp.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import org.apache.log4j.Logger;

public class HttpRequestSAC
{
	private static final Logger logger = Logger.getLogger(HttpRequestSAC.class);

	private String serverUrl;//请求目标地址头
	private int connectTimeout;//连接超时时间
    private int readTimeout;//请求超时时间
	private String requestMode;//请求模式 get/post
	
	public String getServerUrl()
	{
		return serverUrl;
	}

	public void setServerUrl(String serverUrl)
	{
		this.serverUrl = serverUrl;
	}

	public int getConnectTimeout()
	{
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout)
	{
		this.connectTimeout = connectTimeout;
	}

	public int getReadTimeout()
	{
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout)
	{
		this.readTimeout = readTimeout;
	}

	public String getRequestMode()
	{
		return requestMode;
	}

	public void setRequestMode(String requestMode)
	{
		this.requestMode = requestMode;
	}

	public HttpRequestSAC(){
		
	}
	public HttpRequestSAC(String serverUrl,int connectTimeout,int readTimeout,String requestMode){
		this.serverUrl = serverUrl;
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
		this.requestMode = requestMode;
	}
	/**
	 * http request method
	 * @param paramURL
	 * @param paramString
	 * @return InputStream
	 */
	public String httpExecute(String method,String paramString)
	{
		HttpURLConnection httpURLConnection = null;
		InputStream inputStream = null;
		ByteArrayOutputStream bos =null;
		try {
			URL httpUrl = new URL(this.serverUrl + "/" + method);
		    httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
		    httpURLConnection.setConnectTimeout(connectTimeout);
		    httpURLConnection.setReadTimeout(readTimeout);
		    Map<String, String> headers = getHeaders();
		    if ((headers != null) && (headers.size() > 0)) {
				Iterator<String> iterators = headers.keySet().iterator();
				while (iterators.hasNext()) {
				    String str = iterators.next();
				    httpURLConnection.setRequestProperty(str, headers.get(str));
				}
		    }
		    httpURLConnection.setRequestMethod(requestMode);
		    httpURLConnection.setDoOutput(true);
		    OutputStream outputStream = httpURLConnection.getOutputStream();
		    if(paramString!=null){
		    	outputStream.write(paramString.getBytes("utf-8"));
		    }else{
		    	outputStream.write(0);
		    }
		    int i = httpURLConnection.getResponseCode();
		    if (i != 200) throw new Exception("Http Error [" + i + "]");
		    inputStream = httpURLConnection.getInputStream();
		    int j = 0;
		    byte[] buff = new byte[2048]; 
		    bos = new ByteArrayOutputStream();
			while ((j = inputStream.read(buff))!= -1){
				bos.write(buff,0,j);
			}
			byte[] byteArray = bos.toByteArray();
		    return new String(byteArray, "utf-8");
		} catch ( Exception e ) {
			logger.error("httpExecute error…………");
		    e.printStackTrace();
		} finally {
		    try {
				if (bos != null) {
				    bos.close();
				    bos = null;
				}
		    } catch ( Exception e ) {
		    	logger.error("ByteArrayOutputStream close error…………");
			    e.printStackTrace();
		    }
		    try {
				if (inputStream != null) {
				    inputStream.close();
				    inputStream = null;
				}
		    } catch ( Exception e ) {
		    	logger.error("inputStream close error…………");
			    e.printStackTrace();
		    }
		    try {
				if (httpURLConnection != null) {
				    httpURLConnection.disconnect();
				    httpURLConnection = null;
				}
		    } catch ( Exception e ) {
		    	logger.error("httpURLConnection close error…………");
			    e.printStackTrace();
		    }
		}
			return null;
	}
	
	/**
	 * 设置request header
	 * @return
	 */
	private Map<String, String> getHeaders()
    {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "text/plain;charset=UTF-8");
		header.put("User-Agent", "easipay");
		return header;
    }
}
