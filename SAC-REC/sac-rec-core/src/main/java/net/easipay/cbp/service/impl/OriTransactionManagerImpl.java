/**
 * 
 */
package net.easipay.cbp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.dao.OriTransactionDao;
import net.easipay.cbp.form.FinTasksForm;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.form.FinTaskReceiveForm;
import net.easipay.cbp.model.form.SacRemittanceBatchIdForm;
import net.easipay.cbp.service.OriTransactionManager;
import net.easipay.cbp.service.base.impl.GenericManagerImpl;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

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
@Service("oriTransactionManager")
public class OriTransactionManagerImpl extends GenericManagerImpl<SacOtrxInfo, Long> implements OriTransactionManager {
  @Autowired
  OriTransactionDao oriTransactionDao;

  private static final Logger logger = Logger.getLogger(OriTransactionManagerImpl.class);

  @Override
  public List<SacOtrxInfo> selectOriTransactionList(Map<String, Object> filterMap) throws Exception {
    List<SacOtrxInfo> otrxList = oriTransactionDao.getOriTransactionList(filterMap);
    if (otrxList != null && !otrxList.isEmpty()) {
      return otrxList;
    }
    return null;
  }

  @Override
  public SacOtrxInfo selectOriTransaction(Map<String, Object> filterMap) throws Exception {
    // TODO Auto-generated method stub
    SacOtrxInfo sacOtrxInfo = oriTransactionDao.getOriTransaction(filterMap);
    return sacOtrxInfo;
  }

  @Override
  public void processRecTrxFin(List<SacOtrxInfo> otrxList) throws Exception {
    // TODO Auto-generated method stub
    List<FinTasksForm> finTasksList = new ArrayList<FinTasksForm>();
    FinTasksForm finTasksForm=null;
    for (SacOtrxInfo otrxInfo : otrxList) {
      // 根据交易明细以及账务设计进行记账处理
      finTasksForm = new FinTasksForm();
      String trxSerialNo = otrxInfo.getTrxSerialNo();
      String trxState = Constants.REC_SUCC_FINTASK_STATUS;
      String trxCode = otrxInfo.getTrxType();

      finTasksForm.setTrxCode(trxCode);
      finTasksForm.setTrxSerialNo(trxSerialNo);
      finTasksForm.setTrxState(trxState);

      finTasksList.add(finTasksForm);
    }
    if (!finTasksList.isEmpty()) {
      JwsClient jwsClient = new JwsClient(Constants.JOIN_RULEFIN_TASK);
      jwsClient.putParam("finRuleTasks", finTasksList);
      JwsResult result = jwsClient.call();
      if (result.isSuccess()) {
        Map<String, Object> paramTrxNo=null;
        for (SacOtrxInfo otrxInfo : otrxList) {
          paramTrxNo = new HashMap<String, Object>();
          paramTrxNo.put("trxSerialNo", otrxInfo.getTrxSerialNo());
          // 根据交易流水号查询原始交易
          SacOtrxInfo sacOtrxInfo = oriTransactionDao.getOriTransaction(paramTrxNo);
          if (sacOtrxInfo != null) {
            sacOtrxInfo.setAccountStatus(Constants.TRANSACTION_ACCOUNT_SUCC);
            oriTransactionDao.update(sacOtrxInfo);
          } else {
            logger.debug("原始交易表中未找到记录,交易流水号=" + otrxInfo.getTrxSerialNo());
          }
        }
      } else {
        logger.debug("交易记账出现异常,错误码=" + result.getCode() + ",错误信息=" + result.getMessage());
      }
    }

  }

  @Override
  public Integer getSuccTrxCountForOneDay() throws Exception {
    Map<String, String> filterParm = setFilterCondition();
    Integer tcTransactionCount = oriTransactionDao.getSuccTrxCountForOneDay(filterParm);
    return tcTransactionCount;
  }

