package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.FinCode4Dao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.FinCode4;

import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

@Repository("finCode4Ibatis")
public class FinCode4Ibatis extends GenericDaoiBatis<FinCode4, String> implements FinCode4Dao{

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<FinCode4> getFinCode4List(Map<String, String> param) {
		return getSqlMapClientTemplate().queryForList(
				(iBatisDaoUtils.getSelectListQuery(ClassUtils
						.getShortName(FinCode4.class))), param);
	}
}
