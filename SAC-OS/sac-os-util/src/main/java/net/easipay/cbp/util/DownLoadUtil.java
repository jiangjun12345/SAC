package net.easipay.cbp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class DownLoadUtil {

	private static final Logger logger = Logger.getLogger(DownLoadUtil.class);

	public static void download(HttpServletRequest request,
			HttpServletResponse response, String downLoadPath,
			String contentType) throws Exception {
		contentType = "application/octet-stream";
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		// 获取项目根目录
		// String ctxPath = request.getSession().getServletContext()
		// .getRealPath("");
		//
		// //获取下载文件露肩
		// String downLoadPath = ctxPath+"/uploadFile/"+ storeName;

		// 获取文件的长度
		long fileLength = new File(downLoadPath).length();
		
		String fileName = "";
		fileName = downLoadPath.replace("/", "\\");
		int i = fileName.lastIndexOf("\\");
		
		String realName = fileName.substring(i + 1);
		System.out.println("realName=" + realName);

		/*int i = downLoadPath.lastIndexOf("\\");
		String realName = downLoadPath.substring(i + 1);
		System.out.println("realName=" + realName);*/

		// 设置文件输出类型
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(realName.getBytes("utf-8"), "ISO8859-1"));
		// 设置输出长度
		response.setHeader("Content-Length", String.valueOf(fileLength));
		// 获取输入流
		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		// 输出流
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		// 关闭流
		bis.close();
		bos.close();
	}

	public static void downloadFromString(HttpServletResponse response,
			String fileName, String resultString) throws Exception {
		// 设置文件输出类型
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
		InputStreamReader bis = null;
		resultString = resultString.replaceAll("\\|", "\t");
		logger.debug("************resultString is >>>>>>>>>" + resultString);
		// 获取输入流
		InputStream is = new ByteArrayInputStream(
				resultString.getBytes("UTF-8"));
		bis = new InputStreamReader(is);
		java.io.BufferedReader br = new java.io.BufferedReader(bis);

		String tempbf;
		// 输出流
		OutputStreamWriter writer = new OutputStreamWriter(
				response.getOutputStream());
		PrintWriter out = new PrintWriter(writer);
		while ((tempbf = br.readLine()) != null) {
			out.println(tempbf);
		}
		// 关闭流
		out.close();
		is.close();
	}
	
	public static void downLoadFromStringToTxt(HttpServletRequest request,HttpServletResponse response,
			String fileName,String fileContent){
		Writer out = null;
		try
		{
			String userAgent = request.getHeader("User-Agent");
			if (null != userAgent && -1 != userAgent.indexOf("MSIE")) {
				fileName = URLEncoder.encode(fileName, "UTF8");  
	        } else if (null != userAgent && -1 != userAgent.indexOf("Mozilla")) {
	            fileName =  new String( fileName.getBytes("GB2312"), "ISO-8859-1" );
	        }
			// 设置文件输出类型
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			response.setContentType("application/octet-stream");
			out = response.getWriter();
			out.write(fileContent);
			
		} catch (IOException e)
		{
			logger.error(fileName+"文件下载出错！");
		}finally{
			try
			{
				out.flush();
				if(out!=null){out.close();}
			} catch (IOException e)
			{
				logger.error("关闭流异常！");
			}
		}
	}
	
}
