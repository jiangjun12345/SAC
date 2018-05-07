/**
 * 
 */
package net.easipay.pepp.peps.util;

import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.support.PropertiesLoaderSupport;

/**
 * @author Administrator
 * 
 */
public class PepsPropertiesSupport extends PropertiesLoaderSupport implements InitializingBean {

	private Properties properties;
	
	public String getValue(String code){
		return this.properties.getProperty(code);
	}

	public void afterPropertiesSet() throws Exception {
		this.properties = mergeProperties();
	}
}
