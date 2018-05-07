/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.SacRefund;


/**
 * @author Administrator
 *
 */
public interface ISacRefundService {
	
	public List<SacRefund> selectSacRefund(SacRefund sacRefund,int pageNo,int pageSize); 
	
	public SacRefund selectSacRefundById(SacRefund sacRefund); 

	public int selectSacRefundCounts(SacRefund sacRefund);

	public void updateSacRefund(SacRefund sacRefund);

	public void dealRefund(SacOtrxInfo sacOtrxInfo);
}
