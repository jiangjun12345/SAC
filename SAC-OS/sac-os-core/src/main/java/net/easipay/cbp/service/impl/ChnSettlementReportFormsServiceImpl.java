package net.easipay.cbp.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.dao.ISacChannelParamDao;
import net.easipay.cbp.dao.ISacChnSettlementDao;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacChnSettlement;
import net.easipay.cbp.service.IChnSettlementReportFormsService;
import net.easipay.cbp.util.ConstantParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChnSettlementReportFormsServiceImpl implements IChnSettlementReportFormsService
{

	@Autowired
	private ISacChannelParamDao channelDao;
	
	@Autowired
	private ISacChnSettlementDao chnSettlementDao;
	
	
	@Override
	public List<SacChannelParam> queryAllChannel()
	{
		return channelDao.queryAllSacChannelParam();
	}

	/****************渠道应收款报表层*******************/
	@Override
	public List<SacChnSettlement> queryChnSettlement(Map<String, Object> paramMap)
	{
		return chnSettlementDao.queryChnSettlement(paramMap);
	}

	@Override
	public List<SacChnSettlement> simpleQueryChnSettlement(Map<String, Object> paramMap)
	{
		return chnSettlementDao.simpleQueryChnSettlement(paramMap);
	}

	@Override
	public int querySacChnSettlementCount(Map<String, Object> paramMap)
	{
		return chnSettlementDao.queryChnSettlementCount(paramMap);
	}
	
	/**
	 * 对渠道应收查询结果枚举值处理
	 */
	@Override
	public List<SacChnSettlement> handleSacChnSettlement(List<SacChnSettlement> chnSettlementList){
		Map<String,String> typeMap = new HashMap<String, String>();
		typeMap.put("N", "正常");
		typeMap.put("S", "短款");
		typeMap.put("L", "长款");
		typeMap.put("O", "其他");
		if(chnSettlementList==null||chnSettlementList.size()==0){
			return chnSettlementList;
		}
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		for(SacChnSettlement scs:chnSettlementList){
			scs.setType(typeMap.get(scs.getType())==null?"-":typeMap.get(scs.getType()));
			scs.setCurrencyType(ccyMap.get(scs.getCurrencyType())==null?"-":ccyMap.get(scs.getCurrencyType()).toString());
		}
		return chnSettlementList;
	}

	@Override
	public String chnSettlementDownloadContent(int i, Map<String, Object> paramMap)
	{
		List<SacChnSettlement> chnSettlementList = queryChnSettlement(paramMap);
		if(chnSettlementList==null||chnSettlementList.size()==0){
			return null;
		}
		chnSettlementList = handleSacChnSettlement(chnSettlementList);
		//组装下载文件内容
		StringBuffer sb = new StringBuffer();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		int j=i*10000+1;
		for(SacChnSettlement scs:chnSettlementList){
			sb.append(j+"|");
			sb.append(scs.getSacBankName()+"|");
			sb.append(scs.getAccountNumber()+"|");
			sb.append(scs.getSacDate()+"|");
			sb.append(scs.getTransDate()+"|");
			sb.append(scs.getType()+"|");
			sb.append(scs.getTotalNum()+"|");
			sb.append(scs.getTotalSum()+"|");
			sb.append(scs.getTrxCost()+"|");
			sb.append(scs.getReceiveTotSum()+"|");
			sb.append(scs.getCurrencyType()+"\r\n");
			j++;
		}
		return sb.toString();
	}

	@Override
	public List<Map<String, Object>> countChnSettlementAmount(Map<String, Object> paramMap)
	{
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		List<Map<String, Object>> countList = chnSettlementDao.countChnSettlementAmount(paramMap);
		for(Map<String, Object> countMap:countList){
			countMap.put("CURRENCY_TYPE", ccyMap.get(countMap.get("CURRENCY_TYPE")));
		}
		return countList;
	}

}
