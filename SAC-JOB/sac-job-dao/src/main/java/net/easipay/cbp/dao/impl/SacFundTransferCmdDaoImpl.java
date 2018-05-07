package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.ISacFundTransferCmdDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacFundTransferCmd;

/**
 * @author sydai
 */
@Repository
@SuppressWarnings({"deprecation","rawtypes","unchecked"})
public class SacFundTransferCmdDaoImpl extends GenericDaoiBatis<SacFundTransferCmd, Long> implements ISacFundTransferCmdDao {

	@Override
	public List<SacFundTransferCmd> selectSacFundTransferCmdList(Map param) {
		return this.getSqlMapClientTemplate().queryForList("listSacFundTransferCmd", param);
	}
	
	@Override
	public int updateSacFundTransferCmd(SacFundTransferCmd sacFundTransferCmd) {
		return this.getSqlMapClientTemplate().update("updateSacFundTransferCmd", sacFundTransferCmd);
	}
	
}
