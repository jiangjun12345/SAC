package net.easipay.cbp.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.FinMxDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.FinMx;

import org.springframework.stereotype.Repository;

@Repository("finMxIbatis")
public class FinMxIbatis extends GenericDaoiBatis<FinMx, Long> implements FinMxDao {

	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<FinMx> getFinMxList(Map<String, String> param){
		return getSqlMapClientTemplate().queryForList(
				"getFinMxList", param);
	}

	@Override
	@SuppressWarnings({ "deprecation" })
	public FinMx getFinMxFirst(Map<String, String> param) {
		return (FinMx)getSqlMapClientTemplate().queryForObject(
				"statFinMxFirst", param);
	}

	@Override
	@SuppressWarnings({ "deprecation" })
	public FinMx getFinMxEnd(Map<String, String> param) {
		return (FinMx)getSqlMapClientTemplate().queryForObject(
				"statFinMxEnd", param);
	}

	@Override
	@SuppressWarnings({ "deprecation" })
	public BigDecimal getFinMxSumFdebit(Map<String, String> param) {
		return (BigDecimal)getSqlMapClientTemplate().queryForObject(
				"statFinMxSumFdebit", param);
	}

	@Override
	@SuppressWarnings({ "deprecation" })
	public BigDecimal getFinMxSumFcredit(Map<String, String> param) {
		return (BigDecimal)getSqlMapClientTemplate().queryForObject(
				"statFinMxSumFcredit", param);
	}

}
