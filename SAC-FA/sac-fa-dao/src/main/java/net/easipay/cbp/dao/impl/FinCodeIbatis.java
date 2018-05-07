package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;

import net.easipay.cbp.dao.FinCodeDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.FinCode;

import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

@Repository("finCodeIbatis")
public class FinCodeIbatis extends GenericDaoiBatis<FinCode, String> implements FinCodeDao{

	@SuppressWarnings({ "deprecation" })
	@Override
	public FinCode getFinCode(String finCodeId) {
		return (FinCode) getSqlMapClientTemplate().queryForObject(
				(iBatisDaoUtils.getFindQuery(ClassUtils
						.getShortName(FinCode.class))), finCodeId);
	}
	
	
	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<FinCode> getLikeFinCodes(String finCodeId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("codeId", finCodeId);
		return getSqlMapClientTemplate().queryForList(
				"getLikeFinCodes", map);
	}
}
