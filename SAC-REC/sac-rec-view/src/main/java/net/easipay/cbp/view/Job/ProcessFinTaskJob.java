package net.easipay.cbp.view.Job;

import java.util.ArrayList;
import java.util.List;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.service.OriTransactionManager;
import net.easipay.cbp.util.Paging;
import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "处理对账成功的交易记账任务", team = "对账系统", created = "2016-01-19")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessFinTaskJob implements Job {
  @Autowired
  OriTransactionManager oriTransactionManager;

  private static final Logger logger = Logger.getLogger(ProcessFinTaskJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    logger.info("开始执行处理对账成功的交易记账任务...");
    try {
      // 分页 获取T-1日所有对账成功交易
      Integer trxCount = oriTransactionManager.getSuccTrxCountForOneDay();
      Paging page = new Paging(Integer.valueOf(1), trxCount, Constants.MAX_RECORDS_ONEPAGE);
      Integer maxPage = page.getMaxPages();
      Integer startPos = page.getBeginElement();
      Integer endPos = page.getEndElement();
      List<SacOtrxInfo> pageList = new ArrayList<SacOtrxInfo>();
      while (maxPage > 0) {
        page = new Paging(Integer.valueOf(1), page.getMaxRecordsOnePage(), Constants.MAX_RECORDS_ONEPAGE);
        startPos = page.getBeginElement();
        endPos = page.getEndElement();
        pageList = oriTransactionManager.getSplictSuccTrxForOneDay(startPos, endPos);
        // 集中处理已经对账的交易的记账
        if (pageList != null && !pageList.isEmpty()) {
          oriTransactionManager.processRecTrxFin(pageList);
        }
        maxPage--;
        logger.info("成功发送了" + pageList.size() + "已经成功对账的原始交易到会计核算系统！");
      }
    } catch (Exception e) {
      logger.error("任务异常", e);
    }
    logger.info("结束执行处理对账成功的交易记账任务...");

  }
}
