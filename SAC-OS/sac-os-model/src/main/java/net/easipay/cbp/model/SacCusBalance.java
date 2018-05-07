package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SAC_CUS_BALANCE", schema = "SAC_SYN")
public class SacCusBalance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4892475179230381226L;
	private Long id;
	private Long balanceDetailId;
	private String platAccount;
	private BigDecimal trxAmount;
	private String currencyType;
	private BigDecimal trxCost;
	private BigDecimal trxBlance;
	private Long credentialId;
	private BigDecimal cost;
	private String trxType;
	private String rpFlag;
	private String chnNo;
	private String chnName;
	private String platBillNo;
	private String againstPltAcc;
	private Date createTime;
	private Date updateTime;
	private String memo;

	public SacCusBalance() {
	}

	public SacCusBalance(Long id, Long balanceDetailId, String platAccount, BigDecimal trxAmount, 
			String currencyType, BigDecimal trxCost, BigDecimal trxBlance, Long credentialId, 
			BigDecimal cost, String trxType, String rpFlag, String chnNo, String chnName, 
			String platBillNo, String againstPltAcc,
			Date createTime, Date updateTime, String memo)
	{
		this.id = id;
		this.balanceDetailId = balanceDetailId;
		this.platAccount = platAccount;
		this.trxAmount = trxAmount;
		this.currencyType = currencyType;
		this.trxCost = trxCost;
		this.trxBlance = trxBlance;
		this.credentialId = credentialId;
		this.cost = cost;
		this.trxType = trxType;
		this.rpFlag = rpFlag;
		this.chnNo = chnNo;
		this.chnName = chnName;
		this.platBillNo = platBillNo;
		this.againstPltAcc = againstPltAcc;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.memo = memo;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getBalanceDetailId()
	{
		return balanceDetailId;
	}

	public void setBalanceDetailId(Long balanceDetailId)
	{
		this.balanceDetailId = balanceDetailId;
	}

	public String getPlatAccount()
	{
		return platAccount;
	}

	public void setPlatAccount(String platAccount)
	{
		this.platAccount = platAccount;
	}

	public BigDecimal getTrxAmount()
	{
		return trxAmount;
	}

	public void setTrxAmount(BigDecimal trxAmount)
	{
		this.trxAmount = trxAmount;
	}

	public String getCurrencyType()
	{
		return currencyType;
	}

	public void setCurrencyType(String currencyType)
	{
		this.currencyType = currencyType;
	}

	public BigDecimal getTrxCost()
	{
		return trxCost;
	}

	public void setTrxCost(BigDecimal trxCost)
	{
		this.trxCost = trxCost;
	}

	public BigDecimal getTrxBlance()
	{
		return trxBlance;
	}

	public void setTrxBlance(BigDecimal trxBlance)
	{
		this.trxBlance = trxBlance;
	}

	public Long getCredentialId()
	{
		return credentialId;
	}

	public void setCredentialId(Long credentialId)
	{
		this.credentialId = credentialId;
	}

	public BigDecimal getCost()
	{
		return cost;
	}

	public void setCost(BigDecimal cost)
	{
		this.cost = cost;
	}

	public String getTrxType()
	{
		return trxType;
	}

	public void setTrxType(String trxType)
	{
		this.trxType = trxType;
	}

	public String getRpFlag()
	{
		return rpFlag;
	}

	public void setRpFlag(String rpFlag)
	{
		this.rpFlag = rpFlag;
	}

	public String getChnNo()
	{
		return chnNo;
	}

	public void setChnNo(String chnNo)
	{
		this.chnNo = chnNo;
	}

	public String getChnName()
	{
		return chnName;
	}

	public void setChnName(String chnName)
	{
		this.chnName = chnName;
	}

	public String getPlatBillNo()
	{
		return platBillNo;
	}

	public void setPlatBillNo(String platBillNo)
	{
		this.platBillNo = platBillNo;
	}

	public String getAgainstPltAcc()
	{
		return againstPltAcc;
	}

	public void setAgainstPltAcc(String againstPltAcc)
	{
		this.againstPltAcc = againstPltAcc;
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

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	
}
