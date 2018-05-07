package net.easipay.cbp.view.taglib;




import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.Utils;



/**
 * 分页标签
 */
public class PageTagNum extends TagSupport {

	private String url = ""; // 页面指向地址，实际为messages的key

	private String urlName = ""; // 页面指向地址，真正为URL地址，有key找到的value

	private String pageNo = ""; // 当前页面，字符串型，由外面传入

	private String paramsStr = ""; // 组装后的参数字符串

	private int totalPages = 1; // 总页面数

	private int count = 0; // 总记录数

	private int intPageNo = 1; // 当前页面
	
	private int pageSize = ConstantParams.ADMIN_PAGE_SIZE; //每一页面显示的最大记录数

	public PageTagNum() {
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
			if ("pageNo".equals(key) || key.toLowerCase().startsWith("submit"))
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
		if(pageSize==0){
			pageSize = ConstantParams.ADMIN_PAGE_SIZE;
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
		reStr.append("<table cellpadding='0' cellspacing='0' border='0' style='font-size:12px'>");
		reStr.append("<form name='splitPageForm' method='get' ");
		reStr.append("action='" + urlName + addParams(paramsStr) + "'>\n");
		reStr.append("<input type='hidden' name='pageSize' value='"+pageSize+"'/>\n");
		reStr.append("<tr>");
		reStr.append("<td align='center'>");
		reStr.append("<font color='#990000'>第<b>" + intPageNo + "/"
				+ totalPages + "</b>页");
		reStr.append("共<b>" + count + "</b>条" + " 每页<b>" + pageSize + "</b>条</font>\n");
		if (totalPages < 2) {
			reStr.append("【首页】");
			reStr.append("【上页】");
			reStr.append("【下页】");
			reStr.append("【尾页】");
		} else {
			if (intPageNo < 2) {
				reStr.append("【首页】");
				reStr.append("【上页】");
				reStr.append(getUrl(intPageNo + 1, "下页"));
				reStr.append(getUrl(totalPages, "尾页"));
			} else if (intPageNo == totalPages) {
				reStr.append(getUrl(1, "首页"));
				reStr.append(getUrl(intPageNo - 1, "上页"));
				reStr.append("【下页】");
				reStr.append("【尾页】");
			} else {
				reStr.append(getUrl(1, "首页"));
				reStr.append(getUrl(intPageNo - 1, "上页"));
				reStr.append(getUrl(intPageNo + 1, "下页"));
				reStr.append(getUrl(totalPages, "尾页"));
			}
		}
		reStr.append("<select name='pageNo' align='absmiddle' ");
		reStr.append("class='sel-splitpage' ");
		reStr.append("onChange='splitPageForm.submit();'>");
		if (totalPages == 0) {
			reStr.append("<option value=''>0</option>");
		}
		for (int i = 1; i <= totalPages; i++) {
			reStr.append("<option value='" + i + "'");
			if (intPageNo == i) {
				reStr.append(" selected");
			}
			reStr.append(">");
			reStr.append(i);
			reStr.append("</option>");
		}
		reStr.append("</select>");
		reStr.append("</td>");
		reStr.append("</tr>");
		reStr.append("</form>");
		reStr.append("</table>");
		JspWriter writer = pageContext.getOut();
		try {
//			writer.println(new String(reStr.toString().getBytes(), "GBK"));
			writer.println(reStr.toString());
		} catch (Exception e) {
			throw new JspException(e.getMessage());
		}
		return (EVAL_PAGE);
	}

	private String getUrl(int pageNo, String name) {
		return "【<a href=\"javascript:void(0);\" class=\"splitPage\" onclick=\"javascript:window.location.href='"
		+ dealUrl(urlName, pageNo) + "'; return false;\">" + name + "</a>】\n";
	}

	private String dealUrl(String url, int pageNo) {
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
