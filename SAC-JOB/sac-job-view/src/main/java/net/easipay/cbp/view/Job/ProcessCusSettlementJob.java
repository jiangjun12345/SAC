package net.easipay.cbp.view.Job;

import net.easipay.cbp.service.ISacCusSettlementService;
import net.easipay.cbp.service.ISacOflCommandService;

import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "汇总清算出款交易任务", team = "JOB系统", created = "2016-06-22")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessCusSettlementJob implements Job {

	private static final Logger logger = Logger.getLogger(ProcessCusSettlementJob.class);
	
	@Autowired
	private ISacCusSettlementService sacCusSettlementService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始执行汇总清算出款交易任务...");
		try{
			sacCusSettlementService.collectCusSettlement();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("汇总清算出款交易任务执行异常……"+e.getMessage());
		}
		logger.info("汇总清算出款交易任务执行完毕...");
	}
}
