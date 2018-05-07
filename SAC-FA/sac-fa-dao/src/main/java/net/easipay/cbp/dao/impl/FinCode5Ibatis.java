package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.FinCode5Dao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.FinCode5;

import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

@Repository("finCode5Ibatis")
public class FinCode5Ibatis extends GenericDaoiBatis<FinCode5, String> implements FinCode5Dao {
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<FinCode5> getFinCode5List(Map<String, String> param) {
		return getSqlMapClientTemplate().queryForList(
				(iBatisDaoUtils.getSelectListQuery(ClassUtils
						.getShortName(FinCode5.class))), param);
	}
}
