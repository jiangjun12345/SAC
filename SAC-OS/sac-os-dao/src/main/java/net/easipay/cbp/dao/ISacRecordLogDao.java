/**
 * 
 */
package net.easipay.cbp.dao;


import java.util.List;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacRecordLog;

/**
 * @author Administrator
 *
 */
public interface ISacRecordLogDao extends GenericDao<SacRecordLog,Long> {

	
	
	/**
	 * 获取操作日志总数
	 * @param sacOperHistory
	 * @return
	 */
	public int selectSacRecordLogCounts(SacRecordLog sacRecordLog);
	
	/**
	 * 根据参数查询操作日志信息
	 * @param sacOperHistory
	 * @return
	 */
	public List<SacRecordLog> selectSacRecordLogByParam(SacRecordLog sacRecordLog,int pageNo,int pageSize);

	public SacRecordLog selectSacRecordLogById(SacRecordLog sacRecordLog);
	

	
}
