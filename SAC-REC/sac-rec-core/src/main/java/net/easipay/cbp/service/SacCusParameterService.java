/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.model.SacCusParameter;


/**
 * @author Administrator
 *
 */
public interface SacCusParameterService {
	
	public List<SacCusParameter> getSacCusParameterByParam(Map<String,Object> queryMap);
	

}
