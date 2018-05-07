package net.easipay.cbp.dao.impl;

import java.util.Map;
import net.easipay.cbp.dao.SacChnSettlementDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacChnSettlement;
import org.springframework.stereotype.Repository;

@Repository("sacChnSettlementDao")
public class SacChnSettlementIbatis extends GenericDaoiBatis<SacChnSettlement, Long> implements SacChnSettlementDao {
  /**
   * @Description: TODO(定时任务调用 清分渠道应收明细)
   * @author sun (作者英文名称)
   * @date 2015-7-8 上午14:39:48
   * @version V1.0
   * @jdk v1.6
   * @tomcat v7.0
   */
  @Override
  public Map SacChnSettlementJobPro(Map param) {
    return (Map) this.getSqlMapClientTemplate().queryForObject("calls_SacChnSettlement", param);
  }

  @Override
  public void sacTrialBalancingJobPro() {
    // TODO Auto-generated method stub
      this.getSqlMapClientTemplate().queryForObject("calls_FinTrialBalance");
  }

}
