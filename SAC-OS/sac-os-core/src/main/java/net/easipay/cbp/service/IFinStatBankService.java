/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.FinMx;
import net.easipay.cbp.model.FinStatBankForm;


/**
 * @author Administrator
 *
 */
public interface IFinStatBankService {
	
	public List<FinStatBankForm> selectFinStatBankForSplit(Map<String,Object> finStatBankMap,int pageNo,int pageSize); 

	public int selectFinStatBankCounts(Map<String,Object> finStatBankMap);
	
	public List<FinMx> selectPreparedFundDetail(Map<String,Object> paramMap);
	
	public int selectPreparedFundDetailCount(Map<String,Object> paramMap);

}
