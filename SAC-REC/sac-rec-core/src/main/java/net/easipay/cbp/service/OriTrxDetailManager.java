/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacTrxDetail;
import net.easipay.cbp.service.base.GenericManager;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-4-2 下午12:29:24
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public interface OriTrxDetailManager extends GenericManager<SacTrxDetail, Long> {

	/**
	 * 查询原始交易明细,存在多条记录的情况
	 * 
	 * @param trx
	 * @return
	 * @throws Exception
	 */
	public List<SacTrxDetail> selectOriTrxDetailList(
			Map<String, Object> filterMap) throws Exception;

	/**
	 * 查询原始交易明细,只存在一条记录的情况
	 * 
	 * @param trx
	 * @return
	 * @throws Exception
	 */
	public SacTrxDetail selectOriTrxDetail(Map<String, Object> filterMap)
			throws Exception;

}
