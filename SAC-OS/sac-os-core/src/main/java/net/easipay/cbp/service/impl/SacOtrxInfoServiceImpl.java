package net.easipay.cbp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.dao.ISacTrxDetailDao;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.SacTrxDetail;
import net.easipay.cbp.service.ISacOtrxInfoService;
import net.easipay.cbp.service.ITransactionService;
import net.easipay.cbp.util.BeanUtils;
import net.easipay.dsfc.ws.jws.JwsClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacOtrxInfoService")
public class SacOtrxInfoServiceImpl implements ISacOtrxInfoService {

	@Autowired
	private ISacOtrxInfoDao sacOtrxInfoDao;
	
	@Autowired
	private ISacTrxDetailDao sacTrxDetailDao;
	
	@Autowired
	private ITransactionService transactionService;

	@Override
	public List<SacOtrxInfo> selectSacOtrxInfo(
			SacOtrxInfo sacOtrxInfo,int pageNo,int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		Map<String,Object> paramMap = BeanUtils.transBean2Map(sacOtrxInfo);
		
		paramMap.put("start", start);
		paramMap.put("end", end);
		return sacOtrxInfoDao.queryTrxInfo(paramMap);
	}
	
	@Override
	public List<SacOtrxInfo> selectSacOtrxInfoByParam(
			SacOtrxInfo sacOtrxInfo,int pageNo,int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		Map<String,Object> paramMap = BeanUtils.transBean2Map(sacOtrxInfo);
		
		paramMap.put("start", start);
		paramMap.put("end", end);
		return sacOtrxInfoDao.selectOtrxInfo(paramMap);
	}

	@Override
	public int selectSacOtrxInfoCounts(SacOtrxInfo sacOtrxInfo) {
		Map<String,Object> queryMap = BeanUtils.transBean2Map(sacOtrxInfo);
		return sacOtrxInfoDao.getTrxInfoCount(queryMap);
	}

	@Override
	public SacOtrxInfo selectSacOtrxInfoById(SacOtrxInfo sacOtrxInfo) {
		return sacOtrxInfoDao.selectSacOtrxInfoById(sacOtrxInfo);
	}
	/*
	@Override
	public void updateSacOtrxInfo(SacOtrxInfo sacOtrxInfo) {
		sacOtrxInfoDao.updateSacOtrxInfo(sacOtrxInfo);
	}*/

	@Override
	public List<SacOtrxInfo> selectSacOtrxInfoForRefund(
			SacOtrxInfo sacOtrxInfo, int pageNo, int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		Map<String,Object> paramMap = BeanUtils.transBean2Map(sacOtrxInfo);
		
		paramMap.put("start", start);
		paramMap.put("end", end);
		paramMap.put("transType",Constants.REFUND_TRANS_TYPE);
		return sacOtrxInfoDao.selectOtrxInfo(paramMap);
	}

	@Override
	public Integer selectSacOtrxInfoCountsForRefund(SacOtrxInfo sacOtrxInfo) {
		Map<String,Object> queryMap = BeanUtils.transBean2Map(sacOtrxInfo);
		queryMap.put("transType",Constants.REFUND_TRANS_TYPE);
		return sacOtrxInfoDao.getTrxInfoCount(queryMap);
	}

	@Override
	public void updateSacOtrxInfo(SacOtrxInfo sacOtrxInfo) {
		/*JwsClient client = new JwsClient(Constants.NOTIFY_CONFIRM_RESULT);
		client.putParam("confirmTime", sacOtrxInfo.getConfirmTime())
		.putParam("confirmUser",sacOtrxInfo.getConfirmUser())
		.putParam("confirmStatus",sacOtrxInfo.getConfirmStatus())
		.putParam("trxSerialNo",sacOtrxInfo.getTrxSerialNo());
		client.call();*/
		
	}

	@Override
	public void reversalTransaction(List<SacOtrxInfo> trxList,SacOtrxInfo sacOtrxInfo) {
		
		
		
		sacOtrxInfo.setReversalStatus("S");//已冲正
		Date date = new Date();
		sacOtrxInfo.setUpdateTime(date);
		sacOtrxInfoDao.updateTrxInfoBySerialNo(sacOtrxInfo);
		
		SacTrxDetail sacTrxDetail =  new SacTrxDetail();
		sacTrxDetail.setTrxSerialNo(sacOtrxInfo.getTrxSerialNo());
		sacTrxDetail.setReversalStatus("S");//已冲正
		sacTrxDetail.setUpdateTime(date);
		sacTrxDetailDao.updateTrxDetailBySerialNo(sacTrxDetail);
		
		transactionService.transactionDealNewInterface(trxList);
		
	}

	@Override
	public void updateSacOtrxInfoForDB(SacOtrxInfo sacOtrxInfo) {
		sacOtrxInfoDao.updateTrxInfoBySerialNo(sacOtrxInfo);
		
	}


	
}
