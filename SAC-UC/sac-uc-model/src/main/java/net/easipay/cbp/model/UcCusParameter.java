package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

public class UcCusParameter implements java.io.Serializable {
	private static final long serialVersionUID = 5163986561329094986L;
	    private Long id;
	    private String cusNo;
	    private String cusPlatAcc;
	    private String cusPlatAccName;
	    private String businessType;
	    private String trxType;
	    private String refundFlag;
	    private String currencyType;
	    private String sacBankAcc;
	    private String accName;
	    private String depositBank;
	    private String craccBankCode;
	    private BigDecimal feeRate;
	    private String feeComWay;
	    private String feeSacWay;
	    private String sacPeriod;
	    private BigDecimal trcUplim;
	    private Date CreateTime;
	    private String memo;
    
    
    public UcCusParameter(){
    	
    }


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCusNo() {
		return cusNo;
	}


	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}


	public String getCusPlatAcc() {
		return cusPlatAcc;
	}


	public void setCusPlatAcc(String cusPlatAcc) {
		this.cusPlatAcc = cusPlatAcc;
	}


	public String getCusPlatAccName() {
		return cusPlatAccName;
	}


	public void setCusPlatAccName(String cusPlatAccName) {
		this.cusPlatAccName = cusPlatAccName;
	}


	public String getBusinessType() {
		return businessType;
	}


	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	public String getTrxType() {
		return trxType;
	}


	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}


	public String getRefundFlag() {
		return refundFlag;
	}


	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}


	public String getCurrencyType() {
		return currencyType;
	}


	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}


	public String getSacBankAcc() {
		return sacBankAcc;
	}


	public void setSacBankAcc(String sacBankAcc) {
		this.sacBankAcc = sacBankAcc;
	}


	public String getAccName() {
		return accName;
	}


	public void setAccName(String accName) {
		this.accName = accName;
	}


	public String getDepositBank() {
		return depositBank;
	}


	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}


	public String getCraccBankCode() {
		return craccBankCode;
	}


	public void setCraccBankCode(String craccBankCode) {
		this.craccBankCode = craccBankCode;
	}


	public BigDecimal getFeeRate() {
		return feeRate;
	}


	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}


	public String getFeeComWay() {
		return feeComWay;
	}


	public void setFeeComWay(String feeComWay) {
		this.feeComWay = feeComWay;
	}


	public String getFeeSacWay() {
		return feeSacWay;
	}


	public void setFeeSacWay(String feeSacWay) {
		this.feeSacWay = feeSacWay;
	}


	public String getSacPeriod() {
		return sacPeriod;
	}


	public void setSacPeriod(String sacPeriod) {
		this.sacPeriod = sacPeriod;
	}


	public BigDecimal getTrcUplim() {
		return trcUplim;
	}


	public void setTrcUplim(BigDecimal trcUplim) {
		this.trcUplim = trcUplim;
	}


	public Date getCreateTime() {
		return CreateTime;
	}


	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}


	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}


	
	
}