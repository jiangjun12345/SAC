package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.Map;

import net.easipay.cbp.dao.ISacCusParamDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacCusParameter;

import org.springframework.stereotype.Repository;

@SuppressWarnings("deprecation")
@Repository("sacCusParamDao")
public class SacCusParamDao extends GenericDaoiBatis<SacCusParameter, Long> implements ISacCusParamDao
{

    public SacCusParameter querySacCusParameter(String cusNo, String orgCardId, String sacCurrency, String bussType)
    {
	Map<String, Object> param = new HashMap<String, Object>();
	param.put("cusNo", cusNo);
	param.put("orgCardId", orgCardId);
	param.put("sacCurrency", sacCurrency);
	param.put("bussType", bussType);
	return (SacCusParameter) getSqlMapClientTemplate().queryForObject("getSacCusParameter", param);
    }

    public void insertSacCusParameter(SacCusParameter sacCusParameter)
    {
	getSqlMapClientTemplate().insert("insertSacCusParameter", sacCusParameter);
    }

    public void updateSacCusParameter(SacCusParameter sacCusParameter)
    {
	getSqlMapClientTemplate().update("updateSacCusParameter", sacCusParameter);
    }

}
