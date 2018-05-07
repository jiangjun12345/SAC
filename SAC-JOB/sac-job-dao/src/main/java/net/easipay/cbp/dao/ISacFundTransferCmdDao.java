package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacFundTransferCmd;
/**
 * @author sydai 
 */
@SuppressWarnings("rawtypes")
public interface ISacFundTransferCmdDao extends GenericDao<SacFundTransferCmd,Long>{

	public List<SacFundTransferCmd> selectSacFundTransferCmdList(Map param);
	
	public int updateSacFundTransferCmd(SacFundTransferCmd sacFundTransferCmd);
	
}
