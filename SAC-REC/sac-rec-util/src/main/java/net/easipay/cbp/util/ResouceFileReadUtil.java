package net.easipay.cbp.util;

import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ResouceFileReadUtil {
	/**
	 * 在资源文件中读取接口的访问的URL
	 * 
	 * @param request
	 * @param response
	 * @param trxJson
	 * @throws Exception
	 * @throws Exception
	 */
	public static Properties readPropertiesFileByName(String propertiesFileName)
			throws Exception {
		Resource resource = new ClassPathResource(propertiesFileName);
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		return props;
	}
}
