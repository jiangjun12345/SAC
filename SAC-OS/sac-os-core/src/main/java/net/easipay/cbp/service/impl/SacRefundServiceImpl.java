package net.easipay.cbp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacRefundDao;
import net.easipay.cbp.form.FinTasksForm;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.SacRecDifferences;
import net.easipay.cbp.model.SacRefund;
import net.easipay.cbp.model.SacTrxDetail;
import net.easipay.cbp.service.IFinTransactionService;
import net.easipay.cbp.service.ISacChannelParamService;
import net.easipay.cbp.service.ISacChnSettlementService;
import net.easipay.cbp.service.ISacOtrxInfoService;
import net.easipay.cbp.service.ISacRecDifferencesService;
import net.easipay.cbp.service.ISacRefundService;
import net.easipay.cbp.service.ISacTrxDetailService;
import net.easipay.cbp.service.ITransactionService;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacRefundService")
public class SacRefundServiceImpl implements ISacRefundService {

	@Autowired
	private ISacRefundDao sacRefundDao;
	@Autowired
	private ITransactionService transactionServiceImpl;
	@Autowired
	private ISacRecDifferencesService sacRecDifferencesServiceImpl;
	@Autowired
	private ISacOtrxInfoService sacOtrxInfoServiceImpl;
	@Autowired
	private IFinTransactionService finTransactionService;
	@Autowired
	private ISacChnSettlementService sacChnSettlementService;
	@Autowired
	private ISacTrxDetailService sacTrxDetailServiceImpl;
	@Autowired
	private ISacChannelParamService sacChannelParamServiceImpl;

	@Override
	public List<SacRefund> selectSacRefund(
			SacRefund sacRefund,int pageNo,int pageSize) {
		return sacRefundDao.selectSacRefund(sacRefund,pageNo,pageSize);
	}

	@Override
	public int selectSacRefundCounts(SacRefund sacRefund) {
		return sacRefundDao.selectSacRefundCounts(sacRefund);
	}

	@Override
	public SacRefund selectSacRefundById(SacRefund sacRefund) {
		return sacRefundDao.selectSacRefundById(sacRefund);
	}

	@Override
	public void updateSacRefund(SacRefund sacRefund) {
		sacRefundDao.updateSacRefund(sacRefund);
	}

	@Override
	public void dealRefund(SacOtrxInfo sacOtrxInfo) {
		//1.更新交易状态为成功
		String state = sacOtrxInfo.getTrxState();
		if("N".equals(state)){
			sacOtrxInfo.setTrxState("S");
			transactionServiceImpl.updateTransactionStateInterface(sacOtrxInfo);
		}
		
		String trxSerialNo = sacOtrxInfo.getTrxSerialNo();
		String accountStatus = sacOtrxInfo.getAccountStatus();//记账状态
		String recDiffType = "CHA000";
		
		
		//2.更新差错信息 生产库
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("trxSerialNo",trxSerialNo);
		queryMap.put("recDiffType",recDiffType);
		List<SacRecDifferences> selectSacRecDifferencesByParam = sacRecDifferencesServiceImpl.selectSacRecDifferencesByParam(queryMap);
		if(selectSacRecDifferencesByParam!=null&&selectSacRecDifferencesByParam.size()>0){
			for(SacRecDifferences sacRecDifferences : selectSacRecDifferencesByParam){
				String status = sacRecDifferences.getStatus();
				if("N".equals(status)){
					transactionServiceImpl.updateSacRecDifferencesStateInterface(sacRecDifferences);	
				}
			}
		}
		
		
		//3.更新对账状态为已对账
		String succState = "S";
		SacOtrxInfo trx = new SacOtrxInfo();
		trx.setTrxSerialNo(trxSerialNo);
		trx.setInnConFlag(succState);
		trx.setInnConStatus(succState);
		trx.setChaConFlag(succState);
		trx.setChaConStatus(succState);
		sacOtrxInfoServiceImpl.updateSacOtrxInfoForDB(trx);
		
		
		//4.更新对账记账状态 并记账
		if("N".equals(accountStatus)){
			//根据交易以及账务设计进行记账处理
			List<FinTasksForm> finTasksList = new ArrayList<FinTasksForm>();
			FinTasksForm finTasksForm = new FinTasksForm();
			finTasksForm.setTrxCode(sacOtrxInfo.getTrxType());
			finTasksForm.setTrxSerialNo(trxSerialNo);
			finTasksForm.setTrxState("D");//N：代支付；S 交易成功；D：对账成功；G：勾兑成功 
			finTasksList.add(finTasksForm);
			JwsResult result = finTransactionService.finTasksDeal(finTasksList);
			if(result.isSuccess()){
				String succAccountState = "S";
				SacOtrxInfo trx2 = new SacOtrxInfo();
				trx2.setTrxSerialNo(trxSerialNo);
				trx2.setAccountStatus(succAccountState);
				sacOtrxInfoServiceImpl.updateSacOtrxInfoForDB(trx2);
			}
		}
		
		
		String chnNo = "";
		Map<String,Object> queryChannelMap = new HashMap<String,Object>();
		String craccNodeCode = sacOtrxInfo.getCraccNodeCode();
		String payCurrency = sacOtrxInfo.getPayCurrency();
		queryChannelMap.put("chnCode", craccNodeCode);
		queryChannelMap.put("currencyType", payCurrency);
		List<SacChannelParam> channelParamList = sacChannelParamServiceImpl.selectSacChannelParamForB2C(queryChannelMap);
		if(channelParamList!=null&&channelParamList.size()>0){
			SacChannelParam sacChannelParam = channelParamList.get(0);
			chnNo = sacChannelParam.getChnNo();
		}
		
		//更新交易明细表对账状态
		String succSign = "S";
		SacTrxDetail trx3 = new SacTrxDetail();
		trx3.setTrxSerialNo(trxSerialNo);
		trx3.setChaConStatus(succSign);
		trx3.setChnNo(chnNo);
		//trx3.setChnBatchNo("refund_batchNo");
		sacTrxDetailServiceImpl.updateTrxDetailForDB(trx3);
	}


	
}
