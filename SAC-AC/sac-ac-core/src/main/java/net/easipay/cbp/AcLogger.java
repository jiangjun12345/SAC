package net.easipay.cbp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AcLogger
{
    private final static Log logger = LogFactory.getLog(AcLogger.class);

    public static void info(Object message)
    {
	logger.info(message);
    }

    public static void info(Object message, Throwable t)
    {
	logger.info(message, t);
    }

    public static void error(Object message)
    {
	logger.info(message);
    }

    public static void error(Object message, Throwable t)
    {
	logger.info(message, t);
    }

    public static void debug(Object message)
    {
	logger.debug(message);
    }

    public static void debug(Object message, Throwable t)
    {
	logger.debug(message, t);
    }
    
    
    public static boolean isDebugEnabled()
    {
	return logger.isDebugEnabled();

    }
    
}
