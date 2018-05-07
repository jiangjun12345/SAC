/**
 * 
 */
package net.easipay.cbp.service.impl;

import java.util.Map;
import net.easipay.cbp.dao.SacChnSettlementDao;
import net.easipay.cbp.model.SacChnSettlement;
import net.easipay.cbp.service.SacChnSettlementManager;
import net.easipay.cbp.service.base.impl.GenericManagerImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-4-2 下午12:30:57
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */
@Service("sacChnSettlementManager")
public class SacChnSettlementManagerImpl extends GenericManagerImpl<SacChnSettlement, Long> implements SacChnSettlementManager {
  @Autowired
  SacChnSettlementDao sacChnSettlementDao;
  private static final Logger logger = Logger.getLogger(SacChnSettlementManagerImpl.class);

  @Override
  public Map sacChnSettlementJobPro(Map param) {
    // TODO Auto-generated method stub
    return sacChnSettlementDao.SacChnSettlementJobPro(param);
  }

  @Override
  public void sacTrialBalancingJobPro() {
    // TODO Auto-generated method stub
     sacChnSettlementDao.sacTrialBalancingJobPro();
  }

}
