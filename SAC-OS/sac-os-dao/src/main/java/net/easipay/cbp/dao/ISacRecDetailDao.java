package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacRecDetail;

/**
 * @author Administrator
 *
 */
public interface ISacRecDetailDao extends GenericDao<SacRecDetail,Long> {
	
	/**
	 * 根据条件查询批次表中数量
	 * 
	 * @param paramMap
	 * @return
	 */
	public Integer queryRecDetailCount(Map<String, Object> paramMap);

	/**
	 * 查询对账明细分页
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<SacRecDetail> queryRecDetail(Map<String, Object> paramMap);
	
	/**
	 * 查询对账明细不分页
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<SacRecDetail> simpleQueryRecDetail(Map<String, Object> paramMap);

}
