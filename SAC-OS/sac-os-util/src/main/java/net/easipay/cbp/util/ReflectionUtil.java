/**
 * JustCommodity Software Solutions<br/>
 * <br/>
 * Copyright © 2011
 */
package net.easipay.cbp.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

public class ReflectionUtil {
	private static final Logger logger = Logger.getLogger(ReflectionUtil.class);

	public static <S, D> void copyProperties(S src, D desc,
			Set<String> includedPropertySet, Set<String> excludedPropertySet)
			throws IntrospectionException {
		Assert.notNull(src, "Source cannot be null");
		Assert.notNull(desc, "Destination cannot be null");
		Map<String, Method> srcMap = null;
		Map<String, Method> descMap = null;
		try {
			srcMap = getReadMethods(src);
			descMap = getWriteMethods(desc);
			if (includedPropertySet != null && includedPropertySet.size() > 0) {
				Iterator<String> it = includedPropertySet.iterator();
				while (it.hasNext()) {
					String property = it.next();
					if (excludedPropertySet != null
							&& excludedPropertySet.contains(property)) {
						continue;
					}
					Method readMethod = srcMap.get(property);
					Method writeMethod = descMap.get(property);
					if (readMethod != null && writeMethod != null) {
						try {
							Object value = readMethod
									.invoke(src, new Object[0]);
							writeMethod.invoke(desc, value);
						} catch (IllegalAccessException e) {
							logger.warn(e.getMessage(), e);
						} catch (InvocationTargetException e) {
							logger.warn(e.getMessage(), e);
						}
					} else if (readMethod == null) {
						logger.warn("Read method cannot be found for property '"
								+ property
								+ "' [class: "
								+ src.getClass().getName() + "]");
					} else {
						logger.warn("Write method cannot be found for property '"
								+ property
								+ "' [class: "
								+ desc.getClass().getName() + "]");
					}
				}
			} else {
				// copy all of the properties
				Set<Entry<String, Method>> srcSet = srcMap.entrySet();
				for (Entry<String, Method> entry : srcSet) {
					String property = entry.getKey();
					if (excludedPropertySet != null
							&& excludedPropertySet.contains(property)) {
						continue;
					}
					Method readMethod = entry.getValue();
					Method writeMethod = descMap.get(property);
					if (readMethod != null && writeMethod != null) {
						try {
							Object value = readMethod
									.invoke(src, new Object[0]);
							writeMethod.invoke(desc, value);
						} catch (IllegalAccessException e) {
							logger.warn(e.getMessage(), e);
						} catch (InvocationTargetException e) {
							logger.warn(e.getMessage(), e);
						}
					} else if (readMethod == null) {
						logger.warn("Read method cannot be found for property '"
								+ property
								+ "' [class: "
								+ src.getClass().getName() + "]");
					} else {
						logger.warn("Write method cannot be found for property '"
								+ property
								+ "' [class: "
								+ desc.getClass().getName() + "]");
					}
				}
			}
		} catch (IntrospectionException e) {
			throw e;
		} finally {
			if (srcMap != null) {
				srcMap.clear();
			}
			if (descMap != null) {
				descMap.clear();
			}
			srcMap = null;
			descMap = null;
		}
	}

	public static <T> Map<String, Method> getReadMethods(T src)
			throws IntrospectionException {
		if (src != null) {
			BeanInfo beanInfo = null;
			PropertyDescriptor[] descriptors = null;
			try {
				beanInfo = Introspector.getBeanInfo(src.getClass());
				descriptors = beanInfo.getPropertyDescriptors();
				Map<String, Method> map = new HashMap<String, Method>();
				for (PropertyDescriptor descriptor : descriptors) {
					String propertyName = descriptor.getDisplayName();
					if ("class".equals(propertyName)) {
						continue;
					}
					Method method = descriptor.getReadMethod();
					if (method != null) {
						method.setAccessible(true);
						map.put(propertyName, method);
					}
				}
				return map;
			} catch (IntrospectionException e) {
				throw e;
			} finally {
				beanInfo = null;
				descriptors = null;
			}
		}
		return null;
	}

	public static <T> Map<String, Method> getWriteMethods(T src)
			throws IntrospectionException {
		if (src != null) {
			BeanInfo beanInfo = null;
			PropertyDescriptor[] descriptors = null;
			try {
				beanInfo = Introspector.getBeanInfo(src.getClass());
				descriptors = beanInfo.getPropertyDescriptors();
				Map<String, Method> map = new HashMap<String, Method>();
				for (PropertyDescriptor descriptor : descriptors) {
					String propertyName = descriptor.getDisplayName();
					if ("class".equals(propertyName)) {
						continue;
					}
					Method method = descriptor.getWriteMethod();
					if (method != null) {
						method.setAccessible(true);
						map.put(propertyName, method);
					}
				}
				return map;
			} catch (IntrospectionException e) {
				throw e;
			} finally {
				beanInfo = null;
				descriptors = null;
			}
		}
		return null;
	}

	private ReflectionUtil() {
	}
}