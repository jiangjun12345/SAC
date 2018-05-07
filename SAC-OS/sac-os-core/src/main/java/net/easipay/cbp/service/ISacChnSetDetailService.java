package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacChnSetDetail;

public interface ISacChnSetDetailService {    
   
	public List<SacChnSetDetail> queryChnSetDetail(Map<String,Object> paramMap);
	
	public List<SacChnSetDetail> simpleQueryChnSetDetail(Map<String,Object> paramMap);
	
	public int queryChnSetDetailCount(Map<String,Object> paramMap);
	
	public List<SacChnSetDetail> handleSacChnSetDetail(List<SacChnSetDetail> chnSetDetailList);
	
	public String chnSettlementDetailDownloadContent(int i,Map<String,Object> paramMap);
}

