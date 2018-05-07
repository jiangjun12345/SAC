/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.RecDiffDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.SacRecDifferences;

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
@Repository("recDiffDao")
public class RecDiffIbatis extends GenericDaoiBatis<SacRecDifferences, Long>
		implements RecDiffDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.easipay.cbp.tc.dao.transaction.TcTransactionDao#getTransaction(java
	 * .util.Map)
	 */

	@Override
	public SacRecDifferences getRecDiff(Map<String, Object> param) {
		return (SacRecDifferences) getSqlMapClientTemplate().queryForObject(
				(iBatisDaoUtils.getSelectQuery(ClassUtils
						.getShortName(SacRecDifferences.class))), param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SacRecDifferences> getRecDiffList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(
				(iBatisDaoUtils.getSelectListQuery(ClassUtils
						.getShortName(SacRecDifferences.class))), param);
	}

	@Override
	public void insertRecDiff(SacRecDifferences sacRecDifferences) {
		getSqlMapClientTemplate().insert(
				(iBatisDaoUtils.getInsertQuery(ClassUtils
						.getShortName(SacRecDifferences.class))),
				sacRecDifferences);
	}

	@Override
	public void updateRecDiff(SacRecDifferences sacRecDifferences) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update(
				(iBatisDaoUtils.getUpdateQuery(ClassUtils
						.getShortName(SacRecDifferences.class))),
				sacRecDifferences);
	}

	@Override
	public List<SacRecDifferences> selectDifferencesForSupplement(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(
				"selectDifferencesForSupplement", param);

	}

}
