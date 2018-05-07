package net.easipay.cbp.view.Job;

import java.util.ArrayList;
import java.util.List;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.model.SacRecBatch;
import net.easipay.cbp.service.RecBatchManager;
import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "内部对账任务", team = "对账系统", created = "2016-01-19")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessInnerRecJob implements Job {
  @Autowired
  RecBatchManager recBatchManager;

  private static final Logger logger = Logger.getLogger(ProcessInnerRecJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    logger.info("开始执行内部对账任务...");
    try {
      // 获取T-1日所有内部对账批次
      List<SacRecBatch> recBatchList = new ArrayList<SacRecBatch>();
      recBatchList = recBatchManager.selectRecBatchForOneDay(Constants.REC_TYPE_INNER_TRX);
      // 批次循环处理，以对账批次文件为准，与交易原始表进行核对，有差错插入对账差错表
      if (recBatchList != null && !recBatchList.isEmpty()) {
        for (SacRecBatch sacRecBatch : recBatchList) {
          recBatchManager.processInnerRec(sacRecBatch);
          logger.info("成功处理了对账批次ID=" + sacRecBatch.getRecBatchId() + ",此批次包含了" + sacRecBatch.getRecCount() + "笔对账明细交易！");
        }
        //处理清算多余交易
        for (SacRecBatch sacRecBatch : recBatchList) {
          recBatchManager.processInnerRecShortTerm(sacRecBatch);
        }
      }
    } catch (Exception e) {
      logger.error("任务异常", e);
    }
    logger.info("结束执行内部对账任务...");
  }

}
