package net.easipay.cbp.view.Job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.model.SacRecDifferences;
import net.easipay.cbp.service.RecDiffManager;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "处理长款手工补单任务", team = "对账系统", created = "2016-01-19")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessReplenishmentsJob implements Job {
  @Autowired
  RecDiffManager recDiffManager;

  private static final Logger logger = Logger.getLogger(ProcessReplenishmentsJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    logger.info("开始执行处理长款手工补单任务...");
    try {
      // 获取所有需要补单的差错
      UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REC_DIFF_NOT_EXISTED);
      Map<String, Object> paramTrxNo = new HashMap<String, Object>();
      paramTrxNo.put("recDiffType", unifiedConfig.getDicValue());
      paramTrxNo.put("notSuccStatus", Constants.REC_DIFF_DEALTYPE_SUCC);
      paramTrxNo.put("oneMonthFlag", "Y");
      // 根据交易流水号查询原始交易
      List<SacRecDifferences> recDiffList = recDiffManager.getRecDiffList(paramTrxNo);
      // 集中处理补单对账差错
      recDiffManager.processReplenishmentsRecDiff(recDiffList);
    } catch (Exception e) {
      logger.error("任务异常", e);
    }
    logger.info("结束执行处理长款手工补单任务...");
  }

}
