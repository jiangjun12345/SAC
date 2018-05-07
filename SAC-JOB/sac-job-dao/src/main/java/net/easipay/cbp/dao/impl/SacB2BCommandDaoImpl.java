package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacB2BCommandDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacB2BCommand;

import org.springframework.stereotype.Repository;

@Repository
public class SacB2BCommandDaoImpl extends GenericDaoiBatis<SacB2BCommand, Long> implements ISacB2BCommandDao {

	@Override
	public List<SacB2BCommand> selectB2BCommandList(Map paramMap) {
		return getSqlMapClientTemplate().queryForList("listSacB2BCommand", paramMap);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void updateB2BCommand(SacB2BCommand b2bCommand) {
		getSqlMapClientTemplate().update("updateSacB2BCommand", b2bCommand);
	}

}
