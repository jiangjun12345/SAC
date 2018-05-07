/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacRecDifferences;

/**
 * @author Administrator
 *
 */
public interface ISacRecDifferencesDao extends GenericDao<SacRecDifferences,Long> {
	
	public List<SacRecDifferences> selectSacRecDifferences(Map<String,Object> queryMap,int pageNo,int pageSize);
	
	public int selectSacRecDifferencesCounts(Map<String,Object> queryMap);

	public void updateSacRecDifferencesByTrxSerialNo(
			SacRecDifferences sacRecDifferences);

	/**
	 * 根据条件查询差错表中差错数量
	 * 
	 * @param paramMap
	 * @return
	 */
	public Integer queryRecDiffDetailCount(Map<String, Object> paramMap);

	/**
	 * 查询对账差错明细分页
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<SacRecDifferences> queryRecDiffDetail(Map<String, Object> paramMap);
	
	/**
	 * 查询对账差错明细不分页
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<SacRecDifferences> simpleQueryRecDiffDetail(Map<String, Object> paramMap);

	public List<SacRecDifferences> selectSacRecDifferencesByParam(
			Map<String, Object> queryMap);

	public List<Map<String,Object>> selectDifferencesForSupplement(
			Map<String, Object> queryMap, int pageNo, int pageSize);

	public int selectDifferencesForSupplementCounts(Map<String, Object> queryMap);
	
	public void updateSacRecDifferences(SacRecDifferences sacRecDifferences) ;
	
	public List<SacRecDifferences> selectSacRecDifferencesDown(Map<String, Object> paramMap);
	
}
