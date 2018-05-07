/**
 * 
 */
package net.easipay.cbp;

/**
 * 
 * @author mchen
 * @date 2015-11-6
 */
public class FaException extends RuntimeException
{
    private static final long serialVersionUID = -3328317651624442801L;

    private String code;

    private String message = "";

    public FaException(String code, String message)
    {
	this.code = code;
	this.message = message;
    }

    public String getMessage()
    {
	return this.message;
    }

    public String getCode()
    {
	return code;
    }

}
