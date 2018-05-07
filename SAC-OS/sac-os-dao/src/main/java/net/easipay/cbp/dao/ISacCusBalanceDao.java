package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacCusBalance;

public interface ISacCusBalanceDao extends GenericDao<SacCusBalance, Long>
{
	/**
	 * 根据条件分页查询客户余额
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryCusBalance2(Map<String, Object> paramMap);
	
	
	/**
	 * 根据条件分页查询客户每日余额
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryCusBalanceFundDay(Map<String, Object> paramMap);
	
	/**
	 * 根据条件分页查询客户每日余额
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryCusBalanceFundDay2(Map<String, Object> paramMap);

	/**
	 * 根据条件查询客户余额总数
	 * 
	 * @param paramMap
	 * @return
	 */
	public int getCusBalanceCount2(Map<String, Object> paramMap);
	
	/**
	 * 根据条件查询客户每日余额总数
	 * 
	 * @param paramMap
	 * @return
	 */
	public int getCusBalanceFundDayCount(Map<String, Object> paramMap);
	
	/**
	 * 根据条件查询客户每日余额总数
	 * 
	 * @param paramMap
	 * @return
	 */
	public int getCusBalanceFundDayCount2(Map<String, Object> paramMap);
	
	/**
	 * 根据条件不分页查询客户余额
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> simpleQueryCusBalance2(Map<String, Object> paramMap);

	public List<Map<String, Object>> getCusAvalibleBal(
			Map<String, Object> queryMap);
	
	
	public List<String> getCodeIdsByCusparamMap(Map<String, Object> paramMap);
}
