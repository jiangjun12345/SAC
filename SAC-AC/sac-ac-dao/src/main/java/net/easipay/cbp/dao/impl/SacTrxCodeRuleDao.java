package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacTrxCodeRuleDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacTrxCodeRule;

import org.springframework.stereotype.Repository;


@SuppressWarnings({ "unchecked", "deprecation" })
@Repository("sacTrxCodeRuleDao")
public class SacTrxCodeRuleDao extends GenericDaoiBatis<SacTrxCodeRule, Long> implements ISacTrxCodeRuleDao
{
    public List<SacTrxCodeRule> listSacTrxCodeRule(String trxCode)
    {
	Map<String, Object> param = new HashMap<String, Object>();
	param.put("trxCode", trxCode);
	return getSqlMapClientTemplate().queryForList("listSacTrxCodeRule", param);
    }
}
