package net.easipay.cbp.view.Job;

import java.util.HashMap;
import net.easipay.cbp.service.SacChnSettlementManager;
import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "处理清分渠道应收明细处理任务", team = "清分系统", created = "2016-01-19")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessChnSettlementJob implements Job {
  @Autowired
  SacChnSettlementManager sacChnSettlementManager;

  private static final Logger logger = Logger.getLogger(ProcessChnSettlementJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    logger.info("开始执行清分渠道应收明细处理任务...");
    try {
      //清算日期
      String psacDate = "";
      Integer err_num = new Integer(1);
      HashMap<String, Object> param = new HashMap<String, Object>();
      param.put("p_sac_date", psacDate);
      param.put("err_num", err_num);
      param.put("err_msg", "1");
      sacChnSettlementManager.sacChnSettlementJobPro(param);
    } catch (Exception e) {
      logger.error("任务异常", e);
    }
    logger.info("结束执行清分渠道应收明细处理任务...");
  }

}
