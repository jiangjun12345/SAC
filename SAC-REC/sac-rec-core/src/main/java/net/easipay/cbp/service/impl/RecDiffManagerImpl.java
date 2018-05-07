/**
 * 
 */
package net.easipay.cbp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.constant.ReturnCode;
import net.easipay.cbp.dao.OriTransactionDao;
import net.easipay.cbp.dao.OriTrxDetailDao;
import net.easipay.cbp.dao.RecDiffDao;
import net.easipay.cbp.exception.SacException;
import net.easipay.cbp.form.AccountNoticeForm;
import net.easipay.cbp.form.FinTasksForm;
import net.easipay.cbp.form.UpdateSacTransationForm;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.SacRecDifferences;
import net.easipay.cbp.model.SacTrxDetail;
import net.easipay.cbp.service.RecDiffManager;
import net.easipay.cbp.service.base.impl.GenericManagerImpl;
import net.easipay.cbp.util.UrlUtil;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;
import org.apache.commons.lang.StringUtils;
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
@Service("recDiffManager")
public class RecDiffManagerImpl extends GenericManagerImpl<SacRecDifferences, Long> implements RecDiffManager {
  @Autowired
  RecDiffDao recDiffDao;
  @Autowired
  OriTransactionDao oriTransactionDao;
  @Autowired
  OriTrxDetailDao oriTrxDetailDao;
  private String B2B_SUPPLEMENT_NOTIFY;
  private String B2C_SUPPLEMENT_NOTIFY_AC;
  private String B2C_SUPPLEMENT_NOTIFY_ORDER;
  private String B2B_GFH_SUPPLEMENT_NOTIFY;

  public void setB2B_SUPPLEMENT_NOTIFY(String b2b_SUPPLEMENT_NOTIFY) {
    B2B_SUPPLEMENT_NOTIFY = b2b_SUPPLEMENT_NOTIFY;
  }

  public void setB2C_SUPPLEMENT_NOTIFY_AC(String b2c_SUPPLEMENT_NOTIFY_AC) {
    B2C_SUPPLEMENT_NOTIFY_AC = b2c_SUPPLEMENT_NOTIFY_AC;
  }

  public void setB2C_SUPPLEMENT_NOTIFY_ORDER(String b2c_SUPPLEMENT_NOTIFY_ORDER) {
    B2C_SUPPLEMENT_NOTIFY_ORDER = b2c_SUPPLEMENT_NOTIFY_ORDER;
  }
  public String getB2B_GFH_SUPPLEMENT_NOTIFY() {
    return B2B_GFH_SUPPLEMENT_NOTIFY;
  }

  public void setB2B_GFH_SUPPLEMENT_NOTIFY(String b2b_GFH_SUPPLEMENT_NOTIFY) {
    B2B_GFH_SUPPLEMENT_NOTIFY = b2b_GFH_SUPPLEMENT_NOTIFY;
  }
  

  private static final Logger logger = Logger.getLogger(RecDiffManagerImpl.class);

  @Override
  public SacRecDifferences getRecDiff(Map<String, Object> param) {
    // TODO Auto-generated method stub
    SacRecDifferences sacRecDifferences = recDiffDao.getRecDiff(param);
    return sacRecDifferences;
  }

  @Override
  public List<SacRecDifferences> getRecDiffList(Map<String, Object> param) {
    // TODO Auto-generated method stub
    List<SacRecDifferences> sacRecDetaillist = recDiffDao.getRecDiffList(param);
    return sacRecDetaillist;

  }

  @Override
  public void insertRecDiff(SacRecDifferences sacRecDifferences) {
    // TODO Auto-generated method stub
    recDiffDao.insertRecDiff(sacRecDifferences);
  }

  @Override
  public void processInnerRecDiff(List<SacRecDifferences> recDiffList) throws Exception {
    // TODO Auto-generated method stub
    if (recDiffList != null && !recDiffList.isEmpty()) {
      for (SacRecDifferences sacRecDifferences : recDiffList) {
        this.noticeAccountSysInnerRec(sacRecDifferences);
      }
    }
  }

