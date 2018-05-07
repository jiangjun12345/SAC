package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacRecBatch;

/**
 * @Description: 运营系统渠道对账dao层(用一句话描述该文件做什么)
 * @author DELL (作者英文名称)
 * @date 2015-7-14 下午01:29:38
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */
public interface ISacRecBatchDao extends GenericDao<SacRecBatch, Long>
{
	/**
	 * 根据条件查询对账批次表
	 * 
	 * @param paramMap
	 * @return
	 */

	public List<SacRecBatch> queryCheckAccBatch(Map<String, Object> paramMap);
	
	/**
	 * 查询B2C对账结果
	 * @param paramMap
	 * @return
	 */
	public List<Map<String,Object>> checkAccResultList(Map<String, Object> paramMap);

	/**
	 * 查询对账结果总数
	 * @param paramMap
	 * @return
	 */
	public int checkAccResultListCount(Map<String, Object> paramMap);
}
