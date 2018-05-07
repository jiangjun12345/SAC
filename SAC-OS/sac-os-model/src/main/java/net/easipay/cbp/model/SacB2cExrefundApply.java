package net.easipay.cbp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SAC_B2C_EXREFUND_APPLY")
public class SacB2cExrefundApply implements Serializable {

  private static final long serialVersionUID = 2846352267243147377L;

  @Column(name = "EXREFUND_APPLY_ID")
  private Long exrefundApplyId;

  @Column(name = "REFUND_SERIAL_NO")
  private String refundSerialNo;

  @Column(name = "REFUND_TIME")
  private Date refundTime;

  @Column(name = "REC_NCODE")
  private String recNcode;

  @Column(name = "MERCHANT_NAME")
  private String merchantName;

  @Column(name = "OTRX_CODE")
  private String otrxCode;

  @Column(name = "OTRX_SERIAL_NO")
  private String otrxSerialNo;

  @Column(name = "PAY_CURRENCY")
  private String payCurrency;

  @Column(name = "PAY_AMOUNT")
  private BigDecimal payAmount;

  @Column(name = "REFUND_AMOUNT")
  private BigDecimal refundAmount;

  @Column(name = "APPLY_AMOUNT")
  private BigDecimal applyAmount;

  @Column(name = "CNY_AMOUNT")
  private BigDecimal cnyAmount;

  @Column(name = "FRN_AMOUNT")
  private BigDecimal frnAmount;

  @Column(name = "TAX_AMOUNT")
  private BigDecimal taxAmount;

  @Column(name = "TAX_FLAG")
  private String taxFlag;

  @Column(name = "APPLY_STATE")
  private String applyState;

  @Column(name = "APPLY_NO")
  private Integer applyNo;

  @Column(name = "RSP_TIME")
  private Date rspTime;

  @Column(name = "OPERATOR_ID")
  private Long operatorId;

  @Column(name = "OPERATOR_NAME")
  private String operatorName;

  @Column(name = "OPERATE_TIME")
  private Date operateTime;

  @Column(name = "AUDITOR_ID")
  private Long auditorId;

  @Column(name = "AUDITOR_NAME")
  private String auditorName;

  @Column(name = "AUDIT_TIME")
  private Date auditTime;

  @Column(name = "SPT1")
  private String spt1;

  @Column(name = "SPT2")
  private String spt2;

  @Column(name = "SPT3")
  private String spt3;

  @Column(name = "MEMO")
  private String memo;

  @Column(name = "CREATE_TIME")
  private Date createTime;

  @Column(name = "LAST_UPDATE_TIME")
  private Date lastUpdateTime;

  @Column(name = "PURCH_STATE")
  private String purchState;

  @Column(name = "GOODS_AMOUNT")
  private BigDecimal goodsAmount;

  @Column(name = "TRANS_AMOUNT")
  private BigDecimal transAmount;

  @Column(name = "REM_STATE")
  private String remState;

  @Column(name = "BANK_NODE_CODE")
  private String bankNodeCode;

  @Column(name = "CRT_CURRENCY")
  private String crtCurrency;

  @Column(name = "CRT_AMOUNT")
  private BigDecimal crtAmount;

  @Column(name = "EXST_STATE")
  private String exstState;

  @Column(name = "RF_PAY_AMOUNT")
  private BigDecimal rfPayAmount;

  @Column(name = "REFUND_OPER_ID")
  private Long refundOperId;

  @Column(name = "REFUND_OPER_NAME")
  private String refundOperName;

  @Column(name = "REFUND_OPER_TIME")
  private Date refundOperTime;

  @Column(name = "ETRX_SERIAL_NO")
  private String etrxSerialNo;

  @Column(name = "REFUND_BATCH")
  private String refundBatch;

  @Column(name = "DRACC_NAME")
  private String draccName;

  @Column(name = "EX_BANK_CODE")
  private String exBankCode;

  @Transient
  private String bankName;
  @Transient
  private String payCurrencName;
  @Transient
  private String applyStateName;
  @Transient
  private String remStateName;
  @Transient
  private String purchStateName;