  @Override
  public void processReplenishmentsRecDiff(List<SacRecDifferences> recDiffList) throws Exception {
    // TODO Auto-generated method stub
    List<SacOtrxInfo> otrxList = new ArrayList<SacOtrxInfo>();
    if (recDiffList != null && !recDiffList.isEmpty()) {
      Map<String, Object> paramTrxNo=null;
      for (SacRecDifferences sacRecDifferences : recDiffList) {
        String eTrxSerialNo = sacRecDifferences.getBankSerialNo();
        paramTrxNo = new HashMap<String, Object>();
        paramTrxNo.put("etrxSerialNo", eTrxSerialNo);
        SacOtrxInfo sacOtrxInfo = oriTransactionDao.getOriTransaction(paramTrxNo);
        if (sacOtrxInfo != null) {
          sacOtrxInfo.setRecDiffId(sacRecDifferences.getId());
          otrxList.add(sacOtrxInfo);
        }
      }
      if (!otrxList.isEmpty()) {
        this.processTrxFinList(otrxList);
      }
    }

  }

  @Override
  public void processAutoSupplementTrxRecDiff(SacRecDifferences sacRecDifferences) throws Exception {
    // TODO Auto-generated method stub
    String trxSerialNo = sacRecDifferences.getTrxSerialNo();
    String trxCode = sacRecDifferences.getTrxCode();
    String payconType = sacRecDifferences.getPayconType();
    Date recStartDate = sacRecDifferences.getRecStartDate();
    boolean flag = 
        "3204".equals(trxCode)||"3202".equals(trxCode)||
        "3255".equals(trxCode)||"3411".equals(trxCode)||
        "3803".equals(trxCode)||"1705".equals(trxCode)||
        "3423".equals(trxCode)||"3412".equals(trxCode);
    String url = "";
    if (Constants.PAY_CONTYPE_B2B.equals(payconType)) {
      // B2B
      //UnifiedConfig dicCodeConfig = UnifiedConfigSimple.getDicCodeConfig("254");
      if(flag){
        //行邮税  3204 3202 3255  购付汇 3411 3803 1705 3423 3412 TODO
        //B2B
        url = B2B_GFH_SUPPLEMENT_NOTIFY;
      }else{
        //B2B
        url = B2B_SUPPLEMENT_NOTIFY;
      }
    
    } else if (Constants.PAY_CONTYPE_B2C.equals(payconType)) {
      // B2C
      if ("3204".equals(trxCode)) {
        // 调用账务中心接口
        //UnifiedConfig dicCodeConfig = UnifiedConfigSimple.getDicCodeConfig("253");
        url = B2C_SUPPLEMENT_NOTIFY_AC;
        // url = "http://10.68.30.91:8080/ACCOUNTS-CENTER1.1.1/tracenter/transferTransaction.do";
      } else if ("3302".equals(trxCode) || "3303".equals(trxCode)) {
        // 调用收单接口
        // 调用账务中心接口
        // UnifiedConfig dicCodeConfig = UnifiedConfigSimple.getDicCodeConfig("252");
        url = B2C_SUPPLEMENT_NOTIFY_ORDER;
        // url = "http://10.68.30.91:8080/ORDER-RECEIVER/ordinary/transferTransaction.do";
      }
    }
    if (StringUtils.isNotBlank(url)) {
      Map<String, String> params = new HashMap<String, String>();
      params.put("trxSerialNo", trxSerialNo);
      params.put("trxCode", trxCode);
      String response = UrlUtil.methodPost(url, params);
      if (response!=null&&(response.contains("000000") || response.contains("success"))) {
        logger.info("内部对账原交易表不在的差错ID=" + sacRecDifferences.getId() + "补单通知成功");
      } else {
        throw new SacException("999999", "内部对账原交易表不在的差错ID=" + sacRecDifferences.getId() + "补单通知失败(" + response + ")");
      }
    }

  }

