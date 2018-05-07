/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacChnSettlementDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacChnSettlement;
import net.easipay.cbp.util.BeanUtils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */

@Repository("sacChnSettlement")
public class SacChnSettlementDaoImpl extends GenericDaoiBatis<SacChnSettlement,Long> implements ISacChnSettlementDao{
	private static final Logger logger = Logger.getLogger(SacChnSettlementDaoImpl.class);
	
	public SacChnSettlementDaoImpl(){
		super(SacChnSettlement.class);
	}
	
	public SacChnSettlementDaoImpl(Class<SacChnSettlement> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SacChnSettlement> selectSacChnSettlement(
			SacChnSettlement sacChnSettlement,int pageNo,int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		Map<String,Object> queryMap =null;
		queryMap = BeanUtils.transBean2Map(sacChnSettlement);
		queryMap.put("start", start);
		queryMap.put("end", end);
		return  getSqlMapClientTemplate().queryForList("listSplitSacChnSettlement",queryMap);
	}

	@Override
	public int selectSacChnSettlementCounts(SacChnSettlement sacChnSettlement) {
		Map<String,Object> queryMap =null;
		queryMap = BeanUtils.transBean2Map(sacChnSettlement);
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacChnSettlementCount", queryMap);
	}

	@Override
	public void updateSacChnSettlement(SacChnSettlement sacChnSettlement) {
		getSqlMapClientTemplate().update("updateSacChnSettlement", sacChnSettlement);
		
	}

	@Override
	public SacChnSettlement selectSacChnSettlementById(
			SacChnSettlement sacChnSettlement) {
		return (SacChnSettlement)getSqlMapClientTemplate().queryForObject("listSacChnSettlement", sacChnSettlement);
	}

	@Override
	public List<SacChnSettlement> queryChnSettlement(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("listSplitSacChnSettlement",paramMap);
	}

	@Override
	public int queryChnSettlementCount(Map<String, Object> paramMap)
	{
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacChnSettlementCount", paramMap);
	}

	@Override
	public List<SacChnSettlement> simpleQueryChnSettlement(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("listSacChnSettlementByMap",paramMap);
	}

	@Override
	public List<Map<String, Object>> countChnSettlementAmount(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("countChnSettlementAmount",paramMap);
	}



	
}
