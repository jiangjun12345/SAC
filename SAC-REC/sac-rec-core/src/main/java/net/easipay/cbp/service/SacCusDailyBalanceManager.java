package net.easipay.cbp.service;

import java.util.Map;

public interface SacCusDailyBalanceManager {
  /**
   * @Description: TODO(定时任务调用 客户每日余额)
   * @author sun (作者英文名称)
   * @date 2015-7-8 上午14:39:48
   * @version V1.0
   * @return
   * @jdk v1.6
   * @tomcat v7.0
   */
  public void sacCusDailyBalanceJobPro(Map param);
  
  
  /**
   * @Description: TODO(定时任务调用 渠道每日余额)
   * @author sun (作者英文名称)
   * @date 2015-7-8 上午14:39:48
   * @version V1.0
   * @return
   * @jdk v1.6
   * @tomcat v7.0
   */
  public void sacBankDailyBalanceJobPro(Map param);
}
