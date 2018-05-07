package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacOnlCommandDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacB2BCommand;

import org.springframework.stereotype.Repository;


@Repository
public class SacOnlCommandDaoImpl extends GenericDaoiBatis<SacB2BCommand,Long> implements ISacOnlCommandDao{

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Map<String,Object>> getSumAmountForBatch(
			Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("getSumAmountForBatch", queryMap);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateCommandForBatch(Map<String, Object> updateMap) {
		getSqlMapClientTemplate().update("updateCmdForBatch", updateMap);
		
	}


	
}
