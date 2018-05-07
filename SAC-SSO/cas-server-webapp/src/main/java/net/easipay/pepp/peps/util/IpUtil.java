package net.easipay.pepp.peps.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class IpUtil {
	
	public static final Logger logger = Logger.getLogger(IpUtil.class);

	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		logger.info("x-forwarded-for's ip ==" + ip);
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.info("Proxy-Client-IP's ip ==" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			logger.info("WL-Proxy-Client-IP's ip ==" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			logger.info("RemoteAddr's ip ==" + ip);
		}
		return ip;
	}

}
