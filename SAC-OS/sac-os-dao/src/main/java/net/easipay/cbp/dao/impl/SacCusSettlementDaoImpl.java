/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacCusSettlementDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacCusSettlement;
import net.easipay.cbp.util.BeanUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */

@Repository("sacCusSettlement")
public class SacCusSettlementDaoImpl extends GenericDaoiBatis<SacCusSettlement,Long> implements ISacCusSettlementDao{
	private static final Logger logger = Logger.getLogger(SacCusSettlementDaoImpl.class);
	
	public SacCusSettlementDaoImpl(){
		super(SacCusSettlement.class);
	}
	
	public SacCusSettlementDaoImpl(Class<SacCusSettlement> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SacCusSettlement> selectSacCusSettlement(
			SacCusSettlement sacCusSettlement,int pageNo,int pageSize) {
		String sacDate = sacCusSettlement.getSacDate();
		if(StringUtils.isNotBlank(sacDate)&&!"null".equals(sacDate)){
			sacDate = sacDate.replaceAll("-", "");
			sacCusSettlement.setSacDate(sacDate);
		}
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		Map<String,Object> queryMap =null;
		queryMap = BeanUtils.transBean2Map(sacCusSettlement);
		queryMap.put("start", start);
		queryMap.put("end", end);
		return  getSqlMapClientTemplate().queryForList("listSplitSacCusSettlement",queryMap);
	}

	@Override
	public int selectSacCusSettlementCounts(SacCusSettlement sacCusSettlement) {
		Map<String,Object> queryMap =null;
		queryMap = BeanUtils.transBean2Map(sacCusSettlement);
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacCusSettlementCount", queryMap);
	}

	@Override
	public void updateSacCusSettlement(SacCusSettlement sacCusSettlement) {
		getSqlMapClientTemplate().update("updateSacCusSettlement", sacCusSettlement);
		
	}

	@Override
	public SacCusSettlement selectSacCusSettlementById(
			SacCusSettlement sacCusSettlement) {
		return (SacCusSettlement)getSqlMapClientTemplate().queryForObject("listSacCusSettlement", sacCusSettlement);
	}

	@Override
	public List<SacCusSettlement> querySacCusSettlement(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("listSplitSacCusSettlement2",paramMap);
	}

	@Override
	public List<SacCusSettlement> simpleQuerySacCusSettlement(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("querySacCusSettlement",paramMap);
	}

	@Override
	public int querySacCusSettlementCount(Map<String, Object> paramMap)
	{
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacCusSettlementCount2", paramMap);
	}

	@Override
	public List<Map<String,Object>> countCusSettlementAmount(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("countCusSettlementAmount",paramMap);
	}

	
}
