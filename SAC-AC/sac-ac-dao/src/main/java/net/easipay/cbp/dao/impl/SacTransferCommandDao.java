package net.easipay.cbp.dao.impl;

import net.easipay.cbp.dao.ISacTransferCommandDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.FinTask;
import net.easipay.cbp.model.SacDffOflCommand;
import net.easipay.cbp.model.SacTransferCommand;

import org.springframework.stereotype.Repository;

@SuppressWarnings("deprecation")
@Repository("sacTransferCommandDao")
public class SacTransferCommandDao extends GenericDaoiBatis<FinTask, Long> implements ISacTransferCommandDao
{
    @Override
    public void insertSacTransferCommand(SacTransferCommand sacTransferCommand)
    {
	getSqlMapClientTemplate().insert("insertSacB2BCommand", sacTransferCommand);
    }

	@Override
	public void insertSacDffOflCommand(SacDffOflCommand sacDffOflCommand) {
		getSqlMapClientTemplate().insert("insertSacDffOflCommand", sacDffOflCommand);
	}

}
