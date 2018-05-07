package net.easipay.cbp.view.Job;

import net.easipay.cbp.service.ISacFundTransferCmdService;

import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "资金调拨状态更新任务", team = "JOB系统", created = "2016-03-09")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessFundTransferStateJob implements Job{

	private static final Logger logger = Logger.getLogger(ProcessFundTransferStateJob.class);
	
	@Autowired
	private ISacFundTransferCmdService fundTransferCmdService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始执行资金调拨状态更新任务...");
		try{
			//更新资金调拨指令状态
			fundTransferCmdService.updateSacFundTransferCmd();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("资金调拨状态更新任务执行异常……"+e.getMessage());
		}
		logger.info("资金调拨状态更新任务执行完毕...");
	}

}
