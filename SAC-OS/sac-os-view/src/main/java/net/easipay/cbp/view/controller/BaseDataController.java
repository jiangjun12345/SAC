package net.easipay.cbp.view.controller;

import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.easipay.cbp.controller.BaseController;
import net.easipay.cbp.service.IDownLoadContent;

import org.springframework.web.servlet.ModelAndView;

public class BaseDataController extends BaseController
{

	/******************公用处理方法*********************/
	
	/**
	 * 处理页码和默认起止时间
	 * @param request
	 * @param paramMap
	 * @param mav
	 * @param startNum :当前时间向前推几天
	 * @param pageSize 
	 */
	protected void handlePageAndDateRange(HttpServletRequest request,Map<String,Object> paramMap,ModelAndView mav,int startNum,int pageSizeNum){
		// 页码处理
		Integer pageNo = 1;// 默认为第1页
		if (request.getParameter("pageNo") != null && !"".equals(request.getParameter("pageNo")))
		{
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}

		Integer pageSize = pageSizeNum;// 默认每页显示pageSizeNum条
		if (request.getParameter("pageSize") != null && !"".equals(request.getParameter("pageSize")))
		{
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		//起止时间处理
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -startNum);
		String startDate = sdf.format(cal.getTime());
		paramMap.put("startDate", request.getParameter("startDate")==null?startDate:request.getParameter("startDate"));//开始日期
		paramMap.put("endDate", request.getParameter("endDate"));//结束日期
		paramMap.put("start", (pageNo-1)*pageSize);//分页起始
		paramMap.put("end", pageSize*pageNo);//分页结束
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("startDate", request.getParameter("startDate")==null?startDate:request.getParameter("startDate"));//开始日期
		mav.addObject("endDate", request.getParameter("endDate"));//结束日期
	}
	
	@SuppressWarnings("rawtypes")
	public static void saveFormDataToSession(HttpServletRequest request){
		HttpSession session = request.getSession();
		Enumeration parameterNames = request.getParameterNames();
		while(parameterNames.hasMoreElements()){
			String pname = (String) parameterNames.nextElement();
			String[] pvalues = request.getParameterValues(pname);
			if(pvalues.length==1){
				String pvalue = pvalues[0];
				session.setAttribute(pname, pvalue);
			}
		}
	}

	/**
	 * 分批下载
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param count
	 * @param fileName
	 * @param contentHead
	 */
	protected void download(HttpServletRequest request,HttpServletResponse response,
			Map<String,Object> paramMap,String fileName,String contentHead,IDownLoadContent a){
		Writer out = null;
		try
		{
			//解决火狐和IE中文名乱码
			String userAgent = request.getHeader("User-Agent");
			if (null != userAgent && -1 != userAgent.indexOf("MSIE")) {
				fileName = URLEncoder.encode(fileName, "UTF8");  
	        } else if (null != userAgent && -1 != userAgent.indexOf("Mozilla")) {
	            fileName =  new String( fileName.getBytes("GB2312"), "ISO-8859-1" );
	        }
			// 设置文件输出类型
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename="+ fileName);
			out = response.getWriter();
			out.write(contentHead);
			//循环写内容
			for(int i=0;;i++){
				paramMap.put("start", i*1000);
				paramMap.put("end", (i+1)*1000);
				String content = a.downloadContent(i,paramMap);
				if(content==null||"".equals(content)){
					break;
				}
				out.write(content);
				content=null;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}finally{
			try
			{
				out.flush();
				if(out!=null){out.close();}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
