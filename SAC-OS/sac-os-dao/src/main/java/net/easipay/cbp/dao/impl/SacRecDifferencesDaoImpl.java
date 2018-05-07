/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacRecDifferencesDao;
import net.easipay.cbp.model.SacRecDifferences;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;

/**
 * @author Administrator
 *
 */
@SuppressWarnings({ "deprecation", "unchecked" })
@Repository("sacRecDifferences")
public class SacRecDifferencesDaoImpl extends GenericDaoiBatis<SacRecDifferences,Long> implements ISacRecDifferencesDao{
	public SacRecDifferencesDaoImpl(){
		super(SacRecDifferences.class);
	}
	
	public SacRecDifferencesDaoImpl(Class<SacRecDifferences> persistentClass) {
		super(persistentClass);
	}

	@Override
	public List<SacRecDifferences> selectSacRecDifferences(
			Map<String,Object> queryMap,int pageNo,int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		String recDate = queryMap.get("recDate")+"";
		if(StringUtils.isNotBlank(recDate)&&!"null".equals(recDate)){
			recDate = recDate.replaceAll("-", "");
			queryMap.put("recDate", recDate);
		}
		
		queryMap.put("start", start);
		queryMap.put("end", end);
		return  getSqlMapClientTemplate().queryForList("listSplitSacRecDifferences",queryMap);
	}
	
	@Override
	public List<SacRecDifferences> selectSacRecDifferencesDown(
			Map<String,Object> queryMap) {		
		return  getSqlMapClientTemplate().queryForList("listSplitSacRecDifferences",queryMap);
	}

	@Override
	public int selectSacRecDifferencesCounts(Map<String,Object> queryMap) {
		String recDate = queryMap.get("recDate")+"";
		if(StringUtils.isNotBlank(recDate)&&!"null".equals(recDate)){
			recDate = recDate.replaceAll("-", "");
			queryMap.put("recDate", recDate);
		}
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacRecDifferencesCount", queryMap);
	}

	@Override
	public void updateSacRecDifferencesByTrxSerialNo(
			SacRecDifferences sacRecDifferences) {
		getSqlMapClientTemplate().update("updateSacRecDifferencesBySerailNo", sacRecDifferences);
		
	}
	
	@Override
	public void updateSacRecDifferences(SacRecDifferences sacRecDifferences) {
		getSqlMapClientTemplate().update("updateSacRecDifferences", sacRecDifferences);
		
	}

	@Override
	public List<SacRecDifferences> queryRecDiffDetail(Map<String, Object> paramMap)
	{
		return this.getSqlMapClientTemplate().queryForList("listSplitSacRecDifferences2", paramMap);
	}

	@Override
	public Integer queryRecDiffDetailCount(Map<String, Object> paramMap)
	{
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacRecDifferencesCount2", paramMap);
	}

	@Override
	public List<SacRecDifferences> simpleQueryRecDiffDetail(Map<String, Object> paramMap)
	{
		return this.getSqlMapClientTemplate().queryForList("listSacRecDifferences", paramMap);
	}

	@Override
	public List<SacRecDifferences> selectSacRecDifferencesByParam(
			Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("listSacRecDifferences", queryMap);
	}

	@Override
	public List<Map<String,Object>> selectDifferencesForSupplement(
			Map<String, Object> queryMap, int pageNo, int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		queryMap.put("start", start);
		queryMap.put("end", end);
		return this.getSqlMapClientTemplate().queryForList("selectDifferencesForSupplement", queryMap);
	}

	@Override
	public int selectDifferencesForSupplementCounts(Map<String, Object> queryMap) {
		return (Integer)getSqlMapClientTemplate().queryForObject("selectDifferencesForSupplementCounts", queryMap);
	}
	

}
