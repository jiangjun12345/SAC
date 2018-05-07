/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.OriTrxDetailDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.SacTrxDetail;

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
@Repository("oriTrxDetailDao")
public class OriTrxDetailIbatis extends GenericDaoiBatis<SacTrxDetail, Long>
		implements OriTrxDetailDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.easipay.cbp.tc.dao.transaction.TcTransactionDao#getTransaction(java
	 * .util.Map)
	 */

	@Override
	public SacTrxDetail getOriTrxDetail(Map<String, Object> param) {
		return (SacTrxDetail) getSqlMapClientTemplate().queryForObject(
				(iBatisDaoUtils.getSelectQuery(ClassUtils
						.getShortName(SacTrxDetail.class))), param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SacTrxDetail> getOriTrxDetailList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(
				(iBatisDaoUtils.getSelectListQuery(ClassUtils
						.getShortName(SacTrxDetail.class))), param);
	}

	@Override
	public void updateOriTrxDetail(SacTrxDetail sacTrxDetail) {
		getSqlMapClientTemplate().update(
				(iBatisDaoUtils.getUpdateQuery(ClassUtils
						.getShortName(SacTrxDetail.class))), sacTrxDetail);
	}
	
	@Override
	public void updateOriTrxDetailStatus(SacTrxDetail sacTrxDetail) {
	  // TODO Auto-generated method stub
    getSqlMapClientTemplate().update("updateSacTrxDetailStatus", sacTrxDetail);
  }

}
