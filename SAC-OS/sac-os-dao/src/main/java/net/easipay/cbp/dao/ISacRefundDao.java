/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacRecDifferences;
import net.easipay.cbp.model.SacRefund;

/**
 * @author Administrator
 *
 */
public interface ISacRefundDao extends GenericDao<SacRefund,Long> {
	
	public List<SacRefund> selectSacRefund(SacRefund sacRefund,int pageNo,int pageSize);
	
	public int selectSacRefundCounts(SacRefund sacRefund);

	public SacRefund selectSacRefundById(SacRefund sacRefund);

	public void updateSacRefund(SacRefund sacRefund);

	
}
