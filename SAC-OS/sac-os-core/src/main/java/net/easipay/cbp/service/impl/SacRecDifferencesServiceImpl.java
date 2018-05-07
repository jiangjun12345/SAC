package net.easipay.cbp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.dao.ISacRecDifferencesDao;
import net.easipay.cbp.dao.ISacTrxDetailDao;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.SacRecDifferences;
import net.easipay.cbp.model.SacTrxDetail;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacOtrxInfoService;
import net.easipay.cbp.service.ISacRecDifferencesService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.DateUtil;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacRecDifferencesService")
public class SacRecDifferencesServiceImpl implements ISacRecDifferencesService {

	private static final Logger logger = Logger.getLogger(SacRecDifferencesServiceImpl.class);
	
	@Autowired
	private ISacRecDifferencesDao sacRecDifferencesDao;
	
	@Autowired
	private ISacOtrxInfoDao otrxInfoDao;
	
	@Autowired
    private ISacTrxDetailDao sacTrxDetailDao;
	
	@Autowired
	private ISacOtrxInfoService sacOtrxInfoService;

	@Override
	public List<SacRecDifferences> selectSacRecDifferences(
			Map<String,Object> queryMap,int pageNo,int pageSize) {
		return sacRecDifferencesDao.selectSacRecDifferences(queryMap,pageNo,pageSize);
	}

	@Override
	public int selectSacRecDifferencesCounts(Map<String,Object> queryMap) {
		return sacRecDifferencesDao.selectSacRecDifferencesCounts(queryMap);
	}
	
	@Override
	public List<Map<String,Object>> selectDifferencesForSupplement(
			Map<String,Object> queryMap,int pageNo,int pageSize) {
		return sacRecDifferencesDao.selectDifferencesForSupplement(queryMap,pageNo,pageSize);
	}
	
	@Override
	public int selectDifferencesForSupplementCounts(Map<String,Object> queryMap) {
		return sacRecDifferencesDao.selectDifferencesForSupplementCounts(queryMap);
	}

	@Override
	public void updateSacRecDifferencesByTrxSerialNo(
			SacRecDifferences sacRecDifferences) {
		sacRecDifferencesDao.updateSacRecDifferencesByTrxSerialNo(sacRecDifferences);
		
	}

	@Override
	public List<SacRecDifferences> selectSacRecDifferencesByParam(
			Map<String, Object> queryMap) {
		return sacRecDifferencesDao.selectSacRecDifferencesByParam(queryMap);
	}
	@Override
	public List<SacRecDifferences> selectSacRecDifferencesDown(Map<String, Object> paramMap){
		return sacRecDifferencesDao.selectSacRecDifferencesDown(paramMap);
	}

