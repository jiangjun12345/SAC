package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.easipay.cbp.dao.ISacChnSettlementDao;
import net.easipay.cbp.form.FinTasksForm;
import net.easipay.cbp.model.SacChnSettlement;
import net.easipay.cbp.model.SacTrxDetail;
import net.easipay.cbp.service.IFinTransactionService;
import net.easipay.cbp.service.ISacChnSettlementService;
import net.easipay.cbp.service.ISacTrxDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("sacChnSettlementService")
public class SacChnSettlementServiceImpl implements ISacChnSettlementService {

	@Autowired
	private ISacChnSettlementDao sacChnSettlementDao;

	@Autowired
	private ISacTrxDetailService sacTrxDetailService;
	
	@Autowired
	private IFinTransactionService finTransactionService;
	
	@Override
	public List<SacChnSettlement> selectSacChnSettlement(
			SacChnSettlement sacChnSettlement,int pageNo,int pageSize) {
		return sacChnSettlementDao.selectSacChnSettlement(sacChnSettlement,pageNo,pageSize);
	}

	@Override
	public int selectSacChnSettlementCounts(SacChnSettlement sacChnSettlement) {
		return sacChnSettlementDao.selectSacChnSettlementCounts(sacChnSettlement);
	}

	@Override
	public void updateSacChnSettlement(SacChnSettlement sacChnSettlement) {
		sacChnSettlementDao.updateSacChnSettlement(sacChnSettlement);
		
	}

	@Override
	public SacChnSettlement selectSacChnSettlementById(
			SacChnSettlement sacChnSettlement) {
		return sacChnSettlementDao.selectSacChnSettlementById(sacChnSettlement);
	}

	/**
	 * 实收勾兑
	 */
	@Override
	public void dealRealReceiveWipe(SacChnSettlement sacChnSettlement,BigDecimal realTotAmount) {
		sacChnSettlement.setFinSign("Y");//已勾兑
		sacChnSettlement.setRealTotAmount(realTotAmount);
		this.updateSacChnSettlement(sacChnSettlement);//更新勾兑状态
		String chnBatchNo = sacChnSettlement.getChnBatchNo();
		//根据应收清分批次号去找原始交易  依次记账
		SacTrxDetail sacTrxDetail = new SacTrxDetail();
		sacTrxDetail.setChnBatchNo(chnBatchNo);
		List<SacTrxDetail> sacTrxDetailList = sacTrxDetailService.selectSacTrxDetailByParameter(sacTrxDetail);
		List<FinTasksForm> finTasksList = new ArrayList<FinTasksForm>();
		for(SacTrxDetail trxDetail:sacTrxDetailList){
			//根据交易明细以及账务设计进行记账处理
			FinTasksForm finTasksForm = new FinTasksForm();
			String trxSerialNo = trxDetail.getTrxSerialNo();
			String trxCode = trxDetail.getTrxType();
			
			finTasksForm.setTrxCode(trxCode);
			finTasksForm.setTrxSerialNo(trxSerialNo);
			finTasksForm.setTrxState("G");//N：代支付；S 交易成功；D：对账成功；G：勾兑成功 
			
			finTasksList.add(finTasksForm);
			
			//更新交易明细勾兑状态
			sacTrxDetail.setTrxSerialNo(trxSerialNo);
			sacTrxDetail.setFinSign("S");
			sacTrxDetailService.updateTrxDetailForDB(sacTrxDetail);
		}
		if(finTasksList.size()>0){
			finTransactionService.finTasksDeal(finTasksList);
		}
		
	}


	
}