  @Override
  public Integer get3803TrxCountForFinTask() throws Exception {
    Map<String, String> filterParm = setFilterConditionFor3803FinTask();
    Integer tcTransactionCount = oriTransactionDao.getSuccTrxCountForOneDay(filterParm);
    return tcTransactionCount;
  }

  @Override
  public void process3803TrxFinTask(List<SacOtrxInfo> otrxList) throws Exception {
    this.processTrxFinTask(otrxList);
  }

  @Override
  public List<SacOtrxInfo> get3803TrxListFinTask(Integer startPos, Integer endPos) throws Exception {
    Map<String, String> filterParm = setFilterConditionFor3803FinTask();
    filterParm.put("start", String.valueOf(startPos));
    filterParm.put("end", String.valueOf(endPos));
    // 根据条件去查询原始交易
    List<SacOtrxInfo> otrxList = oriTransactionDao.getSplictTrxListForOneDay(filterParm);
    return otrxList;
  }

  private Map<String, String> setFilterConditionFor3803FinTask() {
    Map<String, String> paramTrx = new HashMap<String, String>();
    paramTrx.put("trxType", Constants.FIN_TASK_TRX_TYPE_3803);
    paramTrx.put("isSend", Constants.FIN_TASK_TRX_SEND_NEW);
    return paramTrx;
  }

  @Override
  public List<SacOtrxInfo> getSplictSuccTrxForOneDay(Integer startPos, Integer endPos) throws Exception {
    // TODO Auto-generated method stub
    Map<String, String> filterParm = setFilterCondition();
    filterParm.put("start", String.valueOf(startPos));
    filterParm.put("end", String.valueOf(endPos));
    // 根据条件去查询原始交易
    List<SacOtrxInfo> tcTransactionList = oriTransactionDao.getSplictTrxListForOneDay(filterParm);
    return tcTransactionList;
  }

  private Map<String, String> setFilterCondition() {
    // TODO Auto-generated method stub
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // Calendar cal = new GregorianCalendar();
    // cal.setTime(new Date());
    // cal.set(Calendar.HOUR_OF_DAY, 0);
    // cal.set(Calendar.MINUTE, 0);
    // cal.set(Calendar.SECOND, 0);
    // cal.set(Calendar.MILLISECOND, 0);

    // String beginDate = sdf.format((new Date(
    // cal.getTime().getTime() - 24L * 3600L * 1000L)));
    // String endDate = sdf.format(cal.getTime());
    Map<String, String> paramTrx = new HashMap<String, String>();
    paramTrx.put("innConFlag", Constants.REC_FLAG_SUCC);
    paramTrx.put("chaConFlag", Constants.REC_FLAG_SUCC);
    paramTrx.put("innConStatus", Constants.REC_STATUS_SUCC_FLAG);
    paramTrx.put("chaConStatus", Constants.REC_STATUS_SUCC_FLAG);
    paramTrx.put("trxState", Constants.TRANSACTION_SUCCESS_FLAG);
    paramTrx.put("accountStatus", Constants.TRANSACTION_ACCOUNT_NEW);
    // paramTrx.put("beginDate", beginDate);
    // paramTrx.put("endDate", endDate);
    // 未冲正的交易，才需要记账
    paramTrx.put("reversalStatus", Constants.TRANSACTION_REVERSAL_NEW);
    StringBuffer trxTypeBuff = new StringBuffer();
    // String trxTypeStr = trxTypeBuff.append("'")
    // .append(Constants.REC_FINISH_TRXCODE_PAYMENT).append("'")
    // .append(",").append("'")
    // .append(Constants.REC_FINISH_TRXCODE_SHIPPING1).append("'")
    // .append(",").append("'")
    // .append(Constants.REC_FINISH_TRXCODE_SHIPPING2).append("'")
    // .append(",").append("'")
    // .append(Constants.REC_FINISH_TRXCODE_SHIPPING3).append("'")
    // .append(",").append("'")
    // .append(Constants.REC_FINISH_TRXCODE_REFUND).append("'")
    // .toString();
    List<UnifiedConfig> listUnifiedConfig = UnifiedConfigSimple.getDicTypeConfig(Constants.REC_FINISH_TRXCODE_TYPE);
    if (listUnifiedConfig != null && !listUnifiedConfig.isEmpty()) {
      for (UnifiedConfig unifiedConfig : listUnifiedConfig) {
        trxTypeBuff.append("'").append(unifiedConfig.getDicValue()).append("'").append(",");
      }
      paramTrx.put("trxTypeGrp", trxTypeBuff.substring(0, trxTypeBuff.lastIndexOf(",")).toString());
    }
    return paramTrx;
  }

