package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

public class UcChannelParam implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
    private Long id;
    
    private String chnNo;
    
    private String chnName;
    
    private String sacBankName;
    
    private String accountName;
    
    private String bankAcc;
    
    private String craccBankCode;
    
    private String currencyType;
    
    private Long sacPeriod;
    
    private BigDecimal costRate;
    
    private String costComWay;
    
    private String costSacWay;
    
    private Date createTime;
    
    private String memo;
    
    public UcChannelParam(){
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChnNo() {
		return chnNo;
	}

	public void setChnNo(String chnNo) {
		this.chnNo = chnNo;
	}

	public String getChnName() {
		return chnName;
	}

	public void setChnName(String chnName) {
		this.chnName = chnName;
	}

	public String getSacBankName() {
		return sacBankName;
	}

	public void setSacBankName(String sacBankName) {
		this.sacBankName = sacBankName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	public String getCraccBankCode() {
		return craccBankCode;
	}

	public void setCraccBankCode(String craccBankCode) {
		this.craccBankCode = craccBankCode;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public Long getSacPeriod() {
		return sacPeriod;
	}

	public void setSacPeriod(Long sacPeriod) {
		this.sacPeriod = sacPeriod;
	}

	public BigDecimal getCostRate() {
		return costRate;
	}

	public void setCostRate(BigDecimal costRate) {
		this.costRate = costRate;
	}

	public String getCostComWay() {
		return costComWay;
	}

	public void setCostComWay(String costComWay) {
		this.costComWay = costComWay;
	}

	public String getCostSacWay() {
		return costSacWay;
	}

	public void setCostSacWay(String costSacWay) {
		this.costSacWay = costSacWay;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
   
	
}