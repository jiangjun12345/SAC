package net.easipay.cbp.view.Job;

import java.util.HashMap;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.service.SacChnSettlementManager;
import net.easipay.dsfc.ws.jws.JwsClient;
import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "处理试算平衡任务", team = "对账系统", created = "2016-01-19")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessTrialBalancingJob implements Job {

  @Autowired
  SacChnSettlementManager sacChnSettlementManager;
  
  private static final Logger logger = Logger.getLogger(ProcessTrialBalancingJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    logger.info("开始执行处理试算平衡任务...");
    try {
      sacChnSettlementManager.sacTrialBalancingJobPro();
    } catch (Exception e) {
      logger.error("任务异常", e);
    }
    logger.info("结束执行处理试算平衡任务...");
  }
}