  @Override
  public void process3411TrxFinTask(List<SacOtrxInfo> otrxList) throws Exception {
    // TODO Auto-generated method stub
    List<SacOtrxInfo> batchOtrxList = null;
    Map<String, List<SacOtrxInfo>> batchMap = new HashMap<String, List<SacOtrxInfo>>();
    for (SacOtrxInfo sacOtrxInfo : otrxList) {
      String trxBatchNo = sacOtrxInfo.getTrxBatchNo();
      if (batchMap.keySet().contains(trxBatchNo)) {
        batchMap.get(trxBatchNo).add(sacOtrxInfo);
      } else {
        batchOtrxList = new ArrayList<SacOtrxInfo>();
        batchOtrxList.add(sacOtrxInfo);
        batchMap.put(trxBatchNo, batchOtrxList);
      }
    }
    if (batchMap.size() > 0) {
      for (Map.Entry<String, List<SacOtrxInfo>> entry : batchMap.entrySet()) {
        String keyBatchNo = entry.getKey();
        List<SacOtrxInfo> trxList = entry.getValue();
        //第一步处理3411的记账
        try {
          this.processTrxFinTask(trxList);
        } catch (Exception ex) {
          logger.error("3411,批次流水号=" + keyBatchNo + ",总笔数=" + trxList.size() + "记账失败,错误信息=" + ex.getMessage());
          continue;
        }
        
        Thread.sleep(3000);
        
        //第二步处理3803的记账
        for(SacOtrxInfo sacOtrx3411:trxList){
          String trxSerialNo3411 = sacOtrx3411.getTrxSerialNo();
          Map<String, Object> paramTrxSpec = new HashMap<String, Object>();
          paramTrxSpec.put("trxBatchNo", trxSerialNo3411);
          paramTrxSpec.put("trxType", Constants.FIN_TASK_TRX_TYPE_3803);
          paramTrxSpec.put("isSend", Constants.FIN_TASK_TRX_SEND_NEW);
          paramTrxSpec.put("oneMonthFlag", "Y");
          // 根据交易流水号查询原始交易批次号
          List<SacOtrxInfo> sacOtrxInfoBatch3803 = oriTransactionDao.getOriTransactionList(paramTrxSpec);
          if(sacOtrxInfoBatch3803!=null&&!sacOtrxInfoBatch3803.isEmpty()){
            try {
              this.processTrxFinTask(sacOtrxInfoBatch3803);
            } catch (Exception ex) {
              logger.error("3803,批次流水号=" + trxSerialNo3411 + ",总笔数=" + sacOtrxInfoBatch3803.size() + "记账失败,错误信息=" + ex.getMessage());
              continue;
            }
         }
        }
      }
    }
  }

