/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.OriTransactionDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.SacOtrxInfo;

import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-4-2 下午12:02:57
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */
@SuppressWarnings("deprecation")
@Repository("oriTransactionDao")
public class OriTransactionIbatis extends GenericDaoiBatis<SacOtrxInfo, Long>
		implements OriTransactionDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.easipay.cbp.tc.dao.transaction.TcTransactionDao#getTransaction(java
	 * .util.Map)
	 */

	@Override
	public SacOtrxInfo getOriTransaction(Map<String, Object> param) {
		return (SacOtrxInfo) getSqlMapClientTemplate().queryForObject(
				(iBatisDaoUtils.getSelectQuery(ClassUtils
						.getShortName(SacOtrxInfo.class))), param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SacOtrxInfo> getOriTransactionList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(
				(iBatisDaoUtils.getSelectListQuery(ClassUtils
						.getShortName(SacOtrxInfo.class))), param);
	}

	@Override
	public void updateOriTransaction(SacOtrxInfo sacOtrxInfo) {
		getSqlMapClientTemplate().update(
				(iBatisDaoUtils.getUpdateQuery(ClassUtils
						.getShortName(SacOtrxInfo.class))), sacOtrxInfo);
	}

	@Override
	public Integer getSuccTrxCountForOneDay(Map<String, String> param) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				(iBatisDaoUtils.getListCountQuery(ClassUtils
						.getShortName(SacOtrxInfo.class))), param);
	}

	@Override
	public List<SacOtrxInfo> getSplictTrxListForOneDay(Map<String, String> param) {
		return getSqlMapClientTemplate().queryForList(
				(iBatisDaoUtils.getListSplictQuery(ClassUtils
						.getShortName(SacOtrxInfo.class))), param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getAmountGroupByCus(Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("getAmountGroupByCus", queryMap);
	}

	@Override
	public List<Map<String, Object>> getRefundAmountGroupByCus(
			Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("getRefundAmountGroupByCus", queryMap);
	}

  @Override
  public void updateOriTransactionStatus(SacOtrxInfo sacOtrxInfo) {
    // TODO Auto-generated method stub
    getSqlMapClientTemplate().update("updateSacOtrxInfoStatus", sacOtrxInfo);
  }

@SuppressWarnings("unchecked")
@Override
public List<Map<String, Object>> getSacTrxRemittance(Map<String, Object> queryMap) {
	return getSqlMapClientTemplate().queryForList("getSacTrxRemittance", queryMap);
}

@Override
public void updateSacTrxRemittanceDealFlag(Map<String,Object> updateMap) {
	
	getSqlMapClientTemplate().update("updateSacTrxRemittanceDealFlag",updateMap);
	
}

}
