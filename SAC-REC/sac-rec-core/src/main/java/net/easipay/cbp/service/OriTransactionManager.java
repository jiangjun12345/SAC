/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.form.FinTaskReceiveForm;
import net.easipay.cbp.model.form.SacRemittanceBatchIdForm;
import net.easipay.cbp.service.base.GenericManager;
import net.easipay.dsfc.ws.jws.JwsResult;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-4-2 下午12:29:24
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public interface OriTransactionManager extends GenericManager<SacOtrxInfo, Long> {

  /**
   * 查询原始交易,存在多条记录的情况
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public List<SacOtrxInfo> selectOriTransactionList(Map<String, Object> filterMap) throws Exception;

  /**
   * 查询原始交易,只存在一条记录的情况
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public SacOtrxInfo selectOriTransaction(Map<String, Object> filterMap) throws Exception;

  /**
   * 处理对账成功的交易记账
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public void processRecTrxFin(List<SacOtrxInfo> otrxList) throws Exception;

  /**
   * 获取所有成功对账的交易数目
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public Integer getSuccTrxCountForOneDay() throws Exception;

  /**
   * 分页方式获取当天所有成功对账的交易
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public List<SacOtrxInfo> getSplictSuccTrxForOneDay(Integer startPos, Integer endPos) throws Exception;

  /**
   * 处理3411分批记账
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public void process3411TrxFinTask(List<SacOtrxInfo> otrxList) throws Exception;

  /**
   * 处理3803分批记账
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public void process3803TrxFinTask(List<SacOtrxInfo> otrxList) throws Exception;

  /**
   * 获取3803分批交易
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public List<SacOtrxInfo> get3803TrxListFinTask(Integer startPos, Integer endPos) throws Exception;

  /**
   * 分页方式获取所有3803的交易数量
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public Integer get3803TrxCountForFinTask() throws Exception;
  
  /**
   * 根据客户(craccCardId)按天汇总 将货款运费金额  以及行邮税统计出来 
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public  List<Map<String, Object>> getAmountGroupByCus(Map<String,Object> queryMap);
  
  public  List<Map<String, Object>> getRefundAmountGroupByCus(Map<String,Object> queryMap);
  
  public JwsResult reconliciationAccountToFa(FinTaskReceiveForm form);
  
  /**
   * 查询未更新的付汇购汇批次对应信息
   * 
   * @param trx
   * @return
   * @throws Exception
   */
  public  List<Map<String, Object>> getSacTrxRemittance(Map<String,Object> queryMap);

public void processSendTrxRemittance(String batchId,
		List<SacRemittanceBatchIdForm> sacTrxRemittanceList);

}
