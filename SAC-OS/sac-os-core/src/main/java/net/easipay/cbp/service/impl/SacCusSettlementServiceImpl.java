package net.easipay.cbp.service.impl;

import java.util.List;

import net.easipay.cbp.dao.ISacCusSettlementDao;
import net.easipay.cbp.model.SacCusPayment;
import net.easipay.cbp.model.SacCusSettlement;
import net.easipay.cbp.model.SacTrxDetail;
import net.easipay.cbp.service.ISacCusPaymentService;
import net.easipay.cbp.service.ISacCusSettlementService;
import net.easipay.cbp.service.ISacTrxDetailService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacCusSettlementService")
public class SacCusSettlementServiceImpl implements ISacCusSettlementService {

	@Autowired
	private ISacCusSettlementDao sacCusSettlementDao;
	
	@Autowired
	private ISacTrxDetailService sacTrxDetailService;
	
	@Autowired
	private ISacCusPaymentService sacCusPaymentService;

	@Override
	public List<SacCusSettlement> selectSacCusSettlement(
			SacCusSettlement sacCusSettlement,int pageNo,int pageSize) {
		return sacCusSettlementDao.selectSacCusSettlement(sacCusSettlement,pageNo,pageSize);
	}

	@Override
	public int selectSacCusSettlementCounts(SacCusSettlement sacCusSettlement) {
		String sacDate = sacCusSettlement.getSacDate();
		if(StringUtils.isNotBlank(sacDate)&&!"null".equals(sacDate)){
			sacDate = sacDate.replaceAll("-", "");
			sacCusSettlement.setSacDate(sacDate);
		}
		return sacCusSettlementDao.selectSacCusSettlementCounts(sacCusSettlement);
	}

	@Override
	public void updateSacCusSettlement(SacCusSettlement sacCusSettlement) {
		sacCusSettlementDao.updateSacCusSettlement(sacCusSettlement);
		
	}

	@Override
	public SacCusSettlement selectSacCusSettlementById(
			SacCusSettlement sacCusSettlement) {
		return sacCusSettlementDao.selectSacCusSettlementById(sacCusSettlement);
	}

	@Override
	public void dealRealPayWipe(SacCusSettlement sacCusSettlement) {
		sacCusSettlement.setFinSign("Y");
		this.updateSacCusSettlement(sacCusSettlement);
		String setBatchNo = sacCusSettlement.getSetBatchNo();
		SacCusPayment sacCusPayment = new SacCusPayment();
		sacCusPayment.setSetBatchNo(setBatchNo);
		List<SacCusPayment> cusPaymentList = sacCusPaymentService.selectSacCusPaymentByParameter(sacCusPayment);
		for(SacCusPayment cusPayment : cusPaymentList){
			SacTrxDetail sacTrxDetail = new SacTrxDetail();
			String cusBatchNo = cusPayment.getCusBatchNo();
			sacTrxDetail.setCusBatchNo(cusBatchNo);
			List<SacTrxDetail> trxDetailList = sacTrxDetailService.selectSacTrxDetailByParameter(sacTrxDetail);
			for(SacTrxDetail trxDetail:trxDetailList){
				//TODO 根据交易明细以及账务设计进行记账处理
			}
		}
		
	}

	@Override
	public void dealSettleAllot(SacCusSettlement sacCusSettlement) {
		sacCusSettlement.setTransferStatus("W");//因为代收付系统是异步的
		this.updateSacCusSettlement(sacCusSettlement);
		
		//TODO 调用账务系统资金划拨指令接收接口
		
	}

	/**
	 * CNY的响应处理相当于实付勾兑
	 */
	@Override
	public void dealSettleAllotResponse(SacCusSettlement sacCusSettlement) {
		sacCusSettlement.setTransferStatus("Y");//已划款
		sacCusSettlement.setFinSign("Y");//已勾兑
		this.updateSacCusSettlement(sacCusSettlement);
		//TODO 调用会计系统记账
		
	}


	
}
