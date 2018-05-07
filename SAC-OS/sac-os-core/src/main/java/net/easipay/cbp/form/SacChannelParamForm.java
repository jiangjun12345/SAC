package net.easipay.cbp.form;

import javax.persistence.Entity;

@Entity
public class SacChannelParamForm implements java.io.Serializable
{

	private static final long serialVersionUID = -7741715833392679745L;
	private String chnCode;
	private String bankNodeCode;
	private String chnType;
	private String chnName;
	private String sacBankName;
	private String accountName;
	private String bankAcc;
	private String craccBankCode;
	private String currencyType;
	private String sacPeriod;
	private String costRate;
	private String costComWay;
	private String costSacWay;
	private String isValidFlag;
	private String memo;
	
	public SacChannelParamForm()
	{
	}

	public String getChnCode() {
		return chnCode;
	}

	public void setChnCode(String chnCode) {
		this.chnCode = chnCode;
	}

	public String getBankNodeCode() {
		return bankNodeCode;
	}

	public void setBankNodeCode(String bankNodeCode) {
		this.bankNodeCode = bankNodeCode;
	}

	public String getChnType() {
		return chnType;
	}

	public void setChnType(String chnType) {
		this.chnType = chnType;
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

	public String getSacPeriod() {
		return sacPeriod;
	}

	public void setSacPeriod(String sacPeriod) {
		this.sacPeriod = sacPeriod;
	}

	public String getCostRate() {
		return costRate;
	}

	public void setCostRate(String costRate) {
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

	public String getIsValidFlag() {
		return isValidFlag;
	}

	public void setIsValidFlag(String isValidFlag) {
		this.isValidFlag = isValidFlag;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	
	
	
}
