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

@GlassJob(description = "渠道对账差错对账任务", team = "对账系统", created = "2016-01-19")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessChannelRecDiffJob implements Job
{
    @Autowired
    RecBatchManager recBatchManager;
    
    private static final Logger logger = Logger.getLogger(ProcessChannelRecDiffJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
	logger.info("开始执行渠道对账差错对账任务...");
	try {
	    // 获取当天所有渠道对账批次
	    List<SacRecBatch> recBatchList = new ArrayList<SacRecBatch>();

	    // 第二步处理渠道差错对账
	    recBatchList = recBatchManager.selectRecBatchForOneDay(Constants.REC_TYPE_CHANNEL_DIFF);

	    // 批次循环处理，并查询出交易原始表中这个渠道所有的交易，将不存在于这个差错批次中的原始交易标注为已经对账，并将这个渠道差错批次数据插入对账差错表
	    if (recBatchList != null && !recBatchList.isEmpty()) {
		for (SacRecBatch sacRecBatch : recBatchList) {
		    try {
			recBatchManager.processChannelDiff(sacRecBatch);
			logger.info("成功处理了对账批次ID=" + sacRecBatch.getRecBatchId() + ",此渠道差错对账批次包含了" + sacRecBatch.getRecCount() + "笔对账明细交易！");
		    } catch ( Exception ex ) {
			logger.error("渠道差错对账出现异常,批次ID=" + sacRecBatch.getRecBatchId() + ",异常信息：" + ex);
		    }
		}
	    }

	} catch ( Exception e ) {
	    logger.error("任务异常", e);
	}
	 logger.info("结束执行渠道对账差错对账任务...");

    }

}
