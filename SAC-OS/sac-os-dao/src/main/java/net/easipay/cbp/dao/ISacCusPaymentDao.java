/**
 * 
 */
package net.easipay.cbp.dao;


import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacCancelVerify;
import net.easipay.cbp.model.SacCusPayment;

/**
 * @author Administrator
 *
 */
public interface ISacCusPaymentDao extends GenericDao<SacCusPayment,Long> {
	
	/**
	 * 分页查询客户应付款明细
	 * @param paramMap
	 * @return
	 */
	public List<SacCusPayment> queryCusPayment(Map<String, Object> paramMap);
	
	/**
	 * 查询客户应付款明细
	 * @param paramMap
	 * @return
	 */
	public List<SacCusPayment> simpleQueryCusPayment(Map<String, Object> paramMap);
	
	/**
	 * 查询客户应付款明细总数
	 * @param paramMap
	 * @return
	 */
	public int querySacCusPaymentCount(Map<String,Object> paramMap);
	
	public List<SacCusPayment> selectSacCusPaymentByParameter(SacCusPayment sacCusPayment);
	
	public List<Map<String,Object>> countCusPaymentAmount(Map<String, Object> paramMap);
	
	public List<SacCancelVerify> getSuspendedAccinfoList(Map<String, Object> paramMap);

	public int getSuspendedAccinfoListCount(Map<String, Object> paramMap);
}
