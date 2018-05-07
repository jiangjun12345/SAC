package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.FinCodeRuleDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.FinCodeRule;

import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

@Repository("finCodeRuleIbatis")
public class FinCodeRuleIbatis extends GenericDaoiBatis<FinCodeRule, String> implements FinCodeRuleDao {
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<FinCodeRule> getFinCodeRuleList(Map<String, String> param) {
		return getSqlMapClientTemplate().queryForList(
				(iBatisDaoUtils.getSelectListQuery(ClassUtils
						.getShortName(FinCodeRule.class))), param);
	}
}
