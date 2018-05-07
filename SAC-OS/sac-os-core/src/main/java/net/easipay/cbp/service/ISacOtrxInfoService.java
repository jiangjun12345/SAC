/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.model.SacOtrxInfo;


/**
 * @author Administrator
 *
 */
public interface ISacOtrxInfoService {
	
	public List<SacOtrxInfo> selectSacOtrxInfo(SacOtrxInfo sacOtrxInfo,int pageNo,int pageSize); 
	
	public List<SacOtrxInfo> selectSacOtrxInfoByParam(
			SacOtrxInfo sacOtrxInfo,int pageNo,int pageSize);
	
	public SacOtrxInfo selectSacOtrxInfoById(SacOtrxInfo sacOtrxInfo); 

	public int selectSacOtrxInfoCounts(SacOtrxInfo sacOtrxInfo);

	public List<SacOtrxInfo> selectSacOtrxInfoForRefund(
			SacOtrxInfo sacOtrxInfo, int pageNo, int pageSize);

	public Integer selectSacOtrxInfoCountsForRefund(SacOtrxInfo sacOtrxInfo);

	public void updateSacOtrxInfo(SacOtrxInfo sacOtrxInfo);

	/**
	 * 冲正
	 * @param trxList
	 */
	public void reversalTransaction(List<SacOtrxInfo> trxList,SacOtrxInfo sacOtrxInfo);

	public void updateSacOtrxInfoForDB(SacOtrxInfo sacOtrxInfo);
}
