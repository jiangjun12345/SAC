package net.easipay.cbp.view.Job;

import net.easipay.cbp.service.ISacExceptMonitorService;
import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
@GlassJob(description = "异常信息监控任务", team = "JOB系统", created = "2016-06-28")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class precessExceptMonitorJob implements Job {
	
	private static final Logger logger = Logger.getLogger(ProcessB2BOflCmdJob.class);
	
	@Autowired
	private ISacExceptMonitorService monitorService; 
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("开始执行异常信息监控扫描任务...");
		try {
			monitorService.registerExceptDetail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常监控定时录入任务 执行出错！流水号：" + "。报错信息：" + e.getMessage());
			e.printStackTrace();
		}
	}

}
