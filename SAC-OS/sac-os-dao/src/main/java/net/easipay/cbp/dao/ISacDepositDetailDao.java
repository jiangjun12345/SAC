/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacDepositDetail;

/**
 * @author Administrator
 *
 */
public interface ISacDepositDetailDao extends GenericDao<SacDepositDetail,Long> {

	public List<SacDepositDetail> findDepositDetailByParam(Map<String, Object> queryMap);
	
	public List<SacDepositDetail> findDepositDetailByParamForValid(Map<String, Object> queryMap);

	public int getCountsByParam(Map<String, Object> queryMap);

	public void updateDepositDetailForReMake(SacDepositDetail detail);

	public List<SacDepositDetail> getDepositDetailByPaging(
			Map<String, Object> queryMap);

	public Integer getMunualMatchCheckCounts(Map<String, Object> queryMap);

	public List<Map<String, Object>> getMunualMatchCheckInfo(
			Map<String, Object> queryMap);

	public void deleteDetailByBatchId(Map<String, Object> deleteMap);

	public void updateDepositDetailSpecial(SacDepositDetail detail);

	

	
}
