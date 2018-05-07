package net.easipay.cbp.view.Job;

import java.util.ArrayList;
import java.util.List;

import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.model.SacRecBatch;
import net.easipay.cbp.service.RecBatchManager;

import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.n3r.quartz.glass.job.annotation.JobArgument;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "渠道对账长款任务", team = "对账系统", created = "2016-01-19")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessChannelRecLongTermJob implements Job {
	@Autowired
	RecBatchManager recBatchManager;

	private static final Logger logger = Logger
			.getLogger(ProcessChannelRecLongTermJob.class);
	@JobArgument(description = "仅限正向交易类型", required = true, sampleValues = {
			"true", "false" })
	private String onlyPositive;

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("开始执行渠道对账长款任务...");
		try {
			// 获取当天所有渠道对账批次
			List<SacRecBatch> recBatchList = new ArrayList<SacRecBatch>();
			// 第一步处理普通渠道对账
			recBatchList = recBatchManager
					.selectRecBatchForOneDay(Constants.REC_TYPE_CHANNEL);
			// 批次循环处理，以对账批次文件为准，与交易原始表进行核对，有差错插入对账差错表
			if (recBatchList != null && !recBatchList.isEmpty()) {
				for (SacRecBatch sacRecBatch : recBatchList) {
					try {
						recBatchManager.processChannelLongTerm(sacRecBatch,
								onlyPositive);
						logger.info("成功处理了对账批次ID="
								+ sacRecBatch.getRecBatchId() + ",此渠道对账批次包含了"
								+ sacRecBatch.getRecCount() + "笔对账明细交易！");
					} catch (Exception ex) {
						logger.error("渠道长款对账出现异常,批次ID="
								+ sacRecBatch.getRecBatchId() + ",异常信息：" + ex);
					}
				}
			}

		} catch (Exception e) {
			logger.error("任务异常", e);
		}
		logger.info("结束执行渠渠道对账长款任务...");
	}

}
