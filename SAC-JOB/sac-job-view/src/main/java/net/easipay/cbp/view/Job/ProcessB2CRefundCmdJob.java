package net.easipay.cbp.view.Job;

import net.easipay.cbp.service.ISacRefundCmdService;

import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "B2C退款指令任务", team = "JOB系统", created = "2016-03-09")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessB2CRefundCmdJob implements Job {

	private static final Logger logger = Logger.getLogger(ProcessB2CRefundCmdJob.class);
	
	@Autowired
	ISacRefundCmdService refundCmdService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始执行B2C退款指令任务...");
		try{
			//插入退款指令表
			refundCmdService.insertB2CRefundCmd();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("执行B2C退款指令任务执行异常……"+e.getMessage());
		}
		logger.info("B2C退款指令任务执行完毕...");
	}
}