  @Transient
  private String exBankName;
  @Transient
  private String crtCurrencyName;

  public String toPurchState(String state) {
    if ("N".equals(state)) {
      return "未处理";
    } else if ("A".equals(state) || "P".equals(state)) {
      return "购汇中";
    } else if ("S".equals(state)) {
      return "购汇成功";
    } else if ("F".equals(state)) {
      return "处理失败";
    } else {
      return state;
    }
  }

  public String toRemState(String state) {
    if ("N".equals(state)) {
      return "未处理";
    } else if ("A".equals(state) || "P".equals(state)) {
      return "付汇中";
    } else if ("S".equals(state)) {
      return "付汇成功";
    } else if ("F".equals(state)) {
      return "处理失败";
    } else {
      return state;
    }
  }

  public String toApplyState(String state) {
    if ("00".equals(state)) {
      return "待经办";
    } else if ("02".equals(state)) {
      return "复核不通过";
    } else if ("10".equals(state)) {
      return "待复核";
    } else if ("20".equals(state)) {
      return "已复核";
    } else if ("90".equals(state)) {
      return "废弃";
    } else {
      return state;
    }
  }

  public SacB2cExrefundApply() {
  }

  public SacB2cExrefundApply(Long exrefundApplyId, String refundSerialNo, Date refundTime, String recNcode, String merchantName, String otrxCode, String otrxSerialNo, String payCurrency,
      BigDecimal payAmount, BigDecimal refundAmount, BigDecimal applyAmount, BigDecimal cnyAmount, BigDecimal frnAmount, BigDecimal taxAmount, String taxFlag, String applyState, Integer applyNo,
      Date rspTime, Long operatorId, String operatorName, Date operateTime, Long auditorId, String auditorName, Date auditTime, String spt1, String spt2, String spt3, String memo, Date createTime,
      Date lastUpdateTime, String purchState, BigDecimal goodsAmount, BigDecimal transAmount, String remState, String bankNodeCode, String crtCurrency, BigDecimal crtAmount, String exstState,
      BigDecimal rfPayAmount, Long refundOperId, String refundOperName, Date refundOperTime, String etrxSerialNo, String refundBatch, String draccName, String bankName, String payCurrencName,
      String applyStateName, String remStateName, String purchStateName) {
    super();
    this.exrefundApplyId = exrefundApplyId;
    this.refundSerialNo = refundSerialNo;
    this.refundTime = refundTime;
    this.recNcode = recNcode;
    this.merchantName = merchantName;
    this.otrxCode = otrxCode;
    this.otrxSerialNo = otrxSerialNo;
    this.payCurrency = payCurrency;
    this.payAmount = payAmount;
    this.refundAmount = refundAmount;
    this.applyAmount = applyAmount;
    this.cnyAmount = cnyAmount;
    this.frnAmount = frnAmount;
    this.taxAmount = taxAmount;
    this.taxFlag = taxFlag;
    this.applyState = applyState;
    this.applyNo = applyNo;
    this.rspTime = rspTime;
    this.operatorId = operatorId;
    this.operatorName = operatorName;
    this.operateTime = operateTime;
    this.auditorId = auditorId;
    this.auditorName = auditorName;
    this.auditTime = auditTime;
    this.spt1 = spt1;
    this.spt2 = spt2;
    this.spt3 = spt3;
    this.memo = memo;
    this.createTime = createTime;
    this.lastUpdateTime = lastUpdateTime;
    this.purchState = purchState;
    this.goodsAmount = goodsAmount;
    this.transAmount = transAmount;
    this.remState = remState;
    this.bankNodeCode = bankNodeCode;
    this.crtCurrency = crtCurrency;
    this.crtAmount = crtAmount;
    this.exstState = exstState;
    this.rfPayAmount = rfPayAmount;
    this.refundOperId = refundOperId;
    this.refundOperName = refundOperName;
    this.refundOperTime = refundOperTime;
    this.etrxSerialNo = etrxSerialNo;
    this.refundBatch = refundBatch;
    this.draccName = draccName;
    this.bankName = bankName;
    this.payCurrencName = payCurrencName;
    this.applyStateName = applyStateName;
    this.remStateName = remStateName;
    this.purchStateName = purchStateName;
  }

