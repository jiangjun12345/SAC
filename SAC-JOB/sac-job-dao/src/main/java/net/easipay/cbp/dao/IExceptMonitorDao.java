package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacExceptMonitor;
import net.easipay.cbp.dao.base.GenericDao;

public interface IExceptMonitorDao extends GenericDao<SacExceptMonitor,Long>{
	//AC入口交易异常信息（sac_record_log）
	public List<SacExceptMonitor> getSacRecordLogInfo();
	//系统调用预警异常信息（dsfs_alarm）
	public List<SacExceptMonitor> getDsfsAlarmInfo();
	//对账差错异常信息（sac_rec_differences）
	public List<SacExceptMonitor> getSacRecDifferenceInfo();
	//定时任务异常信息（sac_rec_job_log）
	public List<SacExceptMonitor> getSacRecJobLogInfo();
	//记账异常信息（fin_task）
	public List<SacExceptMonitor> getFinTaskInfo();
	//登记异常信息到主表
	public void insertSacexceptMonitor(List<SacExceptMonitor> param);
	public List<SacExceptMonitor> getSacExcetMonitorList(
			Map<String, Object> queryMap);
}
