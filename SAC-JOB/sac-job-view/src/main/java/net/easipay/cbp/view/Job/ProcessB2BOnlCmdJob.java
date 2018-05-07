package net.easipay.cbp.view.Job;

import net.easipay.cbp.service.ISacOnlCommandService;

import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "B2B线上出款批次生成任务", team = "JOB系统", created = "2016-03-11")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessB2BOnlCmdJob implements Job {

	private static final Logger logger = Logger.getLogger(ProcessB2BOnlCmdJob.class);
	
	@Autowired
	private ISacOnlCommandService onlCmdService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始执行B2B线上出款批次生成任务...");
		try{
			onlCmdService.makeCommandBatch();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("B2B线上出款批次生成任务执行异常……"+e.getMessage());
		}
		logger.info("B2B线上出款批次生成任务执行完毕...");
	}
}
