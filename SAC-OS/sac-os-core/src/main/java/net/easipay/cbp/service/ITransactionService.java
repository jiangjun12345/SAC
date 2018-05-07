/**
 * 
 */
package net.easipay.cbp.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacCheckInfo;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.SacRecDifferences;
import net.easipay.cbp.model.form.FinTaskReceiveForm;
import net.easipay.cbp.model.form.SacRemittanceBatchIdForm;
import net.easipay.cbp.model.form.SacTransationReceiveForm;
import net.easipay.cbp.model.form.SacTransationSendForm;
import net.easipay.dsfc.ws.jws.JwsResult;


/**
 * @author Administrator
 *
 */
public interface ITransactionService {

	public JwsResult transactionDealNewInterface(List<SacOtrxInfo> trxList);
	
	public JwsResult updateTransactionStateInterface(SacOtrxInfo sacOtrxInfo);
	
	public void updateSacRecDifferencesStateInterface (SacRecDifferences sacRecDifferences);

	public JwsResult transactionSendAfterValidate(List<SacTransationSendForm> trxList);

	public void supplementTransactionFromTrxSys(SacRecDifferences sacRecDifferences) throws Exception;
	
	public JwsResult getPrestoreTrxFromDSF(Map<String,Object> queryMap) throws Exception;

	public void reconliciationAccount(HttpServletRequest request, SacChannelParam chnParamD,SacChannelParam chnParamC,SacCusParameter cusParamD,SacCusParameter cusParamC);
	public JwsResult reconliciationAccountToFa(FinTaskReceiveForm form);
	
	public Boolean notifyReplacePayCheckResultToDSF(String trxSerialNo,String checkStatus,SacCheckInfo checkInfo);
	
	public JwsResult manualSendTrxMsg(List<SacTransationReceiveForm> trxList);
	
	public JwsResult updateRemitBatchNo(List<SacRemittanceBatchIdForm> formList);
}
