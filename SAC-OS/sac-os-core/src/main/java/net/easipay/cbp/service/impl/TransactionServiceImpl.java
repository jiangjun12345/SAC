package net.easipay.cbp.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.exception.SacException;
import net.easipay.cbp.form.DsfCheckForm;
import net.easipay.cbp.form.OnbehalfExampleRequest;
import net.easipay.cbp.form.PayMessageForm;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacCheckInfo;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.SacRecDifferences;
import net.easipay.cbp.model.form.FinTaskReceiveForm;
import net.easipay.cbp.model.form.SacRemittanceBatchIdForm;
import net.easipay.cbp.model.form.SacTransationReceiveForm;
import net.easipay.cbp.model.form.SacTransationSendForm;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacRecDifferencesService;
import net.easipay.cbp.service.ITransactionService;
import net.easipay.cbp.util.PersonUtil;
import net.easipay.cbp.util.UrlUtil;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.xws.XwsClient;
import net.easipay.dsfc.ws.xws.XwsMsgHeader;
import net.easipay.dsfc.ws.xws.XwsResult;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("transactionService")
public class TransactionServiceImpl implements ITransactionService {
	
	private static final Logger logger = Logger.getLogger(TransactionServiceImpl.class);

	@Autowired
	private ISacRecDifferencesService sacRecDifferencesService;
	
	private String B2B_SUPPLEMENT_NOTIFY;
	private String B2C_SUPPLEMENT_NOTIFY_AC;
	private String B2C_SUPPLEMENT_NOTIFY_ORDER;
	private String B2B_GFH_SUPPLEMENT_NOTIFY;
	private String WHT_SUPPLEMENT_NOTIFY_AC;
	
	@Override
	public JwsResult transactionDealNewInterface(List<SacOtrxInfo> trxList) {
		List<PayMessageForm> payFormList = new ArrayList<PayMessageForm>();
		for(SacOtrxInfo sacOtrxInfo : trxList){
			PayMessageForm payMessageForm = new PayMessageForm(sacOtrxInfo);
			payFormList.add(payMessageForm);
		}
		JwsClient client = new JwsClient(Constants.RCV_TRANSACTION);
		client.putParam("sacTransationDetails", payFormList);
		JwsResult call = client.call();
		return call;
	}
	@Override
	public JwsResult manualSendTrxMsg(List<SacTransationReceiveForm> trxList){
		JwsClient client = new JwsClient(Constants.RCV_TRANSACTION);
		client.putParam("sacTransationDetails", trxList);
		JwsResult call = client.call();
		return call;
	}
	
	@Override
	public JwsResult transactionSendAfterValidate(List<SacTransationSendForm> trxList) {
		List<PayMessageForm> payFormList = new ArrayList<PayMessageForm>();
		for(SacTransationSendForm trx : trxList){
			PayMessageForm payMessageForm = new PayMessageForm(trx);
			payFormList.add(payMessageForm);
		}
		JwsClient client = new JwsClient(Constants.RCV_TRANSACTION);
		client.putParam("sacTransationDetails", payFormList);
		JwsResult call = client.call();
		return call;
	}

	@Override
	public JwsResult updateTransactionStateInterface(SacOtrxInfo sacOtrxInfo) {
		JwsClient client = new JwsClient(Constants.UPDATE_TRANSACTION);
		client.putParam("trxSerialNo",sacOtrxInfo.getTrxSerialNo())
		.putParam("trxState",sacOtrxInfo.getTrxState()).putParam("etrxSerialNo",sacOtrxInfo.getEtrxSerialNo()).putParam("trxSuccTime", sacOtrxInfo.getTrxSuccTime());
		JwsResult call = client.call();
		return call;
	}
	
	@Override
	public JwsResult updateRemitBatchNo(List<SacRemittanceBatchIdForm> formList) {
		JwsClient client = new JwsClient(Constants.UPDATE_BATCH_NO);
		client.putParam("sacRemittanceBatchIdForms",formList);
		JwsResult call = client.call();
		return call;
	}

