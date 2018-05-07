package net.easipay.cbp.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.dao.ISacChnSetDetailDao;
import net.easipay.cbp.model.SacChnSetDetail;
import net.easipay.cbp.service.ISacChnSetDetailService;
import net.easipay.cbp.util.ConstantParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SacChnSetDetailServiceImpl implements ISacChnSetDetailService{

	@Autowired
    private ISacChnSetDetailDao sacChnSetDetailDao;

	@Override
	public List<SacChnSetDetail> queryChnSetDetail(Map<String, Object> paramMap)
	{
		return sacChnSetDetailDao.queryChnSetDetail(paramMap);
	}

	@Override
	public List<SacChnSetDetail> simpleQueryChnSetDetail(Map<String, Object> paramMap)
	{
		return sacChnSetDetailDao.simpleQueryChnSetDetail(paramMap);
	}

	@Override
	public int queryChnSetDetailCount(Map<String, Object> paramMap)
	{
		return sacChnSetDetailDao.queryChnSetDetailCount(paramMap);
	}

	/**
	 * 渠道应收明细处理
	 * @param chnSetDetailList
	 * @return
	 */
	@Override
	public List<SacChnSetDetail> handleSacChnSetDetail(List<SacChnSetDetail> chnSetDetailList){
		Map<String,String> typeMap = new HashMap<String, String>();
		typeMap.put("N", "正常");
		typeMap.put("S", "短款");
		typeMap.put("L", "长款");
		typeMap.put("O", "其他");
		if(chnSetDetailList==null||chnSetDetailList.size()==0){
			return chnSetDetailList;
		}
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		for(SacChnSetDetail scs: chnSetDetailList){
			scs.setType(typeMap.get(scs.getType())==null?"-":typeMap.get(scs.getType()));
			scs.setBusiType("1".equals(scs.getBusiType())?"正向":"逆向");
			scs.setCurrencyType(ccyMap.get(scs.getCurrencyType())==null?"-":ccyMap.get(scs.getCurrencyType()).toString());
			String payconType = scs.getPayconType();
			if("1".equals(payconType)){
				scs.setPayconType("B2B支付");
			}else if("2".equals(payconType)){
				scs.setPayconType("B2C支付");
			}else{
				scs.setPayconType("银存");
			}
			//scs.setPayconType("1".equals(scs.getPayconType())?"B2B支付":"B2C支付");
			scs.setTrxDate(scs.getTrxDate()==null?"-":scs.getTrxDate());
			scs.setSacDate(scs.getSacDate()==null?"-":scs.getSacDate());
		}
		return chnSetDetailList;
	}
	
	@Override
	public String chnSettlementDetailDownloadContent(int i, Map<String, Object> paramMap)
	{
		List<SacChnSetDetail> chnSetDetailList  = queryChnSetDetail(paramMap);
		if(chnSetDetailList==null||chnSetDetailList.size()==0){
			return null;
		}
		chnSetDetailList = handleSacChnSetDetail(chnSetDetailList);
		//组装下载文件内容
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int j=i*1000+1;
		for(SacChnSetDetail scs:chnSetDetailList){
			sb.append(j+"|");
			sb.append(scs.getChnName()+"|");
			sb.append(scs.getPayconType()+"|");
			sb.append(scs.getSacBankName()+"|");
			sb.append(scs.getAccountNumber()+"|");
			sb.append(scs.getTransDate()+"|");
			sb.append(scs.getSacDate()+"|");
			sb.append(scs.getType()+"|");
			sb.append(scs.getBusiType()+"|");
			sb.append(scs.getTotalNum()+"|");
			sb.append(scs.getTotalSum()+"|");
			sb.append(scs.getTrxCost()+"|");
			sb.append(scs.getCurrencyType()+"|");
			sb.append(scs.getChnBatchNo()+"|");
			sb.append(scs.getCreateTime()==null?"-":sdf.format(scs.getCreateTime())+"\r\n");
			j++;
		}
		return sb.toString();
	}
	
}

