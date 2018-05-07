package net.easipay.cbp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 线下退款转账批次表
 */

@Entity
@Table(name = "SAC_REFUND_BATCH")
public class SacRefundBatch implements Serializable {

  private static final long serialVersionUID = -240677333492678978L;

  private Long oflWithdrawBatchId;// 线下转出批次ID

  private String batchSerialNo;// 线下转账批次号

  private Long batchTcount = 0l;// 总笔数

  private BigDecimal batchTamount = new BigDecimal(0L);// 总金额

  private Date createTime;// 记录创建时间

  private String batchState;// 批次复核状态：00: 待处理;；02 复核失败； 10 复核通过

  private Long operatorId;// 批次经办人ID

  private String operatorName;// 批次经办人

  private Long auditorId;// 手工匹配审核用户ID

  private String auditorName;// 手工匹配审核用户名

  private Date auditTime;// 审批时间

  private String auditMemo;// 审批意见

  private String bankNodeCode;// 银行节点代码

  private String batchStateName;

  public String getBankNodeCode() {
    return bankNodeCode;
  }

  public void setBankNodeCode(String bankNodeCode) {
    this.bankNodeCode = bankNodeCode;
  }

  public Long getOflWithdrawBatchId() {
    return oflWithdrawBatchId;
  }

  public void setOflWithdrawBatchId(Long oflWithdrawBatchId) {
    this.oflWithdrawBatchId = oflWithdrawBatchId;
  }

  public String getBatchSerialNo() {
    return batchSerialNo;
  }

  public void setBatchSerialNo(String batchSerialNo) {
    this.batchSerialNo = batchSerialNo;
  }

  public BigDecimal getBatchTamount() {
    return batchTamount;
  }

  public void setBatchTamount(BigDecimal batchTamount) {
    this.batchTamount = batchTamount;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getBatchState() {
    return batchState;
  }

  public void setBatchState(String batchState) {
    this.batchState = batchState;
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

  public String getAuditMemo() {
    return auditMemo;
  }

  public void setAuditMemo(String auditMemo) {
    this.auditMemo = auditMemo;
  }

  public Long getBatchTcount() {
    return batchTcount;
  }

  public void setBatchTcount(Long batchTcount) {
    this.batchTcount = batchTcount;
  }

  public String getBatchStateName() {
    return batchStateName;
  }

  public void setBatchStateName(String batchStateName) {
    this.batchStateName = batchStateName;
  }

  @Override
  public String toString() {
    return "OFLWithdrawBatch [auditMemo=" + auditMemo + ", auditTime=" + auditTime + ", auditorId=" + auditorId + ", auditorName=" + auditorName + ", bankNodeCode=" + bankNodeCode
        + ", batchSerialNo=" + batchSerialNo + ", batchState=" + batchState + ", batchTamount=" + batchTamount + ", batchTcount=" + batchTcount + ", createTime=" + createTime
        + ", oflWithdrawBatchId=" + oflWithdrawBatchId + ", operatorId=" + operatorId + ", operatorName=" + operatorName + "]";
  }

  public String toViewInfo() {
    return " 批次号：" + batchSerialNo + " 总金额：" + batchTamount + " 总笔数：" + batchTcount;
  }

}
