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
public class SacFundTransferCmdDaoImpl extends GenericDaoiBatis<SacFundTransferCmd, Long> implements ISacFundTransferCmdDao {

	public int insertSacFundTransferCmd(SacFundTransferCmd sacFundTransferCmd) {
		Object o = this.getSqlMapClientTemplate().insert("insertSacFundTransferCmd", sacFundTransferCmd);
		if(o==null){
			return 1;
		}else{
			return 0;
		}
	}

	public SacFundTransferCmd getSacFundTransferCmd(Map param) {
		return (SacFundTransferCmd) this.getSqlMapClientTemplate().queryForObject("getSacFundTransferCmd", param);
	}

	public List listSacFundTransferCmd(Map param) {
		return this.getSqlMapClientTemplate().queryForList("listSacFundTransferCmd", param);
	}

	public int updateSacFundTransferCmd(SacFundTransferCmd sacFundTransferCmd) {
		return this.getSqlMapClientTemplate().update("updateSacFundTransferCmd", sacFundTransferCmd);
	}

	public int deleteSacFundTransferCmd(String id) {
		return this.getSqlMapClientTemplate().update("deleteSacFundTransferCmd", id);
	}

	public int deleteListSacFundTransferCmd(String id) {
		return this.getSqlMapClientTemplate().update("deleteListSacFundTransferCmd", id);
	}

	public int getSacFundTransferCmdCount(Map param) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("getSacFundTransferCmdCount", param);
		return count.intValue();
	}

	public List listSplitSacFundTransferCmd(Map param) {
		return this.getSqlMapClientTemplate().queryForList("listSplitSacFundTransferCmd", param);

	}
}
