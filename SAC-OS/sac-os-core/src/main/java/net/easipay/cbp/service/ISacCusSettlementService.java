/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.model.SacCusSettlement;


/**
 * @author Administrator
 *
 */
public interface ISacCusSettlementService {
	
	public List<SacCusSettlement> selectSacCusSettlement(SacCusSettlement sacCusSettlement,int pageNo,int pageSize); 

	public int selectSacCusSettlementCounts(SacCusSettlement sacCusSettlement);

	public void updateSacCusSettlement(SacCusSettlement sacCusSettlement);

	public SacCusSettlement selectSacCusSettlementById(
			SacCusSettlement sacCusSettlement);

	/**
	 * 实付勾兑处理
	 * @param sacCusSettlement
	 */
	public void dealRealPayWipe(SacCusSettlement sacCusSettlement);

	/**
	 * 结算划款处理
	 * @param sacCusSettlement
	 */
	public void dealSettleAllot(SacCusSettlement sacCusSettlement);

	public void dealSettleAllotResponse(SacCusSettlement sacCusSettlement);

}