  private void processTrxFinTask(List<SacOtrxInfo> otrxList) {
    List<FinTasksForm> finTasksList = new ArrayList<FinTasksForm>();
    FinTasksForm finTasksForm=null;
    for (SacOtrxInfo otrxInfo : otrxList) {
      // 根据交易明细以及账务设计进行记账处理
      finTasksForm = new FinTasksForm();
      String trxSerialNo = otrxInfo.getTrxSerialNo();
      String trxState = otrxInfo.getTrxState();
      String trxCode = otrxInfo.getTrxType();

      finTasksForm.setTrxCode(trxCode);
      finTasksForm.setTrxSerialNo(trxSerialNo);
      finTasksForm.setTrxState(trxState);

      finTasksList.add(finTasksForm);
    }
    if (!finTasksList.isEmpty()) {
      JwsClient jwsClient = new JwsClient(Constants.JOIN_RULEFIN_TASK);
      jwsClient.putParam("finRuleTasks", finTasksList);
      JwsResult result = jwsClient.call();
      if (result.isSuccess()) {
        this.updateOtrxStatusInfo(otrxList);
        if (Constants.FIN_TASK_TRX_TYPE_3411.equals(otrxList.get(0).getTrxType())) {
          logger.debug("3411,批次流水号=" + otrxList.get(0).getTrxBatchNo() + ",总笔数=" + otrxList.size() + "记账成功");
        } else if (Constants.FIN_TASK_TRX_TYPE_3803.equals(otrxList.get(0).getTrxType())) {
          logger.debug("3803,批次流水号=" + otrxList.get(0).getTrxBatchNo() + ",总笔数=" + otrxList.size() + "记账成功");
        }
      } else {
        if (Constants.FIN_TASK_TRX_TYPE_3411.equals(otrxList.get(0).getTrxType())) {
          logger.debug("3411,批次流水号" + otrxList.get(0).getTrxBatchNo() + ",总笔数=" + otrxList.size() + "记账失败,错误码=" + result.getCode() + ",错误信息=" + result.getMessage());
        } else if (Constants.FIN_TASK_TRX_TYPE_3803.equals(otrxList.get(0).getTrxType())) {
          logger.debug("3803,批次流水号" + otrxList.get(0).getTrxBatchNo() + ",总笔数=" + otrxList.size() + "记账失败,错误码=" + result.getCode() + ",错误信息=" + result.getMessage());
        }
      }
    }
  }

  private void updateOtrxStatusInfo(List<SacOtrxInfo> otrxList) {
    for (SacOtrxInfo otrxInfo : otrxList) {
      otrxInfo.setIsSend(Constants.FIN_TASK_TRX_SEND_SUCC);
      oriTransactionDao.updateOriTransactionStatus(otrxInfo);
    }
  }

  @Override
  public List<Map<String, Object>> getAmountGroupByCus(Map<String, Object> queryMap) {
    return oriTransactionDao.getAmountGroupByCus(queryMap);
  }

  @Override
  public List<Map<String, Object>> getRefundAmountGroupByCus(Map<String, Object> queryMap) {
    return oriTransactionDao.getRefundAmountGroupByCus(queryMap);
  }

  @Override
  public JwsResult reconliciationAccountToFa(FinTaskReceiveForm form) {
    JwsClient client = new JwsClient(Constants.RECONLICIATION_ACCOUNT);
    client.putAllParam(form);
    JwsResult rst = client.call();
    return rst;
  }

@Override
public List<Map<String, Object>> getSacTrxRemittance(Map<String, Object> queryMap) {
	return oriTransactionDao.getSacTrxRemittance(queryMap);
}

@Override
public void processSendTrxRemittance(String batchId,
		List<SacRemittanceBatchIdForm> sacTrxRemittanceList) {

	try {
		JwsClient jwsClient = new JwsClient(Constants.UPDATE_REMITTANCE);
		JwsResult rst = jwsClient.putParam("sacRemittanceBatchIdForms", sacTrxRemittanceList).call();
		
		if(ConstantParams.RTN_CODE_SUCCESSS.equals(rst.getCode())){
			//成功则更新处理标志为已处理

			Map<String,Object> updateMap = new HashMap<String,Object>();
			updateMap.put("batchId", batchId);
			updateMap.put("updateTime",new java.util.Date());
			updateMap.put("dealFlag", "S");
			oriTransactionDao.updateSacTrxRemittanceDealFlag(updateMap);
		}
		
	} catch (Exception e) {
		log.error("#############更新付汇批次号失败：#############",e);
	}
    
	
  }

}
