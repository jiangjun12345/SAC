package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.FinCode3Dao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.FinCode3;

import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

@Repository("finCode3Ibatis")
public class FinCode3Ibatis extends GenericDaoiBatis<FinCode3, String> implements FinCode3Dao{

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<FinCode3> getFinCode3List(Map<String, String> param) {
		return getSqlMapClientTemplate().queryForList(
				(iBatisDaoUtils.getSelectListQuery(ClassUtils
						.getShortName(FinCode3.class))), param);
	}
	
	@SuppressWarnings({ "deprecation" })
	@Override
	public FinCode3 getFinCode3(String finCode3Id) {
		return (FinCode3) getSqlMapClientTemplate().queryForObject(
				(iBatisDaoUtils.getFindQuery(ClassUtils
						.getShortName(FinCode3.class))), finCode3Id);
	}

}
