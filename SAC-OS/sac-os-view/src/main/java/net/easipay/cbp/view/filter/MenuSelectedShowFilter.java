package net.easipay.cbp.view.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
* @Description: 菜单选中显示(用一句话描述该文件做什么)
* @author sydai (作者英文名称) 
* @date 2016-2-26 下午03:39:21
* @version V1.0  
* @jdk v1.6
* @tomcat v7.0
 */
public class MenuSelectedShowFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterchain) throws IOException, ServletException {  
        HttpServletRequest request = (HttpServletRequest)servletRequest;  
        HttpSession session = request.getSession();  
        //需要过滤的代码(将selected保存在session中)
        String selected = request.getParameter("selected");
        if(selected!=null&&!"".equals(selected.trim())){//存在selected参数
        	session.setAttribute("selected", selected);//保存在session中
        }
        filterchain.doFilter(servletRequest, servletResponse);  
    }  

	@Override
	public void destroy() {
		
	}

}
