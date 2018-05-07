/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacRecDifferences;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-4-2 上午11:58:48
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public interface RecDiffDao extends GenericDao<SacRecDifferences, Long> {
	/**
	 * 根据条件查询对账差错,只存在一条记录的情况
	 * 
	 * @param param
	 * @return
	 */
	public SacRecDifferences getRecDiff(Map<String, Object> param);

	/**
	 * 根据条件查询对账差错,可能存在多条记录的情况
	 * 
	 * @param param
	 * @return
	 */
	public List<SacRecDifferences> getRecDiffList(Map<String, Object> param);

	/**
	 * 插入对账差错数据
	 * 
	 * @param param
	 * @return
	 */
	public void insertRecDiff(SacRecDifferences sacRecDifferences);

	/**
	 * 更新对账差错
	 * 
	 * @param param
	 * @return
	 */
	public void updateRecDiff(SacRecDifferences sacRecDifferences);

	/**
	 * 根据条件查询对账差错长款需要增补交易,可能存在多条记录的情况
	 * 
	 * @param param
	 * @return
	 */
	public List<SacRecDifferences> selectDifferencesForSupplement(
			Map<String, Object> param);

}
