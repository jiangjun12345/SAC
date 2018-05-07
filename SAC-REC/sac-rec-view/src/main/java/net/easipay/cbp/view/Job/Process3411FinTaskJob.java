package net.easipay.cbp.view.Job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.service.OriTransactionManager;
import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "处理3411记账任务", team = "对账系统", created = "2016-01-19")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class Process3411FinTaskJob implements Job {
  @Autowired
  OriTransactionManager oriTransactionManager;

  private static final Logger logger = Logger.getLogger(ProcessFinTaskJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    logger.info("开始执行处理3411记账任务...");
    try {
      //      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      //      Calendar cal = new GregorianCalendar();
      //      cal.setTime(new Date());
      //      cal.set(Calendar.HOUR_OF_DAY, 0);
      //      cal.set(Calendar.MINUTE, 0);
      //      cal.set(Calendar.SECOND, 0);
      //      cal.set(Calendar.MILLISECOND, 0);
      //
      //      String beginDate = sdf.format((new Date(cal.getTime().getTime() - 24L * 3600L * 1000L)));
      //      String endDate = sdf.format(cal.getTime());

      Map<String, Object> paramTrxNo = new HashMap<String, Object>();
      paramTrxNo.put("trxType", Constants.FIN_TASK_TRX_TYPE_3411);
      paramTrxNo.put("isSend", Constants.FIN_TASK_TRX_SEND_NEW);
      paramTrxNo.put("oneMonthFlag", "Y");
      //      paramTrxNo.put("beginDate", beginDate);
      //      paramTrxNo.put("endDate", endDate);
      List<SacOtrxInfo> trxList = oriTransactionManager.selectOriTransactionList(paramTrxNo);
      if (trxList != null && !trxList.isEmpty()) {
        oriTransactionManager.process3411TrxFinTask(trxList);
      } else {
        logger.info("无相关3411交易...");
      }
    } catch (Exception e) {
      logger.error("任务异常", e);
    }
    logger.info("结束执行处理3411记账任务...");

  }
}
