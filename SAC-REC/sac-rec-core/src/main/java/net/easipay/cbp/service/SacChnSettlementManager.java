package net.easipay.cbp.service;

import java.util.Map;

public interface SacChnSettlementManager {
  /**
   * @Description: TODO(定时任务调用 清分渠道应收明细)
   * @author sun (作者英文名称)
   * @date 2015-7-8 上午14:39:48
   * @version V1.0
   * @return
   * @jdk v1.6
   * @tomcat v7.0
   */
  public Map sacChnSettlementJobPro(Map param);
  
  /**
   * @Description: TODO(定时任务调用 试算平衡)
   * @author sun (作者英文名称)
   * @date 2015-7-8 上午14:39:48
   * @version V1.0
   * @return
   * @jdk v1.6
   * @tomcat v7.0
   */
  public void sacTrialBalancingJobPro();
}
