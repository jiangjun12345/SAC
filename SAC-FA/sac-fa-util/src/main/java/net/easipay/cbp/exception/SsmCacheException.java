package net.easipay.cbp.exception;

public class SsmCacheException extends  AppRuntimeException
{
  private static final long serialVersionUID = 1675800246271193866L;

  public SsmCacheException(String message)
  {
    super(message);
  }

  public SsmCacheException(String message, Throwable cause) {
    super(message, cause);
  }

  public SsmCacheException(Throwable cause) {
    super(cause);
  }
}