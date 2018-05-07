package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinCodeRule;

public interface FinCodeRuleDao extends GenericDao<FinCodeRule, String>{
	/**
	 * 获取所有的Code1数据
	 * 
	 * @param param
	 * @return
	 */
	public List<FinCodeRule> getFinCodeRuleList(Map<String, String> param);

}
