/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinTrialBalancing;

/**
 * @author Administrator
 *
 */
public interface IFinTrialBalancingDao extends GenericDao<FinTrialBalancing,Long> {

	public List<FinTrialBalancing> getFinTrialBalancingBySplit(
			Map<String, Object> queryMap, int pageNo, int pageSize);

	public int getFinTrialBalancingCounts(Map<String, Object> queryMap);
	
}
