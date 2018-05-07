/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacDepositBatch;

/**
 * @author Administrator
 *
 */
public interface ISacDepositBatchDao extends GenericDao<SacDepositBatch,Long> {

	public int getBatchCountsByParam(Map<String,Object> queryMap);

	public List<SacDepositBatch> getDepositBatchByParam(
			Map<String,Object> queryMap, int pageNo, int pageSize);

	public void deleteBatchByBatchId(Map<String, Object> deleteMap);
	
}
