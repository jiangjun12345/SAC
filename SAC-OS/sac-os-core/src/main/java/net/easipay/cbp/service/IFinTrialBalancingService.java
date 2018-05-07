/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.FinTrialBalancing;


/**
 * @author Administrator
 *
 */
public interface IFinTrialBalancingService {
	
	public List<FinTrialBalancing> getFinTrialBalancingBySplit(Map<String,Object> queryMap,int pageNo,int pageSize); 

	public int getFinTrialBalancingCounts(Map<String,Object> queryMap);

}
