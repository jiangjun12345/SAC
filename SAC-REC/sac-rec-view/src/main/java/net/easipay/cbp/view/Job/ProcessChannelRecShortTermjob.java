package net.easipay.cbp.view.Job;

import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.service.RecBatchManager;

import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "渠道对账短款任务", team = "对账系统", created = "2016-01-19")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessChannelRecShortTermjob implements Job
{
    @Autowired
    RecBatchManager recBatchManager;

    private static final Logger logger = Logger.getLogger(ProcessChannelRecShortTermjob.class);

    /**
     * 渠道对账短款
     * 
     * @param request
     * @param response
     * @param trxJson
     * @throws Exception
     */

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
	// 第三步处理渠道短款
	logger.info("开始执行渠道对账短款任务...");
	try {
	    recBatchManager.processChannelShortTerm(Constants.REC_TYPE_CHANNEL);
	} catch ( Exception ex ) {
	    logger.error("渠道短款对账出现异常:" + ex);
	}
	logger.info("结束执行渠道对账短款任务...");
    }

}
