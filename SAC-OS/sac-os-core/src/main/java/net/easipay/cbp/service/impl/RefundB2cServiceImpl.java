package net.easipay.cbp.service.impl;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.dao.ISacB2cexRefundApplyDao;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.model.SacB2cExrefundApply;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.service.IRefundB2cService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.CsvStringBuilder;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.UrlUtil;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefundB2cServiceImpl implements IRefundB2cService {

  Logger logger = LoggerFactory.getLogger(RefundB2cServiceImpl.class);

  @Autowired
  private ISacB2cexRefundApplyDao sacB2cexRefundApplyDao;
  @Autowired
  private ISacOtrxInfoDao sacOtrxInfoDao;

  private String refundB2CUrl;
  private String refundB2CSettlementExchangeUrl;

  @Override
  public List<SacB2cExrefundApply> b2cRefundTrxInfoForPaging(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return sacB2cexRefundApplyDao.b2cRefundTrxInfoForPaging(paramMap);
  }

  @Override
  public int b2cRefundTrxCounts(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return sacB2cexRefundApplyDao.b2cRefundTrxCounts(paramMap);

  }

  @Override
  public ByteArrayOutputStream exportExcel(List<SacB2cExrefundApply> applyList) throws Exception {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    stream.write(this.getExcelTitle().getBytes("gb2312"));
    for (int i = 0; i < applyList.size(); i++) {
      stream.write(toCsvString(applyList.get(i)).getBytes("gb2312"));
      if (i == 1000) {
        stream.flush();
      }
    }
    stream.flush();
    return stream;
  }

  public String getExcelTitle() {
    CsvStringBuilder sb = new CsvStringBuilder();
    sb.append("原交易流水号").append("商户名称").append("退款银行").append("外部交易流水号").append("支付金额(元)").append("退款金额(元)").append("购汇金额(元)").append("购汇状态").append("付汇状态").append("退款提交日期").append("退款状态");
    return sb.toString();
  }

  public String toCsvString(SacB2cExrefundApply curApply) {
    List<SacB2cExrefundApply> applyList = new ArrayList<SacB2cExrefundApply>();
    applyList.add(curApply);
    this.handleSacRefundApplyList(applyList);
    CsvStringBuilder sb = new CsvStringBuilder();
    sb.append(curApply.getOtrxSerialNo()).append(curApply.getMerchantName()).append(curApply.getBankName()).append(curApply.getEtrxSerialNo()).append(curApply.getPayAmount())
        .append(curApply.getApplyAmount()).append(curApply.getFrnAmount()).append(curApply.getPurchStateName()).append(curApply.getRemStateName()).append(curApply.getRefundTime())
        .append(curApply.getApplyStateName());
    return sb.toString();
  }

  @Override
  /**
   * 对B2C交易查询结果枚举值处理
   * 
   * @param otrxInfoList
   */
  public List<SacB2cExrefundApply> handleSacRefundApplyList(List<SacB2cExrefundApply> sacRefundApplyList) {
    Map<String, Object> refundApplyStateMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_B2C_REFUND_STATE);
    Map<String, Object> refundPurStateMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_B2C_PUR_STATE);
    Map<String, Object> refundRemStateMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_B2C_REM_STATE);
    Map<String, Object> bankMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BANK);
    Map<String, Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);//币种
    for (SacB2cExrefundApply apply : sacRefundApplyList) {
      // 退款状态类型
      apply.setApplyStateName(refundApplyStateMap.get(apply.getApplyState()) == null ? "-" : refundApplyStateMap.get(apply.getApplyState()).toString());
      apply.setPurchStateName(refundPurStateMap.get(apply.getPurchState()) == null ? "-" : refundPurStateMap.get(apply.getPurchState()).toString());
      apply.setRemStateName(refundRemStateMap.get(apply.getRemState()) == null ? "-" : refundRemStateMap.get(apply.getRemState()).toString());
      apply.setBankName(bankMap.get(apply.getBankNodeCode()) == null ? "-" : bankMap.get(apply.getBankNodeCode()).toString());
      apply.setExBankName(bankMap.get(apply.getExBankCode()) == null ? "-" : bankMap.get(apply.getExBankCode()).toString());
      apply.setCrtCurrencyName(ccyMap.get(apply.getCrtCurrency()) == null ? "-" : ccyMap.get(apply.getCrtCurrency()).toString());
    }
    return sacRefundApplyList;
  }

  @Override
  /**
   * 对B2C交易查询结果枚举值处理
   * 
   * @param otrxInfoList
   */
  public List<SacB2cExrefundApply> b2cRefundTrxInfo(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return sacB2cexRefundApplyDao.b2cRefundTrxInfo(paramMap);
  }

  @Override
  /**
   * 处理B2c退款经办
   * 
   * @param otrxInfoList
   */
  public void b2cRefundOperate(Long[] ids, SecurityOperator person) throws Exception {
    for (Long selectId : ids) {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("exrefundApplyId", selectId);
      SacB2cExrefundApply sacB2cExrefundApply = sacB2cexRefundApplyDao.b2cRefundTrxInfoForOnly(paramMap);
      UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REFUND_B2C_STATE_NEW);
      if (unifiedConfig.getDicValue().equals(sacB2cExrefundApply.getApplyState())) {
        operateRefundApply(sacB2cExrefundApply, person);
      } else {
        logger.info("退款申请的不为待经办状态，申请的ID:" + sacB2cExrefundApply.getExrefundApplyId());
        throw new RuntimeException("退款申请的不为待经办状态，申请的流水号:" + sacB2cExrefundApply.getRefundSerialNo());
      }
    }

  }

  private void operateRefundApply(SacB2cExrefundApply sacB2cExrefundApply, SecurityOperator user) throws Exception {

    String payTrxSerialNo = sacB2cExrefundApply.getOtrxSerialNo();
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("otrxSerialNo", payTrxSerialNo);
    paramMap.put("trxType", Constants.REFUND_PUR_EXCHANGE_TYPE);

    List<SacOtrxInfo> trxList = sacOtrxInfoDao.selectOtrxInfoByCondition(paramMap);

    if (trxList == null || trxList.isEmpty()) {
      logger.info("不存在购付汇交易，原交易流水号:" + sacB2cExrefundApply.getOtrxSerialNo());
      throw new RuntimeException("不存在购付汇交易，申请的流水号:" + sacB2cExrefundApply.getRefundSerialNo());
    }

    if (sacB2cExrefundApply.getFrnAmount() != null) {
      if (sacB2cExrefundApply.getFrnAmount().compareTo(new BigDecimal(0.00)) < 0) {
        logger.info("购汇金额不为正数，申请的ID:" + sacB2cExrefundApply.getExrefundApplyId());
        throw new RuntimeException("购汇金额不为正数，申请的流水号:" + sacB2cExrefundApply.getRefundSerialNo());
      }
      //      if (sacB2cExrefundApply.getFrnAmount().compareTo(trxList.get(0).getPayAmount()) == 1) {
      //        logger.info("退款申请的购汇金额大于总金额，申请的ID:" + sacB2cExrefundApply.getExrefundApplyId());
      //        throw new RuntimeException("退款申请的购汇金额大于总金额，申请的流水号:" + sacB2cExrefundApply.getRefundSerialNo());
      //      }
    }
    //    if (SttConstants.EXCHANGE_PURCHASE_STATE_A.equals(detail.getPurchState()) || SttConstants.EXCHANGE_REMITTANCE_STATE_A.equals(detail.getRemState()) || "P".equals(detail.getPurchState())
    //        || "P".equals(detail.getRemState())) {
    //      logger.info("购付汇状态为处理中，申请的ID：" + sttExrefundApply.getExrefundApplyId());
    //      throw new RuntimeException("购付汇状态为处理中，申请的流水号:" + sttExrefundApply.getRefundSerialNo());
    //    }
    //    //未购汇(购汇失败“F”也算是未购汇)
    //    if (SttConstants.EXCHANGE_PURCHASE_STATE_N.equals(detail.getPurchState()) || SttConstants.EXCHANGE_PURCHASE_STATE_F.equals(detail.getPurchState())) {
    //      //dealNoPurchDetail(detail,sttExrefundApply);
    //      //        logger.info("未购汇的明细，申请的ID："+sttExrefundApply.getExrefundApplyId());
    //      //        throw new RuntimeException("未购汇的明细，申请的流水号:"+sttExrefundApply.getRefundSerialNo());
    //      //购汇成功，但未付汇的(付汇失败“F”也算是未付汇)
    //    } else if (SttConstants.EXCHANGE_REMITTANCE_STATE_N.equals(detail.getRemState()) || SttConstants.EXCHANGE_REMITTANCE_STATE_F.equals(detail.getRemState())) {
    //      dealPurchDetail(detail, sttExrefundApply);
    //
    //      //付汇成功
    //    } else {
    //      dealRemDetail(detail, sttExrefundApply);
    //
    //    }

    //设置成已处理待复核
    UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REFUND_B2C_STATE_WAIT_CHECK);
    sacB2cExrefundApply.setApplyState(unifiedConfig.getDicValue());
    if (user == null) {
      sacB2cExrefundApply.setOperatorId(0L);
      sacB2cExrefundApply.setOperatorName("system");
    } else {
      sacB2cExrefundApply.setOperatorId(Long.parseLong(user.getOperId()));
      sacB2cExrefundApply.setOperatorName(user.getUsername());
    }
    sacB2cExrefundApply.setOperateTime(new Date());
    sacB2cexRefundApplyDao.update(sacB2cExrefundApply);
  }

  @Override
  public void b2cRefundCheckPass(Long[] ids, SecurityOperator person) throws Exception {
    for (Long selectId : ids) {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("exrefundApplyId", selectId);
      SacB2cExrefundApply sacB2cExrefundApply = sacB2cexRefundApplyDao.b2cRefundTrxInfoForOnly(paramMap);
      UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REFUND_B2C_STATE_WAIT_CHECK);
      if (unifiedConfig.getDicValue().equals(sacB2cExrefundApply.getApplyState())) {
        auditPassRefundApply(sacB2cExrefundApply, person);
      } else {
        logger.info("退款申请的不为待复核状态，申请的ID:" + sacB2cExrefundApply.getExrefundApplyId());
        throw new RuntimeException("退款申请的不为待复核状态，申请的流水号:" + sacB2cExrefundApply.getRefundSerialNo());
      }
    }
  }

  private void auditPassRefundApply(SacB2cExrefundApply sacB2cExrefundApply, SecurityOperator user) throws Exception {
    //    if ("BOS0000".equalsIgnoreCase(sacB2cExrefundApply.getExBankCode())) {
    //      throw new RuntimeException("该银行不支持退款");
    //    }
    //调用B2C接口通知退款成功
    String rtnCode = "";
    if ("0".equals(Constants.INTERFACE_SWITCH)) {
      //生产环境
      rtnCode = ConstantParams.RTN_CODE_SUCCESSS;
    } else if ("1".equals(Constants.INTERFACE_SWITCH)) {
      //测试 开发环境
      rtnCode = this.sendToB2CSys(sacB2cExrefundApply);
    }
    //String rtnCode = "000000";
    if (rtnCode.startsWith(ConstantParams.RTN_CODE_SUCCESSS)) {
      //更新原始交易表退款交易状态为成功
      String refundTrxSerialNo = sacB2cExrefundApply.getRefundSerialNo();
      SacOtrxInfo selectTrxInfo = new SacOtrxInfo();
      selectTrxInfo.setTrxSerialNo(refundTrxSerialNo);
      SacOtrxInfo refundTrxInfo = sacOtrxInfoDao.selectSacOtrxInfoById(selectTrxInfo);

      JwsClient client = new JwsClient("SAC-AC-0002");
      JwsResult jwsResult = client.putParam("trxState", "S").putParam("trxSerialNo", refundTrxSerialNo).putParam("etrxSerialNo", refundTrxInfo.getEtrxSerialNo()).putParam("trxSuccTime", new Date())
          .call(true);
      logger.info("B2C退款更新原始交易表退款交易状态：" + jwsResult.getCode() + "描述信息:" + jwsResult.getMessage());
      if ("000000".equals(jwsResult.getCode())) {
        //设置成已复核
        UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REFUND_B2C_STATE_CHECK_SUCC);
        sacB2cExrefundApply.setApplyState(unifiedConfig.getDicValue());// 已复核
        if (user == null) {
          sacB2cExrefundApply.setAuditorId(0L);
          sacB2cExrefundApply.setAuditorName("system");
        } else {
          sacB2cExrefundApply.setAuditorId(Long.parseLong(user.getOperId()));
          sacB2cExrefundApply.setAuditorName(user.getUsername());
        }
        sacB2cExrefundApply.setAuditTime(new Date());
        sacB2cexRefundApplyDao.update(sacB2cExrefundApply);
        //调用购结汇系统通知结汇
        if ("1".equals(Constants.INTERFACE_SWITCH)) {
          this.sendToPurExchangeSys(sacB2cExrefundApply);
        }
      } else {
        throw new RuntimeException("B2C退款更新原始交易表退款交易状态不成功:" + jwsResult.getMessage());
      }
    } else {
      throw new RuntimeException("B2C退款发送给B2C交易系统不成功:" + rtnCode);
    }
  }

  private void sendToPurExchangeSys(SacB2cExrefundApply sacB2cExrefundApply) {
    String payTrxSerialNo = sacB2cExrefundApply.getOtrxSerialNo();
    Map<String, String> paramPassMap = new HashMap<String, String>();
    paramPassMap.put("otrxSerialNo", payTrxSerialNo);//"EPLATPS2013051400000001029"
    //String url = "http://10.68.5.197:7001/ppat/settlementDetail/receiveSettlementDetail.do";
    //UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REFUND_B2C_REM_EXCHANGE_NOTICE_URL);
    String url = refundB2CSettlementExchangeUrl;
    logger.info("B2C退款发送给购付汇系统请求URL：" + url);
    logger.info("B2C退款发送给购付汇系统请求参数：" + paramPassMap.toString());
    String response = UrlUtil.methodPost(url, paramPassMap);
    if (response != null && !response.equals("")) {
      JSONObject fromObject = JSONObject.fromObject(response);
      String rtnCode = fromObject.getString("code");
      String rtnMessage = fromObject.getString("msg");
      logger.info("B2C退款发送给购付汇系统结汇状态码=" + rtnCode + ",状态描述=" + rtnMessage);
    } else {
      throw new RuntimeException("B2C退款发送给购付汇系统不成功");
    }
  }

  private String sendToB2CSys(SacB2cExrefundApply sacB2cExrefundApply) {
    String refundTrxSerialNo = sacB2cExrefundApply.getRefundSerialNo();
    Map<String, String> paramPassMap = new HashMap<String, String>();
    paramPassMap.put("trxSerialNo", refundTrxSerialNo);//"EPLATPS2014071400000037220"
    paramPassMap.put("trxCode", "3303");
    paramPassMap.put("rtnCode", ConstantParams.RTN_CODE_SUCCESSS);
    paramPassMap.put("rdoTime", DateUtil.getFomateDate(new Date(), "yyyy-mm-dd"));
    paramPassMap.put("rtnInfo", "退款成功");
    paramPassMap.put("payAmount", sacB2cExrefundApply.getApplyAmount().toString());

    //String url = "http://10.68.30.91:8080/ORDER-RECEIVER/ordinary/sacRefundNotice.do";
    //UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REFUND_B2C_SUCC_NOTICE_URL);
    String url = refundB2CUrl;
    logger.info("B2C退款发送给B2C交易系统请求URL：" + url);
    logger.info("B2C退款发送给B2C交易系统请求参数：" + paramPassMap.toString());
    String response = UrlUtil.methodPost(url, paramPassMap);
    logger.info("B2C退款发送给B2C交易系统状态码=" + response);
    return response;
  }

  @Override
  public SacB2cExrefundApply b2cRefundTrxInfoForOnly(Map<String, Object> paramMap) {
    SacB2cExrefundApply sacB2cExrefundApply = sacB2cexRefundApplyDao.b2cRefundTrxInfoForOnly(paramMap);
    return sacB2cExrefundApply;
  }

  public void setRefundB2CUrl(String refundB2CUrl) {
    this.refundB2CUrl = refundB2CUrl;
  }

  public void setRefundB2CSettlementExchangeUrl(String refundB2CSettlementExchangeUrl) {
    this.refundB2CSettlementExchangeUrl = refundB2CSettlementExchangeUrl;
  }

}
