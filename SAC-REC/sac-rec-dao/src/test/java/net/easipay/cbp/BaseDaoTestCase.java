package net.easipay.cbp;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Base class for running DAO tests.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @author jgarcia (updated: migrate to hibernate 4; moved from compass-search
 *         to hibernate-search
 */
@ContextConfiguration(locations = {
		"classpath:/applicationContext-resource.xml",
		"classpath:/applicationContext-persist.xml",
		"classpath:/applicationContext.xml" })
public abstract class BaseDaoTestCase extends
		AbstractTransactionalJUnit4SpringContextTests
{

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());
	/**
	 * ResourceBundle loaded from
	 * src/test/resources/${package.name}/ClassName.properties (if exists)
	 */
	protected ResourceBundle rb;

	/**
	 * Default constructor - populates "rb" variable if properties file exists
	 * for the class in src/test/resources.
	 */
	public BaseDaoTestCase()
	{
		String className = this.getClass().getName();
		try
		{
			rb = ResourceBundle.getBundle(className);
		} catch (MissingResourceException mre)
		{
			log.trace("No resource bundle found for: " + className);
		}
	}

	/**
	 * Utility method to populate a javabean-style object with values from a
	 * Properties file
	 * 
	 * @param obj
	 *            the model object to populate
	 * @return Object populated object
	 * @throws Exception
	 *             if BeanUtils fails to copy properly
	 */
	protected Object populate(final Object obj) throws Exception
	{
		// loop through all the beans methods and set its properties from its
		// .properties file
		Map<String, String> map = new HashMap<String, String>();

		for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();)
		{
			String key = keys.nextElement();
			map.put(key, rb.getString(key));
		}

		BeanUtils.copyProperties(obj, map);

		return obj;
	}
}
