/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;
import net.easipay.cbp.model.SacRecBatch;
import net.easipay.cbp.service.base.GenericManager;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-4-2 下午12:29:24
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public interface RecBatchManager extends GenericManager<SacRecBatch, Long> {

  /**
   * 查询对账批次
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public List<SacRecBatch> selectRecBatchForOneDay(String recType) throws Exception;

  /**
   * 处理内部对账
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public void processInnerRec(SacRecBatch sacRecBatch) throws Exception;

  /**
   * 处理内部对账短款
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public void processInnerRecShortTerm(SacRecBatch sacRecBatch) throws Exception;

  /**
   * 处理渠道对账长款
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public void processChannelLongTerm(SacRecBatch sacRecBatch, String onlyPositive) throws Exception;

  /**
   * 处理渠道差错批次
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public void processChannelDiff(SacRecBatch sacRecBatch) throws Exception;

  /**
   * 处理短款
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public void processChannelShortTerm(String recType) throws Exception;
}
