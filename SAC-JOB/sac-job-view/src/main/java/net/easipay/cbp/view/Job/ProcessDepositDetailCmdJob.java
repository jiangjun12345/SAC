package net.easipay.cbp.view.Job;

import net.easipay.cbp.service.ISacDepositDetailCmdService;

import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "线下预存明细指令状态更新任务", team = "JOB系统", created = "2016-03-09")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessDepositDetailCmdJob implements Job {

	private static final Logger logger = Logger.getLogger(ProcessDepositDetailCmdJob.class);
	
	@Autowired
	private ISacDepositDetailCmdService depositCmdService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始执行线下预存明细指令状态更新任务...");
		try{
			depositCmdService.updateDepositDetailCmd();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("线下预存明细指令状态更新任务执行异常……"+e.getMessage());
		}
		logger.info("线下预存明细指令状态更新任务执行完毕...");
	}
}
