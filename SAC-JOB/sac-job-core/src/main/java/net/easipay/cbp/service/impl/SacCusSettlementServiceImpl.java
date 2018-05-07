package net.easipay.cbp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacCusSettlementDao;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.model.SacCusSettlement;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacCusSettlementService;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.Utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jj 
 */
@Service
public class SacCusSettlementServiceImpl implements ISacCusSettlementService{
	
	private static final Logger logger = Logger.getLogger(SacCusSettlementServiceImpl.class);
	
	@Autowired
	private ISacOtrxInfoDao otrxInfoDao;
	
	@Autowired
	private ISacCusSettlementDao sacCusSettlementDao;

	@Override
	public void collectCusSettlement() {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		List<String> trxTypeList = new ArrayList<String>();
		trxTypeList.add("3255");
		trxTypeList.add("3412");
		trxTypeList.add("5502");
		trxTypeList.add("9338");
		trxTypeList.add("9308");
		trxTypeList.add("1613");
		paramMap.put("trxTypeList", trxTypeList);
		paramMap.put("trxState", "S");
		//设置扫描时间
		String beginDate = Utils.convertDate(new Date(), -1);//T-1
		String endDate = DateUtil.getDateTime("yyyyMMdd", new Date());//T
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		otrxInfoDao.selectOtrxInfoList(paramMap);
		List<SacOtrxInfo> otrxInfoList = otrxInfoDao.selectOtrxInfoList(paramMap);
		for(SacOtrxInfo trx : otrxInfoList){
			SacCusSettlement settlement = new SacCusSettlement();
			settlement.setBussType(trx.getBussType());
			settlement.setCreateTime(trx.getCreateTime());
			settlement.setCurrencyType(trx.getPayCurrency());
			settlement.setCusName(trx.getCraccCusName());
			settlement.setCusNo(trx.getCraccCardId());
			settlement.setDraccBankName(trx.getDraccBankName());
			settlement.setDraccName(trx.getDraccName());
			settlement.setDraccNo(trx.getDraccNo());
			settlement.setDraccNodeCode(trx.getDraccNodeCode());
			settlement.setTransferStatus("Y");
			settlement.setId(SequenceCreatorUtil.getTableId());
			settlement.setSacAmount(trx.getPayAmount());
			settlement.setTotalAmount(trx.getPayAmount());
			settlement.setTotalNum(1);
			settlement.setTrxType(trx.getTrxType());
			settlement.setSacDate(DateUtil.getDateTime("yyyyMMdd", trx.getTrxSuccTime()));
			sacCusSettlementDao.save(settlement);
		}
	}
	

}

