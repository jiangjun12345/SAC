/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacCusSettlement;

/**
 * @author Administrator
 *
 */
public interface ISacCusSettlementDao extends GenericDao<SacCusSettlement,Long> {
	
	public List<SacCusSettlement> selectSacCusSettlement(SacCusSettlement sacCusSettlement,int pageNo,int pageSize);
	
	public int selectSacCusSettlementCounts(SacCusSettlement sacCusSettlement);

	public void updateSacCusSettlement(SacCusSettlement sacCusSettlement);

	public SacCusSettlement selectSacCusSettlementById(
			SacCusSettlement sacCusSettlement);

	public List<SacCusSettlement> querySacCusSettlement(Map<String,Object> paramMap);
	
	public List<SacCusSettlement> simpleQuerySacCusSettlement(Map<String,Object> paramMap);
	
	public int querySacCusSettlementCount(Map<String,Object> paramMap);
	
	public List<Map<String,Object>> countCusSettlementAmount(Map<String, Object> paramMap);
}