	@Override
	public void updateSacRecDifferencesStateInterface(SacRecDifferences sacRecDifferences){
		
		SecurityOperator person = PersonUtil.getUser();
		
		JwsClient client = new JwsClient(Constants.UPDATE_REC_DIFF_STATE);
		client
		.putParam("recDetailId",sacRecDifferences.getRecDetailId())
		.putParam("trxSerialNo",sacRecDifferences.getTrxSerialNo()==null? "":sacRecDifferences.getTrxSerialNo())
		.putParam("bankSerialNo",sacRecDifferences.getBankSerialNo()==null? "":sacRecDifferences.getBankSerialNo())
		.putParam("status","S")//N:未处理，S：已处理
		.putParam("dealType","1")// 1：人工 2: 系统自动
		.putParam("dealOper",person.getMobile());// 处理人
		client.call();
		
	}
	
	@Override
	public void supplementTransactionFromTrxSys(SacRecDifferences sacRecDifferences) throws Exception{
		
		String trxSerialNo = sacRecDifferences.getTrxSerialNo();
		String trxCode = sacRecDifferences.getTrxCode();
		trxCode = trxCode.substring(trxCode.indexOf("(")+1, trxCode.indexOf(")"));
		String supplementFlag = sacRecDifferences.getSupplementFlag();
		String payconType = sacRecDifferences.getPayconType();
		Date recStartDate = sacRecDifferences.getRecStartDate();
		String status = sacRecDifferences.getStatus();
		//未补单
		if("N".equals(supplementFlag)&&"N".equals(status)){
			String url = "";
			boolean flag = 
					"3204".equals(trxCode)||"3202".equals(trxCode)||
					"3255".equals(trxCode)||"3411".equals(trxCode)||
					"3803".equals(trxCode)||"1705".equals(trxCode)||
					"3423".equals(trxCode)||"3412".equals(trxCode);
			if("1".equals(payconType)){
				if(flag){
					//行邮税  3204 3202 3255  购付汇 3411 3803 1705 3423 3412 TODO
					//B2B
					url = B2B_GFH_SUPPLEMENT_NOTIFY;
				}else{
					if("5101".equals(trxCode)){
						//外汇通
						url = WHT_SUPPLEMENT_NOTIFY_AC;
					}else{
						//B2B
						url = B2B_SUPPLEMENT_NOTIFY;
					}
				}
			}
			
			else if("2".equals(payconType)){
				//B2C
				if("3204".equals(trxCode)){
					//调用账务中心接口
					url = B2C_SUPPLEMENT_NOTIFY_AC;
					//url = "http://10.68.30.91:8080/ACCOUNTS-CENTER1.1.1/tracenter/transferTransaction.do";
				}else if("3302".equals(trxCode)||"3303".equals(trxCode)){
					//调用收单接口
					//调用账务中心接口
					url = B2C_SUPPLEMENT_NOTIFY_ORDER;
					//url = "http://10.68.30.91:8080/ORDER-RECEIVER/ordinary/transferTransaction.do";
				}
			}
			
			if(StringUtils.isNotBlank(url)){
				Map<String,String> params = new HashMap<String,String>();
				params.put("trxSerialNo",trxSerialNo);
				params.put("trxCode",trxCode);
				String response = UrlUtil.methodPost(url, params);
				if(response.contains("000000")||response.contains("success")){
					//修改补单标志为已补单
					SacRecDifferences diff  = new SacRecDifferences();
					diff.setTrxSerialNo(trxSerialNo);
					diff.setSupplementFlag("S");
					diff.setRecStartDate(recStartDate);
					sacRecDifferencesService.updateSacRecDifferencesByTrxSerialNo(diff);
					
					/*Map<String,Object> queryMap = new HashMap<String, Object>();
					queryMap.put("trxSerialNo", trxSerialNo);
					queryMap.put("recStartDate", recStartDate);
					List<SacRecDifferences> diffList = sacRecDifferencesService.selectSacRecDifferencesByParam(queryMap);
					
					for(SacRecDifferences recDiff : diffList){
						transactionServiceImpl.updateSacRecDifferencesStateInterface(recDiff);
					}*/
				}else{
					throw new SacException("999999", "补单失败("+response+")");
				}
			}
			
		}
		
	}