  @Override
  public void processUpdateStateAutoSupplementTrxRecDiff(SacRecDifferences sacRecDifferences) throws Exception {
    // TODO Auto-generated method stub
    String trxSerialNo = sacRecDifferences.getTrxSerialNo();

    // 更新原始交易表中此条交易的对账标识
    Map<String, Object> paramTrxNo = new HashMap<String, Object>();
    paramTrxNo.put("trxSerialNo", trxSerialNo);
    paramTrxNo.put("oneMonthFlag", "Y");
    // 根据交易流水号查询原始交易
    SacOtrxInfo sacOtrxInfo = oriTransactionDao.getOriTransaction(paramTrxNo);

    if (sacOtrxInfo != null) {
      this.updateOriTrxData(sacOtrxInfo, Boolean.FALSE);
      // 更新对账差错表中此条交易的对账状态和相关数据
      String dealOper = Constants.REC_DIFF_DEALOPER_SYS;
      String dealType = Constants.REC_DIFF_DEALSTYLE_SYS;
      SacRecDifferences uSacRecDifferences = new SacRecDifferences();
      uSacRecDifferences.setId(sacRecDifferences.getId());
      uSacRecDifferences.setSupplementFlag(Constants.REC_DIFF_SUPPLEMENT_SUCC);// 修改补单标志为已补单
      uSacRecDifferences.setStatus(Constants.REC_DIFF_DEALTYPE_SUCC);//修改差错状态为成功
      uSacRecDifferences.setDealOper(dealOper);
      uSacRecDifferences.setDealType(dealType);
      uSacRecDifferences.setUpdateTime(new Date());
      recDiffDao.updateRecDiff(uSacRecDifferences);
    }

  }

