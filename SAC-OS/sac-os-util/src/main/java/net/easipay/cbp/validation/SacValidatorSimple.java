/**
 * 
 */
package net.easipay.cbp.validation;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import net.easipay.cbp.exception.SacException;

/**
 * 
* ClassName: SacValidatorSimple <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年1月21日 下午1:43:53 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */
public class SacValidatorSimple
{

    /**
     * Newly added JSR303 verification function, need to pass formbean
     * 
     * @param validateObject
     */
    public static void validate(Object validateObject, Class<?>... groups)
    {
	Validator validator = SacValidatorFactory.instance.getValidator();
	if (validateObject instanceof List || validateObject instanceof Map) {
	    throw new SacException("999997", "Not supported List and Map");
	}

	Set<ConstraintViolation<Object>> violations = validator.validate(validateObject, groups);
	if (violations.size() > 0) {
	    StringBuffer sb = new StringBuffer("Found field error [");
	    for (ConstraintViolation<Object> constraintViolation : violations) {
		if(constraintViolation.getPropertyPath() == null){
		    sb.append(constraintViolation.getPropertyPath()).append(":");
		}
		sb.append(constraintViolation.getMessage()).append(", ");
	    }
	    sb.delete(sb.lastIndexOf(","), sb.length() - 1);
	    sb.append("]");
	    throw new SacException("999999", sb.toString());
	}
    }

    /**
     * Collection check of object
     * 
     * @param validateObjects
     */
    public static void validateList(List<?> validateObjects, Class<?>... groups)
    {
	for (Object validateObject : validateObjects) {
	    validate(validateObject, groups);
	}
    }
}