	@Override
	public JwsResult getPrestoreTrxFromDSF(Map<String,Object> queryMap)
			throws Exception {
		JwsClient client = new JwsClient(Constants.GET_PRESOTRE_TRX);
		client.putParam("startDate",queryMap.get("startDate"))
		.putParam("endDate",queryMap.get("endDate"))
		.putParam("bankCode", queryMap.get("bankCode"))
		.putParam("direction",queryMap.get("direction"))
		.putParam("prestoreFlag",queryMap.get("prestoreFlag"));
		JwsResult call = client.call();
		return call;
	}
	
	@Override
	public void reconliciationAccount(HttpServletRequest request, SacChannelParam chnParamD,SacChannelParam chnParamC,SacCusParameter cusParamD,SacCusParameter cusParamC) {
		
		String nameD = "";
		String nameC = "";
		
		String CODE1D = request.getParameter("CODE1D");
    	String CODE1C = request.getParameter("CODE1C");
    	
    	String CODE2D = request.getParameter("CODE2D");
    	String CODE2C = request.getParameter("CODE2C");
    	
    	String CODE3D = request.getParameter("CODE3D").trim();//应收和银存查渠道,其余查客户
    	String CODE3C = request.getParameter("CODE3C").trim();//应收和银存查渠道,其余查客户
    	
    	
    	String payCurrency = request.getParameter("payCurrency");
    	String payAmount = request.getParameter("payAmount");
		
    	Boolean flagD = CODE1D.equals("1122")||CODE1D.equals("1002")||CODE1D.equals("1221");
    	Boolean flagC = CODE1C.equals("1122")||CODE1C.equals("1002")||CODE1C.equals("1221");
    	
    	String bussTypeD = "";
    	String bussTypeC = "";
    	
    	String childAccTypeD = "";
    	String childAccTypeC = "";
    	String branchCodeD = "000000";
    	String branchCodeC = "000000";
    	
    	if(flagD){
    		nameD = chnParamD.getChnName();
    		branchCodeD = request.getParameter("branchCodeD").trim();
    		bussTypeD = branchCodeD.substring(0,2);
    		childAccTypeD = "00";
    	}else{
    		nameD = cusParamD.getCusName();
    		bussTypeD = request.getParameter("bussTypeD");//CODE5
    		childAccTypeD = request.getParameter("childAccTypeD");//CODE6
    	}
    	if(flagC){
    		nameC = chnParamC.getChnName();
    		branchCodeC = request.getParameter("branchCodeC").trim();
    		bussTypeC = branchCodeC.substring(0,2);
    		childAccTypeC = "00";
    	}else{
    		nameC = cusParamC.getCusName();
    		bussTypeC = request.getParameter("bussTypeC");//CODE5
    		childAccTypeC = request.getParameter("childAccTypeC");//CODE6
    	}
    	
    	List<SacOtrxInfo> trxList = new ArrayList<SacOtrxInfo>();
    	
		String trxSerialNo = SequenceCreatorUtil.generateTrxSerialNo();
		
		SacOtrxInfo otrxInfo = new SacOtrxInfo();
		
		if(chnParamD==null){
			otrxInfo.setDraccCardId(cusParamD.getOrgCardId());
			otrxInfo.setDraccCusType("1");
			otrxInfo.setDraccCusName(cusParamD.getCusName());
			otrxInfo.setDraccNo("0");
			otrxInfo.setDraccName(cusParamD.getAccName());
			//otrxInfo.setDraccNodeCode(cusParamD.getCraccBankCode());
			otrxInfo.setDraccNodeCode("BOS0000");
			otrxInfo.setDraccBankName(cusParamD.getAccName());
		}else{
			otrxInfo.setDraccCardId(chnParamD.getBankNodeCode());
			otrxInfo.setDraccCusType("1");
			otrxInfo.setDraccCusName(chnParamD.getChnName());
			otrxInfo.setDraccNo(chnParamD.getBankAcc());
			otrxInfo.setDraccName(chnParamD.getAccountName());
			otrxInfo.setDraccNodeCode(chnParamD.getBankNodeCode());
			otrxInfo.setDraccBankName(chnParamD.getSacBankName());
		}
		if(chnParamC==null){
			otrxInfo.setCraccCardId(cusParamC.getOrgCardId());
			otrxInfo.setCraccCusType("1");
			otrxInfo.setCraccCusName(cusParamC.getCusName());
			otrxInfo.setCraccNo("0");
			otrxInfo.setCraccName(cusParamC.getAccName());
			//otrxInfo.setCraccNodeCode(cusParamC.getCraccBankCode());
			otrxInfo.setCraccNodeCode("BOS0000");//
			otrxInfo.setCraccBankName(cusParamC.getAccName());
		}else{
			otrxInfo.setCraccCardId(chnParamC.getBankNodeCode());
			otrxInfo.setCraccCusType("1");
			otrxInfo.setCraccCusName(chnParamC.getChnName());
			otrxInfo.setCraccNo(chnParamC.getBankAcc());
			otrxInfo.setCraccName(chnParamC.getAccountName());
			otrxInfo.setCraccNodeCode(chnParamC.getBankNodeCode());
			otrxInfo.setCraccBankName(chnParamC.getSacBankName());
		}
		otrxInfo.setTrxSerialNo(trxSerialNo);
		Map<String, Object> ccyTransferMap = CacheUtil.getCcyTransferMap2();
		String ccy = (String)ccyTransferMap.get(payCurrency);
		otrxInfo.setPayCurrency(ccy);
		otrxInfo.setPayAmount(new BigDecimal(payAmount));
		if(flagD){
			otrxInfo.setBussType("60");
		}else{
			otrxInfo.setBussType(bussTypeD);
		}
		otrxInfo.setTrxType(Constants.RECON_ACCOUNT);
		otrxInfo.setTrxState("S");
		otrxInfo.setPayconType("3");
		otrxInfo.setPayWay("2");
		otrxInfo.setTrxTime(new Date());
		otrxInfo.setMemo("收款方业务类型为["+bussTypeC+"]");
		otrxInfo.setTrxSuccTime(new Date());
		otrxInfo.setDraccAreaCode(branchCodeD);
		otrxInfo.setCraccAreaCode(branchCodeC);
		trxList.add(otrxInfo);
		JwsResult rst = transactionDealNewInterface(trxList);
		if(!rst.isSuccess()){
			logger.error("交易接收接口报错："+rst.getMessage());
			throw new SacException("999999", "交易接收接口报错："+rst.getMessage());
		}
    	
    	String codeIdD = CODE1D+CODE2D+CODE3D+payCurrency+bussTypeD+childAccTypeD;
    	String codeIdC = CODE1C+CODE2C+CODE3C+payCurrency+bussTypeC+childAccTypeC;
    	
    	String params = String.format(Constants.TEMPLATE_PARAMS,codeIdD,nameD,payCurrency,payAmount,codeIdC,nameC,payCurrency,payAmount);
    	
    	String digest = String.format(Constants.TEMPLATE_DIGEST,"清算手工调账",nameD,nameC,payCurrency,payAmount);
    	  
    	FinTaskReceiveForm form = new FinTaskReceiveForm();
    	form.setBusiType(bussTypeD);
    	form.setStep(1);
    	form.setTrxCode(Constants.RECON_ACCOUNT);
    	form.setSerno(trxSerialNo);
	    form.setTradeTime(new Date());
	    form.setDigest(digest);
	    form.setParams(params);
	    
	    reconliciationAccountToFa(form);//调用FA记账接口 插入FIN_TASK
    	
		
	}
	
