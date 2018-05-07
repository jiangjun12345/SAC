package net.easipay.cbp.view.Job;

import net.easipay.cbp.constant.Constants;
import net.easipay.dsfc.ws.jws.JwsClient;
import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

@GlassJob(description = "异步处理未处理的记账任务", team = "对账系统", created = "2016-01-19")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessScanUndoFinTaskJob implements Job {


  private static final Logger logger = Logger.getLogger(ProcessScanUndoFinTaskJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    logger.info("开始执行异步处理未处理的记账任务...");
    try {
      JwsClient jwsClient = new JwsClient(Constants.FINTASK_UNDO_TASK);
      jwsClient.putParam("maxSize", 1000).call();

    } catch (Exception e) {
      logger.error("任务异常", e);
    }
    logger.info("结束执行异步处理未处理的记账任务...");

  }
}
