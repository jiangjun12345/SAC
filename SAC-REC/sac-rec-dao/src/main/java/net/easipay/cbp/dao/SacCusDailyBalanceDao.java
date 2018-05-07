package net.easipay.cbp.dao;

import java.util.Map;

public interface SacCusDailyBalanceDao {
  /**
   * @Description: TODO(定时任务调用客户每日余额)
   * @author sun (作者英文名称)
   * @date 2015-7-8 上午14:39:48
   * @version V1.0
   * @return
   * @jdk v1.6
   * @tomcat v7.0
   */
  public void sacCusDailyBalanceJobPro(Map param);
  
  /**
   * @Description: TODO(定时任务调用渠道每日余额)
   * @author sun (作者英文名称)
   * @date 2015-7-8 上午14:39:48
   * @version V1.0
   * @return
   * @jdk v1.6
   * @tomcat v7.0
   */
  public void sacBankDailyBalanceJobPro(Map param);
}
