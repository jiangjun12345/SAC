package net.easipay.cbp.dao.impl;

import java.util.Map;
import net.easipay.cbp.dao.SacCusDailyBalanceDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacChnSettlement;
import org.springframework.stereotype.Repository;

@Repository("sacCusDailyBalanceDao")
public class SacCusDailyBalanceIbatis extends GenericDaoiBatis<SacChnSettlement, Long> implements SacCusDailyBalanceDao {
  /**
   * @Description: TODO(定时任务调用 定时任务调用客户每日余额)
   * @author sun (作者英文名称)
   * @date 2015-7-8 上午14:39:48
   * @version V1.0
   * @jdk v1.6
   * @tomcat v7.0
   */
  @Override
  public void sacCusDailyBalanceJobPro(Map param) {
      this.getSqlMapClientTemplate().queryForObject("calls_CusDailyBalance", param);
  }
  /**
   * @Description: TODO(定时任务调用 定时任务调用渠道每日余额)
   * @author sun (作者英文名称)
   * @date 2015-7-8 上午14:39:48
   * @version V1.0
   * @jdk v1.6
   * @tomcat v7.0
   */
  @Override
  public void sacBankDailyBalanceJobPro(Map param) {
    this.getSqlMapClientTemplate().queryForObject("calls_BankDailyBalance", param);
    
  }

}
