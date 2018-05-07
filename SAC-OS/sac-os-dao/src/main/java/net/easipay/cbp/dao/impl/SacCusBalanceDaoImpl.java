package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.ISacCusBalanceDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacCusBalance;

@SuppressWarnings({ "deprecation", "unchecked" })
@Repository
public class SacCusBalanceDaoImpl extends GenericDaoiBatis<SacCusBalance, Long> implements ISacCusBalanceDao
{

	public static final Logger logger = Logger.getLogger(SacCusBalanceDaoImpl.class);

	@Override
	public List<Map<String, Object>> queryCusBalance2(Map<String, Object> paramMap)
	{
		return this.getSqlMapClientTemplate().queryForList("listSplitSacCusBalance2", paramMap);
	}
	
	@Override
	public List<Map<String, Object>> queryCusBalanceFundDay(Map<String, Object> paramMap)
	{
		return this.getSqlMapClientTemplate().queryForList("listSplitSacCusBalanceFundDay", paramMap);
	}
	
	@Override
	public List<Map<String, Object>> queryCusBalanceFundDay2(Map<String, Object> paramMap)
	{
		return this.getSqlMapClientTemplate().queryForList("listSplitSacCusBalanceFundDay2", paramMap);
	}
	
	@Override
	public int getCusBalanceCount2(Map<String, Object> paramMap)
	{
		return (Integer) this.getSqlMapClientTemplate().queryForObject("getSacCusBalanceCount2", paramMap);
	}
	
	@Override
	public int getCusBalanceFundDayCount(Map<String, Object> paramMap)
	{
		return (Integer) this.getSqlMapClientTemplate().queryForObject("getCusBalanceFundDayCount", paramMap);
	}
	
	@Override
	public int getCusBalanceFundDayCount2(Map<String, Object> paramMap)
	{
		return (Integer) this.getSqlMapClientTemplate().queryForObject("getCusBalanceFundDayCount2", paramMap);
	}

	@Override
	public List<Map<String, Object>> simpleQueryCusBalance2(Map<String, Object> paramMap)
	{
		return this.getSqlMapClientTemplate().queryForList("listSacCusBalance2", paramMap);
	}
	
	@Override
	public List<Map<String, Object>> getCusAvalibleBal(
			Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("getCusBalance",queryMap);
	}
	
	@Override
	public List<String> getCodeIdsByCusparamMap(Map<String, Object> paramMap){
		return this.getSqlMapClientTemplate().queryForList("getCodeIdsByCusparamMap", paramMap);
	}
	
}
