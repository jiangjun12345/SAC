/**
 * 
 */
package net.easipay.cbp.service.impl;

import java.util.Map;
import net.easipay.cbp.dao.SacCusDailyBalanceDao;
import net.easipay.cbp.model.SacChnSettlement;
import net.easipay.cbp.service.SacCusDailyBalanceManager;
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
@Service("sacCusDailyBalanceManager")
public class SacCusDailyBalanceManagerImpl extends GenericManagerImpl<SacChnSettlement, Long> implements SacCusDailyBalanceManager {
  @Autowired
  SacCusDailyBalanceDao sacCusDailyBalanceDao;
  private static final Logger logger = Logger.getLogger(SacCusDailyBalanceManagerImpl.class);

  @Override
  public void sacCusDailyBalanceJobPro(Map param) {
    // TODO Auto-generated method stub
     sacCusDailyBalanceDao.sacCusDailyBalanceJobPro(param);
  }

  @Override
  public void sacBankDailyBalanceJobPro(Map param) {
    // TODO Auto-generated method stub
    sacCusDailyBalanceDao.sacBankDailyBalanceJobPro(param);
  }

}
