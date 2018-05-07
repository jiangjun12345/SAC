package net.easipay.cbp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class DownLoadUtil {

	public static void download(HttpServletRequest request,  
		      HttpServletResponse response, String downLoadPath, String contentType
		       ) throws Exception {  
		    contentType = "application/octet-stream";
		    request.setCharacterEncoding("UTF-8");  
		    BufferedInputStream bis = null;  
		    BufferedOutputStream bos = null;  
		  
		    //获取项目根目录
//		    String ctxPath = request.getSession().getServletContext()  
//		        .getRealPath("");  
//		    
//		    //获取下载文件露肩
//		    String downLoadPath = ctxPath+"/uploadFile/"+ storeName;  
		  
		    //获取文件的长度
		    long fileLength = new File(downLoadPath).length();  
		    
		    
		    	int i=downLoadPath.lastIndexOf("\\");
		    	String realName = downLoadPath.substring(i+1);
		    	System.out.println("realName="+realName);

		    //设置文件输出类型
		    response.setContentType("application/octet-stream");  
		    response.setHeader("Content-disposition", "attachment; filename="  
		        + new String(realName.getBytes("utf-8"), "ISO8859-1")); 
		    //设置输出长度
		    response.setHeader("Content-Length", String.valueOf(fileLength));  
		    //获取输入流
		    bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
		    //输出流
		    bos = new BufferedOutputStream(response.getOutputStream());  
		    byte[] buff = new byte[2048];  
		    int bytesRead;  
		    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
		      bos.write(buff, 0, bytesRead);  
		    }  
		    //关闭流
		    bis.close();  
		    bos.close();  
		  }  
}
