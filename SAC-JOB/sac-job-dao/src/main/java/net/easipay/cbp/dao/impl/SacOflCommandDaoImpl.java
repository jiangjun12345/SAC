package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacOflCommandDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacOflCommand;

import org.springframework.stereotype.Repository;


@Repository
public class SacOflCommandDaoImpl extends GenericDaoiBatis<SacOflCommand,Long> implements ISacOflCommandDao{

	@Override
	public List<SacOflCommand> selectSacOflCommandList(Map param) {
		return this.getSqlMapClientTemplate().queryForList("listSacOflCommand", param);
	}

	@Override
	public void updateSacOflCommand(SacOflCommand sacOflCommand) {
		getSqlMapClientTemplate().update("updateSacOflCommand", sacOflCommand);
	}
	


	
}
