package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.FinCode2Dao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.FinCode2;

import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

@Repository("finCode2Ibatis")
public class FinCode2Ibatis extends GenericDaoiBatis<FinCode2, String> implements FinCode2Dao{

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<FinCode2> getFinCode2List(Map<String, String> param) {
		return getSqlMapClientTemplate().queryForList(
				(iBatisDaoUtils.getSelectListQuery(ClassUtils
						.getShortName(FinCode2.class))), param);
	}

}
