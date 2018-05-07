package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.FinCode6Dao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.FinCode6;

import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

@Repository("finCode6Ibatis")
public class FinCode6Ibatis extends GenericDaoiBatis<FinCode6, String> implements FinCode6Dao {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<FinCode6> getFinCode6List(Map<String, String> param) {
		return getSqlMapClientTemplate().queryForList(
				(iBatisDaoUtils.getSelectListQuery(ClassUtils
						.getShortName(FinCode6.class))), param);
	}

}