	@Override
	public String handleRecStatus(Map<String, String> dataMap) {
		String result = null;
		String recId = dataMap.get("recId");//差错id
		String trxSerialNo = dataMap.get("trxSerialNo");//交易流水号
		String diffType = dataMap.get("diffType");//差错类型
		String handleFlag = dataMap.get("handleFlag");//直接处理标志
		try{
			//更新差错表状态和时间
			SacRecDifferences sacRecDifferences = new SacRecDifferences();
			sacRecDifferences.setId(Long.valueOf(recId));
			sacRecDifferences.setUpdateTime(new Date());
			//更新原始交易表状态和时间
			SacOtrxInfo sacOtrxInfo = new SacOtrxInfo();
			sacOtrxInfo.setTrxSerialNo(trxSerialNo);
			sacOtrxInfo.setUpdateTime(new Date());
			if("INN001".equals(diffType)){//内部对账-清算交易不存在 
				if(!"Y".equals(handleFlag)){
					//判断是否补单成功
					Map<String,Object> paramMap = new HashMap<String, Object>(); 
					paramMap.put("trxSerialNo", trxSerialNo);
					int count = otrxInfoDao.getTrxInfoCount(paramMap);
					if(count==0){
						result = "补单还未成功，差错暂不能处理，请稍后重试！";
						return result;
					}
					sacRecDifferences.setSupplementFlag("S");
				}
				//改状态
				sacRecDifferences.setStatus("S");
				sacOtrxInfo.setInnConFlag("S");
				sacOtrxInfo.setInnConStatus("S");
				sacRecDifferencesDao.updateSacRecDifferences(sacRecDifferences);
				otrxInfoDao.updateTrxInfoBySerialNo(sacOtrxInfo);
				return result;
			}
			if("CHA002".equals(diffType)){ //长款
				Map<String,Object> paramMap = new HashMap<String, Object>(); 
				paramMap.put("trxSerialNo", trxSerialNo);
				if(!"Y".equals(handleFlag)){
					//判断是否补单成功
					int count = otrxInfoDao.getTrxInfoCount(paramMap);
					if(count==0){
						result = "补单还未成功，差错暂不能处理，请稍后重试！";
						return result;
					}else{
						//记账
						String trxType = otrxInfoDao.selectOtrxInfoByCondition(paramMap).get(0).getTrxType();
						JwsClient client = new JwsClient("SAC-AC-0010");
						paramMap.put("trxCode", trxType);
						paramMap.put("trxState", "D");
						List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
						list.add(paramMap);
						Map<String,Object> paramMap2 = new HashMap<String, Object>();
						paramMap2.put("trxSerialNo", trxSerialNo);
						paramMap2.put("trxCode", trxType);
						paramMap2.put("trxState", "G");
						list.add(paramMap2);
						JwsResult jwsResult = client.putParam("finRuleTasks", list).call(true);
						if(!"000000".equals(jwsResult.getCode())){
							result = jwsResult.getMessage();
							return result;
						}
						sacRecDifferences.setSupplementFlag("S");
					}
				}
				//改状态
				sacRecDifferences.setStatus("S");
				sacOtrxInfo.setAccountStatus("S");
				sacOtrxInfo.setChaConFlag("S");
				sacOtrxInfo.setChaConStatus("S");
				sacRecDifferencesDao.updateSacRecDifferences(sacRecDifferences);
				otrxInfoDao.updateTrxInfoBySerialNo(sacOtrxInfo);
			  // 更新交易明细表
        this.updateChannelOriTrxDetailDataForReplenishments(trxSerialNo);
				return result;
			}
			if("INN000".equals(diffType)){//内部对账不一致
				//改状态
				sacOtrxInfo.setInnConStatus("S");
				sacRecDifferences.setStatus("S");
				sacRecDifferencesDao.updateSacRecDifferences(sacRecDifferences);
				otrxInfoDao.updateTrxInfoBySerialNo(sacOtrxInfo);
				return null;
			}
			if("CHA003".equals(diffType)){//渠道对账未内部对账
				//改状态
				sacOtrxInfo.setInnConFlag("S");
				sacOtrxInfo.setInnConStatus("S");
				sacRecDifferences.setStatus("S");
				sacRecDifferencesDao.updateSacRecDifferences(sacRecDifferences);
				otrxInfoDao.updateTrxInfoBySerialNo(sacOtrxInfo);
				return null;
			}
			if("CHA001".equals(diffType)){//渠道对账短款
				//调AC接口 记账
				Map<String,Object> paramMap = new HashMap<String, Object>(); 
				paramMap.put("trxSerialNo", trxSerialNo);
				String trxType = otrxInfoDao.selectOtrxInfoByCondition(paramMap).get(0).getTrxType();
				JwsClient client = new JwsClient("SAC-AC-0010");
				paramMap.put("trxCode", trxType);
				paramMap.put("trxState", "D");
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				list.add(paramMap);
				Map<String,Object> paramMap2 = new HashMap<String, Object>();
				paramMap2.put("trxSerialNo", trxSerialNo);
				paramMap2.put("trxCode", trxType);
				paramMap2.put("trxState", "G");
				list.add(paramMap2);
				JwsResult jwsResult = client.putParam("finRuleTasks", list).call(true);
				if(!"000000".equals(jwsResult.getCode())){
					result = jwsResult.getMessage();
				}else{
					//改状态
					sacOtrxInfo.setAccountStatus("S");
					sacOtrxInfo.setInnConFlag("S");
					sacOtrxInfo.setInnConStatus("S");
					sacOtrxInfo.setChaConFlag("S");
					sacOtrxInfo.setChaConStatus("S");
					sacRecDifferences.setStatus("S");
					sacRecDifferencesDao.updateSacRecDifferences(sacRecDifferences);
					otrxInfoDao.updateTrxInfoBySerialNo(sacOtrxInfo);
				}
				return result;
			}
			if("CHA000".equals(diffType)){//渠道对账不一致
				Map<String,Object> paramMap = new HashMap<String, Object>(); 
				paramMap.put("trxSerialNo", trxSerialNo);
				SacOtrxInfo otrxInfo = otrxInfoDao.selectOtrxInfoByCondition(paramMap).get(0);
				String etrxSerialNo = otrxInfo.getEtrxSerialNo();
				Date trxSuccTime = otrxInfo.getTrxSuccTime()==null?new Date():otrxInfo.getTrxSuccTime();
				//接收金额或状态，调用更新交易接口
				JwsClient client = new JwsClient("SAC-AC-0002");
				JwsResult jwsResult = client.putParam("trxState", "S").putParam("trxSerialNo", trxSerialNo)
					.putParam("etrxSerialNo", etrxSerialNo).putParam("trxSuccTime", trxSuccTime).call(true);
				if("000000".equals(jwsResult.getCode())){
					//改状态
					sacOtrxInfo.setChaConStatus("S");
					sacRecDifferences.setStatus("S");
					sacRecDifferencesDao.updateSacRecDifferences(sacRecDifferences);
					otrxInfoDao.updateTrxInfoBySerialNo(sacOtrxInfo);
				}else{
					result = jwsResult.getMessage();
				}
				return result;
			}
			
			if("INN002".equals(diffType)){
				//1.B2B交易失败  内部文件没传 交易传给清算了 则进行冲正处理
				//2.B2B交易成功  内部文件没传 交易传给清算了 则直接修改差错状态
				if("Y".equals(handleFlag)){
					sacRecDifferences.setStatus("S");
				}else{
					Map<String,Object> paramMap = new HashMap<String, Object>(); 
					
					paramMap.put("trxSerialNo", trxSerialNo);
					
					SacOtrxInfo otrxInfo = otrxInfoDao.selectOtrxInfoByCondition(paramMap).get(0);
					
					String rtrxSerialNo = SequenceCreatorUtil.generateTrxSerialNo();
					
					SacOtrxInfo otrxInfo2 = (SacOtrxInfo)org.apache.commons.beanutils.BeanUtils.cloneBean(otrxInfo);
					
					List<SacOtrxInfo> trxList = assembleTransaction(otrxInfo,trxSerialNo,rtrxSerialNo);
					
					sacOtrxInfoService.reversalTransaction(trxList,otrxInfo2);
					
					sacRecDifferences.setStatus("S");
					
				}
				sacOtrxInfo.setInnConFlag("S");
				sacOtrxInfo.setInnConStatus("S");
				otrxInfoDao.updateTrxInfoBySerialNo(sacOtrxInfo);
				sacRecDifferencesDao.updateSacRecDifferences(sacRecDifferences);
				return result;
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			result = "系统异常，处理失败";
		}
		return result;
	}
	
    /**
     * 组装交易
     * @param sacOtrxInfo
     * @param rtrxSerialNo
     * @param rtrxSerialNo2
     * @return
     */
    private List<SacOtrxInfo> assembleTransaction(
			SacOtrxInfo sacOtrxInfo, String trxSerialNo,
			String rtrxSerialNo) {
    	List<SacOtrxInfo> trxList = new ArrayList<SacOtrxInfo>();
    	sacOtrxInfo.setTrxType(Constants.REVERSAL_WIPE);
    	sacOtrxInfo.setTrxSerialNo(rtrxSerialNo);
    	//sacOtrxInfo.setTrxSerialNo(serialNoService.generateTrxSerialNo());
    	sacOtrxInfo.setOtrxSerialNo(trxSerialNo);
    	String craccBankName = sacOtrxInfo.getCraccBankName();
    	String craccCusCode = sacOtrxInfo.getCraccCusCode();
    	String craccCusName = sacOtrxInfo.getCraccCusName();
    	String craccCusType = sacOtrxInfo.getCraccCusType();
    	String craccName = sacOtrxInfo.getCraccName();
    	String craccNo = sacOtrxInfo.getCraccNo();
    	String craccNodeCode = sacOtrxInfo.getCraccNodeCode();
    	String craccCardId = sacOtrxInfo.getCraccCardId();
    	
    	String draccBankName = sacOtrxInfo.getDraccBankName();
    	String draccCusName = sacOtrxInfo.getDraccCusName();
    	String draccCusType = sacOtrxInfo.getDraccCusType();
    	String draccName = sacOtrxInfo.getDraccName();
    	String draccNo = sacOtrxInfo.getDraccNo();
    	String draccNodeCode = sacOtrxInfo.getDraccNodeCode();
    	String draccCardId = sacOtrxInfo.getDraccCardId();
    	
    	sacOtrxInfo.setCraccCardId(draccCardId);
    	sacOtrxInfo.setCraccBankName(draccBankName);
    	sacOtrxInfo.setCraccCusName(draccCusName);
    	sacOtrxInfo.setCraccCusType(draccCusType);
    	sacOtrxInfo.setCraccName(draccName);
    	sacOtrxInfo.setCraccNo(draccNo);
    	sacOtrxInfo.setCraccNodeCode(draccNodeCode);
    	
    	sacOtrxInfo.setDraccCardId(craccCardId);
    	sacOtrxInfo.setDraccBankName(craccBankName);
    	sacOtrxInfo.setDraccCusCode(craccCusCode);
    	sacOtrxInfo.setDraccCusName(craccCusName);
    	sacOtrxInfo.setDraccCusType(craccCusType);
    	sacOtrxInfo.setDraccName(craccName);
    	sacOtrxInfo.setDraccNo(craccNo);
    	sacOtrxInfo.setDraccNodeCode(craccNodeCode);
    	
    	sacOtrxInfo.setTrxTime(new Date());
    	
    	sacOtrxInfo.setTrxSuccTime(new Date());
    	
    	trxList.add(sacOtrxInfo);
    	
		return trxList;
	}
	
	
	private void updateChannelOriTrxDetailDataForReplenishments(String trxSerialNo) {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("trxSerialNo", trxSerialNo);
    // 根据交易流水号查询原始交易明细
    SacTrxDetail sacTrxDetail = sacTrxDetailDao.getOriTrxDetail(param);
    if (sacTrxDetail != null) {
      //SacTrxDetail uSacOtrxDetail = new SacTrxDetail();
      //uSacOtrxDetail.setId(sacTrxDetail.getId());
      sacTrxDetail.setChnBatchNo(Constants.REPLENISHMENTS_FLAG);
      sacTrxDetail.setCusBatchNo(Constants.REPLENISHMENTS_FLAG);
      sacTrxDetail.setChaConStatus(Constants.TRANSACTION_SUCCESS_FLAG);
      sacTrxDetailDao.updateOriTrxDetailStatus(sacTrxDetail);
    } else {
      logger.debug("原始交易表中存在记录，但是交易明细表中未找到相关的交易记录,交易流水号=" + trxSerialNo);
    }
  }
	private List<SacRecDifferences> dealListTrx(List<SacRecDifferences> sacRecDifferencesList) {
		Map<String, Object> trxTypeMap = CacheUtil
				.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
		for (SacRecDifferences sacRecDifferences : sacRecDifferencesList) {
			// 交易类型
			sacRecDifferences.setTrxCode(trxTypeMap.get(sacRecDifferences
					.getTrxCode()) == null ? "-" : trxTypeMap.get(
					sacRecDifferences.getTrxCode()).toString());
		}
		return sacRecDifferencesList;

	}
	@Override
	public String sacRecDifferencesDownloadContent(int i,
			Map<String, Object> paramMap) {
		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
		List<SacRecDifferences> sacRecDifferencesListRe = null;
		List<SacRecDifferences> sacRecDifferencesList = selectSacRecDifferencesDown(paramMap);
		if(sacRecDifferencesList==null||sacRecDifferencesList.size()==0){
			return null;
		}else{
			sacRecDifferencesListRe = dealListTrx(sacRecDifferencesList);
		}
		//组装下载文件内容
		StringBuffer sb = new StringBuffer();
		int j=i*1000+1;
		for(SacRecDifferences sd:sacRecDifferencesListRe){
			sb.append(j+"|");
			sb.append(sd.getTrxSerialNo()+"|");
			sb.append(sd.getBankSerialNo()+"|");
			sb.append(sd.getPayAmount()+"|");
			if(("INN001").equals(sd.getRecDiffType()) || ("INN000").equals(sd.getRecDiffType())||("INN002").equals(sd.getRecDiffType())){
				sb.append(sd.getOriInnConAmount()+"|");
			}else{
				sb.append(sd.getOriChaConAmount()+"|");
			}
			for(UnifiedConfig u: currencyTypeList){
				if(sd.getCurrencyType().equals(u.getDicCode())){
					sb.append(u.getDicDesc()+"|");
					break;
				}
			}
		//	sb.append(sd.getCurrencyType()+"|");
			sb.append(sd.getRecDiffDesc()+"|");
			sb.append(sd.getTrxCode()+"|");
			sb.append("B2B支付"+"|");
			String status = "S".equals(sd.getStatus())?"已处理":("N".equals(sd.getStatus())?"未处理":"处理中");
			sb.append(status+"|");			    
			sb.append(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", sd.getRecStartDate())+"|");
			sb.append(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", sd.getRecEndDate())+"|");
			sb.append(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss",sd.getTrxTime())+"\r\n");	
			j++;
		}
		return sb.toString();
	}
}
