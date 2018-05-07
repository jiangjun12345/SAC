/**
 * 
 */
package net.easipay.cbp.service;

import java.math.BigDecimal;
import java.util.List;

import net.easipay.cbp.model.SacChnSettlement;


/**
 * @author Administrator
 *
 */
public interface ISacChnSettlementService {
	
	public List<SacChnSettlement> selectSacChnSettlement(SacChnSettlement sacChnSettlement,int pageNo,int pageSize); 

	public int selectSacChnSettlementCounts(SacChnSettlement sacChnSettlement);

	public void updateSacChnSettlement(SacChnSettlement sacChnSettlement);

	public SacChnSettlement selectSacChnSettlementById(
			SacChnSettlement sacChnSettlement);

	/**
	 * 勾兑处理 
	 * @param sacChnSettlement
	 */
	public void dealRealReceiveWipe(SacChnSettlement sacChnSettlement,BigDecimal realTotAmount);

}
