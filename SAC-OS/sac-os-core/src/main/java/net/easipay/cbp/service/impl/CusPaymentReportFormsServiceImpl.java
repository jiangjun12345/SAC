package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.dao.ISacCusPaymentDao;
import net.easipay.cbp.dao.ISacCusSettlementDao;
import net.easipay.cbp.model.SacCancelVerify;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.model.SacCusPayment;
import net.easipay.cbp.model.SacCusSettlement;
import net.easipay.cbp.service.ICusPaymentReportFormsService;
import net.easipay.cbp.service.ISacCusParameterService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CusPaymentReportFormsServiceImpl implements ICusPaymentReportFormsService
{

	@Autowired
	private ISacCusSettlementDao sacCusSettlementDao;
	
	@Autowired
	private ISacCusPaymentDao sacCusPaymentDao;
	
    @Autowired
    private ISacCusParameterService sacCusParameterService;
	
	/****************商户应付款报表层*******************/
	@Override
	public List<SacCusSettlement> queryCusPayment(Map<String, Object> paramMap)
	{
		return sacCusSettlementDao.querySacCusSettlement(paramMap);
	}

	@Override
	public List<SacCusSettlement> simpleQueryCusPayment(Map<String, Object> paramMap)
	{
		return sacCusSettlementDao.simpleQuerySacCusSettlement(paramMap);
	}
	
	@Override
	public int queryCusPaymentCount(Map<String, Object> paramMap)
	{
		return sacCusSettlementDao.querySacCusSettlementCount(paramMap);
	}
	
	@Override
	public List<Map<String, Object>> countCusSettlementAmount(Map<String, Object> paramMap)
	{
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		List<Map<String, Object>> countList = sacCusSettlementDao.countCusSettlementAmount(paramMap);
		for(Map<String, Object> countMap:countList){
			countMap.put("CURRENCY_TYPE", ccyMap.get(countMap.get("CURRENCY_TYPE")));
		}
		return countList;
	}
	
	@Override
	public String cusPaymentDownloadContent(int i, Map<String, Object> paramMap)
	{
		List<SacCusSettlement> cusPaymentList = queryCusPayment(paramMap);
		if(cusPaymentList==null||cusPaymentList.size()==0){
			return null;
		}
		cusPaymentList = handleSacCusSettlementList(cusPaymentList);
		//组装下载文件内容
		StringBuffer sb = new StringBuffer();
		int j=i*1000+1;
		for(SacCusSettlement scs:cusPaymentList){
			sb.append(j+"|");
			sb.append(scs.getCusNo()+"|");
			sb.append(scs.getCusName()+"|");
			sb.append(scs.getSacDate()+"|");
			sb.append(scs.getTotalNum()+"|");
			sb.append(scs.getTotalAmount()+"|");
			sb.append(scs.getCurrencyType()+"|");
			sb.append(scs.getFees()+"|");
			sb.append(scs.getSacAmount()+"|");
			sb.append(scs.getSetBatchNo()+"|");
			sb.append(scs.getDraccName()+"|");
			sb.append(scs.getSacSign()+"|");
			sb.append(scs.getTransferStatus()+"|");
			sb.append(scs.getFinSign()+"\r\n");
			j++;
		}
		return sb.toString();
	}
	
	/**
	 * 对商户应付查询结果枚举值处理
	 * @param otrxInfoList
	 */
	@Override
	public List<SacCusSettlement> handleSacCusSettlementList(List<SacCusSettlement> sacCusSettlementList){
		if(sacCusSettlementList==null||sacCusSettlementList.size()==0){
			return sacCusSettlementList;
		}
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		BigDecimal defaultValue = new BigDecimal(0.00);
		for(SacCusSettlement scs:sacCusSettlementList){
			//币种
			scs.setCurrencyType(ccyMap.get(scs.getCurrencyType())==null?"-":ccyMap.get(scs.getCurrencyType()).toString());
			//是否已结算
			scs.setSacSign("Y".equals(scs.getSacSign())?"是":"否");
			//是否已划款
			if("Y".equals(scs.getTransferStatus())){
				scs.setTransferStatus("已划款");
			}else if("W".equals(scs.getTransferStatus())){
				scs.setTransferStatus("划款中");
			}else{
				scs.setTransferStatus("未划款");
			}
			//是否已勾兑
			scs.setFinSign("Y".equals(scs.getFinSign())?"是":"否");
			scs.setFees(scs.getFees()==null?defaultValue:scs.getFees());
			scs.setSetBatchNo(scs.getSetBatchNo()==null?"-":scs.getSetBatchNo());
			scs.setDraccName(scs.getDraccName()==null?"-":scs.getDraccName());
		}
		return sacCusSettlementList;
	}
	
	/****************商户应付款明细报表层*******************/
	@Override
	public List<SacCusPayment> queryCusPaymentDetail(Map<String, Object> paramMap)
	{
		return sacCusPaymentDao.queryCusPayment(paramMap);
	}

	@Override
	public List<SacCusPayment> simpleQueryCusPaymentDetail(Map<String, Object> paramMap)
	{
		return sacCusPaymentDao.simpleQueryCusPayment(paramMap);
	}

	@Override
	public int queryCusPaymentDetailCount(Map<String, Object> paramMap)
	{
		return sacCusPaymentDao.querySacCusPaymentCount(paramMap);
	}

	@Override
	public List<Map<String, Object>> countCusPaymentAmount(Map<String, Object> paramMap)
	{
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		List<Map<String, Object>> countList = sacCusPaymentDao.countCusPaymentAmount(paramMap);
		for(Map<String, Object> countMap:countList){
			countMap.put("CURRENCY_TYPE", ccyMap.get(countMap.get("CURRENCY_TYPE")));
		}
		return countList;
	}
	
	@Override
	public String cusPaymentDetailDownloadContent(int i, Map<String, Object> paramMap)
	{
		List<SacCusPayment> cusPaymentDetailList = queryCusPaymentDetail(paramMap);
		if(cusPaymentDetailList==null||cusPaymentDetailList.size()==0){
			return null;
		}
		cusPaymentDetailList = handleSacCusPaymentList(cusPaymentDetailList);
		//组装下载文件内容
		StringBuffer sb = new StringBuffer();
		int j=i*1000+1;
		for(SacCusPayment scp:cusPaymentDetailList){
			sb.append(j+"|");
			sb.append(scp.getCusNo()+"|");
			sb.append(scp.getCusName()+"|");
			sb.append(scp.getTrxDate()+"|");
			sb.append(scp.getSacDate()+"|");
			sb.append(scp.getBussType()+"|");
			sb.append(scp.getTrxType()+"|");
			sb.append(scp.getTotalNum()+"|");
			sb.append(scp.getTotalAmount()+"|");
			sb.append(scp.getCurrencyType()+"|");
			sb.append(scp.getFees()+"|");
			sb.append(scp.getSacAmount()+"|");
			sb.append(scp.getSacSign()+"|");
			sb.append(scp.getSetBatchNo()+"\r\n");
			j++;
		}
		return sb.toString();
	}
	
	/**
	 * 对商户应付明细查询结果枚举值处理
	 * @param otrxInfoList
	 */
	@Override
	public List<SacCusPayment> handleSacCusPaymentList(List<SacCusPayment> cusPaymentDetailList){
		if(cusPaymentDetailList==null||cusPaymentDetailList.size()==0){
			return cusPaymentDetailList;
		}
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
		Map<String,Object> trxTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
		for(SacCusPayment scp:cusPaymentDetailList){
			//业务类型
			scp.setBussType(bussTypeMap.get(scp.getBussType())==null?"-":bussTypeMap.get(scp.getBussType()).toString());
			//交易类型
			scp.setTrxType(trxTypeMap.get(scp.getTrxType())==null?"-":trxTypeMap.get(scp.getTrxType()).toString());
			//币种
			scp.setCurrencyType(ccyMap.get(scp.getCurrencyType())==null?"-":ccyMap.get(scp.getCurrencyType()).toString());
			//是否已结算
			scp.setSacSign("Y".equals(scp.getSacSign())?"是":"否");
			scp.setSetBatchNo(scp.getSetBatchNo()==null?"-":scp.getSetBatchNo());
		}
		return cusPaymentDetailList;
	}

	@Override
	public List<SacCancelVerify> getSuspendedAccinfoList(
			Map<String, Object> queryMap) {
		Object pageSizeObj = queryMap.get("pageSize");
		Object pageNoObj = queryMap.get("pageNo");
		if(pageSizeObj!=null&&pageNoObj!=null){
			int pageSize = (Integer)pageSizeObj;
			int pageNo = (Integer)pageNoObj;
			Integer start = (pageNo-1)*pageSize;
			Integer end = pageNo*pageSize;
			queryMap.put("start", start);
			queryMap.put("end", end);
		}
		return sacCusPaymentDao.getSuspendedAccinfoList(queryMap);
	}
	
	@Override
	public String suspendedAccInfoDownload(int i, Map<String, Object> paramMap)
	{
		SacCusParameter param = new SacCusParameter();
		param.setOrgCardId((String)paramMap.get("orgCardId"));
		param.setCusName((String)paramMap.get("cusName"));
		
		String currencyType = (String)paramMap.get("currencyType");
		String bussType = (String)paramMap.get("bussType");
		String cavType = (String)paramMap.get("cavType");
		List<SacCusParameter> paramList = sacCusParameterService.selectAllSacCusParameter(param, 1, 10);
		if(paramList!=null&&paramList.size()!=0){
			String codeId = "";
			param = paramList.get(0);
			String cusNo = param.getCusNo();
			codeId="220203"+cusNo+currencyType+bussType+"02";
			
	        paramMap.put("codeId", codeId);
	        
	        paramMap.put("cavType", cavType);
		}else{
			return null;
		}
		
		
		List<SacCancelVerify> cavList = getSuspendedAccinfoList(paramMap);
		if(cavList==null||cavList.size()==0){
			return null;
		}
		cavList = handleSacCancelVerifyList(cavList);
		//组装下载文件内容
		StringBuffer sb = new StringBuffer();
		for(SacCancelVerify cav:cavList){
			sb.append(cav.getTrxSerialNo()+"|");
			sb.append(cav.getPayAmount()+"|");
			sb.append("人民币"+"|");
			sb.append(cav.getYhxAmount()+"|");
			sb.append(cav.getWhxAmount()+"|");
			sb.append(DateUtil.getDate(cav.getCreateTime())+"|");
			sb.append(DateUtil.getDate(cav.getTrxSuccTime())+"|");
			sb.append(cav.getCavType()+"\r\n");
		}
		return sb.toString();
	}

	private List<SacCancelVerify> handleSacCancelVerifyList(
			List<SacCancelVerify> cavList) {
		Map<String,Object> cavTypeMap = CacheUtil.getCacheByTypeToMap(Constants.CAV_STATE);
		for(SacCancelVerify cav:cavList){
			//核销类型
			cav.setCavType(cavTypeMap.get(cav.getCavType())==null?"-":cavTypeMap.get(cav.getCavType())+"("+cav.getCavType()+")");
		}
		return cavList;
	}

	@Override
	public int getSuspendedAccinfoListCount(Map<String, Object> paramMap) {
		return sacCusPaymentDao.getSuspendedAccinfoListCount(paramMap);
	}

}
