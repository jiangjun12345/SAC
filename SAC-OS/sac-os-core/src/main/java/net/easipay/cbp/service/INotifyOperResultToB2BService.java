package net.easipay.cbp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacB2BCommand;
import net.easipay.cbp.model.SacB2bCmdBatch;
import net.easipay.cbp.model.SacChargeApply;
import net.easipay.cbp.model.SacDepositBatch;
import net.easipay.cbp.model.SacDepositDetail;
import net.easipay.cbp.model.SacFundTransferCmd;
import net.easipay.cbp.model.form.NotifyOflCommandResultForm;
import net.easipay.cbp.model.form.NotifyPrestoreResultForm;
import net.easipay.dsfc.ws.jws.JwsResult;


/**
 * 
* ClassName: INotifyOperResultToB2BService <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年3月7日 下午3:13:45 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */
public interface INotifyOperResultToB2BService
{

	public JwsResult notifyPreStoreResultDff(List<NotifyPrestoreResultForm> details);
	
	public Map<String,String> notifyPreStoreResult(SacDepositDetail detail,SacDepositBatch batch,SacChargeApply apply,int countDff,BigDecimal amountDff);

	public Map<String, String> notifyPreStoreResultForMunualMatch(
			SacDepositDetail detail, SacChargeApply apply);

	public void notifyFundGiveOnline (SacB2BCommand cmd, SacB2bCmdBatch batch) ;
	
	public JwsResult notifyFundGiveOffline(NotifyOflCommandResultForm cmd);
	
	public void notifyFundAllot(SacFundTransferCmd fundTransferCmd) ;
	
}
