package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacCusBalance;
import net.easipay.cbp.model.SacTrxDetail;

/**
 * @Description: 客户交易管理DAO层接口(用一句话描述该文件做什么)
 * @author DELL (作者英文名称)
 * @date 2015-7-8 上午09:45:28
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */
public interface ISacTrxDetailDao extends GenericDao<SacTrxDetail, Long>
{

	/**
	 * 根据条件分页查询订单明细
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<SacTrxDetail> queryTrxDetail(Map<String, Object> paramMap);

	/**
	 * 根据条件查询订单明细(不分页)
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<SacTrxDetail> simpleQueryTrxDetail(Map<String, Object> paramMap);

	/**
	 * 根据条件查询订单明细总数
	 * 
	 * @param paramMap
	 * @return
	 */
	public int getTrxDetailCount(Map<String, Object> paramMap);
	
	public void updateTrxDetailBySerialNo(SacTrxDetail sacTrxDetail);
	
	/**
   * 更新原始交易明细状态
   * 
   * @param param
   * @return
   */
  public void updateOriTrxDetailStatus(SacTrxDetail sacTrxDetail);
  
  /**
   * 根据条件查询原始交易明细,只存在一条记录的情况
   * 
   * @param param
   * @return
   */
  public SacTrxDetail getOriTrxDetail(Map<String, Object> param);
}
