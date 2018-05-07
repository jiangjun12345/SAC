/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacDepositDetailDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacDepositDetail;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
/**
 * @author Administrator
 *
 */

@Repository("sacDepositDetail")
public class SacDepositDetailDaoImpl extends GenericDaoiBatis<SacDepositDetail,Long> implements ISacDepositDetailDao{
	private static final Logger logger = Logger.getLogger(SacDepositDetailDaoImpl.class);
	
	public SacDepositDetailDaoImpl(){
		super(SacDepositDetail.class);
	}
	
	public SacDepositDetailDaoImpl(Class<SacDepositDetail> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<SacDepositDetail> findDepositDetailByParam(
			Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("listSacDepositDetail", queryMap);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<SacDepositDetail> findDepositDetailByParamForValid(
			Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("listSacDepositDetailForValid", queryMap);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getCountsByParam(Map<String, Object> queryMap) {
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacDepositDetailCount", queryMap);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateDepositDetailForReMake(SacDepositDetail detail) {
		getSqlMapClientTemplate().update("updateSacDepositDetailForReMake", detail);
		
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<SacDepositDetail> getDepositDetailByPaging(
			Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("listSplitSacDepositDetail", queryMap);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Integer getMunualMatchCheckCounts(Map<String, Object> queryMap) {
		return (Integer)getSqlMapClientTemplate().queryForObject("getMunualMatchCheckCounts", queryMap);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Map<String, Object>> getMunualMatchCheckInfo(
			Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("listSplitMunualMatchCheck", queryMap);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteDetailByBatchId(Map<String, Object> deleteMap) {
		getSqlMapClientTemplate().delete("deleteSacDepositDetail", deleteMap);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateDepositDetailSpecial(SacDepositDetail detail) {
		getSqlMapClientTemplate().update("updateDepositDetailSpecial", detail);
		
	}


	
}
