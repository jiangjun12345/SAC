package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.model.SacRecordLog;




/**
 * @author Administrator
 *
 */
public interface ISacRecordLogService {
	
	/**
	 * 获取操作日志总数
	 * @param sacRecordLog
	 * @return
	 */
	public int selectSacRecordLogCounts(SacRecordLog sacRecordLog);
	
	/**
	 * 根据参数查询操作日志信息
	 * @paraSacRecordLogry
	 * @return
	 */
	public List<SacRecordLog> selectSacRecordLogByParam(SacRecordLog sacRecordLog,int pageNo,int pageSize);

	public SacRecordLog selectSacRecordLogById(SacRecordLog sacRecordLog);

}
