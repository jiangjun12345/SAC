package net.easipay.cbp.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jxl.Sheet;
import jxl.Workbook;
import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.dao.ISacRefundBatchDao;
import net.easipay.cbp.dao.ISacRefundDetailDao;
import net.easipay.cbp.exception.OFLOnloadException;
import net.easipay.cbp.model.SacRefundBatch;
import net.easipay.cbp.model.SacRefundCommand;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.IRefundService;
import net.easipay.cbp.service.ISequenceCreatorService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.ExcelUtil;
import net.easipay.cbp.util.StringUtil;
import net.easipay.cbp.util.UrlUtil;
import net.easipay.cbp.util.XlsExporter;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefundServiceImpl implements IRefundService {

  Logger logger = LoggerFactory.getLogger(RefundServiceImpl.class);

  @Autowired
  private ISacRefundDetailDao sacRefundDetailDao;

  @Autowired
  private ISacRefundBatchDao sacRefundBatchDao;

  @Autowired
  private ISequenceCreatorService sequenceCreatorService;

  private String refundB2BUrl;

  public void setRefundB2BUrl(String refundB2BUrl) {
    this.refundB2BUrl = refundB2BUrl;
  }

  public enum OFLBatchState {
    Init("00", "待处理"), ReviewReject("02", "复核不通过"), WaitingReview("10", "待复核"), ReviewPass("20", "复核通过");

    private String stateCode;
    private String stateValue;

    OFLBatchState(String stateCode, String stateValue) {
      this.stateCode = stateCode;
      this.stateValue = stateValue;
    }

    public static String getStateValue(String code) {
      return stateMap.get(code);
    }

    public String code() {
      return this.stateCode;
    }

    public String value() {
      return this.stateValue;
    }

    @Override
    public String toString() {
      return this.stateCode + ":" + this.stateValue;
    }

    private static Map<String, String> stateMap = new HashMap<String, String>();
    static {
      for (OFLBatchState state : OFLBatchState.values()) {
        OFLBatchState.stateMap.put(state.stateCode, state.stateValue);
      }
    }
  }

  @Override
  public List<SacRefundCommand> b2bRefundTrxInfoForPaging(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return sacRefundDetailDao.b2bRefundTrxInfoForPaging(paramMap);
  }

  @Override
  public int b2bRefundTrxCounts(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return sacRefundDetailDao.b2bRefundTrxCounts(paramMap);

  }

  @Override
  public ByteArrayOutputStream exportExcel(String[] wpIds, String tplName) {
    StringBuffer idBuff = new StringBuffer();
    for (String wp : wpIds) {
      idBuff.append("'").append(wp).append("'").append(",");
    }
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("idGrp", idBuff.substring(0, idBuff.lastIndexOf(",")).toString());
    List<SacRefundCommand> wpRefundList = sacRefundDetailDao.b2bRefundTrxInfo(paramMap);

    wpRefundList = this.handleSacRefundCommandList(wpRefundList);
    for (SacRefundCommand rCmd : wpRefundList) {
      if ("2".equals(rCmd.getTrxState())) {
        rCmd.setTrxStateNameTmp(rCmd.getTrxStateName() + "(注意：重复下载!)");
      } else if ("1".equals(rCmd.getTrxState())) {
        rCmd.setTrxStateNameTmp("已下载待处理");
      } else {
        rCmd.setTrxStateNameTmp(rCmd.getTrxStateName());
      }
    }

    Map<String, Object> paramMap2 = new HashMap<String, Object>();
    paramMap2.put("trxState", 2);
    paramMap2.put("idGrp", idBuff.substring(0, idBuff.lastIndexOf(",")).toString());
    sacRefundDetailDao.batchUpdateB2bRefundTrxInfo(paramMap2);

    logger.debug("网银退款记录数:" + wpRefundList.size());
    Map<String, Object> statMap = new HashMap<String, Object>();

    // 所有付款方必须相同
    BigDecimal total = new BigDecimal("0");
    Set<String> nodeCodes = new HashSet<String>();
    for (SacRefundCommand w : wpRefundList) {
      nodeCodes.add(w.getBankNodeCode());
      total = total.add(w.getPayAmount());
    }

    logger.debug("合计金额:" + total);
    if (nodeCodes.size() > 1) {
      throw new RuntimeException("付款银行必须相同");
    }

    String nodeCode = wpRefundList.get(0).getBankNodeCode();
    String bankName = wpRefundList.get(0).getBankNodeCodeName();
    statMap.put("total", total);
    statMap.put("draccCode", nodeCode);
    statMap.put("draccBank", bankName);
    StringBuffer serialNo = new StringBuffer();
    serialNo.append(DateUtil.getFomateDate(new Date(), "yyyyMMdd"));
    // serialNo.append("-" + SequenceCreatorUtil.getTableId());
    String batchId = sequenceCreatorService.getSerialNo("SAC_REFUND_BATCH_SEQ", 3);
    statMap.put("batchSerialNo", batchId);
    statMap.put("wpRefunds", wpRefundList);

    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    try {
      XlsExporter.export(statMap, "wpRefunds", tplName, stream);
    } catch (Exception e) {
      logger.error("downLoad allocatedetail throw exception", e);
      throw new RuntimeException("下载失败", e);
    }
    return stream;
  }

  /**
   * 对原交易查询结果枚举值处理
   * 
   * @param otrxInfoList
   */

  public List<SacRefundCommand> handleSacRefundCommandList(List<SacRefundCommand> sacRefundCommandList) {
    Map<String, Object> refundStateMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_REFUND_STATE);
    Map<String, Object> bankMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BANK);

    for (SacRefundCommand command : sacRefundCommandList) {
      // 退款状态类型
      command.setTrxStateName(refundStateMap.get(command.getTrxState()) == null ? "-" : refundStateMap.get(command.getTrxState()).toString());
      // 付款方银行节点类型
      command.setBankNodeCodeName(bankMap.get(command.getBankNodeCode()) == null ? "-" : bankMap.get(command.getBankNodeCode()).toString());
    }
    return sacRefundCommandList;
  }

  /**
   * 读取B2B退款批次文件处理
   * 
   * @param otrxInfoList
   */
  @Override
  public SacRefundBatch readOflXls(String oflExcelFileName, InputStream inputStream, SecurityOperator person) throws Exception {
    Workbook book = Workbook.getWorkbook(inputStream);
    //第一个sheet
    Sheet sheet = book.getSheet(0);
    int columns = sheet.getColumns();
    if (columns < 9) {
      throw new OFLOnloadException("格式错误！列数不足");
    }
    //第二行第二列批次号
    String batchSerNo = ExcelUtil.readCell(sheet, 1, 1);
    if (StringUtil.isBlank(batchSerNo)) {
      throw new OFLOnloadException("批次号不存在或格式错误！");
    }
    //检查是否存在
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("batchSerialNo", batchSerNo);
    SacRefundBatch depBatch = sacRefundBatchDao.b2bRefundBatchInfoForOnly(paramMap);
    if (depBatch != null) {
      if (OFLBatchState.ReviewPass.code().equals(depBatch.getBatchState())) {
        throw new OFLOnloadException("该批次已存在，不能重复提交！");
      }
      //先更新原来的指令的批次号为空
      Map<String, Object> paramMapUp = new HashMap<String, Object>();
      paramMapUp.put("expBatch", batchSerNo);

      List<SacRefundCommand> list = sacRefundDetailDao.b2bRefundTrxInfo(paramMapUp);
      if (list != null && list.size() > 0) {
        for (SacRefundCommand cmd : list) {
          cmd.setExpBatch("N/A");
          sacRefundDetailDao.update(cmd);
        }
      }
      //再删掉原来的批次
      Map<String, Object> paramMapBatch = new HashMap<String, Object>();
      paramMapBatch.put("oflWithdrawBatchId", depBatch.getOflWithdrawBatchId());
      sacRefundBatchDao.b2bRefundBatchInfoForDelete(paramMapBatch);
    }
    //新建批次对象
    SacRefundBatch oflbatch = new SacRefundBatch();
    oflbatch.setOflWithdrawBatchId(SequenceCreatorUtil.getTableId());
    oflbatch.setBatchSerialNo(batchSerNo);
    oflbatch.setCreateTime(new Date());
    oflbatch.setBatchState(OFLBatchState.Init.code());
    oflbatch.setOperatorId(Long.parseLong(person.getOperId()));
    oflbatch.setOperatorName(person.getUsername());

    //第三行第二列收款银行代码
    String bankCode = ExcelUtil.readCell(sheet, 1, 2);
    oflbatch.setBankNodeCode(bankCode);
    //保存获得id
    oflbatch = sacRefundBatchDao.save(oflbatch);
    int i = 4;//初始化从第5行还是读取详细信息
    BigDecimal tmaount = new BigDecimal(0);
    Set<String> seriaSet = new HashSet<String>();
    for (i = 4; i < sheet.getRows(); i++) {
      //3向必填检测
      //----退款流水号
      String serialNo = ExcelUtil.readCell(sheet, 0, i);
      if (StringUtil.isBlank(serialNo)) {//|| serialNo.length() != 26
        if (i == 4) {
          throw new OFLOnloadException("第" + (i + 1) + "行退款流水号错误");
        }
        break;//至少有一条记录，如果发现下一行格式不同一认为记录结束
      }
      //  为了测试暂时注释，请勿删除 谢谢
      //      if (!serialNo.startsWith("EPLATBB")) {
      //        throw new OFLOnloadException("退款流水号不是以平台代码开头，流水号：" + serialNo);
      //      }
      if (seriaSet.contains(serialNo)) {
        throw new OFLOnloadException("流水号重复，流水号：" + serialNo);
      } else {
        seriaSet.add(serialNo);
      }
      //----金额
      BigDecimal payAmount;
      try {
        payAmount = new BigDecimal(ExcelUtil.readCell(sheet, 6, i));
      } catch (Exception e) {
        throw new OFLOnloadException("第" + (i + 1) + "行金额为空或不正确");
      }

      Map<String, Object> paramMapCmd = new HashMap<String, Object>();
      paramMapCmd.put("trxSerialNo", serialNo);
      SacRefundCommand command = sacRefundDetailDao.b2bRefundTrxInfoForOnly(paramMapCmd);

      if (command == null || command.getPayAmount().compareTo(payAmount) != 0) {
        throw new OFLOnloadException("第" + (i + 1) + "行金额与退款流水不符");
      }

      //----状态
      String withdrawState = ExcelUtil.readCell(sheet, 8, i);
      if (StringUtil.isBlank(withdrawState) || !(withdrawState.equalsIgnoreCase("Y") || withdrawState.equalsIgnoreCase("N"))) {
        throw new OFLOnloadException("第" + (i + 1) + "行退款状态为空或格式不正确");
      }
      if (withdrawState.equalsIgnoreCase("Y")) {
        command.setTrxState(Constants.REFUND_STATE_SUCC);
      } else {
        command.setTrxState(Constants.REFUND_STATE_NOT_SUCC);
      }
      command.setExpBatch(batchSerNo);
      command.setRtrxSerialNo(ExcelUtil.readCell(sheet, 7, i));
      //检测银行流水是否重复
      if (StringUtil.isNotBlank(command.getRtrxSerialNo())) {
        Map<String, Object> paramMap2 = new HashMap<String, Object>();
        paramMap2.put("rtrxSerialNo", command.getRtrxSerialNo());
        List<SacRefundCommand> list = sacRefundDetailDao.b2bRefundTrxInfo(paramMap2);
        if (list != null && list.size() > 0) {
          throw new OFLOnloadException("银行流水号重复，流水号：" + command.getRtrxSerialNo());
        }
      }
      tmaount = tmaount.add(payAmount);
      sacRefundDetailDao.update(command);//更新指令数据
    }
    oflbatch.setBatchTamount(tmaount);
    oflbatch.setBatchTcount(new Long(i - 4));
    sacRefundBatchDao.update(oflbatch);
    return oflbatch;
  }

  @Override
  public List<SacRefundBatch> b2bRefundBatchInfoForPaging(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return sacRefundBatchDao.b2bRefundBatchForPaging(paramMap);
  }

  @Override
  /**
   * 对原交易查询结果枚举值处理
   * 
   * @param otrxInfoList
   */
  public List<SacRefundBatch> handleSacRefundBatchList(List<SacRefundBatch> sacRefundBacthList) {
    Map<String, Object> refundBatchStateMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_REFUND_BATCH_STATE);

    for (SacRefundBatch batch : sacRefundBacthList) {
      // 退款批次状态类型
      batch.setBatchStateName(refundBatchStateMap.get(batch.getBatchState()) == null ? "-" : refundBatchStateMap.get(batch.getBatchState()).toString());
    }
    return sacRefundBacthList;
  }

  @Override
  public int b2bRefundBatchCounts(Map<String, Object> paramMap) {
    return sacRefundBatchDao.b2bRefundBatchCounts(paramMap);
  }

  @Override
  public SacRefundBatch b2bRefundBatchInfoForOnly(Map<String, Object> paramMap) {
    return sacRefundBatchDao.b2bRefundBatchInfoForOnly(paramMap);

  }

  @Override
  public void reviewB2BRefundBatchPass(String[] batchIdNos, SecurityOperator person) {
    if (batchIdNos != null && batchIdNos.length > 0) {
      for (String batchId : batchIdNos) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("oflWithdrawBatchId", batchId);
        SacRefundBatch depBatch = sacRefundBatchDao.b2bRefundBatchInfoForOnly(paramMap);
        if (depBatch == null) {
          throw new RuntimeException("修改状态时未找到批次！批次Id号：" + batchId);
        }
        Map<String, Object> paramMapUp = new HashMap<String, Object>();
        paramMapUp.put("expBatch", depBatch.getBatchSerialNo());
        List<SacRefundCommand> list = sacRefundDetailDao.b2bRefundTrxInfo(paramMapUp);
        Boolean allSuccFlag = Boolean.TRUE;
        if (list != null && list.size() > 0) {
          for (SacRefundCommand command : list) {
            String rtnCode = "";
            if ("0".equals(Constants.INTERFACE_SWITCH)) {
              //生产环境
              rtnCode = ConstantParams.RTN_CODE_SUCCESSS;
            } else if ("1".equals(Constants.INTERFACE_SWITCH)) {
              //测试 开发环境
              Map<String, String> paramPassMap = new HashMap<String, String>();
              paramPassMap.put("batchSerialNo", depBatch.getBatchSerialNo());
              paramPassMap.put("batchTcount", String.valueOf(depBatch.getBatchTcount()));
              paramPassMap.put("batchTamount", String.valueOf(depBatch.getBatchTamount()));
              paramPassMap.put("bankNodeCode", depBatch.getBankNodeCode());
              paramPassMap.put("otrxSerialNo", command.getTrxSerialNo());
              paramPassMap.put("payAmount", command.getPayAmount().toString());//27位渠道号
              paramPassMap.put("craccNo", command.getCraccNo());
              paramPassMap.put("craccName", command.getCraccName());
              paramPassMap.put("craccBankBranch", command.getCraccBankBranch());
              paramPassMap.put("bankSerialNo", command.getRtrxSerialNo());
              paramPassMap.put("signValue", "");
              // 调用B2B系统接口
              //UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REFUND_B2B_SUCC_NOTICE_URL);
              String url = refundB2BUrl;
              logger.info("B2B退款成功请求URL：" + url);
              logger.info("B2B退款成功请求参数：" + paramPassMap.toString());
              //String response = UrlUtil.methodPost(url, paramPassMap);
              JSONObject reqJson = JSONObject.fromObject(paramPassMap);
              Map<String, String> reqMap = new HashMap<String, String>();
              reqMap.put("msg", reqJson.toString());
              String response = UrlUtil.methodPost(url, reqMap);

              JSONObject fromObject = JSONObject.fromObject(response);
              rtnCode = fromObject.getString("rtnCode");
            }
            if (!ConstantParams.RTN_CODE_SUCCESSS.equals(rtnCode)) {
              allSuccFlag = false;
            }
          }
        }
        if (allSuccFlag) {
          //更新批次状态为复核成功
          switchB2BRefundBatchState(person, depBatch, OFLBatchState.ReviewPass.code(), "审核通过");
        }
      }

    }

  }

  @Override
  public void reviewB2BRefundBatchReject(String[] batchIdNos, SecurityOperator person) {
    if (batchIdNos != null && batchIdNos.length > 0) {
      for (String batchId : batchIdNos) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("oflWithdrawBatchId", batchId);
        SacRefundBatch depBatch = sacRefundBatchDao.b2bRefundBatchInfoForOnly(paramMap);
        if (depBatch == null) {
          throw new RuntimeException("修改状态时未找到批次！批次Id号：" + batchId);
        }
        //更新批次状态为复核成功
        switchB2BRefundBatchState(person, depBatch, OFLBatchState.ReviewReject.code(), "复核人员审核不通过");
      }

    }

  }

  private void switchB2BRefundBatchState(SecurityOperator person, SacRefundBatch refundBatch, String code, String reson) {
    refundBatch.setBatchState(code);
    refundBatch.setAuditorId(Long.parseLong(person.getOperId()));
    refundBatch.setAuditorName(person.getUsername());
    refundBatch.setAuditTime(new Date());
    refundBatch.setAuditMemo(reson);
    sacRefundBatchDao.update(refundBatch);

  }
}
