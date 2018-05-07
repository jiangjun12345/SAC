package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
*线下预存交易表
* @author Administrator 
* @version  
* @since JDK 1.6
 */

@Entity
@Table(name = "SAC_DEPOSIT_DETAIL")
public class SacDepositDetail implements java.io.Serializable {
    
    private static final long serialVersionUID = 5128216952725189129L;
    
    private Long oflDepositId;// 线下存款ID
    
    private Long oflDepositBatchId;// 线下存款批次ID
    
    private String batchSerialNo;// 线下存款批次号
    
    private String trxSerialNo;// 交易流水号
    
    private String applyCode;// 申请识别号
    
    private String payCurrency;// 申请币种
    
    private BigDecimal payAmount ;// 申请金额
    
    private String dbtCode;// 付款方组织机构代码，预留
    
    private String draccNo;// 付款方账号
    
    private String draccName;// 付款账户名称
    
    private String draccBankName;// 付款方银行名称
    
    private String bankSerialNo;// 银行交易流水号
    
    private Date bankTrxDate;// 银行交易日期，即到账日期
    
    private String dealState;// 处理状态：00 未处理；01 成功待复核； 02 失败待复核；03 未成功待处理 ；04
                             // 手工销账待复核；05 手工销账复核不通过；10 预存成功
    
    private String checkState;// 核销状态：00 未核销； 10 核销成功
    
    private String dealMemo;// 备注：匹配失败原因
    
    private Date operTime;// 手工匹配经办时间
    
    private Long auditorId;// 手工匹配审核用户ID
    
    private String auditorName;// 手工匹配审核用户名
    
    private Date auditTime;// 手工匹配审核时间
    
    private Date createTime;// 记录创建时间
    
    private Date lastUpdateTime;// 最后更新时间
    
    private Long operatorId;// 手工匹配经办人ID
    
    private String operatorName;// 手工匹配经办人
    
    private Long chargeApplyId;
    
    private String memo;
    
    public Long getOflDepositId() {
        return oflDepositId;
    }
    
    public void setOflDepositId(Long oflDepositId) {
        this.oflDepositId = oflDepositId;
    }
    
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
    
    public String getTrxSerialNo() {
		return trxSerialNo;
	}

	public void setTrxSerialNo(String trxSerialNo) {
		this.trxSerialNo = trxSerialNo;
	}

	public String getApplyCode() {
        return applyCode;
    }
    
    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
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
    
    public String getDbtCode() {
        return dbtCode;
    }
    
    public void setDbtCode(String dbtCode) {
        this.dbtCode = dbtCode;
    }
    
    public String getDraccNo() {
        return draccNo;
    }
    
    public void setDraccNo(String draccNo) {
        this.draccNo = draccNo;
    }
    
    public String getDraccName() {
        return draccName;
    }
    
    public void setDraccName(String draccName) {
        this.draccName = draccName;
    }
    
    public String getDraccBankName() {
        return draccBankName;
    }
    
    public void setDraccBankName(String draccBankName) {
        this.draccBankName = draccBankName;
    }
    
    public String getBankSerialNo() {
        return bankSerialNo;
    }
    
    public void setBankSerialNo(String bankSerialNo) {
        this.bankSerialNo = bankSerialNo;
    }
    
    public Date getBankTrxDate() {
        return bankTrxDate;
    }
    
    public void setBankTrxDate(Date bankTrxDate) {
        this.bankTrxDate = bankTrxDate;
    }
    
    public String getDealState() {
        return dealState;
    }
    
    public void setDealState(String dealState) {
        this.dealState = dealState;
    }
    
    public String getCheckState() {
        return checkState;
    }
    
    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }
    
    public String getDealMemo() {
        return dealMemo;
    }
    
    public void setDealMemo(String dealMemo) {
        this.dealMemo = dealMemo;
    }
    
    public Date getOperTime() {
        return operTime;
    }
    
    public void setOperTime(Date operTime) {
        this.operTime = operTime;
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
    

	public Long getChargeApplyId() {
		return chargeApplyId;
	}

	public void setChargeApplyId(Long chargeApplyId) {
		this.chargeApplyId = chargeApplyId;
	}

	@Override
    public String toString() {
        return "OFLDeposit [applyCode="
                + applyCode
                + ", auditTime="
                + auditTime
                + ", auditorId="
                + auditorId
                + ", auditorName="
                + auditorName
                + ", bankSerialNo="
                + bankSerialNo
                + ", bankTrxDate="
                + bankTrxDate
                + ", batchSerialNo="
                + batchSerialNo
                + ", chargeApplyId="
                + chargeApplyId
                + ", checkState="
                + checkState
                + ", createTime="
                + createTime
                + ", dbtCode="
                + dbtCode
                + ", dealMemo="
                + dealMemo
                + ", dealState="
                + dealState
                + ", draccBankName="
                + draccBankName
                + ", draccName="
                + draccName
                + ", draccNo="
                + draccNo
                + ", lastUpdateTime="
                + lastUpdateTime
                + ", oflDepositBatchId="
                + oflDepositBatchId
                + ", oflDepositId="
                + oflDepositId
                + ", trxSerialNo="
                + trxSerialNo
                + ", operTime="
                + operTime
                + ", operatorId="
                + operatorId
                + ", operatorName="
                + operatorName
                + ", payAmount="
                + payAmount
                + ", payCurrency="
                + payCurrency
                + "]";
    }

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
    
}
