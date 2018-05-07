package net.easipay.cbp.view.Job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.exception.SacException;
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

@GlassJob(description = "处理内部对账完成自动增补遗漏差错交易任务", team = "对账系统", created = "2016-01-19")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessAutoSupplementJob implements Job {
  @Autowired
  RecDiffManager recDiffManager;

  private static final Logger logger = Logger.getLogger(ProcessAutoSupplementJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    logger.info("开始执行处理内部对账完成自动增补遗漏差错交易任务...");
    try {
      // 获取所有需要补单的差错
      UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.INNER_DIFF_NOT_EXISTED);
      Map<String, Object> paramTrxNo = new HashMap<String, Object>();
      paramTrxNo.put("recDiffType", unifiedConfig.getDicValue());
      paramTrxNo.put("supplementFlag", Constants.REC_DIFF_SUPPLEMENT_NEW);
      paramTrxNo.put("status", Constants.REC_DIFF_DEALTYPE_NEW);
      paramTrxNo.put("orderByFlag", "1");
      //paramTrxNo.put("oneMonthFlag", "Y");
      paramTrxNo.put("oneDayFlag", "Y");
      
      // paramTrxNo.put("bothsupplementFlag", Boolean.TRUE);

      // 根据条件查询尚未增补交易的长款差错
      List<SacRecDifferences> recDiffList = recDiffManager.getRecDiffList(paramTrxNo);
      // 集中处理对账差错中长款增补交易
      if (recDiffList != null && !recDiffList.isEmpty()) {
        for (SacRecDifferences sacRecDifferences : recDiffList) {
          try {
            recDiffManager.processAutoSupplementTrxRecDiff(sacRecDifferences);
          } catch (SacException ex) {
            logger.error(ex);
          }

        }
      }
    } catch (Exception e) {
      logger.error("任务异常", e);
    }
    logger.info("结束执行处理内部对账完成自动增补遗漏差错交易任务...");
  }
}
