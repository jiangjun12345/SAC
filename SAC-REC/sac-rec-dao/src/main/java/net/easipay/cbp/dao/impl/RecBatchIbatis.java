/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.RecBatchDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.SacRecBatch;

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
@Repository("recBatchDao")
public class RecBatchIbatis extends GenericDaoiBatis<SacRecBatch, Long>
		implements RecBatchDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.easipay.cbp.tc.dao.transaction.TcTransactionDao#getTransaction(java
	 * .util.Map)
	 */

	@Override
	public SacRecBatch getRecBatch(Map<String, String> param) {
		return (SacRecBatch) getSqlMapClientTemplate().queryForObject(
				(iBatisDaoUtils.getSelectQuery(ClassUtils
						.getShortName(SacRecBatch.class))), param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SacRecBatch> getRecBatchList(Map<String, String> param) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(
				(iBatisDaoUtils.getSelectListQuery(ClassUtils
						.getShortName(SacRecBatch.class))), param);
	}

	@Override
	public void updateRecBatch(SacRecBatch sacRecBatch) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update(
				(iBatisDaoUtils.getUpdateQuery(ClassUtils
						.getShortName(SacRecBatch.class))), sacRecBatch);
	}

}