  public Long getExrefundApplyId() {
    return exrefundApplyId;
  }

  public void setExrefundApplyId(Long exrefundApplyId) {
    this.exrefundApplyId = exrefundApplyId;
  }

  public String getRefundSerialNo() {
    return refundSerialNo;
  }

  public void setRefundSerialNo(String refundSerialNo) {
    this.refundSerialNo = refundSerialNo;
  }

  public Date getRefundTime() {
    return refundTime;
  }

  public void setRefundTime(Date refundTime) {
    this.refundTime = refundTime;
  }

  public String getRecNcode() {
    return recNcode;
  }

  public void setRecNcode(String recNcode) {
    this.recNcode = recNcode;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public String getOtrxCode() {
    return otrxCode;
  }

  public void setOtrxCode(String otrxCode) {
    this.otrxCode = otrxCode;
  }

  public String getOtrxSerialNo() {
    return otrxSerialNo;
  }

  public void setOtrxSerialNo(String otrxSerialNo) {
    this.otrxSerialNo = otrxSerialNo;
  }

  public String getPayCurrency() {
    return payCurrency;
  }

  public void setPayCurrency(String payCurrency) {
    this.payCurrency = payCurrency;
  }

  public BigDecimal getPayAmount() {
    return payAmount;
  }

  public void setPayAmount(BigDecimal payAmount) {
    this.payAmount = payAmount;
  }

  public BigDecimal getRefundAmount() {
    return refundAmount;
  }

  public void setRefundAmount(BigDecimal refundAmount) {
    this.refundAmount = refundAmount;
  }

  public BigDecimal getApplyAmount() {
    return applyAmount;
  }

  public void setApplyAmount(BigDecimal applyAmount) {
    this.applyAmount = applyAmount;
  }

  public BigDecimal getCnyAmount() {
    return cnyAmount;
  }

  public void setCnyAmount(BigDecimal cnyAmount) {
    this.cnyAmount = cnyAmount;
  }

  public BigDecimal getFrnAmount() {
    return frnAmount;
  }

  public void setFrnAmount(BigDecimal frnAmount) {
    this.frnAmount = frnAmount;
  }

  public BigDecimal getTaxAmount() {
    return taxAmount;
  }

  public void setTaxAmount(BigDecimal taxAmount) {
    this.taxAmount = taxAmount;
  }

  public String getTaxFlag() {
    return taxFlag;
  }

  public void setTaxFlag(String taxFlag) {
    this.taxFlag = taxFlag;
  }

  public String getApplyState() {
    return applyState;
  }

  public void setApplyState(String applyState) {
    this.applyState = applyState;
  }

  public Integer getApplyNo() {
    return applyNo;
  }

  public void setApplyNo(Integer applyNo) {
    this.applyNo = applyNo;
  }

  public Date getRspTime() {
    return rspTime;
  }

  public void setRspTime(Date rspTime) {
    this.rspTime = rspTime;
  }

  public Long getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(Long operatorId) {
    this.operatorId = operatorId;
  }

  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public Date getOperateTime() {
    return operateTime;
  }

  public void setOperateTime(Date operateTime) {
    this.operateTime = operateTime;
  }

  public Long getAuditorId() {
    return auditorId;
  }

  public void setAuditorId(Long auditorId) {
    this.auditorId = auditorId;
  }

  public String getAuditorName() {
    return auditorName;
  }

  public void setAuditorName(String auditorName) {
    this.auditorName = auditorName;
  }

  public Date getAuditTime() {
    return auditTime;
  }

  public void setAuditTime(Date auditTime) {
    this.auditTime = auditTime;
  }

  public String getSpt1() {
    return spt1;
  }

  public void setSpt1(String spt1) {
    this.spt1 = spt1;
  }

  public String getSpt2() {
    return spt2;
  }

  public void setSpt2(String spt2) {
    this.spt2 = spt2;
  }

  public String getSpt3() {
    return spt3;
  }

  public void setSpt3(String spt3) {
    this.spt3 = spt3;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getLastUpdateTime() {
    return lastUpdateTime;
  }

  public void setLastUpdateTime(Date lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }

  public String getPayCurrencName() {
    return payCurrencName;
  }

  public void setPayCurrencName(String payCurrencName) {
    this.payCurrencName = payCurrencName;
  }

  public String getPurchState() {
    return purchState;
  }

  public void setPurchState(String purchState) {
    this.purchState = purchState;
  }

  public BigDecimal getGoodsAmount() {
    return goodsAmount;
  }

  public BigDecimal getTransAmount() {
    return transAmount;
  }

  public void setGoodsAmount(BigDecimal goodsAmount) {
    this.goodsAmount = goodsAmount;
  }

  public void setTransAmount(BigDecimal transAmount) {
    this.transAmount = transAmount;
  }

  public String getRemState() {
    return remState;
  }

  public void setRemState(String remState) {
    this.remState = remState;
  }

  public String getCrtCurrency() {
    return crtCurrency;
  }

  public void setCrtCurrency(String crtCurrency) {
    this.crtCurrency = crtCurrency;
  }

  public BigDecimal getCrtAmount() {
    return crtAmount;
  }

  public String getExstState() {
    return exstState;
  }

  public BigDecimal getRfPayAmount() {
    return rfPayAmount;
  }

  public void setCrtAmount(BigDecimal crtAmount) {
    this.crtAmount = crtAmount;
  }

  public void setExstState(String exstState) {
    this.exstState = exstState;
  }

  public void setRfPayAmount(BigDecimal rfPayAmount) {
    this.rfPayAmount = rfPayAmount;
  }

  public Long getRefundOperId() {
    return refundOperId;
  }

  public String getRefundOperName() {
    return refundOperName;
  }

  public Date getRefundOperTime() {
    return refundOperTime;
  }

  public void setRefundOperId(Long refundOperId) {
    this.refundOperId = refundOperId;
  }

  public void setRefundOperName(String refundOperName) {
    this.refundOperName = refundOperName;
  }

  public void setRefundOperTime(Date refundOperTime) {
    this.refundOperTime = refundOperTime;
  }

  public String getBankNodeCode() {
    return bankNodeCode;
  }

  public void setBankNodeCode(String bankNodeCode) {
    this.bankNodeCode = bankNodeCode;
  }

  public String getEtrxSerialNo() {
    return etrxSerialNo;
  }

  public void setEtrxSerialNo(String etrxSerialNo) {
    this.etrxSerialNo = etrxSerialNo;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getRefundBatch() {
    return refundBatch;
  }

  public void setRefundBatch(String refundBatch) {
    this.refundBatch = refundBatch;
  }

  public String getDraccName() {
    return draccName;
  }

  public void setDraccName(String draccName) {
    this.draccName = draccName;
  }

  public String getApplyStateName() {
    return applyStateName;
  }

  public void setApplyStateName(String applyStateName) {
    this.applyStateName = applyStateName;
  }

  public String getRemStateName() {
    return remStateName;
  }

  public void setRemStateName(String remStateName) {
    this.remStateName = remStateName;
  }

  public String getPurchStateName() {
    return purchStateName;
  }

  public void setPurchStateName(String purchStateName) {
    this.purchStateName = purchStateName;
  }

  public String getExBankCode() {
    return exBankCode;
  }

  public void setExBankCode(String exBankCode) {
    this.exBankCode = exBankCode;
  }

  public String getExBankName() {
    return exBankName;
  }

  public void setExBankName(String exBankName) {
    this.exBankName = exBankName;
  }

  public String getCrtCurrencyName() {
    return crtCurrencyName;
  }

  public void setCrtCurrencyName(String crtCurrencyName) {
    this.crtCurrencyName = crtCurrencyName;
  }

}
