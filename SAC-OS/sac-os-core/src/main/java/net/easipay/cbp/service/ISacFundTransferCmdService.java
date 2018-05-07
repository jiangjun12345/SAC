package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacFundTransferCmd;
import net.easipay.cbp.model.SacOtrxInfo;

/**
 * @author sydai 
 */
public interface ISacFundTransferCmdService {    
   
	public int insertSacFundTransferCmd(SacFundTransferCmd sacFundTransferCmd);
	
	public List<SacFundTransferCmd> selectSacFundTransferCmd(Map paramMap);
	
	public int selectSacFundTransferCmdCount(Map paramMap);
	
	public int updateSacFundTransferCmd(SacFundTransferCmd sacFundTransferCmd);
	
	public List<SacFundTransferCmd> selectAllSacFundTransferCmd(Map paramMap);
	
	public List<SacOtrxInfo> installSacOtrxInfo(SacFundTransferCmd sacFundTransferCmd,String etrxSerialNo);
	
	public String fundTransferCmdToDSF(SacFundTransferCmd sacFundTransferCmd) throws Exception;//发送资金调拨指令到代收付系统 
}

