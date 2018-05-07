package net.easipay.cbp.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import net.easipay.cbp.model.FinPzNoSequence;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility class to convert one object to another.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public final class ConvertUtil
{
	private static final Log log = LogFactory.getLog(ConvertUtil.class);

	/**
	 * Checkstyle rule: utility classes should not have public constructor
	 */
	private ConvertUtil()
	{
	}

	/**
	 * Method to convert a ResourceBundle to a Map object.
	 * 
	 * @param rb
	 *            a given resource bundle
	 * @return Map a populated map
	 */
	public static Map<String, String> convertBundleToMap(ResourceBundle rb)
	{
		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements())
		{
			String key = keys.nextElement();
			map.put(key, rb.getString(key));
		}

		return map;
	}

	/**
	 * Method to convert a ResourceBundle to a Properties object.
	 * 
	 * @param rb
	 *            a given resource bundle
	 * @return Properties a populated properties object
	 */
	public static Properties convertBundleToProperties(ResourceBundle rb)
	{
		Properties props = new Properties();

		for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();)
		{
			String key = keys.nextElement();
			props.put(key, rb.getString(key));
		}

		return props;
	}

	/**
	 * Convenience method used by tests to populate an object from a
	 * ResourceBundle
	 * 
	 * @param obj
	 *            an initialized object
	 * @param rb
	 *            a resource bundle
	 * @return a populated object
	 */
	public static Object populateObject(Object obj, ResourceBundle rb)
	{
		try
		{
			Map<String, String> map = convertBundleToMap(rb);
			BeanUtils.copyProperties(obj, map);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("Exception occurred populating object: " + e.getMessage());
		}

		return obj;
	}
	
	public static Long convertPzNo(FinPzNoSequence sequence){
		String value = String.format("%4d", (sequence.getValue() + 1)).replace(" ", "0");
		return Long.parseLong(sequence.getKey() + "" + value);
	}
	
	public static List<String> convertCodeId(String codeId){
		String codeId1st = codeId.substring(0,27);
		String codeId2nd = codeId.substring(0,29);
		String codeId3rd = codeId;
		List<String> codeIds = new ArrayList<String>();
		codeIds.add(codeId1st);
		codeIds.add(codeId2nd);
		codeIds.add(codeId3rd);
		return codeIds;
	}
	
	public static String convertList(List<String> list){
		String result = "";
		if (list != null && list.size() != 0){
			result += list.get(0);
			if (list.size() > 1){
				for(int i=1;i<list.size();i++){
					result += "," + list.get(i);
				}
			}
		}
		return result;
	}
}
