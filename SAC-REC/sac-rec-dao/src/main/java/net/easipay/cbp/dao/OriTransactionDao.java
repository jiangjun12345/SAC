/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacOtrxInfo;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-4-2 上午11:58:48
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public interface OriTransactionDao extends GenericDao<SacOtrxInfo, Long> {
	/**
	 * 根据条件查询原始交易,只存在一条记录的情况
	 * 
	 * @param param
	 * @return
	 */
	public SacOtrxInfo getOriTransaction(Map<String, Object> param);

	/**
	 * 根据条件查询原始交易,可能存在多条记录的情况
	 * 
	 * @param param
	 * @return
	 */
	public List<SacOtrxInfo> getOriTransactionList(Map<String, Object> param);

	/**
	 * 更新原始交易
	 * 
	 * @param param
	 * @return
	 */
	public void updateOriTransaction(SacOtrxInfo sacOtrxInfo);

	/**
	 * 获取当天所有成功对账的交易数目
	 * 
	 * @param trx
	 * @return
	 * @throws Exception
	 */
	public Integer getSuccTrxCountForOneDay(Map<String, String> param);

	/**
	 * 分页方式获取当天所有成功对账的交易
	 * 
	 * @param trx
	 * @return
	 * @throws Exception
	 */
	public List<SacOtrxInfo> getSplictTrxListForOneDay(Map<String, String> param);

	public  List<Map<String, Object>> getAmountGroupByCus(Map<String, Object> queryMap);

	public List<Map<String, Object>> getRefundAmountGroupByCus(
			Map<String, Object> queryMap);

	
	/**
   * 更新原始交易表相关状态
   * 
   * @param param
   * @return
   */
  public void updateOriTransactionStatus(SacOtrxInfo sacOtrxInfo);

	public List<Map<String, Object>> getSacTrxRemittance(Map<String, Object> queryMap);

	public void updateSacTrxRemittanceDealFlag(Map<String,Object> updateMap);

}
