/**
 * 
 */
package net.easipay.cbp.exception;

public class AcException extends RuntimeException
{

    /**
     * 
     */
    private static final long serialVersionUID = 5711582010614420489L;
    private String code;
    private String message;

    public AcException(final String code, String message, final Throwable throwable)
    {
	super(throwable);
	this.code = code;
	this.message = message;
    }

    public AcException(String code, String message)
    {
	this.code = code;
	this.message = message;
    }

    public String getCode()
    {
	return code;
    }

    @Override
    public String getMessage()
    {
	return message;
    }

}
