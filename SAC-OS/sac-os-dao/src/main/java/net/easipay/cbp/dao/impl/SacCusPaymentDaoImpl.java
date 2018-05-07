package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacCusPaymentDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacCancelVerify;
import net.easipay.cbp.model.SacCusPayment;
import net.easipay.cbp.util.BeanUtils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */

@Repository()
public class SacCusPaymentDaoImpl extends GenericDaoiBatis<SacCusPayment,Long> implements ISacCusPaymentDao{
	
	private static final Logger logger = Logger.getLogger(SacCusPaymentDaoImpl.class);

	@Override
	public List<SacCusPayment> queryCusPayment(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("listSplitSacCusPayment2",paramMap);
	}

	@Override
	public List<SacCusPayment> simpleQueryCusPayment(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("listSacCusPayment2",paramMap);
	}

	@Override
	public int querySacCusPaymentCount(Map<String, Object> paramMap)
	{
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacCusPaymentCount2", paramMap);
	}
	
	@Override
	public List<SacCusPayment> selectSacCusPaymentByParameter(
			SacCusPayment sacCusPayment) {
		Map<String,Object> queryMap = BeanUtils.transBean2Map(sacCusPayment);
		return getSqlMapClientTemplate().queryForList("listSacCusPayment", queryMap);
	}

	@Override
	public List<Map<String, Object>> countCusPaymentAmount(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("countCusPaymentAmount",paramMap);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<SacCancelVerify> getSuspendedAccinfoList(
			Map<String, Object> paramMap) {
		getSqlMapClientTemplate().queryForObject("calls_CancellVerify",paramMap);
		List<SacCancelVerify> rtnInfo  = (List<SacCancelVerify>) paramMap.get("result");
		return rtnInfo;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getSuspendedAccinfoListCount(Map<String, Object> paramMap) {
		int rtn_count = 0;
		paramMap.put("rtn_count", rtn_count);
		getSqlMapClientTemplate().queryForObject("calls_CancellVerifyCounts",paramMap);
		int counts  = (Integer) paramMap.get("rtn_count");
		return counts;
	}
	

	
}
