package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.ISacSysDicDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacSysDic;

@Repository("sacSysDicDao")
public class SacSysDicDao extends GenericDaoiBatis<SacSysDic, Long> implements ISacSysDicDao
{
    @SuppressWarnings({ "deprecation", "unchecked" })
    public List<SacSysDic> getSacSysDic(String dicType)
    {
	Map<String, Object> param = new HashMap<String, Object>();
	param.put("dicType", dicType);
	return getSqlMapClientTemplate().queryForList("getSacSysDic", param);
    }
}
