/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.model.SacRecDetail;
import net.easipay.cbp.service.base.GenericManager;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-4-2 下午12:29:24
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public interface RecBatchDetailManager extends
		GenericManager<SacRecDetail, Long> {

	/**
	 * 查询对账批次明细,只有一条记录的情况
	 * 
	 * @param trx
	 * @return
	 * @throws Exception
	 */
	public SacRecDetail selectRecBatchDetail(Long id) throws Exception;

	/**
	 * 查询对账批次明细,存在多条记录的情况
	 * 
	 * @param trx
	 * @return
	 * @throws Exception
	 */
	public List<SacRecDetail> selectRecBatchDetailList(SacRecDetail sacRecDetail)
			throws Exception;

}
