package net.easipay.cbp.exception;

public class SacException extends RuntimeException
{

    /**
     * 
     */
    private static final long serialVersionUID = 5711582010614420489L;
    private String code;

    public SacException(String code, String message)
    {
	super(message);
	this.code = code;
    }

    public String getCode()
    {
	return code;
    }
}