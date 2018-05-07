package net.easipay.cbp.view.Job;

import net.easipay.cbp.service.ISacTransferCmdService;

import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "B2B划款指令状态更新任务", team = "JOB系统", created = "2016-03-09")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessB2BTransferCmdJob implements Job {

	private static final Logger logger = Logger.getLogger(ProcessB2BTransferCmdJob.class);
	
	@Autowired
	private ISacTransferCmdService transferCmdService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始执行B2B划款指令状态更新任务...");
		try{
			transferCmdService.updateB2BRefundCmd();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("B2B划款指令状态更新任务执行异常……"+e.getMessage());
		}
		logger.info("B2B划款指令状态更新任务执行完毕...");
	}
}
