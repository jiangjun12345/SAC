package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SAC_CHANNEL_PARAM_CMD", schema = "SAC_SYN")
public class SacChannelParamCmd implements java.io.Serializable
{

	private static final long serialVersionUID = -7741715833392679745L;
	private Long id;
	private String chnNo;
	private String chnName;
	private String bankNodeCode;
	private String chnCode;
	private String chnType;
	private String sacBankName;
	private String accountName;
	private String bankAcc;
	private String craccBankCode;
	private String currencyType;
	private Integer sacPeriod;
	private BigDecimal costRate;
	private String costComWay;
	private String costSacWay;
	private Date createTime;
	private Date updateTime;
	private String isValidFlag;
	private String chnFeeFlag;
	private String memo;
	private String cmdState;
	private String cmdMemo;
	
	public String getCmdState() {
		return cmdState;
	}

	public void setCmdState(String cmdState) {
		this.cmdState = cmdState;
	}

	public String getCmdMemo() {
		return cmdMemo;
	}

	public void setCmdMemo(String cmdMemo) {
		this.cmdMemo = cmdMemo;
	}

	public SacChannelParamCmd()
	{
	}

	public SacChannelParamCmd(Long id, String bankNodeCode, String chnName, String sacBankName, String accountName, String bankAcc, String craccBankCode, String currencyType, Integer sacPeriod,
			BigDecimal costRate, String costComWay, String costSacWay,  Date createTime, Date updateTime, String isValidFlag, String memo,String bankSubjectCode)
	{
		this.id = id;
		this.bankNodeCode = bankNodeCode;
		this.chnName = chnName;
		this.sacBankName = sacBankName;
		this.accountName = accountName;
		this.bankAcc = bankAcc;
		this.craccBankCode = craccBankCode;
		this.currencyType = currencyType;
		this.sacPeriod = sacPeriod;
		this.costRate = costRate;
		this.costComWay = costComWay;
		this.costSacWay = costSacWay;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.isValidFlag = isValidFlag;
		this.memo = memo;
		//this.bankSubjectCode = bankSubjectCode;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getBankNodeCode()
	{
		return bankNodeCode;
	}

	public void setBankNodeCode(String bankNodeCode)
	{
		this.bankNodeCode = bankNodeCode;
	}

	public String getChnName()
	{
		return chnName;
	}

	public void setChnName(String chnName)
	{
		this.chnName = chnName;
	}

	public String getSacBankName()
	{
		return sacBankName;
	}

	public void setSacBankName(String sacBankName)
	{
		this.sacBankName = sacBankName;
	}

	public String getAccountName()
	{
		return accountName;
	}

	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}

	public String getBankAcc()
	{
		return bankAcc;
	}

	public void setBankAcc(String bankAcc)
	{
		this.bankAcc = bankAcc;
	}

	public String getCraccBankCode()
	{
		return craccBankCode;
	}

	public void setCraccBankCode(String craccBankCode)
	{
		this.craccBankCode = craccBankCode;
	}

	public String getCurrencyType()
	{
		return currencyType;
	}

	public void setCurrencyType(String currencyType)
	{
		this.currencyType = currencyType;
	}

	public Integer getSacPeriod()
	{
		return sacPeriod;
	}

	public void setSacPeriod(Integer sacPeriod)
	{
		this.sacPeriod = sacPeriod;
	}

	public BigDecimal getCostRate()
	{
		return costRate;
	}

	public void setCostRate(BigDecimal costRate)
	{
		this.costRate = costRate;
	}

	public String getCostComWay()
	{
		return costComWay;
	}

	public void setCostComWay(String costComWay)
	{
		this.costComWay = costComWay;
	}

	public String getCostSacWay()
	{
		return costSacWay;
	}

	public void setCostSacWay(String costSacWay)
	{
		this.costSacWay = costSacWay;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public String getIsValidFlag()
	{
		return isValidFlag;
	}

	public void setIsValidFlag(String isValidFlag)
	{
		this.isValidFlag = isValidFlag;
	}

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public String getChnNo() {
		return chnNo;
	}

	public void setChnNo(String chnNo) {
		this.chnNo = chnNo;
	}

	public String getChnCode()
	{
		return chnCode;
	}

	public void setChnCode(String chnCode)
	{
		this.chnCode = chnCode;
	}

	public String getChnType()
	{
		return chnType;
	}

	public void setChnType(String chnType)
	{
		this.chnType = chnType;
	}

	public String getChnFeeFlag() {
		return chnFeeFlag;
	}

	public void setChnFeeFlag(String chnFeeFlag) {
		this.chnFeeFlag = chnFeeFlag;
	}
	
}
