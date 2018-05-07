/**
 * 
 */
package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.dao.OriTransactionDao;
import net.easipay.cbp.dao.OriTrxDetailDao;
import net.easipay.cbp.dao.RecBatchDao;
import net.easipay.cbp.dao.RecBatchDetailDao;
import net.easipay.cbp.dao.RecDiffDao;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.SacRecBatch;
import net.easipay.cbp.model.SacRecDetail;
import net.easipay.cbp.model.SacRecDifferences;
import net.easipay.cbp.model.SacTrxDetail;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.RecBatchManager;
import net.easipay.cbp.service.base.impl.GenericManagerImpl;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
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
@Service("recBatchManager")
public class RecBatchManagerImpl extends GenericManagerImpl<SacRecBatch, Long> implements RecBatchManager {
  @Autowired
  RecBatchDao recBatchDao;
  @Autowired
  RecBatchDetailDao recBatchDetailDao;
  @Autowired
  OriTransactionDao oriTransactionDao;
  @Autowired
  OriTrxDetailDao oriTrxDetailDao;
  @Autowired
  RecDiffDao recDiffDao;

  private static final Logger logger = Logger.getLogger(RecBatchManagerImpl.class);

  @Override
  public List<SacRecBatch> selectRecBatchForOneDay(String recType) throws Exception {

    Map<String, String> filterParm = setFilterCondition(recType);
    List<SacRecBatch> recBatchList = recBatchDao.getRecBatchList(filterParm);
    if (recBatchList != null && !recBatchList.isEmpty()) {
      return recBatchList;
    }
    return null;
  }

  private Map<String, String> setFilterCondition(String recType) {

    Map<String, String> param = new HashMap<String, String>();
    param.put("recType", recType);
    param.put("recStatus", Constants.REC_STATUS_NEW);
    param.put("oneMonthFlag", "Y");
    return param;
  }

