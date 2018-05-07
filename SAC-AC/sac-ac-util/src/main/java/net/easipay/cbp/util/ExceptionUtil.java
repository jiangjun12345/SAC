package net.easipay.cbp.util;


public class ExceptionUtil
{
    public static Throwable getOriginalCause(Throwable t)
    {
	Throwable throwable = t;
	while (throwable.getCause() != null) {
	    throwable = throwable.getCause();
	}
	return throwable;
    }

}
