package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.BussTypeGroup;
import net.easipay.cbp.model.SacTrxDetail;

/**
 * @Description: 客户交易管理DAO层接口(用一句话描述该文件做什么)
 * @author DELL (作者英文名称)
 * @date 2015-7-8 上午09:45:28
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */
public interface IBussTypeGroupDao extends GenericDao<BussTypeGroup, Long>
{

	/**
	 * 根据条件分页查询订单明细
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<BussTypeGroup> getBussTypeGroup(Map<String, Object> paramMap);

	
	/**
	 * 根据条件查询订单明细总数
	 * 
	 * @param paramMap
	 * @return
	 */
	public int getBussTypeGroupCount(Map<String, Object> paramMap);
	
	
}
