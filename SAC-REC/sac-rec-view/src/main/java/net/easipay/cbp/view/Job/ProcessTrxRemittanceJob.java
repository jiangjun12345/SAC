package net.easipay.cbp.view.Job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.easipay.cbp.model.form.SacRemittanceBatchIdForm;
import net.easipay.cbp.service.OriTransactionManager;
import net.easipay.cbp.util.BeanUtils;
import net.easipay.cbp.util.DateUtil;

import org.apache.log4j.Logger;
import org.n3r.quartz.glass.job.annotation.GlassJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

@GlassJob(description = "定时更新付汇批次号任务", team = "对账系统", created = "2016-11-16")
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ProcessTrxRemittanceJob implements Job {
  @Autowired
  OriTransactionManager oriTransactionManager;

  private static final Logger logger = Logger.getLogger(ProcessTrxRemittanceJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    logger.info("开始执行定时更新付汇批次号任务...");
    try {
      Map<String,Object> queryMap = new HashMap<String,Object>();
      
      String startDate = DateUtil.formatCurrentDate(Calendar.DATE, -7, "yyyyMMdd");
  	
  	  String endDate = DateUtil.formatCurrentDate(Calendar.DATE, -1, "yyyyMMdd");
  	  
      queryMap.put("dealFlag", "N");//查询未处理的数据
      queryMap.put("startDate",startDate);
      queryMap.put("endDate", endDate);
      
      List<Map<String, Object>> sacTrxRemittanceList = oriTransactionManager.getSacTrxRemittance(queryMap);
      
      Map<String,List<SacRemittanceBatchIdForm>> batchMap = new HashMap<String, List<SacRemittanceBatchIdForm>>();
      
      Iterator<Map<String, Object>> it = sacTrxRemittanceList.iterator();
      while(it.hasNext()){
    	  Map<String, Object> next = it.next();
    	  String batchId = (String)next.get("batchId");
    	  List<SacRemittanceBatchIdForm> list = batchMap.get(batchId);
    	  if(list==null){
    		  list = new ArrayList<SacRemittanceBatchIdForm>();
    		  batchMap.put(batchId, list);
    	  }
    	  SacRemittanceBatchIdForm form = new SacRemittanceBatchIdForm();
    	  BeanUtils.transMap2Bean(next, form);
    	  list.add(form);
      }
      
      if(batchMap!=null&&batchMap.size()>0){

          Iterator<Entry<String, List<SacRemittanceBatchIdForm>>> its = batchMap.entrySet().iterator();
          while(its.hasNext()){
        	  Entry<String, List<SacRemittanceBatchIdForm>> next = its.next();
        	  String key = next.getKey();
        	  List<SacRemittanceBatchIdForm> value = next.getValue();
    		  oriTransactionManager.processSendTrxRemittance(key,value);
    	  }
      
      }
      
    } catch (Exception e) {
      logger.error("任务异常", e);
    }
    logger.info("结束执行定时更新付汇批次号任务...");

  }
}