  @Override
  public void processInnerRec(SacRecBatch sacRecBatch) throws Exception {
    // TODO Auto-generated method stub
    Long recBatchId = sacRecBatch.getRecBatchId();

    Map<String, String> paramBatchId = new HashMap<String, String>();
    paramBatchId.put("recBatchId", String.valueOf(recBatchId));
    // Properties props = ResouceFileReadUtil
    // .readPropertiesFileByName("/sysSwitch.properties");
    // String sysSwitch = props.getProperty("REC_TRX_ONLY_POSITIVE");
    // if (!StringUtils.isBlank(sysSwitch)
    // && Boolean.valueOf(sysSwitch) == Boolean.TRUE) {
    // paramBatchId.put("busiType", "1");
    // }
    List<SacRecDetail> sacRecDetailList = recBatchDetailDao.selectRecBatchDetailList(paramBatchId);

    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // Calendar cal = new GregorianCalendar();
    // cal.setTime(new Date());
    // cal.set(Calendar.HOUR_OF_DAY, 0);
    // cal.set(Calendar.MINUTE, 0);
    // cal.set(Calendar.SECOND, 0);
    // cal.set(Calendar.MILLISECOND, 0);
    //
    // String beginDate = sdf.format((new Date(
    // cal.getTime().getTime() - 48L * 3600L * 1000L)));
    // String endDate = sdf.format(cal.getTime());
    if (sacRecDetailList != null && !sacRecDetailList.isEmpty()) {
      for (SacRecDetail sacRecDetail : sacRecDetailList) {
        String trxSerialNo = sacRecDetail.getTrxSerialNo();
        BigDecimal payAmount = sacRecDetail.getPayAmount();
        String trxCode = sacRecDetail.getTrxCode();
        Map<String, Object> paramTrxNo = new HashMap<String, Object>();
        paramTrxNo.put("trxSerialNo", trxSerialNo);
        paramTrxNo.put("oneMonthFlag", "Y");
        // paramTrxNo.put("beginDate", beginDate);
        // paramTrxNo.put("endDate", endDate);
        // 根据交易流水号查询原始交易
        SacOtrxInfo sacOtrxInfo = oriTransactionDao.getOriTransaction(paramTrxNo);
        //处理3411方式一：
        //        Map<String, Object> paramTrxSpec = new HashMap<String, Object>();
        //        paramTrxSpec.put("trxBatchNo", trxSerialNo);
        //        // 根据交易流水号查询原始交易批次号
        //        List<SacOtrxInfo> sacOtrxInfoBatch = oriTransactionDao.getOriTransactionList(paramTrxSpec);
        //处理3411方式二：
        List<SacOtrxInfo> sacOtrxInfoBatch = null;
        if (Constants.INNER_REC_TRX_SPECIAL.equals(trxCode)) {
          // 根据交易流水号查询原始交易批次号
          Map<String, Object> paramTrxSpec = new HashMap<String, Object>();
          paramTrxSpec.put("trxBatchNo", trxSerialNo);
          paramTrxSpec.put("oneMonthFlag", "Y");
          sacOtrxInfoBatch = oriTransactionDao.getOriTransactionList(paramTrxSpec);
        }
        if (sacOtrxInfo != null) {
          // 对账明细中存在，交易原始表中存在,进行核对
          BigDecimal oriPayAmount = sacOtrxInfo.getPayAmount();
          String trxState = sacOtrxInfo.getTrxState();
          StringBuffer recDiffDescn = new StringBuffer("内部对账:存在不一致-");
          if (!Constants.TRANSACTION_SUCCESS_FLAG.equals(trxState)) {
            recDiffDescn.append("[交易状态]");
          }
          if (payAmount.compareTo(oriPayAmount) != 0) {
            recDiffDescn.append("[交易金额]");
          }
          if (recDiffDescn.toString().endsWith("-")) {
            // 对账不存在差错,更新交易原始表
            this.updateOriTrxData(sacOtrxInfo, Boolean.FALSE, Constants.REC_STATUS_SUCC_FLAG);
          } else {
            // 对账存在差错,计入对账差错表
            // 更新原始交易表，把状态和金额更新成与对账明细一致，同时更新交易明细表。更新交易原始表的时候要触发会计核算系统记账
            UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.INNER_DIFF_INCONSISTENT);
            SacRecDifferences sacRecDifferences = this.constructRecDiffData(sacRecDetail, sacOtrxInfo, unifiedConfig.getDicValue(), recDiffDescn.toString(), Boolean.FALSE, Boolean.FALSE);
            recDiffDao.insertRecDiff(sacRecDifferences);
            // 更新原交易表已经对账，但对账状态为未成功
            this.updateOriTrxData(sacOtrxInfo, Boolean.FALSE, Constants.REC_STATUS_NEW_FLAG);

          }
        } else if (sacOtrxInfoBatch != null && !sacOtrxInfoBatch.isEmpty()) {//&& Constants.INNER_REC_TRX_SPECIAL.equals((sacOtrxInfoBatch.get(0).getTrxType()))
          Boolean batchStates = Boolean.TRUE;

          BigDecimal totalAmout = BigDecimal.ZERO;
          for (SacOtrxInfo sacOtrx : sacOtrxInfoBatch) {
            totalAmout = totalAmout.add(sacOtrx.getPayAmount());
            String trxState = sacOtrx.getTrxState();
            if (!Constants.TRANSACTION_SUCCESS_FLAG.equals(trxState)) {
              batchStates = Boolean.FALSE;
              continue;
            }
          }

          StringBuffer recDiffDescn = new StringBuffer("内部对账:存在不一致-");
          if (!batchStates) {
            recDiffDescn.append("[3411交易状态]");
          }
          if (payAmount.compareTo(totalAmout) != 0) {
            recDiffDescn.append("[3411交易金额]");
          }
          if (recDiffDescn.toString().endsWith("-")) {
            this.process3411Trx(sacOtrxInfoBatch);
          } else {
            SacOtrxInfo sacOtrxInfo3411 = new SacOtrxInfo();
            sacOtrxInfo3411.setTrxState(batchStates == true ? Constants.TRANSACTION_SUCCESS_FLAG : Constants.TRANSACTION_NEW_FLAG);
            sacOtrxInfo3411.setPayAmount(totalAmout);
            // 对账存在差错,计入对账差错表
            UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.INNER_DIFF_INCONSISTENT);
            SacRecDifferences sacRecDifferences = this.constructRecDiffData(sacRecDetail, sacOtrxInfo3411, unifiedConfig.getDicValue(), recDiffDescn.toString(), Boolean.FALSE, Boolean.FALSE);
            recDiffDao.insertRecDiff(sacRecDifferences);
            // 更新原交易表已经对账，但对账状态为未成功
            for (SacOtrxInfo sacOtrx : sacOtrxInfoBatch) {
              this.updateOriTrxData(sacOtrx, Boolean.FALSE, Constants.REC_STATUS_NEW_FLAG);
            }

          }

        }

        else {
          // 如果原始交易表中不存在这条对账明细,直接计入差错表，标注差错记录差错类型原因
          UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.INNER_DIFF_NOT_EXISTED);
          SacRecDifferences sacRecDifferences = this.constructRecDiffData(sacRecDetail, null, unifiedConfig.getDicValue(), "内部对账:原交易表中不存在该笔交易", Boolean.FALSE, Boolean.FALSE);
          recDiffDao.insertRecDiff(sacRecDifferences);
        }

      }
      // 每一批次对账完毕，更新该对账批次表的状态
      this.updateRecBatchData(sacRecBatch, Constants.REC_STATUS_SUCC);
    } else {
      this.updateRecBatchData(sacRecBatch, Constants.REC_STATUS_SUCC);
    }
  }

  @Override
  public void processInnerRecShortTerm(SacRecBatch sacRecBatch) throws Exception {
    // TODO Auto-generated method stub
    List<SacOtrxInfo> otrxList = this.getOtrxInfoForInnerRec(sacRecBatch);
    if (otrxList != null && !otrxList.isEmpty()) {
      for (SacOtrxInfo sacOtrxInfo : otrxList) {
        String busiType=sacOtrxInfo.getBussType();
        String trxType=sacOtrxInfo.getTrxType();
        if("1612".equals(trxType)&&!"20".equals(busiType)){
          continue;
        }
        // 交易原始表中有记录，对账明细中不存在,将这种情况（短款）将这笔交易记录，计入对账差错表，记录差错类型原因等
        UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.INNER_DIFF_NOT_EXISTED_RECBATCH);
        SacRecDifferences sacRecDifferences = this.constructRecDiffData(null, sacOtrxInfo, unifiedConfig.getDicValue(), "内部对账:对账文件中不存在该笔交易", Boolean.FALSE, Boolean.FALSE);
        recDiffDao.insertRecDiff(sacRecDifferences);
        // 更新原交易表
        this.updateOriTrxData(sacOtrxInfo, Boolean.FALSE, Constants.REC_STATUS_NEW_FLAG);
      }
    }
  }

  private List<SacOtrxInfo> getOtrxInfoForInnerRec(SacRecBatch sacRecBatch) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = new GregorianCalendar();
    cal.setTime(sacRecBatch.getRecStartDate());
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.add(Calendar.DAY_OF_MONTH, 1);

    String beginDate = sdf.format((new Date(cal.getTime().getTime() - 24L * 3600L * 1000L)));
    String endDate = sdf.format((new Date(cal.getTime().getTime())));

    Map<String, Object> paramTrxNo = new HashMap<String, Object>();
    paramTrxNo.put("innConFlag", Constants.REC_FLAG_NEW);
    paramTrxNo.put("chaConFlag", Constants.REC_FLAG_NEW);
    paramTrxNo.put("innConStatus", Constants.REC_STATUS_NEW_FLAG);
    paramTrxNo.put("chaConStatus", Constants.REC_STATUS_NEW_FLAG);
    paramTrxNo.put("accountStatus", Constants.TRANSACTION_ACCOUNT_NEW);
    paramTrxNo.put("trxState", Constants.TRANSACTION_SUCCESS_FLAG);
    paramTrxNo.put("beginDate", beginDate);
    paramTrxNo.put("endDate", endDate);
    StringBuffer trxTypeBuff = new StringBuffer();
    List<UnifiedConfig> listUnifiedConfig = UnifiedConfigSimple.getDicTypeConfig(Constants.INNER_REC_EXCLUDE_TRXCODE_TYPE);
    if (listUnifiedConfig != null && !listUnifiedConfig.isEmpty()) {
      for (UnifiedConfig unifiedConfig : listUnifiedConfig) {
        trxTypeBuff.append("'").append(unifiedConfig.getDicValue()).append("'").append(",");
      }
      paramTrxNo.put("trxTypeNotGrp", trxTypeBuff.substring(0, trxTypeBuff.lastIndexOf(",")).toString());
    }
    //paramTrxNo.put("oneMonthFlag", "Y");
    List<SacOtrxInfo> sacOtrInfoList = oriTransactionDao.getOriTransactionList(paramTrxNo);
    return sacOtrInfoList;
  }

  private void process3411Trx(List<SacOtrxInfo> sacOtrxInfoBatch) {
    for (SacOtrxInfo sacOtrx3411 : sacOtrxInfoBatch) {
      Boolean batchStates = Boolean.TRUE;
      Boolean batchAmount = Boolean.TRUE;

      String trxSerialNo3411 = sacOtrx3411.getTrxSerialNo();
      BigDecimal payAmount3411 = sacOtrx3411.getPayAmount();
      Map<String, Object> paramTrxSpec = new HashMap<String, Object>();
      paramTrxSpec.put("trxBatchNo", trxSerialNo3411);
      paramTrxSpec.put("oneMonthFlag", "Y");
      // 根据交易流水号查询原始交易批次号
      List<SacOtrxInfo> sacOtrxInfoBatch3803 = oriTransactionDao.getOriTransactionList(paramTrxSpec);

      BigDecimal totalAmout = BigDecimal.ZERO;
      for (SacOtrxInfo sacOtrxBatch3803 : sacOtrxInfoBatch3803) {
        totalAmout = totalAmout.add(sacOtrxBatch3803.getPayAmount());
        String trxState = sacOtrxBatch3803.getTrxState();
        if (!Constants.TRANSACTION_SUCCESS_FLAG.equals(trxState)) {
          batchStates = Boolean.FALSE;
          continue;
        }
      }

      StringBuffer recDiffDescn = new StringBuffer("内部对账:存在不一致-");
      if (!batchStates) {
        recDiffDescn.append("[3803交易状态]");
      }
      if (payAmount3411.compareTo(totalAmout) != 0) {
        recDiffDescn.append("[3803交易金额]");
      }
      if (recDiffDescn.toString().endsWith("-")) {
        // 对账不存在差错,更新交易原始表
        this.updateOriTrxData(sacOtrx3411, Boolean.FALSE, Constants.REC_STATUS_SUCC_FLAG);
      } else {
        // 对账存在差错,计入对账差错表

        UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.INNER_DIFF_INCONSISTENT);
        SacRecDifferences sacRecDifferences = this.constructRecDiffData(null, sacOtrx3411, unifiedConfig.getDicValue(), recDiffDescn.toString(), Boolean.FALSE, Boolean.TRUE);
        sacRecDifferences.setPayAmount(totalAmout);
        recDiffDao.insertRecDiff(sacRecDifferences);
        // 更新原交易表已经对账，但对账状态为未成功
        this.updateOriTrxData(sacOtrx3411, Boolean.FALSE, Constants.REC_STATUS_NEW_FLAG);

      }

    }
  }

  @Override
  public void processChannelLongTerm(SacRecBatch sacRecBatch, String onlyPositive) throws Exception {
    // 处理长款
    Long recBatchId = sacRecBatch.getRecBatchId();
    // String recDate = sacRecBatch.getRecDate();
    Map<String, String> paramBatchId = new HashMap<String, String>();
    paramBatchId.put("recBatchId", String.valueOf(recBatchId));
    // Properties props = ResouceFileReadUtil
    // .readPropertiesFileByName("/sysSwitch.properties");
    // String sysSwitch = props.getProperty("REC_TRX_ONLY_POSITIVE");
    if (!StringUtils.isBlank(onlyPositive) && Boolean.valueOf(onlyPositive) == Boolean.TRUE) {
      paramBatchId.put("busiType", "1");
    }
    List<SacRecDetail> sacRecDetailList = recBatchDetailDao.selectRecBatchDetailList(paramBatchId);

    if (sacRecDetailList != null && !sacRecDetailList.isEmpty()) {
      Map<String, Object> paramTrxNo = null;
      for (SacRecDetail sacRecDetail : sacRecDetailList) {
        String eTrxSerialNo = sacRecDetail.getBankSerialNo();
        BigDecimal payAmount = sacRecDetail.getPayAmount();
        paramTrxNo = new HashMap<String, Object>();
        paramTrxNo.put("etrxSerialNo", eTrxSerialNo);
        paramTrxNo.put("oneMonthFlag", "Y");
        // 根据交易流水号查询原始交易
        SacOtrxInfo sacOtrxInfo = oriTransactionDao.getOriTransaction(paramTrxNo);
        if (sacOtrxInfo != null) {
          String trxSerialNo = sacOtrxInfo.getTrxSerialNo();
          // 渠道对账中，如果对账明细中的交易流水号为空，使用交易原始表中的交易流水号更新对账明细表交易流水号,否则就不更新
          if (sacRecDetail.getTrxSerialNo() == null || "".equals(sacRecDetail.getTrxSerialNo())) {
            sacRecDetail.setTrxSerialNo(trxSerialNo);
            this.updateRecDetailTrxSerialNo(trxSerialNo, sacRecDetail);
          }

          // 第一步：渠道对账前需要检查内部对账是否已经进行，如果未进行插入差错表
          if (sacOtrxInfo.getInnConFlag().equals(Constants.REC_FLAG_NEW)) {
            UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REC_DIFF_TYPE_NO_INNER);
            SacRecDifferences sacRecDifferences = this.constructRecDiffData(sacRecDetail, sacOtrxInfo, unifiedConfig.getDicValue(), "渠道对账:该笔交易尚未进行内部对账", Boolean.TRUE, Boolean.FALSE);
            recDiffDao.insertRecDiff(sacRecDifferences);
          }
          // 第二步：继续开始渠道对账逻辑处理
          if (sacOtrxInfo.getChaConFlag().equals(Constants.REC_FLAG_NEW)) {
            // 对账明细中存在，交易原始表中存在,进行核对
            BigDecimal oriPayAmount = sacOtrxInfo.getPayAmount();
            String trxState = sacOtrxInfo.getTrxState();

            StringBuffer recDiffDescn = new StringBuffer("渠道对账:存在不一致-");
            if (!Constants.TRANSACTION_SUCCESS_FLAG.equals(trxState)) {
              recDiffDescn.append("[交易状态]");
            }
            if (payAmount.compareTo(oriPayAmount) != 0) {
              recDiffDescn.append("[交易金额]");
            }
            if (recDiffDescn.toString().endsWith("-")) {
              // 对账不存在差错,更新交易原始表
              this.updateOriTrxData(sacOtrxInfo, Boolean.TRUE, Constants.REC_STATUS_SUCC_FLAG);
              // 更新交易明细表
              this.updateChannelOriTrxDetailData(trxSerialNo, sacOtrxInfo);
            } else {
              // 对账存在差错,计入对账差错表
              // 更新原始交易表，把状态和金额更新成与对账明细一致，同时更新交易明细表。更新交易原始表的时候要触发会计核算系统记账
              UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REC_DIFF_TYPE_LONG);
              SacRecDifferences sacRecDifferences = this.constructRecDiffData(sacRecDetail, sacOtrxInfo, unifiedConfig.getDicValue(), recDiffDescn.toString(), Boolean.TRUE, Boolean.FALSE);
              recDiffDao.insertRecDiff(sacRecDifferences);
              // 更新原交易表已经对账，但对账状态为未成功
              this.updateOriTrxData(sacOtrxInfo, Boolean.TRUE, Constants.REC_STATUS_NEW_FLAG);
              // 更新交易明细表
              // Map<String, Object> param = new HashMap<String,
              // Object>();
              // param.put("trxSerialNo", trxSerialNo);
              // // 根据交易流水号查询原始交易明细
              // SacTrxDetail sacTrxDetail = oriTrxDetailDao
              // .getOriTrxDetail(param);
              // if (sacTrxDetail != null) {
              // this.updateOriTrxDetailData(sacTrxDetail,
              // sacOtrxInfo);
              // } else {
              // logger.info("原始交易表中存在记录，但是交易明细表中未找到相关的交易记录,交易流水号="
              // + trxSerialNo);
              // }
            }
          }
        } else {
          // 如果原始交易表中不存在这条对账明细,直接计入差错表，标注差错记录差错类型原因
          UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REC_DIFF_NOT_EXISTED);
          SacRecDifferences sacRecDifferences = this.constructRecDiffData(sacRecDetail, null, unifiedConfig.getDicValue(), "渠道对账:原交易表中不存在该笔交易", Boolean.TRUE, Boolean.FALSE);
          recDiffDao.insertRecDiff(sacRecDifferences);
        }

      }

    }
    // 每一批次对账完毕，更新该对账批次表的状态
    this.updateRecBatchData(sacRecBatch, Constants.REC_STATUS_MIDDLE);
  }

  @Override
  public void processChannelShortTerm(String recType) throws Exception {
    // 取对账批次表中尚未进行短款对账的批次数据
    List<SacRecBatch> recBatchList = this.getRecBatchForShortTerm(recType);

    String batchIdStr = "";
    if (recBatchList != null && !recBatchList.isEmpty()) {
      for (SacRecBatch sacRecBatch : recBatchList) {
        Map<String, Object> rtnMap = shortTermRelativeDateExisted(sacRecBatch);
        if ((Boolean) rtnMap.get("isExisted")) {
          batchIdStr = rtnMap.get("batchIdStr").toString();
          // 取原始交易表中在该账批次日期所有未对账的数据
          List<SacOtrxInfo> sacOtrInfoList = this.getOtrxInfoForShortTerm(sacRecBatch);
          if (sacOtrInfoList != null && !sacOtrInfoList.isEmpty()) {
            for (SacOtrxInfo sacOtrxInfo : sacOtrInfoList) {
              // 类似Trxtype=3303这种外部流水号为空的不参与短款对账
              String eTrxSerialNo = sacOtrxInfo.getEtrxSerialNo();
              if (eTrxSerialNo == null || "".equals(eTrxSerialNo)) {
                continue;
              }

              // 第一步：渠道短款对账前需要检查内部对账是否已经进行，如果未进行插入差错表
              if (sacOtrxInfo.getInnConFlag().equals(Constants.REC_FLAG_NEW)) {
                UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REC_DIFF_TYPE_NO_INNER);
                SacRecDifferences sacRecDifferences = this.constructRecDiffData(null, sacOtrxInfo, unifiedConfig.getDicValue(), "渠道对账:该笔交易尚未进行内部对账", Boolean.TRUE, Boolean.FALSE);
                recDiffDao.insertRecDiff(sacRecDifferences);
              }
              // 第二步：继续开始渠道短款对账逻辑处理
              Map<String, String> filterparam = new HashMap<String, String>();
              if (!batchIdStr.equals("")) {
                filterparam.put("recBatchIdGrp", batchIdStr);
              }
              filterparam.put("bankSerialNo", sacOtrxInfo.getEtrxSerialNo());
              SacRecDetail sacRecDetail = recBatchDetailDao.selectRecBatchDetail(filterparam);
              if (sacRecDetail == null) {
                // 交易原始表中有记录，对账明细中不存在,将这种情况（短款）将这笔交易记录，计入对账差错表，记录差错类型原因等
                UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REC_DIFF_TYPE_SHORT);
                SacRecDifferences sacRecDifferences = this.constructRecDiffData(sacRecDetail, sacOtrxInfo, unifiedConfig.getDicValue(), "渠道对账:对账文件中不存在该笔交易", Boolean.TRUE, Boolean.FALSE);
                recDiffDao.insertRecDiff(sacRecDifferences);
                // 更新原交易表
                // this.updateOriTrxData(sacOtrxInfo, null,
                // Boolean.TRUE);
              }
            }

          }
          // 更新对账批次表状态
          this.updateRecBatchDataForShortTerm(sacRecBatch, Constants.REC_STATUS_SUCC);
        }
      }
    }

  }

  private Map<String, Object> shortTermRelativeDateExisted(SacRecBatch sacRecBatch) {
    Boolean isExisted = Boolean.FALSE;
    Date recStartDate = sacRecBatch.getRecStartDate();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = new GregorianCalendar();
    cal.setTime(recStartDate);
    String preDate = sdf.format((new Date(cal.getTime().getTime() - 24L * 3600L * 1000L)));
    // StringBuffer relativeDateBuff = new StringBuffer();
    // relativeDateBuff.append(preDate + ",");
    cal.add(Calendar.DAY_OF_MONTH, 1);
    String nextDate = sdf.format(cal.getTime());
    // relativeDateBuff.append(nextDate);
    // String recDateBatchStr = relativeDateBuff.toString();
    List<SacRecBatch> sacRecBatchList = new ArrayList<SacRecBatch>();
    Map<String, String> filterparam1 = new HashMap<String, String>();
    filterparam1.put("recRelativePreDate", preDate);
    filterparam1.put("chnCode", sacRecBatch.getChnCode());
    filterparam1.put("recType", sacRecBatch.getRecType());
    filterparam1.put("oneMonthFlag", "Y");
    List<SacRecBatch> sacRecBatchList1 = recBatchDao.getRecBatchList(filterparam1);

    Map<String, String> filterparam2 = new HashMap<String, String>();
    filterparam2.put("recRelativeNextDate", nextDate);
    filterparam2.put("chnCode", sacRecBatch.getChnCode());
    filterparam2.put("recType", sacRecBatch.getRecType());
    filterparam2.put("oneMonthFlag", "Y");
    List<SacRecBatch> sacRecBatchList2 = recBatchDao.getRecBatchList(filterparam2);

    Map<String, Object> rtnMap = new HashMap<String, Object>();
    if (sacRecBatchList1 != null && !sacRecBatchList1.isEmpty() && sacRecBatchList2 != null && !sacRecBatchList2.isEmpty()) {
      isExisted = Boolean.TRUE;
      sacRecBatchList.addAll(sacRecBatchList1);
      sacRecBatchList.addAll(sacRecBatchList2);
      // cal.add(Calendar.DAY_OF_MONTH, -1);
      // String nowDate = sdf.format(cal.getTime());
      // String batchIdStr = batchIdBuff.append("," + nowDate).toString();
      StringBuffer batchIdBuff = new StringBuffer();
      for (SacRecBatch sacBatch : sacRecBatchList) {
        batchIdBuff.append(sacBatch.getRecBatchId() + ",");
      }
      batchIdBuff.append(sacRecBatch.getRecBatchId());
      String batchIdStr = batchIdBuff.toString();
      rtnMap.put("isExisted", isExisted);
      rtnMap.put("batchIdStr", batchIdStr);
    } else {
      rtnMap.put("isExisted", isExisted);
      rtnMap.put("batchIdStr", "");
    }
    return rtnMap;
  }

  @Override
  public void processChannelDiff(SacRecBatch sacRecBatch) throws Exception {

    Long recBatchId = sacRecBatch.getRecBatchId();
    String chnNoCode = sacRecBatch.getChnCode();
    String payConType = sacRecBatch.getPayconType();
    Date recStartDate = sacRecBatch.getRecStartDate();
    Date recEndDate = sacRecBatch.getRecEndDate();
    // 取原始交易表中这个批次渠道的所有的交易数据
    List<SacOtrxInfo> sacOtrInfoList = this.getOtrxInfoForChannelDiff(chnNoCode, payConType, recStartDate, recEndDate);
    if (sacOtrInfoList != null && !sacOtrInfoList.isEmpty()) {
      for (SacOtrxInfo sacOtrxInfo : sacOtrInfoList) {
        // 第一步：渠道差错对账前需要检查内部对账是否已经进行，如果未进行插入差错表
        if (sacOtrxInfo.getInnConFlag().equals(Constants.REC_FLAG_NEW)) {
          UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REC_DIFF_TYPE_NO_INNER);
          SacRecDifferences sacRecDifferences = this.constructRecDiffData(null, sacOtrxInfo, unifiedConfig.getDicValue(), "渠道对账:该笔交易尚未进行内部对账", Boolean.TRUE, Boolean.FALSE);
          recDiffDao.insertRecDiff(sacRecDifferences);
        }
        // 第二步：继续开始渠道短款对账逻辑处理
        String trxSerialNo = sacOtrxInfo.getTrxSerialNo();
        Map<String, String> filterparam = new HashMap<String, String>();
        filterparam.put("recBatchId", String.valueOf(recBatchId));
        // filterparam.put("trxSerialNo", trxSerialNo);
        filterparam.put("bankSerialNo", sacOtrxInfo.getEtrxSerialNo());
        SacRecDetail sacRecDetail = recBatchDetailDao.selectRecBatchDetail(filterparam);
        if (sacRecDetail == null) {
          // 交易原始表中的记录不存在于这批渠道差错中，更新原始交易表的记录为已经对账且成功
          this.updateOriTrxData(sacOtrxInfo, Boolean.TRUE, Constants.REC_STATUS_SUCC_FLAG);
          // 更新交易明细表
          this.updateChannelOriTrxDetailData(trxSerialNo, sacOtrxInfo);
        } else {
          // 渠道对账中，如果对账明细中的交易流水号为空，使用交易原始表中的交易流水号更新对账明细表交易流水号,否则就不更新
          if (sacRecDetail.getTrxSerialNo() == null || "".equals(sacRecDetail.getTrxSerialNo())) {
            sacRecDetail.setTrxSerialNo(trxSerialNo);
            this.updateRecDetailTrxSerialNo(trxSerialNo, sacRecDetail);
          }
          // 交易原始表中的记录存在于这批渠道差错中，更新原始交易表的记录为已经对账但对账状态为未成功
          this.updateOriTrxData(sacOtrxInfo, Boolean.TRUE, Constants.REC_STATUS_NEW_FLAG);
        }
      }
    }
    // 将该批次的渠道差错直接记入到差错表中
    Map<String, String> paramBatchId = new HashMap<String, String>();
    paramBatchId.put("recBatchId", String.valueOf(recBatchId));
    List<SacRecDetail> sacRecDetailList = recBatchDetailDao.selectRecBatchDetailList(paramBatchId);
    if (sacRecDetailList != null && !sacRecDetailList.isEmpty()) {
      SacRecDifferences sacRecDifferences = null;
      for (SacRecDetail sacRecDetail : sacRecDetailList) {
        sacRecDifferences = new SacRecDifferences();
        sacRecDifferences.setId(SequenceCreatorUtil.getTableId());
        sacRecDifferences.setRecBatchId(BigDecimal.valueOf(sacRecDetail.getRecBatchId()));
        sacRecDifferences.setRecDetailId(sacRecDetail.getId());
        sacRecDifferences.setRecStartDate(sacRecDetail.getRecStartDate());
        sacRecDifferences.setRecEndDate(sacRecDetail.getRecEndDate());
        sacRecDifferences.setTrxSerialNo(sacRecDetail.getTrxSerialNo());
        sacRecDifferences.setTrxTime(sacRecDetail.getTrxTime());
        sacRecDifferences.setPayAmount(sacRecDetail.getPayAmount());
        sacRecDifferences.setCurrencyType(sacRecDetail.getCurrencyType());
        sacRecDifferences.setBusiType(sacRecDetail.getBusiType());
        Map<String, Object> paramTrxNo = new HashMap<String, Object>();
        paramTrxNo.put("etrxSerialNo", sacRecDetail.getBankSerialNo());
        // 根据交易流水号查询原始交易
        SacOtrxInfo sacOtrxInfo = oriTransactionDao.getOriTransaction(paramTrxNo);
        StringBuffer recDiffDescn = new StringBuffer("渠道对账:存在不一致-");
        if (sacOtrxInfo != null) {
          sacRecDifferences.setOriTrxState(sacOtrxInfo.getTrxState());
          sacRecDifferences.setOriChaAmt(sacOtrxInfo.getPayAmount());
          BigDecimal payAmount = sacRecDetail.getPayAmount();
          BigDecimal oriPayAmount = sacOtrxInfo.getPayAmount();
          String trxState = sacOtrxInfo.getTrxState();
          String trxSerialNo = sacOtrxInfo.getTrxSerialNo();
          if (!Constants.TRANSACTION_SUCCESS_FLAG.equals(trxState)) {
            recDiffDescn.append("[交易状态]");
          }
          if (payAmount.compareTo(oriPayAmount) != 0) {
            recDiffDescn.append("[交易金额]");
          }

        }
        sacRecDifferences.setBankSerialNo(sacRecDetail.getBankSerialNo());
        sacRecDifferences.setChnCode(sacRecDetail.getChnCode());
        sacRecDifferences.setPayconType(sacRecDetail.getPayconType());
        if (!recDiffDescn.toString().endsWith("-")) {
          sacRecDifferences.setRecDiffDesc(recDiffDescn.toString());
        }
        String diffType = sacRecDetail.getDiffType();
        if (diffType.equals("1")) {
          UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REC_DIFF_TYPE_LONG);
          sacRecDifferences.setRecDiffType(unifiedConfig.getDicValue());
        } else if (diffType.equals("2")) {
          UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REC_DIFF_TYPE_SHORT);
          sacRecDifferences.setRecDiffType(unifiedConfig.getDicValue());
          sacRecDifferences.setRecDiffDesc("渠道对账:对账文件中不存在该笔交易");
        }
        sacRecDifferences.setStatus(Constants.REC_DIFF_DEALTYPE_NEW);
        sacRecDifferences.setSupplementFlag(Constants.REC_DIFF_SUPPLEMENT_NEW);
        sacRecDifferences.setCreateTime(new Date());
        sacRecDifferences.setUpdateTime(new Date());
        sacRecDifferences.setMemo(sacRecDetail.getMemo());
        sacRecDifferences.setRecOper(sacRecDetail.getRecOper());
        sacRecDifferences.setTrxCode(sacRecDetail.getTrxCode());
        sacRecDifferences.setPrivDomain(sacRecDetail.getPrivDomain());
        recDiffDao.insertRecDiff(sacRecDifferences);
      }
    }
    // 每一批次对账完毕，更新该对账批次表的状态
    this.updateRecBatchData(sacRecBatch, Constants.REC_STATUS_SUCC);
  }

  private List<SacOtrxInfo> getOtrxInfoForShortTerm(SacRecBatch sacRecBatch) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = new GregorianCalendar();
    cal.setTime(sacRecBatch.getRecStartDate());
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.add(Calendar.DAY_OF_MONTH, 1);

    String beginDate = sdf.format((new Date(cal.getTime().getTime() - 24L * 3600L * 1000L)));
    String endDate = sdf.format((new Date(cal.getTime().getTime())));

    Map<String, Object> paramTrxNo = new HashMap<String, Object>();
    // paramTrxNo.put("innConFlag", Constants.REC_FLAG_NEW);
    paramTrxNo.put("chaConFlag", Constants.REC_FLAG_NEW);
    paramTrxNo.put("trxState", Constants.TRANSACTION_SUCCESS_FLAG);
    paramTrxNo.put("draccNodeCode", sacRecBatch.getChnCode());
    paramTrxNo.put("beginDate", beginDate);
    paramTrxNo.put("endDate", endDate);
    StringBuffer trxTypeBuff = new StringBuffer();
    // String trxTypeStr = trxTypeBuff.append("'")
    // .append(Constants.SHORT_TERM_TRX_TYPE1).append("'").append(",")
    // .append("'").append(Constants.SHORT_TERM_TRX_TYPE2).append("'")
    // .append(",").append("'").append(Constants.SHORT_TERM_TRX_TYPE3)
    // .append("'").append(",").append("'")
    // .append(Constants.SHORT_TERM_TRX_TYPE4).append("'").append(",")
    // .append("'").append(Constants.SHORT_TERM_TRX_TYPE5).append("'")
    // .append(",").append("'").append(Constants.SHORT_TERM_TRX_TYPE6)
    // .append("'").toString();
    List<UnifiedConfig> listUnifiedConfig = UnifiedConfigSimple.getDicTypeConfig(Constants.SHORT_TERM_TRX_TYPE);
    if (listUnifiedConfig != null && !listUnifiedConfig.isEmpty()) {
      for (UnifiedConfig unifiedConfig : listUnifiedConfig) {
        trxTypeBuff.append("'").append(unifiedConfig.getDicValue()).append("'").append(",");
      }
      paramTrxNo.put("trxTypeGrp", trxTypeBuff.substring(0, trxTypeBuff.lastIndexOf(",")).toString());
    }
    paramTrxNo.put("oneMonthFlag", "Y");
    List<SacOtrxInfo> sacOtrInfoList = oriTransactionDao.getOriTransactionList(paramTrxNo);
    return sacOtrInfoList;
  }

  private List<SacOtrxInfo> getOtrxInfoForChannelDiff(String chnNoCode, String payConType, Date recStartDate, Date recEndDate) throws Exception {
    // 取该批次交易时间24点为终点(对账时间=该批次交易时间)以防止当天批次漏上传，几天以后再上传的情况)，当天零点为起点来搜索原始交易表中
    // 当天那些原始交易通过对账对出了这个渠道差错文件
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // Calendar cal = new GregorianCalendar();
    // cal.setTime(new SimpleDateFormat("yyyyMMdd").parse(recDate));
    // cal.add(Calendar.DAY_OF_MONTH, 1);

    // String beginDate = sdf.format((new Date(
    // cal.getTime().getTime() - 24L * 3600L * 1000L)));
    // String endDate = sdf.format(cal.getTime());
    Map<String, Object> paramTrxNo = new HashMap<String, Object>();
    // paramTrxNo.put("innConFlag", Constants.REC_FLAG_SUCC);
    paramTrxNo.put("chaConFlag", Constants.REC_FLAG_NEW);
    paramTrxNo.put("craccNodeCode", chnNoCode);
    paramTrxNo.put("payconType", payConType);
    paramTrxNo.put("payWay", Constants.PAYWAY_QUICKLY);
    paramTrxNo.put("trxBeginDate", recStartDate);
    paramTrxNo.put("trxEndDate", recEndDate);
    StringBuffer trxTypeBuff = new StringBuffer();
    // String trxTypeStr = trxTypeBuff.append("'")
    // .append(Constants.CHANNEL_DIFF_TRX_TYPE1).append("'")
    // .append(",").append("'")
    // .append(Constants.CHANNEL_DIFF_TRX_TYPE2).append("'")
    // .append(",").append("'")
    // .append(Constants.CHANNEL_DIFF_TRX_TYPE3).append("'")
    // .append(",").append("'")
    // .append(Constants.CHANNEL_DIFF_TRX_TYPE4).append("'")
    // .append(",").append("'")
    // .append(Constants.CHANNEL_DIFF_TRX_TYPE5).append("'")
    // .toString();
    List<UnifiedConfig> listUnifiedConfig = UnifiedConfigSimple.getDicTypeConfig(Constants.CHANNEL_DIFF_TRX_TYPE);
    if (listUnifiedConfig != null && !listUnifiedConfig.isEmpty()) {
      for (UnifiedConfig unifiedConfig : listUnifiedConfig) {
        trxTypeBuff.append("'").append(unifiedConfig.getDicValue()).append("'").append(",");
      }
      paramTrxNo.put("trxTypeGrp", trxTypeBuff.substring(0, trxTypeBuff.lastIndexOf(",")).toString());
    }
    paramTrxNo.put("oneMonthFlag", "Y");
    List<SacOtrxInfo> sacOtrInfoList = oriTransactionDao.getOriTransactionList(paramTrxNo);
    return sacOtrInfoList;
  }

  private List<SacRecBatch> getRecBatchForShortTerm(String recType) {

    Map<String, String> param = new HashMap<String, String>();
    param.put("recType", recType);
    param.put("recStatus", Constants.REC_STATUS_MIDDLE);
    param.put("oneMonthFlag", "Y");
    List<SacRecBatch> recBatchList = recBatchDao.getRecBatchList(param);
    if (recBatchList != null && !recBatchList.isEmpty()) {
      return recBatchList;
    }
    return null;
  }

  private SacRecDifferences constructRecDiffData(SacRecDetail sacRecDetail, SacOtrxInfo sacOtrxInfo, String recDiffType, String recDiffDescn, Boolean isChannel, Boolean isInner3803) {
    SacRecDifferences sacRecDifferences = new SacRecDifferences();
    sacRecDifferences.setId(SequenceCreatorUtil.getTableId());
    UnifiedConfig unifiedConfigShort = UnifiedConfigSimple.getDicCodeConfig(Constants.REC_DIFF_TYPE_SHORT);
    UnifiedConfig unifiedConfigNoInner = UnifiedConfigSimple.getDicCodeConfig(Constants.REC_DIFF_TYPE_NO_INNER);
    UnifiedConfig unifiedConfigNoInnerRecBatch = UnifiedConfigSimple.getDicCodeConfig(Constants.INNER_DIFF_NOT_EXISTED_RECBATCH);
    if (unifiedConfigShort.getDicValue().equals(recDiffType) || unifiedConfigNoInner.getDicValue().equals(recDiffType) || isInner3803 || unifiedConfigNoInnerRecBatch.getDicValue().equals(recDiffType)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Calendar cal = new GregorianCalendar();
      cal.setTime(sacOtrxInfo.getUpdateTime());
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
      Date beginDate = cal.getTime();

      cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
      cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
      cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
      cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
      Date endDate = cal.getTime();

      sacRecDifferences.setRecStartDate(beginDate);
      sacRecDifferences.setRecEndDate(endDate);

      sacRecDifferences.setTrxSerialNo(sacOtrxInfo.getTrxSerialNo());
      sacRecDifferences.setTrxTime(sacOtrxInfo.getCreateTime());
      sacRecDifferences.setPayAmount(BigDecimal.ZERO);
      sacRecDifferences.setCurrencyType(sacOtrxInfo.getPayCurrency());
      sacRecDifferences.setOriTrxState(sacOtrxInfo.getTrxState());
      if (isChannel) {
        sacRecDifferences.setOriChaAmt(sacOtrxInfo.getPayAmount());
      } else {
        sacRecDifferences.setOriInnerAmt(sacOtrxInfo.getPayAmount());
      }
      sacRecDifferences.setBankSerialNo(sacOtrxInfo.getEtrxSerialNo());
      sacRecDifferences.setChnCode(sacOtrxInfo.getDraccNodeCode());
      sacRecDifferences.setPayconType(sacOtrxInfo.getPayconType());
      sacRecDifferences.setTrxCode(sacOtrxInfo.getTrxType());

    } else {
      sacRecDifferences.setRecBatchId(BigDecimal.valueOf(sacRecDetail.getRecBatchId()));
      sacRecDifferences.setRecDetailId(sacRecDetail.getId());
      sacRecDifferences.setRecStartDate(sacRecDetail.getRecStartDate());
      sacRecDifferences.setRecEndDate(sacRecDetail.getRecEndDate());
      sacRecDifferences.setTrxSerialNo(sacRecDetail.getTrxSerialNo());
      sacRecDifferences.setTrxTime(sacRecDetail.getTrxTime());
      sacRecDifferences.setPayAmount(sacRecDetail.getPayAmount());
      sacRecDifferences.setCurrencyType(sacRecDetail.getCurrencyType());
      if (sacOtrxInfo != null) {
        sacRecDifferences.setOriTrxState(sacOtrxInfo.getTrxState());
        if (isChannel) {
          sacRecDifferences.setOriChaAmt(sacOtrxInfo.getPayAmount());
        } else {
          sacRecDifferences.setOriInnerAmt(sacOtrxInfo.getPayAmount());
        }
      }
      sacRecDifferences.setBankSerialNo(sacRecDetail.getBankSerialNo());
      sacRecDifferences.setChnCode(sacRecDetail.getChnCode());
      sacRecDifferences.setPayconType(sacRecDetail.getPayconType());
      sacRecDifferences.setMemo(sacRecDetail.getMemo());
      sacRecDifferences.setRecOper(sacRecDetail.getRecOper());
      sacRecDifferences.setBusiType(sacRecDetail.getBusiType());
      sacRecDifferences.setTrxCode(sacRecDetail.getTrxCode());
      sacRecDifferences.setPrivDomain(sacRecDetail.getPrivDomain());
    }
    sacRecDifferences.setRecDiffType(recDiffType);
    sacRecDifferences.setRecDiffDesc(recDiffDescn);
    sacRecDifferences.setStatus(Constants.REC_DIFF_DEALTYPE_NEW);
    sacRecDifferences.setSupplementFlag(Constants.REC_DIFF_SUPPLEMENT_NEW);
    sacRecDifferences.setCreateTime(new Date());
    sacRecDifferences.setUpdateTime(new Date());
    return sacRecDifferences;
  }

  private void updateOriTrxData(SacOtrxInfo sacOtrxInfo, Boolean isChannel, String recStatus) {
    //SacOtrxInfo uSacOtrxInfo = new SacOtrxInfo();
    //uSacOtrxInfo.setId(sacOtrxInfo.getId());
    if (isChannel) {
      sacOtrxInfo.setChaConFlag(Constants.REC_FLAG_SUCC);
      sacOtrxInfo.setChaConStatus(recStatus);

    } else {
      sacOtrxInfo.setInnConFlag(Constants.REC_FLAG_SUCC);
      sacOtrxInfo.setInnConStatus(recStatus);
    }
    // uSacOtrxInfo.setUpdateTime(new Date());
    // uSacOtrxInfo.setPayAmount(sacRecDetail.getPayAmount());
    // uSacOtrxInfo.setTrxState(Constants.TRANSACTION_SUCCESS_FLAG);
    oriTransactionDao.updateOriTransactionStatus(sacOtrxInfo);

  }

  private void updateChannelOriTrxDetailData(String trxSerialNo, SacOtrxInfo sacOtrxInfo) {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("trxSerialNo", trxSerialNo);
    // 根据交易流水号查询原始交易明细
    SacTrxDetail sacTrxDetail = oriTrxDetailDao.getOriTrxDetail(param);
    if (sacTrxDetail != null) {
      //SacTrxDetail uSacOtrxDetail = new SacTrxDetail();
      //uSacOtrxDetail.setId(sacTrxDetail.getId());
      sacTrxDetail.setChaConStatus(Constants.TRANSACTION_SUCCESS_FLAG);
      // uSacOtrxDetail.setTrxAmount(sacOtrxInfo.getPayAmount());
      // uSacOtrxDetail.setTrxState(sacOtrxInfo.getTrxState());
      // uSacOtrxDetail.setUpdateTime(new Date());
      oriTrxDetailDao.updateOriTrxDetailStatus(sacTrxDetail);
    } else {
      logger.debug("原始交易表中存在记录，但是交易明细表中未找到相关的交易记录,交易流水号=" + trxSerialNo);
    }
  }

  private void updateRecBatchData(SacRecBatch sacRecBatch, String recStauts) {
    SacRecBatch uSacRecBatch = new SacRecBatch();
    uSacRecBatch.setRecBatchId(sacRecBatch.getRecBatchId());
    uSacRecBatch.setRecStatus(recStauts);
    uSacRecBatch.setUpdateTime(new Date());
    recBatchDao.updateRecBatch(uSacRecBatch);
  }

  private void updateRecBatchDataForShortTerm(SacRecBatch sacRecBatch, String recStauts) {
    SacRecBatch uSacRecBatch = new SacRecBatch();
    uSacRecBatch.setRecBatchId(sacRecBatch.getRecBatchId());
    uSacRecBatch.setRecStatus(recStauts);
    recBatchDao.updateRecBatch(uSacRecBatch);
  }

  private void updateRecDetailTrxSerialNo(String trxSerialNo, SacRecDetail sacRecDetail) {
    SacRecDetail uSacRecDetail = new SacRecDetail();
    uSacRecDetail.setId(sacRecDetail.getId());
    uSacRecDetail.setTrxSerialNo(trxSerialNo);
    recBatchDetailDao.updateRecBatchDetail(uSacRecDetail);
  }
}
