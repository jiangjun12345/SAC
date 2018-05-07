package net.easipay.cbp.util;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @Title: ValidateUtil.java
 * @Package net.easipay.pepp.peps.util
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun
 * @date 2014-7-8 下午01:17:41
 * @version V1.0
 * @jdk v1.7
 * @tomcat v1.6IN
 */
@SuppressWarnings({ "rawtypes" })
public class ValidateUtil {
	public static String validateStringLen(String name, String value,
			Integer len) {
		String reg = ".{" + len + "}";
		if (value != null && !value.matches(reg)) {
			return name + " 参数值长度必须是:" + len + "！";
		} else {
			return null;
		}
	}

	public static String validateStringLen(String name, String value,
			Integer start, Integer end) {
		String reg = ".{" + start + "," + end + "}";
		if (value != null && !value.matches(reg)) {
			return name + " 参数值长度必须在 " + start + " 与 " + end + " 之间！";
		} else {
			return null;
		}
	}

	public static String validateByReg(String name, String value, String reg) {
		if (StringUtils.isBlank(value)) {
			return name + " 参数值不能为空！";
		} else if (!StringUtils.isBlank(value) && !value.matches(reg)) {
			return name + " 参数值格式非法！";
		} else {
			return null;
		}
	}

	public static String validateNull(String name, String value) {
		if (StringUtils.isBlank(value)) {
			return name + " 参数值不能为空！";
		} else {
			return null;
		}
	}

	public static String validateNull(Map map) {
		String error = null;
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String temp = (String) it.next();
			if (map.get(temp) == null
					|| map.get(temp).toString().trim().equals("")) {
				error = temp + " 参数值不能为空！";
				break;
			}
		}
		return error;
	}

}
