package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

import net.easipay.cbp.dao.FinCode1Dao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.FinCode1;

@Repository("finCode1Ibatis")
public class FinCode1Ibatis extends GenericDaoiBatis<FinCode1, String> implements FinCode1Dao{

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<FinCode1> getFinCode1List(Map<String, String> param) {
		return getSqlMapClientTemplate().queryForList(
				(iBatisDaoUtils.getSelectListQuery(ClassUtils
						.getShortName(FinCode1.class))), param);
	}

}
