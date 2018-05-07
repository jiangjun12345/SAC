package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;


import net.easipay.cbp.model.BussTypeGroup;


/**
 * 
 * @Description: 运营系统渠道对账业务层(用一句话描述该文件做什么)
 * @author DELL (作者英文名称)
 * @date 2015-7-14 下午02:16:41
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */
public interface IBussTypeGroupService
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