  private void processTrxFinList(List<SacOtrxInfo> otrxList) throws Exception {
    // TODO Auto-generated method stub
    List<FinTasksForm> finTasksList = new ArrayList<FinTasksForm>();
    for (SacOtrxInfo otrxInfo : otrxList) {
      // 根据交易明细以及账务设计进行记账处理
      FinTasksForm finTasksForm = new FinTasksForm();
      String trxSerialNo = otrxInfo.getTrxSerialNo();
      String trxState = Constants.REC_SUCC_FINTASK_STATUS;
      String trxCode = otrxInfo.getTrxType();
      finTasksForm.setTrxCode(trxCode);
      finTasksForm.setTrxSerialNo(trxSerialNo);
      finTasksForm.setTrxState(trxState);
      finTasksList.add(finTasksForm);
      finTasksForm = new FinTasksForm();
      trxState = Constants.REC_SUCC_BLEND_FINTASK_STATUS;
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
        Map<String, Object> paramTrxNo =null;
        Map<String, Object> param=null;
        for (SacOtrxInfo otrxInfo : otrxList) {
          paramTrxNo = new HashMap<String, Object>();
          paramTrxNo.put("trxSerialNo", otrxInfo.getTrxSerialNo());
          // 根据交易流水号查询原始交易
          SacOtrxInfo sacOtrxInfo = oriTransactionDao.getOriTransaction(paramTrxNo);
          if (sacOtrxInfo != null) {
            String trxSerialNo = sacOtrxInfo.getTrxSerialNo();
            this.updateOriTrxDataForReplenishments(sacOtrxInfo, Boolean.TRUE);
            // 更新交易明细表
            this.updateChannelOriTrxDetailDataForReplenishments(trxSerialNo);
            // 更新对账差错表中此条交易的对账状态和相关数据
            String dealOper = Constants.REC_DIFF_DEALOPER_SYS;
            String dealType = Constants.REC_DIFF_DEALSTYLE_SYS;
            param = new HashMap<String, Object>();
            param.put("id", otrxInfo.getRecDiffId());
            SacRecDifferences recDiff = recDiffDao.getRecDiff(param);
            this.updateRecDiffDataForReplenishments(recDiff, trxSerialNo, dealOper, dealType);
          } else {
            logger.debug("原始交易表中未找到记录,交易流水号=" + otrxInfo.getTrxSerialNo());
          }
        }
      } else {
        logger.debug("交易记账出现异常,错误码=" + result.getCode() + ",错误信息=" + result.getMessage());
      }
    }

  }

  private void noticeAccountSysInnerRec(SacRecDifferences recDiff) throws Exception {
    // 根据差错类型，调用账务系统接口，更新差错数据
    String diffType = recDiff.getRecDiffType();
    String trxSerialNo = recDiff.getTrxSerialNo();
    Date trxSuccTime = recDiff.getTrxTime();
    String dealOper = Constants.REC_DIFF_DEALOPER_SYS;
    String dealType = Constants.REC_DIFF_DEALSTYLE_SYS;
    UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.INNER_DIFF_INCONSISTENT);
    if (unifiedConfig.getDicValue().equals(diffType)) {
      JwsClient jwsClient = new JwsClient(Constants.UPDATE_SACTRANSATION_DETAIL);
      UpdateSacTransationForm rnTrx = new UpdateSacTransationForm();
      rnTrx.setEtrxSerialNo(recDiff.getBankSerialNo());
      rnTrx.setTrxState((Constants.TRANSACTION_SUCCESS_FLAG));
      rnTrx.setTrxSerialNo(recDiff.getTrxSerialNo());
      rnTrx.setTrxSuccTime(trxSuccTime);
      JwsResult result = jwsClient.putAllParam(rnTrx).call(Boolean.TRUE);
      if (ReturnCode.RETURN_SUCCESS_CODE.equals(result.getCode())) {
        // 临时解决方案
        // if (true) {
        // 更新原始交易表中此条交易的对账标识
        Map<String, Object> paramTrxNo = new HashMap<String, Object>();
        paramTrxNo.put("trxSerialNo", trxSerialNo);
        // 根据交易流水号查询原始交易
        SacOtrxInfo sacOtrxInfo = oriTransactionDao.getOriTransaction(paramTrxNo);
        this.updateOriTrxData(sacOtrxInfo, Boolean.FALSE);
        // 更新对账差错表中此条交易的对账状态和相关数据
        this.updateRecDiffData(recDiff, null, Boolean.FALSE, dealOper, dealType);
      }
    }
  }

  @Override
  public void processChannelRecDiff(AccountNoticeForm noticeform) throws Exception {
    Map<String, Object> paramTrxNo = new HashMap<String, Object>();
    String oriTrxNo = noticeform.getTrxSerialNo();
    String eTrxNo = noticeform.getBankSerialNo();
    String recDetailId = noticeform.getRecDetailId();
    // paramTrxNo.put("recDiffType", Constants.REC_DIFF_TYPE_LONG);
    if (oriTrxNo != null && !"".equals(oriTrxNo)) {
      paramTrxNo.put("trxSerialNo", oriTrxNo);
    }
    if (eTrxNo != null && !"".equals(eTrxNo)) {
      paramTrxNo.put("bankSerialNo", eTrxNo);
    }
    if (recDetailId != null && !"".equals(recDetailId)) {
      paramTrxNo.put("recDetailId", noticeform.getRecDetailId());
    }

    // 根据交易流水号查询原始交易
    List<SacRecDifferences> sacRecDetaillist = recDiffDao.getRecDiffList(paramTrxNo);
    if (sacRecDetaillist != null && !sacRecDetaillist.isEmpty()) {
      for (SacRecDifferences recDiff : sacRecDetaillist) {
        // 补单交易需要根据外部流水号查找交易流水号
        if (eTrxNo != null && !"".equals(eTrxNo) && (oriTrxNo == null || "".equals(oriTrxNo))) {
          Map<String, Object> paramTrx = new HashMap<String, Object>();
          paramTrx.put("etrxSerialNo", eTrxNo);
          // 根据交易流水号查询原始交易
          SacOtrxInfo sacOtrxInfo = oriTransactionDao.getOriTransaction(paramTrx);
          // 渠道对账中，使用交易原始表中的交易流水号更新对账差错表交易流水号
          String trxSerialNo = sacOtrxInfo.getTrxSerialNo();
          recDiff.setTrxSerialNo(trxSerialNo);
          recDiffDao.update(recDiff);
        }
        this.noticeAccountSysChannelRec(recDiff, noticeform);
      }
    }
  }

  private void noticeAccountSysChannelRec(SacRecDifferences recDiff, AccountNoticeForm noticeform) throws Exception {
    // 根据差错类型，调用账务系统接口，更新差错数据
    String diffType = recDiff.getRecDiffType();
    String eTrxSerialNo = recDiff.getBankSerialNo();
    Date trxSuccTime = recDiff.getTrxTime();
    // String dealOper = noticeform.getDealOper();
    // String dealType = noticeform.getDealType();
    // if (Constants.REC_DIFF_TYPE_LONG.equals(diffType)) {
    JwsClient jwsClient = new JwsClient(Constants.UPDATE_SACTRANSATION_DETAIL);
    UpdateSacTransationForm rnTrx = new UpdateSacTransationForm();
    rnTrx.setEtrxSerialNo(eTrxSerialNo);
    rnTrx.setTrxState(Constants.TRANSACTION_SUCCESS_FLAG);
    rnTrx.setTrxSerialNo(recDiff.getTrxSerialNo());
    rnTrx.setTrxSuccTime(trxSuccTime);
    JwsResult result = jwsClient.putAllParam(rnTrx).call();
    if (ReturnCode.RETURN_SUCCESS_CODE.equals(result.getCode())) {
      // 临时解决方案
      // if (true) {
      // 更新原始交易表中此条交易的对账标识
      Map<String, Object> paramTrxNo = new HashMap<String, Object>();
      paramTrxNo.put("etrxSerialNo", eTrxSerialNo);
      // 根据交易流水号查询原始交易
      SacOtrxInfo sacOtrxInfo = oriTransactionDao.getOriTransaction(paramTrxNo);
      String trxSerialNo = sacOtrxInfo.getTrxSerialNo();
      this.updateOriTrxData(sacOtrxInfo, Boolean.TRUE);
      // 更新交易明细表
      this.updateChannelOriTrxDetailData(trxSerialNo, sacOtrxInfo);
      // 更新对账差错表中此条交易的对账状态和相关数据
      this.updateRecDiffData(recDiff, noticeform, Boolean.TRUE, null, null);
    }
    // }
  }

  private void updateChannelOriTrxDetailData(String trxSerialNo, SacOtrxInfo sacOtrxInfo) {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("trxSerialNo", trxSerialNo);
    // 根据交易流水号查询原始交易明细
    SacTrxDetail sacTrxDetail = oriTrxDetailDao.getOriTrxDetail(param);
    if (sacTrxDetail != null) {
      // SacTrxDetail uSacOtrxDetail = new SacTrxDetail();
      // uSacOtrxDetail.setId(sacTrxDetail.getId());
      // uSacOtrxDetail.setTrxAmount(sacOtrxInfo.getPayAmount());
      // uSacOtrxDetail.setTrxState(sacOtrxInfo.getTrxState());
      // uSacOtrxDetail.setUpdateTime(new Date());
      sacTrxDetail.setChaConStatus(Constants.TRANSACTION_SUCCESS_FLAG);

      oriTrxDetailDao.updateOriTrxDetailStatus(sacTrxDetail);
    } else {
      logger.debug("原始交易表中存在记录，但是交易明细表中未找到相关的交易记录,交易流水号=" + trxSerialNo);
    }
  }

  private void updateChannelOriTrxDetailDataForReplenishments(String trxSerialNo) {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("trxSerialNo", trxSerialNo);
    // 根据交易流水号查询原始交易明细
    SacTrxDetail sacTrxDetail = oriTrxDetailDao.getOriTrxDetail(param);
    if (sacTrxDetail != null) {
      //SacTrxDetail uSacOtrxDetail = new SacTrxDetail();
      //uSacOtrxDetail.setId(sacTrxDetail.getId());
      sacTrxDetail.setChnBatchNo(Constants.REPLENISHMENTS_FLAG);
      sacTrxDetail.setCusBatchNo(Constants.REPLENISHMENTS_FLAG);
      sacTrxDetail.setChaConStatus(Constants.TRANSACTION_SUCCESS_FLAG);
      oriTrxDetailDao.updateOriTrxDetailStatus(sacTrxDetail);
    } else {
      logger.debug("原始交易表中存在记录，但是交易明细表中未找到相关的交易记录,交易流水号=" + trxSerialNo);
    }
  }

  private void updateOriTrxData(SacOtrxInfo sacOtrxInfo, Boolean isChannel) {
//    SacOtrxInfo uSacOtrxInfo = new SacOtrxInfo();
//    uSacOtrxInfo.setId(sacOtrxInfo.getId());
    if (isChannel) {
      // 渠道差错处理中的补单交易需要更新所有内部对账，渠道对账标识和状态为成功
      sacOtrxInfo.setChaConFlag(Constants.REC_FLAG_SUCC);
      sacOtrxInfo.setChaConStatus(Constants.REC_STATUS_SUCC_FLAG);
      sacOtrxInfo.setInnConFlag(Constants.REC_FLAG_SUCC);
      sacOtrxInfo.setInnConStatus(Constants.REC_STATUS_SUCC_FLAG);
    } else {
      sacOtrxInfo.setInnConFlag(Constants.REC_FLAG_SUCC);
      sacOtrxInfo.setInnConStatus(Constants.REC_STATUS_SUCC_FLAG);
    }
    // uSacOtrxInfo.setUpdateTime(new Date());
    oriTransactionDao.updateOriTransactionStatus(sacOtrxInfo);
  }

  private void updateOriTrxDataForReplenishments(SacOtrxInfo sacOtrxInfo, Boolean isChannel) {
    //SacOtrxInfo uSacOtrxInfo = new SacOtrxInfo();
    //uSacOtrxInfo.setId(sacOtrxInfo.getId());
    if (isChannel) {
      // 渠道差错处理中的补单交易需要更新所有内部对账，渠道对账标识和状态为成功
      sacOtrxInfo.setChaConFlag(Constants.REC_FLAG_SUCC);
      sacOtrxInfo.setChaConStatus(Constants.REC_STATUS_SUCC_FLAG);
      sacOtrxInfo.setInnConFlag(Constants.REC_FLAG_SUCC);
      sacOtrxInfo.setInnConStatus(Constants.REC_STATUS_SUCC_FLAG);
      sacOtrxInfo.setAccountStatus(Constants.TRANSACTION_ACCOUNT_SUCC);
    } else {
      sacOtrxInfo.setInnConFlag(Constants.REC_FLAG_SUCC);
      sacOtrxInfo.setInnConStatus(Constants.REC_STATUS_SUCC_FLAG);
    }
    // uSacOtrxInfo.setUpdateTime(new Date());
    oriTransactionDao.updateOriTransactionStatus(sacOtrxInfo);
  }

  private void updateRecDiffData(SacRecDifferences sacRecDifferences, AccountNoticeForm noticeform, Boolean isChannel, String dealOper, String dealType) {
    SacRecDifferences uSacRecDifferences = new SacRecDifferences();
    uSacRecDifferences.setId(sacRecDifferences.getId());
    uSacRecDifferences.setStatus(Constants.REC_DIFF_DEALTYPE_SUCC);
    if (isChannel) {
      uSacRecDifferences.setDealOper(noticeform.getDealOper());
      uSacRecDifferences.setDealType(noticeform.getDealType());
    } else {
      uSacRecDifferences.setDealOper(dealOper);
      uSacRecDifferences.setDealType(dealType);
    }
    uSacRecDifferences.setUpdateTime(new Date());
    recDiffDao.updateRecDiff(uSacRecDifferences);
  }

  private void updateRecDiffDataForReplenishments(SacRecDifferences sacRecDifferences, String trxSerialNo, String dealOper, String dealType) {
    SacRecDifferences uSacRecDifferences = new SacRecDifferences();
    uSacRecDifferences.setId(sacRecDifferences.getId());
    uSacRecDifferences.setStatus(Constants.REC_DIFF_DEALTYPE_SUCC);

    uSacRecDifferences.setDealOper(dealOper);
    uSacRecDifferences.setDealType(dealType);
    // 渠道对账补单差错处理中，使用交易原始表中的交易流水号更新对账差错表交易流水号
    uSacRecDifferences.setTrxSerialNo(trxSerialNo);

    uSacRecDifferences.setUpdateTime(new Date());
    recDiffDao.updateRecDiff(uSacRecDifferences);
  }

  @Override
  public List<SacRecDifferences> selectDifferencesForSupplement(Map<String, Object> param) {
    // TODO Auto-generated method stub
    return recDiffDao.selectDifferencesForSupplement(param);
  }
}
