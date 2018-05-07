package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.Map;

import net.easipay.cbp.dao.FinStatBankDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.FinStatBank;

import org.springframework.stereotype.Repository;

@Repository("finStatBankIbatis")
public class FinStatBankIbatis extends GenericDaoiBatis<FinStatBank, Long> implements FinStatBankDao {

    @SuppressWarnings("deprecation")
    @Override
    public Map<String,String> callSpFaStatBank(String statDate)
    {
	Map<String, String> params = new HashMap<String, String>();
	params.put("p_stat_date", statDate);
	params.put("err_num", "");
	params.put("err_msg", "");
	getSqlMapClientTemplate().queryForObject("callSpFaStatBank", params);
	return params;
    }
}
