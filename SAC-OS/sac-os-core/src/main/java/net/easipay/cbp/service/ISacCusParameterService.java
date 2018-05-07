/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.model.SacCusParameter;


/**
 * @author Administrator
 *
 */
public interface ISacCusParameterService {
	
	public List<SacCusParameter> selectAllSacCusParameter(SacCusParameter sacCusParameter,int pageNo,int pageSize);
	
	public Integer selectSacCusParameterTotal(SacCusParameter sacCusParameter);
	
	public SacCusParameter selectSacCusParameterById(SacCusParameter sacCusParameter);
	
	public List<SacCusParameter> handleSacCusParameterList(List<SacCusParameter> sacCusParameterList);

}
