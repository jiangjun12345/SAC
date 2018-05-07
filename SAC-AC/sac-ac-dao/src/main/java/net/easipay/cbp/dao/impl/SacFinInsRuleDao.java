package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacFinInsRuleDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.FinTask;
import net.easipay.cbp.model.SacFinInsRule;

import org.springframework.stereotype.Repository;

@SuppressWarnings({ "unchecked", "deprecation" })
@Repository("sacFinInsRuleDao")
public class SacFinInsRuleDao extends GenericDaoiBatis<FinTask, Long> implements ISacFinInsRuleDao
{

    public List<SacFinInsRule> listSacFinInsRule(String trxCode, String trxState)
    {
	Map<String, Object> param = new HashMap<String, Object>();
	param.put("trxCode", trxCode);
	param.put("trxState", trxState);
	return getSqlMapClientTemplate().queryForList("listSacFinInsRule", param);
    }

}
