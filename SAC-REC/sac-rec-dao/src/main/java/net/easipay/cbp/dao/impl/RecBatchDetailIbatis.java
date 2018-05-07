/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.RecBatchDetailDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.SacRecDetail;

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
@Repository("recBatchDetailDao")
public class RecBatchDetailIbatis extends GenericDaoiBatis<SacRecDetail, Long>
		implements RecBatchDetailDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.easipay.cbp.tc.dao.transaction.TcTransactionDao#getTransaction(java
	 * .util.Map)
	 */

	@Override
	public SacRecDetail selectRecBatchDetail(Map<String, String> param) {
		return (SacRecDetail) getSqlMapClientTemplate().queryForObject(
				(iBatisDaoUtils.getSelectQuery(ClassUtils
						.getShortName(SacRecDetail.class))), param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SacRecDetail> selectRecBatchDetailList(Map<String, String> param) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(
				(iBatisDaoUtils.getSelectListQuery(ClassUtils
						.getShortName(SacRecDetail.class))), param);
	}

	@Override
	public void updateRecBatchDetail(SacRecDetail sacRecDetail) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update(
				(iBatisDaoUtils.getUpdateQuery(ClassUtils
						.getShortName(SacRecDetail.class))), sacRecDetail);

	}

}