	@Override
	public JwsResult reconliciationAccountToFa(FinTaskReceiveForm form) {
		JwsClient client = new JwsClient(Constants.RECONLICIATION_ACCOUNT);
		client.putAllParam(form);
		JwsResult rst = client.call();
		return rst;
	}
	
	/**
	 * @param trxSerialNo
	 * @param checkStatus
	 * @return
	 */
	@Override
	public Boolean notifyReplacePayCheckResultToDSF(String trxSerialNo,String checkStatus,SacCheckInfo checkInfo) {
		Boolean rtnFlag = true;//成功
		String state = "";
		String stateDesc = "";
		String rtnState = "";
		if("1".equals(checkStatus)){
			//审核通过
			state = "0";
			stateDesc = "审核成功";
			rtnState = "S";
		}else if("3".equals(checkStatus)){
			//审核不通过
			state = "1";
			stateDesc = "审核失败";
			rtnState = "F";
		}else{
			return null;
		}
		
		if(StringUtils.isNotBlank(checkInfo.getMemo())&&checkInfo.getMemo().length()>4){

			OnbehalfExampleRequest form = new OnbehalfExampleRequest();
			XwsClient xwsClient = new XwsClient("EP-ORD-0008");
			XwsMsgHeader xwsMsgHeader = XwsMsgHeader.createXwsMsgHeader("00", "SAC00000", "SAC0000", "ORD0001", null);
			form.setTrxSerialNo(trxSerialNo);
			form.setState(rtnState);
			form.setErrorDesc(stateDesc);
			try {
				XwsResult result = xwsClient.putHeaderAndBody(xwsMsgHeader, form).call();
				String code = result.getCode();
				if("000000".equals(code)){
					rtnFlag = true;
				}else{
					rtnFlag = false;
				}
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		
		}else{
			JwsClient client = new JwsClient(Constants.HELP_PAY_CHECK);
			List<DsfCheckForm> request = new ArrayList<DsfCheckForm>();
			DsfCheckForm requestBean = new DsfCheckForm();
			requestBean.setOrderNo(trxSerialNo);
			requestBean.setStateCode(state);
			requestBean.setStateDesc(stateDesc);
			request.add(requestBean);
			client.putParam("outPayments", request);
			JwsResult rst = client.call();
			if(rst.isSuccess()){
				rtnFlag = true;
			}else{
				rtnFlag = false;
			}
		}
		return rtnFlag;
	}

	public void setB2B_SUPPLEMENT_NOTIFY(String b2b_SUPPLEMENT_NOTIFY) {
		B2B_SUPPLEMENT_NOTIFY = b2b_SUPPLEMENT_NOTIFY;
	}

	public void setB2C_SUPPLEMENT_NOTIFY_AC(String b2c_SUPPLEMENT_NOTIFY_AC) {
		B2C_SUPPLEMENT_NOTIFY_AC = b2c_SUPPLEMENT_NOTIFY_AC;
	}

	public void setB2C_SUPPLEMENT_NOTIFY_ORDER(String b2c_SUPPLEMENT_NOTIFY_ORDER) {
		B2C_SUPPLEMENT_NOTIFY_ORDER = b2c_SUPPLEMENT_NOTIFY_ORDER;
	}

	public String getB2B_GFH_SUPPLEMENT_NOTIFY() {
		return B2B_GFH_SUPPLEMENT_NOTIFY;
	}

	public void setB2B_GFH_SUPPLEMENT_NOTIFY(String b2b_GFH_SUPPLEMENT_NOTIFY) {
		B2B_GFH_SUPPLEMENT_NOTIFY = b2b_GFH_SUPPLEMENT_NOTIFY;
	}
	public void setWHT_SUPPLEMENT_NOTIFY_AC(String wHT_SUPPLEMENT_NOTIFY_AC) {
		WHT_SUPPLEMENT_NOTIFY_AC = wHT_SUPPLEMENT_NOTIFY_AC;
	}
	
	

}
