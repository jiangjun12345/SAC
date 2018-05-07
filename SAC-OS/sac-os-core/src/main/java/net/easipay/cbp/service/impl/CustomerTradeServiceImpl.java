package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.dao.ISacCusParameterDao;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.service.ICustomerTradeService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerTradeServiceImpl implements ICustomerTradeService
{

	Logger logger = LoggerFactory.getLogger(CustomerTradeServiceImpl.class);

	@Autowired
	private ISacOtrxInfoDao otrxInfoDao;
	
	@Autowired
	private ISacCusParameterDao cusParamDao;
	
	@Override
	public List<SacOtrxInfo> queryTrxInfo(Map<String, Object> paramMap)
	{
		return otrxInfoDao.queryTrxInfo(paramMap);
	}

	@Override
	public List<SacOtrxInfo> simpleQueryTrxInfo(Map<String, Object> paramMap)
	{
		return otrxInfoDao.simpleQueryTrxInfo(paramMap);
	}

	@Override
	public int getTrxInfoCount(Map<String, Object> paramMap)
	{
		return otrxInfoDao.newGetTrxInfoCount(paramMap);
	}
	
	@Override
	public Map<String, BigDecimal> trxCurrencyCount(List<SacOtrxInfo> trxInfoList)
	{
		// 对当页汇总处理
		Map<String, BigDecimal> resultMap = new HashMap<String, BigDecimal>();
		BigDecimal bd = null;
		if (trxInfoList != null)
		{
			for (SacOtrxInfo td : trxInfoList)
			{
				if (resultMap.containsKey(td.getPayCurrency()))
				{
					bd = resultMap.get(td.getPayCurrency()).add(td.getPayAmount());// 如果包含该币种则相加
					resultMap.put(td.getPayCurrency(), bd);
				} else
				{
					resultMap.put(td.getPayCurrency(), td.getPayAmount());
				}
			}
		}
		logger.debug("resultMap is " + resultMap);
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		if(resultMap==null||resultMap.isEmpty()){
			return resultMap;
		}else{
			Map<String,BigDecimal> currencyCountMap = new HashMap<String, BigDecimal>();
			Set<String> ccyKey = resultMap.keySet();
			for(String key:ccyKey){
				currencyCountMap.put(ccyMap.get(key)!=null?ccyMap.get(key).toString():key, resultMap.get(key));
			}
			return currencyCountMap;
		}
	}
	
	@Override
	public Map<String, Object> getTrxInfoAmountCount(Map<String, Object> paramMap){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		Map<String,Object> amountCountMap = otrxInfoDao.getTrxInfoAmountCount(paramMap);
		Set<String> currency = amountCountMap.keySet();
		for(String key:currency){
			if(key==null||key.equals("")){
				continue;
			}
			resultMap.put(ccyMap.get(key)!=null?ccyMap.get(key).toString():"", amountCountMap.get(key));
		}
		return resultMap;
	}

	@Override
	public List<SacOtrxInfo> queryPayAndGetInfo(Map<String, Object> paramMap)
	{
		
		return otrxInfoDao.queryPayAndGetInfo(paramMap);
	}

	@Override
	public List<SacOtrxInfo> simpleQueryPayAndGetInfo(Map<String, Object> paramMap)
	{
		return otrxInfoDao.simpleQueryPayAndGetInfo(paramMap);
	}
	
	@Override
	public int getPayAndGetInfoCount(Map<String, Object> paramMap)
	{
		return otrxInfoDao.getPayAndGetInfoCount(paramMap);
	}
	
	
	/**
	 * 对原交易查询结果枚举值处理
	 * @param otrxInfoList
	 */
	@Override
	public List<SacOtrxInfo> handleSacOtrxInfoList(List<SacOtrxInfo> otrxInfoList){
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
		Map<String,Object> trxTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
		Map<String,Object> payWayMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_PAY_WAY);
		Map<String,Object> bankMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BANK);
		BigDecimal bd = new BigDecimal(0.00);
		for(SacOtrxInfo soi:otrxInfoList){
			//业务类型
			soi.setBussType(bussTypeMap.get(soi.getBussType())==null?"-":bussTypeMap.get(soi.getBussType())+"("+soi.getBussType()+")");
			//交易类型
			soi.setMemo(soi.getTrxType());
			//交易类型
			soi.setTrxType(trxTypeMap.get(soi.getTrxType())==null?"-":trxTypeMap.get(soi.getTrxType())+"("+soi.getTrxType()+")");
			//支付币种
			soi.setPayCurrency(ccyMap.get(soi.getPayCurrency())==null?"-":ccyMap.get(soi.getPayCurrency()).toString());
			//购付汇币种
			soi.setSacCurrency(ccyMap.get(soi.getSacCurrency())==null?"-":ccyMap.get(soi.getSacCurrency()).toString());
			//支付方式
			soi.setPayWay(payWayMap.get(soi.getPayWay())==null?"-":payWayMap.get(soi.getPayWay()).toString());
			//收款银行
			soi.setCraccNodeCode(bankMap.get(soi.getCraccNodeCode())==null?"-":bankMap.get(soi.getCraccNodeCode()).toString());
			//付款银行
			soi.setDraccNodeCode(bankMap.get(soi.getDraccNodeCode())==null?"-":bankMap.get(soi.getDraccNodeCode()).toString());
			//交易状态
			if("S".equals(soi.getTrxState())){
				soi.setTrxState("交易成功");
			}else if("F".equals(soi.getTrxState())){
				soi.setTrxState("交易失败");
			}else{
				soi.setTrxState("待支付");
			}
			//支付渠道类型
			String payconTypeName = "";
			String payconType = soi.getPayconType();
			if("1".equals(payconType)){
				payconTypeName = "B2B支付";
			}else if("2".equals(payconType)){
				payconTypeName = "B2C支付";
			}else{
				payconTypeName = "其他";
			}
			soi.setPayconType(payconTypeName);
			//平台订单编号
			soi.setPlatBillNo(soi.getPlatBillNo()==null?"-":soi.getPlatBillNo());
			//订单号/退款申请编号/购结汇批次号
			soi.setCusBillNo(soi.getCusBillNo()==null?"-":soi.getCusBillNo());
			//购结汇金额
			soi.setSacAmount(soi.getSacAmount()==null?bd:soi.getSacAmount());
		}
		return otrxInfoList;
	}
	
	@Override
	public Map<String,Object> getCusParamMap(){
		Map<String,Object> paramMap = new HashMap<String, Object>();
		Map<String,Object> cusParamMap = new HashMap<String, Object>();
		List<SacCusParameter> cusParamList = cusParamDao.queryAllSacCusParameter(paramMap);
		for(SacCusParameter cusParam:cusParamList){
			cusParamMap.put(cusParam.getCusNo(), cusParam.getCusName());
		}
		return cusParamMap;
	}
	
	@Override
	public String trxDetailDownloadContent(int i, Map<String, Object> paramMap)
	{
		List<SacOtrxInfo> trxDetailList = queryTrxInfo(paramMap);
		if(trxDetailList==null||trxDetailList.size()==0){
			return null;
		}
		trxDetailList = handleSacOtrxInfoList(trxDetailList);
		//组装下载文件内容
		StringBuffer sb = new StringBuffer();
		int j=i*10000+1;
		for(SacOtrxInfo sd:trxDetailList){
			sb.append(j+"|");
			sb.append(DateUtil.getDate(sd.getCreateTime())+"|");
			sb.append(DateUtil.getDate(sd.getTrxSuccTime())+"|");
			sb.append(sd.getCusBillNo()+"|");
			sb.append(sd.getTrxSerialNo()+"|");
			sb.append(sd.getEtrxSerialNo()+"|");
			sb.append(sd.getTrxState()+"|");
			sb.append(sd.getBussType()+"|");
			sb.append(sd.getTrxType()+"|");
			sb.append(sd.getPayAmount()+"|");
			sb.append(sd.getPayCurrency()+"|");
			sb.append(sd.getPayWay()+"|");
			sb.append(sd.getPayconType()+"|");
			sb.append(sd.getSacCurrency()+"|");
			sb.append(sd.getSacAmount()+"|");
			sb.append(sd.getExRate()+"|");
			sb.append(sd.getCraccCusName()+"|");
			sb.append(sd.getCraccBankName()+"|");
			sb.append(sd.getDraccCusName()+"|");
			sb.append(sd.getDraccBankName()+"\r\n");
			j++;
		}
		return sb.toString();
	}

	@Override
	public String payAndGetInfoDownloadContent(int i, Map<String, Object> paramMap)
	{
		//查询客户交易明细
		List<SacOtrxInfo> otrxInfoList = queryPayAndGetInfo(paramMap);
		if(otrxInfoList==null||otrxInfoList.size()==0){
			return null;
		}
		otrxInfoList = handleSacOtrxInfoList(otrxInfoList);
		//组装下载文件内容
		StringBuffer sb = new StringBuffer();
		int j=i*10000+1;
		for(SacOtrxInfo sd:otrxInfoList){
			sb.append(j+"|");
			sb.append(DateUtil.getDate(sd.getCreateTime())+"|");
			sb.append("G".equals(sd.getTrxType())?"购汇|":"结汇|");
			sb.append(sd.getDraccBankName()+"|");
			sb.append(sd.getDraccNo()+"|");
			sb.append(sd.getPayAmount()+"|");
			sb.append(sd.getPayCurrency()+"|");
			sb.append(sd.getCraccBankName()+"|");
			sb.append(sd.getCraccNo()+"|");
			sb.append(sd.getSacAmount()+"|");
			sb.append(sd.getSacCurrency()+"|");
			sb.append(sd.getExRate()+"|");
			sb.append((sd.getTrxBatchNo()==null?"-":sd.getTrxBatchNo())+"|");
			sb.append(sd.getMemo()+"|");
			sb.append(sd.getDraccCusCode()+"\r\n");
			j++;
		}
		return sb.toString();
	}
}
