package net.easipay.cbp.dao;

import java.util.Map;
import java.util.List;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacChnSetDetail;

public interface ISacChnSetDetailDao extends GenericDao<SacChnSetDetail,Long>{

	public List<SacChnSetDetail> queryChnSetDetail(Map<String,Object> paramMap);
	
	public List<SacChnSetDetail> simpleQueryChnSetDetail(Map<String,Object> paramMap);
	
	public int queryChnSetDetailCount(Map<String,Object> paramMap);
}
