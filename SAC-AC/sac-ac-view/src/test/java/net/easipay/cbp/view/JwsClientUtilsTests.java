package net.easipay.cbp.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.easipay.dsfc.ws.jws.JwsObjectMapper;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.codehaus.jackson.JsonNode;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author dell (Cyrus)
 * @date 2015-7-20 下午01:41:38
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public class JwsClientUtilsTests
{

    public static String toJwsParamStr(Map<String, Object> params) throws Exception
    {
	Map<String, Object> param = new HashMap<String, Object>();
	param.put("PubInfo", getPubInfo());
	param.put("Request", params);
	param.put("Signature", getSignature());
	return JwsObjectMapper.instance.writeValueAsString(param);
    }

    public static Map<String, String> getHeaders()
    {
	Map<String, String> header = new HashMap<String, String>();
	header.put("Content-Type", "text/plain;charset=UTF-8");
	header.put("User-Agent", "easipay");
	return header;
    }

    public static Map<String, String> getPubInfo()
    {
	Map<String, String> pubInfo = new HashMap<String, String>();
	pubInfo.put("TransactionId", "231298379871923");
	pubInfo.put("TransactionTime", new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()));
	pubInfo.put("Channel", "local");
	pubInfo.put("Origin", "local");
	pubInfo.put("ClientIP", "10.10.101.10");
	return pubInfo;
    }

    public static Map<String, String> getSignature()
    {
	Map<String, String> signature = new HashMap<String, String>();
	signature.put("Sign", "");
	signature.put("data", "");
	return signature;
    }

    public static JwsResult toJwsResult(String response) throws Exception
    {
	JwsResult jwsResult = new JwsResult();
	JsonNode readTree = JwsObjectMapper.instance.readTree(response);
	if (readTree != null) {
	    jwsResult.setCode(readTree.path("Code").getTextValue());
	    jwsResult.setMessage(readTree.path("Message").getTextValue());
	    jwsResult.setObject(readTree.path("Response"));
	}
	return jwsResult;
    }

    // 压缩
    public static byte[] compress(String str) throws IOException
    {
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	GZIPOutputStream gzip = new GZIPOutputStream(out);
	gzip.write(str.getBytes("utf-8"));
	gzip.finish();
	gzip.close();
	return out.toByteArray();
    }

    // 解压缩
    public static String uncompress(String str) throws IOException
    {
	if (str == null || str.length() == 0) {
	    return str;
	}
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("utf-8"));
	GZIPInputStream gunzip = new GZIPInputStream(in);
	byte[] buffer = new byte[256];
	int n;
	while ((n = gunzip.read(buffer)) >= 0) {
	    out.write(buffer, 0, n);
	}
	return out.toString();
    }
}
