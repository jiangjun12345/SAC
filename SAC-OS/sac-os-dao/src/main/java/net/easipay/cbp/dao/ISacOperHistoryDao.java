/**
 * 
 */
package net.easipay.cbp.dao;


import java.util.List;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacOperHistory;

/**
 * @author Administrator
 *
 */
public interface ISacOperHistoryDao extends GenericDao<SacOperHistory,Long> {

	
	
	/**
	 * 获取操作日志总数
	 * @param sacOperHistory
	 * @return
	 */
	public int selectSacOperHistoryCounts(SacOperHistory sacOperHistory);
	
	/**
	 * 根据参数查询操作日志信息
	 * @param sacOperHistory
	 * @return
	 */
	public List<SacOperHistory> selectSacOperHistoryByParam(SacOperHistory sacOperHistory,int pageNo,int pageSize);
	
	
	public void insertSacOperHistory(SacOperHistory sacOperHistory);
	

	
}
