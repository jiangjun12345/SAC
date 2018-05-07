package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SAC_OTRX_INFO", schema = "SAC_SYN")
public class SacOtrxInfo implements java.io.Serializable {

	private static final long serialVersionUID = -2922429917705296479L;
	private Long id;
	private String cusBillNo;
	private String platBillNo;
	private String trxSerialNo;
	private String otrxSerialNo;
	private String etrxSerialNo;
	private String trxState;
	private String craccCusCode;
	private String craccCusName;
	private String craccCusType;
	private String craccNo;
	private String craccName;
	private String craccNodeCode;
	private String craccBankName;
	private String draccCusCode;
	private String draccCusName;
	private String draccCusType;
	private String draccNo;
	private String draccName;
	private String draccNodeCode;
	private String draccBankName;
	private String payCurrency;
	private BigDecimal exRate;
	private BigDecimal payAmount;
	private String sacCurrency;
	private BigDecimal sacAmount;
	private Date trxTime;
	private Date createTime;
	private Date updateTime;
	private String bussType;
	private String trxType;
	private String payconType;
	private String payWay;
	private String sacFlag;
	private String innConFlag;
	private String innConStatus;
	private String chaConFlag;
	private String chaConStatus;
	private String accountStatus;
	private String reversalStatus;
	private String memo;
	private String trxBatchNo;
	private String trxErrDealType;
	private BigDecimal taxAmount;
	private BigDecimal transportExpenses;
	private BigDecimal channelCost;
	private BigDecimal cusCharge;
	private Date trxSuccTime;
	private String craccCardId;
	private String draccCardId;
	private String remittanceBatchId;
	private String trxStateDesc;
	
	public SacOtrxInfo() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCusBillNo() {
		return cusBillNo;
	}
	public void setCusBillNo(String cusBillNo) {
		this.cusBillNo = cusBillNo;
	}
	public String getPlatBillNo() {
		return platBillNo;
	}
	public void setPlatBillNo(String platBillNo) {
		this.platBillNo = platBillNo;
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
	public String getEtrxSerialNo() {
		return etrxSerialNo;
	}
	public void setEtrxSerialNo(String etrxSerialNo) {
		this.etrxSerialNo = etrxSerialNo;
	}
	public String getTrxState() {
		return trxState;
	}
	public void setTrxState(String trxState) {
		this.trxState = trxState;
	}
	public String getCraccCusCode() {
		return craccCusCode;
	}
	public void setCraccCusCode(String craccCusCode) {
		this.craccCusCode = craccCusCode;
	}
	public String getCraccCusName() {
		return craccCusName;
	}
	public void setCraccCusName(String craccCusName) {
		this.craccCusName = craccCusName;
	}
	public String getCraccCusType() {
		return craccCusType;
	}
	public void setCraccCusType(String craccCusType) {
		this.craccCusType = craccCusType;
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
	public String getDraccCusCode() {
		return draccCusCode;
	}
	public void setDraccCusCode(String draccCusCode) {
		this.draccCusCode = draccCusCode;
	}
	public String getDraccCusName() {
		return draccCusName;
	}
	public void setDraccCusName(String draccCusName) {
		this.draccCusName = draccCusName;
	}
	public String getDraccCusType() {
		return draccCusType;
	}
	public void setDraccCusType(String draccCusType) {
		this.draccCusType = draccCusType;
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
	public String getDraccNodeCode() {
		return draccNodeCode;
	}
	public void setDraccNodeCode(String draccNodeCode) {
		this.draccNodeCode = draccNodeCode;
	}
	public String getDraccBankName() {
		return draccBankName;
	}
	public void setDraccBankName(String draccBankName) {
		this.draccBankName = draccBankName;
	}
	public String getPayCurrency() {
		return payCurrency;
	}
	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}
	public BigDecimal getExRate() {
		return exRate;
	}
	public void setExRate(BigDecimal exRate) {
		this.exRate = exRate;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public String getSacCurrency() {
		return sacCurrency;
	}
	public void setSacCurrency(String sacCurrency) {
		this.sacCurrency = sacCurrency;
	}
	public BigDecimal getSacAmount() {
		return sacAmount;
	}
	public void setSacAmount(BigDecimal sacAmount) {
		this.sacAmount = sacAmount;
	}
	public Date getTrxTime() {
		return trxTime;
	}
	public void setTrxTime(Date trxTime) {
		this.trxTime = trxTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getBussType() {
		return bussType;
	}
	public void setBussType(String bussType) {
		this.bussType = bussType;
	}
	public String getTrxType() {
		return trxType;
	}
	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}
	public String getPayconType() {
		return payconType;
	}
	public void setPayconType(String payconType) {
		this.payconType = payconType;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getSacFlag() {
		return sacFlag;
	}
	public void setSacFlag(String sacFlag) {
		this.sacFlag = sacFlag;
	}
	public String getInnConFlag() {
		return innConFlag;
	}
	public void setInnConFlag(String innConFlag) {
		this.innConFlag = innConFlag;
	}
	public String getInnConStatus() {
		return innConStatus;
	}
	public void setInnConStatus(String innConStatus) {
		this.innConStatus = innConStatus;
	}
	public String getChaConFlag() {
		return chaConFlag;
	}
	public void setChaConFlag(String chaConFlag) {
		this.chaConFlag = chaConFlag;
	}
	public String getChaConStatus() {
		return chaConStatus;
	}
	public void setChaConStatus(String chaConStatus) {
		this.chaConStatus = chaConStatus;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getReversalStatus() {
		return reversalStatus;
	}
	public void setReversalStatus(String reversalStatus) {
		this.reversalStatus = reversalStatus;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getTrxBatchNo() {
		return trxBatchNo;
	}
	public void setTrxBatchNo(String trxBatchNo) {
		this.trxBatchNo = trxBatchNo;
	}
	public String getTrxErrDealType() {
		return trxErrDealType;
	}
	public void setTrxErrDealType(String trxErrDealType) {
		this.trxErrDealType = trxErrDealType;
	}
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}
	public BigDecimal getTransportExpenses() {
		return transportExpenses;
	}
	public void setTransportExpenses(BigDecimal transportExpenses) {
		this.transportExpenses = transportExpenses;
	}
	public BigDecimal getChannelCost() {
		return channelCost;
	}
	public void setChannelCost(BigDecimal channelCost) {
		this.channelCost = channelCost;
	}
	public BigDecimal getCusCharge() {
		return cusCharge;
	}
	public void setCusCharge(BigDecimal cusCharge) {
		this.cusCharge = cusCharge;
	}
	public Date getTrxSuccTime() {
		return trxSuccTime;
	}
	public void setTrxSuccTime(Date trxSuccTime) {
		this.trxSuccTime = trxSuccTime;
	}
	public String getCraccCardId() {
		return craccCardId;
	}
	public void setCraccCardId(String craccCardId) {
		this.craccCardId = craccCardId;
	}
	public String getDraccCardId() {
		return draccCardId;
	}
	public void setDraccCardId(String draccCardId) {
		this.draccCardId = draccCardId;
	}
	public String getTrxStateDesc() {
		return trxStateDesc;
	}
	public void setTrxStateDesc(String trxStateDesc) {
		this.trxStateDesc = trxStateDesc;
	}

	public String getRemittanceBatchId() {
		return remittanceBatchId;
	}

	public void setRemittanceBatchId(String remittanceBatchId) {
		this.remittanceBatchId = remittanceBatchId;
	}
	
}
