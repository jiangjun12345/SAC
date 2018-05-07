package net.easipay.cbp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.easipay.cbp.model.SacOperHistory;




/**
 * @author Administrator
 *
 */
public interface ISacOperHistoryService {
	
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

	/**
	 * 插入操作日志信息
	 * @param operType
	 */
	public void insertSacOperHistory(String operType,HttpServletRequest request);
}
