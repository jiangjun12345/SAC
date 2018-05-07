/**
 * 
 */
package net.easipay.pepp.peps.util;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.jasig.cas.authentication.principal.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

/**
 * @author Administrator
 * 解析Service
 */
public final class ServiceUtil {
	
	public static final Logger logger = Logger.getLogger(ServiceUtil.class);

	/**
	 * 抓取目标Service标示符
	 * @param service cas service
	 * @return
	 */
	public static String fetchTargetService(Service service){
		if(null !=service){
			String serviceId = service.getId();
			if(StringUtils.hasText(serviceId)){
				int lastIndex = serviceId.lastIndexOf("/");
				if(lastIndex > 0){
					int lastSecondIndex = serviceId.lastIndexOf("/", lastIndex - 1);
					if(lastSecondIndex > 0){
						return serviceId.substring(lastSecondIndex + 1, lastIndex);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 目标服务的form页面键
	 * @param service
	 * @return
	 */
	public static String getServiceFormPage(String service, ApplicationContext context){
		String key = getKey("cas.loginform.", service);
		PepsPropertiesSupport support = (PepsPropertiesSupport)context.getBean("propertiesSupport");
		String value = support.getValue(key);
		if(null == value){
			value = support.getValue(getKey("cas.loginform.", "default"));
		}
		return value;
	}
	
	/**
	 * 目标服务的form页面键
	 * @param service
	 * @return
	 */
	public static String getServiceDomain(String service,ApplicationContext context){
		String key = getKey("cas.domain.", service);
		PepsPropertiesSupport support = (PepsPropertiesSupport)context.getBean("propertiesSupport");
		String value = support.getValue(key);
		if(null == value){
			value = support.getValue(getKey("cas.domain.", "default"));
		}
		return value;
	}
	
	public static String getServiceLoginView(String service,ApplicationContext context){
		String key = getKey("cas.loginView.", service);
		PepsPropertiesSupport support = (PepsPropertiesSupport)context.getBean("propertiesSupport");
		String value = support.getValue(key);
		if(null == value){
			value = support.getValue(getKey("cas.loginView.", "default"));
		}
		return value;
	}
	
	private static String getKey(String prefix, String service){
		String suffix = "default";
		if(null != service && StringUtils.hasText(service)){
			suffix = service;
		}
		return prefix + suffix;
	}
	
	public static String getValueByKey(String service, String keyValue, String defaultValue, PepsPropertiesSupport propertiesSupport){
		String key = getKey(keyValue, service);
		String value = propertiesSupport.getValue(key);
		if(null == value){
			value = propertiesSupport.getValue(getKey(keyValue, defaultValue));
		}
		return value;
	}
	
}
