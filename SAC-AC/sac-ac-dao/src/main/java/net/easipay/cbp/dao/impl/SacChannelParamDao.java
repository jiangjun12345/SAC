package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacChannelParamDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacChannelParam;

import org.springframework.stereotype.Repository;

@SuppressWarnings("deprecation")
@Repository("sacChannelParamDao")
public class SacChannelParamDao extends GenericDaoiBatis<SacChannelParam, Long> implements ISacChannelParamDao
{

    @SuppressWarnings("unchecked")
    public List<SacChannelParam> listSacChannelParam(String chnCode, String chnType, String currencyType,String isValidFlag)
    {
	Map<String, Object> param = new HashMap<String, Object>();
	param.put("chnCode", chnCode);
	param.put("chnType", chnType);
	param.put("currencyType", currencyType);
	param.put("isValidFlag", isValidFlag);
	return (List<SacChannelParam>) getSqlMapClientTemplate().queryForList("listSacChannelParam", param);
    }

    public void insertSacChannelParam(SacChannelParam sacChannelParam)
    {
	getSqlMapClientTemplate().insert("insertSacChannelParam", sacChannelParam);
    }

    public void updateSacChannelParam(SacChannelParam sacChannelParam)
    {
	getSqlMapClientTemplate().update("updateSacChannelParam", sacChannelParam);

    }

}
