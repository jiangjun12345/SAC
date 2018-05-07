package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

public class SacRecDetail implements java.io.Serializable
{

    private static final long serialVersionUID = -8500903038205043404L;
    private Long id;
    private Long recBatchId;
    private String busiType;
    private Date recStartDate;
    private Date recEndDate;
    private String diffType;
    private String trxSerialNo;
    private Date trxTime;
    private BigDecimal payAmount;
    private String currencyType;
    private String bankSerialNo;
    private String chnCode;
    private String payconType;
    private String recOper;
    private String trxCode;
    private String privDomain;
    private String memo;

    public String getBusiType()
    {
	return busiType;
    }

    public void setBusiType(String busiType)
    {
	this.busiType = busiType;
    }

    public Long getId()
    {
	return id;
    }

    public void setId(Long id)
    {
	this.id = id;
    }

    public Long getRecBatchId()
    {
	return recBatchId;
    }

    public void setRecBatchId(Long recBatchId)
    {
	this.recBatchId = recBatchId;
    }

    public String getDiffType()
    {
	return diffType;
    }

    public void setDiffType(String diffType)
    {
	this.diffType = diffType;
    }

    public String getTrxSerialNo()
    {
	return trxSerialNo;
    }

    public void setTrxSerialNo(String trxSerialNo)
    {
	this.trxSerialNo = trxSerialNo;
    }

    public Date getTrxTime()
    {
	return trxTime;
    }

    public void setTrxTime(Date trxTime)
    {
	this.trxTime = trxTime;
    }

    public BigDecimal getPayAmount()
    {
	return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount)
    {
	this.payAmount = payAmount;
    }

    public String getBankSerialNo()
    {
	return bankSerialNo;
    }

    public void setBankSerialNo(String bankSerialNo)
    {
	this.bankSerialNo = bankSerialNo;
    }

    public String getChnCode()
    {
	return chnCode;
    }

    public void setChnCode(String chnCode)
    {
	this.chnCode = chnCode;
    }

    public String getPayconType()
    {
	return payconType;
    }

    public void setPayconType(String payconType)
    {
	this.payconType = payconType;
    }

    public String getRecOper()
    {
	return recOper;
    }

    public void setRecOper(String recOper)
    {
	this.recOper = recOper;
    }

    public String getMemo()
    {
	return memo;
    }

    public void setMemo(String memo)
    {
	this.memo = memo;
    }

    public String getCurrencyType()
    {
	return currencyType;
    }

    public void setCurrencyType(String currencyType)
    {
	this.currencyType = currencyType;
    }

    public Date getRecStartDate()
    {
	return recStartDate;
    }

    public void setRecStartDate(Date recStartDate)
    {
	this.recStartDate = recStartDate;
    }

    public Date getRecEndDate()
    {
	return recEndDate;
    }

    public void setRecEndDate(Date recEndDate)
    {
	this.recEndDate = recEndDate;
    }

    public String getTrxCode()
    {
	return trxCode;
    }

    public void setTrxCode(String trxCode)
    {
	this.trxCode = trxCode;
    }

    public String getPrivDomain()
    {
	return privDomain;
    }

    public void setPrivDomain(String privDomain)
    {
	this.privDomain = privDomain;
    }

}
