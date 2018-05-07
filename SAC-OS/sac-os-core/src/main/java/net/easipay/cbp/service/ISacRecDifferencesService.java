/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacRecDifferences;


/**
 * @author Administrator
 *
 */
public interface ISacRecDifferencesService {
	
	public List<SacRecDifferences> selectSacRecDifferences(Map<String,Object> queryMap,int pageNo,int pageSize); 

	public int selectSacRecDifferencesCounts(Map<String,Object> queryMap);
	
	public List<SacRecDifferences> selectSacRecDifferencesByParam(Map<String,Object> queryMap);
	
	public String sacRecDifferencesDownloadContent(int i ,Map<String,Object> paramMap);

	/**
	 * 根据交易流水号更新差错表处理状态
	 * @param sacRecDifferences
	 */
	public void updateSacRecDifferencesByTrxSerialNo(
			SacRecDifferences sacRecDifferences);

	public int selectDifferencesForSupplementCounts(Map<String, Object> queryMap);

	public List<Map<String,Object>> selectDifferencesForSupplement(
			Map<String, Object> queryMap, int pageNo, int pageSize);
	
	public String handleRecStatus(Map<String,String> paramMap);
	
	public List<SacRecDifferences> selectSacRecDifferencesDown(Map<String, Object> paramMap);
}
