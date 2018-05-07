package net.easipay.cbp.dao.impl;

import net.easipay.cbp.dao.ISacRefundCommandDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.FinTask;
import net.easipay.cbp.model.SacRefundCommand;

import org.springframework.stereotype.Repository;

@Repository("sacRefundCommandDao")
public class SacRefundCommandDao  extends GenericDaoiBatis<FinTask, Long> implements ISacRefundCommandDao
{
    @SuppressWarnings("deprecation")
    @Override
    public void insertSacRefundCommand(SacRefundCommand sacRefundCommand)
    {
	getSqlMapClientTemplate().insert("insertSacRefundCommand", sacRefundCommand);	
    }
}
