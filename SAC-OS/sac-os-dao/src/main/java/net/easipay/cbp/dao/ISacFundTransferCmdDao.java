package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacFundTransferCmd;
/**
 * @author sydai 
 */
public interface ISacFundTransferCmdDao extends GenericDao<SacFundTransferCmd,Long>{

    public int insertSacFundTransferCmd(SacFundTransferCmd sacFundTransferCmd);
	public SacFundTransferCmd getSacFundTransferCmd(Map param);
	public List listSacFundTransferCmd(Map param);
	public int updateSacFundTransferCmd(SacFundTransferCmd sacFundTransferCmd);
	public int deleteSacFundTransferCmd(String id);
	public int deleteListSacFundTransferCmd(String id);
    public int getSacFundTransferCmdCount(Map param);
    public List listSplitSacFundTransferCmd(Map param);
}
