/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacChnSettlement;

/**
 * @author Administrator
 *
 */
public interface ISacChnSettlementDao extends GenericDao<SacChnSettlement,Long> {
	
	public List<SacChnSettlement> selectSacChnSettlement(SacChnSettlement sacChnSettlement,int pageNo,int pageSize);
	
	public int selectSacChnSettlementCounts(SacChnSettlement sacChnSettlement);

	public void updateSacChnSettlement(SacChnSettlement sacChnSettlement);

	public SacChnSettlement selectSacChnSettlementById(
			SacChnSettlement sacChnSettlement);

	public List<SacChnSettlement> queryChnSettlement(Map<String,Object> paramMap);
	
	public List<SacChnSettlement> simpleQueryChnSettlement(Map<String,Object> paramMap);
	
	public int queryChnSettlementCount(Map<String,Object> paramMap);
	
	public List<Map<String,Object>> countChnSettlementAmount(Map<String,Object> paramMap);
}
