package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.FinYeDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.FinDailyBalance;
import net.easipay.cbp.model.FinYe;

import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

@Repository("finYeIbatis")
public class FinYeIbatis extends GenericDaoiBatis<FinYe, Long> implements FinYeDao
{

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public List<FinYe> getYeInList(Map<String, String> param)
    {
	return getSqlMapClientTemplate().queryForList(("getFinYesIn"), param);
    }

    public List<FinYe> getYes(Map<String, String> param)
    {
	return null;
    }

    @SuppressWarnings({ "deprecation" })
    @Override
    public FinYe getYe(String yeId)
    {
	return (FinYe) getSqlMapClientTemplate().queryForObject((iBatisDaoUtils.getFindQuery(ClassUtils.getShortName(FinYe.class))), yeId);
    }
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public List<FinYe> getBalanceByParam(Map<String, String> param)
    {
	return getSqlMapClientTemplate().queryForList(("getBalanceByParam"), param);
    }

	@SuppressWarnings("deprecation")
	@Override
	public FinDailyBalance getDailyYe(Map<String, String> param) {
		return (FinDailyBalance)getSqlMapClientTemplate().queryForObject("listSplitFinStatOrgDayYe",param);
	}
}
