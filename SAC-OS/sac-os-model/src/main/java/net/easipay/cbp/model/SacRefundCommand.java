package net.easipay.cbp.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SAC_REFUND_COMMAND", schema = "SAC_SYN")
public class SacRefundCommand implements Serializable {

	private static final long serialVersionUID = -2922429917705296479L;
	@Column(name = "WP_REFUND_ID")
	private Long wpRefundId;
	@Column(name = "TRX_SERIAL_NO")
	private String trxSerialNo;
	@Column(name = "OTRX_SERIAL_NO")
	private String otrxSerialNo;
	@Column(name = "CREATE_TIME")
	private java.util.Date createTime;
	@Column(name = "PAY_AMOUNT")
	private BigDecimal payAmount;
	@Column(name = "CRT_CODE")
	private String crtCode;
	@Column(name = "BANK_NODE_CODE")
	private String bankNodeCode;
	@Column(name = "TRX_STATE")
	private String trxState;
	@Column(name = "RTRX_SERIAL_NO")
	private String rtrxSerialNo;
	@Column(name = "AUDIT_STATE")
	private String auditState;
	@Column(name = "LAST_UPDATE_TIME")
	private java.util.Date lastUpdateTime;
	@Column(name = "CRACC_NO")
	private String craccNo;
	@Column(name = "CRACC_NAME")
	private String craccName;
	@Column(name = "CRACC_BANK_BRANCH")
	private String craccBankBranch;
	@Column(name = "PAY_CURRENCY")
	private String payCurrency;
	@Column(name = "EXP_BATCH")
	private String expBatch;

	@Transient
	private String trxStateName;
	@Transient
	private String trxStateNameTmp;
	@Transient
	private String bankNodeCodeName;

	public Long getWpRefundId() {
		return wpRefundId;
	}

	public void setWpRefundId(Long wpRefundId) {
		this.wpRefundId = wpRefundId;
	}

	public String getTrxSerialNo() {
		return trxSerialNo;
	}

	public void setTrxSerialNo(String trxSerialNo) {
		this.trxSerialNo = trxSerialNo;
	}

	public String getOtrxSerialNo() {
		return otrxSerialNo;
	}

	public void setOtrxSerialNo(String otrxSerialNo) {
		this.otrxSerialNo = otrxSerialNo;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getCrtCode() {
		return crtCode;
	}

	public void setCrtCode(String crtCode) {
		this.crtCode = crtCode;
	}

	public String getBankNodeCode() {
		return bankNodeCode;
	}

	public void setBankNodeCode(String bankNodeCode) {
		this.bankNodeCode = bankNodeCode;
	}

	public String getTrxState() {
		return trxState;
	}

	public void setTrxState(String trxState) {
		this.trxState = trxState;
	}

	public String getRtrxSerialNo() {
		return rtrxSerialNo;
	}

	public void setRtrxSerialNo(String rtrxSerialNo) {
		this.rtrxSerialNo = rtrxSerialNo;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public java.util.Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(java.util.Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getCraccNo() {
		return craccNo;
	}

	public void setCraccNo(String craccNo) {
		this.craccNo = craccNo;
	}

	public String getCraccName() {
		return craccName;
	}

	public void setCraccName(String craccName) {
		this.craccName = craccName;
	}

	public String getCraccBankBranch() {
		return craccBankBranch;
	}

	public void setCraccBankBranch(String craccBankBranch) {
		this.craccBankBranch = craccBankBranch;
	}

	public String getPayCurrency() {
		return payCurrency;
	}

	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}

	public String getExpBatch() {
		return expBatch;
	}

	public void setExpBatch(String expBatch) {
		this.expBatch = expBatch;
	}

	public String getTrxStateName() {
		return trxStateName;
	}

	public void setTrxStateName(String trxStateName) {
		this.trxStateName = trxStateName;
	}

	public String getTrxStateNameTmp() {
		return trxStateNameTmp;
	}

	public void setTrxStateNameTmp(String trxStateNameTmp) {
		this.trxStateNameTmp = trxStateNameTmp;
	}

	public String getBankNodeCodeName() {
		return bankNodeCodeName;
	}

	public void setBankNodeCodeName(String bankNodeCodeName) {
		this.bankNodeCodeName = bankNodeCodeName;
	}

}
