/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacTrxDetail;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-4-2 上午11:58:48
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public interface OriTrxDetailDao extends GenericDao<SacTrxDetail, Long> {
	/**
	 * 根据条件查询原始交易明细,只存在一条记录的情况
	 * 
	 * @param param
	 * @return
	 */
	public SacTrxDetail getOriTrxDetail(Map<String, Object> param);

	/**
	 * 根据条件查询原始交易明细,可能存在多条记录的情况
	 * 
	 * @param param
	 * @return
	 */
	public List<SacTrxDetail> getOriTrxDetailList(Map<String, Object> param);

	/**
	 * 更新原始交易明细
	 * 
	 * @param param
	 * @return
	 */
	public void updateOriTrxDetail(SacTrxDetail sacTrxDetail);

	/**
   * 更新原始交易明细状态
   * 
   * @param param
   * @return
   */
	public void updateOriTrxDetailStatus(SacTrxDetail sacTrxDetail);
	
}