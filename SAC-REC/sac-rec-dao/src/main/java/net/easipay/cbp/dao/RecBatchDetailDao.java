/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacRecDetail;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-4-2 上午11:58:48
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public interface RecBatchDetailDao extends GenericDao<SacRecDetail, Long> {
	/**
	 * 查询对账批次明细,只有一条记录的情况
	 * 
	 * @param param
	 * @return
	 */
	public SacRecDetail selectRecBatchDetail(Map<String, String> param);

	/**
	 * 查询对账批次明细,存在多条记录的情况
	 * 
	 * @param param
	 * @return
	 */
	public List<SacRecDetail> selectRecBatchDetailList(Map<String, String> param);

	/**
	 * 更新对账明细
	 * 
	 * @param param
	 * @return
	 */
	public void updateRecBatchDetail(SacRecDetail sacRecDetail);
}
