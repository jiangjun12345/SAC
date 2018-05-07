package net.easipay.cbp.model;

// Generated 2015-7-6 15:57:25 by Hibernate Tools 3.2.2.GA

import java.math.BigDecimal;
import java.util.Date;

public class SacCusParameter implements java.io.Serializable
{
    private static final long serialVersionUID = 266912375772298851L;
    private Long id;
    private String cusNo;
    private String cusType;
    private String merchantNcode;
    private String cusName;
    private String cusPlatAcc;
    private String cusPlatAccName;
    private String refundFlag;
    private String currencyType;
    private String sacBankAcc;
    private String accName;
    private String depositBank;
    private String craccBankCode;
    private BigDecimal feeRate;
    private String feeComWay;
    private String feeSacWay;
    private String sacType;
    private String sacPeriod;
    private String intervalNumber;
    private String sacCurrency;
    private BigDecimal sacStartAmount;
    private BigDecimal trxUpLim;
    private Date createTime;
    private Date updateTime;
    private String isValidFlag;
    private String cusFeeFlag;
    private String orgCardId;
    private String bussType;
    private String memo;
    
    public Long getId()
    {
	return id;
    }

    public void setId(Long id)
    {
	this.id = id;
    }
    public String getCusType()
    {
        return cusType;
    }

    public void setCusType(String cusType)
    {
        this.cusType = cusType;
    }

    public String getMerchantNcode()
    {
        return merchantNcode;
    }

    public void setMerchantNcode(String merchantNcode)
    {
        this.merchantNcode = merchantNcode;
    }

    public String getCusNo()
    {
	return cusNo;
    }

    public void setCusNo(String cusNo)
    {
	this.cusNo = cusNo;
    }

    public String getCusPlatAcc()
    {
	return cusPlatAcc;
    }

    public void setCusPlatAcc(String cusPlatAcc)
    {
	this.cusPlatAcc = cusPlatAcc;
    }

    public String getCusPlatAccName()
    {
	return cusPlatAccName;
    }

    public void setCusPlatAccName(String cusPlatAccName)
    {
	this.cusPlatAccName = cusPlatAccName;
    }

    public String getRefundFlag()
    {
	return refundFlag;
    }

    public void setRefundFlag(String refundFlag)
    {
	this.refundFlag = refundFlag;
    }

    public String getCurrencyType()
    {
	return currencyType;
    }

    public void setCurrencyType(String currencyType)
    {
	this.currencyType = currencyType;
    }

    public String getSacBankAcc()
    {
	return sacBankAcc;
    }

    public void setSacBankAcc(String sacBankAcc)
    {
	this.sacBankAcc = sacBankAcc;
    }

    public String getAccName()
    {
	return accName;
    }

    public void setAccName(String accName)
    {
	this.accName = accName;
    }

    public String getDepositBank()
    {
	return depositBank;
    }

    public void setDepositBank(String depositBank)
    {
	this.depositBank = depositBank;
    }

    public String getCraccBankCode()
    {
	return craccBankCode;
    }

    public void setCraccBankCode(String craccBankCode)
    {
	this.craccBankCode = craccBankCode;
    }

    public BigDecimal getFeeRate()
    {
	return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate)
    {
	this.feeRate = feeRate;
    }

    public String getFeeComWay()
    {
	return feeComWay;
    }

    public void setFeeComWay(String feeComWay)
    {
	this.feeComWay = feeComWay;
    }

    public String getFeeSacWay()
    {
	return feeSacWay;
    }

    public void setFeeSacWay(String feeSacWay)
    {
	this.feeSacWay = feeSacWay;
    }

    public String getSacType()
    {
	return sacType;
    }

    public void setSacType(String sacType)
    {
	this.sacType = sacType;
    }

    public String getSacPeriod()
    {
	return sacPeriod;
    }

    public void setSacPeriod(String sacPeriod)
    {
	this.sacPeriod = sacPeriod;
    }

    public String getIntervalNumber()
    {
	return intervalNumber;
    }

    public void setIntervalNumber(String intervalNumber)
    {
	this.intervalNumber = intervalNumber;
    }

    public BigDecimal getTrxUpLim()
    {
	return trxUpLim;
    }

    public void setTrxUpLim(BigDecimal trxUpLim)
    {
	this.trxUpLim = trxUpLim;
    }

    public Date getCreateTime()
    {
	return createTime;
    }

    public void setCreateTime(Date createTime)
    {
	this.createTime = createTime;
    }

    public String getMemo()
    {
	return memo;
    }

    public void setMemo(String memo)
    {
	this.memo = memo;
    }

    public String getSacCurrency()
    {
	return sacCurrency;
    }

    public void setSacCurrency(String sacCurrency)
    {
	this.sacCurrency = sacCurrency;
    }

    public BigDecimal getSacStartAmount()
    {
	return sacStartAmount;
    }

    public void setSacStartAmount(BigDecimal sacStartAmount)
    {
	this.sacStartAmount = sacStartAmount;
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

    public String getCusName()
    {
	return cusName;
    }

    public void setCusName(String cusName)
    {
	this.cusName = cusName;
    }

    public String getCusFeeFlag()
    {
        return cusFeeFlag;
    }

    public void setCusFeeFlag(String cusFeeFlag)
    {
        this.cusFeeFlag = cusFeeFlag;
    }

    public String getOrgCardId()
    {
        return orgCardId;
    }

    public void setOrgCardId(String orgCardId)
    {
        this.orgCardId = orgCardId;
    }

    public String getBussType()
    {
        return bussType;
    }

    public void setBussType(String bussType)
    {
        this.bussType = bussType;
    }

}
