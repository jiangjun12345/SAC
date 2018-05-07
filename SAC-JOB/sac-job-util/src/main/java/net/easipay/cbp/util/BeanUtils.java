package net.easipay.cbp.util;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

public class BeanUtils extends org.springframework.beans.BeanUtils 
{
	
	private static final Logger logger = Logger.getLogger(BeanUtils.class);

	public static void copyProperties(Object source, Object target)throws BeansException 
	{
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) 
		{
			if (targetPd.getWriteMethod() != null) 
			{
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) 
				{
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) 
						{
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						if (value != null && !value.equals("")) 
						{
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) 
							{
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					} 
					catch (Exception ex) 
					{
						throw new FatalBeanException("Could not copy properties from source to target",ex);
					}
				}
			}
		}
	}
	
	public static <T> Map<String, Object> convertObjectToMap(T src) {
		if (src == null) {
			return Collections.emptyMap();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Method> methods = ReflectionUtil.getReadMethods(src);
			if (methods != null) {
				for (String prop : methods.keySet()) {
					Method method = methods.get(prop);
					if (method != null) {
						try {
							Object value = method.invoke(src, new Object[0]);
							map.put(prop, value);
						} catch (IllegalAccessException e) {
							logger.warn(e.getMessage(), e);
						} catch (InvocationTargetException e) {
							logger.warn(e.getMessage(), e);
						}
					}
				}
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return map;
	}
	
	
	 // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean  
    public static void transMap2Bean(Map<String, Object> map, Object obj) {  
  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                if (map.containsKey(key)) {  
                    Object value = map.get(key);  
                    // 得到property对应的setter方法  
                    Method setter = property.getWriteMethod();  
                    setter.invoke(obj, value);  
                }  
  
            }  
  
        } catch (Exception e) {  
            System.out.println("transMap2Bean Error " + e);  
        }  
  
        return;  
  
    }  
	
	/**
     * Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map 
     * @param obj
     * @return
     */
    public static  Map<String,Object> transBean2Map(Object obj) {  
  
        if(obj == null){  
            return null;  
        }          
         Map<String, Object> map = new HashMap<String, Object>();  
         try {  
             BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
             PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
             for (PropertyDescriptor property : propertyDescriptors) {  
                 String key = property.getName();  
   
                 // 过滤class属性  
                 if (!key.equals("class")) {  
                     // 得到property对应的getter方法  
                     Method getter = property.getReadMethod();  
                     Object value = getter.invoke(obj);  
                     map.put(key, value);  
                 }  
   
             }  
         } catch (Exception e) {  
        	 e.printStackTrace();
             System.out.println("transBean2Map Error " + e);  
         }  
   
         return map;  
   
     }  
		
		
}