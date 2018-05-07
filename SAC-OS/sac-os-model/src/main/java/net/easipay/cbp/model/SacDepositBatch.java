/**
 * Copyright : www.easipay.net , 2009-2010
 * Project : PEPP
 * $Id$
 * $Revision$
 * Last Changed by $Author$ at $Date$
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * your name     2012-2-20        Initailized
 */
  
package net.easipay.cbp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 线下预存批次表
* ClassName: SacDepositBatch <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年2月26日 下午4:42:48 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */
@Entity
@Table(name = "SAC_DEPOSIT_BATCH")
public class SacDepositBatch implements Serializable {
    
    private static final long serialVersionUID = 4837058968056944617L;
    
    private Long oflDepositBatchId;// 线下转账批次ID
    
    private String batchSerialNo;// 线下转账批次号
    
    private Long batchTcount;// 总笔数
    
    private BigDecimal batchTamount;// 总金额
    
    private Date createTime;// 记录创建时间
    
    private String batchState;// 批次复核状态：00: 待处理;；02 复核失败； 10 复核通过
    
    private Long operatorId;// 批次经办人ID
    
    private String operatorName;// 批次经办人
    
    private Long auditorId;// 手工匹配审核用户ID
    
    private String auditorName;// 手工匹配审核用户名
    
    private Date auditTime;// 审批时间
    
    private String auditMemo;// 审批意见
    
    private String craccNodeCode;// 收款银行节点代码
    
    private String craccBankName;// 收款银行名称
    
    
    public Long getOflDepositBatchId() {
        return oflDepositBatchId;
    }
    
    public void setOflDepositBatchId(Long oflDepositBatchId) {
        this.oflDepositBatchId = oflDepositBatchId;
    }
    
    public String getBatchSerialNo() {
        return batchSerialNo;
    }
    
    public void setBatchSerialNo(String batchSerialNo) {
        this.batchSerialNo = batchSerialNo;
    }
    
    public Long getBatchTcount() {
        return batchTcount;
    }
    
    public void setBatchTcount(Long batchTcount) {
        this.batchTcount = batchTcount;
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
    
    public String getCraccNodeCode() {
        return craccNodeCode;
    }

    
    public void setCraccNodeCode(String craccNodeCode) {
        this.craccNodeCode = craccNodeCode;
    }

    
    public String getCraccBankName() {
        return craccBankName;
    }

    
    public void setCraccBankName(String craccBankName) {
        this.craccBankName = craccBankName;
    }

    @Override
    public String toString() {
        return "OFLDepositBatch [auditMemo="
                + auditMemo
                + ", auditTime="
                + auditTime
                + ", auditorId="
                + auditorId
                + ", auditorName="
                + auditorName
                + ", batchSerialNo="
                + batchSerialNo
                + ", batchState="
                + batchState
                + ", batchTamount="
                + batchTamount
                + ", batchTcount="
                + batchTcount
                + ", craccBankName="
                + craccBankName
                + ", craccNodeCode="
                + craccNodeCode
                + ", createTime="
                + createTime
                + ", oflDepositBatchId="
                + oflDepositBatchId
                + ", operatorId="
                + operatorId
                + ", operatorName="
                + operatorName
                + "]";
    }

    public String toViewInfo() {
        return " 批次号："+oflDepositBatchId
                + " 总金额："+batchTamount
                + " 总笔数："+batchTcount;
    }
}
