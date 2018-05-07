/**
 * 
 */
package net.easipay.cbp.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author mchen
 * @date 2015-11-24
 */

public class SpringServiceFactory implements ApplicationContextAware
{
    private static ApplicationContext applicationContext = null;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
	SpringServiceFactory.applicationContext = applicationContext;
    }

    public static Object getBean(String name)
    {
	return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz)
    {
	return (T) applicationContext.getBean(clazz);
    }
}
