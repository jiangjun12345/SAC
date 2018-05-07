package net.easipay.cbp.view.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class TestFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("init");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String characterEncoding = request.getCharacterEncoding();
		int localPort = request.getLocalPort();
		System.out.println(characterEncoding);
		System.out.println(localPort);
	}

	@Override
	public void destroy() {
		System.out.println("destroy");
		
	}

}
