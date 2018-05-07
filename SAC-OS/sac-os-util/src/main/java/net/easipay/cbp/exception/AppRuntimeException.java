package net.easipay.cbp.exception;

public class AppRuntimeException extends RuntimeException
{
  private static final long serialVersionUID = -5929079559127324865L;

  public AppRuntimeException(String message)
  {
    super(message);
  }

  public AppRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public AppRuntimeException(Throwable cause) {
    super(cause);
  }
}