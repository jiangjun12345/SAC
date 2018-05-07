package net.easipay.cbp.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacRecDiffDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacRecDifferences;

import org.springframework.stereotype.Repository;

@SuppressWarnings({ "deprecation", "unchecked" })
@Repository("sacRecDiffDao")
public class SacRecDiffDao extends GenericDaoiBatis<SacRecDifferences, Long> implements ISacRecDiffDao
{
    public List<SacRecDifferences> querySacRecDifferencesList(String payconType, String recOper, Date recStartDate, Date recEndDate, String status)
    {
	Map<String, Object> param = new HashMap<String, Object>();
	param.put("payconType", payconType);
	param.put("recOper", recOper);
	param.put("recStartDate", recStartDate);
	param.put("recEndDate", recEndDate);
	param.put("status", status);
	return getSqlMapClientTemplate().queryForList("listSacRecDifferences", param);
    }

    public void updateSacRecDifferences(SacRecDifferences sacRecDifferences)
    {
	getSqlMapClientTemplate().update("updateSacRecDifferences", sacRecDifferences);
    }

}
