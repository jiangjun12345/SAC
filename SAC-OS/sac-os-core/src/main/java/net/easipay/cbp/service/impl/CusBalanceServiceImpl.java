package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.dao.ISacCusBalanceDao;
import net.easipay.cbp.dao.ISacCusParameterDao;
import net.easipay.cbp.dao.ISysDicDao;
import net.easipay.cbp.model.FinMx;
import net.easipay.cbp.model.SacSysDic;
import net.easipay.cbp.service.ICusBalanceService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsException;
import net.easipay.dsfc.ws.jws.JwsResult;
import oracle.sql.TIMESTAMP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CusBalanceServiceImpl implements ICusBalanceService
{

	Logger logger = LoggerFactory.getLogger(CusBalanceServiceImpl.class);
	
	@Autowired
	private ISacCusBalanceDao cusBalanceDao;
	
	@Autowired
	private ISysDicDao sysDicDao;
	
	@Autowired
	private ISacCusParameterDao cusParamDao;
	
	
	@Override
	public List<String> selectlistSacCusParameterByCusName(Map<String, Object> paramMap)
	{
		return cusParamDao.selectlistSacCusParameterByCusName(paramMap);
	}
	
	@Override
	public List<Map<String, Object>> queryCusBalance(Map<String, Object> paramMap)
	{
		return cusBalanceDao.queryCusBalance2(paramMap);
	}
	
	@Override
	public List<Map<String, Object>> queryCusBalanceFundDay(Map<String, Object> paramMap)
	{
		return cusBalanceDao.queryCusBalanceFundDay(paramMap);
	}
	
	@Override
	public List<Map<String, Object>> queryCusBalanceFundDay2(Map<String, Object> paramMap)
	{
		return cusBalanceDao.queryCusBalanceFundDay2(paramMap);
	}

	@Override
	public List<Map<String, Object>> simpleQueryCusBalance(Map<String, Object> paramMap)
	{
		return cusBalanceDao.simpleQueryCusBalance2(paramMap);
	}

	@Override
	public int getCusBalanceCount(Map<String, Object> paramMap)
	{
		return cusBalanceDao.getCusBalanceCount2(paramMap);
	}
	
	@Override
	public int queryCusBalanceFundDayCount(Map<String, Object> paramMap)
	{
		return cusBalanceDao.getCusBalanceFundDayCount(paramMap);
	}
	
	@Override
	public int queryCusBalanceFundDayCount2(Map<String, Object> paramMap)
	{
		return cusBalanceDao.getCusBalanceFundDayCount2(paramMap);
	}
	
	@Override
	public Map<String, Object> queryFinMxList(Map<String, Object> paramMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JwsResult result = null;
		try{
			JwsClient jc = new JwsClient("SAC-FA-0001");
			result = jc.putParam(paramMap).call();
			if(result.isSuccess()&&result.getCode().equals(ConstantParams.RTN_CODE_SUCCESSS)){
				resultMap.put("mxList",result.getList("finMxList",FinMx.class) );
				resultMap.put("totalCount",result.getIntValue("totalCount") );
			}
		}catch(JwsException e){
			logger.error("request FA exception :"+e.getMessage());
		}
		return resultMap;
	}
	
	@Override
	public List<Map<String,Object>> handleCusBalanceList(List<Map<String,Object>> cusBalanceList){
		if(cusBalanceList==null||cusBalanceList.size()==0){
			return cusBalanceList;
		}
		
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
		for(Map<String,Object> cusBalance : cusBalanceList){
			//处理币种
			Object ccyValue = ccyMap.get(cusBalance.get("sacCurrency"));
			cusBalance.put("sacCurrency", ccyValue==null?"-":ccyValue);
			//处理业务类型
			Object bussType = bussTypeMap.get(cusBalance.get("bussType"));
			cusBalance.put("bussType", bussType==null?"-":bussType);
			//处理账户子类型
			Object childAccType = getChileAccTypeMap().get(cusBalance.get("childAccType"));
			cusBalance.put("childAccType", childAccType==null?"-":childAccType);
			//处理金额
			cusBalance.put("totalAmount", cusBalance.get("totalAmount")==null?"0.00":cusBalance.get("totalAmount"));
		}
		return cusBalanceList;
	}
	
	@Override
	public List<Map<String,Object>> handleCusBalanceList1(List<Map<String,Object>> cusBalanceList){
		if(cusBalanceList==null||cusBalanceList.size()==0){
			return cusBalanceList;
		}
		
//		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
		for(Map<String,Object> cusBalance : cusBalanceList){
			//处理业务类型
			Object bussType = bussTypeMap.get(cusBalance.get("BUSSTYPE"));
			cusBalance.put("bussType", bussType==null?"-":bussType);
			//处理账户子类型
			Object childAccType = getChileAccTypeMap().get(cusBalance.get("CHILDACCTYPE"));
			cusBalance.put("childAccType", childAccType==null?"-":childAccType);
		}
		return cusBalanceList;
	}
	
	public String getDateBySqlTimestamp(Object obj, String formatStr) {  
        try {  
            TIMESTAMP t = (TIMESTAMP)obj;  
            if (formatStr == null || formatStr.equals("")) {  
                formatStr = "yyyy/MM/dd";  
            }  
            Timestamp tt;  
            tt = t.timestampValue();  
            Date date = new Date(tt.getTime());  
            SimpleDateFormat sd = new SimpleDateFormat(formatStr);  
            return sd.format(date);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  

	@Override
	public Map<String,Object> getChileAccTypeMap(){
		Map<String,Object> childAccTypeMap = new HashMap<String, Object>();
		List<SacSysDic> sysDicList = sysDicDao.selectChildAccType();
		for(SacSysDic sysDic:sysDicList){
			childAccTypeMap.put(sysDic.getDicCode(), sysDic.getDicDesc());
		}
		childAccTypeMap.remove("00");
		return childAccTypeMap;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String cusBalanceDetailDownloadContent(int i, Map<String, Object> paramMap)
	{
		List<FinMx> cusBalanceDetailList = (List<FinMx>) (queryFinMxList(paramMap).get("mxList"));
		if(cusBalanceDetailList==null||cusBalanceDetailList.size()==0){
			return null;
		}
		//组装下载文件内容
		Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
		Map<String,Object> trxTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		int j=i*10000+1;
		for(FinMx fm:cusBalanceDetailList){
			sb.append(j+"|");
			sb.append(fm.getCodeId()+"|");
			sb.append(fm.getPzNo()+"|");
			sb.append(sdf.format(fm.getMxTime())+"|");
			sb.append((fm.getDirection()==1?"借":"贷")+"|");
			sb.append(fm.getfDebit()+"|");
			sb.append(fm.getfCredit()+"|");
			sb.append(fm.getAmount()+"|");
			sb.append(fm.getOpenBal()+"|");
			sb.append(fm.getCloseBal()+"|");
			sb.append((fm.getGains()==null?"-":fm.getGains())+"|");
			sb.append((bussTypeMap.get(fm.getBusiType())==null?"-":bussTypeMap.get(fm.getBusiType()))+"|");
			sb.append((trxTypeMap.get(fm.getTrxCode())==null?"-":trxTypeMap.get(fm.getTrxCode()))+"|");
			sb.append(sdf.format(fm.getTradeTime())+"|");
			sb.append(fm.getSerno()+"\r\n");
			j++;
		}
		return sb.toString();
	}

	@Override
	public Boolean validateCusAvalibleBal(String crtCode, BigDecimal amount,
			String batchCur) {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("orgCardId", crtCode);
		queryMap.put("sacCurrency", batchCur);
		List<Map<String,Object>> mapList = cusBalanceDao.getCusAvalibleBal(queryMap);
		BigDecimal totalAmount = new BigDecimal("0.00");
		if(mapList!=null&&mapList.size()>0){
			Map<String, Object> map = mapList.get(0);
			totalAmount = (BigDecimal)map.get("totalAmount");
		}
		if(amount.compareTo(totalAmount)<=0){
			return true;
		}
		return false;
	}
	
	@Override
	public List<String> getCodeIdsByCusparamMap(Map<String, Object> paramMap){
		return cusBalanceDao.getCodeIdsByCusparamMap(paramMap);
				
	}
}
