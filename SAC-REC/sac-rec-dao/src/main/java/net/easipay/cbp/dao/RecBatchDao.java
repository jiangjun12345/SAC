/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacRecBatch;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-4-2 上午11:58:48
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public interface RecBatchDao extends GenericDao<SacRecBatch, Long> {
	/**
	 * 根据条件查询原始交易,只存在一条记录的情况
	 * 
	 * @param param
	 * @return
	 */
	public SacRecBatch getRecBatch(Map<String, String> param);

	/**
	 * 根据条件查询原始交易,可能存在多条记录的情况
	 * 
	 * @param param
	 * @return
	 */
	public List<SacRecBatch> getRecBatchList(Map<String, String> param);

	/**
	 * 更新对账批次
	 * 
	 * @param param
	 * @return
	 */
	public void updateRecBatch(SacRecBatch sacRecBatch);
}
