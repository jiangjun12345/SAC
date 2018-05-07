/**
 * 
 */
package net.easipay.cbp.validation;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
/**
 * 
* ClassName: SacValidatorFactory <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年1月21日 下午1:43:48 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */
public class SacValidatorFactory
{
    public static final SacValidatorFactory instance = new SacValidatorFactory();
    
    private volatile ValidatorFactory delegate;

    private ValidatorFactory getDelegate()
    {
	ValidatorFactory result = delegate;
	if (result == null) {
	    synchronized (this) {
		result = delegate;
		if (result == null) {
		    delegate = result = initFactory();
		}
	    }
	}
	return result;
    }

    private ValidatorFactory initFactory()
    {
	return Validation.buildDefaultValidatorFactory();
    }

    public Validator getValidator()
    {
	return getDelegate().getValidator();
    }
    
    
    
}
