package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacRefundCommandDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacRefundCommand;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class SacRefundCommandDaoImpl extends GenericDaoiBatis<SacRefundCommand, Long> implements ISacRefundCommandDao {

    public static final Logger logger = Logger.getLogger(SacRefundCommandDaoImpl.class);

    @Override
	public List<SacRefundCommand> selectSacRefundCommandList(Map paramMap) {
    	return getSqlMapClientTemplate().queryForList("listSacRefundCommand", paramMap);
	}
    
    
	@SuppressWarnings("deprecation")
	@Override
	public void insertRefundCommand(SacRefundCommand refundCommand) {
		getSqlMapClientTemplate().insert("insertSacRefundCommand", refundCommand);
	}


	@Override
	public void updateSacRefundCommand(SacRefundCommand refundCommand) {
		getSqlMapClientTemplate().insert("updateSacRefundCommand", refundCommand);
	}

}
