package net.easipay.cbp.util;

/**
 * JustCommodity Software Solutions<br/>
 * <br/>
 * Copyright © 2011
 */

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * This Class used to handle null value from an Object
 * 
 * @author Christian
 */
public class TcNvl {

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return 0, if sourceValue == null
	 * 
	 * @param sourceValue
	 * @return sourceValue if not null, return 0 if sourceValue == null
	 */
	public static BigDecimal nvl(BigDecimal sourceValue) {
		return nvl(sourceValue, BigDecimal.ZERO);
	}

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return defaultValue, if sourceValue == null
	 * 
	 * @param sourceValue
	 * @param defaultValue
	 * @return sourceValue if not null,return defaultValue if sourceValue ==
	 *         null
	 */
	public static BigDecimal nvl(BigDecimal sourceValue, BigDecimal defaultValue) {
		if (sourceValue == null) {
			return defaultValue;
		} else {
			return sourceValue;
		}
	}

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return False, if sourceValue == null
	 * 
	 * @param sourceValue
	 * @return sourceValue if not null, return 0 if sourceValue == null
	 */
	public static Boolean nvl(Boolean sourceValue) {
		if (sourceValue == null) {
			return Boolean.FALSE;
		} else {
			return sourceValue;
		}
	}

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return defaultValue, if sourceValue == null
	 * 
	 * @param sourceValue
	 * @param defaultValue
	 * @return sourceValue if not null,return defaultValue if sourceValue ==
	 *         null
	 */
	public static Boolean nvl(Boolean sourceValue, Boolean defaultValue) {
		if (sourceValue == null) {
			return defaultValue;
		} else {
			return sourceValue;
		}
	}

	/**
	 * Method to nvl date value with default value current date.
	 * 
	 * @author Nico Arianto
	 * @date Jul 20, 2010
	 * @param sourceValue
	 *            source value
	 * @return date
	 */
	public static Date nvl(Date sourceValue) {
		return nvl(sourceValue, new Date(System.currentTimeMillis()));
	}

	/**
	 * Method to nvl date value.
	 * 
	 * @author Nico Arianto
	 * @date Jul 20, 2010
	 * @param sourceValue
	 *            source value
	 * @param defaultValue
	 *            defaut value
	 * @return date
	 */
	public static Date nvl(Date sourceValue, Date defaultValue) {
		return sourceValue == null ? defaultValue : sourceValue;
	}

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return 0, if sourceValue == null
	 * 
	 * @param sourceValue
	 * @return sourceValue if not null, return 0 if sourceValue == null
	 */
	public static Integer nvl(Integer sourceValue) {
		return nvl(sourceValue, 0);
	}

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return defaultValue, if sourceValue == null
	 * 
	 * @param sourceValue
	 * @param defaultValue
	 * @return sourceValue if not null,return defaultValue if sourceValue ==
	 *         null
	 */
	public static Integer nvl(Integer sourceValue, Integer defaultValue) {
		if (sourceValue == null) {
			return defaultValue;
		} else {
			return sourceValue;
		}
	}

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return defaultValue, if sourceValue == null
	 * 
	 * @param sourceValue
	 * @param defaultValue
	 * @return sourceValue if not null,return defaultValue if sourceValue ==
	 *         null
	 */
	public static <T> List<T> nvl(List<T> sourceValue) {
		return nvl(sourceValue, Collections.<T> emptyList());
	}

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return defaultValue, if sourceValue == null
	 * 
	 * @param sourceValue
	 * @param defaultValue
	 * @return sourceValue if not null,return defaultValue if sourceValue ==
	 *         null
	 */
	public static <T> List<T> nvl(List<T> sourceValue, List<T> defaultValue) {
		if (sourceValue == null) {
			return defaultValue;
		} else {
			return sourceValue;
		}
	}

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return defaultValue, if sourceValue == null, and also casting sourceValue
	 * to Number
	 * 
	 * @param sourceValue
	 *            Source value to validate
	 * @param defaultValue
	 *            Default value to be return if sourceValue is null
	 * @return sourceValue or defaultValue
	 */
	public static Number nvl(Number sourceValue, Number defaultValue) {
		if (sourceValue == null) {
			return defaultValue;
		} else {
			return sourceValue;
		}
	}

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return "", if sourceValue == null, and also casting sourceValue to String
	 * 
	 * @param sourceValue
	 *            Source value to validate
	 * @return sourceValue or ""
	 */
	public static String nvl(Object sourceValue) {
		return nvl(sourceValue, "");
	}

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return defaultValue, if sourceValue == null, and also casting sourceValue
	 * to Number
	 * 
	 * @param sourceValue
	 *            Source value to validate
	 * @param defaultValue
	 *            Default value to be return if sourceValue is null
	 * @return sourceValue or defaultValue
	 */
	public static Number nvl(Object sourceValue, Number defaultValue) {
		return nvl((Number) sourceValue, defaultValue);
	}

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return defaultValue, if sourceValue == null
	 * 
	 * @param sourceValue
	 *            Source value to validate
	 * @param defaultValue
	 *            Default value to be return if sourceValue is null
	 * @return sourceValue or defaultValue
	 */
	public static Object nvl(Object sourceValue, Object defaultValue) {
		if (sourceValue == null) {
			return defaultValue;
		} else {
			return sourceValue;
		}
	}

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return defaultValue, if sourceValue == null, and also casting sourceValue
	 * to String
	 * 
	 * @param sourceValue
	 *            Source value to validate
	 * @param defaultValue
	 *            Default value to be return if sourceValue is null
	 * @return sourceValue or defaultValue
	 */
	public static String nvl(Object sourceValue, String defaultValue) {
		String strTemp;
		if (sourceValue == null) {
			strTemp = defaultValue;
		} else {
			strTemp = String.valueOf(sourceValue);
		}
		return strTemp;
	}

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return default value if Object[] == null or object on array index
	 * specified by parameter.<br/>
	 * Force default value to ""
	 * 
	 * @param sourceValue
	 *            Array of object
	 * @param index
	 *            Array index
	 * @return object[index] or default value
	 */
	public static String nvl(Object[] sourceValue, int index) {
		return nvl(sourceValue, "", index);
	}

	/**
	 * This Method similar with method NVL in PL/SQL <br/>
	 * Return default value if Object[] == null or object on array index
	 * specified by parameter.
	 * 
	 * @param sourceValue
	 *            Array of object
	 * @param defaultValue
	 *            Default value
	 * @param index
	 *            Array index
	 * @return object[index] or default value
	 */
	public static String nvl(Object[] sourceValue, String defaultValue,
			int index) {
		String strTemp;
		if (sourceValue == null) {
			strTemp = defaultValue;
		} else {
			strTemp = nvl(sourceValue[index], defaultValue);
		}
		return strTemp;
	}
}