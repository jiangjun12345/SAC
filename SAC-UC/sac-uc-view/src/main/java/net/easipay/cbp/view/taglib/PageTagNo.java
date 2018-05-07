package net.easipay.cbp.view.taglib;




import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

import net.easipay.cbp.util.Utils;




/**
 * 分页标签
 */
public class PageTagNo extends TagSupport {

	private String url = ""; // 页面指向地址，实际为messages的key

	private String urlName = ""; // 页面指向地址，真正为URL地址，有key找到的value

	private String pageNo = ""; // 当前页面，字符串型，由外面传入

	private String paramsStr = ""; // 组装后的参数字符串

	private int totalPages = 1; // 总页面数

	private int count = 0; // 总记录数

	private int intPageNo = 1; // 当前页面
	
	private int pageSize = 20; //每一页面显示的最大记录数

	public PageTagNo() {
	}

	public int doStartTag() throws JspException {
		if (url == null) {
			url = "";
		}
		url = url.trim();

		/*ConfigurationHelper helper = (ConfigurationHelper) BeanLocator
        .getBean("configurationHelper");
        urlName = helper.getProperty(url, "");*/

		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		
		urlName = request.getContextPath() + url;
		
		Enumeration en = request.getParameterNames();
		StringBuffer param = new StringBuffer();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			if ("pageNo".equals(key)||"pageSize".equals(key) || key.toLowerCase().startsWith("submit"))
				continue;
			String value = Utils.trim(request.getParameter(key));
			if (value.equals(""))
				continue;
			param.append("&" + key + "=" + value);
		}
		paramsStr = param.toString();

		try {
			intPageNo = Integer.parseInt(pageNo);
		} catch (Exception e) {
		}
		if (count % pageSize > 0) {
			totalPages = count / pageSize + 1;
		} else {
			totalPages = count / pageSize;
		}
		if (intPageNo > totalPages) {
			intPageNo = totalPages;
		}
		return (SKIP_BODY);
	}

	public int doEndTag() throws JspException {
		StringBuffer reStr = new StringBuffer();
		JspWriter out = pageContext.getOut();
		reStr.append(" <tr><td height='20'>&nbsp;</td>");
		reStr.append(" <td colspan='6'><table width='100%' border='0' cellspacing='1' cellpadding='0'>");
		reStr.append("<form name='splitPageForm' method='get' ");
		reStr.append("action='" + urlName + addParams(paramsStr) + "'>");
		reStr.append(" <tr><td width='200'>&nbsp;</td>");
		
		if (totalPages < 2) {
			reStr.append(" <td width='43'><img src='images/button-first.gif' width='12' height='12' border='0' /></td>");
			reStr.append(" <td width='43'><img src='images/button-pre.gif' width='14' height='12' border='0' /></td>");
			reStr.append(" <td width='40'><img src='images/button-next.gif' width='14' height='12' border='0' /></td>");		
			reStr.append(" <td width='126'><img src='images/button-end.gif' width='12' height='12' border='0' /></td>");
		} else {
			if (intPageNo < 2) {
				reStr.append(" <td width='43'><img src='images/button-first.gif' width='12' height='12' border='0' /></td>");
				reStr.append(" <td width='43'><img src='images/button-pre.gif' width='14' height='12' border='0' /></td>");
				reStr.append(" <td width='40'>" + getUrl(intPageNo + 1,pageSize) + "<img src='images/button-next.gif' width='14' height='12' border='0' /></a></td>");		
				reStr.append(" <td width='126'>" + getUrl(totalPages,pageSize) + "<img src='images/button-end.gif' width='12' height='12' border='0' /></a></td>");
			} else if (intPageNo == totalPages) {
				reStr.append(" <td width='43'>" + getUrl(1,pageSize) + "<img src='images/button-first.gif' width='12' height='12' border='0' /></a></td>");
				reStr.append(" <td width='43'>" + getUrl(intPageNo - 1,pageSize) + "<img src='images/button-pre.gif' width='14' height='12' border='0' /></a></td>");
				reStr.append(" <td width='40'><img src='images/button-next.gif' width='14' height='12' border='0' /></td>");		
				reStr.append(" <td width='126'><img src='images/button-end.gif' width='12' height='12' border='0' /></td>");
			} else {
				reStr.append(" <td width='43'>" + getUrl(1,pageSize) + "<img src='images/button-first.gif' width='12' height='12' border='0' /></a></td>");
				reStr.append(" <td width='43'>" + getUrl(intPageNo - 1,pageSize) + "<img src='images/button-pre.gif' width='14' height='12' border='0' /></a></td>");
				reStr.append(" <td width='40'>" + getUrl(intPageNo + 1,pageSize) + "<img src='images/button-next.gif' width='14' height='12' border='0' /></a></td>");		
				reStr.append(" <td width='126'>" + getUrl(totalPages,pageSize) + "<img src='images/button-end.gif' width='12' height='12' border='0' /></a></td>");
			}
		}
		
		
		reStr.append(" <td width='207'><strong class='text24'>总条数:" + count + "  总页数:" + totalPages + "</strong></td>");
		
		reStr.append(" <td align='right'><table width='380' border='0' cellpadding='0' cellspacing='0'>");		
		reStr.append(" <tr>");
		reStr.append(" <td width='85'><strong class='text24'>当前页:" + intPageNo +"</strong></td>");
		reStr.append(" <td width='31'><input name='pageNo' type='text' size='4' maxlength='3' /></td>");		
		reStr.append(" <td width='49'><a href='#' onclick='splitPageForm.submit();'>");
		reStr.append(" <img src='images/button-goto.gif' width='42' height='20' border='0' /></a></td>");
		reStr.append(" <td width='59'><strong class='text24'>每页显示</strong> </td>");
		reStr.append(" <td width='42'><input name='pageSize' type='text' size='4' value='" + pageSize + "' maxlength='2' /></td>");		
		reStr.append(" <td width='63'><a href='#' onclick='splitPageForm.submit();'><img src='images/button-set.gif' width='42' height='20' border='0' /></a></td>");
		reStr.append(" </tr>");
		reStr.append(" </table></td>");   
		
		reStr.append(" </tr>");	
		reStr.append(" </form>");
		reStr.append(" </table></td></tr>");	
		try {
			out.print(reStr.toString());
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
    	return 0;
	}

	private String getUrl(int pageNo,int pageSize) {
		return "<a href='" + dealUrl(urlName, pageNo,pageSize)
				+ "' >";
	}

	private String dealUrl(String url, int pageNo,int pageSize) {
		return url + "?pageNo=" + pageNo + "&pageSize=" + pageSize + paramsStr;
	}

	private String addParams(String params) {
		if (params == null || params.equals("")) {
			return "";
		}
		return "?" + params.substring(1);
	}

	public void release() {
		super.release();
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
